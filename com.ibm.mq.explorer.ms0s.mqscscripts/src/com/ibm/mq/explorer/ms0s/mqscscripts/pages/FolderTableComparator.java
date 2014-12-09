package com.ibm.mq.explorer.ms0s.mqscscripts.pages;

import java.io.File;
import java.text.DateFormat;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class FolderTableComparator extends ViewerComparator {
    private int propertyIndex;
    private static final int DESCENDING = 1;
    private int direction = DESCENDING;

    public FolderTableComparator() {
        this.propertyIndex = 0;
        direction = DESCENDING;
    }

    public int getDirection() {
        return direction == 1 ? SWT.DOWN : SWT.UP;
    }

    public void setColumn(int column) {
        if (column == this.propertyIndex) {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        } else {
            // New column; do an ascending sort
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        IResource res1 = (IResource) e1;
        IResource res2 = (IResource) e2;
        int rc = 0;
        switch (propertyIndex) {
        case 0:
            rc = res1.getName().compareTo(res2.getName());
            break;
        case 1:
            rc = DateFormat
                    .getDateTimeInstance()
                    .format(res1.getLocalTimeStamp())
                    .compareTo(
                            DateFormat.getDateTimeInstance().format(
                                    res2.getLocalTimeStamp()));
            break;
        case 2:
            String fullPath1 = res1.getRawLocation().toOSString();
            File tempFile1 = new File(fullPath1);
            long size1 = tempFile1.length();
            String fullPath2 = res2.getRawLocation().toOSString();
            File tempFile2 = new File(fullPath2);
            long size2 = tempFile2.length();

            rc = (size1 - size2) > 0 ? 1 : -1;
            if (size1 == size2)
                rc = 0;
            break;
        case 3:
            fullPath1 = res1.getRawLocation().toOSString();
            fullPath2 = res2.getRawLocation().toOSString();
            rc = fullPath1.compareTo(fullPath2);
            break;
        default:
            rc = 0;
        }
        // If descending order, flip the direction
        if (direction == DESCENDING) {
            rc = -rc;
        }
        return rc;
    }

}
