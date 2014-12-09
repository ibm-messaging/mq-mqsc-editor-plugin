/*
 * SupportPac MS0S (c) Copyright IBM Corp. 2007. All rights reserved.
 *  
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.doc;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCDocumentModel;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCCodeScanner;

public class MQSCPartitioner extends FastPartitioner{
    private boolean reparse;
    private boolean useThread;

    public MQSCPartitioner(IPartitionTokenScanner scanner,
            String[] legalContentTypes, boolean threaded) {
        super(scanner, legalContentTypes);
        reparse = true;
        useThread = threaded;
    }

    public ITypedRegion[] computePartitioning(int offset, int length,
            boolean includeZeroLengthPartitions) {

        MQSCDocumentModel myDM = MQSCEditorPlugin.getDefault().getMQSCDocumentModel();
        myDM.clear();

        ITypedRegion[] partitions = super.computePartitioning(offset, length,
                includeZeroLengthPartitions);

        if (reparse && useThread) {
            reparse = false;
            Display myDisp = PlatformUI.getWorkbench().getDisplay();
            MQSCPartitionParser actor = new MQSCPartitionParser(partitions,
                    myDisp, fDocument);
            IProgressService progressService = PlatformUI.getWorkbench()
                    .getProgressService();
            try {

                progressService.busyCursorWhile(actor);
                if (actor.isCanceled()) {
                    return new ITypedRegion[1];
                }
            } catch (InvocationTargetException e) {
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    MQSCEditorPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0,
                                    "Invocation Target Exception", e));
                }
            } catch (InterruptedException e) {
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    MQSCEditorPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0,
                                    "Interrupted Exception", e));
                }
            }
        } else if(partitions.length < 20) {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < partitions.length; i++) {

                BufferedRuleBasedScanner scanner = ((MQSCCodeScanner) (MQSCEditorPlugin
                        .getDefault().getMQSCCodeScanner()))
                        .getScannerForPartition(partitions[i],i);
                int tmpOffset, tmpLength;
                tmpOffset = partitions[i].getOffset();
                tmpLength = partitions[i].getLength();
                scanner.setRange(fDocument, tmpOffset, tmpLength);
                IToken myToken = (IToken) scanner.nextToken();
                while ((myToken != null) && (!myToken.isEOF())) {
                    myToken = (IToken) scanner.nextToken();
                }
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    buffer.append("Partition type: " + partitions[i].getType()
                            + ", offset: " + partitions[i].getOffset()
                            + ", length: " + partitions[i].getLength());
                    buffer.append(" \r\n");
                    buffer.append("Text:\n ");
                    try {
                        buffer.append(fDocument.get(partitions[i].getOffset(),
                                partitions[i].getLength()));
                    } catch (BadLocationException e) {
                        if (MQSCEditorPlugin.getDefault().isDebugging()) {
                            MQSCEditorPlugin.getDefault().getLog().log(
                                    new Status(IStatus.ERROR,
                                            MQSCEditor.PLUGIN_ID, 0,
                                            "Bad Location Exception", e));
                        }
                    }
                    buffer.append("\n---------------------------\n");
                }
            }
        }

        return partitions;
    }

    public void connect(IDocument document, boolean delayInitialization) {
        super.connect(document,delayInitialization);
        reparse = true;
    }

}