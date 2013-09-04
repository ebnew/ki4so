package com.github.ebnew.ki4so.core.authentication.status;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录状态。
 * @author burgess yang
 *
 */
public class UserLoggedStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1189086442714197717L;
	
	/**
	 * 登录用户的标识。
	 */
	private String userId;
	
	/**
	 * 用户登录的应用标识。
	 */
	private String appId;
	
	/**
	 * 登录应用的时间。
	 */
	private Date loggedDate;
	

	public UserLoggedStatus(String userId, String appId) {
		super();
		this.userId = userId;
		this.appId = appId;
	}

	public UserLoggedStatus(String userId, String appId, Date loggedDate) {
		super();
		this.userId = userId;
		this.appId = appId;
		this.loggedDate = loggedDate;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getLoggedDate() {
		return loggedDate;
	}

	public void setLoggedDate(Date loggedDate) {
		this.loggedDate = loggedDate;
	}

	@Override
	public String toString() {
		return "UserLoggedStatus [userId=" + userId + ", appId=" + appId
				+ ", loggedDate=" + loggedDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserLoggedStatus other = (UserLoggedStatus) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}


}
