package com.github.ebnew.ki4so.app.custom;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.ebnew.ki4so.client.handler.AppClientLogoutHandler;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

public class Ki4soAppClientLogoutHandlerImpl implements AppClientLogoutHandler {
	
	private static Logger logger = Logger.getLogger(Ki4soAppClientLogoutHandlerImpl.class.getName());

	@Override
	public void logoutClient(HttpServletRequest request,
			HttpServletResponse response) {
		//若已经登录，则作相关处理。
		if(request.getSession().getAttribute(Ki4soAppClientLoginHandlerImpl.USER_KEY)!=null){
			EncryCredentialInfo encryCredentialInfo = (EncryCredentialInfo)request.getSession().getAttribute(Ki4soAppClientLoginHandlerImpl.USER_KEY);
			//remove the exception
			request.getSession().setAttribute(Ki4soAppClientLoginHandlerImpl.USER_KEY, null);
			logger.info("the user id is "+encryCredentialInfo.getUserId() +" has logined out the app");
		}
	}

}
