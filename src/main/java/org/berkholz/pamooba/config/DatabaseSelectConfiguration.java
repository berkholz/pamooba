/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamooba.config;

import jakarta.xml.bind.annotation.XmlElement;

/**
 * Class for representing the database select settings. It is part of the
 * MainConfiguration class and hold only settings for querying the database. It
 * is used to create the SQL SELECT statement for querying the ids and other
 * data.
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
	/**
	 * Constructor to initialize default values for the select statement.
	 */
	public DatabaseSelectConfiguration() {
		DATABASE_SELECT_CONDITION = null;
		DATABASE_TABLE_ID_COLUMN = "id";
		DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN = "shortname";
		DATABASE_TABLE_DESCRIPTION_COLUMN = "fullname";
		DATABASE_TABLE_NAME = "mdl_course";
	}

	/**
	 * METHODS
	 */
	/**
	 * Returns the select settings to print out the configuration.
	 *
	 * @return Return the select settings.
	 */
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
	/**
	 * Return the table column name for the id.
	 *
	 * @return Return the name of the id column of the table.
	 */
	public String getDATABASE_TABLE_ID_COLUMN() {
		return DATABASE_TABLE_ID_COLUMN;
	}

	/**
	 * Return the table column name for the short description.
	 *
	 * @return Return the name of the short description column of the table.
	 */
	public String getDATABASE_TABLE_SHORT_DESCRIPTION_COLUMN() {
		return DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN;
	}

	/**
	 * Return the table column name for the description.
	 *
	 * @return Return the name of the description column of the table.
	 */
	public String getDATABASE_TABLE_DESCRIPTION_COLUMN() {
		return DATABASE_TABLE_DESCRIPTION_COLUMN;
	}

	/**
	 * Return the name of the table where all data should queried.
	 *
	 * @return Return the name of the table.
	 */
	public String getDATABASE_TABLE_NAME() {
		return DATABASE_TABLE_NAME;
	}

	/**
	 * Return the WHERE statement of the SELECT statement to use for condition
	 * based query.
	 *
	 * @return Return the WHERE condition of the SELECT statement.
	 */
	public String getDATABASE_SELECT_CONDITION() {
		return DATABASE_SELECT_CONDITION;
	}
}
