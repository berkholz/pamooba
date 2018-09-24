/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.parallism;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.berkholz.pamoba.BackupCommand;
import org.berkholz.pamoba.Measure;
import org.berkholz.pamoba.config.MainConfiguration;

/**
 * This class represents a single job running on the command line. It is created
 * and will be added to a new fixed thread pool.
 *
 * @author Marcel Berkholz
 */
public class Job implements Runnable {

	// Logger for this class.
	private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(Job.class.getName());

	// this represents the os process
	private ProcessBuilder psBuilder;
	private MainConfiguration config;
	private final Long id;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * Constructor to create a backup job with given id of a course.
	 *
	 * @param id The id of the moodle course to create the backup job with.
	 * @param mainConfig The main configuration for generating the moodle backup
	 * command.
	 */
	public Job(Long id, MainConfiguration mainConfig) {
		this.id = id;
		this.config = mainConfig;
		psBuilder = new ProcessBuilder();

	}

	@Override
	public void run() {
		Process process;
		Measure measureTime = new Measure(config.getMEASUREMENT_UNIT());
		measureTime.start();

		// log prefix for every process
		String logPrefix = "[" + this.id + "] ";
		LOG.trace("Set prefix for process logging to: " + logPrefix);

		// init the process with the backup command
		List<String> moodleBackupCommand = new BackupCommand(config).getMoodleBackupCommand(id);
		psBuilder = psBuilder.redirectErrorStream(true)
				.command(moodleBackupCommand)
				.directory(new File(config.getBACKUP_DESTINATION_PATH()));
		LOG.trace("Intializing processBuilder object with backup command: " + moodleBackupCommand.toString());

		try {
			process = psBuilder.start();

			// get the standard output from the process
			InputStreamReader inStreamReader = new InputStreamReader(process.getInputStream());
			BufferedReader buffer = new BufferedReader(inStreamReader);

			String lineFromStdout;
			while ((lineFromStdout = buffer.readLine()) != null) {
				LOG.info(logPrefix + lineFromStdout);
			}

		} catch (IOException ex) {
			LOG.error(logPrefix + ex.getLocalizedMessage());
		}

		measureTime.stop();
		LOG.info(logPrefix + "Execution time: " + measureTime.getExecutionTime());
	}

}
