/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 19, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.tree;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
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
import com.ibm.mq.explorer.ui.Common;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class MQSCScriptsTreeNodeFile extends TreeNode {

    String fileName = null;

    MQExtObject myObj;

    private static ImageDescriptor myImageDescr = null;

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public MQSCScriptsTreeNodeFile(TreeNode arg0, MQExtObject arg1,
            String arg2, CommonNavigator MQView) {
        super(arg0, arg1, arg2);
        setName((String) arg1.getName());
        myObj = arg1;
        MQView.getCommonViewer().addDoubleClickListener(
                new IDoubleClickListener() {

                    public void doubleClick(DoubleClickEvent event) {
                        treeDoubleClicked(event);
                    }
                });
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return fileName;
    }

    public void setName(String fileName) {
        this.fileName = fileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getId()
     */
    public String getId() {
        IResource resource = (IResource) (myObj.getInternalObject());

        return "File:" + resource.getRawLocation().toOSString();
        // return
        // "com.ibm.mq.explorer.ms0s.mqscscripts.scriptTreeNodeFile"+fileName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getSequence()
     */
    public String getSequence() {
        return "99999";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getContentPageId()
     */
    public String getContentPageId() {
        return "com.ibm.mq.explorer.ms0s.mqscscripts.fileNodeTreeContentPage";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getHelpId()
     */
    public String getHelpId() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getIcon()
     */
    public Image getIcon() {
        if (myImageDescr == null) {
            myImageDescr = ImageDescriptor.createFromURL(MQSCScriptsPlugin
                    .getDefault().getBundle().getEntry("/mqsc.gif"));
        }
        return myImageDescr.createImage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ibm.mq.explorer.ui.extensions.TreeNode#compare(com.ibm.mq.explorer
     * .ui.extensions.TreeNode, com.ibm.mq.explorer.ui.extensions.TreeNode)
     */
    public int compare(TreeNode arg0, TreeNode arg1) {
        return arg0.getId().compareTo(arg1.getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ibm.mq.explorer.ui.extensions.TreeNode#isContextMenuFromUiObject()
     */
    public boolean isContextMenuFromUiObject() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ibm.mq.explorer.ui.extensions.TreeNode#appendToContextMenu(org.eclipse
     * .swt.widgets.Shell, org.eclipse.jface.action.IMenuManager)
     */
    public void appendToContextMenu(Shell arg0, IMenuManager arg1) {
        arg1.add(new GroupMarker(IWorkbenchActionConstants.FIND_EXT));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ibm.mq.explorer.ui.extensions.TreeNode#isAddChildrenWhenExpanded()
     */
    public boolean isAddChildrenWhenExpanded() {
        return false;
    }

    /**
     * Handle double clicked event from the TreeViewer. Flip the expanded state
     * of the TreeNode that has been double-clicked
     * 
     * @param event
     *            the DoubleClick event
     */
    protected void treeDoubleClicked(DoubleClickEvent event) {
        ISelection selection = event.getSelection();
        if ((selection != null) && (selection instanceof IStructuredSelection)) {
            IStructuredSelection structSel = (IStructuredSelection) selection;
            Object object = structSel.getFirstElement();
            if (object instanceof MQSCScriptsTreeNodeFile) {
                TreeNode treeNode = (TreeNode) object;
                openFile(treeNode);
            }
        }

    }

    protected void openFile(TreeNode myNode) {
        myObj = (MQExtObject) myNode.getObject();
        if (myObj != null) {
            Object myIntObj = myObj.getInternalObject();
            if (myIntObj instanceof IResource) {
                IResource resource = (IResource) myIntObj;
                IEditorInput myInput = new FileInPlaceEditorInput(
                        (IFile) resource);
                try {
                    IWorkbench workbench = PlatformUI.getWorkbench();
                    IWorkbenchWindow window = workbench
                            .getActiveWorkbenchWindow();
                    IWorkbenchPage myPage = window.getActivePage();
                    IViewReference view = myPage
                            .findViewReference(Common.VIEWID_MQ_NAVIGATOR_VIEW);
                    IViewPart part = view.getView(false);
                    if ((part != null) && (part instanceof CommonNavigator)) {
                        IActionBars bars = part.getViewSite().getActionBars();
                        if (bars != null) {
                            IMenuManager menuManager = bars.getMenuManager();
                            if (menuManager != null && IWorkbenchActionConstants.FIND_EXT != null) {
                                menuManager.add(new GroupMarker(
                                        IWorkbenchActionConstants.FIND_EXT));
                            }
                        }
                    }
                    // myPage.showView("org.eclipse.ui.views.ContentOutline");
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
                                    "Got PartInitException in MQSCScriptsTreeNode.openFile()",
                                    e));
                }
            }
        }
    }

}
