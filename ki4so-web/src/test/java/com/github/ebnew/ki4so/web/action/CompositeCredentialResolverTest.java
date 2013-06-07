package com.github.ebnew.ki4so.web.action;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;

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

}
