package com.github.ebnew.ki4so.core.exception;

/**
 * 表示不合法的用户凭据异常信息类
 * @author Administrator
 *
 */
public class InvalidCredentialException extends AuthenticationException {
	
	public static final InvalidCredentialException INSTANCE = new InvalidCredentialException();
	
	/**
	 * 异常代码值。
	 */
	public static final String CODE = "INVALID.CREDENTIAL.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "INVALID.CREDENTIAL.MSG";
	
	public InvalidCredentialException(String code, String msgKey) {
		super(code, msgKey);
	}

	public InvalidCredentialException() {
		super(CODE, MSG_KEY);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3350784910105909683L;
	

}
