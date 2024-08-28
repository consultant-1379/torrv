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

import java.util.*;

import com.ericsson.cifwk.taf.Handler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.implementation.SshRemoteCommandExecutor;
import com.ericsson.nms.rv.operators.api.RvApiOperator;
/**
 * 
 * A class file to handler litp.
 * @author ewandaf
 *
 */
public class LitpDeployModelHandler implements Handler {
	
	/**
	 * 
	 */
	private static final String UPDATE = "update";
	/**
	 * 
	 */
	private static final int ID_LINE_NUM = 3;
	/**
	 * 
	 */
	private static final int CHECKED_STATUS_LINE_NUM = 2;
	/**
	 * 
	 */
	private static final String DELIMETER = ":";
	/**
	 * 
	 */
	
	private static final int PROPERTY_LINE_NUM = 4;
	private static final int STATUS_LINE_NUM = 1;
	/**
	 * 
	 */
	private static final String SHOW = "show";
	/**
	 * 
	 */
	private static final String LITP = "/usr/bin/litp";
//	private SshRemoteCommandExecutor remExec = new SshRemoteCommandExecutor();
	private RvApiOperator remExec;
	private Host host;
	private String path;
	
	public void setMSHost(Host host) {
		this.host = host;
		remExec = new RvApiOperator(host);
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	
//	private LitpTreeNode getLitpTreeNode() {
//		LitpTreeNode litpObject = new LitpTreeNode();
//		litpObject.setPath(this.path);
//		return litpObject;
//	}
	
	

	public String getStatus() {
		List<String> showResult = show(this.path);
		String[] result = showResult.get(STATUS_LINE_NUM).split(DELIMETER);
		return result[1].trim();
	}
	
	
	public String getCheckedStatus() {
		List<String> showResult = show(this.path);
		String[] result = showResult.get(CHECKED_STATUS_LINE_NUM).split(DELIMETER);
		return result[1].trim();
	}
	
	public String getId() {
		List<String> showResult = show(this.path);
		String[] result = showResult.get(ID_LINE_NUM).split(DELIMETER);
		return result[1].trim();
	}
	

	public String getProperty(String key) {
		return getPropertyMap().get(key);
	}

	
	public boolean updateProperty(String key, String value) {
		remExec.executeCmd(LITP+" "+this.path+" "+UPDATE+" "+key+"="+value);
		boolean result = remExec.getResult();
		return result;
	}
	
	private Map<String, String> getPropertyMap() {
		Map<String, String> propertyMap = new HashMap<String,String>();
		List<String> showResult = show(this.path);
		for (int i = PROPERTY_LINE_NUM + 1; i < showResult.size(); i++) {
			String[] keyAndValue = showResult.get(i).split(DELIMETER);
			propertyMap.put(keyAndValue[0].trim(), keyAndValue[1].trim());
		}
		return propertyMap;
	}

	private List<String> show(String path) {
		String[] lines = remExec.executeCmd(LITP + " " + path + " " + SHOW).trim().split("\n");
		List<String> returnList = new ArrayList<String>();
		for (String line : lines) {
			returnList.add(line.trim());
		}
		return returnList;
	}
	
	
//	public static void main(String[] args) {
//		Host ms = RvDataProvider.getMS();
//		LitpDeployModelHandler h = new LitpDeployModelHandler();
//		h.setMSHost(ms);
//		h.setPath("/inventory/deployment1/sfs/");
//		System.out.println(h.getId());
//		System.out.println(h.getProperty("admin_user"));
//	}
}
