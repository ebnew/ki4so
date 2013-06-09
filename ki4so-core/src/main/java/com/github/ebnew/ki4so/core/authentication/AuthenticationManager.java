package com.github.ebnew.ki4so.core.authentication;

import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;

/**
 * 认证管理器，负责对用户凭证进行有效性认证。
 * @author Administrator
 *
 */
public interface AuthenticationManager {
	
	/**
	 * 对用户凭据进行认证，若认证失败则抛出异常，若成功返回认证结果。
	 * @param credential 用户凭据。
	 * @return 当认证通过后，返回认证结果。
	 * @throws InvalidCredentialException 当输入的凭据不合法的时候会抛出该异常。
	 */
	public Authentication authenticate(Credential credential) throws InvalidCredentialException;

}
