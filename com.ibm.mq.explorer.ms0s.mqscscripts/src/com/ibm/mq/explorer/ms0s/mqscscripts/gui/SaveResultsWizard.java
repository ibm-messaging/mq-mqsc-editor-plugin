package com.ibm.mq.explorer.ms0s.mqscscripts.gui;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class SaveResultsWizard extends Wizard implements INewWizard {

    private SaveResultsWizardPage page;
    private String results;
    private IStructuredSelection selection;
    private String resultFileName;

    public SaveResultsWizard(String results,String resultFileName) {
        setWindowTitle("Save Results to File");
        this.results = results;
        this.resultFileName = resultFileName;
    }

    public void addPages() {
        page = new SaveResultsWizardPage("", selection);
        page.setResults(results);
        page.setFileName(resultFileName);
        page.setFileExtension("txt");
        addPage(page);
    }
    public boolean performFinish() {

        IFile file = page.createNewFile();
        if (file != null)
            return true;
        else
            return false;
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.selection = selection;
    }

}
