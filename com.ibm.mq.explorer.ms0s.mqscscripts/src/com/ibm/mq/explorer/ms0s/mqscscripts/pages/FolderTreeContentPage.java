/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 29, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.pages;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileInPlaceEditorInput;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ui.extensions.ContentPage;
import com.ibm.mq.explorer.ui.extensions.ContentTitleBar;
import com.ibm.mq.explorer.ui.extensions.MQExtObject;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 * Creates the table that holds a view of folders.
 **/
public class FolderTreeContentPage extends ContentPage {
	private MQExtObject myObj = null;
	TableViewer fileTable = null;
	String[] titles = { "Name", "Modification Time", "Size", "Full Path" };
	int[] bounds = { 150, 150, 100, 460 };
	private static mqscVisitor myVisitor = null;
	private FolderTableComparator comparator;

	class mqscVisitor implements IResourceProxyVisitor {
		ArrayList<IResource> allFiles = new ArrayList<IResource>();

		public boolean visit(IResourceProxy proxy) {
			String visitName;
			visitName = proxy.getName();
			if (proxy.getType() == IResource.FILE
					&& (visitName.toLowerCase().endsWith(".mqsc")
							|| visitName.toLowerCase().endsWith(".tst") || visitName
							.toLowerCase().endsWith(".mqs"))) {
				IResource res = proxy.requestResource();
				allFiles.add(res);
			}
			return true;
		}

		@SuppressWarnings("rawtypes")
		public ArrayList getAllFiles() {
			return allFiles;
		}

	}

	/**
	 * when we want to add right-click pop-up menus, we will need to add a MouseListener. 
	 * 
	 * @param arg0
	 * @param arg1
	 */

