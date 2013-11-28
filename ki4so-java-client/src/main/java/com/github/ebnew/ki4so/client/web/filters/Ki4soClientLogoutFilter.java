package com.github.ebnew.ki4so.client.web.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.github.ebnew.ki4so.common.utils.StringUtils;
import com.github.ebnew.ki4so.client.handler.AppClientLogoutHandler;

/**
 * ki4so客户端应用登出的过滤器。
 * 处理客户端应用本身的登出逻辑。
 * @author Administrator
 *
 */
public class Ki4soClientLogoutFilter extends BaseClientFilter{
	
	/**
	 * 登录本应用处理器类，由此类进行构造一个对象。
	 */
	protected String appClientLogoutHandlerClass = "com.github.ebnew.ki4so.app.custom.Ki4soAppClientLogoutHandlerImpl";

	/**
	 * 登录本应用的处理器。
	 */
	protected AppClientLogoutHandler appClientLogoutHandler;
	
	private static Logger logger = Logger.getLogger(Ki4soClientLogoutFilter.class.getName());

	@Override
	public void doInit(FilterConfig filterConfig) throws ServletException {
		appClientLogoutHandlerClass = getInitParameterWithDefalutValue(filterConfig, "appClientLoginHandlerClass", appClientLogoutHandlerClass);
		//构造登录本应用的处理器对象。
		if(!StringUtils.isEmpty(appClientLogoutHandlerClass)){
			try{
				this.appClientLogoutHandler = (AppClientLogoutHandler)Class.forName(appClientLogoutHandlerClass).newInstance();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpSession session = servletRequest.getSession();
		try{
			//本地应用已经登录，则进行登出处理。
			if(session.getAttribute(Ki4soClientFilter.USER_STATE_IN_SESSION_KEY)!=null){
				//清除session中的值。
				session.setAttribute(Ki4soClientFilter.USER_STATE_IN_SESSION_KEY, null);
				//若本定应用处理器不为空。
				if(appClientLogoutHandler!=null){
					//登出本应用。
					appClientLogoutHandler.logoutClient(servletRequest, servletResponse);
				}
				//清除cookie值。
				removeCookeEC(servletRequest, servletResponse);
			}
			//响应登录结果。
			sendResponse(servletResponse);
		}
		catch (Exception e) {
			//响应登录结果。
			sendError(servletResponse);
		}
	}

	@Override
	public void destroy() {
		
	}
	
	private void sendResponse(HttpServletResponse response){
		response.setContentType("text/javascript;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter outhtml;
		try {
			outhtml = response.getWriter();
			outhtml.print("{result:true}");
			outhtml.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "send response error", e);
		} 
	}
	
	private void sendError(HttpServletResponse response){
		try {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "send response error", e);
		} 
	}
	
	

}
