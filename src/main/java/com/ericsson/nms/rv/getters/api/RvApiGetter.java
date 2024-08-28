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
package com.ericsson.nms.rv.getters.api;

import com.ericsson.cifwk.taf.ApiGetter;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.implementation.SshRemoteCommandExecutor;

public class RvApiGetter implements ApiGetter{

	private static SshRemoteCommandExecutor sshExecutor;
	
	public SshRemoteCommandExecutor getSshRemoteComandExecutor(Host host) {
		sshExecutor = new SshRemoteCommandExecutor(host);
		return sshExecutor;
	}
	
	public SshRemoteCommandExecutor getSshRemoteComandExecutor() {
		return sshExecutor;
	}
	
	public RvApiGetter getTestedService(){
		return this;
	}
	
//	public static void main(String[] args) {
//		RvApiGetter get = new RvApiGetter();
//		RvHost host = RvHostGetter.getSelectedSc1Nodes().get(0);
//		SshRemoteCommandExecutor exe = get.getSshRemoteComandExecutor(host);
//		boolean result = exe.execute("ps -ef|grep 10.45.236.86| grep 'Djboss.home.dir'|sed 's/.*jboss\\.home\\.dir=\\([^ ]*\\).*/\\1/' | grep -v bash");
//		System.out.println(exe.getStdOut());
//	
//		
//	}
}
