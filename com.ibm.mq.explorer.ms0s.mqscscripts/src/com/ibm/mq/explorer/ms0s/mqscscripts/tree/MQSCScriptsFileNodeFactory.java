/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on March 26, 2007
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.preferences.PreferenceConstants;
import com.ibm.mq.explorer.ui.Common;
import com.ibm.mq.explorer.ui.extensions.ITreeNodeFactory;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class MQSCScriptsFileNodeFactory implements ITreeNodeFactory,
        IResourceChangeListener, IResourceDeltaVisitor {
    private static TreeNode foundRoot = null;
    private static TreeNode mqscRoot = null;

    private static Visitor myVisitor = null;

    private static boolean initialRun = true;
    @SuppressWarnings("rawtypes")
    private static ArrayList addList = null;
    @SuppressWarnings("rawtypes")
    private static ArrayList deleteList = null;
    private static LinkedHashMap<String, MQSCScriptsTreeNodeProjectFolder> folderList = null;
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

    public MQSCScriptsFileNodeFactory() {
    }

    class Visitor implements IResourceProxyVisitor {
        @SuppressWarnings("rawtypes")
        Map allFiles = new LinkedHashMap();

        @SuppressWarnings("unchecked")
        public boolean visit(IResourceProxy proxy) {
            String visitName;
            visitName = proxy.getName();
            if (proxy.getType() == IResource.FILE
                    && (visitName.toLowerCase().endsWith(".mqsc")
                            || visitName.toLowerCase().endsWith(".tst") || visitName
                            .toLowerCase().endsWith(".mqs"))) {
                IResource res = proxy.requestResource();
                allFiles.put(res.getFullPath(), res);
            }
            return true;
        }

        @SuppressWarnings("rawtypes")
        public Map getAllFiles() {
            return allFiles;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.ibm.mq.explorer.ui.extensions.ITreeNodeFactory#addChildrenToTreeNode
     * (com.ibm.mq.explorer.ui.extensions.TreeNode)
     */
    public void resourceChanged(IResourceChangeEvent event) {
        // IResource res = event.getResource();
        try {
            event.getDelta().accept(this);
        } catch (CoreException e) {
            MQSCScriptsPlugin
                    .getDefault()
                    .getLog()
                    .log(new Status(
                            IStatus.ERROR,
                            MQSCScriptsPlugin.PLUGIN_ID,
                            0,
                            "Got Core Exception in MQSCScriptsFileNodeFactory.resourceChanged()",
                            e));
        }
    }

    @SuppressWarnings("unchecked")
    public boolean visit(IResourceDelta delta) {
        String visitName;
        TreeNode fileNode;
        MQSCScriptsTreeNodeProjectFolder folderNode = null;
        String fileName;
        String fileID;
        IResource folder;
        IPath folderPath;
        String folderName;
        String folderID;
        // int flags;
        IPreferenceStore store = MQSCScriptsPlugin.getDefault()
                .getPreferenceStore();
        boolean hideProjects = store
                .getBoolean(PreferenceConstants.P_HIDEPROJECT);
        IResource res = delta.getResource();
        visitName = res.getName();
        // flags = delta.getFlags();
        // if (hideProjects) {
        if (res.getType() == IResource.FILE
                && (visitName.toLowerCase().endsWith(".mqsc")
                        || visitName.toLowerCase().endsWith(".tst") || visitName
                        .toLowerCase().endsWith(".mqs"))) {
            switch (delta.getKind()) {
            case IResourceDelta.ADDED:
                fileName = res.getRawLocation().toOSString();
                fileID = "File:" + fileName;
                folder = (IResource) res.getParent();
                folderPath = folder.getLocation();
                folderName = folderPath.toOSString();
                folderID = "Folder:" + folderName;
                if (folderList.containsKey(folderID)) {
                    folderNode = (MQSCScriptsTreeNodeProjectFolder) folderList
                            .get(folderID);
                } else {
                    folderNode = (MQSCScriptsTreeNodeProjectFolder) createFolder(folder);
                }
                MQExtObject myFileObj = new MQExtObject(null, (Object) res,
                        "org.eclipse.core.resources.IResource", fileID,
                        res.getName());
                fileNode = new MQSCScriptsTreeNodeFile(folderNode, myFileObj,
                        "com.ibm.mq.explorer.ms0s.mqscscripts", MQView);
                addList.add(fileNode);

                break;
            case IResourceDelta.REMOVED:
                fileName = res.getFullPath().toString();
                fileID = "File:" + fileName;
                folder = (IResource) res.getParent();
                folderPath = folder.getLocation();
                folderName = folderPath.toOSString();
                folderID = "Folder:" + folderName;
                if (hideProjects) {
                    TreeNode[] children = foundRoot.getChildren();
                    for (int i = 0; i < children.length; i++) {
                        fileNode = children[i];
                        if (fileNode.getId().equals(fileID)) {
                            deleteList.add(fileNode);
                            i = children.length;
                        }
                    }
                } else {
                    if (folderList.containsKey(folderID)) {
                        folderNode = (MQSCScriptsTreeNodeProjectFolder) folderList
                                .get(folderID);
                        TreeNode[] children = folderNode.getChildren();
                        for (int i = 0; i < children.length; i++) {
                            fileNode = children[i];
                            if (fileNode.getId().equals(fileID)) {
                                deleteList.add(fileNode);
                                i = children.length;
                            }
                        }
                    }
                }
                break;
            }
        }
        if ((res.getType() == IResource.PROJECT) || (res.getType()==IResource.FOLDER)){
            //do work here.
            if (delta.getKind() == IResourceDelta.REMOVED) {
                folderPath = res.getLocation();
                folderName = folderPath.toOSString();
                folderID = "Folder:" + folderName;
                if (folderList.containsKey(folderID)) {
                    folderNode = (MQSCScriptsTreeNodeProjectFolder) folderList
                    .get(folderID);
                    deleteList.add(folderNode);
                }
            }
        }
        // }
        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked", "restriction" })
    public void addChildrenToTreeNode(TreeNode foundNode) {
        if (myVisitor == null)
            myVisitor = new Visitor();
        String id = foundNode.getTreeNodeId();
        if (!(id.equals("com.ibm.mq.explorer.ms0s.mqscscripts.scriptTreeNodeFolder"))) {
            return;
        }
        IPreferenceStore store = MQSCScriptsPlugin.getDefault()
                .getPreferenceStore();
        boolean hideProjects = store
                .getBoolean(PreferenceConstants.P_HIDEPROJECT);
        foundRoot = foundNode;
        TreeNode folderNode = null;
        MQExtObject myObj = null;
        if (initialRun) {
            // TODO: DONE? rework to handle preference.
            initialRun = false;
            addList = new ArrayList();
            deleteList = new ArrayList();
            folderList = new LinkedHashMap();

            MQView = getActiveNavigator();
            ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
                    IResourceChangeEvent.POST_CHANGE);
            IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
                    .getRoot();
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
            addFolderNode(myProj);

            try {
                myWorkspaceRoot.accept(myVisitor, IResource.FILE);
            } catch (CoreException e) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(new Status(
                                IStatus.ERROR,
                                MQSCScriptsPlugin.PLUGIN_ID,
                                0,
                                "Got Core Exception while accepting changes in MQSCScriptsFileNodeFactory.addChildrenToTreeNode()",
                                e));
            }
            Map scriptFiles = (Map) myVisitor.getAllFiles();
            MQSCScriptsTreeNodeFile fileNode = null;
            for (Iterator myIter = scriptFiles.keySet().iterator(); myIter
                    .hasNext();) {
                Object key = myIter.next();
                IResource element = (IResource) scriptFiles.get(key);
                String fileName = element.getRawLocation().toOSString();
                String fileID = "File:" + fileName;
                IResource folder = (IResource) element.getParent();
                IPath folderPath = folder.getLocation();
                String folderName = folderPath.toOSString();
                String folderID = "Folder:" + folderName;
                if (hideProjects) {
                    if (!foundRoot.isChildExist(fileID)) {
                        myObj = new MQExtObject(null, (Object) element,
                                "org.eclipse.core.resources.IResource", fileID,
                                element.getName());
                        fileNode = new MQSCScriptsTreeNodeFile(foundRoot,
                                myObj, "com.ibm.mq.explorer.ms0s.mqscscripts",
                                MQView);
                        foundRoot.addChildToNode(fileNode, 1);
                    }
                } else {
                    if (folderList.containsKey(folderID)) {
                        folderNode = folderList.get(folderID);
                    } else {
                        folderNode = createFolder(folder);
                    }
                    if (!folderNode.isChildExist(fileID)) {
                        myObj = new MQExtObject(null, (Object) element,
                                "org.eclipse.core.resources.IResource", fileID,
                                element.getName());
                        fileNode = new MQSCScriptsTreeNodeFile(folderNode,
                                myObj, "com.ibm.mq.explorer.ms0s.mqscscripts",
                                MQView);
                        folderNode.addChildToNode(fileNode, 1);
                    }
                }
            }
            return;
        }

        for (Iterator iter = deleteList.iterator(); iter.hasNext();) {
            TreeNode deleteMe = (TreeNode) iter.next();
            TreeNode deletedParent = deleteMe.getParent();
            // need to deal with case where nested folders have no children.
            deletedParent.removeChildFromNode(deleteMe);
            // deletedParent.update();
            while (!deletedParent.hasChildren()) {
                deleteMe = deletedParent;
                deletedParent = deleteMe.getParent();
                deletedParent.removeChildFromNode(deleteMe);
            }
        }
        deleteList.clear();
        for (Iterator add = addList.iterator(); add.hasNext();) {
            TreeNode addNode = (TreeNode) add.next();
            MQExtObject myEObj = (MQExtObject) (addNode.getParent().getObject());
            IResource folder = (IResource) (myEObj.getInternalObject());
            IPath folderPath = folder.getLocation();
            String folderName = folderPath.toOSString();
            String folderID = "Folder:" + folderName;
            if (!hideProjects) {
                if (folderList.containsKey(folderID)) {
                    folderNode = folderList.get(folderID);
                } else {
                    folderNode = createFolder(folder);
                }
                if (!folderNode.isChildExist(addNode.getId())) {
                    folderNode.addChildToNode(addNode, 1);
                }
            } else {
                if (!folderNode.isChildExist(addNode.getId())) {
                    folderNode.addChildToNode(addNode, 1);
                }

            }
        }
        addList.clear();
        mqscRoot.refresh();
    }

    public static TreeNode getMqscRoot() {
        return mqscRoot;
    }

    public static void setMqscRoot(TreeNode mqscRoot) {
        MQSCScriptsFileNodeFactory.mqscRoot = mqscRoot;
    }

    public static void addFolderNode(IResource thisFolder) {
        createFolder(thisFolder);
    }
    public static void addFileNode(IFile thisFile) {
        TreeNode fileNode;
        TreeNode folderNode = null;
        String fileName;
        String fileID;
        IResource folder;
        IPath folderPath;
        String folderName;
        String folderID;
        MQExtObject myObj = null;

        IPreferenceStore store = MQSCScriptsPlugin.getDefault()
                .getPreferenceStore();
        boolean hideProjects = store
                .getBoolean(PreferenceConstants.P_HIDEPROJECT);
        fileName = thisFile.getRawLocation().toOSString();
        fileID = "File:" + fileName;
        folder = thisFile.getParent();
        folderPath = folder.getLocation();
        folderName = folderPath.toOSString();
        folderID = "Folder:" + folderName;
        if (hideProjects) {
            if (!mqscRoot.isChildExist(fileID)) {
                myObj = new MQExtObject(null, (Object) thisFile,
                        "org.eclipse.core.resources.IResource", fileID,
                        thisFile.getName());
                fileNode = new MQSCScriptsTreeNodeFile(foundRoot, myObj,
                        "com.ibm.mq.explorer.ms0s.mqscscripts", MQView);
                mqscRoot.addChildToNode(fileNode, 1);
            }
        } else {
            if (folderList.containsKey(folderID)) {
                folderNode = folderList.get(folderID);
            } else {
                folderNode = createFolder(folder);
            }
            if (!folderNode.isChildExist(fileID)) {
                myObj = new MQExtObject(null, (Object) thisFile,
                        "org.eclipse.core.resources.IResource", fileID,
                        thisFile.getName());
                fileNode = new MQSCScriptsTreeNodeFile(folderNode,
                        myObj, "com.ibm.mq.explorer.ms0s.mqscscripts",
                        MQView);
                folderNode.addChildToNode(fileNode, 1);
            }
        }

    }

    private static TreeNode createFolder(IResource thisFile) {
        MQSCScriptsTreeNodeProjectFolder folderNode = null;
        String folderID;
        MQExtObject myObj = null;
        TreeNode parentNode;
        // if this resource is already built in the tree,return the node that
        // represents it.
        folderID = "Folder:" + thisFile.getLocation().toOSString();
        if (folderList.containsKey(folderID))
            return folderList.get(folderID);
        // if this resource is the workspace, return the MQSC Scripts Folder
        // Root Node.
        if (thisFile.getWorkspace() == thisFile)
            return getMqscRoot();
        // if this resource's parent is the workspace, return the MQSC Scripts
        // Folder Root Node.
        if (thisFile.getParent() == thisFile.getWorkspace())
            return getMqscRoot();
        if (thisFile instanceof IProject) {
            parentNode = getMqscRoot();
        } else {
            IResource parentResource = thisFile.getParent();
            parentNode = createFolder(parentResource);
        }
        myObj = new MQExtObject(null, (Object) thisFile,
                "org.eclipse.core.resources.IFolder", folderID,
                thisFile.getName());
        folderNode = new MQSCScriptsTreeNodeProjectFolder(parentNode, myObj,
                "com.ibm.mq.explorer.ms0s.mqscscripts");
        parentNode.addChildToNode(folderNode, 1);
        folderList.put(folderID, folderNode);
        return folderNode;
    }
}
