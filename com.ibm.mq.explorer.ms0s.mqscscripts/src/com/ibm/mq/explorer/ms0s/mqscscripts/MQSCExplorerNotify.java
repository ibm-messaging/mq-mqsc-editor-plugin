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
 */
/*
 * <p>
 * This is the base required class to process MQExplorer object change
 * notifications. All of my initial attempts to implement any custom handling of
 * these notifications were unnecessary. And fairly buggy. <p>
 * 
 * Bad idea code is commented out. It should eventually be removed.
 */
public class MQSCExplorerNotify implements IExplorerNotify {

	public void explorerInitialised() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerAdded(com
	 * .ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerAdded(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#isPreventRemoveQueueManager
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public boolean isPreventRemoveQueueManager(ExplorerNotifyEvent arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#isPreventDeleteQueueManager
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public boolean isPreventDeleteQueueManager(ExplorerNotifyEvent arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#isPreventStopQueueManager
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public boolean isPreventStopQueueManager(ExplorerNotifyEvent arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerRemoved
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerRemoved(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerDeleted
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerDeleted(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerStopped
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerStopped(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerStarted
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerStarted(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerShown(com
	 * .ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerShown(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#queueManagerHidden(
	 * com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void queueManagerHidden(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#viewOpened(com.ibm.
	 * mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void viewOpened(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#viewClosed(com.ibm.
	 * mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void viewClosed(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#pluginEnabled(com.ibm
	 * .mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void pluginEnabled(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#pluginDisabled(com.
	 * ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
	 */
	public void pluginDisabled(ExplorerNotifyEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.IExplorerNotify#explorerPreferenceChanged
	 * (com.ibm.mq.explorer.ui.extensions.ExplorerNotifyEvent)
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
