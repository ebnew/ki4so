package com.github.ebnew.ki4so.core.service;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.core.authentication.Authentication;
import com.github.ebnew.ki4so.core.authentication.AuthenticationManager;
import com.github.ebnew.ki4so.core.authentication.Credential;
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
