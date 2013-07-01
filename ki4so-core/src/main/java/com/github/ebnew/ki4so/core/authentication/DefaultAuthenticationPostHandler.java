package com.github.ebnew.ki4so.core.authentication;

import java.util.Date;

public class DefaultAuthenticationPostHandler implements
		AuthenticationPostHandler {

	@Override
	public Authentication postAuthentication(boolean authenticated,
			Credential credential, Principal principal) {
		AuthenticationImpl authentication = new AuthenticationImpl();
		authentication.setAuthenticatedDate(new Date());
		authentication.setPrincipal(principal);
		return authentication;
	}

}
