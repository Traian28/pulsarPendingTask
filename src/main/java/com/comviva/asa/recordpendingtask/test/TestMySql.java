package com.comviva.asa.recordpendingtask.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.comviva.asa.recordpendingtask.service.getInsertData;
import com.comviva.asa.recordpendingtask.utils.ConnectionMySql;
import com.comviva.asa.recordpendingtask.utils.PropertiesReader;

public class TestMySql {
	static Connection connection = null;
	private static Connection cn = null;
	private static PreparedStatement stmt = null;
	private static ResultSet rs = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String queryBackLogData = "INSERT INTO asa_session_backlog (OSB_ICCID , OSB_SES_ID , OSB_STATUS, OSB_SES_CREATION_UTC ) values (?,?,?,?)";
		
		String queryBacklogOperation = "insert into asa_session_backlog_operations (OSB_ICCID , OSB_SES_ID , SBO_OPERATION_DATA , SBO_OPERATION_DATA_TYPE , SBO_OPERATION_NAME , SBO_STATUS ,SBO_ERROR_COUNT , OSB_SES_CREATION_UTC ) values (?,?,?,?,?,?,?,?) ";

		System.out.println("Getting connection to dataBase");

		cn = getMySqlDataSource();
		
		try {
			if (cn.isValid(4)) 
				System.out.println("Is valid");
			else System.out.println("Is not valid");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

//		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
//		
//		//Map<String,String> backLogData = getInsertData.getBackLogData(arg0);
//		//String xml = getInsertData.getXml(arg0, arg1);
//
//		if (cn != null) {
//			try {
//				stmt = cn.prepareStatement(queryBackLogData);
//
//				//stmt.addBatch(queryBackLogData);
//				//stmt.addBatch(queryBacklogOperation);
//				
//				//first query values
//				stmt.setString		(1, "iccid");
//				stmt.setString		(2, "messageId");
//				stmt.setString		(3, "PENDING");
//				stmt.setTimestamp	(4, date);
//				try {
//					//stmt.execute();
//					stmt.execute();
//					//cn.commit();
//				} catch (SQLException e) {
//					System.out.println("Error executing query to DataBase");
//					System.out.println(e.getMessage());
//					cn.rollback();
//					System.out.println("Exception executing Query: " + e.getMessage());
//					//return false;
//				}
//				//second query values
//				
//				stmt = cn.prepareStatement(queryBacklogOperation);
//				
//				stmt.setString		(1, "iccid");
//				stmt.setString		(2, "messageId");
//				stmt.setString		(3, "messageId"); //xml
//				stmt.setString		(4, "PULSAR");
//				stmt.setString		(5, "FLOWONE");
//				stmt.setString		(6, "PENDING");
//				stmt.setInt			(7, 0);
//				stmt.setTimestamp	(8, date);
//				
//				try {
//					//stmt.execute();
//					stmt.execute();
//					//cn.commit();
//				} catch (SQLException e) {
//					System.out.println("Error executing query to DataBase");
//					System.out.println(e.getMessage());
//					cn.rollback();
//					System.out.println("Exception executing Query: " + e.getMessage());
//					//return false;
//				}
//				
//				
//			} catch (Exception e) {
//				System.out.println("Error executing query to DataBase");
//				System.out.println(e.getMessage());
//				System.out.println("Exception executing Query: " + e.getMessage());
//				//return false;
//			}
//		}
		
	}
	
	public static Connection getMySqlDataSource() {
		String[] dataBaseSource;
		
		
		try {
			
			//dataBaseSource = PropertiesReader.getMySqlConnection();
			
			String url = "jdbc:mysql://localhost:3306/ASA_0600_SCH";
			String user = "asa_usr";
			String psw = "Mcom@201811";
			Integer poolSize = 3;
			
			while ((connection == null || connection.isClosed()) && poolSize > 0) {
				try {
					//Class.forName("com.mysql.jdbc.Driver"); 
					connection = DriverManager.getConnection(url, user, psw);
					System.out.println("Connection established with dataBase");
					//measureDB.mark(MarkType.OK);
				} catch (SQLException e) {
					System.out.println("Error establishing connection to database");
					System.out.println(e.getMessage());
					//measureDB.markSpecificError("102");
					//logger.debug("Mark Kpi 102 " + KPIS.dbStatus.getName());
				}
				poolSize--;
			}
		} catch (Exception e) {
			System.out.println("Error establishing connection to database or reading properties");
			System.out.println(e.getMessage());
			//measureDB.markSpecificError("101");
			//logger.debug("Mark Kpi 101 " + KPIS.dbStatus.getName());
		}

		return connection;
}

}
