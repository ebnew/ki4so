package com.github.ebnew.ki4so.core.key;

import java.security.Key;

/**
 * 默认的key管理实现类，从classpath:/keys.js文件中
 * 读取key配置信息，是以json格式存储的。
 * @author Administrator
 *
 */
public class KeyServiceImpl implements KeyService {

	@Override
	public Key findKeyById(String keyId) {
		return null;
	}

}
