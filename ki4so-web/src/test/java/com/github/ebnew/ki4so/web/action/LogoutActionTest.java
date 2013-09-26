package com.github.ebnew.ki4so.web.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.ebnew.ki4so.core.app.App;
import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.service.Ki4soService;
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
	public void testGetAppList() throws UnsupportedEncodingException {
		//测试准备。
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		CredentialResolver credentialResolver = Mockito.mock(CredentialResolver.class);
		logoutAction.setCredentialResolver(credentialResolver);
		
		Ki4soService ki4soService = Mockito.mock(Ki4soService.class);
		logoutAction.setKi4soService(ki4soService);
		
		Credential credential = Mockito.mock(Credential.class);
		
		//设置预期结果。
		Mockito.when(credentialResolver.resolveCredential(request)).thenReturn(credential);
		List<App> list = new ArrayList<App>();
		App app = new App();
		app.setAppId("1000");
		app.setAppName("测试应用程序1");
		app.setHost("app.com");
		app.setLogoutUrl("http://app.com/logout.do");
		list.add(app);
		Mockito.when(ki4soService.getAppList(credential)).thenReturn(list);
		
		//执行查询。
		logoutAction.getAppList(request, response);
		
		//检查结果。
		Assert.assertEquals("application/x-javascript", response.getContentType());
		Assert.assertEquals("UTF-8", response.getCharacterEncoding());
		//检查输出的jsonp串是否正确。
		String content = response.getContentAsString();
		content = content.trim();
		Assert.assertTrue(content.startsWith("fetchAppList("));
		Assert.assertTrue(content.endsWith(");"));
		String json = content.replaceFirst("fetchAppList\\(", "");
		json = json.replaceFirst("\\);", "");
		JSONArray data = (JSONArray)JSON.parse(json);
		Assert.assertEquals(1, data.size());
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)data.get(0);
		Assert.assertEquals("1000", map.get("appId"));
		
	}
	

	@Test
	public void testLogout() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		
		CredentialResolver credentialResolver = Mockito.mock(CredentialResolver.class);
		logoutAction.setCredentialResolver(credentialResolver);
		
		Ki4soService ki4soService = Mockito.mock(Ki4soService.class);
		logoutAction.setKi4soService(ki4soService);
		
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
