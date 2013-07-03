package com.github.ebnew.ki4so.core.authentication;

import java.util.Map;

/**
 * 抽象的参数化实现类。
 * @author burgess yang
 *
 */
public abstract class AbstractParameter implements Parameter{
	
	/**
	 * 其它参数表。
	 */
	protected Map<String, Object> paramters;
	
	@Override
	public Object getParameterValue(String paramName) {
		return this.paramters==null?null:this.paramters.get(paramName);
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.paramters;
	}

	@Override
	public void setParameters(Map<String, Object> parameters) {
		this.paramters = parameters;
	}

}
