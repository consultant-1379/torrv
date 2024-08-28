package com.ericsson.nms.rv.handlers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.Handler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.data.UserType;
import com.ericsson.cifwk.taf.handlers.implementation.SshRemoteCommandExecutor;
import com.ericsson.nms.rv.data.CmdProvider;
import com.ericsson.nms.rv.operators.api.RvApiOperator;


public class ServiceGroupHandler implements Handler {

	private static final String AMF_ADM_UNLOCK = CmdProvider.AMF_ADM_UNLOCK; //"amf-adm unlock  ";
	private static final String AMF_ADM_LOCK = CmdProvider.AMF_ADM_LOCK; //"amf-adm lock  ";
	private static final String OPT_VRT_SVCS_BIN_HASTATUS_SUM = CmdProvider.OPT_VRT_SVCS_BIN_HASTATUS_SUM; //"/opt/VRTSvcs/bin/hastatus -sum";
	private static final String AMF_FIND_SU = CmdProvider.AMF_FIND_SU; //"amf-find su";
	private static final String AMF_FIND_SI = CmdProvider.AMF_FIND_SI; // "amf-find si";
	private static final String AMF_STATE_SU_ALL = CmdProvider.AMF_STATE_SU_ALL; //"amf-state su all ";
	private static final String AMF_STATE_SI_ALL = CmdProvider.AMF_STATE_SI_ALL; // "amf-state si all ";
	private static final String PORT = CmdProvider.PORT; // "ss -ltn";
	private static final String PID_DIR = CmdProvider.PID_DIR; // "/var/run/litp-jboss";
	
	static Logger log = Logger.getLogger(ServiceGroupHandler.class);
//	SshRemoteCommandExecutor remExec = new SshRemoteCommandExecutor();
	RvApiOperator rvApiOperator;
	String hostname;
	String lastResult;
	
	public String getLastResult() {
		return lastResult;
	}

	public ServiceGroupHandler(Host host) {
		hostname=parseHostname(host.getHostname());
		log.info("Created Vcs Handler for "+hostname);
//		remExec.setHost(getAdminUserHost(host));
		rvApiOperator = new RvApiOperator(getAdminUserHost(host));
	}

	/**
	 * Get the service instance on host which is active
	 * @param identifier
	 * @return service instance name
	 */
	public String getSiActiveOnNode(String identifier) {
		String si=null;
		for(String group : listVcsGroups(identifier)) {
			if((group.contains(hostname)) && (group.contains("ONLINE"))) {
				si = group.substring(3, 10);
			}
		}
		
		if(!si.isEmpty()) {
			//Find instance name.
			String siName=listServiceInstances(si).get(0);
			log.info("Service instance:"+siName);
			return siName;
		} else {
			return null;
		}
		
		
	}


	/**
	 * Get lock status for si. 
	 * @param serviceInstance
	 * @return LOCKED|UNLOCKED|UNKNOWN
	 */
	public String getSiLockStatus(String serviceInstance) {
		String result=getSiStatus(serviceInstance);
		if(result.contains("LOCKED")) {
			log.debug(serviceInstance+" is LOCKED");
			return "LOCKED";
		} else if(result.contains("UNLOCKED")){
			log.debug(serviceInstance+" is UNLOCKED");
			return "UNLOCKED";
		} else {
			log.debug(serviceInstance+" is in unknown state");
			return "UNKNOWN";
		}
	}
	
	/**
	 * Get status for si.
	 * @param serviceInstance
	 * @return Result of amf-state command
	 */
	public String getSiStatus(String serviceInstance) {
		log.debug("Getting Si Status for "+serviceInstance);
		return rvApiOperator.executeCmd(AMF_STATE_SI_ALL+serviceInstance);
		//return remExec.simplExec(AMF_STATE_SI_ALL+serviceInstance);
	}
	
	
	/**
	 * Get lock status for su. 
	 * @param serviceUnit
	 * @return LOCKED|UNLOCKED|UNKNOWN
	 */
	public String getSuLockStatus(String serviceUnit) {
		String result=getSiStatus(serviceUnit);
		if(result.contains("LOCKED")) {
			log.debug(serviceUnit+" is LOCKED");
			return "LOCKED";
		} else if (result.contains("UNLOCKED")){
			log.debug(serviceUnit+" is UNLOCKED");
			return "UNLOCKED";
		} else {
			log.debug(serviceUnit+" is in unknown state");
			return "UNKNOWN";
		}
	}
	
