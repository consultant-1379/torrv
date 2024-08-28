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

import java.util.List;

import org.testng.annotations.DataProvider;


public class RvHostDataProvider {

	@DataProvider(name="allHosts")
	public static Object[][] allHosts(){
			List<RvHost> hosts = RvHostGetter.getSelectedRvHosts();
       		Object[][] result = new Object[][]{hosts.toArray()};
       		return result;
	}
	
	@DataProvider(name="singleHosts")
	public static Object[][] singleHosts(){
			List<RvHost> rvHosts = RvHostGetter.getSelectedSingleRvHosts();
			if (rvHosts.isEmpty()) {
				return new Object[0][];
			} else {
				Object[][] result = new Object[][]{rvHosts.toArray()};
				return result;
			}
	}
	
	@DataProvider(name="multiHosts")
	public static Object[][] multiHosts(){
		List<RvHost> rvHosts = RvHostGetter.getSelectedMultiRvHosts();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}
	
	@DataProvider(name="managedNodes")
	public static Object[][] managedNodes(){
		List<RvHost> rvHosts = RvHostGetter.getAllManagedNodes();
		Object[][] result = new Object[][]{rvHosts.toArray()};
		return result;
	}
	
	@DataProvider(name="singleManagedNodes")
	public static Object[][] singleManagedNodes(){
		List<RvHost> rvHosts = RvHostGetter.getSingleManagedNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}
	
	@DataProvider(name="multiManagedNodes")
	public static Object[][] multiManagedNodes(){
		List<RvHost> rvHosts = RvHostGetter.getMultiManagedNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}
	
	@DataProvider(name="allSC1Hosts")
	public static Object[][] allSC1Hosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedSc1Nodes();
		Object[][] result = new Object[][]{rvHosts.toArray()};
		return result;
	}
	
	@DataProvider(name="allSingleSC1Hosts")
	public static Object[][] allSingleSC1Hosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedSc1SingleNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}

	@DataProvider(name="allMultiSC1Hosts")
	public static Object[][] allMultiSC1Hosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedSc1MultiNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}

	@DataProvider(name="allSC2Hosts")
	public static Object[][] allSC2Hosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedSc2Nodes();
		Object[][] result = new Object[][]{rvHosts.toArray()};
		return result;
	}
	
	@DataProvider(name="allSingleSC2Hosts")
	public static Object[][] allSingleSC2Hosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedSc2SingleNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}

	@DataProvider(name="allMultiSC2Hosts")
	public static Object[][] allMultiSC2Hosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedSc2MultiNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}

	@DataProvider(name="allPlHosts")
	public static Object[][] allPlHosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedPlNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}
	
	@DataProvider(name="allSinglePLHosts")
	public static Object[][] allSinglePlHosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedPlSingleNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}
	
	@DataProvider(name="allMultiPlHosts")
	public static Object[][] allMultiPlHosts() {
		List<RvHost> rvHosts = RvHostGetter.getSelectedPlMultiNodes();
		if (rvHosts.isEmpty()) {
			return new Object[0][];
		} else {
			Object[][] result = new Object[][]{rvHosts.toArray()};
			return result;
		}
	}
	
	
//	public static void main(String[] args) {
//		List<RvHost> hosts = RvHostGetter.getSelectedRvHosts();
//		List<RvHost> allSC1 = new ArrayList<RvHost>();
//	}
}
