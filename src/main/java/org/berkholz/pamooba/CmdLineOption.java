/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamooba;

import java.io.File;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.berkholz.configurationframework.Configuration;
import org.berkholz.helperfunctions.HelperFunctions;
import org.berkholz.pamooba.config.MainConfiguration;

/**
 * Class for defining the command line options.
 *
 * See reference for apache common cli for more informations about command line
 * parsing in java, https://commons.apache.org/proper/commons-cli/
 *
 * @author Marcel Berkholz
 */
public class CmdLineOption {

	/**
	 * VARIABLES
	 */
	// Logger for this class.
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(CmdLineOption.class.getName());

	// options object for command line options
	private final Options cmdOptions;

	// parse the command line arguments
	private final String[] args;
	private CommandLine cmdLine;

	// examined configuration filename
	private String configurationFilename;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * Constructor which gets the command line options of the main program.
	 *
	 * @param args Command line options from the main program.
	 */
	public CmdLineOption(String args[]) {
		this.cmdOptions = new Options();
		this.args = args;
	}

	/**
	 * METHODS
	 */
	/**
	 * Method for setup all command line options.
	 *
	 * @throws org.apache.commons.cli.ParseException Throws a ParseException in
	 * case of parsing illegal options.
	 */
	public void setCmdLineOptions() throws ParseException {
		LOG.trace("Begin of defining command line options.");
		cmdOptions.addOption("h", "help", false, "Shows the help.");
		cmdOptions.addOption("D", "debuglevel", true, "Specify the debug level for the main program. Valid levels are: error, warn, info, debug, trace. Default level is info.");
		cmdOptions.addOption("c", "config-file", true, "Specify the UTF-8 formatted configuration file. If no file name is given the pamooba.xml.conf is searched in users home directory. If found it would be used otherwise an error is thrown and the program is terminated.");
		cmdOptions.addOption("b", "blacklist-file", true, "Specify the UTF-8 formatted blacklist file. Single course id each line. Course ids will be excluded from backup. Blacklist overwrites whitelists. If no black list file name is given the pamooba.blacklist is searched in users home directory. If found it would be used otherwise an error is thrown and the program is terminated.");
		cmdOptions.addOption("w", "whitelist-file", true, "Specify the UTF-8 formatted whitelist file. Single course id each line. Only these course ids will backed up. Entries in blacklist file overwrites whitelist entries. If no white list file name is given the pamooba.whitelist is searched in users home directory. If found it would be used otherwise an error is thrown and the program is terminated.");
		cmdOptions.addOption("t", "dump-config-template", true, "Create a template configuration file and save it to given file name. No other action is performed, other options will be dismmissed. When an empty file name is specified (e.g. \"\") the default file name pamooba.templ.conf.xml is created in the temporary directory of the operating system.");
		LOG.trace("End of defining command line options.");

		// create a basic parser with above specified commandline options
		LOG.trace("Creating new GnuParser with options.");
		CommandLineParser parser = new GnuParser();

		LOG.trace("Parsing command line options.");
		this.cmdLine = parser.parse(this.cmdOptions, this.args);
	}

	/**
	 * Meta method to validate all command line options and trigger actions or
	 * set program parameters.
	 */
	public void validateCmdLineOptions() {
		LOG.trace("Begin of validating command line options.");
		this.validateCmdLineOption_D();
		this.validateCmdLineOptions_h();
		this.validateCmdLineOption_t();
		this.validateCmdLineOption_c();
		this.validateCmdLineOption_b();
		this.validateCmdLineOption_w();
		LOG.trace("End of validating command line options.");
	}

	/**
	 * Internal method to validate command line option -h.
	 */
	private void validateCmdLineOptions_h() {
		LOG.trace("Validating command line option -h.");
		// check if help should be printed. If option -h or no parameter are specified, help is printed. 
		if (cmdLine.hasOption("h") || cmdLine.getOptions().length == 0) {
			LOG.info("No command line option given.");
			this.printUsage();
			LOG.trace("Exiting.");
			System.exit(0);
		}
		LOG.trace("End of validating command line option -h.");
	}

	/**
	 * Internal method to validate command line option -c.
	 */
	private void validateCmdLineOption_c() {
		LOG.trace("Validating command line option -c.");

		// Option -c and not option -d found
		if (cmdLine.hasOption("c") && !cmdLine.hasOption("d") && !cmdLine.hasOption("h")) {
			// check if option -c has a value
			if (cmdLine.getOptionValue("c").isEmpty()) {
				//search for local pamooba.conf.xml
				String localConfigFile = HelperFunctions.getUserHomeDirectory() + File.separator + "pamooba.conf.xml";
				LOG.trace("No configuration file given on command line. Searching for configuration file " + localConfigFile + "in user home directory.");

				if (HelperFunctions.checkFile(localConfigFile)) {
					LOG.info("Found local configuration (" + localConfigFile + ") in user home directory.");
					this.configurationFilename = localConfigFile;
				} else {
					LOG.error("Could not find local configuration file in user home directory. Exiting.");
					printUsage();
					System.exit(2);
				}
				// check if file from option -c exist and is readable 
			} else if (HelperFunctions.checkFile(cmdLine.getOptionValue("c"))) {
				LOG.debug("Configuration file (" + cmdLine.getOptionValue("c") + ") found and is readable.");
			} else {
				LOG.error("Configuration file (" + cmdLine.getOptionValue("c") + ") does not exist or is not readable. Exiting.");
			}
		} else {
			LOG.debug("No option -c specified on command line. Exiting...");
			printUsage();
			System.exit(1);
		}
	}

