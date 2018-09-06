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
	private final String databaseName;

	@XmlElement(name = "DatabaseUsername", required = true)
	private final String databaseUsername;

	@XmlElement(name = "DatabasePassword", required = true)
	private final String databasePassword;

	@XmlElement(name = "DatabasePort", required = false)
	private final int databasePort;

	/**
	 * CONSTRUCTORS
	 */
	public DatabaseConnectionConfiguration() {
		databaseName = "Mdl_course";
		databasePassword = "moodle";
		databaseUsername = "moodle";
		databasePort = 5432;
	}

	/**
	 * GETTER AND SETTER
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	public String getDatabaseUsername() {
		return databaseUsername;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}

	public int getDatabasePort() {
		return databasePort;
	}

}
