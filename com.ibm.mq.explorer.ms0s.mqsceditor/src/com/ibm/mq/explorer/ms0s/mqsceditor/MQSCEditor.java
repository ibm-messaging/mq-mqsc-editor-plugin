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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionSupport;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCContentOutlinePage;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCDocumentModel;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCModelElement;

public class MQSCEditor extends TextEditor {

    public static final String PLUGIN_ID = "com.ibm.mq.explorer.ms0s.mqsceditor";

    private IEditorInput input;

//    private class DefineFoldingRegionAction extends TextEditorAction {
//
//        public DefineFoldingRegionAction(ResourceBundle bundle, String prefix,
//                ITextEditor editor) {
//            super(bundle, prefix, editor);
//        }
//
//        private IAnnotationModel getAnnotationModel(ITextEditor editor) {
//            return (IAnnotationModel) editor
//                    .getAdapter(ProjectionAnnotationModel.class);
//        }
//
//        /*
//         * @see org.eclipse.jface.action.Action#run()
//         */
//        public void run() {
//            ITextEditor editor = getTextEditor();
//            ISelection selection = editor.getSelectionProvider().getSelection();
//            if (selection instanceof ITextSelection) {
//                ITextSelection textSelection = (ITextSelection) selection;
//                if (!textSelection.isEmpty()) {
//                    IAnnotationModel model = getAnnotationModel(editor);
//                    if (model != null) {
//
//                        int start = textSelection.getStartLine();
//                        int end = textSelection.getEndLine();
//
//                        try {
//                            IDocument document = editor.getDocumentProvider()
//                                    .getDocument(editor.getEditorInput());
//                            int offset = document.getLineOffset(start);
//                            int endOffset = document.getLineOffset(end + 1);
//                            Position position = new Position(offset, endOffset
//                                    - offset);
//                            model.addAnnotation(new ProjectionAnnotation(),
//                                    position);
//                        } catch (BadLocationException x) {
//                            // ignore
//                        }
//                    }
//                }
//            }
//        }
//    }

    /** The outline page */
    private MQSCContentOutlinePage fOutlinePage;

    /** The projection support */
    private ProjectionSupport fProjectionSupport;

    /**
     * Default constructor.
     */
    public MQSCEditor() {
        super();
    }

    protected void createActions() {
        super.createActions();

        IAction a = new TextOperationAction(MQSCEditorMessages
                .getResourceBundle(), "ContentAssistProposal.", this,
                ISourceViewer.CONTENTASSIST_PROPOSALS);
        a
                .setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
        setAction("ContentAssistProposal", a);

        a = new TextOperationAction(MQSCEditorMessages.getResourceBundle(),
                "ContentAssistTip.", this,
                ISourceViewer.CONTENTASSIST_CONTEXT_INFORMATION);
        a
                .setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
        setAction("ContentAssistTip", a);
        a = new TextOperationAction(MQSCEditorMessages.getResourceBundle(),
                "ContentFormatProposal.", this, ISourceViewer.FORMAT);
        setAction("ContentFormatProposal", a);
    }

    public void dispose() {
//        if (fOutlinePage != null)
//            fOutlinePage.setInput(null);
        super.dispose();
    }

    public void doRevertToSaved() {
        super.doRevertToSaved();
        if (fOutlinePage != null)
            fOutlinePage.update();
        validateAndMark();
    }

    public void doSave(IProgressMonitor monitor) {
        super.doSave(monitor);
        if (fOutlinePage != null)
            fOutlinePage.update();
        validateAndMark();
    }

    public void doSaveAs() {
        super.doSaveAs();
        if (fOutlinePage != null)
            fOutlinePage.update();
        validateAndMark();
    }

    public void doSetInput(IEditorInput input) throws CoreException {
        super.doSetInput(input);
        this.input = input;
        if (fOutlinePage != null)
            fOutlinePage.setInput(input);
        validateAndMark();
    }

