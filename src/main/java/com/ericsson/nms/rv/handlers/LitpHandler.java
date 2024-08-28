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

import java.util.ArrayList;
import java.util.List;

import com.ericsson.cifwk.taf.Handler;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.implementation.SshRemoteCommandExecutor;
import com.ericsson.nms.rv.data.CmdProvider;
import com.ericsson.nms.rv.operators.api.RvApiOperator;
/**
 * Handlers used for Litp.
 * @author ewandaf
 *
 */
public class LitpHandler implements Handler{
	
	private static final String SYSTEM_INSTALLED_ON = "System Installed on";

	private static final String LOCAL_HOST = "127.0.0.1";

	private static final String SYSTEM_LAST_UPGRADED = "System last Upgraded";
	
	private static final String BUILT_FROM_MASTER = "Built from master";
	
	private static final String LITP_VERSION = "LITP Version";
	
	private static final String VERSION_FILE = "/opt/ericsson/nms/litp/.version";

	private static final String SERVICE_HYPERIC_HQ_SERVER_STATUS = CmdProvider.SERVICE_HYPERIC_HQ_SERVER_STATUS; //"service hyperic-hq-server status";

	private static final String COBBLER_LIST = CmdProvider.COBBLER_LIST; //"cobbler list";

	private static final String ETC_HOSTS = CmdProvider.ETC_HOSTS; //"/etc/hosts";
	
	private static final String SERVICE_NTPD_STATUS = CmdProvider.SERVICE_NTPD_STATUS; // "service ntpd status";

	private static final String BIN_CAT = CmdProvider.BIN_CAT; //"/bin/cat";
	
	private static final String SERVICE_COBBLERD_STATUS = CmdProvider.SERVICE_COBBLERD_STATUS; // "service cobblerd status";

	private static final String SERVICE_LANDSCAPED_STATUS = CmdProvider.SERVICE_LANDSCAPED_STATUS; // "service landscaped status";

	private static final String SBIN_LSMOD = CmdProvider.SBIN_LSMOD; // "/sbin/lsmod";

	private static final String SERVICE_PUPPETMASTER_STATUS = CmdProvider.SERVICE_PUPPETMASTER_STATUS; //"service puppetmaster status";
	
	private static final String SERVICE_PUPPET_STATUS = CmdProvider.SERVICE_PUPPET_STATUS; // "service puppet status";
	
	private static final String TIPC_CONFIG_L = CmdProvider.SBIN_TIPC_CONFIG_L; // "/sbin/tipc-config -l";
	
	private static final String SBIN_TIPC_CONFIG_V = CmdProvider.SBIN_TIPC_CONFIG_V; //"/sbin/tipc-config -V";
	
	private static final String BIN_HOSTNAME = CmdProvider.BIN_HOSTNAME; //"/bin/hostname";
	
	private static final String GETENT = CmdProvider.GETENT; // "/usr/bin/getent";
	
	private static final String SESTATUS = CmdProvider.SESTATUS; //"/usr/sbin/sestatus";
	
	private static final String SSH = CmdProvider.SSH; // "/usr/bin/ssh -o StrictHostKeyChecking=no";

	private static final String COBBLER_SYSTEM_LIST = CmdProvider.COBBLER_SYSTEM_LIST;
	
//	SshRemoteCommandExecutor remExec = new SshRemoteCommandExecutor();
	RvApiOperator remExec;
	Host host;
	
	public LitpHandler(Host host) {
		this.host = host;
		remExec= new RvApiOperator(host);
	}
	
	public String getEntries(String database, String key) {
		String returnValue = remExec.executeCmd(GETENT + " " + database + " " + key);
		return returnValue;
	}
	
	public List<String> getSestatus(String key) {
		String[] statusArray = remExec.executeCmd(SESTATUS).split("\n");
		List<String> returnList = new ArrayList<String>();
		for (String status : statusArray) {
			if (status.contains(key)) {
				returnList.add(status);
			}
		}
		return returnList;
	}
	
	public boolean sshTo(String dest) {
		remExec.executeCmd(SSH + " " + dest);
		boolean result = remExec.getResult(); //.simplExec(SSH + " " + dest);
		return result;
	}
	
	public String sshToAndExecute(String dest, String cmd) {
		String result = remExec.executeCmd(SSH + " " + dest + " " + cmd);
		return result;
	}
	
	public String getHostName() {
		String hostName = remExec.executeCmd(BIN_HOSTNAME);
		return hostName;
	}
	
	public String getTipcVersion() {
		String result = remExec.executeCmd(SBIN_TIPC_CONFIG_V);
		return result;
	}
	
