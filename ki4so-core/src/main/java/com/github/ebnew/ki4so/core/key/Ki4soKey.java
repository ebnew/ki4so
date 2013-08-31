package com.github.ebnew.ki4so.core.key;

import java.io.Serializable;
import java.security.Key;

import com.github.ebnew.ki4so.common.DESCoder;

public class Ki4soKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8673821624657924001L;
	
	
	/**
	 * 秘钥ID
	 */
	private String keyId;
	
	
	/**
	 * 应用ID
	 */
	private String appId;
	
	/**
	 * 秘钥值。
	 */
	private String value;

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * 将本对象转换为对应的key对象。
	 * @return Key对象。
	 * @throws Exception 
	 */
	public Key toSecurityKey() throws Exception{
		if(this.getValue()!=null){
			return DESCoder.initSecretKey(this.getValue());
		}
		return null;
	}

	@Override
	public String toString() {
		return "Ki4soKey [keyId=" + keyId + ", appId=" + appId + ", value="
				+ value + "]";
	}
}
