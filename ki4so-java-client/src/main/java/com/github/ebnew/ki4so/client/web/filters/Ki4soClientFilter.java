package com.github.ebnew.ki4so.client.web.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.github.ebnew.ki4so.common.utils.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.github.ebnew.ki4so.client.handler.AppClientLoginHandler;
import com.github.ebnew.ki4so.client.key.DefaultKeyServiceImpl;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.authentication.EncryCredentialManagerImpl;
import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;
import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * ki4so客户端应用的过滤器，从而实现集成ki4so单点登录系统。
 * 此过滤器必须安装或者自己实现。
 * @author Administrator
 */
public class Ki4soClientFilter extends BaseClientFilter {
	
	private static Logger logger = Logger.getLogger(Ki4soClientFilter.class.getName());
	
	/**
	 * 在客户端的session中的用户信息，避免频繁认证，提高性能。
	 */
	public static final String USER_STATE_IN_SESSION_KEY = "ki4so_client_user_info_session_key";
	
	
	
	/**
	 * ki4so服务器登录URL地址。
	 */
	protected String ki4soServerLoginUrl = ki4soServerHost+"login.do";
	
	/**
	 * ki4so服务器获取应用秘钥信息的URL地址。
	 */
	protected String ki4soServerFetchKeyUrl = ki4soServerHost+"fetchKey.do";
	
	/**
	 * 本应用在ki4so服务器上的应用ID值。
	 */
	protected String ki4soClientAppId = "1001";
	
	/**
	 * 登录本应用处理器类，由此类进行构造一个对象。
	 */
	protected String appClientLoginHandlerClass = "com.github.ebnew.ki4so.app.custom.Ki4soAppClientLoginHandlerImpl";
	
	
	/**
	 * 本应用对应的加密key.
	 */
	protected Ki4soKey ki4soKey;
	
	/**
	 * 秘钥获取服务。
	 */
	protected KeyService keyService = null;
	
	/**
	 * 凭据管理器。
	 */
	protected EncryCredentialManagerImpl encryCredentialManager;
	
	/**
	 * 登录本应用的处理器。
	 */
	protected AppClientLoginHandler appClientLoginHandler;
	

