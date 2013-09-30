package com.github.ebnew.ki4so.core.exception;

/**
 * 不合法的已认证凭据异常信息类
 * @author Administrator
 *
 */
public class InvalidEncryCredentialException extends InvalidCredentialException {
	
	public static final InvalidEncryCredentialException INSTANCE = new InvalidEncryCredentialException();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7019558865450982143L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "INVALID.ENC.CREDENTIAL.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "INVALID.ENC.CREDENTIAL.MSG";
	
	
	public InvalidEncryCredentialException() {
		super(CODE, MSG_KEY);
	}

}
