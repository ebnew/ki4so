package com.github.ebnew.ki4so.core.exception;

/**
 * 不合法的已认证凭据异常信息类
 * @author Administrator
 *
 */
public class InvalidEncryCredential extends InvalidCredentialException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7019558865450982143L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "INVALID.ENC.CREDENTIAL.CODE";
	
	/**
	 * 异常代码值。
	 */
	public static final String MESSAGE = "INVALID.ENC.CREDENTIAL.MSG";
	
	
	public InvalidEncryCredential() {
		super(CODE, MESSAGE);
	}

}
