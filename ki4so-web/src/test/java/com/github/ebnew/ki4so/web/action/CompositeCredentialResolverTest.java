package com.github.ebnew.ki4so.web.action;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.UsernamePasswordCredential;
import com.github.ebnew.ki4so.web.utils.WebConstants;

public class CompositeCredentialResolverTest {
	
	private CompositeCredentialResolver resolver;

	@Before
	public void setUp() throws Exception {
		resolver = new CompositeCredentialResolver();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testResolveCredential() {
		
		
		//测试参数request是空的情况。
		Assert.assertNull(resolver.resolveCredential(null));
		
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		
		//测试request不是空，内部两个解析器都是空的情况。
		Assert.assertNull(resolver.resolveCredential(request));
		
		//模拟两个解析器对象。
		CredentialResolver encryCredentialResolver = Mockito.mock(CredentialResolver.class);
		CredentialResolver usernamePasswordCredentialResolver = Mockito.mock(CredentialResolver.class);
		
		//测试request不是空，加密解析器是空，原始密码解析器非空的情况。
		resolver.setEncryCredentialResolver(encryCredentialResolver);
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试request不是空，加密解析器不是空，原始密码解析器为空的情况。
		resolver.setEncryCredentialResolver(null);
		resolver.setUsernamePasswordCredentialResolver(usernamePasswordCredentialResolver);
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试request不是空，加密解析器和原始密码解析器都不是空，但是解析器解析返回值是null的情况。
		resolver.setEncryCredentialResolver(encryCredentialResolver);
		resolver.setUsernamePasswordCredentialResolver(usernamePasswordCredentialResolver);
		Assert.assertNull(resolver.resolveCredential(request));
		
		//测试request不是空，加密解析器和原始密码解析器都不是空，但是加密解析器解析返回值是不null的情况。
		Credential credential = Mockito.mock(Credential.class);
		Credential credential1 = Mockito.mock(Credential.class);
		resolver.setEncryCredentialResolver(encryCredentialResolver);
		resolver.setUsernamePasswordCredentialResolver(usernamePasswordCredentialResolver);
		Mockito.when(encryCredentialResolver.resolveCredential(request)).thenReturn(credential);
		Mockito.when(usernamePasswordCredentialResolver.resolveCredential(request)).thenReturn(credential1);
		Credential result = resolver.resolveCredential(request);
		Assert.assertNotNull(result);
		Assert.assertEquals(result, credential);
		
		//测试request不是空，加密解析器和原始密码解析器都不是空，但是加密凭据解析器解析返回值是null,
		//而原始凭据解析器返回不为null的情况。
		credential = Mockito.mock(Credential.class);
		credential1 = Mockito.mock(Credential.class);
		resolver.setEncryCredentialResolver(encryCredentialResolver);
		resolver.setUsernamePasswordCredentialResolver(usernamePasswordCredentialResolver);
		Mockito.when(encryCredentialResolver.resolveCredential(request)).thenReturn(null);
		Mockito.when(usernamePasswordCredentialResolver.resolveCredential(request)).thenReturn(credential1);
		result = resolver.resolveCredential(request);
		Assert.assertNotNull(result);
		Assert.assertEquals(result, credential1);
		
	}
	
	/**
	 * 测试凭据是参数类型的凭据对象。
	 */
	@Test
	public void testResolveCredentialWithAbstractParameter(){
		//准备测试数据。
		//测试request中有service参数的情况。
		UsernamePasswordCredential credential = new UsernamePasswordCredential();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter(WebConstants.SERVICE_PARAM_NAME, "http://localhost/sss.test.htm");
		request.setParameter("param1", "1");
		
		CredentialResolver usernamePasswordCredentialResolver = Mockito.mock(CredentialResolver.class);
		Mockito.when(usernamePasswordCredentialResolver.resolveCredential(request)).thenReturn(credential);
		resolver.setUsernamePasswordCredentialResolver(usernamePasswordCredentialResolver);
		Credential result = this.resolver.resolveCredential(request);
		Assert.assertEquals(result, credential);
		Assert.assertEquals(2, credential.getParameters().size());
		Assert.assertEquals("http://localhost/sss.test.htm", credential.getParameterValue(WebConstants.SERVICE_PARAM_NAME));
		Assert.assertEquals("1", credential.getParameterValue("param1"));
		
		//测试request没有service参数的情况，session中有参数的情况。
		credential = new UsernamePasswordCredential();
		Mockito.when(usernamePasswordCredentialResolver.resolveCredential(request)).thenReturn(credential);
		request.removeAllParameters();//清除原参数。
		request.getSession().setAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION, "http://localhost/test.htm");
		request.setParameter("param2", "2");
		request.setParameter("param3", "3");
		result = this.resolver.resolveCredential(request);
		Assert.assertEquals(result, credential);
		Assert.assertEquals(3, credential.getParameters().size());
		Assert.assertEquals("http://localhost/test.htm", credential.getParameterValue(WebConstants.SERVICE_PARAM_NAME));
		Assert.assertEquals("2", credential.getParameterValue("param2"));
		Assert.assertEquals("3", credential.getParameterValue("param3"));
	}

}
