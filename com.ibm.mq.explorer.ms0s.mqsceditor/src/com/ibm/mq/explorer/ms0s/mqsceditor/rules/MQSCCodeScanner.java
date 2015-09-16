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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCPartitionScanner;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.IMQSCCommandEventListener;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.IMQSCCommandEventNotifier;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
import com.ibm.mq.explorer.ms0s.mqsceditor.lang.MQSCLanguageConfigurator;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCDocumentModel;
import com.ibm.mq.explorer.ms0s.mqsceditor.util.MQSCColorProvider;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This is a BufferedRuleBasedScanner which attaches the right scanners to the right
 * types of partitions and enables colored text.
 * It also implements the IMQSCCommandEventNotifier to emit events as parsing happens. 
 **/


public class MQSCCodeScanner extends BufferedRuleBasedScanner implements
        IMQSCCommandEventNotifier {

    protected static MQSCColorProvider myProvider;
    @SuppressWarnings("rawtypes")
    private static List fEventListeners;

    protected MQSCCodeScanner() {
    }

    public MQSCCodeScanner(MQSCColorProvider provider) {
        myProvider = provider;
        fEventListeners = new ArrayList<IMQSCCommandEventListener>();
    }

    @SuppressWarnings("rawtypes")
    public BufferedRuleBasedScanner getScannerForPartitionType(
            String partitionType) {
        HashMap<?, ?> opnMap;
        String[] cmdNames = null;
        MQSCLanguageConfigurator myLang;
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            "getting scanner for partition type "
                                    + partitionType, null));
        }

        if (partitionType.equals(MQSCPartitionScanner.MQSC_COMMENT)
                || partitionType.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
            return new MQSCDefaultScanner(myProvider);
        }
        myLang = MQSCEditorPlugin.getDefault().getMQSCLanguageConfiguration();
        cmdNames = myLang.getCommandsForPartitionType(partitionType);
        if (cmdNames == null) {
            return new MQSCDefaultScanner(myProvider);
        }
        opnMap = (HashMap) myLang.getObjectParamMap(cmdNames[0]);
        return new MQSCDefaultScanner(myProvider, cmdNames, opnMap);
    }

    public BufferedRuleBasedScanner getScannerForPartition(
            ITypedRegion partition, int partitionNo) {
        @SuppressWarnings("rawtypes")
        HashMap opnMap;
        String[] cmdNames = null;
        MQSCLanguageConfigurator myLang;
        myLang = MQSCEditorPlugin.getDefault().getMQSCLanguageConfiguration();
        MQSCDocumentModel myDM = MQSCEditorPlugin.getDefault().getMQSCDocumentModel();

        String partitionType = partition.getType();
        if (partitionType.equals(MQSCPartitionScanner.MQSC_COMMENT)
                || partitionType.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
            myDM.remove(""+partitionNo);
            return new MQSCDefaultScanner(myProvider);
        }
        cmdNames = myLang.getCommandsForPartitionType(partitionType);
        opnMap = (HashMap<?, ?>) myLang.getObjectParamMap(cmdNames[0]);
        return new MQSCDefaultScanner(myProvider, cmdNames, opnMap, partition,
                partitionNo);
    }
    @SuppressWarnings("unchecked")
    public void addCommandEventListener(IMQSCCommandEventListener listener,
            boolean registerLocal) {
        if (listener != null) {
            if (!fEventListeners.contains(listener))
                fEventListeners.add(listener);
        }
        if (registerLocal == false) {
            MQSCEditorPlugin.getDefault().getMQSCCommandEventNotifier()
                    .addCommandEventListener(listener, registerLocal);
        }
    }

    public void fireMQSCEventFound(final MQSCCommandEvent eventFound) {
        MQSCEditorPlugin.getDefault().getMQSCCommandEventNotifier()
                .fireMQSCEventFound(eventFound);
        @SuppressWarnings("unchecked")
        List<IMQSCCommandEventListener> list = new ArrayList<IMQSCCommandEventListener>(fEventListeners);
        Iterator<IMQSCCommandEventListener> e = list.iterator();
        while (e.hasNext()) {
            IMQSCCommandEventListener l = (IMQSCCommandEventListener) e.next();
            l.mqscTermFound(eventFound);
        }
        /*
         * Display myDisp = PlatformUI.getWorkbench().getDisplay();
         * myDisp.asyncExec(new Runnable() { public void run() { if
         * (fEventListeners != null && fEventListeners.size() > 0 && eventFound !=
         * null) { List list = new ArrayList(fEventListeners); Iterator e =
         * list.iterator(); while (e.hasNext()) { IMQSCCommandEventListener l =
         * (IMQSCCommandEventListener) e .next(); l.mqscTermFound(eventFound); } } }
         * });
         */
    }

}
