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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This provides the implementation of the MQSC Command Event Notifier
 * to emit events that registered producers want to send.
 * It simply calls methods on each registered listener to pass it the event that's
 * been received.  
 *   
 **/

public class MQSCCommandEventNotifier implements IMQSCCommandEventNotifier {
    private static List<IMQSCCommandEventListener> fEventListeners;

    public MQSCCommandEventNotifier() {
        super();
        fEventListeners = new ArrayList<IMQSCCommandEventListener>();
    }

    public void addCommandEventListener(IMQSCCommandEventListener listener,
            boolean registerLocal) {
        if (listener != null) {
            if (!fEventListeners.contains(listener))
                fEventListeners.add(listener);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void fireMQSCEventFound(final MQSCCommandEvent eventFound) {
        //TODO: THINK ABOUT THIS AGAIN - re the threading.
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            "MQSCCommandEventNotifier.fireMQSCEventFound : "+eventFound.toString(), null));
        }
        List list = new ArrayList<IMQSCCommandEventListener>(fEventListeners);
        Iterator<IMQSCCommandEventListener> e = list.iterator();
        while (e.hasNext()) {
            IMQSCCommandEventListener l = (IMQSCCommandEventListener) e
                    .next();
            l.mqscTermFound(eventFound);
        }
/*        Display myDisp = PlatformUI.getWorkbench().getDisplay();
        myDisp.asyncExec(new Runnable() {
            public void run() {
                if (fEventListeners != null && fEventListeners.size() > 0
                        && eventFound != null) {
                    List list = new ArrayList(fEventListeners);
                    Iterator e = list.iterator();
                    while (e.hasNext()) {
                        IMQSCCommandEventListener l = (IMQSCCommandEventListener) e
                                .next();
                        l.mqscTermFound(eventFound);
                    }
                }
            }
        }); */
    }

}