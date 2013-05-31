package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;

/**
 * 组合凭据解析器，组合两种解析器，按照优先级顺序，从http请求参数或者cookie中解析出优先级较高的凭据，若无优先级高的凭据，则解析优先级低的凭据。
 * @author bidlink
 * @version 1.0
 * @created 31-五月-2013 8:39:55
 */
public class CompositeCredentialResolver implements CredentialResolver {

	private CredentialResolver encryCredentialResolver;
	
	private CredentialResolver usernamePasswordCredentialResolver;

	public CompositeCredentialResolver(){

	}

	/**
	 * <font color="#3f5fbf">从<u>http请求参数的cookie或者参数值中解析出凭据信息对象。</u></font>
	 * <font color="#3f5fbf"><u>返回解析后的凭据对象。
	 * 先解析加密后的已认证凭据，若没有则再解析出原始的用户名秘密凭据，若任何凭据都没有则返回null.</u></font>
	 * 
	 * @param request
	 */
	public Credential resolveCredential(HttpServletRequest request){
		return null;
	}

}