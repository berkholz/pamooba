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
public class DatabaseQuery {

	/**
	 * VARIABLES
	 */
	// Logger for this class.
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DatabaseQuery.class.getName());

	// SQL statement that would be executed
	private String sqlQuery;

	// connection url for database querying
	private String url;

	// connection settings
	private final String hostname;
	private final String dbname;
	private final String dbtype;
	private final String username;
	private final String password;
	private final int port;

	// select settings
	private final String dbTable;
	private final String dbTableIdColumn;
	private final String dbTableShortDescriptionColumn;
	private final String dbTableDescriptionColumn;
	private final String dbSelectCondition;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * Constructor gets the configuration with all settings, including database
	 * settings.
	 *
	 * @param config Configuration for the program and database.
	 */
	public DatabaseQuery(MainConfiguration config) {
		DatabaseConnectionConfiguration dbConSets = config.getDATABASE_CONNECTION_SETTINGS();
		DatabaseSelectConfiguration dbSelSets = config.getDATABASE_SELECT_SETTINGS();

		hostname = dbConSets.getDATABASE_HOSTNAME();
		dbname = dbConSets.getDATABASE_NAME();
		dbtype = dbConSets.getDATABASE_TYPE();
		dbTable = dbSelSets.getDATABASE_TABLE_NAME();
		dbTableIdColumn = dbSelSets.getDATABASE_TABLE_ID_COLUMN();
		dbTableShortDescriptionColumn = dbSelSets.getDATABASE_TABLE_SHORT_DESCRIPTION_COLUMN();
		dbTableDescriptionColumn = dbSelSets.getDATABASE_TABLE_DESCRIPTION_COLUMN();
		dbSelectCondition = dbSelSets.getDATABASE_SELECT_CONDITION();
		username = dbConSets.getDATABASE_USERNAME();
		password = dbConSets.getDATABASE_PASSWORD();
		port = dbConSets.getDATABASE_PORT();

		// create SQL statement 
		this.initializeQuery();

		// every url begins with "jdbc:"
		url = "jdbc:";
	}

	// TODO: add javadoc 
	// TODO: implement
	private void initializeQuery() {
		String sqlstatement = "SELECT " + dbTableIdColumn + "," + dbTableShortDescriptionColumn + "," + dbTableDescriptionColumn + " FROM " + dbname + "." + dbTable;
		if (dbSelectCondition == null || dbSelectCondition.isEmpty()) {
			sqlstatement += ";";
		} else {
			sqlstatement += " WHERE " + dbSelectCondition + ";";
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
					Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "postgresql://" + hostname + ":" + port + "/" + dbname + "?useSSL=false";
				break;
			}
			case "mysql": {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "mysql://" + hostname + ":" + port + "/" + dbname + "?useSSL=false";
				break;
			}
			default: {
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "odbc:" + dbname + "?useSSL=false";
				break;
			}
		}
		LOG.debug("Initializing url for database connection: " + this.url);
	}

	/**
	 *
	 * @return @throws SQLException
	 */
	public List getCourses() throws SQLException {
		List<DatabaseQueryResult> listOfCourses = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(url, username, password)) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				System.out.print("1: " + rs.getString(1));
//				listOfCourses.add(new DatabaseQueryResult(rs.getLong(1), ""));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
		return listOfCourses;
	}
}
