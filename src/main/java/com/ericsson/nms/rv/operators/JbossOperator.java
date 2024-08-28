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

import groovy.util.GroovyMBean;

import java.util.*;

import org.apache.log4j.Logger;
//import org.jfree.util.Log;



import com.ericsson.cifwk.taf.GenericOperator;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.*;
import com.ericsson.nms.rv.data.RvDataProvider;
import com.ericsson.nms.rv.handlers.ServiceGroupHandler;
/**
 * General operators used for JBoss-related test cases.
 * @author ewandaf
 *
 */
public class JbossOperator implements GenericOperator {
	 

	Logger logger = Logger.getLogger(this.getClass());

	private static String expectedSuAdminStateUnLock ="saAmfSUAdminState=UNLOCKED";
	private static String expectedSuPresStateUnLock ="saAmfSUPresenceState=INSTANTIATED";
	private static String expectedSuReadinessUnLock ="saAmfSUReadinessState=IN-SERVICE";
	private static String expectedSuAdminStateLock = "saAmfSUAdminState=LOCKED";
	private static String expectedSuPresStateLock = "saAmfSUPresenceState=UNINSTANTIATED";
	private static String expectedSuReadinessLock = "saAmfSUReadinessState=OUT-OF-SERVICE";
	private static String expectedSiAdminStateUnLock = "saAmfSIAdminState=UNLOCKED";
	private static String expectedSiAdminStateLock = "saAmfSIAdminState=LOCKED";
	private static final String ONLINE = "ONLINE";
	private static final String MANAGEMENT_PORT = "9999";
	private static final String JBOSS_HOME="C:\\software\\jboss";
	
	private JbossHandler jbossHandler;
	private ServiceGroupHandler sgHandler;
	private Host node;
	private Host litpHost;
	private JmxHandler jmxService;

	
	
	
	public void setNode(Host node) {
		this.node = node;
		this.litpHost = this.getLitpHost();
		jbossHandler = new JbossHandler(node, this.litpHost);
		sgHandler = new ServiceGroupHandler(this.litpHost);
		jmxService = new JmxHandler(node);
	}
	
	private Host getLitpHost() {
		 List<Host> allHosts = DataHandler.getHosts();
	        for (Host host : allHosts){
	            for (Host node : host.getNodes()){
	                if (node.getIp().equals(this.node.getIp())){
	                    logger.debug("Got LITP server " + host);
	                    return host;
	                }
	            }
	        }
	      logger.error("No LITP host with JBOSS nodes found");
	      return null;
	}

