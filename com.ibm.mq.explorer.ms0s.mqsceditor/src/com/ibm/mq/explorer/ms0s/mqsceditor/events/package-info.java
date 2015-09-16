/*
 * This package holds a complicated set of event classes for working with MQSC document content.
 * 
 * The intent is to provide a publisher that produces events as a document is parsed, 
 * to identify what has been parsed and where in the document.
 * 
 * This has two purposes: 
 * 1) to make the outline view update as you type, 
 * 2) to enable an method of buliding a PCF message that corresponds to the MQSC message.
 * 
 * The first goal does not work very well, and the second goal is not implemented yet.  
 * It remains a hard problem to programatically get the PCF objects for a given piece of MQSC Text.
 * 
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.events;


