package com.github.ebnew.ki4so.core.app;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

/**
 * 默认的应用管理服务实现类，默认将应用信息存储在
 * json格式的数据文件中。
 * @author burgess yang
 *
 */
public class AppServiceImpl implements AppService {
	
	private static Logger logger = Logger.getLogger(AppServiceImpl.class.getName());
	
	/**
	 * 外部数据文件地址，优先级更高。
	 */
	private String externalData = "D:\\workspace\\ki4so\\ki4so-core\\target\\classes\\app.js";
	
	/**
	 * 默认的数据文件地址，在classpath下。
	 */
	private String classPathData = "classpath:app.js";
	
	
	/**
	 * 构造方法。
	 */
	public AppServiceImpl(){
		//加载数据。
		loadAppData();
	}
	
	private void loadAppData(){
		try{
			InputStream inputStream = null;
			//优先使用外部数据文件。
			if(!StringUtils.isEmpty(externalData)){
				try{
					inputStream = new FileInputStream(externalData);
				}catch (Exception e) {
					inputStream = null;
				}
			}
			//若无外部文件，则使用默认的内部资源文件。
			else{
				Resource resource = new ClassPathResource(classPathData);
				inputStream = resource.getInputStream();
			}
			
			String s = new String(readStream(inputStream));
			List<App> apps = JSON.parseObject(s, new TypeReference<List<App>>(){});
		}catch (Exception e) {
			logger.log(Level.SEVERE, "load app data file error.", e);
		}
	}
	

	@Override
	public App findAppById(String appId) {
		return null;
	}
	
	/**
	 * 读取流
	 * 
	 * @param inStream
	 * @return 字节数组
	 * @throws Exception
	 */
	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	
	public static void main(String[] args) {
		AppServiceImpl appServiceImpl = new AppServiceImpl();
		System.out.println(appServiceImpl.findAppById("1"));
	}

}
