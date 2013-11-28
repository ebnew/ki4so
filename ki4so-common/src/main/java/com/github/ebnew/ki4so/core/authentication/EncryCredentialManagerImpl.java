package com.github.ebnew.ki4so.core.authentication;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.alibaba.fastjson.JSON;
import com.github.ebnew.ki4so.common.Base64Coder;
import com.github.ebnew.ki4so.common.DESCoder;
import com.github.ebnew.ki4so.common.utils.StringUtils;
import com.github.ebnew.ki4so.core.exception.InvalidEncryCredentialException;
import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

public class EncryCredentialManagerImpl implements EncryCredentialManager{
	
	private KeyService keyService;
	
	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	private static final Logger LOGGER = Logger.getLogger(EncryCredentialManagerImpl.class.getName());

	@Override
	public EncryCredentialInfo decrypt(EncryCredential encryCredential) {
		//不为空。
		if(encryCredential!=null && !StringUtils.isEmpty(encryCredential.getCredential())){
			String credential = encryCredential.getCredential();
			return parseEncryCredential(credential);
		}
		//若为空信息，则返回空。
		return null;
	}
	
	/**
	 * 解析加密后的凭据信息为凭据对象。过程与加密过程相反的逆过程。
	 * @param 加密过的凭据字符串。
	 * @return 凭据对象。
	 * @throws Exception 
	 */
	private EncryCredentialInfo parseEncryCredential(String credential) throws InvalidEncryCredentialException{
		EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
		try{
			//先使用URL解码，再用BASE64进行解码。
			credential = URLDecoder.decode(credential, "UTF-8");
			credential = new String(Base64Coder.decryptBASE64(credential));
			
			//问号分割字符串。
			String[] items = credential.split("\\?");
			//如果长度是2.
			if(items.length==2){
				//第2个字符串不为空，先解析第二个字符串。
				if(items[1]!=null && items[1].length()>0){
					//使用&分割字符。
					String[] params = items[1].split("&");
					for(int i=0; i<params.length; i++){
						if(params[i]!=null){
							//使用等号分割。
							String[] values = params[i].split("=");
							if(values!=null && values.length==2){
								if("appId".equals(values[0])){
									encryCredentialInfo.setAppId(values[1]);
								}
								else if("keyId".equals(values[0])){
									encryCredentialInfo.setKeyId(values[1]);
								} 
							}
						}
					}
				}
				else{
					throw new InvalidEncryCredentialException();
				}
				//第1个字符串不为空
				if(!StringUtils.isEmpty(items[0])){
					//使用base64解码为源字符串。
					byte[] data =  Base64Coder.decryptBASE64(items[0]);
					//查询键值。
					Ki4soKey ki4soKey = keyService.findKeyByKeyId(encryCredentialInfo.getKeyId());
					if(ki4soKey!=null){
						//使用密钥进行解密。
						byte[] origin = DESCoder.decrypt(data, ki4soKey.toSecurityKey());
						//将byte数组转换为字符串。
						String json = new String(origin);
						@SuppressWarnings("rawtypes")
						Map map = (Map)JSON.parse(json);
						if(map!=null){
							Object userId = map.get("userId");
							Object createTime = map.get("createTime");
							Object expiredTime = map.get("expiredTime");
							encryCredentialInfo.setUserId(userId==null?null:userId.toString());
							encryCredentialInfo.setCreateTime(createTime==null?null:new Date((Long.parseLong(createTime.toString()))));
							encryCredentialInfo.setExpiredTime(expiredTime==null?null:new Date((Long.parseLong(expiredTime.toString()))));
						}
					}
				}
				else{
					throw new InvalidEncryCredentialException();
				}
			}
			else{
				throw new InvalidEncryCredentialException();
			}
		}
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "parse encry credential exception", e);
			throw new InvalidEncryCredentialException();
		}
		
		return encryCredentialInfo;
	}

	/**
	 * 编码的实现流程如下：
	 * 1.将加密凭据信息的敏感字段包括：userId,createTime和expiredTime字段
	 * 组合成json格式的数据，然后使用密钥对该字符串进行DES加密,再将加密后的字符串通过Base64编码。
	 * 2.将上述加密串与其它非敏感信息进行拼接，格式如是：[敏感信息加密串]?appId=1&keyId=2
	 * 其中敏感信息加密串为第一步得到的结果，appId为应用标识，keyId为密钥标识。
	 * 3.使用URL进行编码。防止tomcat7下报cookie错误。
	 */
	@Override
	public String encrypt(EncryCredentialInfo encryCredentialInfo) {
		StringBuffer sb = new StringBuffer();
		if(encryCredentialInfo!=null){
			try {
				String data = encryptSensitiveInfo(encryCredentialInfo);
				sb.append(data).append("?appId=").append(encryCredentialInfo.getAppId())
				.append("&keyId=").append(encryCredentialInfo.getKeyId());
				//再进行BASE64编码，避免传输错误。
				return URLEncoder.encode(Base64Coder.encryptBASE64(sb.toString().getBytes()), "UTF-8");
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "encrypt data exception", e);
			}
		}
		return sb.toString();
	}
	
	private String encryptSensitiveInfo(EncryCredentialInfo encryCredentialInfo) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", encryCredentialInfo.getUserId());
		if(encryCredentialInfo.getCreateTime()!=null){
			map.put("createTime", encryCredentialInfo.getCreateTime().getTime());
		}
		if(encryCredentialInfo.getExpiredTime()!=null){
			map.put("expiredTime", encryCredentialInfo.getExpiredTime().getTime());
		}
		//查询键值。
		Ki4soKey ki4soKey = keyService.findKeyByKeyId(encryCredentialInfo.getKeyId());
		if(ki4soKey!=null){
			//查询键值。
			Key key = ki4soKey.toSecurityKey();
			if(key!=null){
				byte[] data = DESCoder.encrypt(JSON.toJSONBytes(map), key);
				//先用BASE64编码，再用URL编码。
				return Base64Coder.encryptBASE64(data);
			}
			return "";
		}
		return "";
	}

	@Override
	public boolean checkEncryCredentialInfo(
			EncryCredentialInfo encryCredentialInfo) {
		if(encryCredentialInfo!=null){
			//无凭据对应的用户标识，则无效。
			if(StringUtils.isEmpty(encryCredentialInfo.getUserId())){
				return false;
			}
			Date now = getCurrentDate();
			if(encryCredentialInfo.getExpiredTime()!=null){
				//将未来过期时间减去当前时间。
				long deta = encryCredentialInfo.getExpiredTime().getTime() - now.getTime();
				//若差值大于0，表示过期时间还没有到，凭据继续可以有效使用。
				if(deta>0){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获得当前时间。
	 * @return
	 */
	private Date getCurrentDate(){
		return new Date();
	}
}
