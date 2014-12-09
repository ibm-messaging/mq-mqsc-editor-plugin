/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 * Created on Jan 6, 2007
 * 
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.events;

public interface IMQSCCommandEventListener {
    void mqscTermFound(MQSCCommandEvent eventFound);
}
