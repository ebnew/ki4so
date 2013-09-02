package com.github.ebnew.ki4so.core.authentication;

import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 加密凭据的管理器，包括对加密凭据的加密和解密等操作。
 * @author burgess yang
 *
 */
public interface EncryCredentialManager {

	/**
	 * 对编码的凭据信息进行解码，解码后为
	 * 一个凭据对象。
	 * @param encryCredential 加密和编码后的凭据信息。
	 * @return 解密和解码后的凭据信息。
	 */
	public EncryCredentialInfo decrypt(EncryCredential encryCredential);
	
	
	/**
	 * 对凭据信息进行加密和编码处理，加密和编码之后的一个加密凭据信息字符串。
	 * @param encryCredentialInfo 加密前的用于凭据信息。
	 * @return 加密后的用户字符串。
	 */
	public String encrypt(EncryCredentialInfo encryCredentialInfo);
	
	/**
	 * 检查用户凭据信息的合法性，凭据是否合法，凭据是否过期和有效等。
	 * @param encryCredentialInfo 用户凭据信息。 
	 * @return 凭据信息是否有效，true-表示有效，false-表示无效。
	 */
	public boolean checkEncryCredentialInfo(EncryCredentialInfo encryCredentialInfo);
	
}
