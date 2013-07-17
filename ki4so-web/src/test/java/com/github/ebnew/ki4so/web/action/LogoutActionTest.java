package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.Cookie;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * 登出测试类。
 * @author burgess yang
 *
 */
public class LogoutActionTest {
	
	@Autowired
	private LogoutAction logoutAction;

	@Before
	public void setUp() throws Exception {
		logoutAction = new LogoutAction();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogout() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		//测试没有cookie的情况。
		logoutAction.logout(request, response);
		Assert.assertEquals(0, response.getCookies().length);
		
		//测试存在cookie，登出后要清除cookie值。
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		request.setCookies(new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, "dddsd"));
		logoutAction.logout(request, response);
		Assert.assertEquals(1, response.getCookies().length);
		Assert.assertEquals(0, response.getCookies()[0].getMaxAge());
	}

}
