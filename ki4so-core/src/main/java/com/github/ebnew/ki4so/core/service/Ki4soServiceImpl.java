package com.github.ebnew.ki4so.core.service;

import java.util.List;
import java.util.Map;

import com.github.ebnew.ki4so.core.authentication.Authentication;
import com.github.ebnew.ki4so.core.authentication.AuthenticationManager;
import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;

/**
 * @author Administrator
 * @version 1.0
 * @created 30-五月-2013 21:32:24
 */
public class Ki4soServiceImpl implements Ki4soService {

	private AuthenticationManager authenticationManager;

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public Ki4soServiceImpl(){}


	public void getKey(){

	}

	/**
	 * 实现登录逻辑。
	 */
	@Override
	public LoginResult login(Credential credential){
		//若没有凭据，则返回空。
		if(credential==null){
			return null;
		}
		LoginResult loginResult = new LoginResult();
		loginResult.setSuccess(false);
		//调用认证处理器进行认证。
		try{
			Authentication authentication = authenticationManager.authenticate(credential);
			//登录成功。
			loginResult.setSuccess(true);
			loginResult.setAuthentication(authentication);
		}catch (InvalidCredentialException e) {
			//登录失败。
			loginResult.setSuccess(false);
			//设置异常代码和异常信息键值。
			loginResult.setCode(e.getCode());
			loginResult.setMsgKey(e.getMsgKey());
		}
		return loginResult;
	}

	public void logout(){

	}

	@Override
	public void logout(Credential credential) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> getAppList(Credential credential) {
		// TODO Auto-generated method stub
		return null;
	}

}