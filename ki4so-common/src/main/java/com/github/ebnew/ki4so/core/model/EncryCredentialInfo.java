package com.github.ebnew.ki4so.core.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 加密凭据信息。
 * @author burgess yang
 *
 */
public class EncryCredentialInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4244473629542337749L;
	
	/**
	 * 应用唯一标识.
	 */
	private String appId;
	
	/**
	 * 用户唯一标识.
	 */
	private String userId;
	
	/**
	 * 密钥的唯一标识
	 */
	private String keyId;
	
	/**
	 * 加密凭据的创建时间。
	 */
	private Date createTime;
	
	/**
	 * 加密凭据的失效时间。
	 */
	private Date expiredTime;
	
	/**
	 * 加密凭据的盐值。
	 */
	private String salt;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
