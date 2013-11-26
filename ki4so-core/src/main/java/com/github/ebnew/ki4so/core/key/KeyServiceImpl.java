package com.github.ebnew.ki4so.core.key;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.ebnew.ki4so.core.dao.fs.FileSystemDao;

/**
 * 默认的key管理实现类，从classpath:/keys.js文件中
 * 读取key配置信息，是以json格式存储的。
 * @author Administrator
 *
 */
public class KeyServiceImpl extends FileSystemDao implements KeyService {
	
	private static Logger logger = Logger.getLogger(KeyServiceImpl.class.getName());
	
	/**
	 * 外部数据文件地址，优先级更高。
	 */
	public static final String  DEFAULT_EXTERNAL_DATA =  "D:\\workspace\\ki4so\\ki4so-core\\src\\main\\resources\\keys.js";
	
	/**
	 * 默认的数据文件地址，在classpath下。
	 */
	public static final String DEFAULT_CLASSPATH_DATA = "keys.js";
	
	/**
	 * 秘钥映射表，key是keyId,value是Key对象。
	 */
	private Map<String, Ki4soKey> keyMap = null;
	
	/**
	 * 秘钥映射表，key是appId,value是Key对象。
	 */
	private Map<String, Ki4soKey> appIdMap = null;
	
	public KeyServiceImpl(){
		this.externalData = DEFAULT_EXTERNAL_DATA;
		this.classPathData = DEFAULT_CLASSPATH_DATA;
		//加载数据。
		loadAppData();
	}
	
	@Override
	protected void loadAppData(){
		try{
			String s = this.readDataFromFile();
			//将读取的应用列表转换为应用map。
			List<Ki4soKey> keys = JSON.parseObject(s, new TypeReference<List<Ki4soKey>>(){});
			if(keys!=null){
				keyMap = new HashMap<String, Ki4soKey>(keys.size());
				appIdMap = new HashMap<String, Ki4soKey>(keys.size());
				for(Ki4soKey key:keys){
					keyMap.put(key.getKeyId(), key);
					appIdMap.put(key.getAppId(), key);
				}
				keys = null;
			}
		}catch (Exception e) {
			logger.log(Level.SEVERE, "load app data file error.", e);
		}
	}

	@Override
	public Ki4soKey findKeyByKeyId(String keyId) {
		if(this.keyMap!=null){
			return this.keyMap.get(keyId);
		}
		return null;
	}

	@Override
	public Ki4soKey findKeyByAppId(String appId) {
		if(this.appIdMap!=null){
			return this.appIdMap.get(appId);
		}
		return null;
	}

}
