package com.comviva.asa.recordpendingtask.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.apache.log4j.Logger;

import com.comviva.asa.orchestrator2pulsar.ws.MessageData;
import com.comviva.asa.recordpendingtask.utils.ConnectionMySql;

public class RecordDataBase {

	private MessageData arg0;
	private String arg1;
	private static Connection cn = null;
	private static PreparedStatement stmt = null;
	private static ResultSet rs = null;
	private static final Logger logger = Logger.getLogger(RecordDataBase.class);
	private static final String CONST_STATUS = "PENDING";
	private static final String CONST_OPERATION_TYPE = "PULSAR";
	private static final Integer CONST_ERROR_COUNT = 0;
	private Timestamp creationUTC;

	public RecordDataBase(MessageData arg0, String arg1) {

		this.arg0 = arg0;
		this.arg1 = arg1;

	}

	public Integer recordTask() {

		if (insertIntoSessionBacklog())
			return 0;
		else
			return 1;

	}

	private boolean insertIntoSessionBacklog() {

		String queryBackLogData = "INSERT INTO asa_session_backlog (OSB_ICCID , OSB_SES_ID , OSB_STATUS, OSB_SES_CREATION_UTC ) values (?,?,?,?)";

		String queryBacklogOperation = "insert into asa_session_backlog_operations (OSB_ICCID , OSB_SES_ID , SBO_OPERATION_DATA , SBO_OPERATION_DATA_TYPE , SBO_OPERATION_NAME , SBO_STATUS ,SBO_ERROR_COUNT , OSB_SES_CREATION_UTC ) values (?,?,?,?,?,?,?,?) ";

		logger.info("Getting connection to dataBase");
		
		cn = ConnectionMySql.getMySqlDataSource();

		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());

		Map<String, String> backLogData = getInsertData.getBackLogData(arg0);

		String iccid = backLogData.get("iccid");

		String mesageId = arg0.getMessageId();

		String xml = getInsertData.getXml(arg0, arg1);
		
		logger.debug("Arg0: "+arg0.toString());
		logger.debug("Arg1: "+arg1.toString());
		
		if (cn != null) {
			try {

				if (!existMasterRegister(iccid, mesageId)) {
					// first query values
					stmt = cn.prepareStatement(queryBackLogData);

					stmt.setString(1, iccid);
					stmt.setString(2, mesageId);
					stmt.setString(3, CONST_STATUS);
					stmt.setTimestamp(4, date);

					logger.info("Inserting entry into asa_session_backlog for ICCID = " + iccid);

					logger.debug("Query insert backlog data: INSERT INTO asa_session_backlog (OSB_ICCID , OSB_SES_ID , OSB_STATUS, OSB_SES_CREATION_UTC ) values ("+iccid+" , "+mesageId+" , "+CONST_STATUS+" , "+date+" )");
					
					try {
						stmt.execute();
					} catch (SQLException e) {
						logger.error("Error executing first query to DataBase");
						logger.error(e.getMessage());
						// cn.rollback();
						logger.error("Exception executing Query: " + e.getMessage());
						return false;
					}

					stmt = cn.prepareStatement(queryBacklogOperation);

					stmt.setString(1, iccid);
					stmt.setString(2, mesageId);
					stmt.setString(3, xml); // xml
					stmt.setString(4, CONST_OPERATION_TYPE);
					stmt.setString(5, backLogData.get("operationName"));
					stmt.setString(6, CONST_STATUS);
					stmt.setInt(7, CONST_ERROR_COUNT);
					stmt.setTimestamp(8, date);
					logger.info("Inserting entry into asa_session_backlog_operations for ICCID = " + iccid+ " operationName= "+backLogData.get("operationName"));
					logger.debug("Query insert backlogOperation: insert into asa_session_backlog_operations (OSB_ICCID , OSB_SES_ID , SBO_OPERATION_DATA , SBO_OPERATION_DATA_TYPE , SBO_OPERATION_NAME , SBO_STATUS ,SBO_ERROR_COUNT , OSB_SES_CREATION_UTC ) values ("+iccid+" , "+mesageId+" , "+xml+" , "+CONST_OPERATION_TYPE+" , "+backLogData.get("operationName")+" , "+CONST_STATUS+" , "+CONST_ERROR_COUNT+" , "+date+")");
					
					try {
						stmt.execute();
					} catch (SQLException e) {
						logger.error("Error executing second query to DataBase");
						logger.error(e.getMessage());
						// cn.rollback();
						logger.error("Exception executing Query: " + e.getMessage());
						return false;
					}

				} else {
					// second query values
					stmt = cn.prepareStatement(queryBacklogOperation);

					stmt.setString(1, iccid);
					stmt.setString(2, mesageId);
					stmt.setString(3, xml); // xml
					stmt.setString(4, CONST_OPERATION_TYPE);
					stmt.setString(5, backLogData.get("operationName"));
					stmt.setString(6, CONST_STATUS);
					stmt.setInt(7, CONST_ERROR_COUNT);
					stmt.setTimestamp(8, getCreationUTC());
					logger.debug("Query insert backlogOperation: insert into asa_session_backlog_operations (OSB_ICCID , OSB_SES_ID , SBO_OPERATION_DATA , SBO_OPERATION_DATA_TYPE , SBO_OPERATION_NAME , SBO_STATUS ,SBO_ERROR_COUNT , OSB_SES_CREATION_UTC ) values ("+iccid+" , "+mesageId+" , "+xml+" , "+CONST_OPERATION_TYPE+" , "+backLogData.get("operationName")+" , "+CONST_STATUS+" , "+CONST_ERROR_COUNT+" , "+getCreationUTC()+")");
					try {
						stmt.execute();
					} catch (SQLException e) {
						logger.error("Error executing second query to DataBase");
						//logger.error(e.getMessage());
						// cn.rollback();
						logger.error("Exception executing Query: " + e.getMessage());
						return false;
					}

				}
			} catch (Exception e) {
				logger.error("Error executing query to DataBase");
				logger.error(e.getMessage());
				logger.error("Exception executing Query: " + e.getMessage());
				return false;
			}

			finally {
				try {
					stmt.close();
					cn.close();
					rs.close();
				} catch (SQLException e) {
					logger.error("Error clossing connection to DB");
					logger.error(e.getMessage());
				}

			}

		}

