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

import com.ericsson.cifwk.taf.data.*;

/**
 * This class is in Getter layer. It provide host information.
 * @author ewandaf
 *
 */
public class RvHostGetter {
	private static List<Host> hosts;
	private static List<RvHost> rvHosts;
	private static List<RvHost> rvSelectedHosts;
	private static String selectedHostsKey = "selectedHosts";
	private static String valueDelimiter = ","; // selectedHosts supports multiple hosts, separated by valueDelimiter
	private static String deploymentTypeKey = "deploymentType"; // if taf supports deployment type, it'll be deleted.
	
	static {
		hosts = DataHandler.getHosts();
		rvHosts = getRvHosts();
		rvSelectedHosts = getSelectedRvHosts();
	}
	
	/**
	 * Read property file to get the selected host name and then 
	 * returns one list of host whose name is among the selected host name
	 * @return one list of RvHost
	 */
	public static List<RvHost> getSelectedRvHosts() {
		List<String> hostsName = getSelectedHostName();
		List<RvHost> selectedRvHosts = new ArrayList<RvHost>();
		for (String name : hostsName) {
			for (RvHost h : rvHosts) {
				if (h.getHostname().startsWith(name)) {
					// at the moment, Taf host doesn't provide deployment type
					// if Taf host provides deployment type, the following two lines
					// code can be deleted.
					RvHostDeploymentType systemType = getDeploymentType(name);
					h.setDeploymentType(systemType);
					selectedRvHosts.add(h);
					break;
				}
			}
		}
		return selectedRvHosts;
	}
	
	public static List<RvHost> getSelectedSingleRvHosts() {
		List<RvHost> selectedSingleHosts = new ArrayList<RvHost>();
		for (RvHost host : rvSelectedHosts) {
			if (host.getDeploymentType() == RvHostDeploymentType.SINGLE) {
				selectedSingleHosts.add(host);
			}
		}
		return selectedSingleHosts;
	}

	
	public static List<RvHost> getSelectedMultiRvHosts() {
		List<RvHost> selectedMultiHosts = new ArrayList<RvHost>();
		for (RvHost host : rvSelectedHosts) {
			if (host.getDeploymentType() == RvHostDeploymentType.MULTI) {
				selectedMultiHosts.add(host);
			}
		}
		return selectedMultiHosts;
	}
	
	public static List<RvHost> getAllManagedNodes() {
		return getSelectedRvHostNodes();
	}
	
	public static List<RvHost> getSingleManagedNodes() {
		return getSelectedRvHostNodes(RvHostDeploymentType.SINGLE);
	}

	public static List<RvHost> getMultiManagedNodes() {
		return getSelectedRvHostNodes(RvHostDeploymentType.MULTI);
	}

	public static List<RvHost> getSelectedSc1Nodes() {
		return getSelectedRvHostNodes(HostType.SC1);
	}
	
	public static List<RvHost> getSelectedSc2Nodes() {
		return getSelectedRvHostNodes(HostType.SC2);
	}
	
	
	public static List<RvHost> getSelectedPlNodes() {
		return getSelectedRvHostNodes(HostType.PL);
	}
	
	public static List<RvHost> getSelectedSc1SingleNodes() {
		return getSelectedRvHostNodes(HostType.SC1, RvHostDeploymentType.SINGLE);
	}
	
	public static List<RvHost> getSelectedSc2SingleNodes() {
		return getSelectedRvHostNodes(HostType.SC2, RvHostDeploymentType.SINGLE);
	}
	
	public static List<RvHost> getSelectedSc1MultiNodes() {
		return getSelectedRvHostNodes(HostType.SC1, RvHostDeploymentType.MULTI);
	}
	
	public static List<RvHost> getSelectedSc2MultiNodes() {
		return getSelectedRvHostNodes(HostType.SC2, RvHostDeploymentType.MULTI);
	}
	
	public static List<RvHost> getSelectedPlSingleNodes() {
		return getSelectedRvHostNodes(HostType.PL, RvHostDeploymentType.SINGLE);
	}
	
	public static List<RvHost> getSelectedPlMultiNodes() {
		return getSelectedRvHostNodes(HostType.PL, RvHostDeploymentType.MULTI);
	}
	
	private static List<RvHost> getSelectedRvHostNodes() {
		List<RvHost> result = new ArrayList<RvHost>();
		for (RvHost rvHost : rvSelectedHosts) {
			result = rvHost.getRvNodes();
		}
		return result;
	}
	
	private static List<RvHost> getSelectedRvHostNodes(RvHostDeploymentType type) {
		List<RvHost> rvSelectedHostNodes = new ArrayList<RvHost>();
		for (RvHost rvHost : rvSelectedHosts) {
			if (rvHost.getDeploymentType() == type) {
				rvSelectedHostNodes = rvHost.getRvNodes();
			}
		}
		return rvSelectedHostNodes;
	}
	
	private static List<RvHost> getSelectedRvHostNodes(HostType type) {
		List<RvHost> result = new ArrayList<RvHost>();
		for (RvHost rvHost : rvSelectedHosts) {
			List<RvHost> hs = getRvHostNodes(rvHost, type);
			for (RvHost h : hs) {
				result.add(h);
			}
		}
		return result;
	}

