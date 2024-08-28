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
package com.ericsson.nms.rv.operators.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.RestOperator;
import com.ericsson.cifwk.taf.data.*;
import com.ericsson.cifwk.taf.tal.rest.RestResponseCode;
import com.ericsson.cifwk.taf.tools.RestTool;
import com.ericsson.nms.rv.data.RvDataProvider;

public class LitpRestOperator implements RestOperator{

	/**
	 * 
	 */
	
	private static final String SSO_NAME = RvDataProvider.getSsoName();
	private static final String IDENTITY_AUTHENTICATE = RvDataProvider.getIdentityAuthenticate();
	private static final String HEIMDALLR_IDENTITY_AUTHENTICATE = SSO_NAME + IDENTITY_AUTHENTICATE; // heimdallr/identity/authenticate
	private static final String HEIMDALLR_IS_ALIVE_JSP = SSO_NAME + RvDataProvider.getPolicyAgentPage();
	private static final String REST_APPS_CIF = RvDataProvider.getRestAppUrl();
	private static final String CONTENT_TYPE_HEADER = "Content-Type";
	private static final String APPLICATION_X_ICA = RvDataProvider.getContentTypeIca();
	private static final String X_TOR_USER_ID = RvDataProvider.getXTORUserID();
	private static final Logger logger = Logger.getLogger(LitpRestOperator.class);
	
	private RestTool restTool;
	private Host node;
	
	public LitpRestOperator(Host host) {
		this.node = host;
		restTool = new RestTool(host);
	}
	

	
	public void setNode(Host host) {
		this.node = host;
		restTool = new RestTool(host);
	}
	
	
	public List<String> sendRestToApp(String appName) {
		List<String> headers = new ArrayList<String>();
		headers.add(X_TOR_USER_ID + ":" + this.node.getUser(UserType.WEB));
		headers.add(CONTENT_TYPE_HEADER + ":" + APPLICATION_X_ICA);
		logger.info("Set header: " + headers);
		restTool.setHttpHeaders(headers);
		List<String> result = restTool.get(REST_APPS_CIF + appName);
		logger.info("Get result : "  + result);
		return result;
	}
	
	public List<String> getPolicyAgentRest() {
		List<String> result = restTool.get(HEIMDALLR_IS_ALIVE_JSP);
		return result;
	}
	
	public List<String> getToken() {
		List<User> users = this.node.getUsers(UserType.WEB);
		List<String> results = new ArrayList<String>();
		for (User u : users) {
			String result = restTool.get(HEIMDALLR_IDENTITY_AUTHENTICATE + "?username=" + u.getUsername() +
					"&&password=" + u.getPassword()).get(0);
			results.add(result);
		}
		return results;
	}
	
	public Long getExecuteTime() {
		return restTool.getAverageExecutionTime();
	}
	
	public List<RestResponseCode> getLastResponseCodes() {
		return restTool.getLastResponseCodes();
	}
	
	public List<String> getLastErrorResponses() {
		return restTool.getLastErrorResponses();
	}
	
	

}
