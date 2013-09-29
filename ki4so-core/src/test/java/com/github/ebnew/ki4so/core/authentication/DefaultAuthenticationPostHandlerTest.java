/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ebnew.ki4so.core.authentication;

import com.github.ebnew.ki4so.core.app.App;
import com.github.ebnew.ki4so.core.app.AppService;
import com.github.ebnew.ki4so.core.authentication.status.UserLoggedStatusStore;
import com.github.ebnew.ki4so.core.exception.NoKi4soKeyException;
import com.github.ebnew.ki4so.core.key.KeyService;
import com.github.ebnew.ki4so.core.key.Ki4soKey;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;
import com.github.ebnew.ki4so.web.utils.WebConstants;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author bidlink
 */
public class DefaultAuthenticationPostHandlerTest {

    private DefaultAuthenticationPostHandler handler;

    public DefaultAuthenticationPostHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        handler = new DefaultAuthenticationPostHandler();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testPostAuthentication() {

        /**
         * 测试传入错误参数的情况。
         */
        Authentication authentication = this.handler.postAuthentication(null, null);
        assertNotNull(authentication);
        Assert.assertNull(authentication.getPrincipal());

        /**
         * 测试参数正确的情况，但是ki4so 服务器的app对象为空的情况。
         */
        String username = "test";
        String password = "pwdsssss";
        UsernamePasswordCredential credential = new UsernamePasswordCredential(username, password);
        DefaultUserPrincipal principal = new DefaultUserPrincipal();
        principal.setId(username);
        //设置模拟服务。
        AppService appService = Mockito.mock(AppService.class);
        Mockito.when(appService.findKi4soServerApp()).thenReturn(null);
        this.handler.setAppService(appService);
        try {
            this.handler.postAuthentication(credential, principal);
            fail("应该抛出异常");
        } catch (NoKi4soKeyException e) {

        }

         /**
         * 测试参数正确的情况，但是ki4so 服务器的app对象不为空的情况。
         * 服务对应的key信息不为的正常情况。
         */
        String appId = "1000";
        String app2Id = "1001";
        String keyId = "1000000";
        String encStringValue = "ssssdafdsafdsafdsafdasfdsafdsa";
        String service = "http://loacahost:8080/ki4so-app/home.do";
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(WebConstants.SERVICE_PARAM_NAME, service);
        credential.setParameters(parameters);
        App app = new App();
        app.setAppId(appId);
        
        App clientApp = new App();
        clientApp.setAppId(app2Id);
        
        Ki4soKey key = new Ki4soKey();
        key.setKeyId(keyId);
        
        Mockito.when(appService.findKi4soServerApp()).thenReturn(app);
        Mockito.when(appService.findAppByHost(service)).thenReturn(clientApp);
         
        KeyService keyService = Mockito.mock(KeyService.class);
        Mockito.when(keyService.findKeyByAppId(appId)).thenReturn(key);
        Mockito.when(keyService.findKeyByAppId(app2Id)).thenReturn(key);
        
        this.handler.setKeyService(keyService);
        EncryCredentialManager encryCredentialManager = Mockito.mock(EncryCredentialManager.class);
        Mockito.when(encryCredentialManager.encrypt(Mockito.any(EncryCredentialInfo.class))).thenReturn(encStringValue);
        this.handler.setEncryCredentialManager(encryCredentialManager);
        
        UserLoggedStatusStore userLoggedStatusStore = Mockito.mock(UserLoggedStatusStore.class);
        this.handler.setUserLoggedStatusStore(userLoggedStatusStore);
        
        authentication = this.handler.postAuthentication(credential, principal);
        assertNotNull(authentication);
    }
}
