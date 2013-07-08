package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登出web控制器，处理登出的请求。
 * @author burgess yang
 *
 */
@Controller
public class LogoutAction {
	
	/**
	 * 查询用户登录的所有应用列表。
	 * @param request 请求对象。
	 * @param response 响应对象。
	 * @return 模型和视图对象。
	 */
	@RequestMapping("/getAppList")
	public ModelAndView getAppList(HttpServletRequest request,
			HttpServletResponse response){
		ModelAndView mv = new ModelAndView();
		
		
		return mv;
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
				//设置过期时间为立即。
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		return mv;
	}

}
