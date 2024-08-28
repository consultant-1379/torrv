/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
package com.ericsson.nms.rv.test.cases;

import org.testng.annotations.Test;

import se.ericsson.jcat.fw.annotations.Setup;
import se.ericsson.jcat.fw.annotations.Teardown;

import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.VUsers;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.nms.rv.operators.UiSsoOperator;
import com.ericsson.nms.rv.test.data.RVTestDataProvider;

public class UI_SSO extends TorTestCaseHelper implements TestCase {
	/**
	 * 
	 */
	private static final String INITIALIZE_UISSO_OPERATOR = "Initialize uisso operator";
	UiSsoOperator uissoOperator = new UiSsoOperator();
//	BrowserOperator browser;
	@Setup 
	void prepareTestCaseForTORRV303_Func_218(){
	} 
	
//	@VUsers(vusers = {1})
//	@Context(context = {Context.API})
//	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
//	public void healthcheckOnPolicyAgent(Host host){
//		setTestcase("TORRV-303_Func_01","Verify litp_admin user exists");
//		
//		setTestStep(INITIALIZE_UISSO_OPERATOR);
//		uissoOperator.setNode(host);
//
//		setTestStep("Verfiy policy agent is alive");
//		System.out.println(uissoOperator.executeCall());
//		System.out.println(uissoOperator.getExecuteTime());
//	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="AppList", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnApplication(Host node, String appName){
		setTestcase("TORRV-303_Func_01","Verify litp_admin user exists");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("Send REST command to app");
		uissoOperator.sendRestToApp(appName);
		
		setTestStep("Verify response code is Ok");
		assertTrue(uissoOperator.verifyResponseCodeIsOk());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="SsoJboss", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnPolicyAgent(Host node){
		setTestcase("TORRV-303_Func_02","Verify Policy agent is alive");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("Verifty plicy agent is alive");
		assertTrue(uissoOperator.verifyPolicy_AgentRest());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="SsoJboss", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnTokenId(Host node){
		setTestcase("TORRV-303_Func_03","Verify OpenAm returns correct token");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("Verify token id is returned");
		assertTrue(uissoOperator.verifyTokenId());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="JenkinsServer", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckLogin(Host node){
		setTestcase("TORRV-303_Func_04","Verify login UI launcher");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("login UI launcher");
		assertTrue(uissoOperator.loginTest());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="JenkinsServer", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckFavorite(Host node){
		setTestcase("TORRV-303_Func_05","Login UI launcher and verify favoritate tab is working");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("login UI launcher");
		assertTrue(uissoOperator.favoritateTest());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="JenkinsServer", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckHelp(Host node){
		setTestcase("TORRV-303_Func_06","Login UI launcher and verify help page is working");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("login UI launcher");
		assertTrue(uissoOperator.helpTest());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.REST})
	@Test (groups = {"health check"},dataProvider="JenkinsServer", dataProviderClass=RVTestDataProvider.class)
	public void healthchecklogoutLogin(Host node){
		setTestcase("TORRV-303_Func_06","Login UI launcher and verify logout and login is working");

		setTestStep(INITIALIZE_UISSO_OPERATOR);
		uissoOperator.setNode(node);
		
		setTestStep("login UI launcher");
		assertTrue(uissoOperator.logoutLoginTest());
	}

}
