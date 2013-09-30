package com.github.ebnew.ki4so.core.authentication;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试加密凭据管理器对象。
 * @author burgess yang
 *
 */
public class EncryCredentialManagerImplTest {
	
	private EncryCredentialManagerImpl encryCredentialManager;

	@Before
	public void setUp() throws Exception {
		encryCredentialManager = new EncryCredentialManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDecrypt() {
	}

	@Test
	public void testEncrypt() {
		
		/**
		 * 测试异常情况。
		 */
		Assert.assertEquals(0, encryCredentialManager.encrypt(null).length());
		Assert.assertEquals(0, encryCredentialManager.encrypt(null).length());
		
	}

	@Test
	public void testCheckEncryCredentialInfo() {
	}

}
