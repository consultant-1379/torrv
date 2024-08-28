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
package com.ericsson.nms.rv.operators.api;


import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.ApiOperator;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.nms.rv.getters.api.RvApiGetter;

public class RvApiOperator implements ApiOperator{

	Logger logger = Logger.getLogger(this.getClass());
	private Host rvHost;
	private boolean result;
	private String errOut;
	private String stdOut;
	
	private RvApiGetter rvApiService = new RvApiGetter().getTestedService();
	
	public RvApiOperator() {
		
	}
	
	public RvApiOperator(Host host) {
		this.rvHost = host;
	}
	
	public void setHost(Host rvHost) {
		this.rvHost = rvHost;
	}
	
	public Host getHost() {
		return this.rvHost;
	}
	
	public String executeCmd(String cmd) {
		logger.info("Sending '" + cmd + "' to " + this.rvHost.getIp() + " [ " + this.rvHost.getHostname()+ " ]");
		this.result = rvApiService.getSshRemoteComandExecutor(this.rvHost).execute(cmd);
		this.stdOut = rvApiService.getSshRemoteComandExecutor().getStdOut();
		this.errOut = rvApiService.getSshRemoteComandExecutor().getStdErr();
		if (result != false) {
			logger.info("Executed Result: <font color='green'>True</font>");
		} else {
			logger.info("Executed Result: <font color='red'>" + result +"</font>");
		}
		logger.info("Executed Result (stdOut): " + stdOut);
		logger.info("Executed Result (errOut): " + errOut);
		return stdOut;
	}
	
	public boolean getResult() {
		return this.result;
	}
	
	public String getErrOut() {
		return this.errOut;
	}
	
	public String getStdOut() {
		return this.stdOut;
	}

}
