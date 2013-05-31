package com.github.ebnew.ki4so.core.service;

import com.github.ebnew.ki4so.core.authentication.Credential;

/**
 * 核心服务接口，定义了所有的核心方法。
 * 该接口是一个门面类，定义了ki4so核心的对外
 * 提供的方法。
 * @author Administrator
 *
 */
public interface Ki4soService {
	
	/**
	 * 使用一个用户凭据登录ki4so中心认证服务。
	 * @param credential 用户凭据。
	 * @return 登录结果。
	 */
	public LoginResult login(Credential credential);
	

}
