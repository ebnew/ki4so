package com.github.ebnew.ki4so.core.authentication.resolvers;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.DefaultUserPrincipal;
import com.github.ebnew.ki4so.core.authentication.Principal;
import com.github.ebnew.ki4so.core.authentication.UsernamePasswordCredential;

public class UsernamePasswordCredentialToPrincipalResolver implements CredentialToPrincipalResolver{
	
	/** Default class to support if one is not supplied. */
	private static final Class<UsernamePasswordCredential> DEFAULT_CLASS = UsernamePasswordCredential.class;

	/** Class that this instance will support. */
	private Class<?> classToSupport = DEFAULT_CLASS;
	
	/**
	 * Boolean to determine whether to support subclasses of the class to
	 * support.
	 */
	private boolean supportSubClasses = true;

	public void setSupportSubClasses(boolean supportSubClasses) {
		this.supportSubClasses = supportSubClasses;
	}

	@Override
	public Principal resolvePrincipal(Credential credential) {
		//若类型匹配，则进行转换。
		if(credential!=null && this.supports(credential)){
			UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential)credential;
			DefaultUserPrincipal principal = new DefaultUserPrincipal();
			//设置用户名为唯一标识。
			principal.setId(usernamePasswordCredential.getUsername());
			//设置参数表为用户属性。
			principal.setAttributes(usernamePasswordCredential.getParameters());
			return principal;
		}
		return null;
	}

	@Override
	public boolean supports(Credential credential) {
		return credential != null
		&& (this.classToSupport.equals(credential.getClass()) || (this.classToSupport
				.isAssignableFrom(credential.getClass()))
				&& this.supportSubClasses);
	}

}
