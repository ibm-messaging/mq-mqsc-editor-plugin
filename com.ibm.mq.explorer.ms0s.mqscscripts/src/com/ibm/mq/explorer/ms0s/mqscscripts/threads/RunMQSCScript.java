/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on May 22, 2007
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.threads;

import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.ui.navigator.CommonNavigator;

import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.commonservices.internal.trace.Trace;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.constants.CMQCFC;
import com.ibm.mq.explorer.core.internal.objects.DmQueueManager;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCPartitionScanner;
import com.ibm.mq.explorer.ms0s.mqsceditor.doc.MQSCPartitioner;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCCodeScanner;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCToken;
import com.ibm.mq.explorer.ms0s.mqscscripts.IMQSCScriptsConstants;
import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.gui.RunSkipRunAllSkipAllDialog;
import com.ibm.mq.explorer.ui.extensions.MQQmgrExtObject;
import com.ibm.mq.explorer.ui.internal.queuemanager.UiQueueManager;
import com.ibm.mq.pcf.MQCFIN;
import com.ibm.mq.pcf.MQCFST;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFParameter;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class RunMQSCScript implements IRunnableWithProgress {
    /** The MQSC string */
    private Document mqscDoc;

    /** The Queue Manager on which the MQSC will be executed */
    private UiQueueManager queueManager;

    private MQQueueManager mqQM;

    MQQueue adminQueue = null;
    MQQueue replyQueue = null;

    private String fileName;

    private IMQSCResultAccumulator resultStore;

    private boolean skip = false;

    private boolean ask = true;

    private boolean cancel = false;

    private RunSkipRunAllSkipAllDialog skipDialog = null;

    private String holdPartitionText;
    private CommonNavigator MQView;

    /**
     * @param mqscDoc
     * @param queueManager
     */
    public RunMQSCScript(Document mqscDoc, MQQmgrExtObject queueManager,
            String fileName, IMQSCResultAccumulator resultStore,CommonNavigator MQView) {
        super();
        this.mqscDoc = mqscDoc;
//        this.queueManager = (UiQueueManager) (queueManager.getInstanceId());
//        this.mqQM = queueManager.getMQQueueManager();
        this.fileName = fileName;
        this.resultStore = resultStore;
        this.MQView = MQView;
        skip = false;
        ask = true;
    }
    public void setQmgrObject(MQQmgrExtObject queueManager) {
        this.queueManager = (UiQueueManager) (queueManager.getInstanceId());
        this.mqQM = queueManager.getMQQueueManager();
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run(IProgressMonitor progressMonitor) {
        String qmgrName;
        // attach partition scanner
        MQSCPartitionScanner scanner = MQSCEditorPlugin.getDefault()
                .getMQSCPartitionScanner();
        IDocumentPartitioner partitioner = new MQSCPartitioner(scanner,
                MQSCPartitionScanner.MQSC_PARTITION_TYPES, false);

        ((IDocumentExtension3) mqscDoc).setDocumentPartitioner(
                MQSCEditorPlugin.MQSC_PARTITIONING, partitioner);
        partitioner.connect((Document) mqscDoc);
        ITypedRegion[] partitions = partitioner.computePartitioning(0, mqscDoc
                .getLength());
        // walk partitions
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Preparing to run script file " + fileName, null));
        }
        if (progressMonitor.isCanceled())
            return;
        DmQueueManager dmQueueManager = queueManager.getDmQueueManagerObject();
        // PCFMessage command;
        PCFParameter escapeTypeParameter = new MQCFIN(
                CMQCFC.MQIACF_ESCAPE_TYPE, CMQCFC.MQET_MQSC);
        // PCFParameter escapeTextParameter;
        PCFMessage[] responses;
        responses = new PCFMessage[1];
        Trace t = Trace.getDefault();
        qmgrName = queueManager.toFormattedString();
        progressMonitor.beginTask("Executing MQSC file \"" + fileName
                + "\" on Queue Manager \"" + qmgrName 
                + "\"", partitions.length);
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Executing MQSC file \"" + fileName
                                    + "\" on Queue Manager \""
                                    + queueManager.toFormattedString() + "\"",
                            null));
        }

        resultStore.clearResultSet(qmgrName );
        adminQueue=null;
        replyQueue = null;
        for (int i = 0; i < partitions.length; i++) {
            String partType = partitions[i].getType();
            if (partType != MQSCPartitionScanner.MQSC_COMMENT
                    && partType != IDocument.DEFAULT_CONTENT_TYPE) {
                try {
                    if (progressMonitor.isCanceled()) {
                        progressMonitor.done();
                        return;
                    }
                    // get text from partition
                    // alternately, attach code scanner to partition, and use
                    // events
                    // to build PCF message
                    String partitionText = mqscDoc.get(partitions[i]
                            .getOffset(), partitions[i].getLength());
                    setPartitionText(partitionText);
                    if (ask && !isValidPartition(partitions[i],i)) {
                        MQView.getViewSite().getShell().getDisplay().syncExec(
                                new Runnable() {
                                    public void run() {
                                        int buttonPressed = askRunSkipRunAllSkipAll(getPartitionText());
                                        switch (buttonPressed) {
                                        case IMQSCScriptsConstants.RUN_SCRIPT_RUN_BUTTON:
                                            ask = true;
                                            skip = false;
                                            break;
                                        case IMQSCScriptsConstants.RUN_SCRIPT_RUNALL_BUTTON:
                                            ask = false;
                                            skip = false;
                                            break;
                                        case IMQSCScriptsConstants.RUN_SCRIPT_SKIP_BUTTON:
                                            skip = true;
                                            ask = true;
                                            break;
                                        case IMQSCScriptsConstants.RUN_SCRIPT_SKIPALL_BUTTON:
                                            skip = true;
                                            ask = false;
                                            break;
                                        case IMQSCScriptsConstants.RUN_SCRIPT_ABORT_BUTTON:
                                            cancel = true;
                                            break;
                                        }

                                    }
                                });
                        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                            MQSCScriptsPlugin.getDefault().getLog().log(
                                    new Status(IStatus.INFO,
                                            MQSCScriptsPlugin.PLUGIN_ID, 0,
                                            "Asked for RunSkipRunAllSkipAll, results are ask: "
                                                    + ask + " skip: " + skip
                                                    + " cancel: " + cancel,
                                            null));
                        }
                    }
                    if (cancel) {
                        progressMonitor.done();
                        return;
                    }
                    if ((skip) && !isValidPartition(partitions[i],i)) {
                        continue;
                    }
                    partitionText = partitionText.trim();
                    // Trim semicolons
                    if (partitionText.endsWith(";")) {
                        partitionText = partitionText.substring(0,
                                partitionText.length() - 1);
                    }
                    StringBuffer newText = new StringBuffer();
                    boolean append = true;
                    for (int j = 0; j < partitionText.length(); j++) {
                        if (partitionText.charAt(j) == '*') {
                            if ((partitionText.charAt(j - 1) == '\n')
                                    || ((partitionText.charAt(j - 1) == '\n') && (partitionText
                                            .charAt(j - 1) == '\r'))) {
                                append = false;
                            }
                        } else if (((partitionText.charAt(j) == '\r') || (partitionText
                                .charAt(j) == '\n'))
                                && !append) {
                            append = true;
                        }
                        if (append)
                            newText.append(partitionText.charAt(j));
                    }

                    if (newText.length() > 0)
                        partitionText = newText.toString();
                    // Remove +, - continuation characters
                    partitionText = partitionText.replaceAll("[+-][\n\r]+", "");
                    if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                        MQSCScriptsPlugin.getDefault().getLog().log(
                                new Status(IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID, 0,
                                        "Building command for partitionText \""
                                                + partitionText + "\" ", null));
                    }

                    // build PCF messages
                    if (dmQueueManager.getPlatform() != CMQC.MQPL_MVS) {
                        processCommandAsPCFEscape(progressMonitor,
                                dmQueueManager, escapeTypeParameter, responses,
                                t, partitionText,qmgrName);
                    } else {

                        processCommandAsPlainText(progressMonitor, mqQM,
                                responses, t, partitionText,qmgrName);
                    }
                    progressMonitor.worked(1);
                } catch (BadLocationException e1) {
                    if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                        MQSCScriptsPlugin.getDefault().getLog().log(
                                new Status(IStatus.ERROR,
                                        MQSCScriptsPlugin.PLUGIN_ID, 0,
                                        "Bad Location Exception processing commands in RunMQSCScript for file "
                                                + fileName, e1));
                    }
                }
            }
        }
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog()
                    .log(
                            new Status(IStatus.INFO,
                                    MQSCScriptsPlugin.PLUGIN_ID, 0,
                                    "Completed executing MQSC file \""
                                            + fileName, null));
        }
        try {
            if (adminQueue != null && adminQueue.isOpen()) {
                adminQueue.close();
                adminQueue = null;
            }
            if (replyQueue != null && replyQueue.isOpen()) {
                replyQueue.close();
                replyQueue = null;
            }
        } catch (MQException e1) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Got MQException closing zOS queue:", e1));
        }
        progressMonitor.done();
    }

    private void processCommandAsPlainText(IProgressMonitor progressMonitor,
            MQQueueManager qmanager, PCFMessage[] _responses, Trace t,
            String partitionText, String qmgrName) {
        PCFMessage[] responses = _responses;
        int resultCount = 0;
        String replyQueueName = "MS0S_ZOS_REPLY*";
        MQPutMessageOptions pmo = new MQPutMessageOptions();
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        try {
            MQMessage msg = new MQMessage();
            msg.writeChars(partitionText);
            if (adminQueue == null) {
                adminQueue = qmanager.accessQueue(qmanager
                        .getCommandInputQueueName(), CMQC.MQOO_OUTPUT, "", "",
                        "mqm");
            }
            if (replyQueue == null) {
                replyQueue = qmanager.accessQueue("SYSTEM.DEFAULT.MODEL.QUEUE",
                        CMQC.MQOO_INPUT_EXCLUSIVE, "", replyQueueName, "mqm");
            }
            msg.messageType = CMQC.MQMT_REQUEST;
            msg.feedback = CMQC.MQFB_NONE;
            msg.format = CMQC.MQFMT_STRING;
            msg.characterSet = 1200;
            msg.replyToQueueName = replyQueue.getName();
            adminQueue.put(msg, pmo);
            gmo.options = CMQC.MQGMO_WAIT | CMQC.MQGMO_CONVERT;
            gmo.waitInterval = 1000;

            // Read all the response messages from the reply-to queue
            boolean readMoreMessages = true;
            do {
                try {
                    msg.messageId = CMQC.MQMI_NONE;
                    replyQueue.get(msg, gmo);
                    String resultText = msg.readStringOfByteLength(msg
                            .getMessageLength());
                    resultStore.addResult(qmgrName,resultText,partitionText);
                    resultCount++;
                } catch (MQException e2) {
                    if (e2.reasonCode != 2033) {
                        throw e2;
                    } else {
                        readMoreMessages = false;
                    }
                }
            } while (readMoreMessages);
            if (resultCount == 0) {
                resultStore
                        .addResult("Did not receive any responses from the queue manager.");
            }
        } catch (MQException e1) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Got MQException processing command :", e1));

            MQException source = (MQException) (e1.exceptionSource);
            if (source != null) {
                responses = (PCFMessage[]) (source.exceptionSource);
            } else {
                responses = (PCFMessage[]) (e1.exceptionSource);
            }
            for (int j = 0; j < responses.length; j++) {
                resultStore.addResult(qmgrName,responses[j]
                        .getParameterValue(CMQCFC.MQCACF_ESCAPE_TEXT));
            }
        } catch (IOException e1) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Got IOException processing command :", e1));
        }
    }

    private void processCommandAsPCFEscape(IProgressMonitor progressMonitor,
            DmQueueManager dmQueueManager, PCFParameter escapeTypeParameter,
            PCFMessage[] _responses, Trace t, String partitionText, String qmgrName) {
        PCFMessage command;
        PCFMessage[] responses = _responses;
        PCFParameter escapeTextParameter;
        progressMonitor.subTask("Executing Command " + partitionText);
        command = new PCFMessage(CMQCFC.MQCMD_ESCAPE);
        command.addParameter(escapeTypeParameter);
        escapeTextParameter = new MQCFST(CMQCFC.MQCACF_ESCAPE_TEXT,
                partitionText);
        command.addParameter(escapeTextParameter);
        // send pcf messages
        try {
            responses = dmQueueManager.sendCommand(t, command);
        } catch (MQException e1) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Got MQException processing command :", e1));

            MQException source = (MQException) (e1.exceptionSource);
            if (source != null) {
                responses = (PCFMessage[]) (source.exceptionSource);
            } else {
                responses = (PCFMessage[]) (e1.exceptionSource);
            }
        }
        // accumulate results
        for (int j = 0; j < responses.length; j++) {
            resultStore.addResult(qmgrName,responses[j]
                    .getParameterValue(CMQCFC.MQCACF_ESCAPE_TEXT),partitionText);
        }
    }

    public boolean isValidPartition(ITypedRegion partition, int partitionIndex) {
        // TODO: Use model instead
        String partType = partition.getType();
        int offset = partition.getOffset();
        int length = partition.getLength();
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Validating partition text \"" + getPartitionText()
                                    + " as partition type " + partType, null));
        }

        BufferedRuleBasedScanner scanner = ((MQSCCodeScanner) (MQSCEditorPlugin
                .getDefault().getMQSCCodeScanner()))
                .getScannerForPartition(partition,partitionIndex);
        scanner.setRange((Document) mqscDoc, offset, length);
        IToken myToken = (IToken) scanner.nextToken();
        MQSCCommandEvent myEvent = null;

        while ((myToken != null) && (!myToken.isEOF())) {
            if (myToken instanceof MQSCToken) {
                myEvent = ((MQSCToken) myToken).getEvent();
                if (myEvent != null) {
                    switch (myEvent.getType()) {
                    case MQSCCommandEvent.TERMINAL_EVENT:
                        return true;
                    case MQSCCommandEvent.INVALID_EVENT:
                        return false;
                    default:
                        break;
                    }
                }
            }
            myToken = (IToken) scanner.nextToken();
        }
        boolean retVal = (myEvent != null)
                && (myEvent.getType() == MQSCCommandEvent.INVALID_EVENT);
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Partition validity is " + retVal, null));
        }
        return retVal;
    }

    public int askRunSkipRunAllSkipAll(String partitionText) {
        if (skipDialog == null) {
            skipDialog = new RunSkipRunAllSkipAllDialog(MQView.getViewSite().getShell());
        }
        skipDialog.setText(partitionText);
        int buttonPressed = skipDialog.open();
        return buttonPressed;
    }

    public String getPartitionText() {
        return holdPartitionText;
    }

    public void setPartitionText(String partitionText) {
        holdPartitionText = partitionText;
    }
}
