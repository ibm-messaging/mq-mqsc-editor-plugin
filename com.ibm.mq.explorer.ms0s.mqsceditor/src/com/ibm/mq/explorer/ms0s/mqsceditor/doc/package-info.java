/*
 * This package holds the classes that discover and parse out each MQSC Command 
 * as a single document partition.  So each MQSC command and all of it's text is in a separate
 * partition from every other command.  This makes it easier to run individual commands and easier to
 * deal with errors on specific commands, rather than the whole script file.  
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.doc;


