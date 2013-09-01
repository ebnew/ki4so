package com.github.ebnew.ki4so.client.web.filters;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import com.github.ebnew.ki4so.client.key.DefaultKeyServiceImpl;
import com.github.ebnew.ki4so.client.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;

/**
 * ki4so客户端应用的过滤器，从而实现集成ki4so单点登录系统。
 * 此过滤器必须安装或者自己实现。
 * @author Administrator
 */
public class Ki4soClientFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(Ki4soClientFilter.class.getName());
	
	/**
	 * 在客户端的session中的用户信息，避免频繁认证，提高性能。
	 */
	public static final String USER_STATE_IN_SESSION_KEY = "ki4so_client_user_info_session_key";
	
	/**
	 * ki4so服务器主机地址。
	 */
	private String ki4soServerHost = "http://localhost:8080/ki4so-web/";
	
	/**
	 * ki4so服务器登录URL地址。
	 */
	private String ki4soServerLoginUrl = ki4soServerHost+"login.do";
	
	/**
	 * ki4so服务器获取应用秘钥信息的URL地址。
	 */
	private String ki4soServerFetchKeyUrl = ki4soServerHost+"fetchKey.do";
	
	/**
	 * 本应用在ki4so服务器上的应用ID值。
	 */
	private String ki4soClientAppId = "1001";
	
	/**
	 * 本应用对应的加密key.
	 */
	private Ki4soKey ki4soKey;
	
	/**
	 * 秘钥获取服务。
	 */
	private KeyService keyService = null;
	
	/**
	 * 本应用是否登录过。
	 */
	private boolean localAppLogined = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ki4soServerHost = getInitParameterWithDefalutValue(filterConfig, "ki4soServerHost", ki4soServerHost);
		ki4soClientAppId = getInitParameterWithDefalutValue(filterConfig, "ki4soClientAppId", ki4soClientAppId);
		ki4soServerLoginUrl = getInitParameterWithDefalutValue(filterConfig, "ki4soServerLoginUrl", ki4soServerLoginUrl);
		ki4soServerFetchKeyUrl = getInitParameterWithDefalutValue(filterConfig, "ki4soServerFetchKeyUrl", ki4soServerFetchKeyUrl);
		keyService = new DefaultKeyServiceImpl(ki4soServerFetchKeyUrl);
		logger.info("the ki4so sever is :"+this.ki4soServerHost+", please check this service is ok.");
		try{
			ki4soKey = keyService.findKeyByAppId(ki4soClientAppId);
		}catch (Exception e) {
			logger.log(Level.SEVERE, "fetch ki4so key info error", e);
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//若已经登录过，则直接返回，继续其它过滤器。
		if(localAppLogined){
			chain.doFilter(request, response);
			return;
		}
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpSession session = servletRequest.getSession();
		//本地应用未登录。
		if(session.getAttribute(USER_STATE_IN_SESSION_KEY)==null){
			//如果没有key，则获取一次。
			if(ki4soKey==null){
				try{
					ki4soKey = keyService.findKeyByAppId(ki4soClientAppId);
					
					
				}catch (Exception e) {
					logger.log(Level.SEVERE, "fetch ki4so key info error", e);
				}
			}
		}
		

	}

	@Override
	public void destroy() {
		this.ki4soKey = null;
	}
	
	/**
	 * 获取过滤器参数值，带有默认值，若没有配置，则使用默认值。
	 * @param filterConfig
	 * @param paramName
	 * @param defalutValue
	 * @return
	 */
	private String getInitParameterWithDefalutValue(FilterConfig filterConfig, String paramName, String defalutValue){
		String value = filterConfig.getInitParameter(paramName);
		if(StringUtils.isEmpty(value)){
			value = defalutValue;
		}
		return value;
	}

}
