/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 19, 2007
 *
 * 
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.part.FileInPlaceEditorInput;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeFile;
import com.ibm.mq.explorer.ui.Common;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;

/**
 * @author jlowrey
 * 
 */
public class OpenScriptAction implements IActionDelegate {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    private MQSCScriptsTreeNodeFile myNode = null;

    public void run(IAction action) {
        MQExtObject myObj;

        if (myNode != null) {
            // if (myObj.equals(arg0)) return;
            myObj = (MQExtObject) myNode.getObject();
            if (myObj != null) {
                Object myIntObj = myObj.getInternalObject();
                if (myIntObj instanceof IResource) {
                    IResource resource = (IResource) myIntObj;
                    if (!resource.exists()) {
                        return;
                    }
                    ; // Can't open a script
                      // that doesn't exist.
                    IEditorInput myInput = new FileInPlaceEditorInput(
                            (IFile) resource);
                    try {
                        IWorkbench workbench = PlatformUI.getWorkbench();
                        // org.eclipse.ui.menus.IMenuService menuServ =
                        // (org.eclipse.ui.menus.IMenuService);
                        IWorkbenchWindow window = workbench
                                .getActiveWorkbenchWindow();
                        // window.getActivePage().showView(
                        // "org.eclipse.ui.views.ContentOutline");
                        IWorkbenchPage myPage = window.getActivePage();
                        IViewReference view = myPage
                                .findViewReference(Common.VIEWID_MQ_NAVIGATOR_VIEW);
                        if (view != null) {
                            IViewPart part = view.getView(false);
                            if ((part != null)
                                    && (part instanceof CommonNavigator)) {
                                IMenuManager menus = part.getViewSite()
                                        .getActionBars().getMenuManager();
                                menus.add(new GroupMarker(
                                        IWorkbenchActionConstants.FIND_EXT));
                            }
                        }
                        if (myPage != null && myInput != null) {
                            myPage.openEditor(myInput,
                                    "com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor");
                        }
                    } catch (PartInitException e) {
                        MQSCScriptsPlugin
                                .getDefault()
                                .getLog()
                                .log(new Status(
                                        IStatus.ERROR,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "Got PartInitException in OpenScriptAction.run()",
                                        e));
                        // } catch (WorkbenchException e) {
                        // MQSCScriptsPlugin.getDefault().getLog().log(
                        // new Status(IStatus.ERROR,
                        // MQSCScriptsPlugin.PLUGIN_ID, 0,
                        // "Got Workbench Exception in OpenScriptAction.run()",
                        // e));
                    }
                }
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
     * .IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection != null && selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            // Get the selected object
            Object obj = structuredSelection.getFirstElement();
            if (obj != null) {
                if (obj instanceof MQSCScriptsTreeNodeFile) {
                    myNode = (MQSCScriptsTreeNodeFile) obj;
                }
            }
        }
    }

}
