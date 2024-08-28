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
package com.ericsson.nms.rv.host;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.User;

public class RvHost extends Host{
	private Logger logger = Logger.getLogger(this.getClass());
	private RvHostDeploymentType deploymentType;
	private RvHost parentHost;
	
	public RvHost() {
		super();
	}
	
	public RvHost(Host host) {
		this.setHostname(host.getHostname());
		this.setIp(host.getIp());
		this.setNodes(host.getNodes());
		this.setOffset(host.getOffset());
		this.setUser(host.getUsers());
		this.setPass(host.getPass());
		this.setPort(host.getPort());
		this.setType(host.getType());
	}
	
	public RvHost(Host host, RvHostDeploymentType deploymentType) {
		this(host);
		this.setDeploymentType(deploymentType);
	}
	

	public List<RvHost> getRvNodes() {
		List<Host> nodes = super.getNodes();
		List<RvHost> rvNodes = new ArrayList<RvHost>();
		for (Host node : nodes ) {
			RvHost rvNode = new RvHost(node);
			logger.trace("Set parent node of " + node.getHostname() + " to " + this.getHostname());
			rvNode.setParentHost(this);
			rvNode.setDeploymentType(this.deploymentType);
			rvNodes.add(rvNode);
		}
		return rvNodes;
	}
	/**
	 * @param hostName
	 * @param ip
	 * @param user
	 * @param pass
	 * @param type
	 */
	public RvHost(String hostname, String ip, String user, String pass,
			RvHostDeploymentType type) {
		super.setHostname(hostname);
		super.setIp(ip);
		super.setUser(user);
		super.setPass(pass);
		this.deploymentType = type;
	}
	
	/**
	 * @param hostName
	 * @param ip
	 * @param user
	 * @param pass
	 * @param type
	 * @param parentHost
	 */
	public RvHost(String hostname, String ip, String user, String pass,
			RvHostDeploymentType type, RvHost parentHost) {
		super.setHostname(hostname);
		super.setIp(ip);
		super.setUser(user);
		super.setPass(pass);
		this.deploymentType = type;
		this.parentHost = parentHost;
	}
	
	/**
	 * @return the hostName
	 */
	public String getHostname() {
		return super.getHostname();
	}

	/**
	 * @param hostName the hostName to set
	 */
	public void setHostname(String hostname) {
		super.setHostname(hostname);
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return super.getIp();
	}
	

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		super.setIp(ip);
	}
	
	/**
	 * @return the user
	 */
	public String getUser() {
		return super.getUser();
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(List<User> user) {
		super.setUsers(user);
	}

	/**
	 * @return the pass
	 */
	public String getPass() {
		return super.getPass();
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		super.setPass(pass);
	}

	/**
	 * @return the type
	 */
	public RvHostDeploymentType getDeploymentType() {
		return this.deploymentType;
	}

	/**
	 * @param set the type to set
	 */
	public void setDeploymentType(RvHostDeploymentType type) {
		logger.trace("Set " + this.getHostname() + " Deplployment Type to " + deploymentType);
		this.deploymentType = type;
	}
	
	/**
	 * @return the ParentHost
	 */
	public RvHost getParentHost() {
		return this.parentHost;
	}

	/**
	 * @param set the ParentHost to set
	 */
	public void setParentHost(RvHost parentHost) {
		this.parentHost = parentHost;
	}

	public RvHost getRoot() {
		RvHost result = this;
		RvHost p = this.parentHost;
		if (p != null) {
			result = p.getRoot();
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "RvHost [hostname=" + getHostname() + ", ip=" + getIp() + ", user="
				+ getUser() + ", pass=" + getPass() + ", systemType=" + getDeploymentType() + "]";
	}
	
	
	public static void main(String[] args) {
		
//		System.out.println(RvHostGetter.getSelectedRvHosts().get(0).getNodes());
//		System.out.println(RvHostGetter.getSelectedRvHosts().get(0).getNodes().get(0).getNodes().get(0).getHostname());
	}
	
}

