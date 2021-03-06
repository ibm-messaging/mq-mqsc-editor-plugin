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
package com.ibm.mq.explorer.ms0s.mqsceditor.doc;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.swt.widgets.Display;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCCodeScanner;

/**
 * @author jlowrey
 */
/**
 * <p>
 * This is a coarse grained parser that creates elements in the 
 * MQSC Document model that represent the contents of each partition
 * created by the MQSCPartitioner  
 * 
 **/

public class MQSCPartitionParser implements IRunnableWithProgress {
    private ITypedRegion[] partitions;
    
    private IDocument fDocument;
    
    private boolean canceled = false;

    public MQSCPartitionParser(ITypedRegion[] partitions,Display display, IDocument fDocument) {
        this.partitions = partitions;
        this.fDocument = fDocument;
    }

    public void run(IProgressMonitor progressMonitor) throws InvocationTargetException,
            InterruptedException {
        StringBuffer buffer = new StringBuffer();
        progressMonitor.beginTask("Building MQSC document model", partitions.length);

        for (int i = 0; i < partitions.length; i++) {
            if (progressMonitor.isCanceled()) {
                progressMonitor.done();
                canceled = true;
                return;
            }
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
                buffer.append("Partition type: "
                        + partitions[i].getType() + ", offset: "
                        + partitions[i].getOffset() + ", length: "
                        + partitions[i].getLength());
                buffer.append(" \r\n");
                buffer.append("Text:\n ");
                try {
                    buffer.append(fDocument.get(partitions[i]
                            .getOffset(), partitions[i].getLength()));
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
            progressMonitor.worked(1);
        }
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            buffer.toString(), null));
        }

    }

    public boolean isCanceled() {
        return canceled;
    }
}
