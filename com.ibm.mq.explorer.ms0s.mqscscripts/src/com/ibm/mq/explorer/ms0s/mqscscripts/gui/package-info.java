/*
 * These classes provide implementations of all of the commands in the menus accesable from tree nodes 
 * built by the MQSC Script Editor plugins for MQ Explorer.
 * 
 * All of these classes implement extend Wizard and implement INewWizard to provide 
 * the necessary framework to execute their functions.
 * 
 * Each class has the following methods - defined from INewWizard.
 * 
 * run : This method implements the actual logic.  Wherever possible, it uses standard Eclipse dialogs to
 * process user interactions.  The New Script File Wizard has to use a custom dialog. 
 * 
 * selectionChanged :  This method makes sure that each class has a valid pointer 
 * to the currently selected node in the tree.
 * 
 * getActiveNavigator : 
 * Most of these classes use a method to retrieve the correct MQ Explorer Navigator View.
 * All of them *should* use this method. This method should be extracted into a parent class to avoid 
 * unnecessary duplicated code. 
 * 
 *  init:
 *  This is a default method needed for INewWizard interfaces.  
 *  It merely assigns the local selection variable to the current selection.
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.gui;


