/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 20, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFolderMainPage;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.wizards.newresource.BasicNewFolderResourceWizard;

import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsFileNodeFactory;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeProjectFolder;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeRootFolder;
import com.ibm.mq.explorer.ui.Common;

/**
 * @author jlowrey
 * 
 */
public class NewFolderAction implements IActionDelegate {

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
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
                .getRoot();
//        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
//        IContainer myProj = null;
        MQView = getActiveNavigator();

        
        BasicNewFolderResourceWizard wizard = new BasicNewFolderResourceWizard();
//        Object testObj = mySel.getFirstElement();
//        if ((null != testObj) && (testObj instanceof MQSCScriptsTreeNodeProjectFolder)) {
//            Path location = new Path(((MQSCScriptsTreeNodeProjectFolder)testObj).getPath());
//            myProj = myWorkspaceRoot.getContainerForLocation(location);
//            //Project(().toString());
//        }
        StructuredSelection newSel = new StructuredSelection(myWorkspaceRoot);//(Object) myProj);
        wizard.init(workbench, newSel);
        WizardDialog dialog = new WizardDialog(MQView.getViewSite().getShell(), wizard);
        dialog.open();
        // FolderTreeContentPage.java:227
        // null pointer exception here. Presumably mySel.
        if (null == mySel) {
            return;
        }
        Object obj = mySel.getFirstElement();
        WizardNewFolderMainPage thisPage = (WizardNewFolderMainPage)wizard.getPages()[0];
        IFolder newFolder = thisPage.createNewFolder();
        MQSCScriptsFileNodeFactory.addFolderNode(newFolder);
        if (obj != null) {
            if (obj instanceof MQSCScriptsTreeNodeRootFolder) {
                MQSCScriptsTreeNodeRootFolder myNode = (MQSCScriptsTreeNodeRootFolder) obj;
                myNode.refresh();
            }
            //this says I need to write an interface. 
            if (obj instanceof MQSCScriptsTreeNodeProjectFolder) {
                MQSCScriptsTreeNodeProjectFolder myNode = (MQSCScriptsTreeNodeProjectFolder) obj;
                myNode.refresh();
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
                if (obj instanceof MQSCScriptsTreeNodeRootFolder
                        || obj instanceof MQSCScriptsTreeNodeProjectFolder) {
                    mySel = structuredSelection;
                }
            }
        }
    }

}
