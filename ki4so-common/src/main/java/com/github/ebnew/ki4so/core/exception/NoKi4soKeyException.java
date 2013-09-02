package com.github.ebnew.ki4so.core.exception;

public class NoKi4soKeyException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 115935182258831210L;

	public static final NoKi4soKeyException INSTANCE = new NoKi4soKeyException();
	
	/**
	 * 异常代码值。
	 */
	public static final String CODE = "NO.KI4SO.KEY.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "NO.KI4SO.KEY.MSG";
	
	public NoKi4soKeyException(String code, String msgKey) {
		super(code, msgKey);
	}
	
	public NoKi4soKeyException() {
		super(CODE, MSG_KEY);
	}

}
