package com.github.ebnew.ki4so.client.web.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ki4so处理登出的javascript文件请求的过滤器。
 * @author burgess yang
 *
 */
public class Ki4soLogoutJavascriptFilter implements Filter {
	
	/**
	 * javascirpt片段缓存。
	 */
	private String javascript;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			if(javascript==null){
				//读取javascript模版文件。
				javascript = new String(readStream(Ki4soLogoutJavascriptFilter.class.getResourceAsStream("logout.js")));
			}
			//替换一些参数值。
			try {
				response.setContentType("application/x-javascript");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().println(javascript);
			} catch (IOException e) {
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

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
