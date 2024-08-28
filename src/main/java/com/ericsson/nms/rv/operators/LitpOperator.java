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
package com.ericsson.nms.rv.operators;

import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.GenericOperator;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.nms.rv.data.RvDataProvider;
import com.ericsson.nms.rv.handlers.CmwHandler;
import com.ericsson.nms.rv.handlers.LitpHandler;
import com.ericsson.nms.rv.test.cases.Litp;
import com.ericsson.cifwk.taf.data.DataHandler;
/**
 * General operators used for litp test cases.
 * @author ewandaf
 *
 */
public class LitpOperator implements GenericOperator {

	/**
	 * 
	 */
	private static final String SYSTEMS = "systems";
	/**
	 * 
	 */
	private static final String DEPLOYMENT1_CLUSTER1_SC2 = "deployment1_cluster1_sc2";
	/**
	 * 
	 */
	private static final String DEPLOYMENT1_CLUSTER1_SC1 = "deployment1_cluster1_sc1";
	/**
	 * 
	 */
	private static final String DISTROS = "distros";
	/**
	 * 
	 */
	private static final String MS1 = "ms1";
	/**
	 * 
	 */
	private static final String IS_RUNNING = "is running";
	/**
	 * 
	 */
	private static final String COMMITTED = "COMMITTED";
	private static final String STATUS_OK = "Status OK";
	private static final String ENFORCING = "enforcing";
	private static final String BIN_HOSTNAME = "/bin/hostname";
	private static final String SC_1 = RvDataProvider.getHostByType(HostType.SC1).getHostname();
	private static final String SC_2 = RvDataProvider.getHostByType(HostType.SC2).getHostname();
	private static final String CURRENT_MODE = "Current mode";
	private static final String LITP_ADMIN = "litp_admin";
	private static final String LITP_USER = "litp_user";

	private static final String PASSWD = "passwd";

	
	Logger logger = Logger.getLogger(this.getClass());
	private LitpHandler litpHandler;
	private CmwHandler  cmwHandler;
	
	public void setNode(Host host) {
		litpHandler = new LitpHandler(host);
		cmwHandler = new CmwHandler(host);
	}
	
	public boolean verifyUserlitp_admin() {
		String value = litpHandler.getEntries(PASSWD, LITP_ADMIN);
		return value.contains(LITP_ADMIN);
	}
	
	public boolean verifyUserlitp_user() {
		String value = litpHandler.getEntries(PASSWD, LITP_USER);
		return value.contains(LITP_USER);
	}
	
	public boolean verifySestatus() {
		String value = litpHandler.getSestatus(CURRENT_MODE).get(0);
		return value.contains(ENFORCING);
	}
	
	public boolean verifySshOnSc_1() {
		String hostName = this.sshToSC1AndExecute(BIN_HOSTNAME);
		return hostName.contains(SC_1);
	}
	
	public boolean verifySshOnSc_2() {
		String hostName = this.sshToSC2AndExecute(BIN_HOSTNAME);
		return hostName.equals(SC_2);
	}
	
	
	public boolean sshToSC1() {
		boolean result = litpHandler.sshTo(SC_1);
		return result;
	}
	
	public String sshToSC1AndExecute(String cmd) {
		String result = litpHandler.sshToAndExecute(SC_1, cmd);
		return result;
	}
	
	public String sshToSC2AndExecute(String cmd) {
		String result = litpHandler.sshToAndExecute(SC_2, cmd);
		return result;
	}
	
	public boolean sshToSC2() {
		boolean result = litpHandler.sshTo(SC_2);
		return result;
	}
	
	public boolean verifyIsOnSC1() {
		String result = litpHandler.getHostName();
		return result.contains(SC_1);
	}
	
	public boolean verifyIsOnSC2() {
		String result = litpHandler.getHostName();
		return result.contains(SC_2);
	}
	
	public boolean verifyTipcVersion() {
		String result = litpHandler.getTipcVersion();
		return !result.isEmpty();
	}
	
	public boolean verifyTipcLink() {
		String result = litpHandler.getTipcLink();
		return result.contains("eth2") && result.contains("eth3");
	}
	
