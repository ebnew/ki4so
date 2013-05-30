package com.github.ebnew.ki4so.core.authentication;

import java.util.List;

/**
 * 认证管理器默认的实现类，
 * @author bidlink
 * @version 1.0
 * @updated 30-五月-2013 21:33:16
 */
public class AuthenticationManagerImpl implements AuthenticationManager {

	/**
	 * 认证处理器集合，使用多个认证处理器对凭证逐一校验，只要有一个通过即可。
	 */
	private List<AuthenticationHandler> authenticationHandlers;
	
	public AuthenticationManagerImpl(){

	}


	/**
	 * 对用户凭据进行认证，返回认证结果。
	 * 
	 * @param credential
	 */
	public Authentication authenticate(Credential credential){
		return null;
	}

}