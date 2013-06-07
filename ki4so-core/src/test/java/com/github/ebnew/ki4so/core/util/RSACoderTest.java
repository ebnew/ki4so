package com.github.ebnew.ki4so.core.util;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;


public class RSACoderTest {
	private String publicKey;
	private String privateKey;

	@Before
	public void setUp() throws Exception {
		Map<String, Object> keyMap = RSACoder.initKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMfU9jlnAqAmztEMmQfABlmChOzRpvFv9Rab9Q");

		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
	}

	@Test
	public void test() throws Exception {
		String str = "tianchen";
		byte [] data = str.getBytes();
		//公钥加密字符串
		byte [] enStr = RSACoder.encryptByPublicKey(data, publicKey);
		//私钥解密
		byte [] deStr = RSACoder.decryptByPrivateKey(enStr, privateKey);
		
		System.out.println("原始字符串:"+str+"\n"+"加密后字符串"+enStr+"\n解密后字符串:"+new String(deStr));
	}

	
}