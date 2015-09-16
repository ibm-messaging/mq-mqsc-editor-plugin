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
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationPresenter;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;
import org.eclipse.jface.text.rules.BufferedRuleBasedScanner;
import org.eclipse.jface.text.rules.IToken;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCPartitionScanner;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
import com.ibm.mq.explorer.ms0s.mqsceditor.lang.MQSCLanguageConfigurator;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCCodeScanner;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCToken;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This reacts to requests for code completion and produces the relevant
 * suggestions based on where in the MQSC command the request came from.  
 * It uses MQSC Command Event objects attaced to each parser token to
 * determine what values to retrieve from the MQSC Language configuration.
 **/

public class MQSCCompletionProcessor implements IContentAssistProcessor {
    private String lineDelim = null;

    protected static class Validator implements IContextInformationValidator,
            IContextInformationPresenter {

        protected int fInstallOffset;

        /*
         * @see IContextInformationValidator#isContextInformationValid(int)
         */
        public boolean isContextInformationValid(int offset) {
            return Math.abs(fInstallOffset - offset) < 5;
        }

        /*
         * @see IContextInformationValidator#install(IContextInformation,
         *      ITextViewer, int)
         */
        public void install(IContextInformation info, ITextViewer viewer,
                int offset) {
            fInstallOffset = offset;
        }

        /*
         * @see org.eclipse.jface.text.contentassist.IContextInformationPresenter#updatePresentation(int,
         *      TextPresentation)
         */
        public boolean updatePresentation(int documentPosition,
                TextPresentation presentation) {
            return true;
        }
    }

    protected IContextInformationValidator fValidator = new Validator();

