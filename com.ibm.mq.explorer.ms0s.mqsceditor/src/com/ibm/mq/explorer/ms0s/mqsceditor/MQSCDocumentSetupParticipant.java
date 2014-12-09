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
package com.ibm.mq.explorer.ms0s.mqsceditor;

import org.eclipse.core.filebuffers.IDocumentSetupParticipant;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IDocumentPartitioner;

import com.ibm.mq.explorer.ms0s.mqsceditor.doc.MQSCPartitioner;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCDocumentModel;

public class MQSCDocumentSetupParticipant implements IDocumentSetupParticipant {

    public MQSCDocumentSetupParticipant() {
    }

    /*
     * @see org.eclipse.core.filebuffers.IDocumentSetupParticipant#setup(org.eclipse.jface.text.IDocument)
     */
    public void setup(IDocument document) {
        if (document instanceof IDocumentExtension3) {
            IDocumentExtension3 extension3 = (IDocumentExtension3) document;
            IDocumentPartitioner partitioner = new MQSCPartitioner(
                    MQSCEditorPlugin.getDefault().getMQSCPartitionScanner(),
                    MQSCPartitionScanner.MQSC_PARTITION_TYPES, true);

            MQSCDocumentModel myDM = MQSCEditorPlugin.getDefault().getMQSCDocumentModel();
            myDM.clear();
            MQSCEditorPlugin.getDefault().getMQSCCommandEventNotifier().addCommandEventListener(myDM,false);
            extension3.setDocumentPartitioner(
                    MQSCEditorPlugin.MQSC_PARTITIONING, partitioner);
            partitioner.connect(document);
        }
    }
}
