/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 19, 2007
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.tree;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.preferences.PreferenceConstants;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author Admin
 * 
 */
public class MQSCScriptsTreeNodeRootFolder extends TreeNode {

    /**
	 */
    public MQSCScriptsTreeNodeRootFolder(TreeNode arg0, MQExtObject arg1,
            String arg2) {
        super(arg0, arg1, arg2);

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        IPreferenceStore store = MQSCScriptsPlugin.getDefault()
                .getPreferenceStore();
        boolean hideProjects = store
                .getBoolean(PreferenceConstants.P_HIDEPROJECT);
        if (hideProjects) {
            return "MQSC Scripts";
        } else {
            return "MQSC Script Projects";
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.TreeNode#getId()
     */
    public String getId() {
        return "com.ibm.mq.explorer.ms0s.mqscscripts.scriptTreeNodeFolder";
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
        return 0;
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