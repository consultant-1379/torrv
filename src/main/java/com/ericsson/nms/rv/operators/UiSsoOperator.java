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
import com.ericsson.cifwk.taf.handlers.JbossHandler;
import com.ericsson.cifwk.taf.handlers.JmxHandler;
import com.ericsson.cifwk.taf.tal.rest.RestResponseCode;
import com.ericsson.nms.rv.handlers.LitpHandler;
import com.ericsson.nms.rv.operators.api.RvApiOperator;
import com.ericsson.nms.rv.operators.rest.LitpRestOperator;

public class UiSsoOperator implements GenericOperator  {
	/**
	 * 
	 */
	private static final String SERVER_IS_ALIVE = "Server is ALIVE";
	private static final String PHANTOM_SCRIPT = "/bin/sh /home/lciadm100/jenkins_slave/ui_test/TestScripts/loki/ui/run-test.sh ";
	/**
	 * 
	 */
//	private static final String SSO_NAME = RvDataProvider.getSsoName();
	/**
	 * 
	 */
//	private static final String SSO_URL = "http://sso.atrcxb2291-2.athtem.eei.ericsson.se";
	Logger logger = Logger.getLogger(this.getClass());
	private LitpHandler litpHandler;
	private LitpRestOperator restOperator;
	private RvApiOperator apiOperator;
	
	public void setNode(Host host) {
		litpHandler = new LitpHandler(host);
		restOperator = new LitpRestOperator(host);
		apiOperator = new RvApiOperator(host);
	}
	
//	public boolean verifyPolicy_Agent() {
//		String result = litpHandler.sendCurlCommand(SSO_URL + "/" + SSO_NAME + "/" + "isAlive.jsp");
//		return result.contains(SERVER_IS_ALIVE);
//	}
	
//	public List<String> executeCall() {
//		List<String> result = restOperator.executeCall();
//		return result;
//	}
	
//	public List<String> executeIsAlive {
//		String result = restOperator.executeCall().sendCurlCommand("http://sso.atrcxb2550-2.athtem.eei.ericsson.se:8080/heimdallr/isAlive.jsp");
//	}

	
	public Long getExecuteTime() {
		Long result = restOperator.getExecuteTime();
		return result;
	}
	
	public List<String> sendRestToApp(String appName) {
		return restOperator.sendRestToApp(appName);
	}
	
	public List<RestResponseCode> getLastResponseCodes() {
		return restOperator.getLastResponseCodes();
	}
	
	public boolean verifyResponseCodeIsOk() {
		RestResponseCode responseCode = restOperator.getLastResponseCodes().get(0);
		return responseCode == RestResponseCode.OK;
	}
	
	public boolean verifyPolicy_AgentRest() {
		List<String> result = restOperator.getPolicyAgentRest();
		for (String line : result) {
			if (line.contains(SERVER_IS_ALIVE)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean verifyTokenId() {
		List<String> results = restOperator.getToken();
		if (results.isEmpty()) {
			return false;
		}
		for (String result : results) {
			if (!result.contains("token.id")) {
				logger.info("Get the result: " + result);
				return false;
			}
		}
		return true;
	}
	
	public boolean loginTest() {
		apiOperator.executeCmd(PHANTOM_SCRIPT + "login");
		boolean result = apiOperator.getResult();
		return result;
	}

	public boolean favoritateTest() {
		apiOperator.executeCmd(PHANTOM_SCRIPT + "login favorites");
		boolean result = apiOperator.getResult();
		return result;
	}
	
	public boolean helpTest() {
		apiOperator.executeCmd(PHANTOM_SCRIPT + "login help");
		boolean result = apiOperator.getResult();
		return result;
	}
	
	public boolean logoutLoginTest() {
		apiOperator.executeCmd(PHANTOM_SCRIPT + "login logout_login");
		boolean result = apiOperator.getResult();
		return result;
	}
}
