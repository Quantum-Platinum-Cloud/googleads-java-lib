// Copyright 2014, Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.jaxws;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.google.api.ads.adwords.jaxws.factory.AdWordsServices;
import com.google.api.ads.adwords.jaxws.testing.SoapRequestXmlProvider;
import com.google.api.ads.adwords.jaxws.v201809.cm.Budget;
import com.google.api.ads.adwords.jaxws.v201809.cm.BudgetBudgetDeliveryMethod;
import com.google.api.ads.adwords.jaxws.v201809.cm.BudgetOperation;
import com.google.api.ads.adwords.jaxws.v201809.cm.BudgetServiceInterface;
import com.google.api.ads.adwords.jaxws.v201809.cm.Money;
import com.google.api.ads.adwords.jaxws.v201809.cm.Operator;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.soap.testing.SoapResponseXmlProvider;
import com.google.api.ads.common.lib.testing.MockHttpIntegrationTest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.common.collect.Lists;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

/**
 * Tests that a AdWords JAX-WS SOAP call can be made end-to-end with compression disabled.
 */
@RunWith(JUnit4.class)
public class AdWordsJaxWsSoapIntegrationTest extends MockHttpIntegrationTest {
  
  private static final String API_VERSION = "v201809";
  
  @Rule
  public final ExpectedException thrown = ExpectedException.none(); 
  
  @BeforeClass
  public static void setupClass() {
    System.setProperty("api.adwords.useCompression", "false");
  }

  /**
   * Tests making a JAX-WS Ad Manager API call with OAuth2.
   */
  @Test
  public void testGoldenSoap_oauth2() throws Exception {
    testHttpServer.setMockResponseBody(SoapResponseXmlProvider.getTestSoapResponse(API_VERSION));

    GoogleCredential credential =
        new GoogleCredential.Builder()
            .setTransport(new NetHttpTransport())
            .setJsonFactory(GsonFactory.getDefaultInstance())
            .build();
    credential.setAccessToken("TEST_ACCESS_TOKEN");

    AdWordsSession session = new AdWordsSession.Builder()
        .withUserAgent("TEST_APP")
        .withOAuth2Credential(credential)
        .withEndpoint(testHttpServer.getServerUrl())
        .withDeveloperToken("TEST_DEVELOPER_TOKEN")
        .withClientCustomerId("TEST_CLIENT_CUSTOMER_ID")
        .build();
  
    BudgetServiceInterface budgetService =
        new AdWordsServices().get(session, BudgetServiceInterface.class);
    
    Budget budget = new Budget();
    budget.setName("Test Budget Name");
    Money money = new Money();
    money.setMicroAmount(50000000L);
    budget.setAmount(money);
    budget.setDeliveryMethod(BudgetBudgetDeliveryMethod.STANDARD);

    BudgetOperation operation = new BudgetOperation();
    operation.setOperand(budget);
    operation.setOperator(Operator.ADD);
    
    Budget responseBudget = budgetService.mutate(Lists.newArrayList(operation)).getValue().get(0);

    assertEquals("Budget ID does not match", 251877074L, responseBudget.getBudgetId().longValue());
    assertEquals("Budget name does not match", budget.getName(), responseBudget.getName());
    assertEquals("Budget amount does not match", budget.getAmount().getMicroAmount(),
        responseBudget.getAmount().getMicroAmount());
    assertEquals("Budget delivery method does not match", budget.getDeliveryMethod(),
        responseBudget.getDeliveryMethod());

    Diff diff = DiffBuilder.compare(SoapRequestXmlProvider.getOAuth2SoapRequest(API_VERSION)).withTest(
        testHttpServer.getLastRequestBody()).checkForSimilar().build();
    assertFalse(diff.hasDifferences());
    assertFalse("Did not request compression but request was compressed",
        testHttpServer.wasLastRequestBodyCompressed());
    assertEquals("Bearer TEST_ACCESS_TOKEN", testHttpServer.getLastAuthorizationHttpHeader());
  }
}
