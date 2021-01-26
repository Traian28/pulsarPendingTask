package com.comviva.asa.recordpendingtask.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReader {
	
	private static final Logger logger     = Logger.getLogger(PropertiesReader.class);

	public static String getendpointAddress() throws IOException{

	    String endpointAddress = null;

	    //to load application's properties, we use this class
	    Properties mainProperties = new Properties();

	    FileInputStream file;

	    //the base folder is ./, the root of the main.properties file  
	    String path = "./application.properties";

	    //load the file handle for main.properties
	    file = new FileInputStream(path);

	    //load all the properties from this file
	    mainProperties.load(file);

	    //we have loaded the properties, so close the file handle
	    file.close();

	    //retrieve the property we are intrested, the app.version
	    endpointAddress = mainProperties.getProperty("server.address");

	    logger.info("Server address endpoint successfully read");
	    
	    return endpointAddress;
	}
	
	public static String[] getMySqlConnection() throws IOException{

	    String[] dataBaseDate = new String[4];

	    //to load application's properties, we use this class
	    Properties mainProperties = new Properties();

	    FileInputStream file;

	    //the base folder is ./, the root of the main.properties file  
	    String path = "./application.properties";

	    //load the file handle for main.properties
	    file = new FileInputStream(path);

	    //load all the properties from this file
	    mainProperties.load(file);

	    //we have loaded the properties, so close the file handle
	    file.close();

	    //retrieve the property we are intrested, the app.version
	    dataBaseDate[0] = mainProperties.getProperty("datasource.url");
	    dataBaseDate[1] = mainProperties.getProperty("datasource.username");
	    dataBaseDate[2] = mainProperties.getProperty("datasource.password");
	    dataBaseDate[3] = mainProperties.getProperty("datasource.poolSize");
	    
	    logger.info("DataBase data succesfully read");

	    return dataBaseDate;
	}
	
	public static String[] getCustomProperties(String ... properties) throws IOException{

	    String [] resultProperties = new String[properties.length];

	    //to load application's properties, we use this class
	    Properties mainProperties = new Properties();

	    FileInputStream file;

	    //the base folder is ./, the root of the main.properties file  
	    String path = "./application.properties";

	    //load the file handle for main.properties
	    file = new FileInputStream(path);

	    //load all the properties from this file
	    mainProperties.load(file);

	    //we have loaded the properties, so close the file handle
	    file.close();

	    //retrieve the property we are intrested, the app.version
		for (int i=0 ; i<properties.length; i++) {
			
			String property = properties[i];
			resultProperties[i] = mainProperties.getProperty(property);
			
		}
	    
	    logger.info("DataBase data succesfully read");

	    return resultProperties;
	}
	
	public static String getModuleVersion() throws IOException{

	    String moduleVersion = null;

	    //to load application's properties, we use this class
	    Properties mainProperties = new Properties();

	    FileInputStream file;

	    //the base folder is ./, the root of the main.properties file  
	    String path = "./application.properties";

	    //load the file handle for main.properties
	    file = new FileInputStream(path);

	    //load all the properties from this file
	    mainProperties.load(file);

	    //we have loaded the properties, so close the file handle
	    file.close();

	    //retrieve the property we are intrested, the app.version
	    moduleVersion = mainProperties.getProperty("module.version");

	    logger.info("Module Version successfully read");
	    
	    return moduleVersion;
	}
}
