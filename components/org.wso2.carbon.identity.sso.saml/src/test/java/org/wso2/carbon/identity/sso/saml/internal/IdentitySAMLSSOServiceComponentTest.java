/*
 * Copyright (c) 2026, WSO2 LLC. (http://www.wso2.org).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.sso.saml.internal;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.identity.sso.saml.SAMLSSOConstants;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

/**
 * Unit tests for IdentitySAMLSSOServiceComponent.
 */
public class IdentitySAMLSSOServiceComponentTest {

    @DataProvider(name = "intermediateLoaderPageConfigProvider")
    public Object[][] intermediateLoaderPageConfigProvider() {

        return new Object[][]{
                {"true", true},
                {"True", true},
                {"TRUE", true},
                {"false", false},
                {"False", false},
                {"FALSE", false},
                {null, false},
                {"", false},
                {"invalid", false},
        };
    }

    /**
     * Tests that useIntermediateLoaderPage() correctly parses all valid and edge-case configuration values.
     */
    @Test(dataProvider = "intermediateLoaderPageConfigProvider")
    public void testUseIntermediateLoaderPage(String configValue, boolean expected) throws Exception {

        IdentitySAMLSSOServiceComponent component = new IdentitySAMLSSOServiceComponent();
        try (MockedStatic<IdentityUtil> identityUtil = Mockito.mockStatic(IdentityUtil.class)) {
            identityUtil.when(() -> IdentityUtil.getProperty(
                            SAMLSSOConstants.USE_INTERMEDIATE_LOADER_PAGE_CONFIG_NAME))
                    .thenReturn(configValue);

            Method method = IdentitySAMLSSOServiceComponent.class
                    .getDeclaredMethod("useIntermediateLoaderPage");
            method.setAccessible(true);
            boolean result = (boolean) method.invoke(component);

            assertEquals(result, expected,
                    "useIntermediateLoaderPage() should return " + expected
                            + " for config value: '" + configValue + "'");
        }
    }
}
