/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 20, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeProjectFolder;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author jlowrey
 *
 */
public class DeleteFolderAction implements IActionDelegate {
    private MQSCScriptsTreeNodeProjectFolder myNode = null;    

    /* (non-Javadoc)
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @SuppressWarnings("restriction")
    public void run(IAction action) {
        MQExtObject myObj;

        if (myNode != null) {
            myObj = (MQExtObject) myNode.getObject();
            if (myObj != null) {
                Object myIntObj = myObj.getInternalObject();
                if (myIntObj instanceof IResource) {
                    IResource resource = (IResource) myIntObj;
                    if (!resource.exists()) {return;}; //Can't delete a script that doesn't exist.
                    boolean confirm;
                    confirm = MessageDialog.openConfirm(null,"Delete Folder","This will delete the entire folder named '"+resource.getName()+"' including everything it contains.");
                    try {
                        if (confirm) {
                            resource.delete(false,null);
                            TreeNode myParent = myNode.getParent();
                            myParent.removeChildFromNode(myNode);
                            myParent.refresh();
                        }
                    } catch (CoreException e) {
                        MQSCScriptsPlugin.getDefault().getLog().log(
                                new Status(IStatus.ERROR,
                                        MQSCScriptsPlugin.PLUGIN_ID, 0,
                                        "Got Core Exception in DeleteScriptAction.run()",
                                        e));
                    }
                }
            }
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
                if (obj instanceof MQSCScriptsTreeNodeProjectFolder) {
                    myNode = (MQSCScriptsTreeNodeProjectFolder) obj;
                }
            }
        }

    }

}