		logger.info("New pending pulsar task save in dataBase for Iccid: " + iccid);

		return true;
	}

	private boolean existMasterRegister(String iccid, String sessionId) {

		boolean exist = false;

		String query = "SELECT OSB_ICCID, OSB_SES_CREATION_UTC from asa_session_backlog where OSB_ICCID = ? and OSB_SES_ID = ? ";
		logger.info("Preparing query to found existing register in asa_session_backlog");
		logger.info("Getting connection to dataBase");
		
		logger.debug("Searching for existing master record: SELECT OSB_ICCID, OSB_SES_CREATION_UTC from asa_session_backlog where OSB_ICCID = ? and OSB_SES_ID = ? ");

		if (cn != null) {
			try {
				stmt = cn.prepareStatement(query);
				stmt.setString(1, iccid);
				stmt.setString(2, sessionId);
				// stmt.execute();
				rs = stmt.executeQuery();
				
				logger.debug("Searching for existing master record: SELECT OSB_ICCID, OSB_SES_CREATION_UTC from asa_session_backlog where OSB_ICCID = "+iccid +"and OSB_SES_ID ="+sessionId);
			} catch (SQLException e) {
				logger.error("Error executing query to DataBase");
				logger.error(e.getMessage());
			}

			try {
				if (rs != null && rs.next()) {
					exist = rs.getString("OSB_ICCID").equals(iccid);
					if (exist) {
						logger.info("OSB_ICCID: " + iccid + " for OSB_SES_ID: " + sessionId
								+ " already exist in asa_session_backlog");

						this.creationUTC = rs.getTimestamp("OSB_SES_CREATION_UTC");

					} else {
						logger.info("OSB_ICCID: " + iccid + " for OSB_SES_ID: " + sessionId
								+ " does not exist in asa_session_backlog, creating a register");
					}
				}
			} catch (SQLException e) {
				logger.error("Error reading date after query executed");
				logger.error(e.getMessage());
			}

			finally {
				try {
					// stmt.close();
					rs.close();
					// cn.close();
				} catch (SQLException e) {
					logger.error("Error clossing statement");
					logger.error(e.getMessage());
				}

			}

		}

		return exist;
	}
	
	
	private Timestamp getCreationUTC () {
		
		return this.creationUTC;
	}

}
