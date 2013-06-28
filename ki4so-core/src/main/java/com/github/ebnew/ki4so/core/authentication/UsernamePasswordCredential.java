package com.github.ebnew.ki4so.core.authentication;

import java.util.Map;

/**
 * 用户名和密码形式的未经过认证的原始用户凭证
 * @author bugess yang
 */
public class UsernamePasswordCredential implements Credential, Parameter{
	
	/**
	 * 用户登录名。
	 */
	private String username;
	
	/**
	 * 用户登录密码。
	 */
	private String password;
	
	/**
	 * 其它参数表。
	 */
	private Map<String, Object> paramters;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean isOriginal() {
		return true;
	}

	@Override
	public Object getParameterValue(String paramName) {
		return this.paramters==null?null:this.paramters.get(paramName);
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.paramters;
	}

}
