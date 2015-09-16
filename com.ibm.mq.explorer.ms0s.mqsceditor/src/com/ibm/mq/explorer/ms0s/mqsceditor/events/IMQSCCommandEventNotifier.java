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
 * This defines an interface that allows document parsers to send
 * MQSC Command events as they find each part of the document. 
 *   
 **/
public interface IMQSCCommandEventNotifier {
	public void fireMQSCEventFound(MQSCCommandEvent eventFound);
    public void addCommandEventListener (IMQSCCommandEventListener listener, boolean registerLocal);
}
