package com.github.ebnew.ki4so.core.key;

import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 加密用户凭据信息的解析器，负责加密和解密用户的加密凭据信息。
 * @author Administrator
 *
 */
public interface EncryCredentialResolver {
	
	/**
	 * 对编码的凭据信息进行解码，解码后为
	 * 一个凭据对象。
	 * @param encryCredential 加密后的凭据信息。
	 * @param ki4soKey 解密的秘钥信息。
	 * @return 解密和解码后的凭据信息。 
	 */
	public EncryCredentialInfo decrypt(EncryCredential encryCredential, Ki4soKey ki4soKey);
	
	
	/**
	 * 对凭据信息进行加密和编码处理。
	 * @param encryCredentialInfo 要加密的原始信息。
	 * @param ki4soKey 加密的秘钥信息。
	 * @return 加密和编码后的凭据信息。
	 */
	public String encrypt(EncryCredentialInfo encryCredentialInfo, Ki4soKey ki4soKey);
	

}
