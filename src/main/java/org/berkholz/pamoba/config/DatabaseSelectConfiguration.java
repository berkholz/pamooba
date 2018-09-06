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
	private final String DATABASE_TABLE_COLUMN;

	// FROM <TABLE>
	@XmlElement(name = "DatabaseSelectTable", required = true)
	private final String DATABASE_TABLE_NAME;

	// WHERE <CONDITION>
	@XmlElement(name = "DatabaseSelectCondition", nillable = true)
	private final String DATABASE_SELECT_CONDITION;

	/**
	 * CONSTRCUTOR
	 */
	// TODO: add javadoc
	public DatabaseSelectConfiguration() {
		DATABASE_SELECT_CONDITION = null;
		DATABASE_TABLE_COLUMN = "id";
		DATABASE_TABLE_NAME = "mdl_course";
	}

	/**
	 * METHODS
	 */
	// TODO: add javadoc
	public String toString() {
		return String.format(
				"\n\t\tDATABASE_SELECT_CONDITION:\t%s\n"
				+ "\t\tDATABASE_TABLE_COLUMN:\t\t%s\n"
				+ "\t\tDATABASE_TABLE_NAME:\t\t%s\n", DATABASE_SELECT_CONDITION == null ? "-" : DATABASE_SELECT_CONDITION, DATABASE_TABLE_COLUMN, DATABASE_TABLE_NAME);
	}

	/**
	 * GETTER AND SETTER
	 */
	// TODO: add javadoc
	public String getDATABASE_TABLE_COLUMN() {
		return DATABASE_TABLE_COLUMN;
	}

	// TODO: add javadoc
	public String getDATABASE_TABLE_NAME() {
		return DATABASE_TABLE_NAME;
	}

	// TODO: add javadoc
	public String getDATABASE_SELECT_CONDITION() {
		return DATABASE_SELECT_CONDITION;
	}
}
