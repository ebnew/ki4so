package com.github.ebnew.ki4so.core.authentication;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
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
	 * 测试解密方法。
	 */
	@Test
	public void testDecrypt(){
		//测试异常输入情况。
		Assert.assertNull(encryCredentialManager.decrypt(null));
		
		EncryCredential encryCredential = new EncryCredential("");
		Assert.assertNull(encryCredentialManager.decrypt(encryCredential));
		
		//错误的凭据格式。
		encryCredential = new EncryCredential("error");
		try{
			encryCredentialManager.decrypt(encryCredential);
			Assert.fail("should throw excption");
		}catch (InvalidEncryCredentialException e) {
			// TODO: handle exception
		}
		
		//测试正常情况。
		EncryCredentialInfo encryCredentialInfo = buildTextEncryCredentialInfo();
		String result =  encryCredentialManager.encrypt(encryCredentialInfo);
		encryCredentialManager.decrypt(new EncryCredential(result));
	}

	/**
	 * 测试加密方法。
	 */
	@Test
	public void testEncrypt(){
		//测试对null加密的情况，返回''。
		Assert.assertEquals(0, encryCredentialManager.encrypt(null).length());
		
		//测试正确情况。
		String result =  encryCredentialManager.encrypt(buildTextEncryCredentialInfo());
		Assert.assertNotNull(result);
	}
	
	private EncryCredentialInfo buildTextEncryCredentialInfo(){
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		encryCredentialInfo.setAppId("1");
		Date createTime = new Date();
		encryCredentialInfo.setCreateTime(createTime);
		Date expiredTime = new Date();
		encryCredentialInfo.setExpiredTime(expiredTime);
		encryCredentialInfo.setKeyId("333");
		encryCredentialInfo.setUserId("wubingyang");
		return encryCredentialInfo;
	}
}
