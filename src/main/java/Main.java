
import java.io.File;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.berkholz.configurationframework.Configuration;
import org.berkholz.helperfunctions.HelperFunctions;
import org.berkholz.pamoba.CmdLineOption;
import org.berkholz.pamoba.config.MainConfiguration;
import org.berkholz.pamoba.dbms.DatabaseQuery;
import org.berkholz.pamoba.dbms.DatabaseQueryResult;

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

	public static void main(String[] args) throws SQLException {

		// MEASURE START
		long measureStartTime = System.currentTimeMillis();
		LOG.debug("Defining start time for measuring the execution time of the complete program: " + measureStartTime);

		// COMMAND LINE PARSING
		LOG.debug("Initiating the command line options.");
		CmdLineOption commandLineOptions = new CmdLineOption(args);
		try {
			commandLineOptions.setCmdLineOptions();
		} catch (ParseException ex) {
			LOG.error("Something went wrong on parsing the command line options: " + ex.getLocalizedMessage());
		}

		// CONFIG SAVE
//		Configuration.save(new MainConfiguration(), new File(HelperFunctions.getUserHomeDirectory() + File.separator + "pamoba.conf.xml"));
		// CONFIG LOAD
		MainConfiguration mainConfig = (MainConfiguration) Configuration.load(MainConfiguration.class, new File(HelperFunctions.getUserHomeDirectory() + File.separator + "pamoba.conf.xml"));

		// PRINT CONFIG
		LOG.info("Using the following configuration settings:\n" + mainConfig.print());

		// QUERY COURSE IDs
		DatabaseQuery qdb = new DatabaseQuery(mainConfig);
		qdb.initializeConnectionURL();

		List courses = qdb.getCourses();

		for (Iterator iterator = courses.iterator(); iterator.hasNext();) {
			DatabaseQueryResult next = (DatabaseQueryResult) iterator.next();
			System.out.println(next.getId());

		}

		// MEASURE END
		long measureEndTime = System.currentTimeMillis();
		LOG.debug("Defining end time for measuring the execution time of the complete program: " + measureEndTime);

		// LOG
		LOG.info(String.format("Execution time: %s %s",
				new DecimalFormat("#0.0000").format((measureEndTime - measureStartTime) / 1000d), "seconds"));

	}
}
