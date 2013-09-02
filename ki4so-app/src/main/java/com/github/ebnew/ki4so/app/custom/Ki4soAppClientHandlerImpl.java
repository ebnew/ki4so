package com.github.ebnew.ki4so.app.custom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.ebnew.ki4so.client.handler.AppClientLoginHandler;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 默认的登录认证实现。
 * @author burgess yang
 *
 */
public class Ki4soAppClientHandlerImpl implements AppClientLoginHandler {

	public static final String USER_KEY = "USER_KEY_SESSON";
	
	@Override
	public void loginClient(EncryCredentialInfo encryCredentialInfo, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(USER_KEY, encryCredentialInfo);
		System.out.println("the user id is "+encryCredentialInfo.getUserId() +" has logined the app");
	}

}
