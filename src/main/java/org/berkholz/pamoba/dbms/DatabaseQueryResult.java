/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.berkholz.pamoba.config.MainConfiguration;

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

	private List courses;
	private DatabaseQuery dbQuery;

	/**
	 * CONSTRUCTORS
	 */
	public DatabaseQueryResult(DatabaseQuery dbQuery) {
		this.dbQuery = dbQuery;
	}

	/**
	 * METHODS
	 */
	/**
	 *
	 * @return @throws SQLException
	 */
	public List getCourses() throws SQLException {
		List<DatabaseQueryResultItem> listOfCourses = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(dbQuery.getUrl(), dbQuery.getUsername(), dbQuery.getPassword())) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(dbQuery.getSqlQuery());

			while (rs.next()) {
//				System.out.println("id:" + rs.getString(1) + ", short: " + rs.getString(2) + ", long: " + rs.getString(3));
				listOfCourses.add(new DatabaseQueryResultItem(rs.getLong(1), rs.getString(2), rs.getString(3)));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			LOG.error(e.getLocalizedMessage());
		}
		return listOfCourses;
	}
}
