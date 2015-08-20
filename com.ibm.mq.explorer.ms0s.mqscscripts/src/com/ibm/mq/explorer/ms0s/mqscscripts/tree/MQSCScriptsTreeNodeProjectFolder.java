/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 19, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.tree;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;

import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class MQSCScriptsTreeNodeProjectFolder extends TreeNode {

    String folderName = null;

    MQExtObject myObj;

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    public MQSCScriptsTreeNodeProjectFolder(TreeNode arg0, MQExtObject arg1,
            String arg2) {
        super(arg0, arg1, arg2);
        setName((String) arg1.getName());
        myObj = arg1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return folderName;
    }

    public void setName(String folderName) {
        this.folderName = folderName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getId()
     */
    public String getPath() {
        IResource resource = (IResource) (myObj.getInternalObject());

        return resource.getLocation().toOSString();

    }

    public String getId() {
        IResource resource = (IResource) (myObj.getInternalObject());

        return "Folder:" + resource.getLocation().toOSString();
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
        return "com.ibm.mq.explorer.ms0s.mqscscripts.folderTreeContentPage";
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
        ISharedImages myImages = PlatformUI.getWorkbench().getSharedImages();
        return myImages.getImage(ISharedImages.IMG_OBJ_FOLDER);
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
        return true;
    }

}
