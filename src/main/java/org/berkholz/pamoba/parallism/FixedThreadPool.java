/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba.parallism;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.berkholz.pamoba.config.MainConfiguration;

/**
 * Class for initializing a fixed thread pool for executing the moodle backup
 * jobs.
 *
 * @author Marcel Berkholz
 */
public class FixedThreadPool {

	// executor service which is initialized with a fixed thread pool
	ExecutorService executor;

	// max started threads at the same time
	private final int maxPoolThreads;

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
		this.executor = Executors.newFixedThreadPool(maxPoolThreads);
	}
	
	/**
	 * METHODS
	 */

}
