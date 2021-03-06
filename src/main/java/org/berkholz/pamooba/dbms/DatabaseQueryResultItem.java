/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamooba.dbms;

/**
 * Class for representing the result of queries against the moodle database. It
 * is used for printing out the concrete moodle course that will be backed up.
 *
 * @author Marcel Berkholz
 */
public class DatabaseQueryResultItem {

	/**
	 * VARIABLES
	 */
	private Long id;
	private String shortDescription;
	private String description;

	/**
	 * CONSTRUCTOR
	 */
	/**
	 * Constructor for creating a query result with course id and its short
	 * description and normal description.
	 *
	 * @param id The result of the column with the id.
	 * @param shortDescription The result of the column with the short
	 * description.
	 * @param description The result of the column with the description.
	 */
	public DatabaseQueryResultItem(Long id, String shortDescription, String description) {
		this.id = id;
		this.shortDescription = shortDescription;
		this.description = description;
	}

	/**
	 * GETTER AND SETTER
	 */
	/**
	 * Getter for the course id.
	 *
	 * @return Returns the course id.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Getter for the short description.
	 *
	 * @return Return the short description.
	 */
	public String getShortDescription() {
		return shortDescription;
	}

	/**
	 * Getter for the description.
	 *
	 * @return Return the description.
	 */
	public String getDescription() {
		return description;
	}

}
