package com.comviva.asa.recordpendingtask.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.comviva.asa.orchestrator2pulsar.ws.MessageData;
import com.comviva.asa.orchestrator2pulsar.ws.MessageData.Parameters.Entry;

public class getInsertData {
	
	private static final String CONST_TOPIC = "WS-PENDING-PULSAR";
	private static final String CONST_ICCID_OCS_OPERATION 		= "svcChangeSubIdentity";
	private static final String CONST_ICCID_OCS_SERVICE 		= "HRDChgSubIdentity";
	private static final String CONST_UPD_BSCS_OPERATION 		= "PROVISION_SIMCARD_UPDATE_MSISDN";
	private static final String CONST_UPD_BSCS_SERVICE 			= "CRM_Manage_Resource";
	private static final String CONST_RECHARGE_OCS_OPERATION 	= "provisionRecharge";
	private static final String CONST_RECHARGE_OCS_SERVICE 		= "CRDCreateRecharge";
	private static final String CONST_OFFER_OCS_OPERATION 		= "provisionOffer";
	private static final String CONST_OFFER_OCS_SERVICE 		= "CUTManageMSE";
	private static final String CONST_UPD_OTA_OPERATION 		= "CHANGING_MSISDN";
	private static final String CONST_UPD_OTA_SERVICE 			= "SubsManagerService";
	private static final String CONST_UPD_HPERM_OPERATION 		= "notifica_troca_chip_numero";
	private static final String CONST_UPD_HPERM_SERVICE 		= "HpermWS";
	private static final String CONST_UPD_ACT_OPERATION 		= "SimCardActivationResult";
	private static final String CONST_UPD_ACT_SERVICE 			= "ActivationSimcard";
	
	public static String getXml (MessageData arg0, String arg1) {
		
		String stateMachineState = getStateMachineState (arg0);
		String operationName = getOperationName(stateMachineState);
		String serviceName = getServiceName(stateMachineState);
		
		String xml = 	"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ws=\"http://ws.orchestrator2pulsar.asa.comviva.com/\">\n"
					+	"<soapenv:Header/>\n"
					+	"<soapenv:Body>\n"
					+		"<ws:sendMessage>\n"
					+			"<arg0>\n"
		            +				"<messageId>"+arg0.getMessageId()+"</messageId>\n"
		            +				"<operationName>"+operationName+"</operationName>\n"
		            +				"<originName>"+arg0.getOriginName()+"</originName>\n"
		            +				"<parameters>\n"
		            +				getEntry (arg0, serviceName, operationName)
		            +				"</parameters>\n"
		            +				"<serviceName>"+serviceName+"</serviceName>\n"
		            +				"<siteName>"+arg0.getSiteName()+"</siteName>\n"
		            +			"</arg0>\n"
		            +			"<arg1>"+CONST_TOPIC+"</arg1>\n"
		            +		"</ws:sendMessage>\n"
		            +	"</soapenv:Body>\n"
		            +	"</soapenv:Envelope>";
		
		return xml;
		
	}
	
	private static String getEntry (MessageData arg0, String serviceName, String operationName) {
		
		Iterator<Entry> iteratorEntry = arg0.getParameters().getEntry().iterator();
		
		String resultEntry = "";
		
		Entry entry;
		String auxKey = "";
		String auxValue = "";
		
		while (iteratorEntry.hasNext()) {
			
			entry = iteratorEntry.next();
			
			auxKey = entry.getKey();
			auxValue = entry.getValue();
			if (auxKey.contentEquals("sessionInfo")) {
				auxKey = "param01";
			}
			else if (auxKey.contentEquals("subscriberInfo")) {
					auxKey = "param02";
			}
				else if (auxKey.contentEquals("requestInfo")) {
					auxKey = "param03";
					auxValue = auxValue.replace("sendMessage", operationName);
					auxValue = auxValue.replace("pulsarPendingTask", serviceName);
				}

			resultEntry = 	resultEntry
						+	"<entry>\n"
						+		"<key>"+ auxKey +"</key>\n"
						+		"<value>"+auxValue+"</value>\n"
						+	"</entry>\n";
			
		}
		
		resultEntry = 	resultEntry 
				+	"<entry>\n"
				+		"<key>param10</key>\n"
				+		"<value>WS-PENDING-PULSAR</value>\n"
				+	"</entry>\n"; 
		
		return resultEntry;
		
	}
	
