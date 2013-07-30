package com.github.ebnew.ki4so.core.authentication;

import org.springframework.util.StringUtils;

import com.github.ebnew.ki4so.common.Base64Coder;

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

	@Override
	public void encrypt() {
		// TODO Auto-generated method stub
		
	}

}
