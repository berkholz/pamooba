**Pa**rallized **Moo**dle **Ba**ckup (pamooba) is a tool written in Java to parallize the buildin backup script of the learning management platform [Moodle](https://www.moodle.org/). 

Moodles backup is organized as php-script called by a cronjob. This script runs with a single thread, so that backups on large setups will keep running for a long time. 

The backup script can be run for a specific course id to only backup this course. This mechanism is used by pamooba to run the script for each course simultaniously. To avoid overloading an active queue is used to execute only a defined number of parallel backups.

# How to setup?


# How to use?


# How to debug?


# How to build?
For build instructions see [BUILD.md](BUILD.md)
