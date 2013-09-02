package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.service.Ki4soService;
import com.github.ebnew.ki4so.core.service.LoginResult;
import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * 登入web控制器类，处理登录的请求。
 * @author burgess yang
 *
 */
@Controller
public class LoginAction {
	
	@Autowired
	protected CredentialResolver credentialResolver;
	
	@Autowired
	protected Ki4soService ki4soService;
	
	@Autowired
	protected LoginResultToView loginResultToView;

	public void setLoginResultToView(LoginResultToView loginResultToView) {
		this.loginResultToView = loginResultToView;
	}

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
	 * 登录接口，该接口处理所有与登录有关的请求。 包括以下几种情况： 1.
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("login");
		//解析用户凭据。
		Credential credential = credentialResolver.resolveCredential(request);
		//没有提供任何认证凭据。
		if(credential==null){
			//设置serivce地址到session中。
			String service = request.getParameter(WebConstants.SERVICE_PARAM_NAME);
			if(!StringUtils.isEmpty(service)){
				request.getSession().setAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION, service);
			}
			//返回到登录页面，索取用户凭据。
			return mv;
		}
		//提供了用户凭据
		else{
			//调用核心结果进行凭据认证。
			LoginResult result = ki4soService.login(credential);
			//将验证结果转换为视图输出结果。
			mv = loginResultToView.loginResultToView(mv, result, request, response);
		}
		return mv;
	}
	

}
