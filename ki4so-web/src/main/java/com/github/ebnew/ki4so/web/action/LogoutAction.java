package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.service.Ki4soService;
import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * 登出web控制器，处理登出的请求。
 * @author burgess yang
 *
 */
@Controller
public class LogoutAction {

	
	@Autowired
	protected CredentialResolver credentialResolver;
	
	@Autowired
	protected Ki4soService ki4soService;
	
	public void setKi4soService(Ki4soService ki4soService) {
		this.ki4soService = ki4soService;
	}

	/**
	 * 设置用户凭据解析器。
	 * @param credentialResolver
	 */
	public void setCredentialResolver(CredentialResolver credentialResolver) {
		this.credentialResolver = credentialResolver;
	}
	
	/**
	 * 查询用户登录的所有应用列表。输出一个json串，该串表示的是当前用户登录的应用列表。
	 * json串的格式如下所示：
	 * {[{appId:1, appName:'系统1', logoutUrl:'http://www.test.com/app1/logout.do'}, {appId:2, appName:'系统2', logoutUrl:'http://www.test.com/app2/logout.do'}]}
	 * @param request 请求对象。
	 * @param response 响应对象。
	 * @return 模型和视图对象。
	 */
	@RequestMapping("/getAppList")
	@ResponseBody
	public Object getAppList(HttpServletRequest request,
			HttpServletResponse response){
		//解析用户凭据。
		Credential credential = credentialResolver.resolveCredential(request);
		//实现输出为json串。
		return this.ki4soService.getAppList(credential);
	}
	
	/**
	 * 登出接口，该接口处理所有与登录有关的请求。
	 * 1.清除用户登录的状态信息，即用户登录了那些应用。
	 * 2.清除sso服务端的cookie。
	 * @param request 请求对象。
	 * @param response 响应对象。
	 * @return 模型和视图对象。
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		
		//清除用户登录应用列表。
		
		//清除cookie值。
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
			for(Cookie cookie:cookies){
				if(WebConstants.KI4SO_SERVER_ENCRYPTED_CREDENTIAL_COOKIE_KEY.equals(cookie.getName())){
					//设置过期时间为立即。
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		//登出后返回登录页面。
		mv.setView(new RedirectView("login.do"));
		return mv;
	}

}
