package com.github.ebnew.ki4so.client.key;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * 默认的秘钥信息获取实现类，该类只是一个简单的实现，非常不安全。 在生产环境，建议请使用公钥和私钥的方式对秘钥信息
 * 进行加密，避免秘钥在公网环境下泄露。请自行加强安全性。
 * 
 * @author Administrator
 */
@SuppressWarnings("deprecation")
public class DefaultKeyServiceImpl implements KeyService {

	private static Logger logger = Logger.getLogger(DefaultKeyServiceImpl.class
			.getName());

	private String ki4soServerFetchKeyUrl;

	/**
	 * 本应用的秘钥信息。
	 */
	private Ki4soKey ki4soKey;

	/**
	 * 本应用的应用id.
	 */
	private String appId;

	private static DefaultHttpClient httpClient = new DefaultHttpClient();

	public DefaultKeyServiceImpl(String ki4soServerFetchKeyUrl, String appId) {
		super();
		this.ki4soServerFetchKeyUrl = ki4soServerFetchKeyUrl;
		this.appId = appId;
	}

	@Override
	public Ki4soKey findKeyByAppId(String appId) {
		if (ki4soKey == null) {
			// do fetch key from remote server.
			this.ki4soKey = fetchKeyFromKi4soServer();
		}
		return ki4soKey;
	}

	private Ki4soKey fetchKeyFromKi4soServer() {
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(ki4soServerFetchKeyUrl);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("appId", this.appId));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				String content = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				return JSON.parseObject(content, Ki4soKey.class);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "fetch ki4so key from server error, the url is ["+ki4soServerFetchKeyUrl+"]", e);
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
		return null;
	}

	@Override
	public Ki4soKey findKeyByKeyId(String keyId) {
		if (ki4soKey == null) {
			return this.findKeyByAppId(null);
		}
		return ki4soKey;
	}

}
