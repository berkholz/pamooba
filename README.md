**Pa**rallized **Moo**dle **Ba**ckup (pamooba) is a tool written in Java to parallize the buildin backup script of the learning management platform [Moodle](https://www.moodle.org/). 

Moodles backup is organized as php-script called by a cronjob. This script runs with a single thread, so that backups on large setups will keep running for a long time. 

The backup script can be run for a specific course id to only backup this course. This mechanism is used by pamooba to run the script for each course simultaniously. To avoid overloading an active queue is used to execute only a defined number of parallel backups.

# How to install?

The installation depends on your server setup. This is just an example installation.

First we create a directory for pamooba binaries, we take as base directory /opt

    mkdir -p /opt/pamooba/bin


To run pamooba we need an java runtime environment. So, we have to install it:

    # debian
    apt install openjdk-11-jre

    # fedora/cent os
    dnf install java-11-openjdk
    yum install java-11-openjdk

Now, we have to download the pamooba release:

    cd /opt/pamooba/bin
    curl -L -O https://github.com/berkholz/pamooba/releases/download/v0.1.2/PaMooBa-0.1.2-jar-with-dependencies.jar


# How to setup?
We create a directory for the pamooba configration file and a template configuration file:

    mkdir -p /opt/pamooba/conf/
    java -jar /opt/pamooba/bin/PaMooBa-0.1.2-jar-with-dependencies.jar -t /opt/pamooba/conf/pamooba.conf.xml

Edit configuration file to meet your server setup.
The following table list the options and a short explanation:
| config option | explanation | default value | possible values |
| ------------- | ----------- | ------------- | --------------- |
| BACKUP_DESTINATION_PATH | absolute path where the backups should be stored | /tmp | <PATH> |
| MEASUREMENT_UNIT | | M | |
| MAXIMUM_RUNNING_BACKUP_JOBS | | 5 |  |
| FULL_BACKUP_MODE | | true | true, false |
| PHP_COMMAND | | /usr/bin/php | <PATH> |
| DATABASE_NAME | | moodle | <DB_NAME> |
| DATABASE_HOSTNAME | | localhost | localhost, <IP_ADDRESS> |
| DATABASE_PORT | | 3306 | 3306, 5432, <PORT_NUMBER> |
| DATABASE_TYPE | | myslq | mysql, postgresql |
| DATABASE_USERNAME | | moodleuser | |
| DATABASE_PASSWORD | | secret | |
| DATABASE_SSL_ENABLED | | | |
| DATABASE_SELECT_CONDITION | | - | |
| DATABASE_TABLE_ID_COLUMN | | id | |
| DATABASE_TABLE_SHORT_DESCRIPTION_COLUMN | | shortname | |
| DATABASE_TABLE_DESCRIPTION_COLUMN | | fullname | |
| DATABASE_TABLE_NAME | | mdl_course | <TABLE_NAME> |
| MOODLE_BACKUP_CLI_COMMAND | | /var/www/moodle/admin/cli/backup.php | <PATH> |
| MOODLE_BACKUP_CLI_COMMAND_PARAMETERS | | | |
| BLACK_LIST_FILE | | /tmp/pamooba.blacklist.xml | <PATH> |
| WHITE_LIST_FILE | | /tmp/pamooba.whitelist.xml | <PATH> |

    
# How to use?

You have two choices: manual or automatic execution via cronjob.

### manual execution 

    java -jar /opt/pamooba/bin/PaMooBa-0.1.2-jar-with-dependencies.jar -c /opt/pamooba/conf/pamooba.conf.xml

### cronjob execution
    
    # execute pamooba every day at 2 am and save output to /opt/pamooba/pamooba.$(date +%Y%m%d).log
    0 2 0 0 0 /usr/bin/java -jar /opt/pamooba/bin/PaMooBa-0.1.2-jar-with-dependencies.jar -c /opt/pamooba/conf/pamooba.conf.xml &>> /opt/pamooba/log/pamooba.$(date +%Y%m%d).log

    
> Note: You have to create the log directory first, when adding the cronjob.


# How to debug?
For debugging purposes you can call pamooba with command line option -D. 

Possible command line option values are:
 * error
 * warn
 * info
 * debug
 * trace

    
> Note: For more infos about log levels, see https://logging.apache.org/log4j/2.x/manual/customloglevels.html.

    
An example call is: 
    
    java -jar /opt/pamooba/bin/PaMooBa-0.1.2-jar-with-dependencies.jar -c /opt/pamooba/conf/pamooba.conf.xml -D trace


# How to build?
For build instructions see [BUILD.md](BUILD.md)
