package com.github.ebnew.ki4so.core.authentication;

/**
 * 认证成功后的处理器，该接口的职责是将
 * 用户认证主体，用户凭据转换为一个合适的
 * 认证结果对象。根据用户凭据中的信息，参数
 * 进行合适的转换。
 * @author burgess yang
 *
 */
public interface AuthenticationPostHandler {
	
	/**
	 * 认证后的处理方法，将用户的凭据和主体转换为一个最终的认证结果对象。
	 * @param authenticated 认证是否通过。
	 * @param credential 用户凭据。
	 * @param principal 用户主体。
	 * @return 认证结果对象信息。
	 */
	public Authentication postAuthentication(boolean authenticated, Credential credential, Principal principal);

}
