package com.github.ebnew.ki4so.core.authentication;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

public class EncryCredentialManagerImplTest {
	
	EncryCredentialManagerImpl encryCredentialManager;

	@Before
	public void setUp() throws Exception {
		encryCredentialManager = new EncryCredentialManagerImpl();
	}

	@After
	public void tearDown() throws Exception {
		encryCredentialManager = null;
	}

	/**
	 * 测试加密方法。
	 */
	@Test
	public void testEncrypt(){
		//测试对null加密的情况，返回''。
		Assert.assertEquals(0, encryCredentialManager.encrypt(null).length());
		
		
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		encryCredentialInfo.setAppId("1");
		Date createTime = new Date();
		encryCredentialInfo.setCreateTime(createTime);
		Date expiredTime = new Date();
		encryCredentialInfo.setExpiredTime(expiredTime);
		encryCredentialInfo.setKeyId("333");
		encryCredentialInfo.setUserId("wubingyang");
		
		String result =  encryCredentialManager.encrypt(encryCredentialInfo);
		Assert.assertNotNull(result);
		Assert.assertTrue(result.endsWith("?appId=1&keyId=333"));
		
	}
}
