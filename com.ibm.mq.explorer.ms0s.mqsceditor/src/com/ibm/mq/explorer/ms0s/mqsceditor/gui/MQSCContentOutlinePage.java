/*******************************************************************************
 * Copyright (c) 2007,2019 IBM Corporation and other Contributors.
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

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.TreeMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.DefaultPositionUpdater;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorMessages;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.IMQSCCommandEventListener;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCDocumentModel;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCModelElement;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This builds the contents of the Outline view. It registers a listener for
 * MQSC Command events, which are emitted by the parser. But either better
 * effort needs to be taken to ensure that the document isn't parsed multiple
 * times or taken in this class to identify multiple events that are duplicates
 * of each other.
 **/

public class MQSCContentOutlinePage extends ContentOutlinePage {

	protected Object fInput;

	protected IDocumentProvider fDocumentProvider;

	protected ITextEditor fTextEditor;

	public static class Segment {
		public String name;

		public Position position;

		public MQSCModelElement element;

		public Segment(String name, Position position, MQSCModelElement el) {
			this.name = name;
			this.position = position;
			this.element = el;
		}

		/*
		 * public Segment(String name, Position position, MQSCCommandEvent
		 * event) { this.name = name; this.position = position; this.event =
		 * event; }
		 */
		public Position getPosition() {
			return position;
		}

		public MQSCModelElement getElement() {
			return element;
		}

		public String toString() {
			return name;
		}
	}

