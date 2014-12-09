/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 20, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.gui.NewScriptFileWizard;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeProjectFolder;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeRootFolder;
import com.ibm.mq.explorer.ui.Common;

/**
 * @author jlowrey
 * 
 */
public class NewScriptFileAction implements IActionDelegate {

    IStructuredSelection mySel;

    private static CommonNavigator MQView;

    private CommonNavigator getActiveNavigator() {
        CommonNavigator nav = null;
        IViewReference view = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .findViewReference(Common.VIEWID_MQ_NAVIGATOR_VIEW);
        if (view != null) {
            IViewPart part = view.getView(false);
            if ((part != null) && (part instanceof CommonNavigator)) {
                IMenuManager menus = part.getViewSite().getActionBars().getMenuManager();
                menus.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
                nav = (CommonNavigator) part;
            }
        }
        return nav;
    }
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @SuppressWarnings("restriction")
    public void run(IAction action) {
        // Check to see if my default project exists.
        MQView = getActiveNavigator();
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
                .getRoot();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        IContainer myProj = null;
        myProj = myWorkspaceRoot.getProject("MQSC Scripts");
        if (!myProj.exists()) {
            try {
                ((IProject) myProj).create(null);
                ((IProject) myProj).open(null);
                IProjectDescription myNewProjDesc = ((IProject) myProj)
                        .getDescription();
                myNewProjDesc.setName("MQSC Scripts");
                myWorkspaceRoot.refreshLocal(IWorkspaceRoot.DEPTH_ONE, null);
            } catch (CoreException e) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(new Status(
                                IStatus.ERROR,
                                MQSCScriptsPlugin.PLUGIN_ID,
                                0,
                                "Got Core Exception in NewScriptFileAction.run()",
                                e));
            }
        }
        // try {
        try {
            window.getActivePage().showView(
                    "org.eclipse.ui.views.ContentOutline");
        } catch (PartInitException e) {
            e.printStackTrace();
        }
         IWorkbenchWizard wizard = new NewScriptFileWizard();
        // This worked and is a viable option. Might want to extend it to allow
        // for adding folders.
        //BasicNewFileResourceWizard wizard = new BasicNewFileResourceWizard();

        // construct a selection that points to the IProject that's been
        // selected.
        // this worked great the first time. wheeeeeeee! And then later it fails
        // miserable for no apparent reason. wheeee!!!!:-(
        Object testObj = mySel.getFirstElement();
        if (null != testObj) {
            if (testObj instanceof MQSCScriptsTreeNodeProjectFolder) {
                Path location = new Path(
                        ((MQSCScriptsTreeNodeProjectFolder) testObj).getPath());
                myProj = myWorkspaceRoot.getContainerForLocation(location);
            }
            if (testObj instanceof MQSCScriptsTreeNodeRootFolder) {
                myProj = myWorkspaceRoot.getProject("MQSC Scripts");
            }
        }
        StructuredSelection newSel = new StructuredSelection(
                (Object) myProj);
        wizard.init(workbench, newSel);
        // wizard.
        WizardDialog dialog = new WizardDialog(MQView.getViewSite().getShell(), wizard);
        dialog.open();
        // FolderTreeContentPage.java:227
        // null pointer exception here. Presumably mySel.
        if (null == mySel) {
            return;
        }
        Object obj = mySel.getFirstElement();
        if (obj != null) {
            if (obj instanceof MQSCScriptsTreeNodeRootFolder) {
                MQSCScriptsTreeNodeRootFolder myNode = (MQSCScriptsTreeNodeRootFolder) obj;
                myNode.refresh();
            }
            // this says I need to write an interface.
            if (obj instanceof MQSCScriptsTreeNodeProjectFolder) {
                MQSCScriptsTreeNodeProjectFolder myNode = (MQSCScriptsTreeNodeProjectFolder) obj;
                myNode.refresh();
            }

        }
        /*
         * } catch (WorkbenchException e) {
         * MQSCScriptsPlugin.getDefault().getLog().log( new
         * Status(IStatus.ERROR, MQSCScriptsPlugin.PLUGIN_ID, 0,
         * "Got Workbench Exception in NewScriptFileAction.run()", e)); }
         */

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
                if (obj instanceof MQSCScriptsTreeNodeRootFolder
                        || obj instanceof MQSCScriptsTreeNodeProjectFolder) {
                    mySel = structuredSelection;
                }
            }
        }
    }

}