    /*
     * (non-Javadoc) Method declared on IContentAssistProcessor
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int documentOffset) {

        ITypedRegion myPartition = null;
        ICompletionProposal[] result = null;
        String completionStartsWith = "";
        int whitespacePos = 0;
        int completionLength = 0;
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            "Computing completion proposals at document offset "
                                    + documentOffset, null));
        }

        if (documentOffset == 0) {
            result = generateProposal(MQSCEditorPlugin.getDefault()
                    .getMQSCLanguageConfiguration().getCommandNames(),
                    documentOffset, completionStartsWith, completionStartsWith
                            .length(), null, null, false);

            return result;
        }

        try {
            IDocument document = viewer.getDocument();
            IDocumentExtension3 extension3 = (IDocumentExtension3) document;
            String[] lds = document.getLegalLineDelimiters();
            if (lds != null) {
                lineDelim = lds[0];
            } else {
                lineDelim = "";
            }
            myPartition = extension3.getPartition(
                    MQSCEditorPlugin.MQSC_PARTITIONING, documentOffset - 1,
                    false);
            whitespacePos = searchLeft(documentOffset, document);
            completionStartsWith = document.get(whitespacePos, documentOffset
                    - whitespacePos);
            completionLength = completionStartsWith.length();
            completionStartsWith = completionStartsWith.replaceAll(
                    "[+-][\n\r]+", "");
        } catch (BadLocationException e) {
            if (MQSCEditorPlugin.getDefault().isDebugging()) {
                MQSCEditorPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0,
                                "Bad Location Exception", e));
            }
        } catch (BadPartitioningException e) {
            if (MQSCEditorPlugin.getDefault().isDebugging()) {
                MQSCEditorPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0,
                                "Bad Partitioning Exception", e));
            }
        }
        if (myPartition != null) {
            if (myPartition.getType() == Document.DEFAULT_CONTENT_TYPE) {
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    MQSCEditorPlugin
                            .getDefault()
                            .getLog()
                            .log(
                                    new Status(
                                            IStatus.INFO,
                                            MQSCEditor.PLUGIN_ID,
                                            0,
                                            "Computing completion proposals for default content type",
                                            null));
                }

                result = generateProposal(MQSCEditorPlugin.getDefault()
                        .getMQSCLanguageConfiguration().getCommandNames(),
                        documentOffset, completionStartsWith, completionLength,
                        null, null, false);
            } else if (myPartition.getType() != MQSCPartitionScanner.MQSC_COMMENT) {
                String objType = "";
               // String objName = "";
                String cmdName = "";
                IToken myToken = null;
                int length = 0;
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    MQSCEditorPlugin.getDefault().getLog().log(
                            new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                                    "Computing completion proposals for partition type "
                                            + myPartition.getType(), null));
                }
                BufferedRuleBasedScanner scanner = ((MQSCCodeScanner) (MQSCEditorPlugin
                        .getDefault().getMQSCCodeScanner()))
                        .getScannerForPartition(myPartition,-1);
                length = documentOffset - myPartition.getOffset()
                        - completionStartsWith.length();
                if (length < 0) {
                    length = 0;
                }
                scanner.setRange(viewer.getDocument(), myPartition.getOffset(),
                        myPartition.getLength());
                myToken = (IToken) scanner.nextToken();
                if (!(myToken instanceof MQSCToken)) {
                    result = generateProposal(MQSCEditorPlugin.getDefault()
                            .getMQSCLanguageConfiguration().getCommandNames(),
                            documentOffset, completionStartsWith,
                            completionLength, null, null, false);
                    return result;
                }
                MQSCCommandEvent myEvent = ((MQSCToken) myToken).getEvent();
                int lastTypeFound = -1;
                if (myEvent != null
                        && myEvent.getType() == MQSCCommandEvent.COMMAND_WORD_EVENT) {
                    cmdName = myEvent.getValue().toUpperCase().trim();
                    lastTypeFound = myEvent.getType();
                } else if (myEvent == null) {
                    lastTypeFound = -1;
                }
                boolean halt = false;
                List dupParams = new ArrayList();
                String subTypeName = " ";
//                boolean hasSubType = false;
                while ((myToken != null) && (!myToken.isEOF()) && !halt) {
                    myToken = (IToken) scanner.nextToken();
                    if (myToken instanceof MQSCToken) {
                        myEvent = ((MQSCToken) myToken).getEvent();
                        if (myEvent != null) {
                            if (myEvent.getOffset() <= documentOffset) {
                                if (myEvent.getType() != MQSCCommandEvent.INVALID_EVENT)
                                    lastTypeFound = myEvent.getType();
                            }
                            if (myEvent.getType() == MQSCCommandEvent.OBJECT_VALUE_EVENT) {
                                objType = myEvent.getValue().toUpperCase()
                                        .trim();
                                if (myEvent.getOffset() <= documentOffset)
                                    lastTypeFound = myEvent.getType();
                            } else if (myEvent.getType() == MQSCCommandEvent.OBJECT_NAME_EVENT) {
                            	//We can't create proposals for the names of MQSC objects
                            } else if (myEvent.getType() == MQSCCommandEvent.TERMINAL_EVENT) {
                                halt = true;
                            } else if (myEvent.getType() == MQSCCommandEvent.PARAMETER_NAME_EVENT) {
                                dupParams.add((Object) myEvent.getValue()
                                        .toUpperCase());
                            } else if (myEvent.getType() == MQSCCommandEvent.SUBTYPE_VALUE_EVENT) {
                                subTypeName = myEvent.getValue().toUpperCase();
                                subTypeName = subTypeName.replace('\'', ' ');
                                subTypeName = subTypeName.trim();
                            }
                        }
                    }
                }
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    MQSCEditorPlugin.getDefault().getLog().log(
                            new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                                    "Last MQSCCommandEvent type found while computing proposals ="
                                            + lastTypeFound, null));
                }

                switch (lastTypeFound) {
                case -1:
                    result = generateProposal(MQSCEditorPlugin.getDefault()
                            .getMQSCLanguageConfiguration().getCommandNames(),
                            documentOffset, completionStartsWith,
                            completionLength, null, null, false);
                    break;
                case MQSCCommandEvent.COMMAND_WORD_EVENT:
                    result = generateProposal(MQSCEditorPlugin.getDefault()
                            .getMQSCLanguageConfiguration()
                            .getObjectsForCommand(cmdName), documentOffset,
                            completionStartsWith, completionLength, null,
                            cmdName, false);
                    break;
                case MQSCCommandEvent.OBJECT_VALUE_EVENT:
                case MQSCCommandEvent.OBJECT_NAME_EVENT:
                case MQSCCommandEvent.PARAMETER_NAME_EVENT:
                case MQSCCommandEvent.PARAMETER_VALUE_EVENT:
                case MQSCCommandEvent.SUBTYPE_NAME_EVENT:
                case MQSCCommandEvent.SUBTYPE_VALUE_EVENT:
                case MQSCCommandEvent.TERMINAL_EVENT:
                    Object parmNames = MQSCEditorPlugin.getDefault()
                            .getMQSCLanguageConfiguration()
                            .getObjectParamNames(cmdName, objType);
                    if (parmNames instanceof HashMap) {
                        result = generateProposal(
                                (String[]) ((HashMap) parmNames)
                                        .get(subTypeName), documentOffset,
                                completionStartsWith, completionLength,
                                dupParams, cmdName, true);
                    } else {
                        result = generateProposal((String[]) parmNames,
                                documentOffset, completionStartsWith,
                                completionLength, dupParams, cmdName, true);

                    }
                    break;
                default:
                    break;
                }
            }
        }
        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ICompletionProposal[] generateProposal(String[] proposals,
            int documentOffset, String startsWith, int replaceLength,
            List dupParams, String commandName, boolean isParen) {

        ICompletionProposal[] result;
        if (proposals == null) return null;
        List myList = Arrays.asList(proposals);
        List myProposals = new ArrayList();
        Collections.sort(myList);
        for (Iterator myIter = myList.iterator(); myIter.hasNext();) {
            String proposal = (String) myIter.next();
            if (proposal.toUpperCase().startsWith(startsWith.toUpperCase())) {
                if (dupParams != null
                        && dupParams.contains((Object) proposal.toUpperCase())) {
                } else {
                    if (shouldAppendParens(proposal, commandName, isParen)) {
                        myProposals.add(new CompletionProposal(proposal
                                .toUpperCase()
                                + "()", documentOffset - replaceLength,
                                replaceLength, proposal.length() + 1, null,
                                proposal.toUpperCase(), null, null));

                    } else {
                        myProposals.add(new CompletionProposal(proposal
                                .toUpperCase(), documentOffset - replaceLength,
                                replaceLength, proposal.length(), null,
                                proposal.toUpperCase(), null, null));
                    }
                }
            }
        }
        if (commandName != null && !myProposals.isEmpty()) {
            myProposals.add(new CompletionProposal("+" + lineDelim,
                    documentOffset, startsWith.length(),
                    1 + lineDelim.length(), null, "+", null, null));
            myProposals.add(new CompletionProposal("-" + lineDelim,
                    documentOffset, 0, 1 + lineDelim.length(), null, "-", null,
                    null));
        }
        if (myProposals.isEmpty()) {
            if (shouldAppendParens(startsWith, commandName, isParen)) {
                myProposals.add(new CompletionProposal(startsWith + "()",
                        documentOffset - startsWith.length(), 2 + startsWith
                                .length(), startsWith.length() + 1, null, "()",
                        null, null));
            }
        }
        printProposals(myProposals, commandName, startsWith);
        result = new ICompletionProposal[myProposals.size()];
        for (int i = 0; i < myProposals.size(); i++) {
            result[i] = (ICompletionProposal) myProposals.get(i);
        }
        return result;
    }

    private int searchLeft(int docOffset, IDocument document) {
        int result = docOffset - 1;
        try {
            char c;
            c = document.getChar(result);
            while (!Character.isWhitespace(c) && c != '\n' && c != '\r'
                    && result > 0) {
                result--;
                c = document.getChar(result);
            }
            ;
        } catch (BadLocationException e) {
            if (MQSCEditorPlugin.getDefault().isDebugging()) {
                MQSCEditorPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0,
                                "Bad Location Exception", e));
            }
        }
        return (result == 0) ? 0 : result + 1;
    }

    /*
     * (non-Javadoc) Method declared on IContentAssistProcessor
     */
    public IContextInformation[] computeContextInformation(ITextViewer viewer,
            int documentOffset) {
        return null;
    }