	protected class ContentProvider implements ITreeContentProvider,
			IMQSCCommandEventListener {

		protected final static String SEGMENTS = "__mqsc_segments";

		protected final static String COMMANDISCOMPLETE = "__ContentOutlineProvider_command_is_complete";

		protected final static String COMMANDPARTITIONOFFSET = "__ContentOutlineProvider_partition_offset";

		protected final static String COMMANDPARTITIONLENGTH = "__ContentOutlineProvider_partition_length";

		protected IPositionUpdater fPositionUpdater = new DefaultPositionUpdater(
				SEGMENTS);

		@SuppressWarnings("rawtypes")
		protected TreeMap fContentModel = new TreeMap();

		public void mqscTermFound(MQSCCommandEvent eventFound) {
			if (MQSCEditorPlugin.getDefault().isDebugging()) {
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCContentOutlinePage.mqscTermFound : Entry, found term: "
										+ eventFound.toString(), null));
			}
			if (fContentModel == null)
				return; // Exit if we are called before we are initialized.
			MQSCDocumentModel myDM = MQSCEditorPlugin.getDefault()
					.getMQSCDocumentModel();
			int hashKey = eventFound.getPartitionOffset();
			MQSCModelElement el = (MQSCModelElement) myDM.get("" + hashKey);
			if (el != null) {
				Segment element;
				element = (Segment) fContentModel.get("" + hashKey);
				IDocument document = fDocumentProvider.getDocument(fInput);
				if (element != null) {
					addOutlineItem(document, el);
				} else {
					updateOutlineItem(document, el);
				}
			}
		}

		/*
		 * @see IContentProvider#inputChanged(Viewer, Object, Object)
		 */
		@SuppressWarnings("rawtypes")
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			if (MQSCEditorPlugin.getDefault().isDebugging()) {
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCContentOutlinePage.inputChanged : Entry, old input is "
										+ ((oldInput == null) ? "null"
												: "not null")
										+ " new input is "
										+ ((newInput == null) ? "null"
												: "not null"), null));
			}
			if (oldInput != null) {
				IDocument document = fDocumentProvider.getDocument(oldInput);
				if (document != null) {
					try {
						document.removePositionCategory(SEGMENTS);
					} catch (BadPositionCategoryException x) {
						if (MQSCEditorPlugin.getDefault().isDebugging()) {
							MQSCEditorPlugin
									.getDefault()
									.getLog()
									.log(new Status(
											IStatus.ERROR,
											MQSCEditor.PLUGIN_ID,
											0,
											"MQSCContentOutlinePage.inputChanged : Bad Position Category Exception",
											x));
						}
					}
					document.removePositionUpdater(fPositionUpdater);
				}
			}

			fContentModel.clear();

			if (newInput != null) {
				IDocument document = fDocumentProvider.getDocument(newInput);
				if (document != null) {
					document.addPositionCategory(SEGMENTS);
					document.addPositionUpdater(fPositionUpdater);
					MQSCDocumentModel myDM = MQSCEditorPlugin.getDefault()
							.getMQSCDocumentModel();

					for (Iterator iter = myDM.keySet().iterator(); iter
							.hasNext();) {
						// int offset;
						String currKey = (String) iter.next();
						if (MQSCEditorPlugin.getDefault().isDebugging()) {
							MQSCEditorPlugin
									.getDefault()
									.getLog()
									.log(new Status(IStatus.INFO,
											MQSCEditor.PLUGIN_ID, 0,
											"MQSCContentOutlinePage.inputChanged : looking at current key = "
													+ currKey, null));
						}
						MQSCModelElement currentCommand = (MQSCModelElement) myDM
								.get(currKey);
						if (currentCommand != null) {
							addOutlineItem(document, currentCommand);

						}
					}
					// parse(document);
				}
			}
		}

		@SuppressWarnings("unchecked")
		private void updateOutlineItem(IDocument document,
				MQSCModelElement currentCommand) {
			int offset;
			int length;
			String cmdName;
			String objType;
			String objName;
			if (MQSCEditorPlugin.getDefault().isDebugging()) {
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCContentOutlinePage.updateOutlineItem : Entry, current command is "
										+ currentCommand.toString(), null));
			}
			cmdName = currentCommand.getCommand();
			objType = currentCommand.getObject();
			objName = currentCommand.getObjectValue();
			offset = currentCommand.getOffset();
			length = currentCommand.getLength();
			Position p = new Position(offset, length);

			document.removePosition(p);
			fContentModel
					.put("" + offset,
							new Segment(
									MessageFormat.format(MQSCEditorMessages
											.getString("OutlinePage.segment"),
											new Object[] { cmdName, objType,
													objName, }), p,
									currentCommand));
			try {
				getTreeViewer().refresh();
			} catch (RuntimeException e) {
				// IGNORE THIS, We don't care if it throws a runtime exception.
			}

		}

		@SuppressWarnings("unchecked")
		private void addOutlineItem(IDocument document,
				MQSCModelElement currentCommand) {
			int offset;
			int length;
			String cmdName;
			String objType;
			String objName;
			if (MQSCEditorPlugin.getDefault().isDebugging()) {
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCContentOutlinePage.addOutlineItem : Entry, current command is "
										+ currentCommand.toString(), null));
			}
			try {
				cmdName = currentCommand.getCommand();
				objType = currentCommand.getObject();
				objName = currentCommand.getObjectValue();
				offset = currentCommand.getOffset();
				length = currentCommand.getLength();
				Position p = new Position(offset, length);
				document.addPosition(SEGMENTS, p);
				fContentModel.put(
						"" + offset,
						new Segment(MessageFormat.format(MQSCEditorMessages
								.getString("OutlinePage.segment"),
								new Object[] { cmdName, objType, objName, }),
								p, currentCommand));
				try {
					getTreeViewer().refresh();
				} catch (RuntimeException e) {
					// IGNORE THIS, We don't care if it throws a runtime
					// exception.
				}
			} catch (BadPositionCategoryException x) {
				if (MQSCEditorPlugin.getDefault().isDebugging()) {
					MQSCEditorPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCEditor.PLUGIN_ID,
									0,
									"MQSCContentOutlinePage.addOutlineItem : Bad Position Category Exception",
									x));
				}
			} catch (BadLocationException x) {
				if (MQSCEditorPlugin.getDefault().isDebugging()) {
					MQSCEditorPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCEditor.PLUGIN_ID,
									0,
									"MQSCContentOutlinePage.addOutlineItem : Bad Location Exception",
									x));
				}
			}
		}

		/*
		 * @see IContentProvider#dispose
		 */
		public void dispose() {
			if (fContentModel != null) {
				fContentModel.clear();
				fContentModel = null;
			}
		}

		/*
		 * @see IContentProvider#isDeleted(Object)
		 */
		public boolean isDeleted(Object element) {
			return false;
		}

		/*
		 * @see IStructuredContentProvider#getElements(Object)
		 */
		public Object[] getElements(Object element) {
			return fContentModel.values().toArray();
		}

		/*
		 * @see ITreeContentProvider#hasChildren(Object)
		 */
		public boolean hasChildren(Object element) {
			return element == fInput;
		}

		/*
		 * @see ITreeContentProvider#getParent(Object)
		 */
		public Object getParent(Object element) {
			if (element instanceof Segment)
				return fInput;
			return null;
		}

		/*
		 * @see ITreeContentProvider#getChildren(Object)
		 */
		public Object[] getChildren(Object element) {
			if (element == fInput)
				return fContentModel.values().toArray();
			return new Object[0];
		}

		
	}

	public MQSCContentOutlinePage(IDocumentProvider provider, ITextEditor editor) {
		super();
		fDocumentProvider = provider;
		fTextEditor = editor;
	}

	/*
	 * (non-Javadoc) Method declared on ContentOutlinePage
	 */
	public void createControl(Composite parent) {

		super.createControl(parent);

		TreeViewer viewer = getTreeViewer();
		ContentProvider myContentProvider = new ContentProvider();

		viewer.setContentProvider(myContentProvider);
		viewer.setLabelProvider(new MQSCContentOutlineLabelProvider());
		viewer.addSelectionChangedListener(this);

		if (fInput != null)
			viewer.setInput(fInput);
		MQSCEditorPlugin
				.getDefault()
				.getMQSCCommandEventNotifier()
				.addCommandEventListener(
						(IMQSCCommandEventListener) myContentProvider, false);

	}

	/*
	 * (non-Javadoc) Method declared on ContentOutlinePage
	 */
	public void selectionChanged(SelectionChangedEvent event) {

		super.selectionChanged(event);

		ISelection selection = event.getSelection();
		if (selection.isEmpty())
			fTextEditor.resetHighlightRange();
		else {
			Segment segment = (Segment) ((IStructuredSelection) selection)
					.getFirstElement();
			int start = segment.position.getOffset();
			int length = segment.position.getLength();
			try {
				fTextEditor.setHighlightRange(start, length, true);
			} catch (IllegalArgumentException x) {
				fTextEditor.resetHighlightRange();
				if (MQSCEditorPlugin.getDefault().isDebugging()) {
					MQSCEditorPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCEditor.PLUGIN_ID,
									0,
									"MQSCContentOutlinePage.addOutlineItem : Illegal Argument Exception",
									x));
				}
			}
		}
	}

	public void setInput(Object input) {
		fInput = input;
		update();
	}

	public void update() {
		TreeViewer viewer = getTreeViewer();

		if (viewer != null) {
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(fInput);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}
}
