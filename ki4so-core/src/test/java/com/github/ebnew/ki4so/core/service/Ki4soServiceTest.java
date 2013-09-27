package com.github.ebnew.ki4so.core.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.core.app.App;
import com.github.ebnew.ki4so.core.app.AppService;
import com.github.ebnew.ki4so.core.authentication.Authentication;
import com.github.ebnew.ki4so.core.authentication.AuthenticationManager;
import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.Principal;
import com.github.ebnew.ki4so.core.authentication.status.UserLoggedStatus;
import com.github.ebnew.ki4so.core.authentication.status.UserLoggedStatusStore;
import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;

public class Ki4soServiceTest {
	
	private Ki4soServiceImpl ki4soService;
	
	@Before
	public void setUp() throws Exception {
		ki4soService = new Ki4soServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
		ki4soService = null;
	}
	
	@Test
	public void testLogin(){
		//测试输入Null的情况。
		Assert.assertNull(ki4soService.login(null));
		
		
		//测试认证失败的情况。
		Credential credential = Mockito.mock(Credential.class);
		//当调用认证方法则抛出异常信息。模拟测试数据。
		AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
		this.ki4soService.setAuthenticationManager(authenticationManager);
		InvalidCredentialException exception = Mockito.mock(InvalidCredentialException.class);
		String code = "message code";
		String msgKey ="message key";
		Mockito.when(exception.getCode()).thenReturn(code);
		Mockito.when(exception.getMsgKey()).thenReturn(msgKey);
		Mockito.when(authenticationManager.authenticate(credential)).thenThrow(exception);
		LoginResult loginResult = ki4soService.login(credential);
		LoginResult expected = new LoginResult();
		expected.setSuccess(false);
		expected.setCode(code);
		expected.setMsgKey(msgKey);
		//比较结果。
		this.assertLoginResult(expected, loginResult);
		
		//测试认证成功。
		credential = Mockito.mock(Credential.class);
		//当调用认证方法则抛出异常信息。模拟测试数据。
		authenticationManager = Mockito.mock(AuthenticationManager.class);
		this.ki4soService.setAuthenticationManager(authenticationManager);
		
		
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(exception.getCode()).thenReturn(msgKey);
		Mockito.when(exception.getCode()).thenReturn(code);
		Mockito.when(authenticationManager.authenticate(credential)).thenReturn(authentication);
		loginResult = ki4soService.login(credential);
		expected = new LoginResult();
		expected.setSuccess(true);
		expected.setAuthentication(authentication);
		//比较结果。
		this.assertLoginResult(expected, loginResult);
	}
	
	/**
	 * 测试登出的情况。
	 */
	@Test
	public void testLogout(){
		//测试认证失败的情况。
		Credential credential = Mockito.mock(Credential.class);
		//当调用认证方法则抛出异常信息。模拟测试数据。
		AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
		UserLoggedStatusStore userLoggedStatusStore = Mockito.mock(UserLoggedStatusStore.class);
		
		this.ki4soService.setAuthenticationManager(authenticationManager);
		this.ki4soService.setUserLoggedStatusStore(userLoggedStatusStore);
		
		/**
		 * 测试为空过的情况。
		 */
		this.ki4soService.logout(null);
		
		/**
		 * 测试正常参数情况，但是验证失败。
		 */
		this.ki4soService.logout(credential);
		
		/**
		 * 测试正常参数情况，但是验证成功。
		 */
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authenticationManager.authenticate(credential)).thenReturn(authentication);
		this.ki4soService.logout(credential);
		
		/**
		 * 测试正常参数情况，但是验证成功。
		 */
		authentication = Mockito.mock(Authentication.class);
		Mockito.when(authentication.getPrincipal()).thenReturn(Mockito.mock(Principal.class));
		Mockito.when(authenticationManager.authenticate(credential)).thenReturn(authentication);
		this.ki4soService.logout(credential);
		
		
		/**
		 * 测试认证抛出异常情况。
		 */
		Mockito.when(authenticationManager.authenticate(credential)).thenThrow(Mockito.mock(InvalidCredentialException.class));
		this.ki4soService.logout(credential);
	}
	
	@Test
	public void testGetAppList(){
		
		//当调用认证方法则抛出异常信息。模拟测试数据。
		AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
		UserLoggedStatusStore userLoggedStatusStore = Mockito.mock(UserLoggedStatusStore.class);
		AppService appService = Mockito.mock(AppService.class);
		this.ki4soService.setAuthenticationManager(authenticationManager);
		this.ki4soService.setUserLoggedStatusStore(userLoggedStatusStore);
		this.ki4soService.setAppService(appService);
		
		/**
		 * 测试传递错误参数的情况。
		 */
		Assert.assertEquals(0, this.ki4soService.getAppList(null).size());
		
		/**
		 * 测试正确参数的情况。
		 */
		Credential credential = Mockito.mock(Credential.class);
		Assert.assertEquals(0, this.ki4soService.getAppList(credential).size());
		
		/**
		 * 测试正确参数，但是返回值的属principal为空的情况。
		 */
		credential = Mockito.mock(Credential.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authenticationManager.authenticate(credential)).thenReturn(authentication);
		Assert.assertEquals(0, this.ki4soService.getAppList(credential).size());
		
		
		/**
		 * 测试正确参数，但是返回值的属principal不是空的情况。
		 */
		credential = Mockito.mock(Credential.class);
		authentication = Mockito.mock(Authentication.class);
		Mockito.when(authenticationManager.authenticate(credential)).thenReturn(authentication);
		Mockito.when(authentication.getPrincipal()).thenReturn(Mockito.mock(Principal.class));
		List<UserLoggedStatus> list = new ArrayList<UserLoggedStatus>();
		UserLoggedStatus loggedStatus = new UserLoggedStatus("test", "1001");
		list.add(loggedStatus);
		Mockito.when(userLoggedStatusStore.findUserLoggedStatus(Mockito.anyString())).thenReturn(list);
		App app = new App();
		Mockito.when(appService.findAppById(Mockito.anyString())).thenReturn(app);
		Assert.assertEquals(1, this.ki4soService.getAppList(credential).size());
	}
	
	/**
	 * 比较两个登录结果对象。
	 * @param expected 预期结果。
	 * @param aucual 实际结果。
	 */
	private void assertLoginResult(LoginResult expected, LoginResult aucual){
		if(expected==null && aucual==null){
			return;
		}
		else if(expected != null && aucual!=null){
			Assert.assertEquals(expected.getCode(), aucual.getCode());
			Assert.assertEquals(expected.getMsgKey(), aucual.getMsgKey());
			Assert.assertEquals(expected.getAuthentication(), aucual.getAuthentication());
		}
		else{
			Assert.fail("预期和实际登录对象不等");
		}
	}
}
