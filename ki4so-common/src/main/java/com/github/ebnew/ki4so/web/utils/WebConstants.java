package com.github.ebnew.ki4so.web.utils;

/**
 * web相关的常量类，定义了与web相关的所有常量值。
 * @author burgess yang
 *
 */
public interface WebConstants {
	
	/**
	 * ki4so中心认证服务器写入到用户web客户端cookie中的认证加密后的凭据的键名称。
	 */
	public static final String KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY = "KI4SO_SERVER_EC";
	
	/**
	 * ki4so客户端应用服务器写入到用户web客户端cookie中的认证加密后的凭据的键名称。
	 */
	public static final String KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY = "KI4SO_CLIENT_EC";
	
	/**
	 * 目的服务地址service的参数名。
	 */
	public static final String SERVICE_PARAM_NAME = "service";
	
	/**
	 * 目的服务地址存储在session中的key值。
	 */
	public static final String KI4SO_SERVICE_KEY_IN_SESSION = "KI4SO_SERVICE_KEY";


}