	/**
	 * Get lock status for su. 
	 * @param serviceUnit
	 * @return IN-SERVICE|OUT-OF-SERVICE|UNKNOWN
	 */
	public String getSuServiceStatus(String serviceUnit) {
		String result=getSiStatus(serviceUnit);
		if(result.contains("IN-SERVICE")) {
			log.debug(serviceUnit+" is IN-SERVICE");
			return "IN-SERVICE";
		} else if (result.contains("OUT-OF-SERVICE")){
			log.debug(serviceUnit+" is OUT-OF-SERVICE");
			return "OUT-OF-SERVICE";
		} else {
			log.debug(serviceUnit+" is in unknown state");
			return "UNKNOWN";
		}
	}
	
	/**
	 * Get status for su.
	 * @param serviceUnit
	 * @return Result of amf-state command
	 */
	public String getSuStatus(String serviceUnit) {
		log.debug("Getting Su Status for "+serviceUnit);
		return rvApiOperator.executeCmd(AMF_STATE_SU_ALL+serviceUnit);
//		return remExec.simplExec(AMF_STATE_SU_ALL+serviceUnit);
	}
	
	/**
	 * Lists service instances.
	 * @return Result of amf-find si command
	 */
	public List<String> listServiceInstances() {
		log.debug("Listing Si's");
		String[] siArray= rvApiOperator.executeCmd(AMF_FIND_SI).split("\n");
//		String[] siArray= remExec.simplExec(AMF_FIND_SI).split("\n");
		return Arrays.asList(siArray);
	}
	
	/**
	 * Lists vcs service instances which contain the string identifier.
	 * @param identifier
	 * @return List of matching si's
	 */
	public List<String> listServiceInstances(String identifier) {
		List<String> siList= new ArrayList<String>();
		for(String si : listServiceInstances()) {
			if(si.contains(identifier)) {
				log.debug("Found match vcsgroup "+si);
				siList.add(si);
			}
		}
		return siList;
	}
	
	/**
	 * Lists service unit.
	 * @return Result of amf-find su command
	 */
	public List<String> listServiceUnits() {
		log.debug("Listing Su's");
		String[] suArray= rvApiOperator.executeCmd(AMF_FIND_SU).split("\n");
//		String[] suArray= remExec.simplExec(AMF_FIND_SU).split("\n");
		return Arrays.asList(suArray);
	}
	
	/**
	 * Lists vcs service unit which contain the string identifier.
	 * @param identifier
	 * @return List of matching su's
	 */
	public List<String> listServiceUnits(String identifier) {
		List<String> suList= new ArrayList<String>();
		for(String su : listServiceUnits()) {
			if(su.contains(identifier)) {
				log.debug("Found match vcsgroup "+su);
				suList.add(su);
			}
		}
		return suList;
	}
	
		
	/**
	 * Returns list of service groups.
	 * @return Result of /opt/VRTSvcs/bin/hastatus -sum command
	 */
	public List<String> listVcsGroups() {
		log.debug("Listing Groups");
		String[] sgArray= rvApiOperator.executeCmd(OPT_VRT_SVCS_BIN_HASTATUS_SUM).split("\n");
//		String[] sgArray= remExec.simplExec(OPT_VRT_SVCS_BIN_HASTATUS_SUM).split("\n");
		return Arrays.asList(sgArray);
	}
	
	/**
	 * List of vcs groups which contain the string identifier.
	 * @param identifier
	 * @return List of matching groups
	 */
	public List<String> listVcsGroups(String identifier) {
		List<String> grpList= new ArrayList<String>();
		for(String group : listVcsGroups()) {
			if(group.contains(identifier)) {
				log.debug("Found match vcsgroup "+group);
				grpList.add(group);
			}
		}
		return grpList;
	}
	
