package com.github.ebnew.ki4so.core.authentication;

/**
 * 加密凭据的管理器，包括对加密凭据的加密和解密等操作。
 * @author burgess yang
 *
 */
public interface EncryCredentialManager {

	/**
	 * 凭据的解密。
	 */
	public void decrypt(EncryCredential encryCredential);
	
	
	/**
	 * 凭据的加密。
	 */
	public void encrypt();
	
}
