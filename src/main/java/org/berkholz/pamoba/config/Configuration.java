/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.config;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.berkholz.helperfunctions.HelperFunctions;

/**
 *
 * @author Marcel Berkholz
 */
@XmlRootElement(name = "PaMoBa-Configuration")
public class Configuration {

	/**
	 * VARIABLES
	 */

	// Logger for this class
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Configuration.class.getName());

	@XmlElement(name = "BackupDestinationPath")
	private final String BACKUP_DESTINATION_PATH;

	@XmlElement(name = "PathToPHPBinary")
	private final String PHP_COMMAND;

	@XmlElement(name = "FullBackupMode")
	private final Boolean FULL_BACKUP_MODE;

	@XmlElement(name = "MaximumRunningBackupJobs")
	private final Integer MAXIMUM_RUNNING_BACKUP_JOBS;

	@XmlElement(name = "DatabaseSettings")
	private DatabaseConfiguration DATABASE_SETTINGS;

	@XmlElement(name = "MoodleBackupCliCommand")
	private final String MOODLE_BACKUP_CLI_COMMAND;

	@XmlElement(name = "MoodleBackupCliCommandParameters")
	private final ArrayList MOODLE_BACKUP_CLI_COMMAND_PARAMETERS;

	/**
	 * CONSTRUCTOR
	 */
	public Configuration() {

		this.BACKUP_DESTINATION_PATH = HelperFunctions.getTempDirectory();
		LOG.debug("Setting default backup destination path to: " + this.BACKUP_DESTINATION_PATH);

		// TODO: calculate CPUs and set it in addiction to the number of CPUs. Now we just take 1
		this.MAXIMUM_RUNNING_BACKUP_JOBS = 1;

		// only full backups by default
		this.FULL_BACKUP_MODE = new Boolean(true);

		// TODO: may be we search for the command on different systems
		this.PHP_COMMAND = "/usr/bin/php";

		this.MOODLE_BACKUP_CLI_COMMAND = "/var/www/moodle/admin/cli/backup.php";
		this.MOODLE_BACKUP_CLI_COMMAND_PARAMETERS = new ArrayList();
	}

	/**
	 * GETTER AND SETTER
	 */
	
	public String getBACKUP_DESTINATION_PATH() {
		return BACKUP_DESTINATION_PATH;
	}

	public String getPHP_COMMAND() {
		return PHP_COMMAND;
	}

	public Boolean getFULL_BACKUP_MODE() {
		return FULL_BACKUP_MODE;
	}

	public Integer getMAXIMUM_RUNNING_BACKUP_JOBS() {
		return MAXIMUM_RUNNING_BACKUP_JOBS;
	}

	public DatabaseConfiguration getDATABASE_SETTINGS() {
		return DATABASE_SETTINGS;
	}

	public String getMOODLE_BACKUP_CLI_COMMAND() {
		return MOODLE_BACKUP_CLI_COMMAND;
	}

	public ArrayList getMOODLE_BACKUP_CLI_COMMAND_PARAMETERS() {
		return MOODLE_BACKUP_CLI_COMMAND_PARAMETERS;
	}
	
}
