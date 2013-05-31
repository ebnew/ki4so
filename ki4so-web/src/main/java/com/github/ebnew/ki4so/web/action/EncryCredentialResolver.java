package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;

/**
 * 经过认证加密后的凭据信息解析器，从http请求的cookie中解析出对应的加密后的凭据信息。
 * @author burgess yang
 *
 */
public class EncryCredentialResolver implements CredentialResolver {

	@Override
	public Credential resolveCredential(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
