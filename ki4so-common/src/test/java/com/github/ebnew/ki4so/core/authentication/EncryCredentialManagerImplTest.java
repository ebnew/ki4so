package com.github.ebnew.ki4so.core.authentication;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.ebnew.ki4so.common.Base64Coder;
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
	public void testDecrypt() {
	}

	@Test
	public void testEncrypt() throws UnsupportedEncodingException {
		
		/**
		 * 测试异常情况。
		 */
		Assert.assertEquals(0, encryCredentialManager.encrypt(null).length());
		
		
		//测试传入的参数不合法的情况。
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		KeyService keyService = Mockito.mock(KeyService.class);
		encryCredentialManager.setKeyService(keyService);
		String result = encryCredentialManager.encrypt(encryCredentialInfo);
		checkData(result, encryCredentialInfo);
		
		
		//设置模拟服务，查询到的key为null.
		result = encryCredentialManager.encrypt(encryCredentialInfo);
		checkData(result, encryCredentialInfo);
		
		//设置模拟服务，查询到的key不是null.
		String keyId = "100";
		Ki4soKey key = new Ki4soKey();
		key.setKeyId(keyId);
		key.setValue("dafdasfdasfds");
		encryCredentialInfo.setKeyId(keyId);
		Mockito.when(keyService.findKeyByKeyId(keyId)).thenReturn(key);
		result = encryCredentialManager.encrypt(encryCredentialInfo);
		checkData(result, encryCredentialInfo);
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

	@Test
	public void testCheckEncryCredentialInfo() {
	}

}
