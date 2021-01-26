package com.comviva.asa.recordpendingtask.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.comviva.asa.recordpendingtask.service.RecordPendingTaskService;

public class MainService {
	public static void main(String[] args) {
		
		Logger logger = Logger.getLogger(MainService.class);
		
		PropertyConfigurator.configure("log4j.properties");
		
		logger.info("RecordPendingTask service Start");
		
		RecordPendingTaskService webService = new RecordPendingTaskService();
		
		webService.startServer();
		
	}
}