	public boolean verifyServiceUnit() {
		List<String> suList = sgHandler.listServiceUnits();
		logger.info("Get su list: " + suList);
		String suName = RvDataProvider.getJbossSuName(this.node);
		String suNum = RvDataProvider.getJbossSuNum(this.node);
		
		for (String su : suList) {
			if (su.contains(suName) && su.contains(suNum)) {
				return true;
			}
		}
		return false;
	}
	

	
	public boolean verifyServiceInstance() {
		List<String> siList = sgHandler.listServiceInstances();
		logger.info("Get si list: " + siList);
		String suName = RvDataProvider.getJbossSuName(this.node);
		for (String si : siList) {
			if (si.contains(suName)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyServiceUnitState() {
		String suName = RvDataProvider.getJbossSuName(this.node);
		List<String> suList = sgHandler.listServiceUnits(suName);
		for (String su : suList) {
			String suState = sgHandler.getSuStatus(su);
			logger.info("Get state of " + su + ": " + suState);
			if (suState.contains(expectedSuAdminStateUnLock) && suState.contains(expectedSuPresStateUnLock) && suState.contains(expectedSuReadinessUnLock) 
					|| suState.contains(expectedSuAdminStateLock) && suState.contains(expectedSuPresStateLock) && suState.contains(expectedSuReadinessLock)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyServiceInstanceState() {
		String suName = RvDataProvider.getJbossSuName(this.node);
		List<String> siList = sgHandler.listServiceInstances(suName);
		for (String si : siList) {
			String siState = sgHandler.getSiStatus(si);
			logger.info("Get state of " + si + ": " + siState);
			if (siState.contains(expectedSiAdminStateUnLock)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyJbossManagementPort() {
		List<String> portList = sgHandler.getServiceGroupPort(this.node.getIp());
		logger.info("Expected port: " + MANAGEMENT_PORT);
		logger.info("Real port: " + portList);
		return portList.contains(MANAGEMENT_PORT);
	}
	
	public boolean verifyJbossInstancePid() {
		String suName = RvDataProvider.getJbossSuName(this.node);
		String pid = sgHandler.getServiceGroupPID(suName);
		if (!pid.isEmpty()) {
			logger.info("The pid is " + pid);
			return true;
		} else {
			logger.error("The pid is empty");
			return false;
		}
	}
	
	public boolean verifyHaStatusOnline() {
		String suName = RvDataProvider.getJbossSuName(this.node);
		String hostName = this.litpHost.getHostname();
		List<String> serviceGroups = sgHandler.listVcsGroups(suName);
		for (String group : serviceGroups) {
			if (group.contains(suName) 
					&& group.contains(hostName) 
					&& group.contains(ONLINE)){
				return true;
			}
		}
		return false;
	}
	
	public String getJbossHomeDir() {
		return (String) jmxService.getMBean("jboss.as:path=jboss.home.dir").getProperty("path");
	}
	
	public String getJbossLogDir() {
		return (String) jmxService.getMBean("jboss.as:path=jboss.server.log.dir").getProperty("path");
	}
	

	/**
	 * @param deployment
	 * @return
	 */
	public boolean verifyDeployment(String deploymentFile) {
		List<Object> result = getDeploymentFileStatus(deploymentFile);
		boolean isEnabled = (boolean)result.get(0);
		boolean isPersistent = (boolean)result.get(1);
		String status = (String)result.get(2);
		boolean isOk = status.equals("OK");
		return isEnabled && isPersistent && isOk;

	}
	
	private List<Object> getDeploymentFileStatus(String deploymentFile) {
		List<Object> result = (List<Object>) jbossHandler.deploymentStates(deploymentFile);
		return result;
	}
	
//	private List<String> getDeploymentInfo(String deploymentFile) {
//		List<String> listToReturn = new ArrayList<String>();
//		try {
////			List<Object> result = (List<Object>) jbossHandler.deploymentStates(deploymentFile);
//			String[] result = jbossCmdService.simplExec("deployment-info --name=" + deploymentFile).split("\n");
//			String[] info = result[1].split(" ");
//			for (String i : info) {
//				String item = i.trim();
//				if (!item.isEmpty()) {
//					listToReturn.add(i.trim());
//				}
//			}
//		} catch (Exception e) {
//			logger.error("Deployment info is wrong! Pleace manually run the deployment-info --name=" + deploymentFile );
//		}
//		return listToReturn;
//	}
	
//	public boolean verifyAmfSu(List<RvHost> jboss) {
//		if (jboss.isEmpty()) {
//			logger.error("There is no Jboss in properties file.");
//			return false;
//		}
//		boolean result = true;
//		for (RvHost j : jboss) {
//			result = result && jbossApiOperator.verifyAmfSu(j);
//		}
//		return result;
//	}
	
	
	/*
	public boolean verifyAmfSi(List<RvHost> jboss) {
		if (jboss.isEmpty()) {
			logger.error("There is no Jboss in properties file.");
			return false;
		}
		boolean result = true;
		for (RvHost j : jboss) {
			result = result && jbossApiOperator.verifyAmfSi(j);
		}
		return result;
	}
	

	
	public String jbossExecuteCmd(RvHost jboss, String cmd) {
		return jbossApiOperator.jbossExecuteCmd(jboss, cmd);
	}
	
	public boolean verifyJbossInstancePort(List<RvHost> jboss) {
		if (jboss.isEmpty()) {
			logger.error("There is no Jboss in properties file.");
			return false;
		}
		boolean result = true;
		for (RvHost j : jboss) {
			result = result && jbossApiOperator.verifyJbossInstanceManagementPort(j);
		}
		return result;
	}
	
	
	public boolean verifyJbossInstancePid(List<String> pid) {
		if (pid.isEmpty()) {
			logger.error("There is no jboss PID.");
			return false;
		}
		boolean result = true;
		for (String id : pid) {
			result= result && jbossApiOperator.verifyJbossInstanceManagementPid(id);
		}
		return result;
	}
	



	*//**
	 * @param jbossInstances
	 * @return
	 *//*
	public boolean verifyAmfSiLockAndUnLock(List<RvHost> jbossInstances) {
		if (jbossInstances.isEmpty()) {
			logger.error("There is no jbossInstance");
			return false;
		}
		boolean result = true;
		for (RvHost j : jbossInstances) {
			String[] entries = getAmfSiEntryByJbossInstanceName(j);
			for (String entry : entries) {
				entry = entry.trim();
				logger.trace("Locking....." + entry);
				this.lockAmfSiSu(entry);
				String state = this.getSuState(entry);
				logger.trace("State of " + entry + "after locking");
				result = result && state.contains("saAmfSIAdminState=LOCKED") && state.contains("saAmfSIAssignmentState=UNASSIGNED");
				logger.trace("Unlocking....." + entry);
				this.unLockAmfSiSu(entry);
				state = this.getSuState(entry);
				logger.trace("State of " + entry + "after unlocking");
				result = result && state.contains("saAmfSIAdminState=UNLOCKED") && state.contains("saAmfSIAssignmentState=FULLY_ASSIGNED");
			}
		}
		return result;
	}
	
	*//**
	 * @param entry
	 * @return
	 *//*
	private String getSuState(String entry) {
		String cmd = RvCmdKeyDataProvider.AmfSiState.toCmd() + "|grep -A 4 " + entry;
		return this.executeCmd(cmd);
	}

	private void lockAmfSiSu(String entry) {
		String cmd = RvCmdKeyDataProvider.Amfadm.toCmd() + " lock " + entry;
		this.executeCmd(cmd);
	}
	
	private void unLockAmfSiSu(String entry) {
		String cmd = RvCmdKeyDataProvider.Amfadm.toCmd() + " unlock " + entry;
		this.executeCmd(cmd);
	}
	
	private String getSiState(String entry) {
		String cmd = RvCmdKeyDataProvider.AmfSiState.toCmd() + "|grep -A 2 " + entry;
		return this.executeCmd(cmd);
	}
	
	private String[] getAmfSiEntryByJbossInstanceName(RvHost jboss) {
		String cmd = RvCmdKeyDataProvider.AmfSiState.toCmd() + "|grep " + jboss.getHostname();
		String[] result = cmd.split("\n");
		return result;
	}

	*//**
	 * @param jbossInstances
	 * @return
	 *//*
	public boolean verifyAmfSuLockAndUnLock(List<RvHost> jbossInstances) {
		if (jbossInstances.isEmpty()) {
			logger.error("There is no jbossInstance");
			return false;
		}
		boolean result = true;
		for (RvHost j : jbossInstances) {
			String[] entries = getAmfSuEntryByJbossInstanceName(j);
			for (String entry : entries) {
				entry = entry.trim();
				logger.trace("Locking....." + entry);
				this.lockAmfSiSu(entry);
				String state = this.getSiState(entry);
				logger.trace("State of " + entry + "after locking");
				result = result && state.contains("saAmfSUAdminState=LOCKED") 
						&& state.contains("saAmfSUOperState=ENABLED")&&
						state.contains("saAmfSUPresenceState=UNINSTANTIATED") &&
						state.contains("saAmfSUReadinessState=OUT-OF-SERVICE");
				logger.trace("Unlocking....." + entry);
				this.unLockAmfSiSu(entry);
				state = this.getSiState(entry);
				logger.trace("State of " + entry + "after unlocking");
				result = result && state.contains("saAmfSUAdminState=UNLOCKED") 
						&& state.contains("saAmfSUOperState=ENABLED")&&
						state.contains("saAmfSUPresenceState=INSTANTIATED") &&
						state.contains("saAmfSUReadinessState=IN-SERVICE")
						;
				
			}
		}
		return result;
	}

	*//**
	 * @param j
	 * @return
	 *//*
	private String[] getAmfSuEntryByJbossInstanceName(RvHost jboss) {
		String cmd = RvCmdKeyDataProvider.AmfSuState.toCmd() + "|grep " + jboss.getHostname();
		String[] result = cmd.split("\n");
		return result;
	}
	*/
	
//	public static void main(String[] args) {
//		Host node = RvDataProvider.getUiJbossNode();
//		JbossOperator jo = new JbossOperator();
//		jo.setNode(node);
////		List<String> r = jo.jmxService.getMBeansList();
////		System.out.println(r);
////		GroovyMBean mb = jo.jmxService.getMBean("java.lang:type=OperatingSystem");
////		int n = (int) mb.getProperty("AvailableProcessors");
////		String home = jo.getJbossHomeDir();
////		
////		System.out.println(home);
////		System.out.println(n);
//		
//		jo.verifyDeployment("PlatformIntegrationBridge-ear-1.4.2.ear");
//	}
//	


}
