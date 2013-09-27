package com.github.ebnew.ki4so.core.service;

import java.util.ArrayList;
import java.util.List;

import com.github.ebnew.ki4so.core.app.App;
import com.github.ebnew.ki4so.core.app.AppService;
import com.github.ebnew.ki4so.core.authentication.Authentication;
import com.github.ebnew.ki4so.core.authentication.AuthenticationManager;
import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.status.UserLoggedStatus;
import com.github.ebnew.ki4so.core.authentication.status.UserLoggedStatusStore;
import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;

/**
 * @author Administrator
 * @version 1.0
 * @created 30-五月-2013 21:32:24
 */
public class Ki4soServiceImpl implements Ki4soService {

	private AuthenticationManager authenticationManager;
	
	private UserLoggedStatusStore userLoggedStatusStore;
	
	private AppService appService;

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public void setUserLoggedStatusStore(UserLoggedStatusStore userLoggedStatusStore) {
		this.userLoggedStatusStore = userLoggedStatusStore;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public Ki4soServiceImpl(){}

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

	@Override
	public void logout(Credential credential) {
		try{
			if(credential==null){
				return;
			}
			//对凭据做一次认证。
			Authentication authentication = authenticationManager.authenticate(credential);
			//清除用户登录状态。
			if(authentication!=null && authentication.getPrincipal()!=null){
				this.userLoggedStatusStore.clearUpUserLoggedStatus(authentication.getPrincipal().getId());
			}
		}catch (InvalidCredentialException e) {
		}
	}

	@Override
	public List<App> getAppList(Credential credential){
		List<App> apps = new ArrayList<App>();
		if(credential==null){
			return apps;
		}
		try{
			//对凭据做一次认证。
			Authentication authentication = authenticationManager.authenticate(credential);
			if(authentication!=null && authentication.getPrincipal()!=null){
				List<UserLoggedStatus> list = this.userLoggedStatusStore.findUserLoggedStatus(authentication.getPrincipal().getId());
				//批量查询对应的应用信息。
				if(list!=null&& list.size()>0){
					for(UserLoggedStatus status:list){
						App app = appService.findAppById(status.getAppId());
						if(app!=null){
							apps.add(app);
						}
					}
				}
			}
			
		}catch (InvalidCredentialException e) {
		}
		return apps;
	}

}