package com.github.ebnew.ki4so.core.exception;

public class NoAuthenticationPostHandlerException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8319434404552943653L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "NO.AUTH.HANDLER.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "NO.AUTH.HANDLER.MSG";
	
	public NoAuthenticationPostHandlerException() {
		super(CODE, MSG_KEY);
	}

}
