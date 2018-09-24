/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba;

import java.io.File;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.berkholz.configurationframework.Configuration;
import org.berkholz.helperfunctions.HelperFunctions;
import org.berkholz.pamoba.config.MainConfiguration;

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
	 * @throws org.apache.commons.cli.ParseException
	 */
	public void setCmdLineOptions() throws ParseException {
		// options definitions
		// TODO: specify default values
		LOG.trace("Begin of defining command line options.");
		cmdOptions.addOption("h", "help", false, "Shows the help.");
//		cmdOptions.addOption("D", "debuglevel", true, "Specify debug level (error, warn, info, debug, trace).");
		cmdOptions.addOption("c", "config-file", true, "Specify the UTF-8 formatted configuration file.");
		cmdOptions.addOption("b", "blacklist-file", true, "Specify the UTF-8 formatted blacklist file. Single course id each line. course ids will be excluded from backup. Blacklist overwrites whitelists.");
		cmdOptions.addOption("w", "whitelist-file", true, "Specify the UTF-8 formatted whitelist file. Single course id each line. Only these course ids will backed up. Entries in blacklist file overwrites whitelist entries.");
		cmdOptions.addOption("d", "dump-config-template", true, "A template configuration file is dumped out. No other action is performed, other options will be dismmissed. When no file name is specified the temporary directory of the operating system is used. Default file name is pamoba.conf.xml.");
		LOG.trace("End of defining command line options.");

		// create a basic parser with above specified commandline options
		LOG.trace("Creating new BasicParser with options.");
		CommandLineParser parser = new GnuParser();

		LOG.trace("Parsing command line options.");
		this.cmdLine = parser.parse(this.cmdOptions, this.args);
	}

	/**
	 * Validate all command line options and trigger actions.
	 */
	public void validateCmdLineOptions() {
		LOG.trace("Begin of validating command line options.");
		this.validateCmdLineOptions_h();
		this.validateCmdLineOption_d();
		this.validateCmdLineOption_c();
		this.validateCmdLineOption_w();
		this.validateCmdLineOption_b();
		LOG.trace("End of validating command line options.");
	}

	/**
	 * Internal method to validate command line option -h.
	 */
	private void validateCmdLineOptions_h() {
		LOG.trace("Validating command line option -h.");
		// check if help should be printed. If option -h or no parameter are specified, help is printed. 
		if (cmdLine.hasOption("h") || cmdLine.getOptions().length == 0) {
			this.printUsage();
			LOG.trace("Exiting.");
			System.exit(0);
		}
	}

	/**
	 * Internal method to validate command line option -c.
	 */
	private void validateCmdLineOption_c() {
		LOG.trace("Validating command line option -c.");

		if (cmdLine.hasOption("c") && !cmdLine.hasOption("d")) {
			if (!HelperFunctions.checkFile(cmdLine.getOptionValue("c"))) {
				LOG.error("Configuration file (" + cmdLine.getOptionValue("c") + ") does not exist or is not readable. Exiting.");
				printUsage();
				System.exit(1);
			}
			LOG.debug("Configuration file (" + cmdLine.getOptionValue("c") + ") found and is readable.");
		} else {
			// check pamoba.conf.xml in home dir
			String localConfigFile = HelperFunctions.getUserHomeDirectory() + File.separator + "pamoba.conf.xml";
			LOG.trace("No configuration file given on command line. Searching for configuration file " + localConfigFile + "in user home directory.");

			if (!HelperFunctions.checkFile(localConfigFile)) {
				LOG.error("No Configuration file given on command line and could not find local configuration file in user home directory. Exiting.");
				System.exit(1);

			}
			LOG.info("Found local configuration (" + localConfigFile + ") in user home directory.");
		}
	}

	/**
	 * Internal method to validate command line option -b.
	 */
	private void validateCmdLineOption_b() {
		LOG.trace("Validating command line option -b.");
		// check if help should be printed. If option -h or no parameter are specified, help is printed. 
		if (cmdLine.hasOption("b")) {
			LOG.info("Option -b not yet implemented.");
		}
	}

	/**
	 * Internal method to validate command line option -w.
	 */
	private void validateCmdLineOption_w() {
		LOG.trace("Validating command line option -.");
		// check if help should be printed. If option -h or no parameter are specified, help is printed. 
		if (cmdLine.hasOption("")) {
			LOG.info("Option -w not yet implemented.");
		}
	}

	/**
	 * Internal method to validate command line option -d.
	 */
	private void validateCmdLineOption_d() {
		String filename;

		LOG.trace("Validating command line option -d.");
		// dump out template config file
		if (cmdLine.hasOption("d")) {

			// file must exist and has to be readable
			if (HelperFunctions.checkFile(cmdLine.getOptionValue("d"))) {
				filename = cmdLine.getOptionValue("d");
			} else {
				LOG.trace("Set default filename in user home dir.");
				filename = HelperFunctions.getUserHomeDirectory() + File.separator + "pamoba.templ.conf.xml";
			}

			// save config with default values  and exit
			LOG.trace("Saving default settings to template configuration file: " + filename);
			Configuration.save(new MainConfiguration(), new File(filename));
			LOG.trace("Exiting.");
			System.exit(0);
		}
	}

	/**
	 * Print the usage of the program.
	 */
	private void printUsage() {
		HelpFormatter formatter = new HelpFormatter();
		System.out.println("PaMoBa - PArallized MOodle BAckup\n");
		formatter.printHelp("pamoba -h | -c <CONFIGURATION_FILE> | -d [ -b <BLACK_LIST_FILE> ] [ -w <WHITE_LIST_FILE> ]", cmdOptions);
	}

	/**
	 * GETTER AND SETTER
	 */
	public CommandLine getCmdLine() {
		return cmdLine;
	}

}
