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
		return new sun.misc.BASE64Encoder().encode(bstr);  
    }  
 
    /**  
     * 解码  
     * @param filecontent  
     * @return string  
     */ 
	public static byte[] decryptBASE64(String str){  
	    byte[] bt = null;  
	    try {  
	        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
	        bt = decoder.decodeBuffer( str );  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
        return bt;  
    }  
}
