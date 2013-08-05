package com.github.ebnew.ki4so.core.authentication;

import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 加密凭据的管理器，包括对加密凭据的加密和解密等操作。
 * @author burgess yang
 *
 */
public interface EncryCredentialManager {

	/**
	 * 对编码的凭据信息进行解码。
	 */
	public void decrypt(EncryCredential encryCredential);
	
	
	/**
	 * 对凭据信息进行加密和编码处理。
	 */
	public String encrypt(EncryCredentialInfo encryCredentialInfo);
	
}
