/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

/**
 * Class for defining the command line options.
 * 
 * See, https://commons.apache.org/proper/commons-cli/
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
	 * 
	 * @param args
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
		cmdOptions.addOption("D", "debuglevel", true, "Specify debug level (error, warn, info, debug, trace).");
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
	
	public void validateCmdLineOptions() {
		LOG.trace("Begin of validating command line options.");
		
		
		
		LOG.trace("End of validating command line options.");
	}
	

}
