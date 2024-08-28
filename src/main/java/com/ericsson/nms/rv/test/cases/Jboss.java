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
import com.ericsson.cifwk.taf.TorTestCaseHelper;
import com.ericsson.cifwk.taf.TestCase;
import com.ericsson.cifwk.taf.annotations.Context;
import com.ericsson.cifwk.taf.annotations.VUsers;
import com.ericsson.cifwk.taf.data.Host;

import com.ericsson.nms.rv.operators.JbossOperator;
import com.ericsson.nms.rv.test.data.RVTestDataProvider;

public class Jboss extends TorTestCaseHelper implements TestCase{
	JbossOperator jbossOperator = new JbossOperator();
	
	@Setup 
	void prepareTestCaseForTORRV303_Func_218(){
		//TODO LITP Installed
	} 
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void healthCheckOnJbossSu(Host node){
		setTestcase("TORRV-303_Func_01","Health Check on Jboss su");
		
		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);

		setTestStep("Verify jboss node is in amf su list");
		assertTrue(jbossOperator.verifyServiceUnit());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void healthCheckOnJbossSi(Host node){
		setTestcase("TORRV-303_Func_02","Health Check on Jboss su");

		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);

		setTestStep("Verify jboss node is in amf si Si");
		assertTrue(jbossOperator.verifyServiceInstance());
	}

	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void healthCheckOnJbossSuState(Host node){
		setTestcase("TORRV-303_Func_05","Health Check on Jboss su state");

		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);
		
		setTestStep("Verify the service unit state of jboss node");
		assertTrue(jbossOperator.verifyServiceUnitState());
		
	}
	

	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void jbossInstanceManagementPort(Host node){
		
		setTestcase("TORRV-303_Func_11","Test on Jboss instance management port");
		
		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);
	
		setTestStep("Verify management port is open");
		assertTrue(jbossOperator.verifyJbossManagementPort());
	}
	

		
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void jbossInstancePidCheck(Host node){
		setTestcase("TORRV-303_Func_13","Test Jboss PID");

		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);
	
		setTestStep("Verify all the jboss node has one process id");
		assertTrue(jbossOperator.verifyJbossInstancePid());
	}
		
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void jbossInstanceOnlineInVcsOnSC1(Host node){
		
		setTestcase("TORRV-303_Func_17","Test Jboss instances online in vcs on sc-1");
			
		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);
		
		setTestStep("Verify all the jboss node is ONLINE status");
		assertTrue(jbossOperator.verifyHaStatusOnline());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="Deployment", dataProviderClass=RVTestDataProvider.class)
	public void deployment(Host node, String deployment){
		
		setTestcase("TORRV-303_Func_17","Test Jboss instances online in vcs on sc-1");
			
		setTestStep("Initialize Jboss operator");
		jbossOperator.setNode(node);
		
		setTestStep("Verify all the deployment info");
		assertTrue(jbossOperator.verifyDeployment(deployment));
	}

	/*
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="JBossNodes", dataProviderClass=RVTestDataProvider.class)
	public void jbossInstanceDeploymentCheckOnSC1(Host node){
		//jbossOperator.setHost(host);
		setTestcase("TORRV-303_Func_15","Test Jboss deployment on SC1");

		setTestStep("Initialize Jboss handler");
		jbossOperator.setNode(node);
		
//		setTestStep("Get jboss Instances on " + host.getIp() + "[" + host.getHostname() +"]");
//		List<RvHost> jbossInstances = jbossOperator.getJbossInstance(host);
		
		setTestStep("Verify all the jboss has all deployments");
		assertTrue(jbossOperator.verifyJbossDeployment());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void jbossInstanceDeploymentCheckOnSC2(RvHost host){
		jbossOperator.setHost(host);
		setTestcase("TORRV-303_Func_16","Test Jboss deployment on SC2");

		setTestStep("Get jboss Instances on " + host.getIp() + "[" + host.getHostname() +"]");
		List<RvHost> jbossInstances = jbossOperator.getJbossInstance(host);
		
		setTestStep("Verify all the jboss has all deployments");
		assertTrue(jbossOperator.verifyJbossDeployment(jbossInstances));
	}
	

	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void jbossInstanceOnlineInVcsOnSC2(RvHost host){
		jbossOperator.setHost(host);
		setTestcase("TORRV-303_Func_18","Test Jboss instances online in vcs on sc-2");

		setTestStep("Get Jboss host on " + host.getIp() + "[" + host.getHostname() +"]");
		List<RvHost> jbossInstances = jbossOperator.getJbossInstance(host);
		
		setTestStep("Verify all the jboss instances are ONLINE status");
		assertTrue(jbossOperator.verifyHastatusOnline(jbossInstances));
	}*/
	
}
