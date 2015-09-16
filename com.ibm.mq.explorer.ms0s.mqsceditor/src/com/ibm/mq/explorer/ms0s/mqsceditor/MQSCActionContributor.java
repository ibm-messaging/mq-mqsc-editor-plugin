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

import java.util.ResourceBundle;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.RetargetTextEditorAction;
import org.eclipse.ui.texteditor.TextEditorAction;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This sets up the default ActionContributor.  
 **/


public class MQSCActionContributor extends TextEditorActionContributor {

    protected RetargetTextEditorAction fContentAssistProposal;
    protected RetargetTextEditorAction fContentAssistTip;
    protected RetargetTextEditorAction fFormatProposal;
    protected TextEditorAction fTogglePresentation;

    public MQSCActionContributor() {
        super();
        ResourceBundle bundle = MQSCEditorMessages.getResourceBundle();

        fContentAssistProposal = new RetargetTextEditorAction(bundle,
                "ContentAssistProposal."); //$NON-NLS-1$
        fContentAssistProposal
                .setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
        fContentAssistTip = new RetargetTextEditorAction(bundle,
                "ContentAssistTip."); //$NON-NLS-1$
        fContentAssistTip
                .setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_CONTEXT_INFORMATION);
        fFormatProposal = new RetargetTextEditorAction(bundle,
                "ContentFormatProposal.");
        IActionBars bars = getActionBars();
        if (bars != null) {
            IMenuManager menuManager = bars.getMenuManager();
            if (menuManager != null) {
                menuManager.add(new GroupMarker(
                        IWorkbenchActionConstants.FIND_EXT));
            }
        }
        fTogglePresentation = new MQSCPresentationAction();
    }

    /*
     * @see IEditorActionBarContributor#init(IActionBars)
     */
    public void init(IActionBars bars) {
        IMenuManager menuManager = bars.getMenuManager();
        if (menuManager != null) {
            menuManager
                    .add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
        }
        IMenuManager editMenu = menuManager
                .findMenuUsingPath(IWorkbenchActionConstants.M_EDIT);
        if (editMenu != null) {
            editMenu.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
            editMenu.add(new Separator());
            editMenu.add(fFormatProposal);
            editMenu.add(fContentAssistProposal);
            editMenu.add(fContentAssistTip);
        }
        super.init(bars);
    }

    private void doSetActiveEditor(IEditorPart part) {
        super.setActiveEditor(part);

        ITextEditor editor = null;
        if (part instanceof ITextEditor)
            editor = (ITextEditor) part;
        IActionBars bars = getActionBars();
        if (bars != null) {
            IMenuManager menuManager = bars.getMenuManager();
            if (menuManager != null) {
                menuManager.add(new GroupMarker(
                        IWorkbenchActionConstants.FIND_EXT));
            }
        }
        fContentAssistProposal.setAction(getAction(editor,
                "ContentAssistProposal")); //$NON-NLS-1$
        fContentAssistTip.setAction(getAction(editor, "ContentAssistTip")); //$NON-NLS-1$
        fFormatProposal.setAction(getAction(editor, "ContentFormatProposal"));

        fTogglePresentation.setEditor(editor);
        fTogglePresentation.update();
    }

    /*
     * @see IEditorActionBarContributor#setActiveEditor(IEditorPart)
     */
    public void setActiveEditor(IEditorPart part) {
        super.setActiveEditor(part);
        doSetActiveEditor(part);
    }

    /*
     * @see IEditorActionBarContributor#dispose()
     */
    public void dispose() {
        doSetActiveEditor(null);
        super.dispose();
    }
}
