/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamooba.config;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.berkholz.helperfunctions.HelperFunctions;

/**
 * Main configuration definition for the xml configuration file. It contains the
 * database connection and select settings as variables from other classes.
 *
 * @author Marcel Berkholz
 */
@XmlRootElement(name = "PaMooBaConfiguration")
@XmlAccessorType(XmlAccessType.PROPERTY)
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

    @XmlElement(name = "ExecutorShutdownTimeout")
    private final Long EXECUTOR_SHUTDOWN_TIMEOUT;

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
     * Define the unit in which the execution time is calculated and printed.
     * Possible values are: <br>
     * <ul>
     * <li>S for seconds, </li>
     * <li>M for minutes, </loi>
     * <li>H for hours.</li>
     * </ul>
     */
    @XmlElement(name = "MeasurementUnit")
    private final char MEASUREMENT_UNIT;

    /**
     * CONSTRUCTOR
     */
    /**
     * Constructor for creating a basic configuration file with default values.
     */
    public MainConfiguration() {

        this.BACKUP_DESTINATION_PATH = HelperFunctions.getTempDirectory();
        LOG.debug("Setting default backup destination path to: " + this.BACKUP_DESTINATION_PATH);

        this.MEASUREMENT_UNIT = 'M';

        // calculate CPUs and set it in addiction to the number of CPUs. 
        this.MAXIMUM_RUNNING_BACKUP_JOBS = Runtime.getRuntime().availableProcessors() <= 2 ? 1 : Runtime.getRuntime().availableProcessors() - 1;

        // timeout in minutes for awaiting shutdown of the executor service
        this.EXECUTOR_SHUTDOWN_TIMEOUT = 5L;

        // only full backups by default
        this.FULL_BACKUP_MODE = true;

        // TODO: may be we search for the command on different systems
        this.PHP_COMMAND = "/usr/bin/php";

        this.DATABASE_CONNECTION_SETTINGS = new DatabaseConnectionConfiguration();
        this.DATABASE_SELECT_SETTINGS = new DatabaseSelectConfiguration();

        this.MOODLE_BACKUP_CLI_COMMAND = "/var/www/moodle/admin/cli/backup.php";
        this.MOODLE_BACKUP_CLI_COMMAND_PARAMETERS = new ArrayList();

        this.BLACK_LIST_FILE = HelperFunctions.getUserHomeDirectory() + File.separator + "pamooba.blacklist";
        this.WHITE_LIST_FILE = HelperFunctions.getUserHomeDirectory() + File.separator + "pamooba.whitelist";
    }

    /**
     * METHODS
     */
    /**
     * Override the default toString() method to get a string representation of
     * all configuration settings.
     *
     * @return Returns all class constants as string representation. Override is
     * used to print the configuration settings.
     */
    public String print() {
        return String.format(
                "\tBACKUP_DESTINATION_PATH:\t\t%s\n"
                + "\tMEASUREMENT_UNIT:\t\t%s\n"
                + "\tMAXIMUM_RUNNING_BACKUP_JOBS:\t\t%s\n"
                + "\tEXECUTOR_SHUTDOWN_TIMEOUT:\t\t%s\n"
                + "\tFULL_BACKUP_MODE:\t\t\t%s\n"
                + "\tPHP_COMMAND:\t\t\t\t%s\n"
                + "\tDATABASE_CONNECTION_SETTINGS:\t%s\n"
                + "\tDATABASE_SELECT_SETTINGS:\t%s\n"
                + "\tMOODLE_BACKUP_CLI_COMMAND:\t\t%s\n"
                + "\tMOODLE_BACKUP_CLI_COMMAND_PARAMETERS:\t%s\n"
                + "\tBLACK_LIST_FILE:\t\t\t%s\n"
                + "\tWHITE_LIST_FILE:\t\t\t%s\n", BACKUP_DESTINATION_PATH, MEASUREMENT_UNIT, MAXIMUM_RUNNING_BACKUP_JOBS, EXECUTOR_SHUTDOWN_TIMEOUT, FULL_BACKUP_MODE, PHP_COMMAND, DATABASE_CONNECTION_SETTINGS, DATABASE_SELECT_SETTINGS, MOODLE_BACKUP_CLI_COMMAND, MOODLE_BACKUP_CLI_COMMAND_PARAMETERS, BLACK_LIST_FILE, WHITE_LIST_FILE);
    }

    /**
     * GETTER AND SETTER
     */
    /**
     * Get the destination path where backups will be saved to.
     *
     * @return Return the path where backups should be stored.
     */
    public String getBACKUP_DESTINATION_PATH() {
        return BACKUP_DESTINATION_PATH;
    }

    /**
     * Get the full path and file name of PHP binary.
     *
     * @return Return the full path to PHP command binaries.
     */
    public String getPHP_COMMAND() {
        return PHP_COMMAND;
    }

    /**
     * Method for indicating the backup mode.
     *
     * @return True if backup should be made in FULL MODE, otherwise false.
     */
    public Boolean getFULL_BACKUP_MODE() {
        return FULL_BACKUP_MODE;
    }

    /**
     * Get the maximum amount of jobs running parallel.
     *
     * @return Return the maximum of parallel running jobs.
     */
    public Integer getMAXIMUM_RUNNING_BACKUP_JOBS() {
        return MAXIMUM_RUNNING_BACKUP_JOBS;
    }

    /**
     * Get the setting for select of the database.
     *
     * @return Return the select setting for querying the database.
     */
    public DatabaseSelectConfiguration getDATABASE_SELECT_SETTINGS() {
        return DATABASE_SELECT_SETTINGS;
    }

    /**
     * Get the database connection setting, e.g. database user, password etc.
     *
     * @return Return the database connection settings.
     */
    public DatabaseConnectionConfiguration getDATABASE_CONNECTION_SETTINGS() {
        return DATABASE_CONNECTION_SETTINGS;
    }

    /**
     * Get the moodle backup command.
     *
     * @return Return the backup command in moodle for backup.
     */
    public String getMOODLE_BACKUP_CLI_COMMAND() {
        return MOODLE_BACKUP_CLI_COMMAND;
    }

    /**
     * Get the moodle backup command parameters.
     *
     * @return Return the parameters for the moodle backup command.
     */
    public ArrayList getMOODLE_BACKUP_CLI_COMMAND_PARAMETERS() {
        return MOODLE_BACKUP_CLI_COMMAND_PARAMETERS;
    }

    /**
     * Get the file name of the black list file.
     *
     * @return Return the file name of the black list.
     */
    public String getBLACK_LIST_FILE() {
        return BLACK_LIST_FILE;
    }

    /**
     * Get the file name of the white list file.
     *
     * @return Return the file name of the white list.
     */
    public String getWHITE_LIST_FILE() {
        return WHITE_LIST_FILE;
    }

    /**
     * Get the measurement unit.
     *
     * @return Return the measure unit.
     */
    public char getMEASUREMENT_UNIT() {
        return MEASUREMENT_UNIT;
    }

    /**
     * Get the shutdown timeout for the executor service.
     *
     * @return Return the executor shutdown timeout.
     */
    public Long getEXECUTOR_SHUTDOWN_TIMEOUT() {
        return EXECUTOR_SHUTDOWN_TIMEOUT;
    }

}
