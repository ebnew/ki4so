package com.github.ebnew.ki4so.web.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
		 * 测试输入null的情况。
		 */
		Assert.assertNotNull(loginResultToView.loginResultToView(new ModelAndView(), null, null, null));
		
		/**
		 * 测试输入null的情况。
		 */
		Assert.assertNotNull(loginResultToView.loginResultToView(new ModelAndView(), new LoginResult(), null, null));
		
		/**
		 * 测试输入null的情况。
		 */
		Assert.assertNotNull(loginResultToView.loginResultToView(new ModelAndView(), new LoginResult(), new MockHttpServletRequest(), null));
		
		
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
		 * 测试登录成功的情况，不存在service参数的情况。
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
		
		

		/**
		 * 测试登录成功的情况，结果中存在service参数，则应该跳转到该地址去。
		 */
		mv = new ModelAndView();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		result = new LoginResult();
		result.setSuccess(true);
		authentication = new AuthenticationImpl();
		attributes = new HashMap<String, Object>();
		authentication.setAttributes(attributes);
		attributes.put(AuthenticationPostHandler.KI4SO_SERVER_EC_KEY, "ki4so-sever-key");
		attributes.put(AuthenticationPostHandler.KI4SO_CLIENT_EC_KEY, "ki4so-client-key");
		attributes.put(WebConstants.SERVICE_PARAM_NAME, "htpp://localhost:8080/space-web/hello.jsp");
		result.setAuthentication(authentication);
		mvResult = loginResultToView.loginResultToView(mv, result, request, response);
		//检查输出结果。
		Assert.assertNotNull(mvResult);
		Assert.assertNull(request.getSession().getAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION));
		Assert.assertTrue(mvResult.getView() instanceof RedirectView);
		RedirectView view = (RedirectView)mvResult.getView();
		Assert.assertEquals("htpp://localhost:8080/space-web/hello.jsp?"+WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY+"=ki4so-client-key",view.getUrl());
		Assert.assertEquals(authentication, mvResult.getModel().get("authentication"));
		Assert.assertEquals("ki4so-sever-key", response.getCookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY).getValue());
		
		
		/**
		 * 测试登录成功的情况，结果中存在service参数，且URL地址中是带"?"的。
		 */
		mv = new ModelAndView();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		result = new LoginResult();
		result.setSuccess(true);
		authentication = new AuthenticationImpl();
		attributes = new HashMap<String, Object>();
		authentication.setAttributes(attributes);
		attributes.put(AuthenticationPostHandler.KI4SO_SERVER_EC_KEY, "ki4so-sever-key");
		attributes.put(AuthenticationPostHandler.KI4SO_CLIENT_EC_KEY, "ki4so-client-key");
		attributes.put(WebConstants.SERVICE_PARAM_NAME, "htpp://localhost:8080/space-web/hello.jsp?hello=word");
		result.setAuthentication(authentication);
		mvResult = loginResultToView.loginResultToView(mv, result, request, response);
		//检查输出结果。
		Assert.assertNotNull(mvResult);
		Assert.assertNull(request.getSession().getAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION));
		Assert.assertTrue(mvResult.getView() instanceof RedirectView);
		view = (RedirectView)mvResult.getView();
		Assert.assertEquals("htpp://localhost:8080/space-web/hello.jsp?hello=word&"+WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY+"=ki4so-client-key",view.getUrl());
		Assert.assertEquals(authentication, mvResult.getModel().get("authentication"));
		Assert.assertEquals("ki4so-sever-key", response.getCookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY).getValue());
	}
	
	
}
