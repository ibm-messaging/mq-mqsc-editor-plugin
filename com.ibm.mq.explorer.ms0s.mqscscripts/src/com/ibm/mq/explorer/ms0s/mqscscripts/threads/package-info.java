/*
 * The classes in this package implement the threads to execute the MQSC Script selected
 * against the current queue manaager.   
 * 
 * The IMAMQSCResultAccumulator allows the display of the output from the execution of the script
 * in the results window which is displayed by the RunScriptResultDialog in the gui package. 
 * The RunScriptAction implements this interface to accumulate the results so that it can pass them 
 * to the results dialog. 
 * 
 * The RunMQSCScript class performs the execution of the script file.  It has to parse the file
 * so it can construct the necessary PCF Escape messages.  This parsing is causing a conflict of
 * streams of events that are used by parts of the MQSC Editors. 
 * 
 * Until this conflict is resolved, the outline view won't be correct, and it will be ... extremely hard..
 * to build proper PCF Messages for each command, rather than PCF Escape messages. 
 * 
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.threads;


