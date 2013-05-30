package com.github.ebnew.ki4so.core.authentication;

import java.util.Date;
import java.util.Map;

/**
 * 认证结果
 * @author Administrator
 * @version 1.0
 */
public interface Authentication {

	public Map<String, Object> getAttributes();

	public Date getAuthenticatedDate();

	public Principal getPrincipal();

}