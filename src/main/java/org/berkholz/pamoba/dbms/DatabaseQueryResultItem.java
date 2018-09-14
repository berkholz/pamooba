/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.dbms;

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
	 * Constructor for creating a query result with a course id and its
	 * logname.The shortname is set with an empty string.
	 *
	 * @param courseId
	 * @param courseLongname
	 */
	public DatabaseQueryResultItem(Long courseId, String courseLongname) {
		this(courseId, "", courseLongname);
	}

	/**
	 * Constructor for creating a query result with course id and its short- and
	 * longname.
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

	public String getShortDescription() {
		return shortDescription;
	}

	public String getDescription() {
		return description;
	}

}
