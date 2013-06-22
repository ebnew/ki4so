package com.github.ebnew.ki4so.core.authentication.resolvers;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.Principal;

/**
 * 用户认证凭据转换为用户主体对象的解析器接口。
 * @author burgess yang
 *
 */
public interface CredentialToPrincipalResolver {
	
	 /**
     * 将用户凭据转换为对应的用户主体对象。
     * @param 要转成用户主体的用户凭据对象。
     * @return 转换后的用户主体对象。
     */
    Principal resolvePrincipal(Credential credential);

    /**
     * 判断是否支持该用户凭据。
     * 
     * @param 要检查的用户凭据。
     * @return true表示支持，false表示不支持。
     */
    boolean supports(Credential credential);

}
