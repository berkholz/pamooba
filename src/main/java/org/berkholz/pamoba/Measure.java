/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba;

import java.text.DecimalFormat;

/**
 * Class for measuring the execution time of code fragments. Create a new
 * Measure object. To start the measure call the start method. To stop the
 * measure call stop method. The execution time is given with the method
 * getExecutionTime().
 *
 * @author Marcel Berkholz
 */
public class Measure {

	private long startTime;
	private long stopTime;
	private final String unit;

	/**
	 * CONSTRUCTORS
	 */
	/**
	 * Constructor initializes the measure with the specified unit. If no valid
	 * unit is given then minutes will be defined as default unit.
	 *
	 * @param unit Unit for the execution time. Valid units are: <br>
	 * <ul>
	 * <li> S : seconds</li>
	 * <li> M : minutes</li>
	 * <li> H : hours</li>
	 * </ul>
	 */
	public Measure(String unit) {
		startTime = 0;
		stopTime = 0;
		switch (unit.toLowerCase()) {
			case "m":
				this.unit = "M";
				break;
			case "h":
				this.unit = "H";
				break;
			case "s":
				this.unit = "S";
				break;
			default:
				this.unit = "M";
				break;
		}
	}

	/**
	 * METHODS
	 */
	/**
	 * Start the measurement.
	 */
	public void start() {
		this.startTime = System.currentTimeMillis();
	}

	/**
	 * Stop the measurement.
	 */
	public void stop() {
		this.stopTime = System.currentTimeMillis();
	}

	/**
	 * Return the execution time in the initialized unit.
	 *
	 * @return Return the execution time. Unit is specified with constructor.
	 */
	public String getExecutionTime() {
		String returnTime;
		switch (this.unit) {
			case "S":
				returnTime = new DecimalFormat("#0.000000").format((stopTime - startTime) / 1000d);
				break;
			case "M":
				returnTime = new DecimalFormat("#0.00000000").format((stopTime - startTime) / 1000d / 60d);
				break;
			case "H":
				returnTime = new DecimalFormat("#0.0000000000").format((stopTime - startTime) / 1000d / 60d / 60d);
				break;
			// in minutes
			default:
				returnTime = new DecimalFormat("#0.0000000000").format((stopTime - startTime) / 1000d / 60d);
				break;
		}
		return returnTime + " " + this.unit;
	}

	/**
	 * GETTER AND SETTER
	 */
	/**
	 * Get the start time in milli seconds.
	 *
	 * @return Return the start time of the measurement in milli seconds.
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Get the stop time in milli seconds.
	 *
	 * @return Return the stop time of the measurement in milli seconds.
	 */
	public long getStopTime() {
		return stopTime;
	}

}
