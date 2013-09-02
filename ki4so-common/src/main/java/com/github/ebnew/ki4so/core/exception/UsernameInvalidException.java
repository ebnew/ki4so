package com.github.ebnew.ki4so.core.exception;

/**
 * 用户名不合法的异常类。
 * @author burgess yang
 *
 */
public class UsernameInvalidException extends InvalidCredentialException{
	
	public static final UsernameInvalidException INSTANCE = new UsernameInvalidException();

	/**
	 * 
	 */
	private static final long serialVersionUID = -4250926514996058588L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "USERNAME.INVALID.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "USERNAME.INVALID.MSG";
	
	public UsernameInvalidException() {
		super(CODE, MSG_KEY);
	}
}
