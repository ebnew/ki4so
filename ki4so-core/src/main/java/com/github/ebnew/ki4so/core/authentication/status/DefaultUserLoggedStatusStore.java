package com.github.ebnew.ki4so.core.authentication.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

public class DefaultUserLoggedStatusStore implements UserLoggedStatusStore {
	
	/**
	 * 用户登录状态集合，不允许重复状态值。
	 */
	private Set<UserLoggedStatus> loggedStatus = new HashSet<UserLoggedStatus>();

	public Set<UserLoggedStatus> getLoggedStatus() {
		return loggedStatus;
	}


	/**
	 * 用户标识和用户状态列表之间的映射表，相当于一个索引，方便根据用户标识查询所有的登录状态标。
	 * 其中map中的key是用户标识，value是该用户所有的登录状态列表。 
	 */
	private Map<String, List<UserLoggedStatus>> userIdIndexMap = new HashMap<String, List<UserLoggedStatus>>();
	
	public Map<String, List<UserLoggedStatus>> getUserIdIndexMap() {
		return userIdIndexMap;
	}

	@Override
	public synchronized void addUserLoggedStatus(UserLoggedStatus userLoggedStatus) {
		//检查数据的合法性。
		if(userLoggedStatus!=null 
				&& !StringUtils.isEmpty(userLoggedStatus.getAppId())
				&& !StringUtils.isEmpty(userLoggedStatus.getUserId())
				){
			//避免数据为空。
			if(userLoggedStatus.getLoggedDate()==null){
				userLoggedStatus.setLoggedDate(new Date());
			}
			this.loggedStatus.add(userLoggedStatus);
			List<UserLoggedStatus> list = this.userIdIndexMap.get(userLoggedStatus.getUserId());
			if(list==null){
				list = new ArrayList<UserLoggedStatus>();
				this.userIdIndexMap.put(userLoggedStatus.getUserId(), list);
			}
			list.add(userLoggedStatus);
		}
	}

	@Override
	public synchronized void deleteUserLoggedStatus(String userId, String appId) {
		//检查数据的合法性。
		if(!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(appId)){
			UserLoggedStatus status = new UserLoggedStatus(userId, appId);
			this.loggedStatus.remove(status);
			List<UserLoggedStatus> list = this.userIdIndexMap.get(userId);
			if(list!=null){
				list.remove(status);
			}
		}
	}
	
	@Override
	public synchronized void clearUpUserLoggedStatus(String userId) {
		if(!StringUtils.isEmpty(userId)){
			List<UserLoggedStatus> list = this.userIdIndexMap.get(userId);
			if(list!=null){
				list.clear();
				this.userIdIndexMap.put(userId, null);
			}
		}
	}


	@Override
	public List<UserLoggedStatus> findUserLoggedStatus(String userId) {
		if(!StringUtils.isEmpty(userId)){
			return this.userIdIndexMap.get(userId);
		}
		return null;
	}

	

}
