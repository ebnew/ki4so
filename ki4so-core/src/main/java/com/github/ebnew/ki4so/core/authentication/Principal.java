package com.github.ebnew.ki4so.core.authentication;

import java.util.Map;

/**
 * 用户主体，代表一个用户
 * @author Administrator
 * @version 1.0
 * @updated 30-五月-2013 21:33:18
 */
public interface Principal {

	public Map<String, Object> getAttributes();

	public String getId();

}