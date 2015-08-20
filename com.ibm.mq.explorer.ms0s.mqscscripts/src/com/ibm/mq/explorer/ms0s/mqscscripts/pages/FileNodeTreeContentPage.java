/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 29, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.pages;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.ibm.mq.explorer.ui.extensions.ContentPage;
import com.ibm.mq.explorer.ui.extensions.ContentTitleBar;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class FileNodeTreeContentPage extends ContentPage {

    private MQExtObject myObj = null;

    Label fullName = null;

    Label modTime = null;

    Label fileSize = null;
    TabItem editorItem, infoItem;
    TabFolder myTabFolder;

    /**
     * <p>
     * Constructs the page, including the table view. 
     * @param arg0
     * @param arg1
     */
    public FileNodeTreeContentPage(Composite arg0, int arg1) {
        super(arg0, arg1);
        RowLayout rowLayout = new RowLayout();
        rowLayout.wrap = false;
        rowLayout.pack = false;
        rowLayout.fill = true;
        rowLayout.justify = false;
        rowLayout.type = SWT.VERTICAL;
        rowLayout.marginLeft = 5;
        rowLayout.marginTop = 5;
        rowLayout.marginRight = 5;
        rowLayout.marginBottom = 5;
        rowLayout.spacing = 5;
        this.setLayout(rowLayout);
        ContentTitleBar title = new ContentTitleBar(this, SWT.NONE);
        title.setText("MQSC File");
        Label blank = new Label(this, SWT.NONE);
        blank.setText("");
        blank.pack();
        fullName = new Label(this, SWT.NONE);
        modTime = new Label(this, SWT.NONE);
        fileSize = new Label(this, SWT.NONE);
    }

    /*
     * <p>
     * Default, empty init method.  
     *  
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#init()
     */
    public void init() {

    }

    /* 
     * <p>
     * standard getId method that returns the class name. 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#getId()
     */
    public String getId() {
        return "com.ibm.mq.explorer.ms0s.mqscscripts.fileNodeTreeContentPage";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#setObject(com.ibm.mq.explorer.ui.extensions.MQExtObject)
     */
    public void setObject(MQExtObject arg0) {
        myObj = arg0;
    }

    /*
     * <p>
     * This method updates the content of the existing page.  
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#updatePage()
     */
    public void updatePage() {
        if (myObj != null) {
            Object myIntObj = myObj.getInternalObject();
            if (myIntObj instanceof IResource) {
                IResource myRes = (IResource) myIntObj;
                String fullPath = myRes.getRawLocation().toOSString();
                File tempFile = new File(fullPath);
                fullName.setText("");
                fullName.pack();
                fullName.setText("Full path: " + fullPath);
                fullName.pack();
                Date tempDate = new Date(tempFile.lastModified());
                modTime.setText("");
                modTime.pack();
                modTime.setText("Modification Time: "
                        + DateFormat.getDateTimeInstance().format(tempDate));
                modTime.pack();
                fileSize.setText("");
                fileSize.pack();
                long size = tempFile.length();
                DecimalFormat rounded = new DecimalFormat("#.##");
                if (size > (1024 * 1024 * 1000)) {
                    fileSize.setText("File Size: "
                            + rounded.format((size / 1024 / 1024 / 1000.0))
                            + " GB");
                } else if (size > (1024 * 1024)) {
                    fileSize.setText("File Size: "
                            + rounded.format(size / 1024 / 1024.0) + " MB");
                } else if (size > 1024) {
                    fileSize.setText("File Size: "
                            + rounded.format(size / 1024.0) + " KB");
                } else {
                    fileSize.setText("File Size: " + (size) + " bytes");
                }
                fileSize.pack();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#refresh()
     */
    public void refresh() {
        if (myObj != null) {
            updatePage();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#repaint()
     */
    public void repaint() {
        if (myObj != null) {
            updatePage();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#isEnableRefreshAction()
     */
    public boolean isEnableRefreshAction() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#isEnableSystemObjectsAction()
     */
    public boolean isEnableSystemObjectsAction() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#showSystemObjects(boolean)
     */
    public void showSystemObjects(boolean arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.ContentPage#instanceDeleted(java.lang.Object)
     */
    public void instanceDeleted(Object arg0) {
    }

}
