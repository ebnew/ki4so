package com.github.ebnew.ki4so.web.action;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.UsernamePasswordCredential;

public class UsernamePasswordCredentialResolverTest {
	
	/**
	 * 被测对象。
	 */
	private UsernamePasswordCredentialResolver resolver;
	
	@Before
	public void setUp() throws Exception {
		resolver = new UsernamePasswordCredentialResolver();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testResolveCredential(){
		String username = "admin";
		String password = "sssss";
		
		//测试传入null的情况。
		Assert.assertNull(resolver.resolveCredential(null));
		//测试两个参数全部为null的情况。
		MockHttpServletRequest request = new MockHttpServletRequest();
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试用户名为null，密码不为null的情况。
		request = new MockHttpServletRequest();
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, password);
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试用户名不为null，密码为null的情况。
		request = new MockHttpServletRequest();
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, username);
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试测试用户名密码都不为null的情况。
		request = new MockHttpServletRequest();
		request.setParameter(UsernamePasswordCredentialResolver.USERNAME_PARAM_NAME, username);
		request.setParameter(UsernamePasswordCredentialResolver.PASSWORD_PARAM_NAME, password);
		UsernamePasswordCredential credential = (UsernamePasswordCredential)resolver.resolveCredential(request);
		Assert.assertNotNull(credential);
		Assert.assertEquals(username, credential.getUsername());
		Assert.assertEquals(password, credential.getPassword());
		
		Assert.assertEquals(2, credential.getParameters().size());
		
	}

}