	private static List<RvHost> getSelectedRvHostNodes(HostType hostType, RvHostDeploymentType deploymentType) {
		List<RvHost> result = new ArrayList<RvHost>();
		for (RvHost rvHost : rvSelectedHosts) {
			result = getRvHostNodes(rvHost, hostType, deploymentType);
		}
		return result;
	}
	
	
	private static List<RvHost> getRvHostNodes(RvHost ms, HostType type, RvHostDeploymentType deploymentType) {
		List<RvHost> result = new ArrayList<RvHost>();
		if (ms.getDeploymentType() == deploymentType) {
			result = getRvHostNodes(ms, type);
		}
		return result;
	}
	
	
	/**
	 * This method only converts Host in TAF to RvHost in RV team.
	 * Because Host in TAF team doens't have deployment type. 
	 * RvHost provides deployment type.
	 * @return all RvHost recorded in properties file.
	 */
	private static List<RvHost> getRvHosts() {
		List<RvHost> rvHosts = new ArrayList<RvHost>();
		for (Host host : hosts) {
			RvHost rvHost = new RvHost(host);
			rvHosts.add(rvHost);
		}
		return rvHosts;
	}



//	/**
//	 * This method is to turn Host in TAF to RvHost.
//	 * If TAF provides deployment type in Host, this method can be deleted.
//	 * @param host RvHost who has child hosts.
//	 * @return the child hosts of RvHost passed in this method.
//	 */
//	private static List<RvHost> getRvHostNodes(RvHost host) {
//		List<Host> tafHostNodes = host.getNodes();
//		List<RvHost> rvHostNodes = new ArrayList<RvHost>();
//		for (Host node : tafHostNodes) {
//			RvHost rvHostNode = new RvHost(node);
//			rvHostNode.setDeploymentType(host.getDeploymentType());
//			rvHostNode.setParentHost(host);
//			rvHostNodes.add(rvHostNode);
//		}
//		return rvHostNodes;
//	}

	private static List<RvHost> getRvHostNodes(RvHost ms, HostType hostType) {
		List<RvHost> nodes = new ArrayList<RvHost>();
		if (ms.getType() == HostType.MS) {
			List<RvHost> allNodes = ms.getRvNodes();
			for (RvHost node : allNodes) {
				HostType nodeType = node.getType();
				if (nodeType == hostType) {
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	private static RvHostDeploymentType getDeploymentType(String hostName) {
		String systemType = getPropertyValue(hostName + "." + deploymentTypeKey);
		if (systemType.equals(RvHostDeploymentType.SINGLE.toString())) {
			return RvHostDeploymentType.SINGLE;
		} else {
			return RvHostDeploymentType.MULTI;
		}
	}

	/**
	 * Get host name specified in property file by <code>selectedHostsKey</code>("selectedHosts") key.
	 * selectedHosts can be more than one host. Using <code>valueDelimiter</code>(",") separates multiple hosts.
	 * @return host name specified by selectedHosts key
	 */
	private static List<String> getSelectedHostName() {
		String selectedHosts = getPropertyValue(selectedHostsKey);
		String[] hosts = selectedHosts.split(valueDelimiter);
		List<String> listHosts = new ArrayList<String>();
		for (String h : hosts) {
			listHosts.add(h);
		}
		return listHosts;
	}


	/**
	 * Given a key and return property value of this key
	 * @param key 
	 * @return
	 */
	private static String getPropertyValue(String key) {
		String value = DataHandler.getAttributes().get(key).toString();
		return value;
	}


	/**
	 * Returns all hosts recorded in property file.(at the moment this is not used because of lack of deployment type in TAF Host)
	 * @return all hosts mentioned in property file.
	 * @see Host
	 */
	private static List<Host> getHosts() {
		return hosts;
	}

	/**
	 * Give one host type declared in <code>HostType</code> then it returns a list of this type of hosts mentioned
	 * in property file.
	 * (at the moment this is not used because of lack of deployment type in TAF Host)
	 * @param hostType declared in <code>HostType</code>
	 * @return specific type of hosts
	 * @see Host
	 * @see HostType
	 */
	private static List<Host> getHosts(HostType hostType) {
		List<Host> result = new ArrayList<Host>();
		for (Host h : hosts) {
			if (h.getType() == hostType) {
				result.add(h);
			}
		}
		return result;
	}
	
	/**
	 * Get hosts specified in property file by <code>selectedHostsKey</code>("selectedHosts") key.
	 * selectedHosts can be more than one host. Using <code>valueDelimiter</code>(",") separates multiple hosts.
	 * (at the moment this is not used because of lack of deployment type in TAF Host)
	 * @return a host list contains all the selected hosts
	 */
	private static List<Host> getSelectedHosts() {
		List<String> hostsName = getSelectedHostName();
		List<Host> selectedHosts = new ArrayList<Host>();
		for (String name : hostsName) {
			for (Host h : hosts) {
				if (h.getHostname().startsWith(name)) {
					selectedHosts.add(h);
					break;
				}
			}
		}
		return selectedHosts;
	}
	
	public static void main(String[] args) {
		//System.out.print(getSelectedHosts());
		System.out.print(getSelectedRvHosts());
		System.out.print(getSelectedSc1MultiNodes().get(0).getHostname());
	}
}
