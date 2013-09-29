/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ebnew.ki4so.core.authentication.resolvers;

import com.github.ebnew.ki4so.core.authentication.Credential;
import com.github.ebnew.ki4so.core.authentication.EncryCredential;
import com.github.ebnew.ki4so.core.authentication.Principal;
import com.github.ebnew.ki4so.core.model.EncryCredentialInfo;
import java.util.HashMap;
import java.util.Map;
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
public class EncryCredentialToPrincipalResolverTest {

    private EncryCredentialToPrincipalResolver resolver;

    public EncryCredentialToPrincipalResolverTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.resolver = new EncryCredentialToPrincipalResolver();
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testResolvePrincipal() {
        //测试null情况。
        assertNull(this.resolver.resolvePrincipal(null));

        //测试不只是的用户凭据对象。
        Credential credential = Mockito.mock(Credential.class);
        assertNull(this.resolver.resolvePrincipal(credential));

        //测试正常情况。
        String userId = "test";
        EncryCredential encryCredential = new EncryCredential("ddd");
        Map<String, Object> param = new HashMap<String, Object>();
        encryCredential.setParameters(param);
        EncryCredentialInfo encryCredentialInfo = new EncryCredentialInfo();
        encryCredentialInfo.setUserId(userId);
        encryCredential.setEncryCredentialInfo(encryCredentialInfo);
        Principal principal = this.resolver.resolvePrincipal(encryCredential);
        assertNotNull(principal);
        assertEquals(userId, principal.getId());
        assertEquals(param, principal.getAttributes());
    }
}
