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
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCContentOutlinePage.Segment;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCModelElement;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This assigns a default image to each item in the outline view.  
 * 
 * It should eventually provide command specific graphics, to make it
 * easier to quickly find a command you want to look at.
 * 
 **/

public class MQSCContentOutlineLabelProvider extends LabelProvider {
    /*Ignore for now. 
     * We'd like, eventually, to have different icons in the outline view for each
     * type of MQSC command
     * 
     * public static final ImageDescriptor MQSC_ALTER_VALID;

     * public static final ImageDescriptor MQSC_ALTER_INVALID;

     * static final String BASE_URL = MQSCEditorMessages.getString("/");
     * 
     * static { String iconPath = "icons/";
     * 
     * MQSC_ALTER_VALID = createImageDescriptor(iconPath + "alterok.gif");
     * MQSC_ALTER_INVALID = createImageDescriptor(iconPath + "alterinv.gif"); }
     */
	
    public void dispose() {
    }

/*    private static ImageDescriptor createImageDescriptor(String path) {
        return ImageDescriptor.createFromFile(
                MQSCContentOutlineLabelProvider.class, path);
    }
*/
    public Image getImage(Object element) {
//        TODO: Create and implement partition specific graphics
        ISharedImages myImages = PlatformUI.getWorkbench().getSharedImages();
        if (element instanceof Segment) {
            Segment mySeg = (Segment) element;
            if (mySeg.element!= null && mySeg.element instanceof MQSCModelElement) {
                if (mySeg.element.isInvalid()) {
                    return myImages.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
                }
            }
        }
        
        return myImages.getImage(ISharedImages.IMG_OBJS_INFO_TSK);
    }

}
