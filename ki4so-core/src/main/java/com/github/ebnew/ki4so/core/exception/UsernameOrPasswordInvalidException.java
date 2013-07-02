package com.github.ebnew.ki4so.core.exception;

/**
 * 用户名或者密码不合法的异常信息
 * @author burgess yang
 *
 */
public class UsernameOrPasswordInvalidException extends InvalidCredentialException {
	
	public static final UsernameOrPasswordInvalidException INSTANCE = new UsernameOrPasswordInvalidException();

	/**
	 * 
	 */
	private static final long serialVersionUID = -4250926514996058588L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "USERNAME.OR.PWD.INVALID.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "USERNAME.OR.PWD.INVALID.MSG";
	
	public UsernameOrPasswordInvalidException() {
		super(CODE, MSG_KEY);
	}

}
