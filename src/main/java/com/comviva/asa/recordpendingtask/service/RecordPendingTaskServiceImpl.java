package com.comviva.asa.recordpendingtask.service;

import org.apache.log4j.Logger;

import com.comviva.asa.orchestrator2pulsar.ws.MessageData;
import com.comviva.asa.orchestrator2pulsar.ws.PulsarInterface;
import com.comviva.asa.orchestrator2pulsar.ws.RequestResponse;


@javax.jws.WebService(
        serviceName = "PulsarInterfaceService",
        portName = "PulsarInterfacePort",
        targetNamespace = "http://ws.orchestrator2pulsar.asa.comviva.com/",
        wsdlLocation = "wsdl/wsPulsar.wsdl",
        endpointInterface = "com.comviva.asa.orchestrator2pulsar.ws.PulsarInterface")

public class RecordPendingTaskServiceImpl implements PulsarInterface {

	Logger logger = Logger.getLogger(RecordPendingTaskServiceImpl.class);

	public RequestResponse sendMessage(
			MessageData arg0, String arg1) {
		
		logger.info("---------- New record pulsarPendingTask receive ----------");
		logger.info("Recording pending task for service: "+arg0.getServiceName());
		logger.info("Recording pending task for operation: "+arg0.getOperationName());
		
		logger.info("Request receive with session dates: "+arg0.getParameters().getEntry().get(2).getValue().toString());
		
		RequestResponse _return = new RequestResponse();
		
		RecordDataBase recordPulsar = new RecordDataBase (arg0, arg1);

		Integer resultCode = recordPulsar.recordTask();
		
		_return.setMessageId(arg0.getMessageId());
		_return.setResultCode(Integer.toString(resultCode));
		_return.setResultDescription(getResultDescription(resultCode));
		
		logger.info("Reponse sent");
		
		return _return;

	}
	
	private String getResultDescription (Integer resultCode) {
		
		if (resultCode == 0 )
			
			return "Operation succesfully record into database";
		
		else return "Operation not record into database";
	}
}
