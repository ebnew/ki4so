package com.github.ebnew.ki4so.core.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.common.Base64Coder;
import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

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
	public void testDecrypt() throws UnsupportedEncodingException {
		
		/**
		 * 测试异常输入的情况，当加密的用户凭据为空的情况。
		 */
		Assert.assertNull(this.encryCredentialManager.decrypt(null));
		EncryCredential encryCredential = new EncryCredential(null);
		Assert.assertNull(this.encryCredentialManager.decrypt(encryCredential));
		encryCredential = new EncryCredential("");
		Assert.assertNull(this.encryCredentialManager.decrypt(encryCredential));
		
		
		/**
		 * 测试异常输入的情况，当加密的用户凭据不合法的情况。
		 */
		encryCredential = new EncryCredential("sdafdasfdsafdsa");
		try{
			this.encryCredentialManager.decrypt(encryCredential);
			Assert.fail("invalid encry credential ,should trhow an exception.");
		}
		catch (InvalidEncryCredentialException e) {
			
		}
	}

	@Test
	public void testEncrypt() throws UnsupportedEncodingException {
		
		/**
		 * 测试异常情况。
		 */
		Assert.assertEquals(0, encryCredentialManager.encrypt(null).length());
		
		
		/**
		 * 测试传入的参数不合法的情况。
		 */
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		KeyService keyService = Mockito.mock(KeyService.class);
		encryCredentialManager.setKeyService(keyService);
		String result = encryCredentialManager.encrypt(encryCredentialInfo);
		checkData(result, encryCredentialInfo);
		
		
		/**
		 * 设置模拟服务，查询到的key为null.
		 */
		result = encryCredentialManager.encrypt(encryCredentialInfo);
		checkData(result, encryCredentialInfo);
		
		/**
		 * 设置模拟服务，查询到的key不是null.
		 */
		String keyId = "1001";
		Ki4soKey key = new Ki4soKey();
		key.setKeyId(keyId);
		key.setValue("dafdasfdasfds");
		encryCredentialInfo.setKeyId(keyId);
		Mockito.when(keyService.findKeyByKeyId(keyId)).thenReturn(key);
		result = encryCredentialManager.encrypt(encryCredentialInfo);
		checkData(result, encryCredentialInfo);
	
		/**
		 * 测试正常的情况。
		 */
		String appId = "1000";
		String userId = "test";
		encryCredentialInfo.setAppId(appId);
		Date now = new Date();
		encryCredentialInfo.setCreateTime(now);
		encryCredentialInfo.setExpiredTime(now);
		encryCredentialInfo.setKeyId(keyId);
		encryCredentialInfo.setUserId(userId);
		//加码。
		result = encryCredentialManager.encrypt(encryCredentialInfo);
		EncryCredential encryCredential = new EncryCredential(result);
		//解码。
		EncryCredentialInfo encryCredentialInfo2 = this.encryCredentialManager.decrypt(encryCredential);
		checkEncryCredentialInfo(encryCredentialInfo, encryCredentialInfo2);
	}
	
	private void checkData(String data, EncryCredentialInfo encryCredentialInfo) throws UnsupportedEncodingException{
		String decodeStr = URLDecoder.decode(data, "UTF-8");
		byte[] bytes = Base64Coder.decryptBASE64(decodeStr);
		String result = new String(bytes);
		Assert.assertTrue(result.length()>0);
		String[] rs = result.split("\\?");
		String[] ds = rs[1].split("&");
		String[] appIds =  ds[0].split("=");
		Assert.assertEquals("appId", appIds[0]);
		if(encryCredentialInfo.getAppId()==null){
			Assert.assertEquals("null", appIds[1]);
		}
		else{
			Assert.assertEquals(encryCredentialInfo.getAppId(), appIds[1]);
		}
		
		String[] keyIds =  ds[1].split("=");
		Assert.assertEquals("keyId", keyIds[0]);
		if(encryCredentialInfo.getKeyId()==null){
			Assert.assertEquals("null", keyIds[1]);
		}
		else{
			Assert.assertEquals(encryCredentialInfo.getKeyId(), keyIds[1]);
		}
	}
	
	private void checkEncryCredentialInfo(EncryCredentialInfo encryCredentialInfo1, EncryCredentialInfo encryCredentialInfo2){
		Assert.assertNotNull(encryCredentialInfo1);
		Assert.assertNotNull(encryCredentialInfo2);
		
		Assert.assertEquals(encryCredentialInfo1.getAppId(), encryCredentialInfo2.getAppId());
		Assert.assertEquals(encryCredentialInfo1.getKeyId(), encryCredentialInfo2.getKeyId());
		Assert.assertEquals(encryCredentialInfo1.getUserId(), encryCredentialInfo2.getUserId());
		Assert.assertEquals(encryCredentialInfo1.getCreateTime(), encryCredentialInfo2.getCreateTime());
		Assert.assertEquals(encryCredentialInfo1.getExpiredTime(), encryCredentialInfo2.getExpiredTime());
	}

	@Test
	public void testCheckEncryCredentialInfo() {
		
		/**
		 * 测试输入异常的情况。
		 */
		Assert.assertFalse(this.encryCredentialManager.checkEncryCredentialInfo(null));
		
		/**
		 * 测试凭据信息的userId为空。
		 */
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		Assert.assertFalse(this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo));
		
		/**
		 * 测试凭据信息的userId不为空，过期时间为空。
		 */
		String userId = "test";
		encryCredentialInfo = new EncryCredentialInfo();
		encryCredentialInfo.setUserId(userId);
		Assert.assertFalse(this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo));
		
		
		/**
		 * 测试凭据信息的userId不为空，过期时间不为空，但是凭据已经过期的情况。
		 */
		encryCredentialInfo.setExpiredTime(new Date());
		Assert.assertFalse(this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo));
		
	}

}

