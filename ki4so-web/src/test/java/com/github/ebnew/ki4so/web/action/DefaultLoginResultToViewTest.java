package com.github.ebnew.ki4so.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.authentication.AuthenticationImpl;
import com.github.ebnew.ki4so.core.authentication.AuthenticationPostHandler;
import com.github.ebnew.ki4so.core.exception.InvalidCredentialException;
import com.github.ebnew.ki4so.core.message.MessageUtils;
import com.github.ebnew.ki4so.core.service.LoginResult;
import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * 对类DefaultLoginResultToView的单元测试类。
 * @author burgess yang
 *
 */
public class DefaultLoginResultToViewTest {

	private DefaultLoginResultToView loginResultToView = new DefaultLoginResultToView();
	
	/**
	 * 测试被测类对应的方法。
	 */
	@Test
	public void testLoginResultToView(){
		/**
		 * 测试输入null的情况。
		 */
		Assert.assertNotNull(loginResultToView.loginResultToView(null, null, null, null));
		
		/**
		 * 测试登录失败的情况。
		 */
		//准备测试输入数据。
		ModelAndView mv = new ModelAndView();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		LoginResult result = new LoginResult();
		result.setSuccess(false);
		result.setCode(InvalidCredentialException.CODE);
		result.setMsgKey(InvalidCredentialException.MSG_KEY);
		
		request.setCookies(new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, "test"));
		ModelAndView mvResult = loginResultToView.loginResultToView(mv, result, request, response);
		//检查输出结果。
		Assert.assertNotNull(mvResult);
		Assert.assertEquals(InvalidCredentialException.CODE, mvResult.getModel().get("code"));
		Assert.assertEquals(MessageUtils.getMessage(InvalidCredentialException.MSG_KEY), mvResult.getModel().get("msg"));
		Assert.assertEquals("test", response.getCookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY).getValue());
		
		
		/**
		 * 测试登录成功的情况。
		 */
		//准备测试输入数据。
		mv = new ModelAndView();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		result = new LoginResult();
		request.getSession().setAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION, "service");
		result.setSuccess(true);
		AuthenticationImpl authentication = new AuthenticationImpl();
		Map<String, Object> attributes = new HashMap<String, Object>();
		authentication.setAttributes(attributes);
		attributes.put(AuthenticationPostHandler.KI4SO_SERVER_EC_KEY, "ki4so-sever-key");
		result.setAuthentication(authentication);
		mvResult = loginResultToView.loginResultToView(mv, result, request, response);
		//检查输出结果。
		Assert.assertNotNull(mvResult);
		Assert.assertNull(request.getSession().getAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION));
		Assert.assertEquals("loginSucess", mvResult.getViewName());
		Assert.assertEquals(authentication, mvResult.getModel().get("authentication"));
		Assert.assertEquals("ki4so-sever-key", response.getCookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY).getValue());
	}
	
	
}
