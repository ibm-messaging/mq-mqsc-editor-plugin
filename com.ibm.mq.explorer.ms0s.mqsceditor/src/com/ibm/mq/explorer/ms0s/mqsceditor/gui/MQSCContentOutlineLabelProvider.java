/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 17, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.ibm.mq.explorer.ms0s.mqsceditor.gui.MQSCContentOutlinePage.Segment;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCModelElement;

/**
 */
public class MQSCContentOutlineLabelProvider extends LabelProvider {
    /*Ignore for now. 
    public static final ImageDescriptor MQSC_ALTER_VALID;

    public static final ImageDescriptor MQSC_ALTER_INVALID;

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
