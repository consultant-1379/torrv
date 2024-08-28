package com.ericsson.nms.rv.test.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ericsson.cifwk.taf.TestData;

import org.jboss.as.controller.client.helpers.domain.DeploymentSetPlan;
import org.testng.annotations.DataProvider;

import com.ericsson.cifwk.taf.data.DataHandler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.nms.rv.data.RvDataProvider;


public class RVTestDataProvider implements TestData {
	
	@DataProvider(name="LauncherHost")
	public static Object[][] LauncherHost() {
		
		//TODO - get movable
		//Object[][] activeHost = {{ ApacheServiceHandler.getActiveApache(RvDataProvider.getSC1(),RvDataProvider.getSC2()) }};
		Object[][] activeHost = {{ RvDataProvider.getSC1() }};
		return activeHost;
	}
	
	
	@DataProvider(name="ShelfControllers")
	public static Object[][] ShelfControllers() {
		Object[][] result = {{RvDataProvider.getSC1(), RvDataProvider.getSC2()}};
		return result;
	}
	
	@DataProvider(name="MS1")
	public static Object[][] MS1Host() {
		Object[][] result = {{RvDataProvider.getMS()}};
		return result;
	}
	
	@DataProvider(name="SC1Host")
	public static Object[][] SC1WebHost() {
		Object[][] result = {{RvDataProvider.getSC1()}};
		return result;
	}
	
	@DataProvider(name="SC2Host")
	public static Object[][] SC2WebHost() {
		Object[][] result = {{RvDataProvider.getSC2()}};
		return result;
	}
	
	@DataProvider(name="JBossNodes")
	public static Iterator<Object[]> JBossNodes() {
//		Object[][] result = {{RvDataProvider.getJBossNodes().get(0)}};
//		return result;
		List<Host> nodes = RvDataProvider.getJBossNodes();
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		
		for (Host node : nodes) {
			dataToBeReturned.add(new Object[] {node});
		}
		return dataToBeReturned.iterator();
	}
	
	@DataProvider(name="AllHosts")
	public static Iterator<Object[]> allLauncherHosts() {
		List<Host> hosts = RvDataProvider.getHosts();
		
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		
		for(Host host: hosts) {
			dataToBeReturned.add(new Object[] {host});
		}
		return dataToBeReturned.iterator();
	}
	
	@DataProvider(name="AppList")
	public static Iterator<Object[]> AppList() {
		List<String> appList = RvDataProvider.getAppList();
		Host host = RvDataProvider.getJBossNodes(RvDataProvider.APP_UI).get(0);
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		for(String app: appList) {
			dataToBeReturned.add(new Object[] {host,app});
		}
		
		return dataToBeReturned.iterator();
	}
	
	@DataProvider(name="SsoJboss")
	public static Object[][] SsoJboss() {
		Host nodes = RvDataProvider.getJBossNodes(RvDataProvider.APP_SSO).get(0);
		Object[][] dataToBeReturned = {{nodes}};
		return dataToBeReturned;
	}
	
	@DataProvider(name="Deployment")
	public static Iterator<Object[]> Deployment() {
		
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		List<Host> jbossNodes = RvDataProvider.getJBossNodes();
		for (Host jbossNode : jbossNodes) {
			String suName = RvDataProvider.getJbossSuName(jbossNode);
			List<String> deployments = RvDataProvider.getDeploymentFileName(suName);
			
			if (deployments.isEmpty() || deployments.get(0).trim().isEmpty()) {
				continue;
			}
			
			for (String deployment : deployments) {
				dataToBeReturned.add(new Object[] {jbossNode, deployment});
			}
		}
		return dataToBeReturned.iterator();
	}
	/**
	 * This method is for ui phantom test
	 * @return
	 */
	@DataProvider(name="JenkinsServer")
	public static Object[][] JenkinsServer() {
		Host node = RvDataProvider.getJenkinsHost();
		Object[][] dataToBeReturned = {{node}};
		return dataToBeReturned;
	}

}