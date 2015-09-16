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
package com.ibm.mq.explorer.ms0s.mqsceditor.events;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This defines an interface for relevant listeners to receive
 * MQSC Command Events. These are events that are emitted from 
 * the fine grained parsing of the MQSC document. 
 * 
 * This was intended to enable the outline view to update asynchronously
 * instead of when the document is saved.
 * However, these events are emitted at least twice for each document, and
 * so the Outline view is inconsistent. 
 * 
 **/

public interface IMQSCCommandEventListener {
    void mqscTermFound(MQSCCommandEvent eventFound);
}
