package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;

/**
 * 凭据解析器，从http请求中解析参数中的各种类型的凭据。
 * 包括已认证凭据和未认证凭据。
 * @author burgess yang
 *
 */
public interface CredentialResolver {
	
	
	/**
	 * 从http请求中解析没有经过认证的原始的用户凭据，若没有解析出合法的凭据，
	 * 则返回null
	 * @param request http servlet请求对象，不能空。
	 * @return 若没有合法的凭据，请返回空。
	 */
	public Credential resolveCredential(HttpServletRequest request);

}
