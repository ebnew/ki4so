package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.Cookie;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.web.utils.WebConstants;

public class LoginActionTest {

	/**
	 * 被测对象。
	 */
	private LoginAction loginAction;
	
	@Before
	public void setUp() throws Exception {
		loginAction = new LoginAction();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 测试登录方法。
	 */
	@Test
	public void testLogin() {
		//构造模拟请求和响应对象。
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		/**
		 * 测试无任何认证凭据的情况。
		 */
		ModelAndView mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//直接返回登录页面。
		Assert.assertEquals("login", mv.getViewName());
		
		
		/**
		 * 测试有已认证凭据的情况，已认证凭据不合法的情况。
		 * 则返回登录页面让用户重新输入凭据。
		 */
		
		//测试模拟一个非法的已加密凭据值。
		Cookie cookie = new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, "a invalid KI4SO_SERVER_EC value");
		request.setCookies(cookie);
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		
		/**
		 * 测试有已认证凭据的情况，认证凭据合法的情况。
		 * 此时返回的地址重新定位到应用登录成功后的入口地址，同时会带上参数KI4SO-EC。
		 */
	}

}
