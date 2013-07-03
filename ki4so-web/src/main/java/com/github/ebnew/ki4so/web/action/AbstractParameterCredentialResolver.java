package com.github.ebnew.ki4so.web.action;

import javax.servlet.http.HttpServletRequest;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.Parameter;

/**
 * 该类提供了参数化的凭据类型的解析后处理方法，将请求中的所有参数全部
 * 转到参数列表中，供相关的处理。
 * @author burgess yang
 *
 */
public abstract class AbstractParameterCredentialResolver extends
		AbstractPreAndPostProcessingCredentialResolver {

	@SuppressWarnings("unchecked")
	@Override
	protected Credential postResolveCredential(HttpServletRequest request,
			Credential credential) {
		if(credential==null){
			return null;
		}
		if(credential instanceof Parameter){
			Parameter parameter = (Parameter)credential;
			parameter.setParameters(request.getParameterMap());
		}
		return super.postResolveCredential(request, credential);
	}

}
