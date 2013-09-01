package com.github.ebnew.ki4so.core.authentication.handlers;

import java.util.Date;

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
		if(credential!=null && credential instanceof EncryCredential){
			EncryCredential encryCredential = (EncryCredential)credential;
			try{
				EncryCredentialInfo encryCredentialInfo = this.encryCredentialManager.decrypt(encryCredential);
				if(encryCredentialInfo!=null){
					encryCredential.setEncryCredentialInfo(encryCredentialInfo);
					Date now = new Date();
					if(encryCredentialInfo.getExpiredTime()!=null){
						//比较过期时间与当前时间。
						long bet = now.getTime()-encryCredentialInfo.getExpiredTime().getTime();
						//若凭据未过期，则直接返true.
						if(bet>0){
							return true;
						}
					}
				}
			}catch (InvalidEncryCredentialException e) {
				return false;
			}
		}
		return false;
	}

}
