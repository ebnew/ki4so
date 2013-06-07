package com.github.ebnew.ki4so.core.message;

import java.util.HashMap;
import java.util.Map;

import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
import com.github.ebnew.ki4so.core.exception.PasswordInvalidException;
import com.github.ebnew.ki4so.core.exception.UsernameInvalidException;
import com.github.ebnew.ki4so.core.exception.UsernameOrPasswordEmptyException;

/**
 * 使用简单的国际化策略，只支持中文，以后可以改成更加灵活的
 * 能够支持多种语言的消息提示。
 * @author burgess yang
 *
 */
public class MessageUtils {
	
	/**
	 * 信息表，其中key是消息键，value是具体对应的中文的消息内容。
	 */
	private static Map<String, String> msgMap = null;
	
	static{
		//初始化消息表。
		msgMap = new HashMap<String, String>();
		msgMap.put(InvalidEncryCredentialException.MSG_KEY, "不合法的登录凭据");
		msgMap.put(UsernameOrPasswordEmptyException.MSG_KEY, "用户名或者密码为空");
		msgMap.put(UsernameInvalidException.MSG_KEY, "用户名不存在");
		msgMap.put(PasswordInvalidException.MSG_KEY, "密码错误");
		
	}
	
	
	/**
	 * 查询消息键对应的消息提示内容。
	 * @param key 消息键
	 * @return 消息内容。
	 */
	public static String getMessage(String key){
		return msgMap.get(key);
	}
}
