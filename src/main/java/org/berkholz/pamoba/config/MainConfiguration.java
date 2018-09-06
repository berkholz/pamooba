/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.config;

import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.berkholz.helperfunctions.HelperFunctions;

/**
 *
 * @author Marcel Berkholz
 */
@XmlRootElement(name = "PaMoBaConfiguration")
public class MainConfiguration {

	/**
	 * VARIABLES
	 */
	// Logger for this class
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(MainConfiguration.class.getName());

	@XmlElement(name = "BackupDestinationPath")
	private final String BACKUP_DESTINATION_PATH;

	@XmlElement(name = "PathToPHPBinary")
	private final String PHP_COMMAND;

	@XmlElement(name = "FullBackupMode")
	private final Boolean FULL_BACKUP_MODE;

	@XmlElement(name = "MaximumRunningBackupJobs")
	private final Integer MAXIMUM_RUNNING_BACKUP_JOBS;

	@XmlElement(name = "DatabaseSelectSettings")
	private DatabaseSelectConfiguration DATABASE_SELECT_SETTINGS;

	@XmlElement(name = "DatabaseConnectionSettings")
	private DatabaseConnectionConfiguration DATABASE_CONNECTION_SETTINGS;

	@XmlElement(name = "MoodleBackupCliCommand")
	private final String MOODLE_BACKUP_CLI_COMMAND;

	@XmlElement(name = "MoodleBackupCliCommandParameters")
	private final ArrayList MOODLE_BACKUP_CLI_COMMAND_PARAMETERS;

	@XmlElement(name = "BlackListFile")
	private final String BLACK_LIST_FILE;

	@XmlElement(name = "WhiteListFile")
	private final String WHITE_LIST_FILE;

	/**
	 * CONSTRUCTOR
	 */
	public MainConfiguration() {

		this.BACKUP_DESTINATION_PATH = HelperFunctions.getTempDirectory();
		LOG.debug("Setting default backup destination path to: " + this.BACKUP_DESTINATION_PATH);

		// TODO: calculate CPUs and set it in addiction to the number of CPUs. Now we just take 1
		this.MAXIMUM_RUNNING_BACKUP_JOBS = 1;

		// only full backups by default
		this.FULL_BACKUP_MODE = true;

		// TODO: may be we search for the command on different systems
		this.PHP_COMMAND = "/usr/bin/php";

		this.DATABASE_CONNECTION_SETTINGS = new DatabaseConnectionConfiguration();
		this.DATABASE_SELECT_SETTINGS = new DatabaseSelectConfiguration();

		this.MOODLE_BACKUP_CLI_COMMAND = "/var/www/moodle/admin/cli/backup.php";
		this.MOODLE_BACKUP_CLI_COMMAND_PARAMETERS = new ArrayList();

		this.BLACK_LIST_FILE = HelperFunctions.getUserHomeDirectory() + File.separator + "pamoba.blacklist";
		this.WHITE_LIST_FILE = HelperFunctions.getUserHomeDirectory() + File.separator + "pamoba.whitelist";
	}

	/**
	 * METHODS
	 */
	/**
	 *
	 * @return
	 */
	@Override
	// TODO: add all variables to toString()
	// TODO: add javadoc
	public String toString() {
		return String.format(
				"BACKUP_DESTINATION_PATH: %s\n"
				+ "BLACK_LIST_FILE: %s", this.BACKUP_DESTINATION_PATH, this.BLACK_LIST_FILE);
	}

	/**
	 * GETTER AND SETTER
	 */
	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public String getBACKUP_DESTINATION_PATH() {
		return BACKUP_DESTINATION_PATH;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public String getPHP_COMMAND() {
		return PHP_COMMAND;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public Boolean getFULL_BACKUP_MODE() {
		return FULL_BACKUP_MODE;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public Integer getMAXIMUM_RUNNING_BACKUP_JOBS() {
		return MAXIMUM_RUNNING_BACKUP_JOBS;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public DatabaseSelectConfiguration getDATABASE_SELECT_SETTINGS() {
		return DATABASE_SELECT_SETTINGS;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public DatabaseConnectionConfiguration getDATABASE_CONNECTION_SETTINGS() {
		return DATABASE_CONNECTION_SETTINGS;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public String getMOODLE_BACKUP_CLI_COMMAND() {
		return MOODLE_BACKUP_CLI_COMMAND;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public ArrayList getMOODLE_BACKUP_CLI_COMMAND_PARAMETERS() {
		return MOODLE_BACKUP_CLI_COMMAND_PARAMETERS;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public String getBLACK_LIST_FILE() {
		return BLACK_LIST_FILE;
	}

	/**
	 *
	 * @return
	 */
	// TODO: add javadoc
	public String getWHITE_LIST_FILE() {
		return WHITE_LIST_FILE;
	}
}