	public String getTipcLink() {
		String result = remExec.executeCmd(TIPC_CONFIG_L);
		return result;
	}
	
	public String getPuppetServiceStatus() {
		String result = remExec.executeCmd(SERVICE_PUPPET_STATUS);
		return result;
	}
	
	public String getPuppetMasterServiceStatus() {
		String result = remExec.executeCmd(SERVICE_PUPPETMASTER_STATUS);
		return result;
	}
	
	public String[] getSystemModules() {
		String[] modulesArray = remExec.executeCmd(SBIN_LSMOD).split("\n");
		return modulesArray;
	}

	/**
	 * @return
	 */
	public String getLandscapedStatus() {
		String status = remExec.executeCmd(SERVICE_LANDSCAPED_STATUS);
		return status;
	}

	/**
	 * @return
	 */
	public String getCobblerStatus() {
		String status = remExec.executeCmd(SERVICE_COBBLERD_STATUS);
		return status;
	}

	/**
	 * @return
	 */
	public String getLitpVersion() {
		String[] lines = remExec.executeCmd(BIN_CAT + " " + VERSION_FILE).split("\n");
		for (String line : lines) {
			if (line.contains(LITP_VERSION)) {
				return line;
			}
		}
		return "";
	}

	/**
	 * @return
	 */
	public String getLitpBuiltDate() {
		String[] lines = remExec.executeCmd(BIN_CAT + " " + VERSION_FILE).split("\n");
		for (String line : lines) {
			if (line.contains(BUILT_FROM_MASTER)) {
				return line;
			}
		}
		return "";
	}
	
	public String getLitpLastUpgradedDate() {
		String[] lines = remExec.executeCmd(BIN_CAT + " " + VERSION_FILE).split("\n");
		for (String line : lines) {
			if (line.contains(SYSTEM_LAST_UPGRADED)) {
				return line;
			}
		}
		return "";
	}

	/**
	 * @return
	 */
	public String getNtpServiceStatus() {
		String status = remExec.executeCmd(SERVICE_NTPD_STATUS);
		return status;
	}

	/**
	 * @param sc1
	 * @return
	 */
	public String getIpFromHostsFile(String alais) {
		String[] lines = remExec.executeCmd(BIN_CAT + " " + ETC_HOSTS).split("\n");
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] ipAndAlias = line.split(" |\t");
			String ip = ipAndAlias[0].trim();
			for (int i = 1; i < ipAndAlias.length; i++) {
				if (ipAndAlias[i].trim().equals(alais) && !ip.equals(LOCAL_HOST)) {
					return ip;
				}
			}
		}
		return "";
	}
	
	public Host getHost() {
		return this.host;
	}

	/**
	 * @return
	 */
	public List<String> getCobblerList(String key) {
		String[] lines = remExec.executeCmd(COBBLER_LIST).split("\n");
		List<String> returnList = new ArrayList<String>();
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].contains(key)) {
				for (int j = i; j < lines.length; j++)
				if (j != lines.length - 1) {
					String value = lines[j+1].trim();
					if (!value.isEmpty()) {
						returnList.add(value);
					} else {
						return returnList;
					}
				}
			}
		}
		return returnList;
	}
	
	public List<String> getCobblerSystemList() {
		String[] lines = remExec.executeCmd(COBBLER_SYSTEM_LIST).split("\n");
		List<String> returnList = new ArrayList<String>();
		for (String line : lines) {
			returnList.add(line);
		}
		return returnList;
	}

	/**
	 * @return
	 */
	public String getHypericHqServerStatus() {
		String status = remExec.executeCmd(SERVICE_HYPERIC_HQ_SERVER_STATUS);
		return status;
	}

	/**
	 * @return
	 */
	public String getLitpSystemInstalledDate() {
		String[] lines = remExec.executeCmd(BIN_CAT + " " + VERSION_FILE).split("\n");
		for (String line : lines) {
			if (line.contains(SYSTEM_INSTALLED_ON)) {
				return line;
			}
		}
		return "";
	}
	
	public String sendCurlCommand(String cmd) {
		String result = remExec.executeCmd("curl " + cmd);
		return result;
	}
	
	public String readPibValue(String name, String serviceIdentifier, String appServerIdentifier) {
		String result = remExec.executeCmd("/opt/ericsson/PlatformIntegrationBridge/etc/config.py read "
				+ "--name=" + name + " --service_identifier=" + serviceIdentifier + " --app_server_identifier="
						+ appServerIdentifier );
		return result;
	}

	/**
	 * @param string
	 * @return
	 */
	public boolean exportJson(String string) {
		remExec.executeCmd("/usr/bin/litp / exportjson /tmp/" + string);
		return remExec.getResult();
	}

}
