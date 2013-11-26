package com.github.ebnew.ki4so.core.authentication.key;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.ebnew.ki4so.core.key.KeyServiceImpl;
import com.github.ebnew.ki4so.core.key.Ki4soKey;

public class KeyServiceImplTest {
	
	private KeyServiceImpl keyService;

	@Before
	public void setUp() throws Exception {
		keyService = new KeyServiceImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindKeyByKeyId() {
		//测试异常输入数据情况。
		Assert.assertNull(keyService.findKeyByAppId(null));
		Assert.assertNull(keyService.findKeyByAppId(""));
		Assert.assertNull(keyService.findKeyByAppId("not exsited"));
		
		//测试存在数据的情况。
		Ki4soKey key = keyService.findKeyByAppId("1");
		Assert.assertNotNull(key);
		System.out.println(key);
	}

	@Test
	public void testFindKeyByAppId() {
		//测试异常输入数据情况。
		Assert.assertNull(keyService.findKeyByKeyId(null));
		Assert.assertNull(keyService.findKeyByKeyId(""));
		Assert.assertNull(keyService.findKeyByKeyId("not exsited"));
		
		//测试存在数据的情况。
		Ki4soKey key = keyService.findKeyByKeyId("2");
		Assert.assertNotNull(key);
		System.out.println(key);
	}
	
	/**
	 * 测试读取文件数据方法。
	 */
	@Test
	public void testReadDataFromFile(){
		
		KeyServiceImpl keyServiceImpl = new KeyServiceImpl();
		//测试文件为空的情况。
		//设置文件路径空。
		keyServiceImpl.setClassPathData(null);
		keyServiceImpl.setExternalData(null);
		//读取文件。
		Assert.assertNull(keyServiceImpl.readDataFromFile());
		
		//测试错误的文件路径的情况。
		keyServiceImpl.setExternalData("ssss");
		//读取文件。
		Assert.assertNull(keyServiceImpl.readDataFromFile());
		
		//测试错误的文件路径的情况。
		keyServiceImpl.setClassPathData("ssss");
		//读取文件。
		Assert.assertNull(keyServiceImpl.readDataFromFile());
	}
}
