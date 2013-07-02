package com.github.ebnew.ki4so.core.authentication.handlers;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.exception.AuthenticationException;
import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;

/**
 * 抽象的认证处理器实现，提供了具体实现类可以在认证之前和认证之后执行一些任务。
 * 
 * @author burgess yang
 * 
 */
public abstract class AbstractPreAndPostProcessingAuthenticationHandler
		implements AuthenticationHandler {
	/**
	 * Method to execute before authentication occurs.
	 * 
	 * @param credentials
	 *            the Credentials supplied
	 * @return true if authentication should continue, false otherwise.
	 */
	protected boolean preAuthenticate(final Credential credential) {
		return true;
	}

	/**
	 * Method to execute after authentication occurs.
	 * 
	 * @param credentials
	 *            the supplied credentials
	 * @param authenticated
	 *            the result of the authentication attempt.
	 * @return true if the handler should return true, false otherwise.
	 */
	protected boolean postAuthenticate(final Credential credential,
			final boolean authenticated) {
		return authenticated;
	}

	public final boolean authenticate(final Credential credential)
			throws AuthenticationException {

		if (!preAuthenticate(credential)) {
			return false;
		}

		final boolean authenticated = doAuthentication(credential);

		return postAuthenticate(credential, authenticated);
	}

	
	/**
	 * 执行真正的认证方法。
	 * @param credential 用户凭据。
	 * @return 认证结果。
	 * @throws InvalidCredentialException
	 */
	protected abstract boolean doAuthentication(final Credential credential)
			throws AuthenticationException;

}
