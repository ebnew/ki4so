package com.github.ebnew.ki4so.core.authentication;

import java.util.Map;

/**
 * 参数，定义了获得动态参数列表的接口。
 * @author burgess yang
 *
 */
public interface Parameter {
	
	/**
	 * 通过参数名获得参数值的方法。
	 * @param paramName 参数名。
	 * @return 对应的参数值。
	 */
	public Object getParameterValue(String paramName);
	
	
	/**
	 * 获得所有的参数表。
	 * @return 所有的参数列表。
	 */
	public Map<String, Object> getParameters();
	
	/**
	 * 设置参数列表。
	 * @param parameters 要设置的参数表。
	 */
	public void setParameters(Map<String, Object> parameters);

}
