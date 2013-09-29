package com.github.ebnew.ki4so.core.authentication.resolvers;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.DefaultUserPrincipal;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.authentication.Principal;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;

/**
 * 实现了加密后的凭据转换为对应的用户主体对象的解析器
 *
 * @author Administrator
 *
 */
public class EncryCredentialToPrincipalResolver implements CredentialToPrincipalResolver {

    /**
     * Default class to support if one is not supplied.
     */
    private static final Class<EncryCredential> DEFAULT_CLASS = EncryCredential.class;

    /**
     * Class that this instance will support.
     */
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
        if (credential != null && this.supports(credential)) {
            EncryCredential encryCredential = (EncryCredential) credential;
            DefaultUserPrincipal principal = new DefaultUserPrincipal();
            //解析加密后凭据信息。
            EncryCredentialInfo encryCredentialInfo = encryCredential.getEncryCredentialInfo();
            //设置用户名为唯一标识。
            if (encryCredentialInfo != null) {
                principal.setId(encryCredentialInfo.getUserId());
                //设置参数表为用户属性。
                principal.setAttributes(encryCredential.getParameters());
            }
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
