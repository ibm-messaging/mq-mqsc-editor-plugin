/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 29, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.ibm.mq.explorer.ui.extensions.ContentPage;
import com.ibm.mq.explorer.ui.extensions.IContentPageFactory;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 * A very basic factory.  Minimal code is required here.
 * 
 **/
public class FileContentPageFactory implements IContentPageFactory {

    FileNodeTreeContentPage myPage = null;

    /*
     * <p>
     * standard getPageId method that returns the class name. 
     */
    public String getPageId() {
        return "com.ibm.mq.explorer.ms0s.mqscscripts.fileNodeTreeContentPage";
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ibm.mq.explorer.ui.extensions.IContentPageFactory#makePage(org.eclipse.swt.widgets.Composite)
     */
    public ContentPage makePage(Composite arg0) {
        if (myPage == null)
            myPage = new FileNodeTreeContentPage(arg0, SWT.NONE);
        return myPage;
    }

}
