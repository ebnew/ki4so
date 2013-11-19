package com.github.ebnew.ki4so.common;

import java.security.Key;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class DESCoderTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		
		//获得秘钥
		Key key = DESCoder.initSecretKey("12345645");
		
		String data ="{id:1234,loginName:\"tianchen\",appId:123}";
		
		System.out.println("en before :"+data);
		
		byte[] encryptData = DESCoder.encrypt(data.getBytes(), key);
		
		String enStr = Base64Coder.encryptBASE64(encryptData);
		System.out.println("en affter:"+enStr);
		
		byte[] decryptData = DESCoder.decrypt(Base64Coder.decryptBASE64(enStr), key);
		System.out.println("de affter:"+new String(decryptData));
		Map<String,Object> map = (Map<String, Object>) JSON.parse(new String(decryptData));;
		System.out.println(map);
	}
	
	/**
	 * 对加密解密性能测试。
	 * @throws Exception 
	 */
	@Test
	public void testPerformance() throws Exception{
		//获得秘钥
		Key key = DESCoder.initSecretKey("12345645");
		String data ="{id:1234,loginName:\"tianchen\",appId:123}";
		
		//检测加密时间。
		long s = System.currentTimeMillis();
		byte[] encryptData = DESCoder.encrypt(data.getBytes(), key);
		String enStr = Base64Coder.encryptBASE64(encryptData);
		long e = System.currentTimeMillis();
		System.out.println("encrypt time:"+(e-s));
		
		
		//检测解密时间。
		s = System.currentTimeMillis();
		byte[] decryptData = DESCoder.decrypt(Base64Coder.decryptBASE64(enStr), key);
		@SuppressWarnings({ "unused", "unchecked" })
		Map<String,Object> map = (Map<String, Object>) JSON.parse(new String(decryptData));;
		e = System.currentTimeMillis();
		System.out.println("decrypt time:"+(e-s));
	}
}
