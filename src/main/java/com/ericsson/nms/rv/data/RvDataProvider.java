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
package com.ericsson.nms.rv.data;

import java.util.*;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.DataProvider;
import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.HostType;
import com.ericsson.nms.rv.operators.rest.LitpRestOperator;

public class RvDataProvider implements DataProvider{


	private static final String UI_DEPLOYMENT = "UI_DEPLOYMENT";
	private static final String SSO_DEPLOYMENT = "SSO_DEPLOYMENT";
	private static final String FMPMServ_DEPLOYMENT = "FMPMServ_DEPLOYMENT";
	private static final String MSPM0_DEPLOYMENT = "MSPM0_DEPLOYMENT";
	private static final String MSPM1_DEPLOYMENT = "MSPM1_DEPLOYMENT";
	private static final String MedCore_DEPLOYMENT = "MedCore_DEPLOYMENT";
	private static final String MSFM_DEPLOYMENT = "MSFM_DEPLOYMENT";

	private static final String JBOSS_DELIMITER = "JBOSS_DELIMITER";

	private static final String CONTENT_TYPE_ICA = "CONTENT_TYPE_ICA";

	private static final String X_TOR_USER_ID = "X_TOR_USER_ID";
/**
	 * 
	 */
	private static final String SSO_AUTHENTICATE = "SSO_AUTHENTICATE";
/**
	 * 
	 */
	private static final String REST_APPS = "REST_APPS";
	
	public static final String APP_UI = "UIServ";
	public static final String APP_SSO = "SSO";
	public static final String APP_FMPMServ = "FMPMServ";
	public static final String APP_MSPM0 = "MSPM0";
	public static final String APP_MSPM1 = "MSPM1";
	public static final String APP_MSFM = "MSFM";
	public static final String APP_MedCore = "MedCore";
	public static final String JENKINS_NAME = "jenkins";
	
	
	/**
	 * 
	 */
	private static final String POLICY_AGENT_PAGE = "POLICY_AGENT_PAGE";
	/**
	 * 
	 */
	private static final String SSO_NAME = "SSO_NAME";
	/**
	 * 
	 */
	private static final String APP_LIST_KEY = "AppList";
	/**
	 * 
	 */
	private static final String DELIMITER = ",";
	/**
	 * 
	 */
//	private static final String ACTIVE_UI_JBOSS = "Run_Rest_Ui_Jboss";
	private final static Logger logger = Logger.getLogger(RvDataProvider.class);
	public static List<Host> getHosts() {
		return DataHandler.getHosts();
	}
	
	public static Host getMS() {
		return DataHandler.getHostByType(HostType.MS);
	}
	
	public static Host getSC1() {	
		return DataHandler.getHostByType(HostType.SC1);
	}
	
	public static Host getSC2() {
		return DataHandler.getHostByType(HostType.SC2);
	}
	
	public static List<Host> getJBossNodes(String key) {
		List<Host> jbossNodes = getJBossNodes();
		List<Host> listToReturn = new ArrayList<Host>();
		for (Host h : jbossNodes) {
			if (h.getHostname().contains(key)) {
				logger.info("Get " + key + " jboss");
				listToReturn.add(h);
			}
		}
		logger.error("Cannot get " + key + " Jboss");
		return listToReturn;
	}
	
	public static String getJbossSuName(Host jbossNode) {
		String suName = jbossNode.getHostname();
		String[] paresedName = suName.split(getJbossDelimiter());
		if (paresedName.length != 2) {
			logger.error("Service unit name in properties is not as expected");
		} 
		return paresedName[0];
	}
	
	public static String getJbossSuNum(Host jbossNode) {
		String suName = jbossNode.getHostname();
		String[] paresedName = suName.split(getJbossDelimiter());
		if (paresedName.length != 2) {
			logger.error("Service unit name in properties is not as expected");
		} 
		return paresedName[1];
	}
	
	public static List<Host> getJBossNodes() {
		List<Host> returnNodes = new ArrayList<Host>();
		for (Host host : DataHandler.getHosts()) {
			List<Host> nodes = host.getNodes();
			for (Host node : nodes) {
				if (node.getType() == HostType.JBOSS) {
					returnNodes.add(node);
				}
			}
		}
		return returnNodes;
	}

