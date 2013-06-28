package com.github.ebnew.ki4so.core.authentication.resolvers;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.DefaultUserPrincipal;
import com.github.ebnew.ki4so.core.authentication.UsernamePasswordCredential;

public class UsernamePasswordCredentialToPrincipalResolverTest {
	
	private UsernamePasswordCredentialToPrincipalResolver resolver = new UsernamePasswordCredentialToPrincipalResolver();
	
	@Test
	public void testSupports(){
		//测试凭据为空的情况。
		Assert.assertFalse(resolver.supports(null));
		
		//测试不支持的凭据类型的情况。
		Credential credential = Mockito.mock(Credential.class);
		Assert.assertFalse(resolver.supports(credential));
		
		//测试支持的凭据类型UsernamePasswordCredential的情况。
		credential = new UsernamePasswordCredential();
		Assert.assertTrue(resolver.supports(credential));
		
		
		//测试支持的凭据类型UsernamePasswordCredential的子类情况。
		credential = new SubUsernamePasswordCredential();
		Assert.assertTrue(resolver.supports(credential));
	}
	
	
	@Test
	public void testResolvePrincipal(){
		//测试传入null的情况。
		Assert.assertNull(resolver.resolvePrincipal(null));
		
		//测试传入不支持的凭据类型的情况。
		Credential credential = Mockito.mock(Credential.class);
		Assert.assertNull(resolver.resolvePrincipal(credential));
		
		//测试传入正确的凭据对象的情况。
		String username = "admin";
		UsernamePasswordCredential usernamePasswordCredential = new UsernamePasswordCredential();
		usernamePasswordCredential.setUsername(username);
		DefaultUserPrincipal principal = (DefaultUserPrincipal)resolver.resolvePrincipal(usernamePasswordCredential);
		Assert.assertNotNull(principal);
		Assert.assertEquals(username, principal.getId());
		
		
	}

}

class SubUsernamePasswordCredential extends UsernamePasswordCredential{
	
}
