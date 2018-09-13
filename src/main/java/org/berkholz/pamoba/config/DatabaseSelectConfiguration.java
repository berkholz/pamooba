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
public class DatabaseSelectConfiguration {

	/**
	 * VARIABLES
	 */
	// SELECT <COLUMN_WITH_ID>,
	@XmlElement(name = "DatabaseSelectTableIdColumn", required = true)
	private final String DATABASE_TABLE_ID_COLUMN;

	// <COLUMN_WITH_SHORT_DESCRIPTION>,
	@XmlElement(name = "DatabaseSelectTableShortDescriptionColumn", required = false)
	private final String DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN;

	// <COLUMN_WITH_DESCRIPTION>
	@XmlElement(name = "DatabaseSelectTableDescriptionColumn", required = false)
	private final String DATABASE_TABLE_DESCRIPTION_COLUMN;

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
		DATABASE_TABLE_ID_COLUMN = "id";
		DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN = "shortname";
		DATABASE_TABLE_DESCRIPTION_COLUMN = "longname";
		DATABASE_TABLE_NAME = "mdl_course";
	}

	/**
	 * METHODS
	 */
	// TODO: add javadoc
	public String toString() {
		return String.format("\n\t\tDATABASE_SELECT_CONDITION:\t\t\t%s\n"
				+ "\t\tDATABASE_TABLE_ID_COLUMN:\t\t\t%s\n"
				+ "\t\tDATABASE_TABLE_SHORT_DESCRIPTION_COLUMN:\t%s\n"
				+ "\t\tDATABASE_TABLE_DESCRIPTION_COLUMN:\t\t%s\n"
				+ "\t\tDATABASE_TABLE_NAME:\t\t\t\t%s\n", DATABASE_SELECT_CONDITION == null ? "-" : DATABASE_SELECT_CONDITION, DATABASE_TABLE_ID_COLUMN, DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN, DATABASE_TABLE_DESCRIPTION_COLUMN, DATABASE_TABLE_NAME);
	}

	/**
	 * GETTER AND SETTER
	 */
	// TODO: add javadoc
	public String getDATABASE_TABLE_ID_COLUMN() {
		return DATABASE_TABLE_ID_COLUMN;
	}

	// TODO: add javadoc
	public String getDATABASE_TABLE_SHORT_DESCRIPTION_COLUMN() {
		return DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN;
	}

	// TODO: add javadoc
	public String getDATABASE_TABLE_DESCRIPTION_COLUMN() {
		return DATABASE_TABLE_DESCRIPTION_COLUMN;
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