    @SuppressWarnings("rawtypes")
    private boolean shouldAppendParens(String proposalValue,
            String commandName, boolean isParam) {
        if (commandName == null)
            return false;
        if (proposalValue == null || proposalValue.trim().length() == 0)
            return false;
        if ((commandName.equalsIgnoreCase("display") || commandName
                .equalsIgnoreCase("dis"))
                && isParam) {
            if (proposalValue.equalsIgnoreCase("type"))
                return true;
            return false;
        }
        if (!isParam && proposalValue.equalsIgnoreCase("qmgr"))
            return false;
        MQSCLanguageConfigurator lang = MQSCLanguageConfigurator
                .getLanguageConfiguration();
        ArrayList noParens = lang.hasNoValue();
        if (noParens.contains(proposalValue))
            return false;
        return true;
    }

    /*
     * (non-Javadoc) Method declared on IContentAssistProcessor
     */
    public char[] getCompletionProposalAutoActivationCharacters() {
        return null;
    }

    /*
     * (non-Javadoc) Method declared on IContentAssistProcessor
     */
    public char[] getContextInformationAutoActivationCharacters() {
        return null;
    }

    /*
     * (non-Javadoc) Method declared on IContentAssistProcessor
     */
    public IContextInformationValidator getContextInformationValidator() {
        return fValidator;
    }

    /*
     * (non-Javadoc) Method declared on IContentAssistProcessor
     */
    public String getErrorMessage() {
        return null;
    }
    @SuppressWarnings("rawtypes")
    private void printProposals(List proposals, String commandName,
            String startsWith) {
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("Proposals for command '" + commandName
                    + "' for word starting with '" + startsWith + "' -- --");
            buffer.append("\r\n");
            for (Iterator iter = proposals.iterator(); iter.hasNext();) {
                ICompletionProposal proposal = (ICompletionProposal) iter
                        .next();
                buffer.append("-- Keyword: '");
                buffer.append(proposal.getDisplayString());
                buffer.append("'\r\n--");
            }
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0, buffer
                            .toString(), null));

        }
    }
}
