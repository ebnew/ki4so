package com.github.ebnew.ki4so.core.dao.fs;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

/**
 * 本地文件系统数据存取对象抽象类。
 * @author Administrator
 *
 */
public abstract class FileSystemDao {
	
	private static Logger logger = Logger.getLogger(FileSystemDao.class.getName());
	
	/**
	 * 外部数据文件地址，优先级更高。
	 */
	protected String externalData = null;
	
	/**
	 * 默认的数据文件地址，在classpath下。
	 */
	protected String classPathData = null;

	public String getExternalData() {
		return externalData;
	}

	public void setExternalData(String externalData) {
		this.externalData = externalData;
	}

	public String getClassPathData() {
		return classPathData;
	}

	public void setClassPathData(String classPathData) {
		this.classPathData = classPathData;
	}
	
	
	/**
	 * 从数据文件中读取数据。
	 * @return 读取的字符串。
	 */
	public String readDataFromFile(){
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
			if(inputStream==null){
				Resource resource = new ClassPathResource(classPathData);
				inputStream = resource.getInputStream();
			}
			return new String(readStream(inputStream));
			
		}catch (Exception e) {
			logger.log(Level.SEVERE, "load app data file error.", e);
		}
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

}