	@Override
	public void doInit(FilterConfig filterConfig) throws ServletException {
		ki4soClientAppId = getInitParameterWithDefalutValue(filterConfig, "ki4soClientAppId", ki4soClientAppId);
		ki4soServerLoginUrl = getInitParameterWithDefalutValue(filterConfig, "ki4soServerLoginUrl", ki4soServerHost+"login.do");;
		ki4soServerFetchKeyUrl = getInitParameterWithDefalutValue(filterConfig, "ki4soServerFetchKeyUrl", ki4soServerHost+"fetchKey.do");
		appClientLoginHandlerClass = getInitParameterWithDefalutValue(filterConfig, "appClientLoginHandlerClass", appClientLoginHandlerClass);
		//构造key服务等相关对象。
		//构造登录本应用的处理器对象。
		if(!StringUtils.isEmpty(appClientLoginHandlerClass)){
			try{
				this.appClientLoginHandler = (AppClientLoginHandler)Class.forName(appClientLoginHandlerClass).newInstance();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		keyService = new DefaultKeyServiceImpl(ki4soServerFetchKeyUrl, ki4soClientAppId);
		this.encryCredentialManager = new EncryCredentialManagerImpl();
		this.encryCredentialManager.setKeyService(keyService);
		logger.info("the ki4so sever is :"+this.ki4soServerHost+", please check this service is ok.");
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpSession session = servletRequest.getSession();
		try{
			//本地应用未登录。
			if(session.getAttribute(USER_STATE_IN_SESSION_KEY)==null){
				//查找参数中是否存在ki4so_client_ec值，若没有则重定向到登录页面。
				String ki4so_client_ec = getClientEC(servletRequest);
				if(StringUtils.isEmpty(ki4so_client_ec)){
					//跳转到Ki4so登录页面。
					servletResponse.sendRedirect(buildRedirectToKi4soServer(servletRequest));
					return;
				}
				//如果没有key，则重试获取一次。
				if(ki4soKey==null){
					try{
						ki4soKey = keyService.findKeyByAppId(ki4soClientAppId);
					}catch (Exception e) {
						logger.log(Level.SEVERE, "fetch ki4so key info error", e);
					}
				}
				//解密凭据信息。
				EncryCredentialInfo encryCredentialInfo = this.encryCredentialManager.decrypt(new EncryCredential(ki4so_client_ec));
				if(encryCredentialInfo!=null){
					//检查凭据合法性。
					boolean valid = this.encryCredentialManager.checkEncryCredentialInfo(encryCredentialInfo);
					//如果合法，则继续其它处理。
					if(valid){
						//设置登录状态到session中。
						session.setAttribute(USER_STATE_IN_SESSION_KEY, encryCredentialInfo);
						//触发登录本应用的处理。
						if(appClientLoginHandler!=null){
							//登录本应用。
							appClientLoginHandler.loginClient(encryCredentialInfo, servletRequest, servletResponse);
						}
						
						//重新定位到原请求，去除EC参数。
						String url = servletRequest.getRequestURL().toString();
						if(!StringUtils.isEmpty(url)){
							//如果请求中存在EC参数，则去除这个参数，重定位。
							if(url.contains(WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY)){
								url = url.substring(0, url.indexOf(WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY));
								//去除末尾的问号。
								if(url.endsWith("?")){
									url = url.substring(0, url.length()-1);
								}
								
								//去除末尾的&符号。
								if(url.endsWith("&")){
									url = url.substring(0, url.length()-1);
								}
							}
						}
						//登录成功后，写入EC到cookie中。
						writeEC(ki4so_client_ec, servletResponse);
						
						//重新定位请求，避免尾部出现长参数。
						servletResponse.sendRedirect(url);
						return;
					}
				}
				//否则凭据信息不合法，跳转到Ki4so登录页面。
				servletResponse.sendRedirect(buildRedirectToKi4soServer(servletRequest));
				return;
			}
			
			//若已经登录过，则直接返回，继续其它过滤器。
			chain.doFilter(request, response);
			return;
		}
		//处理异常信息。
		catch (Exception e) {
			removeCookeEC(servletRequest, servletResponse);
			
			//否则凭据信息不合法，跳转到Ki4so登录页面。
			servletResponse.sendRedirect(buildRedirectToKi4soServer(servletRequest));
			return;
		}
		
	}
	
	protected String buildRedirectToKi4soServer(HttpServletRequest servletRequest){
		StringBuffer sb = new StringBuffer(this.ki4soServerLoginUrl);
		if(this.ki4soServerLoginUrl.contains("?")){
			sb.append("&");
		}
		else{
			sb.append("?");
		}
		sb.append("service=").append(servletRequest.getRequestURL().toString());
		return sb.toString();
	}

	@Override
	public void destroy() {
		this.ki4soKey = null;
	}
	
	
	/**
	 * 从客户端参数或者cookie中获取EC值。
	 * @param request http请求对象。
	 * @return EC值。
	 */
	protected String getClientEC(HttpServletRequest request){
		String ec = null;
		if(request!=null){
			ec = request.getParameter(WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY);
			//再从cookie中获取值。
			if(StringUtils.isEmpty(ec)){
				Cookie cookie = getCookie(request, WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY);
				if(cookie!=null){
					ec = cookie.getValue().trim();
				}
			}
		}
		return ec;
	}
	
	/**
	 * 将EC的值写入到服务器的cookie中。
	 * @param ec EC值。
	 * @param response Http响应对象。
	 */
	protected void writeEC(String ec, HttpServletResponse response){
		//使用URL进行编码，避免写入cookie错误。
		try {
			ec = URLEncoder.encode(ec, "UTF-8");
			response.addCookie(new Cookie(
					WebConstants.KI4SO_CLIENT_ENCRYPTED_CREDENTIAL_COOKIE_KEY, ec));
		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE, "encode with URL error", e);
		}
		
	}
	
}
