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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.graphics.Point;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This enables hover text.
 * 
 * The getHoverInfo method is now deprecated. At some point, this needs to 
 * be reworked to the ITextHoverExtension2 interface. But we'll suppress the
 * warning temporarily and just put a TODO in here
 **/
@SuppressWarnings( "deprecation" )
public class MQSCTextHover implements ITextHover {

  // TODO: Move to a non-deprecated API
  
	/* (non-Javadoc)
	 * Method declared on ITextHover
	 */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null) {
			try {
				if (hoverRegion.getLength() > -1)
					return textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
			} catch (BadLocationException x) {
    	        if (MQSCEditorPlugin.getDefault().isDebugging()) {
    	            MQSCEditorPlugin.getDefault().getLog().log( new Status(IStatus.ERROR,MQSCEditor.PLUGIN_ID,0, "Bad Location Exception", x)); 
    	        }
			}
		}
		return MQSCEditorMessages.getString("MQSCTextHover.emptySelection"); //$NON-NLS-1$
	}
	
	/* (non-Javadoc)
	 * Method declared on ITextHover
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		Point selection= textViewer.getSelectedRange();
		if (selection.x <= offset && offset < selection.x + selection.y)
			return new Region(selection.x, selection.y);
		return new Region(offset, 0);
	}
}
