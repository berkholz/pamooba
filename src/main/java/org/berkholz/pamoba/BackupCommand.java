/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.berkholz.pamoba;

import java.util.ArrayList;
import java.util.List;
import org.berkholz.pamoba.config.MainConfiguration;

/**
 * Class for generating the moodle backup command.
 *
 * @author Marcel Berkholz
 */
public class BackupCommand {

	private final MainConfiguration mainConfig;

	public BackupCommand(MainConfiguration config) {
		mainConfig = config;
	}

	/**
	 * Get the moodle backup command for executing on the shell.
	 *
	 * @param id The course id to create the backup command for.
	 * @return Return the shell command for backing up moodle courses.
	 */
	public List<String> getMoodleBackupCommand(Long id) {
		List<String> command = new ArrayList();

		command.add(mainConfig.getPHP_COMMAND());
		command.add(mainConfig.getMOODLE_BACKUP_CLI_COMMAND());
		command.add("--courseid=" + id);
		command.add("--destination=" + mainConfig.getBACKUP_DESTINATION_PATH());
		command.addAll(mainConfig.getMOODLE_BACKUP_CLI_COMMAND_PARAMETERS());

		return command;
	}
}
