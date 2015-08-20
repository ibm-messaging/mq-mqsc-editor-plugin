/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 19, 2007
 *
 * MQSC specific code written by Jeff Lowrey - IBM - jlowrey@us.ibm.com
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.tree;

import com.ibm.mq.explorer.ui.extensions.ITreeNodeFactory;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.TreeNode;
import com.ibm.mq.explorer.ui.extensions.TreeNodeId;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class MQSCScriptsTreeNodeFactory implements ITreeNodeFactory {

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ITreeNodeFactory#addChildrenToTreeNode(com.ibm.mq.explorer.ui.extensions.TreeNode)
     */
    TreeNode node = null;

    public void addChildrenToTreeNode(TreeNode arg0) {
        if (arg0 == null) {return;}
        String id = arg0.getTreeNodeId();
        if (id.equals(TreeNodeId.NODEID_WMQ)) {
            //			System.out.println("Adding Tree Node");
            if (node == null) {
                MQExtObject rootObj = new MQExtObject(null, "MQSC Scripts Folder", "java.lang.string", "com.ibm.mq.explorer.ms0s.mqscscripts", "MQSC Scripts Root");
                node = new MQSCScriptsTreeNodeRootFolder(arg0, rootObj,
                        "com.ibm.mq.explorer.ms0s.mqscscripts");
                MQSCScriptsFileNodeFactory.setMqscRoot(node);
            }
            arg0.addChildToNode(node, 1);
        }
    }


}