
import java.io.File;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.berkholz.configurationframework.Configuration;
import org.berkholz.pamoba.CmdLineOption;
import org.berkholz.pamoba.Measure;
import org.berkholz.pamoba.config.MainConfiguration;
import org.berkholz.pamoba.dbms.DatabaseQuery;
import org.berkholz.pamoba.dbms.DatabaseQueryResult;
import org.berkholz.pamoba.dbms.DatabaseQueryResultItem;
import org.berkholz.pamoba.parallism.Job;
import org.berkholz.pamoba.parallism.ThreadPool;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 * Exit codes: <br>
 * <ul>
 * <li> 0 : No errors.</li>
 * <li> 1 : No configuration file given. </li>
 * <li> 2 : No local configuration file found, but command line option -c
 * given.</li>
 * <li> 4: No valid black list file given.</li>
 * <li> 5: No local black or white list file found.</li>
 * <li> 6: Black or white list file not found or not readable.</li>
 * <li> 10: Dummy configuration file saved to file.</li>
 * <li>20 : No jobs where added to internal List. </li>
 * <li></li>
 * </ul>
 *
 *
 * @author Marcel Berkholz
 */
public class Main {

	// Logger for this class. See, https://logging.apache.org/log4j/2.x/
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) throws SQLException {

		// MEASURE START
		Measure measureTime = new Measure('M');
		measureTime.start();
		LOG.debug("Defining start time for measuring the execution time of the complete program: " + measureTime.getStartTime());

		// COMMAND LINE PARSING
		LOG.debug("Initiating the command line options.");
		CmdLineOption commandLineOptions = new CmdLineOption(args);
		try {
			commandLineOptions.setCmdLineOptions();
		} catch (ParseException ex) {
			LOG.error("Something went wrong on parsing the command line options: " + ex.getLocalizedMessage());
		}
		commandLineOptions.validateCmdLineOptions();

		// CONFIG LOAD
		MainConfiguration mainConfig = (MainConfiguration) Configuration.load(MainConfiguration.class, new File(commandLineOptions.getCmdLine().getOptionValue("c")));

		// set the corret measurement unit
		measureTime.setUnit(mainConfig.getMEASUREMENT_UNIT());

		// intialize FixedThreadPool
		ThreadPool ftp = new ThreadPool(mainConfig);

		// PRINT CONFIG
		LOG.info("Using the following configuration settings:\n" + mainConfig.print());

		// QUERY COURSE IDs
		LOG.trace("Create database query with config file.");
		DatabaseQuery dbq = new DatabaseQuery(mainConfig);
		LOG.trace("Read in all course from database.");
		DatabaseQueryResult dbqr = new DatabaseQueryResult(dbq);

		// filter white list, before black list
		LOG.trace("Set white list file.");
		dbqr.setWhitelist(commandLineOptions.getCmdLine().getOptionValue("w"));

		// process blacklist
		LOG.trace("Set black list file");
		dbqr.setBlacklist(commandLineOptions.getCmdLine().getOptionValue("b"));

		LOG.trace("Get courses calculated with black and white list.");
		List courses = dbqr.getCourses();

		LOG.trace("Iterate over all courses");
		for (Iterator iterator = courses.iterator(); iterator.hasNext();) {
			DatabaseQueryResultItem next = (DatabaseQueryResultItem) iterator.next();
			LOG.debug(next.getId() + ":" + next.getShortDescription() + ":" + next.getDescription());
			// add new Job with id an dmainconfig to fixedThreadPool
			LOG.trace("Add new Job with id " + next.getId() + "an dmainconfig to fixedThreadPool.");
			ftp.addJob(new Job(next.getId(), mainConfig));
		}

		LOG.trace("Submit all jobs in FixedThreadPool.");
		ftp.submit();

		// shutdown 
		LOG.trace("Shutdown execution service.");
		ftp.shutdown();

		// MEASURE END
//		long measureEndTime = System.currentTimeMillis();
		measureTime.stop();
		LOG.debug("Defining end time for measuring the execution time of the complete program: " + measureTime.getStopTime());

		// LOG
		LOG.info(String.format("Execution time: %s ", measureTime.getExecutionTime()));

	}
}
