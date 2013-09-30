package com.github.ebnew.ki4so.common;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Base64CoderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEncryptBASE64() {
		//测试空情况。
		Assert.assertNull(Base64Coder.encryptBASE64(null));
		Assert.assertNull(Base64Coder.encryptBASE64(new byte[]{}));
		
		//测试正常输入。
		Assert.assertNotNull(Base64Coder.encryptBASE64(new byte[]{2,2,'a'}));
	}

	@Test
	public void testDecryptBASE64() {
		//测试空情况。
		Assert.assertNull(Base64Coder.decryptBASE64(null));
		Assert.assertNull(Base64Coder.decryptBASE64(""));
		
		//测试随意的输入情况。
		Assert.assertNotNull(Base64Coder.decryptBASE64("ddd"));
		
		//测试加密解密匹配的情况。
		byte[] i =new byte[]{1};
		String s = Base64Coder.encryptBASE64(i);
		byte[] b = Base64Coder.decryptBASE64(s);
		
		Assert.assertEquals(i[0], b[0]);
	}

}
