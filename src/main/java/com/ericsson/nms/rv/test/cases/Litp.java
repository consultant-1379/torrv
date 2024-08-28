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
import com.ericsson.nms.rv.operators.JbossOperator;
import com.ericsson.nms.rv.operators.LitpOperator;
import com.ericsson.nms.rv.test.data.RVTestDataProvider;

public class Litp  extends TorTestCaseHelper implements TestCase{

	private static final String INITIALIZE_LITP_OPERATOR = "Initialize litp operator";

	LitpOperator litpOperator = new LitpOperator();
	
	@Setup 
	void prepareTestCaseForTORRV303_Func_218(){
		
	} 
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLitp_admin(Host host){
		setTestcase("TORRV-303_Func_01","Verify litp_admin user exists");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);

		setTestStep("Verfiy litp_admin user exists");
		assertTrue(litpOperator.verifyUserlitp_admin());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLitp_user(Host host){
		setTestcase("TORRV-303_Func_02","Verify litp_user user exists");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);

		setTestStep("Verify litp_user user exists");
		assertTrue(litpOperator.verifyUserlitp_user());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnSestatus(Host host){
		setTestcase("TORRV-303_Func_03","Health Check on sestatus");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);

		setTestStep("Verify sestatus status");
		assertTrue(litpOperator.verifySestatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnSshSc_1(Host host){
		setTestcase("TORRV-303_Func_04","Health Check on ssh to sc1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);

		setTestStep("verify ssh to sc-1");
		assertTrue(litpOperator.verifySshOnSc_1());
		
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnSshSc_2(Host host){
		setTestcase("TORRV-303_Func_05","Health Check on ssh sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);

		setTestStep("verify ssh to sc-2");
		assertTrue(litpOperator.verifySshOnSc_2());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLitp_userFromSc_1(Host host){
		setTestcase("TORRV-303_Func_06","Health Check on litp_user on sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify litp_user exits on sc-1");
		assertTrue(litpOperator.verifyUserlitp_user());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLitp_userFromSc_2(Host host){
		setTestcase("TORRV-303_Func_07","Health Check on litp_user on sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify litp_user user exits on sc-2");
		assertTrue(litpOperator.verifyUserlitp_user());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLitp_adminFromSc_1(Host host){
		setTestcase("TORRV-303_Func_08","Health Check on litp_admin on sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify litp_admin exits on sc-1");
		assertTrue(litpOperator.verifyUserlitp_admin());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLitp_adminFromSc_2(Host host){
		setTestcase("TORRV-303_Func_09","Health Check on litp_admin on sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify litp_admin exits on sc-2");
		assertTrue(litpOperator.verifyUserlitp_admin());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnSestatusFromSc_1(Host host){
		setTestcase("TORRV-303_Func_10","Health Check on sestatus on sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify sestatus on sc-1");
		assertTrue(litpOperator.verifySestatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnSestatusFromSc_2(Host host){
		setTestcase("TORRV-303_Func_11","Health Check on sestatus on sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify sestatus on sc-2");
		assertTrue(litpOperator.verifySestatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnTipcFromSc_1(Host host){
		setTestcase("TORRV-303_Func_12","Health Check on tipc on sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify tipc version on sc-1");
		assertTrue(litpOperator.verifyTipcVersion());
		
		setTestStep("Verify tipc link on sc-1");
		assertTrue(litpOperator.verifyTipcLink());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnTipcFromSc_2(Host host){
		setTestcase("TORRV-303_Func_13","Health Check on tipc on sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify tipc version on sc-2");
		assertTrue(litpOperator.verifyTipcVersion());
		
		setTestStep("Verify tipc link on sc-2");
		assertTrue(litpOperator.verifyTipcLink());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnCmwStatusNodeFromSc_1(Host host){
		setTestcase("TORRV-303_Func_14","Health Check on cmw status node");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify cmw-status node from sc-1");
		assertTrue(litpOperator.verifyCmwStatusNode());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnCmwStatusNodeFromSc_2(Host host){
		setTestcase("TORRV-303_Func_15","Health Check on Jboss su");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify cmw-status node from sc-2");
		assertTrue(litpOperator.verifyCmwStatusNode());
	}
	
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnCmwCampaignStatus(Host host) {
		setTestcase("TORRV-303_Func_16","Health Check on cmw campaign status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify cmw campaign status on sc node");
		assertTrue(litpOperator.verifyCmwCampaignStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnPuppetServiceStatus(Host host) {
		setTestcase("TORRV-303_Func_17","Health Check on puppet service status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify puppet service status from ms");
		assertTrue(litpOperator.verifyPuppetServiceStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnPuppetMasterServiceStatus(Host host) {
		setTestcase("TORRV-303_Func_18","Health Check on puppet master status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify puppet master service status from ms");
		assertTrue(litpOperator.verifyPuppetMasterServiceStatus());
	}
	
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnPuppetServiceStatusFromSc1(Host host) {
		setTestcase("TORRV-303_Func_19","Health Check on puppet service status on sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify puppet service status from sc-1");
		assertTrue(litpOperator.verifyPuppetServiceStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnPuppetServiceStatusFromSc2(Host host) {
		setTestcase("TORRV-303_Func_20","Health Check on puppet service status on sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify puppet service status from sc-2");
		assertTrue(litpOperator.verifyPuppetServiceStatus());
	}
	
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnIptablesFromMs(Host host) {
		setTestcase("TORRV-303_Func_21","Health Check on iptables");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify iptables service status from ms1");
		assertTrue(litpOperator.verifyIptablesModule());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnIptablesFromSc1(Host host) {
		setTestcase("TORRV-303_Func_22","Health Check on iptables from sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify iptables service status from sc1");
		assertTrue(litpOperator.verifyIptablesModule());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnIptablesFromSc2(Host host) {
		setTestcase("TORRV-303_Func_23","Health Check on iptables from sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify iptables service status from sc2");
		assertTrue(litpOperator.verifyIptablesModule());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnLandscapedStatus(Host host) {
		setTestcase("TORRV-303_Func_24","Health Check on landscaped status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify landscaped service status from ms");
		assertTrue(litpOperator.verifyLandscapedStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnCobblerStatus(Host host) {
		setTestcase("TORRV-303_Func_25","Health Check on cobbler service status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify cobbler service status from ms1");
		assertTrue(litpOperator.verifyCobblerStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnVersionFile(Host host) {
		setTestcase("TORRV-303_Func_26","Health Check on version file");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify version file contains required information");
		assertTrue(litpOperator.verifyVersionFile());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnVersionFileFromSc1(Host host) {
		setTestcase("TORRV-303_Func_27","Health Check on version file from sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify version file contains required information on sc-1");
		assertTrue(litpOperator.verifyVersionFile());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnVersionFileFromSc2(Host host) {
		setTestcase("TORRV-303_Func_28","Health Check on version file from sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify version file contains required information on sc-2");
		assertTrue(litpOperator.verifyVersionFile());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnNtpServiceStatusFromMs(Host host) {
		setTestcase("TORRV-303_Func_29","Health Check on ntp service status on ms");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify ntp service status on ms");
		assertTrue(litpOperator.verifyNtpServiceStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnNtpServiceStatusFromSc1(Host host) {
		setTestcase("TORRV-303_Func_30","Health Check on ntp service status on sc-1");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify ntp service status on sc-1");
		assertTrue(litpOperator.verifyNtpServiceStatus());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnNtpServiceStatusFromSc2(Host host) {
		setTestcase("TORRV-303_Func_31","Health Check on ntp service status on sc-2");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify ntp service status on sc-2");
		assertTrue(litpOperator.verifyNtpServiceStatus());
	}
	
//	@VUsers(vusers = {1})
//	@Context(context = {Context.API})
//	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
//	public void healthcheckOnHostsFileFromMs(Host host) {
//		setTestcase("TORRV-303_Func_32","Health Check on hosts file");
//		
//		setTestStep(INITIALIZE_LITP_OPERATOR);
//		litpOperator.setNode(host);
//		
//		setTestStep("Verify hosts file contains required information");
//		assertTrue(litpOperator.verifyHostsFile());
//	}
//	
//	@VUsers(vusers = {1})
//	@Context(context = {Context.API})
//	@Test (groups = {"health check"},dataProvider="SC1Host", dataProviderClass=RVTestDataProvider.class)
//	public void healthcheckOnHostsFileFromSc1(Host host) {
//		setTestcase("TORRV-303_Func_33","Health Check on hosts file from sc-1");
//		
//		setTestStep(INITIALIZE_LITP_OPERATOR);
//		litpOperator.setNode(host);
//		
//		setTestStep("Verify hosts file contains required information on sc-1");
//		assertTrue(litpOperator.verifyHostsFile());
//	}
//	
//	@VUsers(vusers = {1})
//	@Context(context = {Context.API})
//	@Test (groups = {"health check"},dataProvider="SC2Host", dataProviderClass=RVTestDataProvider.class)
//	public void healthcheckOnHostsFileFromSc2(Host host) {
//		setTestcase("TORRV-303_Func_34","Health Check on hosts file from sc-2");
//		
//		setTestStep(INITIALIZE_LITP_OPERATOR);
//		litpOperator.setNode(host);
//		
//		setTestStep("Verify hosts file contains required information on sc-2");
//		assertTrue(litpOperator.verifyHostsFile());
//	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnCobblerList(Host host) {
		setTestcase("TORRV-303_Func_35","Health Check on cobbler list");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify cobbler list outputs contains required information");
		assertTrue(litpOperator.verifyCobblerList());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnHypericHqServerStatus(Host host) {
		setTestcase("TORRV-303_Func_36","Health Check on hyperic hq server status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify hyperic hq server is running");
		assertTrue(litpOperator.verifyHypericHqServer());
	}
	
	@VUsers(vusers = {1})
	@Context(context = {Context.API})
	@Test (groups = {"health check"},dataProvider="MS1", dataProviderClass=RVTestDataProvider.class)
	public void healthcheckOnExportJsonCmd(Host host) {
		setTestcase("TORRV-303_Func_36","Health Check on hyperic hq server status");
		
		setTestStep(INITIALIZE_LITP_OPERATOR);
		litpOperator.setNode(host);
		
		setTestStep("Verify litp / exportjson /tmp/test is working");
		assertTrue(litpOperator.verifyExportJson());
	}
	
}
