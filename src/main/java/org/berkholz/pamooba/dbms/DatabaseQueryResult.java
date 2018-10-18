/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamooba.dbms;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.berkholz.helperfunctions.HelperFunctions;

/**
 * Class for representing the result of the database query which is created
 * within the class DatabaseQuery.
 *
 *
 * @author Marcel Berkholz
 */
public class DatabaseQueryResult {

	/**
	 * VARIABLES
	 */
	// Logger for this class.
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DatabaseQuery.class.getName());

	private List<DatabaseQueryResultItem> courses;
	private String blackListFile;
	private String whiteListFile;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * Constructor connects to the database with setting from DatabaseQuery
	 * object. The result is stored in a List&lt;DatabaseQueryResultItem&gt;.
	 *
	 * @param dbQuery Database settings for querying the database.
	 */
	public DatabaseQueryResult(DatabaseQuery dbQuery) {
		courses = new ArrayList<>();

		LOG.debug("Trying to connect to database.");
		try (Connection con = DriverManager.getConnection(dbQuery.getUrl(), dbQuery.getUsername(), dbQuery.getPassword())) {
			Statement stmt = con.createStatement();
			LOG.trace("Executing SQL query with statement.");
			ResultSet rs = stmt.executeQuery(dbQuery.getSqlQuery());

			LOG.trace("Adding all query result elements to courses list.");
			while (rs.next()) {
				courses.add(new DatabaseQueryResultItem(rs.getLong(1), rs.getString(2), rs.getString(3)));
			}

			LOG.trace("Closing all database connections.");
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
	}

	/**
	 * METHODS
	 */
	/**
	 * Get all courses for backup without black listed and with only white
	 * listed elements. Black list has higher precedence than white list.
	 *
	 * @return Return all courses. If black list was given only not black listed
	 * courses will be returned. If white list is given, only courses in white
	 * list are returned.
	 */
	public List<DatabaseQueryResultItem> getCourses() {
		filterWhiteList();
		filterBlackList();
		return courses;
	}

	/**
	 * Filter all courses with id listed in black list.
	 */
	private void filterBlackList() {
		if (blackListFile != null) {
			LOG.debug("Read lines from black list file.");
			List<Long> blackList = HelperFunctions.readStringArrayFromFile(new File(blackListFile)).stream().map(Long::parseLong).collect(Collectors.toList());

			List<DatabaseQueryResultItem> tmpList = new ArrayList<>();
			tmpList.addAll(courses);

			if (blackList != null && !blackList.isEmpty()) {
				for (DatabaseQueryResultItem course : courses) {
					if (blackList.contains(course.getId())) {
						LOG.debug("Remove black listed elements with id " + course.getId() + "(" + course.getShortDescription() + "; " + course.getDescription() + " from list.");
						tmpList.remove(course);
					}
				}
			}
			courses = tmpList;
		} else {
			LOG.debug("No black list file given, skipping.");
		}
	}

	/**
	 * Filter the course list by white list.
	 */
	private void filterWhiteList() {
		if (whiteListFile != null) {
			LOG.debug("Read lines from white list file.");
			List<Long> whiteList = HelperFunctions.readStringArrayFromFile(new File(whiteListFile)).stream().map(Long::parseLong).collect(Collectors.toList());
			List<DatabaseQueryResultItem> tmpList = new ArrayList<>();

			// iteriere über white list
			whiteList.stream().forEach((whiteListEntry) -> {
				for (DatabaseQueryResultItem course : courses) {
					if (course.getId().equals(whiteListEntry)) {
						LOG.debug(String.format("Mark course (%d;%s;%s) for backup.", course.getId(), course.getShortDescription(), course.getDescription()));
						tmpList.add(course);
					}
				}
			});
			courses = tmpList;
		} else {
			LOG.debug("No white list file given, skipping.");
		}
	}

	/**
	 * Method to set the black list for the courses.
	 *
	 * @param fileNameOfBlackList File name of the black list file.
	 */
	public void setBlacklist(String fileNameOfBlackList) {
		this.blackListFile = fileNameOfBlackList;
	}

	/**
	 * Method to set the white list for the courses.
	 *
	 * @param fileNameOfWhiteList File name of the white list file.
	 */
	public void setWhitelist(String fileNameOfWhiteList) {
		this.whiteListFile = fileNameOfWhiteList;
	}
}
