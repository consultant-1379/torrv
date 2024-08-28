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
package com.ericsson.nms.rv.handlers;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.Handler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.implementation.SshRemoteCommandExecutor;
import com.ericsson.nms.rv.data.CmdProvider;
import com.ericsson.nms.rv.operators.api.RvApiOperator;

/**
 * Handlers used for CmwHandlers
 * @author ewandaf
 *
 */
public class CmwHandler implements Handler {
	/**
	 * command to get campaign status
	 */
	private static final String CMW_CAMPAIGN_STATUS = CmdProvider.CMW_CAMPAIGN_STATUS; // "cmw-campaign-status";
	/**
	 * command to get node status
	 */
	private static final String CMW_STATUS_NODE = CmdProvider.CMW_STATUS_NODE; //"cmw-status node";
	
	/**
	 * command to get campaigns
	 */
	private static final String CMW_REPOSITORY_LIST_CAMPAIGN = CmdProvider.CMW_REPOSITORY_LIST_CAMPAIGN; // "cmw-repository-list --campaign";
	
	static Logger log = Logger.getLogger(CmwHandler.class);
	
	private RvApiOperator remExec;
//	private SshRemoteCommandExecutor remExec = new SshRemoteCommandExecutor();
	private Host host;
	
	public CmwHandler(Host host) {
		this.host = host;
		remExec = new RvApiOperator(host);
	}
	
	public String[] getCmwCampaigns() {
		String[] campaigns = remExec.executeCmd(CMW_REPOSITORY_LIST_CAMPAIGN).split("\n");
		return campaigns;
	}
	
	public String getCmwCampaignStatus(String campaign) {
		String status = remExec.executeCmd(CMW_CAMPAIGN_STATUS + " " + campaign);
		return status;
	}
	
	public String getCmwStatusNode() {
		String result = remExec.executeCmd(CMW_STATUS_NODE);
		return result;
	}
}
