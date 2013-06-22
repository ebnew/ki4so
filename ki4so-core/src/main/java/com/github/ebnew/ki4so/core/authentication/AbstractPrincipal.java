package com.github.ebnew.ki4so.core.authentication;

import java.util.Map;

/**
 * 抽象的用户主体实现类。
 * @author Administrator
 *
 */
public abstract class AbstractPrincipal implements Principal{
	
	/**
	 * 用户主体的唯一标记。
	 */
	protected String id;
	
	/**
	 * 用户主体的其它属性表。
	 */
	protected Map<String, Object> attributes;
	
	public AbstractPrincipal() {
		super();
	}

	public AbstractPrincipal(String id, Map<String, Object> attributes) {
		super();
		this.id = id;
		this.attributes = attributes;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
}
