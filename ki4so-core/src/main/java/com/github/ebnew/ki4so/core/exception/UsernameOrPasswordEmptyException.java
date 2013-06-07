package com.github.ebnew.ki4so.core.exception;

/**
 * 用户名或者密码为空的异常类
 * @author burgess yang
 *
 */
public class UsernameOrPasswordEmptyException extends InvalidCredentialException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5781748461802851385L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "USERNAME.OR.PWD.EMPTY.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "USERNAME.OR.PWD.EMPTY.MSG";
	
	
	public UsernameOrPasswordEmptyException() {
		super(CODE, MSG_KEY);
	}
}
