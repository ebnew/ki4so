package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.util.WebUtils;

import com.github.ebnew.ki4so.core.authentication.AbstractParameter;
import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.web.utils.WebConstants;

/**
 * 组合凭据解析器，组合两种解析器，按照优先级顺序，从http请求参数或者cookie中解析出优先级较高的凭据，若无优先级高的凭据，则解析优先级低的凭据。
 * @author bidlink
 * @version 1.0
 * @created 31-五月-2013 8:39:55
 */
public class CompositeCredentialResolver implements CredentialResolver {

	/**
	 * 加密后的凭据解析器。
	 */
	private CredentialResolver encryCredentialResolver;
	
	/**
	 * 原始用户名密码凭据解析器。
	 */
	private CredentialResolver usernamePasswordCredentialResolver;

	public CompositeCredentialResolver(){

	}

	/**
	 * 从http请求参数的cookie或者参数值中解析出凭据信息对象。
	 * 返回解析后的凭据对象。
	 * 先解析加密后的已认证凭据，若没有则再解析出原始的用户名秘密凭据，若任何凭据都没有则返回null.
	 * @param request
	 */
	public Credential resolveCredential(HttpServletRequest request){
		if(request==null){
			return null;
		}
		
		Credential credential = null;
		if(encryCredentialResolver!=null){
			//先解析加密后的凭据。
			credential = encryCredentialResolver.resolveCredential(request);
		}
		//若返回空，则用原始凭据解析器解析。
		if(credential==null){
			if(usernamePasswordCredentialResolver!=null){
				credential = usernamePasswordCredentialResolver.resolveCredential(request);
			}
		}
		
		//如果是抽象的参数凭据对象，则解析其它的参数。
		if(credential instanceof AbstractParameter){
			AbstractParameter abstractParameter = (AbstractParameter)credential;
			//将所有的参数设置到参数列表中，方便以后处理使用。
			abstractParameter.setParameters(WebUtils.getParametersStartingWith(request, null));
			//如果参数列表中无service,则从session中获取。
			if(abstractParameter.getParameterValue(WebConstants.SERVICE_PARAM_NAME)==null){
				if(request.getSession().getAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION)!=null){
					abstractParameter.getParameters().put(WebConstants.SERVICE_PARAM_NAME, request.getSession().getAttribute(WebConstants.KI4SO_SERVICE_KEY_IN_SESSION));
				}
			}
		}
		return credential;
	}

	public void setEncryCredentialResolver(
			CredentialResolver encryCredentialResolver) {
		this.encryCredentialResolver = encryCredentialResolver;
	}

	public void setUsernamePasswordCredentialResolver(
			CredentialResolver usernamePasswordCredentialResolver) {
		this.usernamePasswordCredentialResolver = usernamePasswordCredentialResolver;
	}
	
}