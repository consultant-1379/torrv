package com.ericsson.nms.rv.operators;

import com.ericsson.cifwk.taf.data.Host;
/*
 * This interface is used for making the access to all contexts unified.
 */
public interface RestOperatorExampleOperatorActions {
	/*
	 * It is assumed that calling a services with credential will be available in all contexts, otherwise there would be no point to put it on interface  
	 */
	boolean executeAuthorisedCall(Host restServer, String userName, String password);
}
