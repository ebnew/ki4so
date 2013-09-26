package com.github.ebnew.ki4so.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * 与秘钥相关的web请求处理类，处理查询应用的秘钥等信息。
 * @author burgess yang
 *
 */
@Controller
public class KeyAction {

	/**
	 * 秘钥服务。
	 */
	@Autowired
	private KeyService keyService;
	
	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	/**
	 * 根据应用ID，查询对应的秘钥信息，默认的实现是不加密的，未实现认证，
	 * 请自行增加该服务的安全性。
	 * @param appId 应用ID.
	 * @return 对应的秘钥。
	 */
	@RequestMapping("/fetchKey")
	@ResponseBody
	public Ki4soKey fetchKey(String appId){
		return keyService.findKeyByAppId(appId);
	}
}
