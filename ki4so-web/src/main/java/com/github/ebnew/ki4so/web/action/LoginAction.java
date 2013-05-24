package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.ebnew.ki4so.core.authentication.Credential;

@Controller
public class LoginAction {
	
	protected CredentialResolver credentialResolver;

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
		//解析出已认证凭据。
		Credential authenticatedCredential = credentialResolver.resolveAuthenticatedCredential(request);
		//没有已经认证凭据。
		if(authenticatedCredential==null){
			//解析未认证原始凭据。
			Credential unAuthenticatedCredential = credentialResolver.resolveUnAuthenticatedCredential(request);
			if(unAuthenticatedCredential==null){
				//返回到登录页面，索取用户凭据。
				return mv;
			}
			//有原始凭据，则走认证原始凭据过程。
			else{
				
			}
		}
		//有已认证凭据
		else{
			//校验原始凭据正确性。
			
			
		}
		mv.getModel().put("hello", "测试一下");
		return mv;
	}
	

}
