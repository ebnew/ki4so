package com.github.ebnew.ki4so.core.app;

public interface AppService {
	
	/**
	 * 根据应用ID查询对应的应用信息。
	 * @param appId
	 * @return
	 */
	public App findAppById(String appId);

}