	public FolderTreeContentPage(Composite arg0, int arg1) {
		super(arg0, arg1);
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginLeft = 5;
		gridLayout.marginTop = 5;
		gridLayout.marginRight = 5;
		gridLayout.marginBottom = 5;
		this.setLayout(gridLayout);
		ContentTitleBar title = new ContentTitleBar(this, SWT.NONE);
		title.setText("MQSC Scripts");
		title.pack();
		Label verbiage = new Label(this, SWT.NONE);
		verbiage.setText("Objects that appear in this folder are WebSphere MQ Script Command files.  \n\n"
				+ "In order to appear in this folder, the files must be in the current workspace \n\n"
				+ "and must have an extension of \".mqsc\" or \".mqs\" or \".tst\"\n");
		verbiage.pack();
		fileTable = new TableViewer(this, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		fileTable.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				tableDoubleClicked(event);
			}
		});
		final Table table = fileTable.getTable();
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		fileTable.getControl().setLayoutData(gridData);
		fileTable.getControl().pack();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		comparator = new FolderTableComparator();
		fileTable.setComparator(comparator);
		fileTable.setContentProvider(ArrayContentProvider.getInstance());
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				IResource res = (IResource) cell.getElement();
				cell.setText(res.getName());
			}
		});
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				IResource res = (IResource) cell.getElement();
				cell.setText(DateFormat.getDateTimeInstance().format(
						res.getLocalTimeStamp()));
			}
		});
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				IResource res = (IResource) cell.getElement();
				String fullPath = res.getRawLocation().toOSString();
				File tempFile = new File(fullPath);
				long size = tempFile.length();
				DecimalFormat rounded = new DecimalFormat("#.##");
				if (size > (1024 * 1024 * 1000)) {
					cell.setText(rounded.format((size / 1024 / 1024 / 1000.0))
							+ " GB");
				} else if (size > (1024 * 1024)) {
					cell.setText(rounded.format(size / 1024 / 1024.0) + " MB");
				} else if (size > 1024) {
					cell.setText(rounded.format(size / 1024.0) + " KB");
				} else {
					cell.setText((size) + " bytes");
				}
				tempFile = null;
				fullPath = null;
				rounded = null;
			}
		});
		col = createTableViewerColumn(titles[3], bounds[3], 3);
		col.setLabelProvider(new StyledCellLabelProvider() {
			@Override
			public void update(ViewerCell cell) {
				IResource res = (IResource) cell.getElement();
				String fullPath = res.getRawLocation().toOSString();
				cell.setText(fullPath);
				fullPath = null;
			}
		});

		Label blank1 = new Label(this, SWT.NONE);
		blank1.pack();
		Label blank2 = new Label(this, SWT.NONE);
		blank2.pack();
		Label blank3 = new Label(this, SWT.NONE);
		blank3.pack();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.mq.explorer.ui.extensions.ContentPage#init()
	 */
	public void init() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.mq.explorer.ui.extensions.ContentPage#getId()
	 */
	public String getId() {
		return "com.ibm.mq.explorer.ms0s.mqscscripts.folderTreeContentPage";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.ContentPage#setObject(com.ibm.mq.explorer
	 * .ui.extensions.MQExtObject)
	 */
	public void setObject(MQExtObject arg0) {
		myObj = arg0;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.mq.explorer.ui.extensions.ContentPage#updatePage()
	 */
	public void updatePage() {
		if (myVisitor == null)
			myVisitor = new mqscVisitor();
		if (myObj != null) {
			if (myObj.getName().equals("MQSC Scripts Root")) {
				// Must be the MQSC Scripts Root folder
				IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace()
						.getRoot();
				myVisitor.getAllFiles().clear();
				fileTable.setInput(null);
				try {
					myWorkspaceRoot.accept(myVisitor, IResource.FILE);
				} catch (CoreException e) {
					MQSCScriptsPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCScriptsPlugin.PLUGIN_ID,
									0,
									"Got Core Exception while accepting changes in MQSCScriptsFileNodeFactory.addChildrenToTreeNode()",
									e));
				}
				fileTable.setInput(myVisitor.getAllFiles().toArray());
			} else {
				try {
					IResource res = (IResource) myObj.getInternalObject();
					myVisitor.getAllFiles().clear();
					fileTable.setInput(null);
					res.accept(myVisitor, IResource.FILE);
					fileTable.setInput(myVisitor.getAllFiles().toArray());
				} catch (CoreException e) {
					MQSCScriptsPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCScriptsPlugin.PLUGIN_ID,
									0,
									"Got CoreException in FolderTreeContentPage updatePage",
									e));

				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.mq.explorer.ui.extensions.ContentPage#refresh()
	 */
	public void refresh() {
		if (myObj != null) {
			updatePage();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.mq.explorer.ui.extensions.ContentPage#repaint()
	 */
	public void repaint() {
		if (myObj != null) {
			updatePage();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.ContentPage#isEnableRefreshAction()
	 */
	public boolean isEnableRefreshAction() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.ContentPage#isEnableSystemObjectsAction
	 * ()
	 */
	public boolean isEnableSystemObjectsAction() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.ContentPage#showSystemObjects(boolean)
	 */
	public void showSystemObjects(boolean arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ibm.mq.explorer.ui.extensions.ContentPage#instanceDeleted(java.lang
	 * .Object)
	 */
	public void instanceDeleted(Object arg0) {
	}

	private TableViewerColumn createTableViewerColumn(String title, int bound,
			final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(fileTable,
				SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		column.addSelectionListener(getSelectionAdapter(column, colNumber));
		return viewerColumn;
	}

	private SelectionAdapter getSelectionAdapter(final TableColumn column,
			final int index) {
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				comparator.setColumn(index);
				int dir = comparator.getDirection();
				fileTable.getTable().setSortDirection(dir);
				fileTable.getTable().setSortColumn(column);
				fileTable.refresh();
			}
		};
		return selectionAdapter;
	}

	protected void tableDoubleClicked(DoubleClickEvent event) {
		ISelection selection = event.getSelection();
		if ((selection != null) && (selection instanceof IStructuredSelection)) {
			IStructuredSelection structSel = (IStructuredSelection) selection;
			Object object = structSel.getFirstElement();
			if (object instanceof IResource) {
				IEditorInput myInput = new FileInPlaceEditorInput(
						(IFile) object);
				try {
					IWorkbench workbench = PlatformUI.getWorkbench();
					IWorkbenchWindow window = workbench
							.getActiveWorkbenchWindow();
					IWorkbenchPage myPage = window.getActivePage();
					myPage.openEditor(myInput,
							"com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor");
				} catch (PartInitException e) {
					MQSCScriptsPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCScriptsPlugin.PLUGIN_ID,
									0,
									"Got PartInitException in MQSCScriptsTreeNode.openFile()",
									e));
				}

			}
		}
	}

}