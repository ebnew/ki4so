package com.github.ebnew.ki4so.app.custom;

import com.github.ebnew.ki4so.client.handler.AppClientLoginHandler;

public class Ki4soAppClientHandlerImpl implements AppClientLoginHandler {

	@Override
	public void loginClient(String id) {
		System.out.println("the user id is"+id +" has logined the app");
	}

}