	/**
	 * Internal wrapper method to call the validate method for command line
	 * option -b.
	 */
	private void validateCmdLineOption_b() {
		validateCmdLineOption_lists("b");
	}

	/**
	 * Internal wrapper method to call the validate method for command line
	 * option -w.
	 */
	private void validateCmdLineOption_w() {
		validateCmdLineOption_lists("w");
	}

	/**
	 * Internal wrapper method to validate both list command line options.
	 *
	 * @param listType List type as single String character. "w" for white and
	 * "b" for black list.
	 */
	private void validateCmdLineOption_lists(String listType) {
		LOG.trace("Validating command line option -" + listType + ".");
		String listString = null;
		if (listType == "b") {
			listString = "black";
		} else {
			listString = "white";
		}
		if (cmdLine.hasOption(listType)) {
			// if empty search for local black or white list file
			if (cmdLine.getOptionValue("c").isEmpty()) {
				//search for local pamooba.conf.xml
				String localxListFile = HelperFunctions.getUserHomeDirectory() + File.separator + "pamooba." + listString + "list";
				LOG.trace("No " + listString + "file given on command line. Searching for " + listString + "file " + localxListFile + "in user home directory.");

				if (HelperFunctions.checkFile(localxListFile)) {
					LOG.info("Found local " + listString + " list file (" + localxListFile + ") in user home directory.");
					this.configurationFilename = localxListFile;
				} else {
					LOG.error("Could not find local " + listString + " list file in user home directory. Exiting.");
					printUsage();
					System.exit(5);
				}

			} else {
				// lis file as param given
				String optionValue = cmdLine.getOptionValue(listType);
				// check if file exists and is readable
				if (HelperFunctions.checkFile(optionValue)) {
					LOG.info("Using " + listString + " list file " + optionValue + ".");
				} else {
					LOG.error(listString.substring(0, 1).toUpperCase() + listString.substring(1) + "list file " + optionValue + " does not exist or is not readable.");
					System.exit(6);
				}
			}
		}
		LOG.trace("Finished validating command line option -" + listType + ".");
	}

	/**
	 * Internal method to validate command line option -d.
	 */
	private void validateCmdLineOption_t() {
		String filename;

		LOG.trace("Validating command line option -t.");
		// dump out template config file
		if (cmdLine.hasOption("t")) {

			// file must exist and has to be readable
			if (HelperFunctions.checkFile(cmdLine.getOptionValue("t"))) {
				filename = cmdLine.getOptionValue("t");
			} else {
				LOG.trace("Set default filename in user home dir.");
				filename = HelperFunctions.getUserHomeDirectory() + File.separator + "pamooba.templ.conf.xml";
			}

			// save config with default values  and exit
			LOG.trace("Saving default settings to template configuration file: " + filename);
			Configuration.save(new MainConfiguration(), new File(filename));
			LOG.trace("Exiting.");
			System.exit(10);
		}
	}

	/**
	 * Internal method to validate command line option -D. It checks if debug
	 * command line option is set and if a valid log level is given. If not the
	 * default level info is set. Because the parsing of the command line option
	 * is not the first thing done, the log level is turned after some main and
	 * command line option actions set. If you want to change the debug level
	 * for the complete program from beginning you have to change the log4j2.xml
	 * file in the jar file.
	 *
	 */
	private void validateCmdLineOption_D() {
		LOG.trace("Validating command line option -D.");
		String optionValue = cmdLine.getOptionValue("D");

		if (cmdLine.hasOption("D")) {
			if (optionValue.isEmpty()) {
				Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);
				LOG.info("Setting debug level to default: info");
			} else {
				switch (optionValue.toLowerCase()) {
					case "info":
						Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);
						break;
					case "debug":
						Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.DEBUG);
						break;
					case "error":
						Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.ERROR);
						break;
					case "trace":
						Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.TRACE);
						break;
					case "warn":
						Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.WARN);
						break;
					default:
						Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.INFO);
						break;
				}
			}
		} else {
			LOG.info("Letting the debug level on files log4j2.xml values.");
		}
	}

	/**
	 * Print the usage of the program.
	 */
	private void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		System.out.println("PaMooBa - PArallized MOOdle BAckup\n");
		formatter.printHelp("pamooba -h | -c <CONFIGURATION_FILE> | -t [ -D <DEBUGLEVEL> ] [ -b <BLACK_LIST_FILE> ] [ -w <WHITE_LIST_FILE> ]", cmdOptions);
	}

	/**
	 * GETTER AND SETTER
	 */
	/**
	 * Get command line options.
	 *
	 * @return Return the cmdLine.
	 */
	public CommandLine getCmdLine() {
		return cmdLine;
	}

	/**
	 * Get the full name of the examined configuration file.
	 *
	 * @return Return configuration file name.
	 */
	public String getConfigurationFilename() {
		return configurationFilename;
	}

}
