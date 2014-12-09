/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * Created on Jan 28, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts;

import com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent;
import com.ibm.mq.explorer.ui.extensions.IExplorerNotify;

/**
 * @author Jeff Lowrey
 *  
 */
public class MQSCExplorerNotify implements IExplorerNotify {

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#explorerInitialised()
     */
    public void explorerInitialised() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerAdded(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerAdded(ExplorerNotifyEvent arg0) {
/*        MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        List myList = MQSCScriptsPlugin.getDefault().getQmgrList();
        myList.add(element);
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Adding Queue Manager " + element.getName()
                                    + " to Run Qmgr List", null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#isPreventRemoveQueueManager(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public boolean isPreventRemoveQueueManager(ExplorerNotifyEvent arg0) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#isPreventDeleteQueueManager(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public boolean isPreventDeleteQueueManager(ExplorerNotifyEvent arg0) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#isPreventStopQueueManager(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public boolean isPreventStopQueueManager(ExplorerNotifyEvent arg0) {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerRemoved(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerRemoved(ExplorerNotifyEvent arg0) {
/*	Wow this was buggy!  the remove() was inside isDebugging()!
        MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        MQSCScriptsPlugin.getDefault().getQmgrList().remove(element);
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Removing Queue Manager " + element.getName()
                                    + " from Run Qmgr List", null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerDeleted(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerDeleted(ExplorerNotifyEvent arg0) {
/*        MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Queue Manager " + element.getName() + " deleted",
                            null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerStopped(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerStopped(ExplorerNotifyEvent arg0) {
/*        MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Queue Manager " + element.getName() + " stopped",
                            null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerStarted(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerStarted(ExplorerNotifyEvent arg0) {
/*        MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Queue Manager " + element.getName() + " started",
                            null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerShown(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerShown(ExplorerNotifyEvent arg0) {
/*        MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Queue Manager " + element.getName() + " shown",
                            null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerHidden(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void queueManagerHidden(ExplorerNotifyEvent arg0) {
        /*MQQmgrExtObject element = (MQQmgrExtObject) arg0.getObject();
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Queue Manager " + element.getName() + " hidden",
                            null));
        }*/
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#viewOpened(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void viewOpened(ExplorerNotifyEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#viewClosed(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void viewClosed(ExplorerNotifyEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#pluginEnabled(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void pluginEnabled(ExplorerNotifyEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#pluginDisabled(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void pluginDisabled(ExplorerNotifyEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#explorerPreferenceChanged(com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
     */
    public void explorerPreferenceChanged(ExplorerNotifyEvent arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IExplorerNotify#explorerClosing()
     */
    public void explorerClosing() {
    }

}
