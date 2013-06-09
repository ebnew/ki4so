package com.github.ebnew.ki4so.core.exception;

public class UnsupportedCredentialsException extends InvalidCredentialException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4250926514996058588L;

	/**
	 * 异常代码值。
	 */
	public static final String CODE = "UNSUPPORTED.CREDENTIALS.CODE";
	
	/**
	 * 异常信息键值，要转换为具体的语言值。
	 */
	public static final String MSG_KEY = "UNSUPPORTED.CREDENTIALS.MSG";
	
	public UnsupportedCredentialsException() {
		super(CODE, MSG_KEY);
	}
}
