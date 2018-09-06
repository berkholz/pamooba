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
class DatabaseSelectConfiguration {

	/**
	 * VARIABLES
	 */
	// SELECT <COLUMN>
	@XmlElement(name = "DatabaseSelectTableColumn", required = true)
	private final String databaseTableColumn;

	// FROM <TABLE>
	@XmlElement(name = "DatabaseSelectTable", required = true)
	private final String databaseTableName;

	// WHERE <CONDITION>
	@XmlElement(name = "DatabaseSelectCondition", nillable = true)
	private final String databaseSelectCondition;

	/**
	 * CONSTRCUTOR
	 */
	public DatabaseSelectConfiguration() {
		this.databaseSelectCondition = null;
		this.databaseTableColumn = "id";
		this.databaseTableName = "mdl_course";
	}

	/**
	 * GETTER AND SETTER
	 */
	public String getDatabaseTableColumn() {
		return databaseTableColumn;
	}

	public String getDatabaseTableName() {
		return databaseTableName;
	}

	public String getDatabaseSelectCondition() {
		return databaseSelectCondition;
	}
}
