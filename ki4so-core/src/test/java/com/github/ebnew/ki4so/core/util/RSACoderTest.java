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
		String str = "[id:1234,loginName:tianchen,appId:123]";
		byte [] data = str.getBytes();
		//公钥加密字符串
		byte [] enStr = RSACoder.encryptByPublicKey(data, publicKey);
		//解密后上午字符串。用base64转换成字符串给给前端
		String enCode = RSACoder.encryptBASE64(enStr);
		//用base64解密成byte数组
		byte [] deCode = RSACoder.decryptBASE64(enCode);
		//私钥解密
		byte [] deStr = RSACoder.decryptByPrivateKey(deCode, privateKey);
		
		System.out.println("原始字符串:"+str+"\n"+"加密后字符串:"+enCode+"\n解密后字符串:"+new String(deStr));
	}

}