package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.Cookie;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
import com.github.ebnew.ki4so.core.exception.PasswordInvalidException;
import com.github.ebnew.ki4so.core.exception.UsernameInvalidException;
import com.github.ebnew.ki4so.core.exception.UsernameOrPasswordEmptyException;
import com.github.ebnew.ki4so.core.message.MessageUtils;
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
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//测试模拟一个非法的已加密凭据值。
		Cookie cookie = new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, "a invalid KI4SO_SERVER_EC value");
		request.setCookies(cookie);
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		//检查错误代码。
		Assert.assertEquals(InvalidEncryCredentialException.CODE, mv.getModel().get("code"));
		//检查错误提示信息。
		Assert.assertEquals(MessageUtils.getMessage(InvalidEncryCredentialException.MSG_KEY), mv.getModel().get("msg"));
		
		
		
		/**
		 * 测试有已认证凭据的情况，且认证凭据合法的情况。
		 * 此时返回的地址重新定位到应用的入口地址，带上参数service和WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY
		 * 其中参数service表示用户想要访问的地址，WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY 表示应用中cookie
		 */
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//传入参数service表示用户要访问的受保护地址URL.
		request.setParameter("service", "http://test.com/user.jsp");
		//设置cookie中合法的已认证凭据。
		String encCredential = "a valid KI4SO_SERVER_EC value";
		cookie = new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, encCredential);
		request.setCookies(cookie);
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回的视图类型是重定向视图。
		Assert.assertEquals(RedirectView.class, mv.getView().getClass());
		//假设应用的登录入口地址是：http://test.com/loginAction.jsp
		Assert.assertEquals("http://test.com/loginAction.jsp?service=http://test.com/user.jsp&"+WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY+"KI4SO CLIENT ENCRYPTED CREDENTIAL COOKIE"
				, ((RedirectView)mv.getView()).getUrl());
		
		
		/**
		 * 测试有无认证凭据的情况，但是有未认证凭据的情况。
		 * 即存在用户名和密码参数的情况。但是传入的用户名或者密码是空的情况。
		 */
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//设置用户名和密码都是空。
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, "");
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, "");
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		//检查错误代码。
		Assert.assertEquals(UsernameOrPasswordEmptyException.CODE, mv.getModel().get("code"));
		//检查错误提示信息。
		Assert.assertEquals(MessageUtils.getMessage(UsernameOrPasswordEmptyException.MSG_KEY), mv.getModel().get("msg"));
		
		//设置用户名是空，密码不为空的情况。
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, "");
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, "123456");
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		//检查错误代码。
		Assert.assertEquals(UsernameOrPasswordEmptyException.CODE, mv.getModel().get("code"));
		//检查错误提示信息。
		Assert.assertEquals(MessageUtils.getMessage(UsernameOrPasswordEmptyException.MSG_KEY), mv.getModel().get("msg"));
		
		//设置用户名不是空，密码是空的情况。
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, "admin");
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, "");
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		//检查错误代码。
		Assert.assertEquals(UsernameOrPasswordEmptyException.CODE, mv.getModel().get("code"));
		//检查错误提示信息。
		Assert.assertEquals(MessageUtils.getMessage(UsernameOrPasswordEmptyException.MSG_KEY), mv.getModel().get("msg"));
		
		/**
		 * 测试有无认证凭据的情况，但是有未认证凭据的情况。
		 * 即存在用户名和密码参数的情况。但是传入的用户名或者密码不是空，但是用户名不存在的情况。
		 */
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//设置用户名和密码都是空。
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, "admin");
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, "123456");
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		//检查错误代码。
		Assert.assertEquals(UsernameInvalidException.CODE, mv.getModel().get("code"));
		//检查错误提示信息。
		Assert.assertEquals(MessageUtils.getMessage(UsernameInvalidException.MSG_KEY), mv.getModel().get("msg"));
	
	
		/**
		 * 测试有无认证凭据的情况，但是有未认证凭据的情况。
		 * 即存在用户名和密码参数的情况。但是传入的用户名或者密码不是空，但是密码不正确的情况。
		 */
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//设置用户名和密码都是空。
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, "admin");
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, "error");
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回登录页面，带有错误提示信息。
		Assert.assertEquals("login", mv.getViewName());
		//检查错误代码。
		Assert.assertEquals(PasswordInvalidException.CODE, mv.getModel().get("code"));
		//检查错误提示信息。
		Assert.assertEquals(MessageUtils.getMessage(PasswordInvalidException.MSG_KEY), mv.getModel().get("msg"));
	
		/**
		 * 测试有无认证凭据的情况，但是有未认证凭据的情况。
		 * 即存在用户名和密码参数的情况。但是传入的用户名或者密码不是空，验证通过的情况。
		 */
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		//设置用户名和密码都是空。
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, "admin");
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, "123456");
		mv = loginAction.login(request, response);
		Assert.assertNotNull(mv);
		//返回的视图类型是重定向视图。
		Assert.assertEquals(RedirectView.class, mv.getView().getClass());
		//假设应用的登录入口地址是：http://test.com/loginAction.jsp
		Assert.assertEquals("http://test.com/loginAction.jsp?service=http://test.com/user.jsp&"+WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY+"KI4SO CLIENT ENCRYPTED CREDENTIAL COOKIE"
				, ((RedirectView)mv.getView()).getUrl());
		//检查写入到response中的cookie值。
		Assert.assertNotNull(response.getCookies());
		Assert.assertNotNull(response.getCookies()[0]);
		Assert.assertEquals(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, response.getCookies()[0].getName());
		Assert.assertNotNull(response.getCookies()[0].getValue());
		
	}
	
}
