package com.github.ebnew.ki4so.core.exception;


/**
 * 基础异常类，定义了根级异常类。
 * @author burgess yang.
 *
 */
public abstract class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1783421378934140252L;
	
	/**
	 * 异常代号。
	 */
	private String code;
	
	/**
	 * 异常详细信息键值，根据该键值查找某种语言下的具体值。
	 */
	private String meessage;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMeessage() {
		return meessage;
	}

	public void setMeessage(String meessage) {
		this.meessage = meessage;
	}

	public BaseException(String code, String meessage) {
		super();
		this.code = code;
		this.meessage = meessage;
	}
	
	
	
	
}
