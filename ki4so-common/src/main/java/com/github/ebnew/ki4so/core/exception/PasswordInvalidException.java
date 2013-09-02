package com.github.ebnew.ki4so.core.exception;

/**
 * 密码不合法的异常类
 * @author burgess yang
 *
 */
public class PasswordInvalidException extends InvalidCredentialException {
	
	public static final PasswordInvalidException INSTANCE = new PasswordInvalidException();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4250926514996058588L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "PASSWORD.INVALID.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "PASSWORD.INVALID.MSG";
	
	public PasswordInvalidException() {
		super(CODE, MSG_KEY);
	}
}
