package com.github.ebnew.ki4so.core.authentication;

/**
 * 用户名和密码形式的未经过认证的原始用户凭证
 *
 * @author bugess yang
 */
public class UsernamePasswordCredential extends AbstractParameter implements Credential {

    /**
     * 用户登录名。
     */
    private String username;

    /**
     * 用户登录密码。
     */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsernamePasswordCredential() {
    }
    
    public UsernamePasswordCredential(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isOriginal() {
        return true;
    }

}
