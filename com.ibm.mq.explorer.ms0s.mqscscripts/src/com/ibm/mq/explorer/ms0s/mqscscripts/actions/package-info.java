/*
 * These classes provide implementations of all of the commands in the menus accesable from tree nodes 
 * built by the MQSC Script Editor plugins for MQ Explorer.
 * 
 * All of these classes implement an IActionDelegate to provide the necessary framework to execute their
 * functions.
 * 
 * Most of these classes use standard dialogs and wizards to interact with the user.  
 * The New Script File action and the Run Script Action do not. 
 * 
 * Each class has the following methods - defined from IActionDelegate.
 * 
 * performFinish :
 * This is the Wizard method that gets called when the Finish button 
 * (or the okay button or etc).  
 * 
 * doFinish :
 * This method performs the actual work of the Wizard. It creates the new file or executes the file. 
 * 
 * addPages : 
 * This method sets up the dialog by inserting all of the necessary WizardPage extension objects.
 *   
 *    
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

