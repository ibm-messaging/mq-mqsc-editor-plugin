/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jul 25, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.gui;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.navigator.CommonNavigator;

import com.ibm.mq.explorer.ms0s.mqscscripts.actions.RunScriptAction;
import com.ibm.mq.explorer.ui.Common;

/**
 * @author jlowrey
 * 
 */
public class RunScriptResultDialog extends Dialog {
    @SuppressWarnings("rawtypes")
    private HashMap fInput;

    // private Shell fShell;
    private String scriptName;
    private Text text;
    private String results;
    private TabFolder tf;    
    private static CommonNavigator MQView;

    private CommonNavigator getActiveNavigator() {
        CommonNavigator nav = null;
        IViewReference view = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .findViewReference(Common.VIEWID_MQ_NAVIGATOR_VIEW);
        if (view != null) {
            IViewPart part = view.getView(false);
            if ((part != null) && (part instanceof CommonNavigator)) {
                nav = (CommonNavigator) part;
            }
        }
        return nav;
    }

    /**
     * @param parent
     */
    public RunScriptResultDialog(Shell parent) {
        this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
    }

    public RunScriptResultDialog(Shell parent, int style) {
        super(parent, style);
        text = null;
    }

    @SuppressWarnings("rawtypes")
    public void setInput(Object input) {
        fInput = (HashMap) input;
    }

    public Object getInput() {
        return fInput;
    }

    @SuppressWarnings("rawtypes")
    private void createContents(final Shell shell) {
        shell.setLayout(new GridLayout(1, true));
        GridData data;
        Composite buttonGroup = new Composite(shell, SWT.SHADOW_NONE);
        GridLayout gridLayout = new GridLayout(2, false);
        buttonGroup.setLayout(gridLayout);
        data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        data.horizontalSpan = 1;
        buttonGroup.setLayoutData(data);

        final Label label = new Label(buttonGroup, SWT.NONE);
        String message = "Results of running Script " + scriptName
                + " on Queue Managers";
        data = new GridData(GridData.FILL_HORIZONTAL);
        label.setLayoutData(data);
        label.setText(message);
        Button saveResult = new Button(buttonGroup, SWT.PUSH);
        saveResult.setText("Save Results");
        saveResult
                .setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, true, false));

        tf = new TabFolder(shell, SWT.H_SCROLL | SWT.MULTI
                | SWT.READ_ONLY | SWT.BORDER | SWT.CLOSE | SWT.V_SCROLL);
        data = new GridData(GridData.FILL_BOTH);
        tf.setLayoutData(data);
        Display disp = shell.getDisplay();
        StringBuffer sb = new StringBuffer();
        int i;
        for (Iterator iterator = ((HashMap) fInput).keySet().iterator(); iterator
                .hasNext();) {
            String qmgrName = (String) iterator.next();
            if (qmgrName.equals(RunScriptAction.SINGLE_RESULT)) {
                continue;
            }
            TabItem ti = new TabItem(tf, SWT.H_SCROLL | SWT.MULTI
                    | SWT.READ_ONLY | SWT.BORDER |  SWT.V_SCROLL, 0);

            text = new Text(tf, SWT.H_SCROLL | SWT.MULTI | SWT.READ_ONLY
                    | SWT.BORDER | SWT.V_SCROLL);
            text.setBackground(disp.getSystemColor(SWT.COLOR_WHITE));
            ti.setControl(text);
            ti.setText(qmgrName);
            Object resultList = fInput.get(qmgrName);
            if (resultList instanceof Object[]) {
                Object[] results = (Object[]) resultList;
                for (i = 0; i < results.length; i++) {
                    if (results[i] == null)
                        continue;
                    sb.append(((String) results[i]).trim() + "\r\n");
                }
            } else if (resultList instanceof Collection) {
                List results = (List) resultList;
                for (Iterator iter = results.iterator(); iter.hasNext();) {
                    String result = (String) iter.next();
                    if (result == null)
                        continue;
                    sb.append(result.trim() + "\r\n");
                }
            }
            text.setText(sb.toString());
            sb = null;
            sb = new StringBuffer();
        }
        setResults(sb.toString());
        saveResult.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                
                saveResultsButton();
            }
        });

        Button ok = new Button(shell, SWT.PUSH);
        ok.setText("OK");
        data = new GridData(SWT.CENTER, SWT.NONE, true, false);
        ok.setLayoutData(data);
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                shell.close();
            }
        });

        shell.setDefaultButton(ok);
    }

    public Object open() {
//        if (fInput == null)
//            return null;
        MQView = getActiveNavigator();

        Shell shell = new Shell(MQView.getViewSite().getShell(), getStyle());
        createContents(shell);
        shell.setSize(600, 600);
        shell.open();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return fInput;
    }

    public void saveResultsButton() {
        String resultFileName;
        Date today;
        String output;
        String tempResult;
        TabItem selectedItem;
        Text tempTextControl;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("yyyyMMdd-hhmmss", Locale.getDefault());
        today = new Date();
        output = formatter.format(today);
        selectedItem = (TabItem)tf.getSelection()[0];
        resultFileName = scriptName.substring(0, scriptName.lastIndexOf('.'))
                + "-" + selectedItem.getText() + "-" + output;

        tempTextControl = (Text)selectedItem.getControl();
        tempResult = tempTextControl.getText();
        IWorkbenchWizard wizard = new SaveResultsWizard(tempResult,
                resultFileName);
        IWorkbench workbench = PlatformUI.getWorkbench();
        wizard.init(workbench, StructuredSelection.EMPTY);
        WizardDialog dialog = new WizardDialog(MQView.getViewSite().getShell(), wizard);
        dialog.open();

        return;
    }


    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public void setResults(String result) {
        this.results = result;
    }

    public String getResults() {
        return results;
    }
}
