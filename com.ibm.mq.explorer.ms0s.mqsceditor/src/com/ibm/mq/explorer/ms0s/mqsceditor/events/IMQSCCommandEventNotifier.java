/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 * Created on Jan 7, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.events;

public interface IMQSCCommandEventNotifier {
	public void fireMQSCEventFound(MQSCCommandEvent eventFound);
    public void addCommandEventListener (IMQSCCommandEventListener listener, boolean registerLocal);
}
