package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.UsernamePasswordCredential;

/**
 * 用户名和密码凭据解析器，从参数中解析出用户的用户名和密码信息。
 * @author burgess yang
 *
 */
public class UsernamePasswordCredentialResolver extends AbstractParameterCredentialResolver{
	
	/**
	 * 用户名的参数名。
	 */
	public static final String USERNAME_PARAM_NAME = "username";
	
	/**
	 * 密码的参数名。
	 */
	public static final String PASSWORD_PARAM_NAME = "password";

	@Override
	public Credential doResolveCredential(HttpServletRequest request) {
		if(request!=null && request.getParameter(USERNAME_PARAM_NAME)!=null && 
				request.getParameter(PASSWORD_PARAM_NAME)!=null){
			UsernamePasswordCredential credential = new UsernamePasswordCredential();
			credential.setUsername(request.getParameter(USERNAME_PARAM_NAME));
			credential.setPassword(request.getParameter(PASSWORD_PARAM_NAME));
			return credential;
		}
		return null;
	}

}
