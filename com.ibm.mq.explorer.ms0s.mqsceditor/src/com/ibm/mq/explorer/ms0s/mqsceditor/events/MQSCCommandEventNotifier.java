/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 * Created on Feb 9, 2007
 * 
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;

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