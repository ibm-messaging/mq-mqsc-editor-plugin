/*******************************************************************************
 * Copyright (c) 2007,2014 IBM Corporation and other Contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jeff Lowrey - Initial Contribution
 *******************************************************************************/
package com.ibm.mq.explorer.ms0s.mqsceditor.rules;

import org.eclipse.jface.text.rules.Token;

import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This extends the Token class to add minimimal support for MQSCCommandEvents 
 **/


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
