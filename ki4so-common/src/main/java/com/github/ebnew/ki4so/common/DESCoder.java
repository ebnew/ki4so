/*
 * Copyright (c) 2001-2013 Bidlink(Beijing) E-Biz Tech Co.,Ltd.
 * All rights reserved.
 * 必联（北京）电子商务科技有限公司 版权所有
 *
 * <p>DESCoder.java</p>
 *   
 * Created on 2013-6-13-上午10:07:45 by zhaodong.wang
 *
 */
package com.github.ebnew.ki4so.common;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


public class DESCoder {
	
	/**
	 * 密钥算法
	*/
	private static final String KEY_ALGORITHM = "DESede";
	
	private static final String DEFAULT_CIPHER_ALGORITHM = "DESede/ECB/ISO10126Padding";
	
	private static final int KEY_SIZE = 168;
	
	/**
	 * 初始化密钥
	 * 
	 * @return byte[] 密钥 
	 * @throws Exception
	 */
	public static Key initSecretKey(String seed) throws Exception{
		//返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator keyPairGen = KeyGenerator.getInstance(KEY_ALGORITHM);
		//初始化此密钥生成器，使其具有确定的密钥大小
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(seed.getBytes());
		keyPairGen.init(KEY_SIZE, secureRandom);
		//生成一个密钥
		SecretKey  secretKey = keyPairGen.generateKey();
		//实例化DES密钥规则
		DESedeKeySpec dks = new DESedeKeySpec(secretKey.getEncoded());
		//实例化密钥工厂
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		//生成密钥
		return skf.generateSecret(dks);
	}
	
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	二进制密钥
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key) throws Exception{
		return encrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	
	
	
	/**
	 * 加密
	 * 
	 * @param data	待加密数据
	 * @param key	密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}
	
	
	
	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	二进制密钥
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key) throws Exception{
		return decrypt(data, key,DEFAULT_CIPHER_ALGORITHM);
	}
	

	/**
	 * 解密
	 * 
	 * @param data	待解密数据
	 * @param key	密钥
	 * @param cipherAlgorithm	加密算法/工作模式/填充方式
	 * @return byte[]	解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data,Key key,String cipherAlgorithm) throws Exception{
		//实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		//使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		//执行操作
		return cipher.doFinal(data);
	}
	
}