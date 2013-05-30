package com.github.ebnew.ki4so.core.authentication;

/**
 * 认证管理器，负责对用户凭证进行有效性认证。
 * @author Administrator
 *
 */
public interface AuthenticationManager {
	
	/**
	 * 对用户凭据进行认证，若认证失败则抛出异常，若成功返回认证结果。
	 * @param credential 用户凭据。
	 * @return 认证结果。
	 */
	public Authentication authenticate(Credential credential);

}
