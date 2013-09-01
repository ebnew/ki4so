package com.github.ebnew.ki4so.client.key;

import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * 获取应用对应的加密Key的服务。
 * @author Administrator
 *
 */
public interface KeyService {
	
	/**
	 * 根据应用的ID查找对应的秘钥信息。可能从远程获取
	 * 秘钥信息，但是要注意安全问题。
	 * @param appId 应用的ID.
	 * @return 应用ID对应的秘钥信息。
	 */
	public Ki4soKey findKeyByAppId(String appId);

}
