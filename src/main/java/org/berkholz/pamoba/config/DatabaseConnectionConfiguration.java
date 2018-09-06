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

	@XmlElement(name = "DatabaseUsername", required = true)
	private final String DATABASE_USERNAME;

	@XmlElement(name = "DatabasePassword", required = true)
	private final String DATABASE_PASSWORD;

	@XmlElement(name = "DatabasePort", required = false)
	private final int DATABASE_PORT;

	// TODO: add description
	@XmlElement(name = "DatabaseType", required = true)
	private final String DATABASE_TYPE;

	/**
	 * CONSTRUCTORS
	 */
	// TODO: add javadoc
	public DatabaseConnectionConfiguration() {
		DATABASE_NAME = "Mdl_course";
		DATABASE_USERNAME = "moodle";
		DATABASE_PASSWORD = "secret";
		DATABASE_PORT = 5432;
		DATABASE_TYPE = "postgres,mysql";
	}

	/**
	 * METHODS
	 */
	// TODO: add javadoc
	public String toString() {
		return String.format(
				"\n\t\tDATABASE_NAME:\t\t\t%s\n"
				+ "\t\tDATABASE_USERNAME:\t\t%s\n"
				+ "\t\tDATABASE_PASSWORD:\t\t***\n"
				+ "\t\tDATABASE_PORT:\t\t\t%s\n"
				+ "\t\tDATABASE_TYPE:\t\t\t%s", DATABASE_NAME, DATABASE_USERNAME, DATABASE_PORT, DATABASE_TYPE);
	}

	/**
	 * GETTER AND SETTER
	 */
	// TODO: add javadoc
	public String getDATABASE_NAME() {
		return DATABASE_NAME;
	}

	// TODO: add javadoc
	public String getDATABASE_USERNAME() {
		return DATABASE_USERNAME;
	}

	// TODO: add javadoc
	public String getDATABASE_PASSWORD() {
		return DATABASE_PASSWORD;
	}

	// TODO: add javadoc
	public int getDATABASE_PORT() {
		return DATABASE_PORT;
	}

}
