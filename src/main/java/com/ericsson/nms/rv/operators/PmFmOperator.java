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
package com.ericsson.nms.rv.operators;

import java.util.List;

import org.apache.log4j.Logger;

import com.ericsson.cifwk.taf.GenericOperator;
import com.ericsson.cifwk.taf.data.Host;
import com.ericsson.cifwk.taf.handlers.JbossHandler;
import com.ericsson.cifwk.taf.handlers.JmxHandler;
import com.ericsson.cifwk.taf.tal.rest.RestResponseCode;
import com.ericsson.nms.rv.handlers.LitpHandler;
import com.ericsson.nms.rv.handlers.ServiceGroupHandler;

public class PmFmOperator implements GenericOperator {
	Logger logger = Logger.getLogger(this.getClass());
	private LitpHandler litpHandler;
	
	public void setNode(Host host) {
		litpHandler = new LitpHandler(host);
	}
	
	public String getFmSu0MediationConsumerChannelId() {
		return litpHandler.readPibValue("mediation_service_consumer_channelId", 
				"mediationservice", "MSFM_su_0_jee_instance");
	}
	
	public String getFmSu1MediationConsumerChannelId() {
		return litpHandler.readPibValue("mediation_service_consumer_channelId", 
				"mediationservice", "MSFM_su_1_jee_instance");
		
	}
	
	public String getFmSu0ProtocolInfo() {
		return litpHandler.readPibValue("med_service_protocol_info",
				"mediationservice", "MSFM_su_0_jee_instance");
	}
	
	public String getFmSu1ProtocolInfo() {
		return litpHandler.readPibValue("med_service_protocol_info",
				"mediationservice", "MSFM_su_1_jee_instance");
	}
	
	public String getPmSu0ProtocolInfo() {
		return litpHandler.readPibValue("med_service_protocol_info",
				"mediationservice", "MSPM_su_0_jee_instance");
	}
	
	public String getPmSu1ProtocolInfo() {
		return litpHandler.readPibValue("med_service_protocol_info",
				"mediationservice", "MSPM_su_1_jee_instance");
	}
	
	public String getExpectedFmSu0ProtocolInfo() {
		return "FM";
	}
	
	public String getExpectedFmSu1ProtocolInfo() {
		return "FM";
	}
	
	public String getExpectedPmSu0ProtocolInfo() {
		return "PM";
	}
	
	public String getExpectedPmSu1ProtocolInfo() {
		return "PM";
	}
	
	public String getExpectedFmSu0MediationConsumerChannelId() {
		return "FmMediationServiceConsumer_0";
	}
	
	public String getExpectedFmSu1MediationConsumerChannelId() {
		return "FmMediationServiceConsumer_1";
	}
}
