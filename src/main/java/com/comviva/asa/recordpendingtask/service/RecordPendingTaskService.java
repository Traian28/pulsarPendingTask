package com.comviva.asa.recordpendingtask.service;

import java.io.IOException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;

import com.comviva.asa.recordpendingtask.utils.PropertiesReader;

public class RecordPendingTaskService {
	
	private Endpoint endpoint;
	private String endpointAddress;
	Logger logger = Logger.getLogger(RecordPendingTaskService.class);
	
	public RecordPendingTaskService() {
		try {
			this.endpointAddress = PropertiesReader.getendpointAddress();
			
			//this.kpiMeasure = ProxyLocationKpiApplication.getInstance();
			//kpiMeasure.setModuleVersion(PropertiesReader.getModuleVersion());
			
			logger.info("Endpoint successfully read from properties");
		} catch (IOException e) {
			logger.error("Error reading endpoint from properties");
			logger.error(e.getMessage());
		}
	}

	public void startServer() {

		com.comviva.asa.recordpendingtask.service.RecordPendingTaskServiceImpl service = new com.comviva.asa.recordpendingtask.service.RecordPendingTaskServiceImpl();
		try {
			endpoint = javax.xml.ws.Endpoint.create(service);
			endpoint = javax.xml.ws.Endpoint.publish(this.endpointAddress, service);
			logger.info("Record pending task Server Start listening on: "+this.endpointAddress);
		} catch (Exception e) {
			logger.error("Error starting Webserver");
			logger.error(e.getMessage());
		}
	}
}
