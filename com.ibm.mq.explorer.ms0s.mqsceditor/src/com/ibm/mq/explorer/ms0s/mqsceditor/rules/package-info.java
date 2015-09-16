/*
 * The classes in this package create all of the MQ Navigator tree nodes.
 * 
 * The factories are associated in the plugin properties with the relevant factories. 
 * 
 * The MQSSCriptsTreeNodeFactory creates the basic Root node - either "MQSC Scripts" or "MQSC Script Projects",
 * depending on the preference. 
 * 
 * The MQSCScriptsFileNodeFactory uses a Visitor object to visit files that have .mqsc extensions, and
 * adds them to a private list.  When a TreeNode is to be added, it uses this list to create the necessary
 * parent folders and add the files in the right level.  
 * 
 *  The objects created by the factory all extend the MQExplorer TreeNode object, and provide specific
 *  behavior for that type of node (Root,Project,File).  
 * 
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.rules;


