/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.ebnew.ki4so.core;

import com.github.ebnew.ki4so.core.authentication.UsernamePasswordCredential;

/**
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.0.2
 */
public final class TestUtils {

    public static final String CONST_USERNAME = "test";

    private static final String CONST_PASSWORD = "test1";

    public static final String CONST_EXCEPTION_EXPECTED = "Exception expected.";

    public static final String CONST_EXCEPTION_NON_EXPECTED = "Exception not expected.";
    
    public static final String CONST_GOOD_URL = "https://github.com/";

    private TestUtils() {
        // do not instanciate
    }

    public static UsernamePasswordCredential getCredentialsWithSameUsernameAndPassword() {
        return getCredentialsWithSameUsernameAndPassword(CONST_USERNAME);
    }

    public static UsernamePasswordCredential getCredentialsWithSameUsernameAndPassword(
        final String username) {
        return getCredentialsWithDifferentUsernameAndPassword(username,
            username);
    }

    public static UsernamePasswordCredential getCredentialsWithDifferentUsernameAndPassword() {
        return getCredentialsWithDifferentUsernameAndPassword(CONST_USERNAME,
            CONST_PASSWORD);
    }

    public static UsernamePasswordCredential getCredentialsWithDifferentUsernameAndPassword(
        final String username, final String password) {
        // noinspection LocalVariableOfConcreteClass
        final UsernamePasswordCredential usernamePasswordCredentials = new UsernamePasswordCredential();
        usernamePasswordCredentials.setUsername(username);
        usernamePasswordCredentials.setPassword(password);

        return usernamePasswordCredentials;
    }



}
