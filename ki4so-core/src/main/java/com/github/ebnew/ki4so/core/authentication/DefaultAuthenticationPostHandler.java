package com.github.ebnew.ki4so.core.authentication;

import java.util.Date;

/**
 * 抽象的认证后处理器实现类，提供抽象方法由具体子类实现。
 * @author burgess yang
 *
 */
public class DefaultAuthenticationPostHandler implements
		AuthenticationPostHandler {

	@Override
	public Authentication postAuthentication(Credential credential, Principal principal){
		//若认证通过，则返回认证结果对象。
		AuthenticationImpl authentication = new AuthenticationImpl();
		authentication.setAuthenticatedDate(new Date());
		authentication.setPrincipal(principal);
		return authentication;
	}

}
