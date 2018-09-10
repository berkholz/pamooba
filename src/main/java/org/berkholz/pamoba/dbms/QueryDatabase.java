/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.berkholz.pamoba.config.DatabaseConnectionConfiguration;
import org.berkholz.pamoba.config.DatabaseSelectConfiguration;
import org.berkholz.pamoba.config.MainConfiguration;

/**
 *
 * @author Marcel Berkholz
 */
public class QueryDatabase {

	/**
	 * VARIABLES
	 */
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(QueryDatabase.class.getName());

	// SQL statement that would be executed
	private String sqlQuery;

	// connection url for database querying
	private String url;

	// connection settings
	private final String hostname;
	private final String dbname;
	private final String dbtype;
	private final String dbtable;
	private final String dbtablecolumn;
	private final String dbselectcondition;
	private final String username;
	private final String password;
	private final int port;

	/**
	 * CONSTRUCTORS
	 */
	// TODO: add javadoc 
	public QueryDatabase(MainConfiguration config) {
		LOG.debug("Entering Constructor..");
		hostname = config.getDATABASE_CONNECTION_SETTINGS().getDATABASE_HOSTNAME();
		dbname = config.getDATABASE_CONNECTION_SETTINGS().getDATABASE_NAME();
		dbtype = config.getDATABASE_CONNECTION_SETTINGS().getDATABASE_TYPE();
		dbtable = config.getDATABASE_SELECT_SETTINGS().getDATABASE_TABLE_NAME();
		dbtablecolumn = config.getDATABASE_SELECT_SETTINGS().getDATABASE_TABLE_COLUMN();
		dbselectcondition = config.getDATABASE_SELECT_SETTINGS().getDATABASE_SELECT_CONDITION();
		username = config.getDATABASE_CONNECTION_SETTINGS().getDATABASE_USERNAME();
		password = config.getDATABASE_CONNECTION_SETTINGS().getDATABASE_PASSWORD();
		port = config.getDATABASE_CONNECTION_SETTINGS().getDATABASE_PORT();

		// create SQL statement 
		this.initializeQuery();

		// every url begins with "jdbc:"
		url = "jdbc:";
	}

	// TODO: add javadoc 
	// TODO: implement
	private void initializeQuery() {
		String sqlstatement = "SELECT " + dbtablecolumn + " FROM " + dbname + "." + dbtable;
		if (dbselectcondition == null || dbselectcondition.isEmpty()) {
			sqlstatement += ";";
		} else {
			sqlstatement += " WHERE " + dbselectcondition + ";";
		}
		LOG.debug("Using SQL statement: " + sqlstatement);
		sqlQuery = sqlstatement;
	}

	// TODO: add javadoc 
	// TODO: implement
	public Boolean validateQuery() {
		return true;
	}

	// TODO: useSSL in configuration implementieren
	// TODO: add javadoc
	public void initializeConnectionURL() {
		LOG.trace("Initializing the connection URL.");
		switch (dbtype) {
			//PostgreSQL (v7.0 and later)
			case "postgresql": {
				try {
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "postgresql://" + hostname + ":" + port + "/" + dbname + "?useSSL=false";
				break;
			}
			case "mysql": {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "mysql://" + hostname + ":" + port + "/" + dbname + "?useSSL=false";
				break;
			}
			default: {
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "odbc:" + dbname + "?useSSL=false";
				break;
			}
		}
		LOG.debug("Initializing url for database connection: " + this.url);
	}

	/**
	 *
	 * @return @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public List getCourseIds() throws SQLException {
		List<Long> listOfCourseIds = new ArrayList<>();
		listOfCourseIds = new ArrayList();

		Connection con = DriverManager.getConnection(url, username, password);
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlQuery);

		while (rs.next()) {
			Long courseId = rs.getLong(1);
			System.out.println(rs.getString(1));
			listOfCourseIds.add(courseId);
//			String name = rs.getString("Name");
//			String desc = rs.getString("Description");
//			int qty = rs.getInt("Qty");
//			float cost = rs.getFloat("Cost");
//			System.out.println(name + ", " + desc + "\t: " + qty + "\t@ $" + cost);
		}

		rs.close();
		stmt.close();
		con.close();
		return null;
	}
}
