package com.github.ebnew.ki4so.common;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class DESCoderTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public  void test() throws Exception {
		
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
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("k1", 1);
		map.put("k2", 2);
		map.put("k3", 3);
		map.put("k4", 4);
		String str = JSON.toJSONString(map);
		System.out.println(str);
		Map<String,Object> jsonMap = (Map<String, Object>) JSON.parse(str);
		System.out.println(jsonMap);
		
		String str_en = "{\"k3\":3,\"k4\":4,\"k1\":1,\"k2\":2}";
		Map<String,Object> jsonMap_en = (Map<String, Object>) JSON.parse(str_en);
		System.out.println(jsonMap_en.get("k3"));
	}
}
