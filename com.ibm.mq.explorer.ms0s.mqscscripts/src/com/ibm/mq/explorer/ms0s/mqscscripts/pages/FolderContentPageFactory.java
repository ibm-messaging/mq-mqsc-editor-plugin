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
 *
 */
public class FolderContentPageFactory implements IContentPageFactory { 

	FolderTreeContentPage myPage = null;
	
	/* (non-Javadoc)
	 * @see com.ibm.mq.explorer.ui.extensions.IContentPageFactory#getPageId()
	 */
	public String getPageId() {
		return "com.ibm.mq.explorer.ms0s.mqscscripts.folderTreeContentPage";
	}

	/* (non-Javadoc)
	 * @see com.ibm.mq.explorer.ui.extensions.IContentPageFactory#makePage(org.eclipse.swt.widgets.Composite)
	 */
	public ContentPage makePage(Composite arg0) {
		if (myPage == null) {
			myPage = new FolderTreeContentPage(arg0,SWT.NONE);
		}
		return myPage;
	}

}
