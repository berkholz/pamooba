/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.parallism;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.berkholz.pamoba.config.MainConfiguration;

/**
 * Class for initializing a fixed thread pool for executing the moodle backup
 * jobs.
 *
 * After creating an instance of a FixedThreadPool jobs (Runnable) must be
 * added. After all jobs are added the submit method have to be called to
 * execute all jobs in the FixedThreadPool.
 *
 * @author Marcel Berkholz
 */
public class FixedThreadPool {

	// logger
	private static final Logger LOG = LogManager.getLogger(FixedThreadPool.class.getName());

	// executor service which is initialized with a fixed thread pool
	ExecutorService executor;

	// max started threads at the same time
	private final int maxPoolThreads;

	private List<Job> jobList;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * constructor which creates an executor service with a fixed thread pool
	 * with maximum running backup jobs specified in the config.
	 *
	 * @param config Configuration with the value of the MAX_RUNNING_BACKUP_JOBS
	 * for limiting the maximum of parallel executed threads.
	 */
	public FixedThreadPool(MainConfiguration config) {
		this.maxPoolThreads = config.getMAXIMUM_RUNNING_BACKUP_JOBS();
		this.jobList = new ArrayList<>();
		this.executor = Executors.newFixedThreadPool(maxPoolThreads);
	}

	/**
	 * METHODS
	 */
	/**
	 * Adds a given job to an internal list. The jobs in this list will be
	 * submitted to the fixed thread pool.
	 *
	 * @param job Job to add to the list of jobs that should be submitted to the
	 * fixed thread pool.
	 */
	public void addJob(Job job) {
		this.jobList.add(job);
	}

	/**
	 * Submit all jobs in the internal List of jobs to the FixedThreadPool.
	 */
	public void submit() {
		// check if job list is empty
		if (this.jobList.isEmpty() || this.jobList == null) {
			LOG.error("List of jobs are empty. Nothing to do. Eciting...");
			System.exit(20);
		} else {
			// all jobs are submitted 
			// TODO: test the lambda expression in openjdk 			
			LOG.debug("Found jobs and submitting them to the fixed thread pool.");
			jobList.stream().forEach((task) -> {
				LOG.debug("Adding job with id (" + task.getId() + ") to executor service.");
				executor.submit(task);
			});
		}

	}// end submit()

	// TODO: shutdown executor service
	public void shutdown() {

	}
}
