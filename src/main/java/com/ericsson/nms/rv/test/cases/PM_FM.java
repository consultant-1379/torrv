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

import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.VUsers;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.nms.rv.operators.PmFmOperator;
import com.ericsson.nms.rv.operators.UiSsoOperator;
import com.ericsson.nms.rv.test.data.RVTestDataProvider;

public class PM_FM extends TorTestCaseHelper implements TestCase {
	private static final String INITIALIZE_UISSO_OPERATOR = "Initialize uisso operator";
	PmFmOperator pmfmOperator = new PmFmOperator();
	
	@Setup 
	void prepareTestCaseForTORRV303_Func_218(){
		
	} 
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void fmSu0ProtocolInfo(Host node){
		
		setTestcase("TORRV-303_FMPM_1","FM su 0 protocol info is correct");
			
		setTestStep("Initialize PM_FM operator");
		pmfmOperator.setNode(node);
		
		setTestStep("Verify all the protocol info is correct");
		assertEquals(pmfmOperator.getFmSu0ProtocolInfo(),pmfmOperator.getExpectedFmSu0ProtocolInfo());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void fmSu1ProtocolInfo(Host node){
		
		setTestcase("TORRV-303_FMPM_2","FM su 1 protocol info is correct");
			
		setTestStep("Initialize PM_FM operator");
		pmfmOperator.setNode(node);
		
		setTestStep("Verify all the info is correct");
		assertEquals(pmfmOperator.getFmSu1ProtocolInfo(),pmfmOperator.getExpectedFmSu1ProtocolInfo());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void pmSu0ProtocolInfo(Host node){
		
		setTestcase("TORRV-303_FMPM_3","PM su 0 protocol info is correct");
			
		setTestStep("Initialize PM_FM operator");
		pmfmOperator.setNode(node);
		
		setTestStep("Verify all the protocol info is correct");
		assertEquals(pmfmOperator.getPmSu0ProtocolInfo(),pmfmOperator.getExpectedPmSu0ProtocolInfo());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void pmSu1ProtocolInfo(Host node){
		
		setTestcase("TORRV-303_FMPM_4","PM su 1 protocol info is correct");
			
		setTestStep("Initialize PM_FM operator");
		pmfmOperator.setNode(node);
		
		setTestStep("Verify all the info is correct");
		assertEquals(pmfmOperator.getPmSu1ProtocolInfo(),pmfmOperator.getExpectedPmSu1ProtocolInfo());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void fmSu0ConsumerChannelId(Host node){
		
		setTestcase("TORRV-303_FMPM_5","FM su 0 consumer channel id is correct");
			
		setTestStep("Initialize PM_FM operator");
		pmfmOperator.setNode(node);
		
		setTestStep("Verify channel id is correct");
		assertEquals(pmfmOperator.getFmSu0MediationConsumerChannelId(),pmfmOperator.getExpectedFmSu0MediationConsumerChannelId());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void fmSu1ConsumerChannelId(Host node){
		
		setTestcase("TORRV-303_FMPM_6","FM su 1 consumer channel id is correct");
			
		setTestStep("Initialize PM_FM operator");
		pmfmOperator.setNode(node);
		
		setTestStep("Verify channel id is correct");
		assertEquals(pmfmOperator.getFmSu1MediationConsumerChannelId(),pmfmOperator.getExpectedFmSu1MediationConsumerChannelId());
	}
}
