package com.github.ebnew.ki4so.core.authentication;

import java.security.Key;

import org.springframework.util.StringUtils;

import com.github.ebnew.ki4so.common.Base64Coder;
import com.github.ebnew.ki4so.common.DESCoder;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

public class EncryCredentialManagerImpl implements EncryCredentialManager{

	@Override
	public void decrypt(EncryCredential encryCredential) {
		//不为空。
		if(encryCredential!=null && !StringUtils.isEmpty(encryCredential.getCredential())){
			byte[] bytes = Base64Coder.decryptBASE64(encryCredential.getCredential());
			if(bytes!=null){
				String s = new String(bytes);
			}
		}
	}

	/**
	 * 编码的实现流程如下：
	 * 1.将加密凭据信息的敏感字段包括：userId,createTime和expiredTime字段
	 * 组合成json格式的数据，然后使用密钥对该字符串进行DES加密,再将加密后的字符串通过Base64编码。
	 * 2.将上述加密串与其它非敏感信息进行拼接，格式如是：[敏感信息加密串]?appId=1&keyId=2
	 * 其中敏感信息加密串为第一步得到的结果，appId为应用标识，keyId为密钥标识。
	 */
	@Override
	public String encrypt(EncryCredentialInfo encryCredentialInfo) {
		StringBuffer sb = new StringBuffer();
		if(encryCredentialInfo!=null){
			try {
				String data = encryptSensitiveInfo(encryCredentialInfo);
				sb.append(data).append("?appId=").append(encryCredentialInfo.getAppId())
				.append("&keyId=").append(encryCredentialInfo.getKeyId());
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		return sb.toString();
	}
	
	private String encryptSensitiveInfo(EncryCredentialInfo encryCredentialInfo) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("{\"userId\":\"").append(encryCredentialInfo.getUserId()).append("\"")
		.append(", \"createTime\":\"").append(encryCredentialInfo.getCreateTime()).append("\"")
		.append(", \"expiredTime\":\"").append(encryCredentialInfo.getExpiredTime()).append("\"");
		Key key = DESCoder.initSecretKey("12345645");
		byte[] data = DESCoder.encrypt(sb.toString().getBytes(), key);
		return Base64Coder.encryptBASE64(data);
	}

}
