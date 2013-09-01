package com.github.ebnew.ki4so.core.key;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.github.ebnew.ki4so.common.Base64Coder;
import com.github.ebnew.ki4so.common.DESCoder;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

public class DefaultEncryCredentialResolver implements EncryCredentialResolver {
	
	private static final Logger LOGGER = Logger.getLogger(DefaultEncryCredentialResolver.class.getName());

	@Override
	public EncryCredentialInfo decrypt(EncryCredential encryCredential,
			Ki4soKey ki4soKey) {
		//不为空。
		if(encryCredential!=null && !StringUtils.isEmpty(encryCredential.getCredential())){
			String credential = encryCredential.getCredential();
			try {
				return parseEncryCredential(credential, ki4soKey);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		//若为空信息，则返回空。
		return null;
	}
	
	/**
	 * 解析加密后的凭据信息为凭据对象。过程与加密过程相反的逆过程。
	 * @param 加密过的凭据字符串。
	 * @return 凭据对象。
	 * @throws Exception 
	 */
	private EncryCredentialInfo parseEncryCredential(String credential, Ki4soKey ki4soKey) throws Exception{
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		try{
			//先使用URL进行解码。
			credential = URLDecoder.decode(credential, "UTF-8");
			//问号分割字符串。
			String[] items = credential.split("\\?");
			//如果长度是2.
			if(items.length==2){
				//第2个字符串不为空，先解析第二个字符串。
				if(!StringUtils.isEmpty(items[1])){
					//使用&分割字符。
					String[] params = items[1].split("&");
					for(int i=0; i<params.length; i++){
						if(params[i]!=null){
							//使用等号分割。
							String[] values = params[i].split("=");
							if(values!=null && values.length==2){
								if("appId".equals(values[0])){
									encryCredentialInfo.setAppId(values[1]);
								}
								else if("keyId".equals(values[0])){
									encryCredentialInfo.setKeyId(values[1]);
								} 
							}
						}
					}
				}
				else{
					throw new Exception("不是以问号分割两个字符串的格式");
				}
				//第1个字符串不为空
				if(!StringUtils.isEmpty(items[0])){
					//使用base64解码为源字符串。
					byte[] data =  Base64Coder.decryptBASE64(items[0]);
					//查询键值。
					if(ki4soKey!=null){
						//使用密钥进行解密。
						byte[] origin = DESCoder.decrypt(data, ki4soKey.toSecurityKey());
						//将byte数组转换为字符串。
						String json = new String(origin);
						@SuppressWarnings("rawtypes")
						Map map = (Map)JSON.parse(json);
						if(map!=null){
							Object userId = map.get("userId");
							Object createTime = map.get("createTime");
							Object expiredTime = map.get("expiredTime");
							encryCredentialInfo.setUserId(userId==null?null:userId.toString());
							encryCredentialInfo.setCreateTime(createTime==null?null:new Date((Long.parseLong(createTime.toString()))));
							encryCredentialInfo.setExpiredTime(expiredTime==null?null:new Date((Long.parseLong(expiredTime.toString()))));
						}
					}
				}
				else{
					throw new Exception("格式错误，第一个字符部位空");
				}
			}
			else{
				throw new Exception("格式错误，长度不是2");
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "parse encry credential exception", e);
			throw new Exception("解密错误");
		}
		
		return encryCredentialInfo;
	}

	@Override
	public String encrypt(EncryCredentialInfo encryCredentialInfo,
			Ki4soKey ki4soKey) {
		StringBuffer sb = new StringBuffer();
		if(encryCredentialInfo!=null){
			try {
				String data = encryptSensitiveInfo(encryCredentialInfo, ki4soKey);
				sb.append(data).append("?appId=").append(encryCredentialInfo.getAppId())
				.append("&keyId=").append(encryCredentialInfo.getKeyId());
				return URLEncoder.encode(sb.toString(), "UTF-8");
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "encrypt data exception", e);
			}
		}
		return sb.toString();
	}
	
	private String encryptSensitiveInfo(EncryCredentialInfo encryCredentialInfo, Ki4soKey ki4soKey) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", encryCredentialInfo.getUserId());
		map.put("createTime", encryCredentialInfo.getCreateTime().getTime());
		map.put("expiredTime", encryCredentialInfo.getExpiredTime().getTime());
		if(ki4soKey!=null){
			//查询键值。
			Key key = ki4soKey.toSecurityKey();
			byte[] data = DESCoder.encrypt(JSON.toJSONBytes(map), key);
			return Base64Coder.encryptBASE64(data);
		}
		return null;
	}

}
