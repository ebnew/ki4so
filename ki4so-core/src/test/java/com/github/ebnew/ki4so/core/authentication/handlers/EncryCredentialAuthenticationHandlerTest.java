package com.github.ebnew.ki4so.core.authentication.handlers;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.authentication.EncryCredentialManager;
import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 测试加密后凭据认证处理器对象。
 * @author burgess yang
 *
 */
public class EncryCredentialAuthenticationHandlerTest {
	
	private EncryCredentialAuthenticationHandler handler;

	@Before
	public void setUp() throws Exception {
		handler = new EncryCredentialAuthenticationHandler();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoAuthentication() {
		
		/**
		 * 测试异常输入情况。
		 */
		Assert.assertFalse(handler.authenticate(null));
		Credential credential = Mockito.mock(Credential.class);
		Assert.assertFalse(handler.authenticate(credential));
		
		/**
		 * 测试解密失败抛出异常的情况。
		 */
		EncryCredential encryCredential = new EncryCredential("sssddaf");
		EncryCredentialManager encryCredentialManager = Mockito.mock(EncryCredentialManager.class);
		this.handler.setEncryCredentialManager(encryCredentialManager);
		Mockito.when(encryCredentialManager.decrypt(encryCredential)).thenThrow(InvalidEncryCredentialException.INSTANCE);
		Assert.assertFalse(handler.authenticate(encryCredential));
		
		/**
		 * 测试解密返回null的情况。
		 */
		Mockito.reset(encryCredentialManager);
		Mockito.when(encryCredentialManager.decrypt(encryCredential)).thenReturn(null);
		Assert.assertFalse(handler.authenticate(encryCredential));
		
		/**
		 * 测试解密成功，但是凭据不合法的情况。
		 */
		Mockito.reset(encryCredentialManager);
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		Mockito.when(encryCredentialManager.decrypt(encryCredential)).thenReturn(encryCredentialInfo);
		Assert.assertFalse(handler.authenticate(encryCredential));
		
		
		/**
		 * 测试解密成功，但是凭据合法的情况。
		 */
		Mockito.reset(encryCredentialManager);
		encryCredentialInfo = new EncryCredentialInfo();
		Mockito.when(encryCredentialManager.decrypt(encryCredential)).thenReturn(encryCredentialInfo);
		Mockito.when(encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo)).thenReturn(true);
		Assert.assertTrue(handler.authenticate(encryCredential));
	}

}
