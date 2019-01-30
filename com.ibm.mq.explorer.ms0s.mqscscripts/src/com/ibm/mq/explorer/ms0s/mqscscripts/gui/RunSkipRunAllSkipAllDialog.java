/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Oct 1, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.gui;

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
import org.eclipse.swt.widgets.Text;

import com.ibm.mq.explorer.ms0s.mqscscripts.IMQSCScriptsConstants;
/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public class RunSkipRunAllSkipAllDialog extends Dialog {
   
    private Shell fShell;
    private String fText;
    private int resultCode;
	private Text textArea = null;
	private Label header = null;
	private Label trailer = null;
	private Button button = null;
	private Button button1 = null;
	private Button button2 = null;
	private Button button3 = null;
	private Button button4 = null;
    /**
     * @param parent
     */
    public RunSkipRunAllSkipAllDialog(Shell parent) {
        this(parent,SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL|SWT.RESIZE);
    }

    /**
     * @param parent
     * @param style
     */
    public RunSkipRunAllSkipAllDialog(Shell parent, int style) {
        super(parent, style);
    }

    
    public String getText() {
        return fText;
    }
    public void setText(String text) {
        fText = text;
    }
	protected void buttonPressed(int buttonId) {
	    resultCode = buttonId;
	    fShell.close();
	}

	private Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		Button localButton = new Button(parent, SWT.PUSH);
		localButton.setText(label);
		localButton.setData(new Integer(id));
//		button.setLayoutData(data);
		localButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				buttonPressed(((Integer) event.widget.getData()).intValue());
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(localButton);
			}
		}
		return localButton;
	}
	
    private void createContents(final Shell shell) {
		GridLayout gridLayout1 = new GridLayout();
		GridData gridData2 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData3 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData4 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData5 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData6 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData7 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData8 = new GridData(GridData.FILL_HORIZONTAL);
		GridData gridData9 = new GridData(GridData.FILL_HORIZONTAL);
		header = new Label(shell, SWT.NONE);
		textArea = new Text(shell, SWT.MULTI | SWT.READ_ONLY| SWT.BORDER | SWT.V_SCROLL);
        Display disp = shell.getDisplay();
        textArea.setBackground(disp.getSystemColor(SWT.COLOR_WHITE));
		trailer = new Label(shell, SWT.NONE);
		button = createButton(shell,IMQSCScriptsConstants.RUN_SCRIPT_RUN_BUTTON,"Run",false);
		button1 = createButton(shell,IMQSCScriptsConstants.RUN_SCRIPT_SKIP_BUTTON,"Skip",false);
		button2 = createButton(shell,IMQSCScriptsConstants.RUN_SCRIPT_RUNALL_BUTTON,"Run All",false);
		button3 = createButton(shell,IMQSCScriptsConstants.RUN_SCRIPT_SKIPALL_BUTTON,"Skip All",false);		
		button4 = createButton(shell,IMQSCScriptsConstants.RUN_SCRIPT_ABORT_BUTTON,"Abort",false);		
		shell.setLayout(gridLayout1);
		header.setLayoutData(gridData2);
		trailer.setLayoutData(gridData4);
		gridLayout1.numColumns = 5;
		gridData2.horizontalSpan = 5;
		gridData3.horizontalSpan = 5;
		gridData3.verticalSpan = 25;
		textArea.setLayoutData(gridData3);
		gridData4.horizontalSpan = 5;
		gridData5.grabExcessHorizontalSpace = true;
		button.setLayoutData(gridData5);
		gridData6.grabExcessHorizontalSpace = true;
		button1.setLayoutData(gridData6);
		gridData7.grabExcessHorizontalSpace = true;
		button2.setLayoutData(gridData7);
		gridData8.grabExcessHorizontalSpace = true;
		button3.setLayoutData(gridData8);
		gridData9.grabExcessHorizontalSpace = true;
		button4.setLayoutData(gridData9);
		shell.setSize(new org.eclipse.swt.graphics.Point(350,225));
        shell.setText("Invalid MQSC Command Found");
        header.setText("The following command appears to be invalid MQSC.");
        textArea.setText(fText);
        trailer.setText("Do you want to Run this command, Skip it, Run All invalid commands, or Skip All invalid commands?");
		shell.pack();
    }

    public int open() {
        if (fText == null) return IMQSCScriptsConstants.RUN_SCRIPT_INVALID_SCRIPTTEXT;
        fShell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        createContents(fShell);
        fShell.open();
        Display display = getParent().getDisplay();
        while (!fShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return resultCode;
    }
}
