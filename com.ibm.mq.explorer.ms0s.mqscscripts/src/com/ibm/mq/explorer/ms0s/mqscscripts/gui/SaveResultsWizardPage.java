package com.ibm.mq.explorer.ms0s.mqscscripts.gui;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/


public class SaveResultsWizardPage extends WizardNewFileCreationPage {
    private String results;
    private ByteArrayInputStream resStream;
    
    public SaveResultsWizardPage(String pageName, IStructuredSelection selection) {
        super(pageName, selection);
    }

    protected InputStream getInitialContents() {
        resStream = new ByteArrayInputStream(results.getBytes());
        return resStream;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public IPath getContainerFullPath() {
        return super.getContainerFullPath();
    }

    public String getFileExtension() {
        return ".txt";
    }

}
