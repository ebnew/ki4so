package com.github.ebnew.ki4so.core.authentication;

/**
 * 认证过的加密后的用户凭证，用于输出给客户端。
 * @author bugess yang
 *
 */
public class EncryCredential extends AbstractParameter implements Credential{
	
	/**
	 * 加密后的用户凭据串。
	 */
	private String credential;

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public EncryCredential(String credential) {
		super();
		this.credential = credential;
	}

	@Override
	public boolean isOriginal() {
		return false;
	}
	

}
