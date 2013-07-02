package com.github.ebnew.ki4so.core.exception;

public class EmptyCredentialException extends InvalidCredentialException {
	
	public static final EmptyCredentialException INSTANCE = new EmptyCredentialException();

	/**
	 * 
	 */
	private static final long serialVersionUID = -1075351110429734216L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "EMPTY.CREDENTIAL.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "EMPTY.CREDENTIAL.MSG";
	
	
	public EmptyCredentialException() {
		super(CODE, MSG_KEY);
	}
}
