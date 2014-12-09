/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 22, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.wizards.datatransfer.FileSystemImportWizard;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeProjectFolder;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeRootFolder;
import com.ibm.mq.explorer.ui.Common;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author jlowrey
 *
 */
public class ImportScriptAction implements IActionDelegate {
    IStructuredSelection mySel = null;
    private static CommonNavigator MQView;

    private CommonNavigator getActiveNavigator() {
        CommonNavigator nav = null;
        IViewReference view = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .findViewReference(Common.VIEWID_MQ_NAVIGATOR_VIEW);
        if (view != null) {
            IViewPart part = view.getView(false);
            if ((part != null) && (part instanceof CommonNavigator)) {
                nav = (CommonNavigator) part;
            }
        }
        return nav;
    }

    @SuppressWarnings("restriction")
    public void run(IAction action) {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
                .getRoot();
//        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

        MQView = getActiveNavigator();
        IProject myProj = null;
        myProj = myWorkspaceRoot.getProject("MQSC Scripts");
        if (!myProj.exists()) {
            try {
                myProj.create(null);
                myProj.open(null);
                IProjectDescription myNewProjDesc = myProj.getDescription();
                myNewProjDesc.setName("MQSC Scripts");
                myWorkspaceRoot.refreshLocal(IWorkspaceRoot.DEPTH_ONE, null);
            } catch (CoreException e) {
                MQSCScriptsPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR,
                                MQSCScriptsPlugin.PLUGIN_ID, 0,
                                "Got Core Exception while creating project in ImportScriptAction.run()",
                                e));
            }
        }
        try {

            IWorkbenchWizard wizard = new FileSystemImportWizard();
            StructuredSelection newSel = new StructuredSelection((Object)myProj);
            wizard.init(workbench, newSel);
            WizardDialog dialog = new WizardDialog(MQView.getViewSite().getShell(), wizard);
            dialog.open();
            
            //Refresh workspace
            myProj.refreshLocal(IWorkspaceRoot.DEPTH_ONE, null);
            myWorkspaceRoot.refreshLocal(IWorkspaceRoot.DEPTH_ONE, null);
            if (mySel != null) {
                IStructuredSelection structuredSelection = (IStructuredSelection) mySel;
                //             Get the selected object
                Object obj = structuredSelection.getFirstElement();
                if (obj != null) {
                    if (obj instanceof MQSCScriptsTreeNodeRootFolder) {
//                        MQSCScriptsFileNodeFactory.addNode()
                        TreeNode node = (TreeNode)obj;
                        node.refresh();
                    }
                    if (obj instanceof MQSCScriptsTreeNodeProjectFolder) {
                        TreeNode node = (TreeNode)obj;
                        node.refresh();
                    }
                }
                
            }
        } catch (CoreException e) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR,
                            MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Got Core Exception while importing file in ImportScriptAction.run()",
                            e));
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection != null && selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            //             Get the selected object
            Object obj = structuredSelection.getFirstElement();
            if (obj != null) {
                if (obj instanceof MQSCScriptsTreeNodeRootFolder) {
                    mySel = structuredSelection;
                }
            }
        }
    
    }

}
