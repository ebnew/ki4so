package com.github.ebnew.ki4so.core.authentication;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.core.authentication.handlers.AuthenticationHandler;
import com.github.ebnew.ki4so.core.authentication.resolvers.CredentialToPrincipalResolver;
import com.github.ebnew.ki4so.core.exception.EmptyCredentialException;
import com.github.ebnew.ki4so.core.exception.NoAuthenticationPostHandlerException;
import com.github.ebnew.ki4so.core.exception.UnsupportedCredentialsException;

public class AuthenticationManagerImplTest {
	
	AuthenticationManagerImpl authenticationManager;

	@Before
	public void setUp() throws Exception {
		authenticationManager = new AuthenticationManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
		authenticationManager = null;
	}

	/**
	 * 测试认证方法。
	 */
	@Test
	public void testAuthenticate() {
		//测试情况1，当传入的凭据为null的情况，应该抛出异常。
		try{
			authenticationManager.authenticate(null);
			fail("当参数为Null应该抛出异常信息");
		}catch (EmptyCredentialException e) {
			
		}
		
		Credential credential = Mockito.mock(Credential.class);
		
		//测试情况2，测试当认证管理器中的认证处理器列表为null的情况。
		try{
			authenticationManager.authenticate(credential);
			fail("当没有认证处理器应该抛出异常信息");
		}catch (UnsupportedCredentialsException e) {
			
		}
		
		//测试情况3，测试当认证管理器中的认证处理器列表不为null但是是空列表的情况。
		List<AuthenticationHandler> authenticationHandlers = null;
		try{
			authenticationHandlers = new ArrayList<AuthenticationHandler>();
			authenticationManager.setAuthenticationHandlers(authenticationHandlers);
			authenticationManager.authenticate(credential);
			fail("当没有认证处理器应该抛出异常信息");
		}catch (UnsupportedCredentialsException e) {
			
		}
		
		//测试情况4，测试当认证管理器中的认证处理器列表不为null，列表有一个认证处理器，但是不支持该凭据的情况。
		AuthenticationHandler handler = Mockito.mock(AuthenticationHandler.class);
		Mockito.when(handler.supports(credential)).thenReturn(false);
		try{
			authenticationHandlers = new ArrayList<AuthenticationHandler>();
			authenticationHandlers.add(handler);
			authenticationManager.setAuthenticationHandlers(authenticationHandlers);
			authenticationManager.authenticate(credential);
			fail("当没有认证处理器应该抛出异常信息");
		}catch (UnsupportedCredentialsException e) {
			
		}
		
		
		//测试情况5，测试存在合法的认证处理器的情况，但是无合法的凭据转换器。
		handler = Mockito.mock(AuthenticationHandler.class);
		Mockito.when(handler.supports(credential)).thenReturn(true);
		Mockito.when(handler.authenticate(credential)).thenReturn(true);
		try{
			authenticationHandlers = new ArrayList<AuthenticationHandler>();
			authenticationHandlers.add(handler);
			authenticationManager.setAuthenticationHandlers(authenticationHandlers);
			authenticationManager.authenticate(credential);
			fail("当没有凭据转换器应该抛出异常信息");
		}catch (UnsupportedCredentialsException e) {
			
		}
		
		
		//测试情况6，测试存在合法的认证处理器的情况，但是凭据转换器列表不为空，但是集合中的元素为空。
		List<CredentialToPrincipalResolver> resolvers = new ArrayList<CredentialToPrincipalResolver>();
		handler = Mockito.mock(AuthenticationHandler.class);
		Mockito.when(handler.supports(credential)).thenReturn(true);
		Mockito.when(handler.authenticate(credential)).thenReturn(true);
		try{
			authenticationHandlers = new ArrayList<AuthenticationHandler>();
			authenticationHandlers.add(handler);
			authenticationManager.setAuthenticationHandlers(authenticationHandlers);
			authenticationManager.setCredentialToPrincipalResolvers(resolvers);
			authenticationManager.authenticate(credential);
			fail("当没有凭据转换器应该抛出异常信息");
		}catch (UnsupportedCredentialsException e) {
			
		}
		
		//测试情况7，测试存在合法的认证处理器的情况，但是凭据转换器列表不为空，但是集合中的元素不为空，认证后处理器对象是空。
		handler = Mockito.mock(AuthenticationHandler.class);
		Mockito.when(handler.supports(credential)).thenReturn(true);
		Mockito.when(handler.authenticate(credential)).thenReturn(true);
		CredentialToPrincipalResolver resolver = Mockito.mock(CredentialToPrincipalResolver.class);
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(resolver.supports(credential)).thenReturn(true);
		Mockito.when(resolver.resolvePrincipal(credential)).thenReturn(principal);
		try{
			authenticationHandlers = new ArrayList<AuthenticationHandler>();
			authenticationHandlers.add(handler);
			authenticationManager.setAuthenticationHandlers(authenticationHandlers);
			resolvers = new ArrayList<CredentialToPrincipalResolver>();
			resolvers.add(resolver);
			authenticationManager.setCredentialToPrincipalResolvers(resolvers);
			authenticationManager.authenticate(credential);
			fail("当没有认证后处理器应该抛出异常信息");
		}catch (NoAuthenticationPostHandlerException e) {
			
		}
		
		//测试情况8，测试存在合法的认证处理器的情况，但是凭据转换器列表不为空，但是集合中的元素不为空，认证后处理器对象不是空。
		handler = Mockito.mock(AuthenticationHandler.class);
		Mockito.when(handler.supports(credential)).thenReturn(true);
		Mockito.when(handler.authenticate(credential)).thenReturn(true);
		resolver = Mockito.mock(CredentialToPrincipalResolver.class);
		principal = Mockito.mock(Principal.class);
		Mockito.when(resolver.supports(credential)).thenReturn(true);
		Mockito.when(resolver.resolvePrincipal(credential)).thenReturn(principal);
		AuthenticationPostHandler authenticationPostHandler = Mockito.mock(AuthenticationPostHandler.class);
		Authentication authentication = Mockito.mock(Authentication.class);
		Mockito.when(authenticationPostHandler.postAuthentication(credential, principal)).thenReturn(authentication);
		authenticationHandlers = new ArrayList<AuthenticationHandler>();
		authenticationHandlers.add(handler);
		authenticationManager.setAuthenticationHandlers(authenticationHandlers);
		resolvers = new ArrayList<CredentialToPrincipalResolver>();
		resolvers.add(resolver);
		authenticationManager.setCredentialToPrincipalResolvers(resolvers);
		authenticationManager.setAuthenticationPostHandler(authenticationPostHandler);
		authenticationManager.authenticate(credential);
	}

}
