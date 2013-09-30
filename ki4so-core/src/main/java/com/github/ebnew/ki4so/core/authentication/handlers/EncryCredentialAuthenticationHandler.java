package com.github.ebnew.ki4so.core.authentication.handlers;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.authentication.EncryCredentialManager;
import com.github.ebnew.ki4so.core.exception.AuthenticationException;
import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 认证后的凭据认证处理器实现类，需要验证认证后的凭据是否有效，凭据是否过期等等其它
 * 合法性验证。
 * @author burgess yang
 *
 */
public class EncryCredentialAuthenticationHandler extends
		AbstractPreAndPostProcessingAuthenticationHandler {
	
	@Autowired
	private EncryCredentialManager encryCredentialManager;
	
	public void setEncryCredentialManager(
			EncryCredentialManager encryCredentialManager) {
		this.encryCredentialManager = encryCredentialManager;
	}

	/** Default class to support if one is not supplied. */
	private static final Class<EncryCredential> DEFAULT_CLASS = EncryCredential.class;

	/**
	 * @return true if the credentials are not null and the credentials class is
	 *         equal to the class defined in classToSupport.
	 */
	public final boolean supports(final Credential credential) {
		return credential != null
				&& (DEFAULT_CLASS.equals(credential.getClass()) || (DEFAULT_CLASS
						.isAssignableFrom(credential.getClass())));
	}

	@Override
	protected boolean doAuthentication(Credential credential)
			throws AuthenticationException {
		//不支持的凭据直接返回false.
		if(!this.supports(credential)){
			return false;
		}
		if(credential!=null && credential instanceof EncryCredential){
			EncryCredential encryCredential = (EncryCredential)credential;
			try{
				//解密凭据信息。
				EncryCredentialInfo encryCredentialInfo = this.encryCredentialManager.decrypt(encryCredential);
				//设置凭据信息的关联性。
				if(encryCredentialInfo!=null){
					encryCredential.setEncryCredentialInfo(encryCredentialInfo);
					//检查加密凭据的合法性。
					return this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo);
				}
			}catch (InvalidEncryCredentialException e) {
				return false;
			}
		}
		return false;
	}

}
