/*
 * Copyright (c) 2001-2013 Bidlink(Beijing) E-Biz Tech Co.,Ltd.
 * All rights reserved.
 * 必联（北京）电子商务科技有限公司 版权所有
 *
 * <p>Base64Coder.java</p>
 *   
 * Created on 2013-6-13-上午10:07:15 by zhaodong.wang
 *
 */

package com.github.ebnew.ki4so.common;

import java.io.IOException;

public class Base64Coder {
	  /**  
     * 编码  
     * @param filecontent  
     * @return String  
     */ 
	public static String encryptBASE64(byte[] bstr){
		if(bstr==null || bstr.length==0){
			return null;
		}
		return new sun.misc.BASE64Encoder().encode(bstr);  
    }  
 
    /**  
     * 解码  
     * @param filecontent  
     * @return string  
     */ 
	public static byte[] decryptBASE64(String str){  
		if(str==null || str.length()==0){
			return null;
		}
	    byte[] bt = null;  
	    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
	    try {
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			throw new RuntimeException("decrypt base64 error.", e);
		}  
        return bt;  
    }  
}