	public boolean verifyCmwStatusNode() {
		String result = cmwHandler.getCmwStatusNode();
		return result.contains(STATUS_OK);
	}
	
	public boolean verifyCmwCampaignStatus() {
		String[] cmwCampaigns = cmwHandler.getCmwCampaigns();
		if (cmwCampaigns.length == 0) {
			logger.error("No campaigns!");
			return false;
		}
		boolean returnResult = true;
		for (String campaign : cmwCampaigns) {
			String status = cmwHandler.getCmwCampaignStatus(campaign);
			logger.info(status);
			returnResult = returnResult && status.contains(COMMITTED);
		}
		return returnResult;
	}
	
	public boolean verifyPuppetServiceStatus() {
		String status = litpHandler.getPuppetServiceStatus();
		return status.contains(IS_RUNNING);
	}
	
	public boolean verifyPuppetMasterServiceStatus() {
		String status = litpHandler.getPuppetMasterServiceStatus();
		return status.contains(IS_RUNNING);
	}
	
	public boolean verifyIptablesModule() {
		String[] modules = litpHandler.getSystemModules();
		for (String mod : modules) {
			if (mod.contains("ip_tables")) {
				return true;
			}
		}
		logger.error("iptables is not running");
		return false;
	}
	
	public boolean verifyLandscapedStatus() {
		String status = litpHandler.getLandscapedStatus();
		return status.contains(IS_RUNNING);
	}

	/**
	 * @return
	 */
	public boolean verifyCobblerStatus() {
		String status = litpHandler.getCobblerStatus();
		return status.contains(IS_RUNNING);
	}

	/**
	 * @return
	 */
	public boolean verifyVersionFile() {
		String litpVersion = litpHandler.getLitpVersion();
		String litpBuiltDate = litpHandler.getLitpBuiltDate();
		//String litpLastUpgradedDate = litpHandler.getLitpLastUpgradedDate();
		String litpSystemInstalledDate = litpHandler.getLitpSystemInstalledDate();
		return !litpVersion.isEmpty() && !litpBuiltDate.isEmpty() 
				&& !litpSystemInstalledDate.isEmpty();
	}

	/**
	 * @return
	 */
	public boolean verifyNtpServiceStatus() {
		String status = litpHandler.getNtpServiceStatus();
		return status.contains(IS_RUNNING);
	}

	/**
	 * @return
	 */
	public boolean verifyHostsFile() {
		String sc1IpHostsFile = litpHandler.getIpFromHostsFile(SC_1);
		String sc2IpHostsFile = litpHandler.getIpFromHostsFile(SC_2);
		String ms1IpHostsFile = litpHandler.getIpFromHostsFile(MS1);
		Host host = litpHandler.getHost();
		return sc1IpHostsFile.equals(host.getIp()) ||
				sc2IpHostsFile.equals(host.getIp()) ||
				ms1IpHostsFile.equals(host.getIp());
	}
	
	/**
	 * @return
	 */
	public boolean verifyCobblerList() {
		List<String> distros = litpHandler.getCobblerSystemList();
		boolean returnResult = true;
		returnResult = returnResult && distros.get(0).contains(DEPLOYMENT1_CLUSTER1_SC1)
		&& distros.get(1).contains(DEPLOYMENT1_CLUSTER1_SC2);
		return returnResult;
	}
	
	public boolean verifyHypericHqServer() {
		String status = litpHandler.getHypericHqServerStatus();
		return status.contains(IS_RUNNING);
	}
	
	public boolean verifyExportJson() {
		boolean status = litpHandler.exportJson("test");
		return status;
	}
	
	
//	public static void main(String[] args) {
//		Host h = RvDataProvider.getMS();
//		LitpOperator lo = new LitpOperator();
//		lo.setNode(h);
//		lo.verifyHostsFile();
//		System.out.println(lo.litpHandler.getHostName());
//		System.out.println(lo.sshToSC1());
//		System.out.println(lo.litpHandler.getHostName());
//		System.out.println("test");
//		System.out.println(lo.litpHandler.runTest());
//		
//	}


}