    protected void removeMarkers() {
        IFile file = getInputFile();
        try {
            file.deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_ZERO);
        } catch (CoreException e1) {
            if (MQSCEditorPlugin.getDefault().isDebugging()) {
                MQSCEditorPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0,
                                "Core Exception", e1));
            }
        }
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    //Parse document, produce any error markers in Problems view.
    protected void validateAndMark() {
        Map map = new HashMap();
        int lineNumber = 0;
        int columnNumber = 0;
        int lineStart = 0;
        String message = "";
        IFile file;
        IDocument document = getInputDocument();
        removeMarkers();
        file = getInputFile();

        MQSCDocumentModel myDM = MQSCEditorPlugin.getDefault()
                .getMQSCDocumentModel();
        
        for (Iterator iter = myDM.keySet().iterator(); iter.hasNext();) {
            String key = (String)iter.next();
            MQSCModelElement el = (MQSCModelElement) myDM.get(key);
            if (el.isInvalid()) {
                for (Iterator invIter = el.getInvalidEvents().iterator(); invIter
                        .hasNext();) {
                    MQSCCommandEvent myEvent = (MQSCCommandEvent) invIter
                            .next();
                    try {
                        columnNumber = myEvent.getOffset();
                        lineNumber = document
                                .getLineOfOffset(columnNumber);
                        lineStart = document
                                .getLineOffset(lineNumber);
                        map.clear();
                    } catch (BadLocationException e) {
                        if (MQSCEditorPlugin.getDefault().isDebugging()) {
                            MQSCEditorPlugin.getDefault().getLog().log(
                                    new Status(IStatus.ERROR,
                                            MQSCEditor.PLUGIN_ID, 0,
                                            "Bad Location Exception", e));
                        }
                    }
                    MarkerUtilities.setLineNumber(map, lineNumber);
                    map.put(IMarker.LOCATION, file.getFullPath()
                            .toString());

                    Integer charStart = new Integer(columnNumber);
                    if (charStart != null)
                        map.put(IMarker.CHAR_START, charStart);

                    Integer charEnd = new Integer(columnNumber
                            + myEvent.getLength());
                    if (charEnd != null)
                        map.put(IMarker.CHAR_END, charEnd);

                    map.put(IMarker.SEVERITY, new Integer(
                            IMarker.SEVERITY_ERROR));
                    if (myEvent.getType() == MQSCCommandEvent.INVALID_EVENT) {
                        message = "MQSC syntax error: invalid token \""
                                + myEvent.getValue()
                                + "\" on line " + lineNumber;
                    } else if (myEvent.getType() == MQSCCommandEvent.BAD_PAREN_EVENT) {
                        message = "MQSC syntax error: Unmatched parentheses  \""
                                + myEvent.getValue()
                                + "\" at character "
                                + (columnNumber - lineStart)
                                + " on line " + lineNumber;

                    } else if (myEvent.getType() == MQSCCommandEvent.BAD_QUOTE_EVENT) {
                        message = "MQSC syntax error: Unmatched single-quote \""
                                + myEvent.getValue()
                                + "\" on line " + lineNumber;
                    }

                    MarkerUtilities.setMessage(map, message);
                    map.put(IMarker.MESSAGE, message);

                    try {
                        MarkerUtilities.createMarker(file, map,
                                IMarker.PROBLEM);
                    } catch (CoreException ee) {
                        if (MQSCEditorPlugin.getDefault()
                                .isDebugging()) {
                            MQSCEditorPlugin
                                    .getDefault()
                                    .getLog()
                                    .log(
                                            new Status(
                                                    IStatus.ERROR,
                                                    MQSCEditor.PLUGIN_ID,
                                                    0,
                                                    "Core Exception",
                                                    ee));
                        }
                    }
                }
            }
        }

        IDocumentExtension3 extension3 = (IDocumentExtension3) document;
        ITypedRegion[] partitions = extension3.getDocumentPartitioner(
                MQSCEditorPlugin.MQSC_PARTITIONING).computePartitioning(0,
                document.getLength());
        for (int i = 0; i < partitions.length; i++) {
            int offset = partitions[i].getOffset();
            int length = partitions[i].getLength();
            String partType = partitions[i].getType();
            String partitionText = "";
            if (partType.equalsIgnoreCase(IDocument.DEFAULT_CONTENT_TYPE)) {
                try {
                    //create error mark
                    lineNumber = document.getLineOfOffset(offset);
                    partitionText = document.get(partitions[i].getOffset(),
                            partitions[i].getLength());
                    if (partitionText.startsWith("\n")
                            || partitionText.startsWith("\r"))
                        lineNumber += 1;
                } catch (BadLocationException e) {
                    if (MQSCEditorPlugin.getDefault().isDebugging()) {
                        MQSCEditorPlugin.getDefault().getLog().log(
                                new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID,
                                        0, "Bad Location Exception", e));
                    }
                }
                //deal with perfectly normal default text
                if (partitionText.matches("\\s*"))
                    continue;
                MarkerUtilities.setLineNumber(map, lineNumber);
                map.put(IMarker.LOCATION, file.getFullPath().toString());

                Integer charStart = new Integer(offset);
                if (charStart != null)
                    map.put(IMarker.CHAR_START, charStart);

                Integer charEnd = new Integer(offset
                        + partitions[i].getLength() - 1);
                if (charEnd != null)
                    map.put(IMarker.CHAR_END, charEnd);

                map.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR));
                message = "MQSC syntax error: invalid text starting with \""
                        + partitionText.substring(0, (length > 10) ? 10
                                : length) + "\" on line " + lineNumber;
                MarkerUtilities.setMessage(map, message);
                map.put(IMarker.MESSAGE, message);

                try {
                    MarkerUtilities.createMarker(file, map, IMarker.PROBLEM);
                } catch (CoreException ee) {
                    if (MQSCEditorPlugin.getDefault().isDebugging()) {
                        MQSCEditorPlugin.getDefault().getLog().log(
                                new Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID,
                                        0, "Core Exception", ee));
                    }
                }

            } 
            /*            else if (!partType
                    .equalsIgnoreCase(MQSCPartitionScanner.MQSC_COMMENT)) {
                BufferedRuleBasedScanner scanner = ((MQSCCodeScanner) (MQSCEditorPlugin
                        .getDefault().getMQSCCodeScanner()))
                        .getScannerForPartition(partitions[i]);
                scanner.setRange(document, offset, length);
                IToken myToken = (IToken) scanner.nextToken();
                MQSCCommandEvent myEvent = ((MQSCToken) myToken).getEvent();
                boolean halt = false;
                while ((myToken != null) && (!myToken.isEOF()) && !halt) {
                    if (myToken instanceof MQSCToken) {
                        myEvent = ((MQSCToken) myToken).getEvent();
                        if (myEvent != null) {
                            switch (myEvent.getType()) {
                            case MQSCCommandEvent.TERMINAL_EVENT:
                                halt = true;
                                break;
                            case MQSCCommandEvent.INVALID_EVENT:
                            case MQSCCommandEvent.BAD_PAREN_EVENT:
                            case MQSCCommandEvent.BAD_QUOTE_EVENT:
                                try {
                                    //create error mark
                                    columnNumber = myEvent.getOffset();
                                    lineNumber = document
                                            .getLineOfOffset(columnNumber);
                                    lineStart = document
                                            .getLineOffset(lineNumber);
                                    map.clear();
                                } catch (BadLocationException e) {
                                    if (MQSCEditorPlugin.getDefault()
                                            .isDebugging()) {
                                        MQSCEditorPlugin
                                                .getDefault()
                                                .getLog()
                                                .log(
                                                        new Status(
                                                                IStatus.ERROR,
                                                                MQSCEditor.PLUGIN_ID,
                                                                0,
                                                                "Bad Location Exception",
                                                                e));
                                    }
                                }
                                MarkerUtilities.setLineNumber(map, lineNumber);
                                map.put(IMarker.LOCATION, file.getFullPath()
                                        .toString());

                                Integer charStart = new Integer(columnNumber);
                                if (charStart != null)
                                    map.put(IMarker.CHAR_START, charStart);

                                Integer charEnd = new Integer(columnNumber
                                        + myEvent.getLength());
                                if (charEnd != null)
                                    map.put(IMarker.CHAR_END, charEnd);

                                map.put(IMarker.SEVERITY, new Integer(
                                        IMarker.SEVERITY_ERROR));
                                if (myEvent.getType() == MQSCCommandEvent.INVALID_EVENT) {
                                    message = "MQSC syntax error: invalid token \""
                                            + myEvent.getValue()
                                            + "\" on line " + lineNumber;
                                } else if (myEvent.getType() == MQSCCommandEvent.BAD_PAREN_EVENT) {
                                    message = "MQSC syntax error: Unmatched parentheses  \""
                                            + myEvent.getValue()
                                            + "\" at character "
                                            + (columnNumber - lineStart)
                                            + " on line " + lineNumber;

                                } else if (myEvent.getType() == MQSCCommandEvent.BAD_QUOTE_EVENT) {
                                    message = "MQSC syntax error: Unmatched single-quote \""
                                            + myEvent.getValue()
                                            + "\" on line " + lineNumber;
                                }

                                MarkerUtilities.setMessage(map, message);
                                map.put(IMarker.MESSAGE, message);

                                try {
                                    MarkerUtilities.createMarker(file, map,
                                            IMarker.PROBLEM);
                                } catch (CoreException ee) {
                                    if (MQSCEditorPlugin.getDefault()
                                            .isDebugging()) {
                                        MQSCEditorPlugin
                                                .getDefault()
                                                .getLog()
                                                .log(
                                                        new Status(
                                                                IStatus.ERROR,
                                                                MQSCEditor.PLUGIN_ID,
                                                                0,
                                                                "Core Exception",
                                                                ee));
                                    }
                                }
                                break;
                            }
                        }
                    }
                    myToken = (IToken) scanner.nextToken();
                }
            }  
            */
        }
    }

    /*
     * @see org.eclipse.ui.texteditor.ExtendedTextEditor#editorContextMenuAboutToShow(org.eclipse.jface.action.IMenuManager)
     */
    protected void editorContextMenuAboutToShow(IMenuManager menu) {
        menu.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
        super.editorContextMenuAboutToShow(menu);
        addAction(menu, "ContentAssistProposal"); //$NON-NLS-1$
        addAction(menu, "ContentAssistTip"); //$NON-NLS-1$
        addAction(menu, "ContentFormatProposal"); //$NON-NLS-1$
    }
    @SuppressWarnings("rawtypes")
    public Object getAdapter(Class required) {
        if (IContentOutlinePage.class.equals(required)) {
            if (fOutlinePage == null) {
                fOutlinePage = new MQSCContentOutlinePage(
                        getDocumentProvider(), this);
                if (getEditorInput() != null)
                    fOutlinePage.setInput(getEditorInput());
            }
            return fOutlinePage;
        }

        if (fProjectionSupport != null) {
            Object adapter = fProjectionSupport.getAdapter(getSourceViewer(),
                    required);
            if (adapter != null)
                return adapter;
        }

        return super.getAdapter(required);
    }

    /*
     * (non-Javadoc) Method declared on AbstractTextEditor
     */
    protected void initializeEditor() {
        super.initializeEditor();
        setSourceViewerConfiguration(new MQSCSourceViewerConfiguration());
    }

    /*
     * @see org.eclipse.ui.texteditor.ExtendedTextEditor#createSourceViewer(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.jface.text.source.IVerticalRuler, int)
     */
    public ISourceViewer createSourceViewer(Composite parent,
            IVerticalRuler ruler, int styles) {

        fAnnotationAccess = createAnnotationAccess();
        fOverviewRuler = createOverviewRuler(getSharedColors());

        ISourceViewer viewer = new ProjectionViewer(parent, ruler,
                getOverviewRuler(), isOverviewRulerVisible(), styles);
        // ensure decoration support has been created and configured.
        getSourceViewerDecorationSupport(viewer);

        return viewer;
    }

    /*
     * @see org.eclipse.ui.texteditor.ExtendedTextEditor#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        ProjectionViewer viewer = (ProjectionViewer) getSourceViewer();
        fProjectionSupport = new ProjectionSupport(viewer,
                getAnnotationAccess(), getSharedColors());
        fProjectionSupport
                .addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.error"); //$NON-NLS-1$
        fProjectionSupport
                .addSummarizableAnnotationType("org.eclipse.ui.workbench.texteditor.warning"); //$NON-NLS-1$
        fProjectionSupport.install();
        viewer.doOperation(ProjectionViewer.TOGGLE);
    }

    /*
     * @see org.eclipse.ui.texteditor.AbstractTextEditor#adjustHighlightRange(int,
     *      int)
     */
    protected void adjustHighlightRange(int offset, int length) {
        ISourceViewer viewer = getSourceViewer();
        if (viewer instanceof ITextViewerExtension5) {
            ITextViewerExtension5 extension = (ITextViewerExtension5) viewer;
            extension.exposeModelRange(new Region(offset, length));
        }
    }

    protected IDocument getInputDocument() {
        IDocument document = getDocumentProvider().getDocument(input);
        return document;
    }

    protected IFile getInputFile() {
        IFileEditorInput ife = (IFileEditorInput) input;
        IFile file = ife.getFile();
        return file;
    }
}
