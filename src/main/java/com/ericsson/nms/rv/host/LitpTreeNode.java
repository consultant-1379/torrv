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

import java.util.*;


public class LitpTreeNode {
	/**
	 * 
	 */
	private static final String PROPERTIES_DELIMETER = ":";
	private String path;
	private String status;
	private String checkedStatus;
	private String id;
	private String properties;
	private List<LitpTreeNode> children;
	
	
	/**
	 * @return the checkedStatus
	 */
	public String getCheckedStatus() {
		return checkedStatus;
	}

	/**
	 * @param checkedStatus the checkedStatus to set
	 */
	public void setCheckedStatus(String checkedStatus) {
		this.checkedStatus = checkedStatus;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Map<String, String> getProperties() {
		String[] propertiesArray = this.properties.split("\n");
		Map<String, String> result = new HashMap<String, String>();
		for (String p : propertiesArray) {
			String[] arr = p.split(PROPERTIES_DELIMETER);
			result.put(arr[0].trim(), arr[1].trim());
		}
		return result;
	}
	
	public String getProperties(String key) {
		Map<String, String> propertyMap = this.getProperties();
		return propertyMap.get(key);
	}
	
	public List<LitpTreeNode> getChildren() {
		return this.children;
	}
	
	public void setChildren(List<LitpTreeNode> children) {
		this.children = children;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getName() {
		String[] fields = this.path.split("/");
		return fields[fields.length - 1];
	}
	
	public List<String> getChildNames() {
		List<String> childNames = new ArrayList<String>();
		for (LitpTreeNode childNode : this.children ) {
			String name = childNode.getName();
			childNames.add(name);
		}
		return childNames;
	}
	
}
