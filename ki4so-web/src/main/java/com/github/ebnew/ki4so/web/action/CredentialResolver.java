package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;

/**
 * 凭据解析器，从http请求的cookie，参数等值中解析出各种类型的用户凭证，该接口由具体实现类负责具体凭据解析。
 * @author burgess yang
 *
 */
public interface CredentialResolver {
	
	
	/**
	 * 从http请求参数的cookie或者参数值中解析出凭据信息对象，返回解析后的凭据对象。
	 * @param request http servlet请求对象，不能空。
	 * @return 若没有合法的凭据，请返回空。
	 */
	public Credential resolveCredential(HttpServletRequest request);

}
