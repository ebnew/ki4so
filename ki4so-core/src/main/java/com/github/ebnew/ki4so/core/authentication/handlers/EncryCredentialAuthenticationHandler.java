package com.github.ebnew.ki4so.core.authentication.handlers;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.exception.AuthenticationException;

/**
 * 认证后的凭据认证处理器实现类，需要验证认证后的凭据是否有效，凭据是否过期等等其它
 * 合法性验证。
 * @author burgess yang
 *
 */
public class EncryCredentialAuthenticationHandler extends
		AbstractPreAndPostProcessingAuthenticationHandler {
	
	/** Default class to support if one is not supplied. */
	private static final Class<EncryCredential> DEFAULT_CLASS = EncryCredential.class;

	/**
	 * @return true if the credentials are not null and the credentials class is
	 *         equal to the class defined in classToSupport.
	 */
	public final boolean supports(final Credential credential) {
		return credential != null
				&& (DEFAULT_CLASS.equals(credential.getClass()) || (DEFAULT_CLASS
						.isAssignableFrom(credential.getClass())));
	}

	@Override
	protected boolean doAuthentication(Credential credential)
			throws AuthenticationException {
		return false;
	}

}
