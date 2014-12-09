/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 * Created on Jan 7, 2007
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.rules;

import org.eclipse.jface.text.rules.Token;

import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;


public class MQSCToken extends Token {
    
    private MQSCCommandEvent myEvent;
    /**
     * @param data
     */
    public MQSCToken(Object data) {
        super(data);
    }
    
    public MQSCCommandEvent getEvent() {
        return myEvent;
    }
    public void setEvent(MQSCCommandEvent myEvent) {
        this.myEvent = myEvent;
    }
}
