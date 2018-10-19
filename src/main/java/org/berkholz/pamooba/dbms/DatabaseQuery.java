/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamooba.dbms;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.berkholz.pamooba.config.DatabaseConnectionConfiguration;
import org.berkholz.pamooba.config.DatabaseSelectConfiguration;
import org.berkholz.pamooba.config.MainConfiguration;

/**
 * Class for initializing the connection url for connecting to the database. The
 * database driver is loaded in this class.
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
	private final Boolean dbSSLenabled;

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
		dbSSLenabled = dbConSets.getDATABASE_SSL_ENABLED();

		// check invalid characters in sql query components
		this.validateQuery();

		// create SQL statement 
		this.initializeQuery();

		// every url begins with "jdbc:"
		url = "jdbc:";
		this.initializeConnectionURL();
	}

	/**
	 * Create the SQL query to get all courses and their informations.
	 */
	private void initializeQuery() {
		String dbTablePrefix = "";
		// workaround for mysql and postgresql
		if (!"postgresql".equals(this.dbtype)) {
			dbTablePrefix = " " + dbname + ".";
		}

		String sqlstatement = "SELECT " + dbTableIdColumn + "," + dbTableShortDescriptionColumn + "," + dbTableDescriptionColumn + " FROM " + dbTablePrefix + dbTable;

		if (dbSelectCondition == null || dbSelectCondition.isEmpty()) {
			LOG.trace("No select condition used and given.");
		} else {
			sqlstatement += " WHERE " + dbSelectCondition;
		}
		sqlstatement += ";";
		LOG.debug("Using SQL statement: " + sqlstatement);
		sqlQuery = sqlstatement;
	}

	/**
	 * Validate the SQL query for reducing SQL injections.
	 *
	 * @return Return true if SQL query is valid, otherwise false.
	 */
	private Boolean validateQuery() {
		LOG.trace("Validating the table name.");
		this.validateQueryItem(dbTable);
		LOG.trace("Validating the name of the id column of the table.");
		this.validateQueryItem(dbTableIdColumn);
		LOG.trace("Validating the name of the short description column of the table.");
		this.validateQueryItem(dbTableShortDescriptionColumn);
		LOG.trace("Validating the name of the description column of the table.");
		this.validateQueryItem(dbTableDescriptionColumn);
		LOG.trace("Validating the database name.");
		this.validateQueryItem(dbname);
		return true;
	}

	/**
	 * Check if the given string matches the regular expression. If it matches
	 * the string should be valid.
	 *
	 * @return Return true if given string matches the valid regular expression.
	 */
	private boolean validateQueryItem(String queryPart) {
		// for valid characters, see https://www.postgresql.org/docs/9.2/static/sql-syntax-lexical.html#SQL-SYNTAX-IDENTIFIERS
		return queryPart.matches("[A-Za-z][A-Za-z0-9_-]+[A-Za-z]");
	}

	/**
	 * Initialize the connection URL and loading the database driver. The
	 * following drivers are yet supported:
	 * <ul>
	 * <li>postgresql (for PostgreSQL (v7.0 and later))</li>
	 * <li>mysql (for MySQL since 5.6)</li>
	 * <li>generic JdbcOdbcDriver</li>
	 * </ul>
	 *
	 * If SSL certificate checking should be disabled, the configuration option
	 * DatabaseSSLenabled have to be set to false:
	 * <p>
	 * &lt;DatabaseSSLenabled&gt;false&lt;/DatabaseSSLenabled&gt;
	 * </p>
	 */
	public void initializeConnectionURL() {
		LOG.trace("Initializing the connection URL.");
		switch (dbtype) {
			//PostgreSQL (v7.0 and later)
			case "postgresql": {
				try {
					LOG.trace("Loading database driver: org.postgresql.Driver.");
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "postgresql://" + hostname + ":" + port + "/" + dbname + "?useSSL=" + dbSSLenabled;
				break;
			}
			case "mysql": {
				try {
					LOG.trace("Loading database driver: com.mysql.cj.jdbc.Driver.");
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "mysql://" + hostname + ":" + port + "/" + dbname + "?useSSL=" + dbSSLenabled;
				break;
			}
			default: {
				try {
					LOG.trace("Loading database driver: sun.jdbc.odbc.JdbcOdbcDriver.");
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
				} catch (ClassNotFoundException ex) {
					Logger.getLogger(DatabaseQuery.class.getName()).log(Level.SEVERE, null, ex);
				}
				url += "odbc:" + dbname + "?useSSL=" + dbSSLenabled;
				break;
			}
		}
		LOG.debug("Initializing url for database connection: " + this.url);
	}

	/**
	 * GETTER AND SETTER
	 */
	/**
	 * Get the SQL query to get the courses.
	 *
	 * @return Return the SQL query.
	 */
	public String getSqlQuery() {
		return sqlQuery;
	}

	/**
	 * Get the database connection URL .
	 *
	 * @return Returns the database connection URL.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Get the username to connect with to the database.
	 *
	 * @return Return the database user for the connection.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get the password for the database connection user.
	 *
	 * @return Return the password of database connection user.
	 */
	public String getPassword() {
		return password;
	}

}
