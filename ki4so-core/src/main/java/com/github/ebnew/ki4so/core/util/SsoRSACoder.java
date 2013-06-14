package com.github.ebnew.ki4so.core.util;

public class SsoRSACoder extends RSACoder {

	
	
	public String encryptData(String data,String key) throws Exception{
		
		//使用公钥加密
		byte [] enByte = RSACoder.encryptByPublicKey(data.getBytes(), key);
		
		return RSACoder.encryptBASE64(enByte);
	}
	
	
	public String decryptByPrivateKey(String data, String key) throws Exception{
		byte [] deByteOrg = RSACoder.decryptBASE64(data);
		//使用私钥解密
		byte [] deByte = RSACoder.decryptByPrivateKey(deByteOrg, key);
		return new String(deByte);
		
	}
}
