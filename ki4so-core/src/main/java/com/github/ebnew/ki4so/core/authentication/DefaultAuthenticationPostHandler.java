package com.github.ebnew.ki4so.core.authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 默认的认证后处理器实现类，提供抽象方法由具体子类实现。
 * @author burgess yang
 *
 */
public class DefaultAuthenticationPostHandler implements
		AuthenticationPostHandler {
	
	/**
	 * 密钥持续过期时间，3个月。
	 */
	private static final long DURATION = 3*30*24*60*60*1000;
	
	private EncryCredentialManager encryCredentialManager;
	
	private KeyService keyService;

	public KeyService getKeyService() {
		return keyService;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	public EncryCredentialManager getEncryCredentialManager() {
		return encryCredentialManager;
	}

	public void setEncryCredentialManager(
			EncryCredentialManager encryCredentialManager) {
		this.encryCredentialManager = encryCredentialManager;
	}

	@Override
	public Authentication postAuthentication(Credential credential, Principal principal){
		Date createTime = new Date();
		//若认证通过，则返回认证结果对象。
		AuthenticationImpl authentication = new AuthenticationImpl();
		authentication.setAuthenticatedDate(createTime);
		authentication.setPrincipal(principal);
		String encryCredential = encryCredentialManager.encrypt(buildEncryCredentialInfo(principal, createTime));
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("encryCredential", encryCredential);
		authentication.setAttributes(attributes);
		return authentication;
	}
	
	/**
	 * 构造一个密钥凭据信息对象。
	 * @param principal
	 * @return
	 */
	private EncryCredentialInfo buildEncryCredentialInfo(Principal principal, Date createTime){
		if(principal!=null){
			EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
			Ki4soKey ki4soKey = keyService.findKeyByAppId("1");
			encryCredentialInfo.setAppId("1");
			encryCredentialInfo.setCreateTime(createTime);
			encryCredentialInfo.setUserId(principal.getId());
			encryCredentialInfo.setKeyId(ki4soKey.getKeyId());
			Date expiredTime = new Date((createTime.getTime()+DURATION)); 
			encryCredentialInfo.setExpiredTime(expiredTime);
			return encryCredentialInfo;
		}
		return null;
	}

}
