
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.berkholz.pamoba.CmdLineOption;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Marcel Berkholz
 */
public class Main {

	// Logger for this class. See, https://logging.apache.org/log4j/2.x/
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		long measureStartTime = System.currentTimeMillis();
		LOG.debug("Defining start time for measuring the execution time of the complete program: " + measureStartTime);
		
		LOG.debug("Initiating command line options.");
		CmdLineOption commandLineOptions = new CmdLineOption(args);
		try {
			commandLineOptions.setCmdLineOptions();
		} catch (ParseException ex) {
			LOG.error("Something went wrong on parsing the command line options: " + ex.getLocalizedMessage());
		}
		
		long measureEndTime = System.currentTimeMillis();
		LOG.debug("Defining end time for measuring the execution time of the complete program: " + measureEndTime);
		
		LOG.info(String.format("Backup execution time in seconds: %d", measureEndTime - measureStartTime));
		
	}
}
