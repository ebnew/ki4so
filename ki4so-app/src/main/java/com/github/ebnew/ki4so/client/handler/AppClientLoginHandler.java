package com.github.ebnew.ki4so.client.handler;

/**
 * 本应用登录处理器接口，请实现该处理器，
 * 实现本接口，将本应用的登录逻辑写在这里。
 * @author Administrator
 *
 */
public interface AppClientLoginHandler {
	
	/**
	 * 登录本应用。
	 * @param id 用户标识信息，唯一标识一个用户。
	 */
	public void loginClient(String id);

}
