package com.github.ebnew.ki4so.client.key;

import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * 默认的秘钥信息获取实现类，该类只是一个简单的实现，非常不安全。
 * 在生产环境，建议请使用公钥和私钥的方式对秘钥信息
 * 进行加密，避免秘钥在公网环境下泄露。请自行加强安全性。
 * @author Administrator
 */
public class DefaultKeyServiceImpl implements KeyService{
	
	private String ki4soServerFetchKeyUrl;
	
	public DefaultKeyServiceImpl(String ki4soServerFetchKeyUrl) {
		super();
		this.ki4soServerFetchKeyUrl = ki4soServerFetchKeyUrl;
	}


	@Override
	public Ki4soKey findKeyByAppId(String appId) {
		return null;
	}

}