	public static Map<String, String> getBackLogData(MessageData arg0) {

		Map<String, String> backlogData = new HashMap<String, String>();

		List<com.comviva.asa.orchestrator2pulsar.ws.MessageData.Parameters.Entry> arg0Params = arg0.getParameters()
				.getEntry();

		Entry entry2 = arg0Params.get(1);

		String[] params2 = entry2.getValue().split(";");
		//String[] params3 = entry3.getValue().split(";");

		boolean foundIccid = false;
		Integer index = 0;
		String[] paramEntry;

		while (!foundIccid && index < params2.length) {

			paramEntry = params2[index].split("=");

			if (paramEntry[0].equals("iccid")) {
				foundIccid = true;
				backlogData.put("iccid", paramEntry[1]);
			}
			
			index++;
		}
		backlogData.put("operationName", getOperationName(getStateMachineState (arg0)));

		return backlogData;

	}
	
	private static String getStateMachineState (MessageData arg0) {
		
		List<com.comviva.asa.orchestrator2pulsar.ws.MessageData.Parameters.Entry> arg0Params = arg0.getParameters()
				.getEntry();

		Entry entry = arg0Params.get(0);

		String[] params = entry.getValue().split(";");
		
		String stateMachineState = "";

		boolean foundIccid = false;
		Integer index = 0;
		String[] paramEntry;

		while (!foundIccid && index < params.length) {

			paramEntry = params[index].split("=");

			if (paramEntry[0].equals("stateMachineState")) {
				foundIccid = true;
				stateMachineState = paramEntry[1];
			}
			
			index++;
		}
			
		return stateMachineState;
	}
	
	private static String getOperationName (String stateMachineState) {
		String operationName = "";
		
		switch (stateMachineState) {
		case "RECORD_ICCID_OCS":
			operationName = CONST_ICCID_OCS_OPERATION;
			break;
		case "RECORD_UPD_BSCS":
			operationName = CONST_UPD_BSCS_OPERATION;
			break;
		case "RECORD_RECHARGE_OCS":
			operationName = CONST_RECHARGE_OCS_OPERATION;
			break;
		case "RECORD_OFFER_OCS":
			operationName = CONST_OFFER_OCS_OPERATION;
			break;
		case "RECORD_UPD_OTA":
			operationName = CONST_UPD_OTA_OPERATION;
			break;
		case "RECORD_UPD_HPERM":
			operationName = CONST_UPD_HPERM_OPERATION;
			break;
		case "RECORD_UPD_ACT":
			operationName = CONST_UPD_ACT_OPERATION;
			break;
		}
		
		return operationName;
	}
	
	private static String getServiceName (String stateMachineState) {
		String serviceName = "";
		
		switch (stateMachineState) {
		case "RECORD_ICCID_OCS":
			serviceName = CONST_ICCID_OCS_SERVICE;
			break;
		case "RECORD_UPD_BSCS":
			serviceName = CONST_UPD_BSCS_SERVICE;
			break;
		case "RECORD_RECHARGE_OCS":
			serviceName = CONST_RECHARGE_OCS_SERVICE;
			break;
		case "RECORD_OFFER_OCS":
			serviceName = CONST_OFFER_OCS_SERVICE;
			break;
		case "RECORD_UPD_OTA":
			serviceName = CONST_UPD_OTA_SERVICE;
			break;
		case "RECORD_UPD_HPERM":
			serviceName = CONST_UPD_HPERM_SERVICE;
			break;
		case "RECORD_UPD_ACT":
			serviceName = CONST_UPD_ACT_SERVICE;
			break;
		}
		
		return serviceName;
	}

}
