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

import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.ContentFormatter;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.graphics.RGB;

import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCAutoIndentStrategy;
import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCCompletionProcessor;
import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCDamagerRepairer;
import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCDoubleClickSelector;
import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCFormattingStrategy;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCCodeScanner;


public class MQSCSourceViewerConfiguration extends SourceViewerConfiguration {


    /**
     * Default constructor.
     */
    public MQSCSourceViewerConfiguration() {
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */
    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return new MQSCAnnotationHover();
    }

    public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
        ContentFormatter formatter = new ContentFormatter();
        MQSCFormattingStrategy MQSCFormatter = new MQSCFormattingStrategy();
        String myContents[] = getConfiguredContentTypes(sourceViewer);
        for (int i = 0; i < myContents.length; i++) {
            formatter.setFormattingStrategy(MQSCFormatter, myContents[i]);
        }
        return formatter;
    }

    /*
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAutoEditStrategies(org.eclipse.jface.text.source.ISourceViewer,
     *      java.lang.String)
     */
    //TODO: Implement AutoEditStrategy for indenting/formatting
    public IAutoEditStrategy[] getAutoEditStrategies(
            ISourceViewer sourceViewer, String contentType) {
        IAutoEditStrategy strategy = (IDocument.DEFAULT_CONTENT_TYPE
                .equals(contentType) ? new MQSCAutoIndentStrategy()
                : new DefaultIndentLineAutoEditStrategy());
        //Possible indent strategy: Replace entire text of document with
        // contents of valid MQSCCommandEvents

        //		IAutoEditStrategy strategy= new MQSCAutoIndentStrategy();
        return new IAutoEditStrategy[] { strategy };
    }

    /*
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredDocumentPartitioning(org.eclipse.jface.text.source.ISourceViewer)
     */
    public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
        return MQSCEditorPlugin.MQSC_PARTITIONING;
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */
    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return new String[] { MQSCPartitionScanner.MQSC_ALTER_COMMAND,
                MQSCPartitionScanner.MQSC_ALT_COMMAND,
                MQSCPartitionScanner.MQSC_ARCHIVE_COMMAND,
                MQSCPartitionScanner.MQSC_ARC_COMMAND,
                MQSCPartitionScanner.MQSC_BACKUP_COMMAND,
                MQSCPartitionScanner.MQSC_CLEAR_COMMAND,
                MQSCPartitionScanner.MQSC_CL_COMMAND,
                MQSCPartitionScanner.MQSC_COMMENT,
                MQSCPartitionScanner.MQSC_DEFINE_COMMAND,
                MQSCPartitionScanner.MQSC_DEF_COMMAND,
                MQSCPartitionScanner.MQSC_DELETE_COMMAND,
                MQSCPartitionScanner.MQSC_DISPLAY_COMMAND,
                MQSCPartitionScanner.MQSC_DIS_COMMAND,
                MQSCPartitionScanner.MQSC_MOVE_COMMAND,
                MQSCPartitionScanner.MQSC_PING_COMMAND,
                MQSCPartitionScanner.MQSC_RECOVER_COMMAND,
                MQSCPartitionScanner.MQSC_REC_COMMAND,
                MQSCPartitionScanner.MQSC_REFRESH_COMMAND,
                MQSCPartitionScanner.MQSC_REF_COMMAND,
                MQSCPartitionScanner.MQSC_RESET_COMMAND,
                MQSCPartitionScanner.MQSC_RESOLVE_COMMAND,
                MQSCPartitionScanner.MQSC_RES_COMMAND,
                MQSCPartitionScanner.MQSC_RESUME_COMMAND,
                MQSCPartitionScanner.MQSC_RVERIFY_COMMAND,
                MQSCPartitionScanner.MQSC_REV_COMMAND,
                MQSCPartitionScanner.MQSC_SET_COMMAND,
                MQSCPartitionScanner.MQSC_START_COMMAND,
                MQSCPartitionScanner.MQSC_STA_COMMAND,
                MQSCPartitionScanner.MQSC_STOP_COMMAND,
                MQSCPartitionScanner.MQSC_SUSPEND_COMMAND, };
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */

    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {

        ContentAssistant assistant = new ContentAssistant();
        assistant
                .setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        assistant.setContentAssistProcessor(new MQSCCompletionProcessor(),
                IDocument.DEFAULT_CONTENT_TYPE);
        for (int i = 0; i < MQSCPartitionScanner.MQSC_PARTITION_TYPES.length; i++) {
            assistant.setContentAssistProcessor(new MQSCCompletionProcessor(),
                    MQSCPartitionScanner.MQSC_PARTITION_TYPES[i]);
        }

        assistant.enableAutoActivation(true);
        assistant.setAutoActivationDelay(500);
        assistant
                .setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
        assistant
                .setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
        assistant.setContextInformationPopupBackground(MQSCEditorPlugin
                .getDefault().getMQSCColorProvider().getColor(
                        new RGB(150, 150, 0)));

        return assistant;
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */
    public String getDefaultPrefix(ISourceViewer sourceViewer,
            String contentType) {
        return (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType) ? "//"
                : null);
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */
    public ITextDoubleClickStrategy getDoubleClickStrategy(
            ISourceViewer sourceViewer, String contentType) {
        return new MQSCDoubleClickSelector();
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */
    public String[] getIndentPrefixes(ISourceViewer sourceViewer,
            String contentType) {
        return new String[] { "\t", "    " };
    }

    /*
     * (non-Javadoc) Method declared on SourceViewerConfiguration
     */
    public IPresentationReconciler getPresentationReconciler(
            ISourceViewer sourceViewer) {

//        MQSCColorProvider provider = MQSCEditorPlugin.getDefault()
//                .getMQSCColorProvider();
        PresentationReconciler reconciler = new PresentationReconciler();
        reconciler
                .setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
        MQSCDamagerRepairer dr = null;
/*        DefaultDamagerRepairer dr1 = null;
        dr1 = new DefaultDamagerRepairer(new SingleTokenScanner(
                new TextAttribute(provider
                        .getColor(MQSCColorProvider.MQSC_INVALID_VALUE))));*/
        BufferedRuleBasedScanner scanner = null;
        scanner = ((MQSCCodeScanner) MQSCEditorPlugin.getDefault()
                .getMQSCCodeScanner())
                .getScannerForPartitionType(IDocument.DEFAULT_CONTENT_TYPE);
        dr = new MQSCDamagerRepairer(scanner);
        reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
        reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
        for (int i = 0; i < MQSCPartitionScanner.MQSC_PARTITION_TYPES.length; i++) {
            //System.out.println("Adding partition types for " + MQSCPartitionScanner.MQSC_PARTITION_TYPES[i]);
            scanner = ((MQSCCodeScanner) MQSCEditorPlugin.getDefault()
                    .getMQSCCodeScanner())
                    .getScannerForPartitionType(MQSCPartitionScanner.MQSC_PARTITION_TYPES[i]);
            dr = new MQSCDamagerRepairer(scanner);
            reconciler.setDamager(dr,
                    MQSCPartitionScanner.MQSC_PARTITION_TYPES[i]);
            reconciler.setRepairer(dr,
                    MQSCPartitionScanner.MQSC_PARTITION_TYPES[i]);
        }

        return reconciler;
    }

    /* (non-Javadoc)
     * Method declared on SourceViewerConfiguration
     */
    public int getTabWidth(ISourceViewer sourceViewer) {
        return 4;
    }

    /* (non-Javadoc)
     * Method declared on SourceViewerConfiguration
     */
    public ITextHover getTextHover(ISourceViewer sourceViewer,
            String contentType) {
        return new MQSCTextHover();
    }

}