	public static Host getJenkinsHost() {
		return DataHandler.getHostByName(JENKINS_NAME);
	}
	/**
	 * @return
	 */
	public static List<String> getAppList() {
		String apps = (String) DataHandler.getAttribute(APP_LIST_KEY);
		String[] appList = apps.split(DELIMITER);
		List<String> dataToReturn = new ArrayList<String>();
		for (String app : appList) {
			dataToReturn.add(app.trim());
		}
		return dataToReturn;
	}
	
	public static String getSsoName() {
		String ssoName = (String) DataHandler.getAttribute(SSO_NAME);
		return ssoName;
	}
	
	public static String getPolicyAgentPage() {
		String url = (String) DataHandler.getAttribute(POLICY_AGENT_PAGE);
		if (!url.startsWith("/")) {
			url = "/" + url;
		}
		return url;
		
	}
	
	public static String getRestAppUrl() {
		String url = (String) DataHandler.getAttribute(REST_APPS);
		if (!url.endsWith("/")) {
			url = url + "/";
		}
		return url;
	}
	
	public static String getIdentityAuthenticate() {
		String url = (String) DataHandler.getAttribute(SSO_AUTHENTICATE);
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		if (!url.startsWith("/")) {
			url = "/" + url;
		}
			
		return url;
	}
	
	public static String getXTORUserID() {
		String header = (String) DataHandler.getAttribute(X_TOR_USER_ID);
		return header;
	}
	
	public static String getContentTypeIca() {
		String header = (String) DataHandler.getAttribute(CONTENT_TYPE_ICA);
		return header;
	}
	
	public static String getJbossDelimiter() {
		String delimiter = (String) DataHandler.getAttribute(JBOSS_DELIMITER);
		return delimiter;
	}


//	public static List<String> getUiDeployment() {
//		List<String> listToBeReturned = new ArrayList<String>();
//		String result = (String) DataHandler.getAttribute(UI_DEPLOYMENT);
//		String[] deploymentArray = result.split(DELIMITER);
//		for (String deployment : deploymentArray) {
//			listToBeReturned.add(deployment.trim());
//		}
//		return listToBeReturned;
//	}
	
	public static List<String> getDeploymentFileName(String app) {
		if(app.equals(APP_UI)) {
			return getAttributeList(UI_DEPLOYMENT, DELIMITER);
		} else if (app.equals(APP_SSO)) {
			return getAttributeList(SSO_DEPLOYMENT, DELIMITER);
		} else if (app.equals(APP_FMPMServ)) {
			return getAttributeList(FMPMServ_DEPLOYMENT, DELIMITER);
		} else if (app.equals(APP_MedCore)) {
			return getAttributeList(MedCore_DEPLOYMENT, DELIMITER);
		} else if (app.equals(APP_MSPM0)) {
			return getAttributeList(MSPM0_DEPLOYMENT, DELIMITER);
		} else if (app.equals(APP_MSPM1)) {
			return getAttributeList(MSPM1_DEPLOYMENT, DELIMITER);
		} else if (app.equals(APP_MSFM)){
			return getAttributeList(MSFM_DEPLOYMENT, DELIMITER);
		} else {
			logger.info("Cannot find any deployments of app in the properties");
			return new ArrayList<String>();
		}
	}
	
	private static List<String> getAttributeList(String key, String delimiter) {
		List<String> listToBeReturned = new ArrayList<String>();
		String result = (String) DataHandler.getAttribute(key);
		String[] deploymentArray = result.split(delimiter);
		for (String deployment : deploymentArray) {
			listToBeReturned.add(deployment.trim());
		}
		return listToBeReturned;
	}

	public static Host getHostByType(HostType hostType){
		Host host = DataHandler.getHostByType(hostType);
		if (host == null) {
			logger.error("No host for " + hostType);
		}
		return host;
	}
	
//	public List<String> ntpServer() {
//		return map.get("Ntp Server");
//	}
//	
//	public List<String> getValue(String key) {
//		return map.get(key);
//	}
//
//	public List<String> ntpStatus() {
//		return map.get("Ntp Status");
//	}
//	
//	public List<String> sc1Ip() {
//		return map.get("Sc1 address");
//	}
//	
//	public List<String> sc2Ip() {
//		return map.get("Sc2 address");
//	}
//	
}
