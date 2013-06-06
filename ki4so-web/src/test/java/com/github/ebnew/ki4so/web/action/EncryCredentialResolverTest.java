package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.Cookie;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.web.utils.WebConstants;

public class EncryCredentialResolverTest {

	/**
	 * 被测对象。
	 */
	private EncryCredentialResolver resolver;
	
	@Before
	public void setUp() throws Exception {
		resolver = new EncryCredentialResolver();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testResolveCredential(){
		String cookie = "VS032SDAFDAFD";
	
		//测试传入null的情况。
		Assert.assertNull(resolver.resolveCredential(null));
		
		//测试不存在cookie的情况。
		MockHttpServletRequest request = new MockHttpServletRequest();
		Assert.assertNull(resolver.resolveCredential(request));
	
		//测试存在cookie，但是cookie中值是的元素为null的情况。
		request = new MockHttpServletRequest();
		request.setCookies(null, null, null, null);
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试存在cookie数组，但是有2个不为null的情况。
		request = new MockHttpServletRequest();
		request.setCookies(new Cookie("C1", null), null, null, new Cookie("C2", "dafdafdada"));
		Assert.assertNull(resolver.resolveCredential(request));
		
		
		//测试存在cookie数组，但是有1个不为null的情况，且cookie名称为KI4SO服务端写入的cookie值,但是cookie的值为Null.
		request = new MockHttpServletRequest();
		request.setCookies(new Cookie("C1", null), null, null, new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, null));
		Assert.assertNull(resolver.resolveCredential(request));
		
		
		//测试存在cookie数组，但是有1个不为null的情况，且cookie名称为KI4SO服务端写入的cookie值,但是cookie的值不是Null.
		request = new MockHttpServletRequest();
		request.setCookies(new Cookie("C1", null), null, null, new Cookie(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY, cookie));
		EncryCredential credential = (EncryCredential)resolver.resolveCredential(request);
		Assert.assertNotNull(credential);
		Assert.assertEquals(cookie, credential.getCredential());
	}
}
