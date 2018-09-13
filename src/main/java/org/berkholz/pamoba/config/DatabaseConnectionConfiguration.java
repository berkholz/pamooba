/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.config;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Marcel Berkholz
 */
public class DatabaseConnectionConfiguration {

	/**
	 * VARIABLES
	 */
	@XmlElement(name = "DatabaseName", required = true)
	private final String DATABASE_NAME;

	@XmlElement(name = "DatabaseHostname", required = true)
	private final String DATABASE_HOSTNAME;

	@XmlElement(name = "DatabasePort", required = false)
	private final int DATABASE_PORT;

	// TODO: add description
	@XmlElement(name = "DatabaseType", required = true)
	private final String DATABASE_TYPE;

	@XmlElement(name = "DatabaseUsername", required = true)
	private final String DATABASE_USERNAME;

	@XmlElement(name = "DatabasePassword", required = true)
	private final String DATABASE_PASSWORD;

	@XmlElement(name = "DatabaseSSLenabled")
	private final Boolean DATABASE_SSL_ENABLED;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * Constructor to initialize the database connection with a default
	 * configuration.
	 */
	public DatabaseConnectionConfiguration() {
		DATABASE_NAME = "Mdl_course";
		DATABASE_PORT = 3306;
		DATABASE_HOSTNAME = "localhost";
		DATABASE_TYPE = "mysql";
		DATABASE_USERNAME = "moodle";
		DATABASE_PASSWORD = "secret";
		DATABASE_SSL_ENABLED = true;
	}

	/**
	 * *********
	 * METHODS * *********
	 */
	/**
	 * Override toString() method to get all variables printed out.
	 *
	 * @return All variables as formatted String.
	 */
	@Override
	public String toString() {
		return String.format(
				"\n\t\tDATABASE_NAME:\t\t\t%s\n"
				+ "\t\tDATABASE_HOSTNAME:\t\t%s\n"
				+ "\t\tDATABASE_PORT:\t\t\t%s\n"
				+ "\t\tDATABASE_TYPE:\t\t\t%s\n"
				+ "\t\tDATABASE_USERNAME:\t\t%s\n"
				+ "\t\tDATABASE_PASSWORD:\t\t***\n"
				+ "\t\tDATABASE_SSL_ENABLED:\t\t***\n", DATABASE_NAME, DATABASE_HOSTNAME, DATABASE_PORT, DATABASE_TYPE, DATABASE_USERNAME, DATABASE_SSL_ENABLED);
	}

	/**
	 * GETTER AND SETTER
	 */
	/**
	 * Return the database name to query against.
	 *
	 * @return Return the database name to query against.
	 */
	public String getDATABASE_NAME() {
		return DATABASE_NAME;
	}

	/**
	 * Return the hostname where the database is hosted.
	 *
	 * @return Return the hostname where the database is hosted.
	 */
	public String getDATABASE_HOSTNAME() {
		return DATABASE_HOSTNAME;
	}

	// TODO: add javadoc
	/**
	 *
	 * @return
	 */
	public int getDATABASE_PORT() {
		return DATABASE_PORT;
	}

	// TODO: add javadoc
	/**
	 *
	 * @return
	 */
	public String getDATABASE_TYPE() {
		return DATABASE_TYPE;
	}

	// TODO: add javadoc
	/**
	 *
	 * @return
	 */
	public String getDATABASE_USERNAME() {
		return DATABASE_USERNAME;
	}

	// TODO: add javadoc
	/**
	 *
	 * @return
	 */
	public String getDATABASE_PASSWORD() {
		return DATABASE_PASSWORD;
	}

	// TODO: add javadoc
	/**
	 *
	 * @return
	 */
	public Boolean getDATABASE_SSL_ENABLED() {
		return DATABASE_SSL_ENABLED;
	}

}
