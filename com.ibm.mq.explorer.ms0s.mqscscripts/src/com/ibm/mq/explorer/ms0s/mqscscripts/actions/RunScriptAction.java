/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 24, 2007
 *
 */

package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.navigator.CommonNavigator;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.gui.RunScriptResultDialog;
import com.ibm.mq.explorer.ms0s.mqscscripts.threads.IMQSCResultAccumulator;
import com.ibm.mq.explorer.ms0s.mqscscripts.threads.RunMQSCScript;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeFile;
import com.ibm.mq.explorer.ui.Common;
import com.ibm.mq.explorer.ui.extensions.ExplorerExtension;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;
import com.ibm.mq.explorer.ui.extensions.MQQmgrExtObject;
import com.ibm.mq.explorer.ui.internal.queuemanager.UiQueueManager;
/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class RunScriptAction implements IActionDelegate, IMQSCResultAccumulator {

    private MQSCScriptsTreeNodeFile myNode = null;

    @SuppressWarnings("rawtypes")
    private ArrayList resultList;
    @SuppressWarnings("rawtypes")
    private HashMap resultSets = null;
    public static String SINGLE_RESULT = "QUEUEMANAGERNAME";
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
    public void run(IAction action) {
        String content = null;
        MQExtObject myObj;
        // Get qmgr to run on.
        MQView = getActiveNavigator();

        Object[] qmgrObj = askWhichQmgr();
        if (qmgrObj == null) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "Queue Manager Object is null when returned from RunScriptAction.askWhichQmgr(), exiting run()",
                                        null));
            }
            return;
        }

        // Validate selection is okay
        if (myNode == null) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "Tree Node is null when entering RunScriptAction.run()",
                                        null));
            }
            return;
        }
        myObj = (MQExtObject) myNode.getObject();
        if (myObj == null) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "MQ External Object is null when entering RunScriptAction.run()",
                                        null));
            }
            return;
        }
        Object myIntObj = myObj.getInternalObject();
        if (myIntObj == null) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "MQ Internal Object is null in RunScriptAction.run(), exiting",
                                        null));
            }
            return;
        }
        if (!(myIntObj instanceof IResource)) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "MQ Internal Object is not an IResource in RunScriptAction.run(), exiting",
                                        null));
            }
            return;
        }
        if (!((IResource) myIntObj).exists())
            return; // Can't run a script that doesn't exist in the file system.
        IFile myFile = (IFile) myIntObj;

        content = getDocumentContent(myFile);
        if (content == null) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "Document Content is null in RunScriptAction.run(), exiting",
                                        null));
            }
            return;
        }
        Document myDoc = new Document(content);
        /* Execute the command */

        /*
         * UiQueueManager intQmgrObj = (UiQueueManager) (qmgrObj[i]
         * .getInstanceId());
         */
        clearResults();
        RunMQSCScript actor = new RunMQSCScript(myDoc,
                null, myFile.getName(),
                (IMQSCResultAccumulator) this,MQView);
        for (int i = 0; i < qmgrObj.length; i++) {
            try {
                ProgressMonitorDialog myDiag = new ProgressMonitorDialog(
                        MQView.getViewSite().getShell());
                actor.setQmgrObject((MQQmgrExtObject) qmgrObj[i]);
                myDiag.run(true, true, actor);
            } catch (InvocationTargetException e) {
                MQSCScriptsPlugin
                        .getDefault()   
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.ERROR,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "InvocationTargetException in RunScriptAction.run()",
                                        e));
            } catch (InterruptedException e) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.ERROR,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "InterruptedException in RunScriptAction.run()",
                                        e));
            }
        }

        done(myFile.getName());
    }

    private String getDocumentContent(IFile myFile) {
        String content;
        try {
            InputStream myStream = myFile.getContents();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myStream));

            String line = null;
            StringBuffer buffer = new StringBuffer();
            while ((line = in.readLine()) != null) {
                buffer.append(line + "\n");
            }// end while
            content = buffer.toString();
            return content;
        } catch (CoreException e) {
            MQSCScriptsPlugin
                    .getDefault()
                    .getLog()
                    .log(
                            new Status(
                                    IStatus.ERROR,
                                    MQSCScriptsPlugin.PLUGIN_ID,
                                    0,
                                    "Core Exception in RunScriptAction.getDocumentContent()",
                                    e));
        } catch (IOException e) {
            MQSCScriptsPlugin
                    .getDefault()
                    .getLog()
                    .log(
                            new Status(
                                    IStatus.ERROR,
                                    MQSCScriptsPlugin.PLUGIN_ID,
                                    0,
                                    "IO Exception in RunScriptAction.getDocumentContent()",
                                    e));
        }
        return null;
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    private Object[] askWhichQmgr() {
        List qmgrList = ExplorerExtension.getShownQueueManagers();// MQSCScriptsPlugin.getDefault().getQmgrList();

        ArrayList activeQmgrs = new ArrayList();
        for (Iterator iter = qmgrList.iterator(); iter.hasNext();) {
            MQQmgrExtObject element = (MQQmgrExtObject) iter.next();
            if (element.isConnected()) {
                activeQmgrs.add(element);
                if (MQSCScriptsPlugin.getDefault().isDebugging()) {

                    MQSCScriptsPlugin
                            .getDefault()
                            .getLog()
                            .log(
                                    new Status(
                                            IStatus.INFO,
                                            MQSCScriptsPlugin.PLUGIN_ID,
                                            0,
                                            "Queue Manager "
                                                    + element.getName()
                                                    + " is connected, adding to Active Qmgr List",
                                            null));
                }
            } else {
                if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                    MQSCScriptsPlugin
                            .getDefault()
                            .getLog()
                            .log(
                                    new Status(
                                            IStatus.INFO,
                                            MQSCScriptsPlugin.PLUGIN_ID,
                                            0,
                                            "Queue Manager "
                                                    + element.getName()
                                                    + " is NOT connected, NOT adding to Active Qmgr List",
                                            null));
                }
            }
        }
        Collections.sort(activeQmgrs, new Comparator() {
            public int compare(Object o1, Object o2) {
                return (((MQQmgrExtObject) o1).getName()
                        .compareTo(((MQQmgrExtObject) o2).getName()));
            }
        });
        ListSelectionDialog myLd = new ListSelectionDialog(MQView.getViewSite().getShell(),
                (activeQmgrs).toArray(), new ArrayContentProvider(),
                new DecoratingLabelProvider(new LabelProvider(),
                        new QmgrLabelDecorator()),
                "Run Script on Queue Manager...");
        // myLd.setContentProvider();
        myLd.setTitle("Run Script on Queue Manager...");

        // myLd.setInput();
        // myLd.setLabelProvider();
        int buttonPressed = myLd.open();
        if (buttonPressed == Window.CANCEL) {
            return null;
        }
        Object[] res = myLd.getResult();
        if (res.length == 0)
            return null;
        // MQQmgrExtObject qmgrObj = (MQQmgrExtObject) res[0];
        return res;
    }
    public void addResult(String resultSetName, Object result,String command) {
        addResult(resultSetName, (String)result + "\n From command "+command);
        
    }
    @SuppressWarnings({"rawtypes","unchecked"})
    public void addResult(String _resultSetName, Object result) {
    	String resultSetName = _resultSetName;
        if (resultSetName == null)
            resultSetName = SINGLE_RESULT;
        resultList = (ArrayList) resultSets.get(resultSetName);
        if (resultList == null) {
            resultList = new ArrayList();
            resultSets.put(resultSetName, resultList);
        }

        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog()
                    .log(
                            new Status(IStatus.INFO,
                                    MQSCScriptsPlugin.PLUGIN_ID, 0,
                                    "Adding PCF Result text to results list for result set "
                                            + resultSetName + " Text: "
                                            + result, null));
        }

        resultList.add(result);

    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public void clearResultSet(String _resultSetName) {
    	String resultSetName = _resultSetName;
        if (resultSetName == null)
            resultSetName = SINGLE_RESULT;
        if (resultSets == null) {
            resultSets = new HashMap();
            return;
        }
        resultList = (ArrayList) resultSets.get(resultSetName);
        if (resultList != null)
            this.resultList = null;
        this.resultList = new ArrayList();
        resultSets.put(resultSetName, resultList);
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public void newResultSet(String _resultSetName) {
    	String resultSetName = _resultSetName;
        if (resultSetName == null)
            resultSetName = SINGLE_RESULT;
        if (resultSets == null) {
            resultSets = new HashMap();
        }
        resultList = new ArrayList();
        resultSets.put(resultSetName, resultList);
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public synchronized void addResult(Object result) {
        if (resultSets == null) {
            resultSets = new HashMap();
        }
        resultList = (ArrayList) resultSets.get(SINGLE_RESULT);
        if (resultList == null) {
            resultList = new ArrayList();
            resultSets.put(SINGLE_RESULT, resultList);
        }

        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Adding PCF Result text to results list" + result,
                            null));
        }

        resultList.add(result);
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public synchronized void clearResults() {
        if (MQSCScriptsPlugin.getDefault().isDebugging()) {
            MQSCScriptsPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCScriptsPlugin.PLUGIN_ID, 0,
                            "Result List is cleared", null));
        }
        if (resultSets == null) {
            resultSets = new HashMap();
            return;
        } else {
            resultSets.clear();
        }
        resultList = (ArrayList) resultSets.get(SINGLE_RESULT);
        if (resultList != null)
            this.resultList = null;
        this.resultList = new ArrayList();
        resultSets.put(SINGLE_RESULT, resultList);
    }

    public void done(String scriptName) {
        if (resultSets == null) {
            if (MQSCScriptsPlugin.getDefault().isDebugging()) {
                MQSCScriptsPlugin
                        .getDefault()
                        .getLog()
                        .log(
                                new Status(
                                        IStatus.INFO,
                                        MQSCScriptsPlugin.PLUGIN_ID,
                                        0,
                                        "Result List is null when entering RunScriptAction.done()",
                                        null));
            }
            return;
        }
        // Shell newShell = UiPlugin.getShell();
        RunScriptResultDialog myRd = new RunScriptResultDialog(MQView.getViewSite().getShell());
        myRd.setInput(resultSets);
        myRd.setScriptName(scriptName);
        myRd.open();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
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

    class QmgrLabelDecorator implements ILabelDecorator {
        public Image decorateImage(Image image, Object element) {
            MQQmgrExtObject qmgrObj = (MQQmgrExtObject) element;
            UiQueueManager intQmgrObj = (UiQueueManager) (qmgrObj
                    .getInstanceId());
            return intQmgrObj.getImage();
        }

        public String decorateText(String text, Object element) {
            return null;
        }

        public void addListener(ILabelProviderListener listener) {

        }

        public void dispose() {
        }

        public boolean isLabelProperty(Object element, String property) {
            return false;
        }

        public void removeListener(ILabelProviderListener listener) {
        }
    }

}