	/**
	 * Vcs Lock si
	 * @param serviceInstance
	 * @return Result of lock command.
	 */
	public boolean lockSi(String serviceInstance) {
		log.debug("LOCKING "+serviceInstance);
		String result= rvApiOperator.executeCmd(AMF_ADM_LOCK+serviceInstance);
//		String result= remExec.simplExec(AMF_ADM_LOCK+serviceInstance);
		sleep(30);
		
		return verifyResult(serviceInstance, result);
	}

	private boolean verifyResult(String serviceName, String result) {
		lastResult=result;
		if(result.contains("error")) {
			log.info("Could not lock "+serviceName+":"+result);
			return false;
		} else {
			log.info("Lock success."+serviceName+":"+result);
			return true;
		}
	}
	
	/**
	 * Vcs Lock su
	 * @param serviceUnit
	 * @return Result of lock command.
	 */
	public boolean lockSu(String serviceUnit) {
		log.debug("LOCKING "+serviceUnit);
		String result=rvApiOperator.executeCmd(AMF_ADM_LOCK+serviceUnit);
//		String result=remExec.simplExec(AMF_ADM_LOCK+serviceUnit);
		sleep(30);

		return verifyResult(serviceUnit, result);
	}
	
	private String parseHostname(String hostname2) {
		if(hostname2.equals("sc1")) {
			return "SC-1";
		} else if (hostname2.equals("sc2")) {
			return "SC-2";
		} else {
			return "UNKNOWN";
		}
	}
	
	/**
	 * Vcs unlock si
	 * @param serviceInstance
	 * @return Result of unlock command.
	 */
	public boolean unlockSi(String serviceInstance) {
		log.debug("UNLOCKING "+serviceInstance);
		String result=rvApiOperator.executeCmd(AMF_ADM_UNLOCK+serviceInstance);
//		String result=remExec.simplExec(AMF_ADM_UNLOCK+serviceInstance);
		
		return verifyResult(serviceInstance, result);
	}
	
	/**
	 * Vcs unlock su
	 * @param serviceUnit
	 * @return Result of unlock command.
	 */
	public boolean unlockSu(String serviceUnit) {
		log.debug("UNLOCKING "+serviceUnit);
		String result= rvApiOperator.executeCmd(AMF_ADM_UNLOCK+serviceUnit);
//		String result= remExec.simplExec(AMF_ADM_UNLOCK+serviceUnit);
		
		return verifyResult(serviceUnit, result);
	}
	
	private Host getAdminUserHost(Host host) {
		Host returnHost = new Host();
		
		returnHost.setIp(host.getIp());
		returnHost.setUser(host.getUser(UserType.ADMIN));
		returnHost.setPass(host.getPass(UserType.ADMIN));

		return returnHost;
	}
	
	private void sleep(int sec) {
		try {
			log.info("Sleeping for "+sec+" seconds");
			Thread.sleep(sec*1000);
		} 
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<String> getServiceGroupPort(String ip) {
		String argument = "|grep " + ip + "|sed 's/.*:\\([0-9][0-9]*\\).*/\\1/g'";
		String[] portArray= rvApiOperator.executeCmd(PORT + argument).split("\n");
//		String[] portArray= remExec.simplExec(PORT + argument).split("\n");
		log.info(ip + " opens the following port numbers: " + portArray.toString());
		return Arrays.asList(portArray);
	}
	
	public String getServiceGroupPID(String name) {
		String fileName = rvApiOperator.executeCmd("/bin/ls " + PID_DIR + "|grep " + name);
//		String fileName = remExec.simplExec("/bin/ls " + PID_DIR + "|grep " + name);
		String pid = rvApiOperator.executeCmd("/bin/cat " + PID_DIR + "/" + fileName);
//		String pid = remExec.simplExec("/bin/cat " + PID_DIR + "/" + fileName);
		return pid;
	}
	
}
