/*
 * These classes generate the custom pages needed by Wizard objects 
 * in the com.ibm.mq.explorer.ms0s.mqscripts.gui pakage.
 * 
 * Some of them extend ContentPage and some of them implement IContentPageFactory.
 * The names of the classes should make it obvious which does which. 
 *  
 * The FolderTableComparator class is used by the FolderTreeContentPage to sort items in the
 * Table that the page creates and manages.  
 * 
 * It extends ViewerComparator and implements the necessary override methods.
 * 
 * This holds true for all of the classes in this package - they implement the necessary
 * methods for their parent class or type. 
 * 
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.pages;


