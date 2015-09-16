/*******************************************************************************
 * Copyright (c) 2007,2014 IBM Corporation and other Contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jeff Lowrey - Initial Contribution
 *******************************************************************************/
package com.ibm.mq.explorer.ms0s.mqsceditor;


import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This implements an IAnnotationHover that provides some additional logging when
 * the plugin is in debug mode.  
 **/


public class MQSCAnnotationHover implements IAnnotationHover {

	/* (non-Javadoc)
	 * Method declared on IAnnotationHover
	 */
	public String getHoverInfo(ISourceViewer sourceViewer, int lineNumber) {
		IDocument document= sourceViewer.getDocument();

		try {
			IRegion info= document.getLineInformation(lineNumber);
			return document.get(info.getOffset(), info.getLength());
		} catch (BadLocationException x) {
	        if (MQSCEditorPlugin.getDefault().isDebugging()) {
	            MQSCEditorPlugin.getDefault().getLog().log( new Status(IStatus.ERROR,MQSCEditor.PLUGIN_ID,0, "Bad Location Exception", x)); 
	        }
		}

		return null;
	}
}
