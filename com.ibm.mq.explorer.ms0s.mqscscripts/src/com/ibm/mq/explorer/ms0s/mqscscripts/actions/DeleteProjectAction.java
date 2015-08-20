/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Apr 20, 2007
 *
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.actions;

import java.util.Arrays;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.navigator.CommonNavigator;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;
import com.ibm.mq.explorer.ms0s.mqscscripts.tree.MQSCScriptsTreeNodeProjectFolder;
import com.ibm.mq.explorer.ui.Common;
import com.ibm.mq.explorer.ui.extensions.TreeNode;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 * Some investigation could be done to see if the same action could be used for
 * deleting folders, files and projects. I think there was a reason for
 * separating them - but it may have simply been reflexive coding.
 * <p>
 * The type of the resource could be used to change which warning message is
 * shown.
 **/
public class DeleteProjectAction implements IActionDelegate {
	private MQSCScriptsTreeNodeProjectFolder myNode = null;
	private static CommonNavigator MQView;


	private CommonNavigator getActiveNavigator() {
		CommonNavigator nav = null;
		IViewReference view = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findViewReference(Common.VIEWID_MQ_NAVIGATOR_VIEW);
		/*
		 * <p> Get the MQExplorer Navigator View, for updating/manipulating parts of
		 * it. This probably needs to be abstracted to a common source file
		 * somewhere, and all of these Actions need to use it.
		 */
		if (view != null) {
			IViewPart part = view.getView(false);
			if ((part != null) && (part instanceof CommonNavigator)) {
				nav = (CommonNavigator) part;
			}
		}
		return nav;
	}

	@SuppressWarnings("restriction")
	public void run(IAction action) {
		// Get the right View object so we can open a dialog properly.
		MQView = getActiveNavigator();
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

		// Ask the user to select the project to delete.
		ListDialog dialog = new ListDialog(MQView.getViewSite().getShell());
		dialog.setContentProvider(new ArrayContentProvider());
		dialog.setInput(Arrays.asList((Object[]) root.getProjects()));
		dialog.setLabelProvider(new DecoratingLabelProvider(
				new LabelProvider(), new ProjectLabelDecorator()));
		dialog.setTitle("Select a project");

		if (dialog.open() == ListSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				IResource resource = (IResource) result[0];
				if (!resource.exists()) {
					return;
					// Can't delete a project that doesn't exist.
					// Maybe should produce a warning.
				}
				boolean confirm;
				// Give the user a chance to avoid a 'Whoops!'
				confirm = MessageDialog.openConfirm(null,
						"Delete MQSC Project",
						"This will delete the entire project named '"
								+ resource.getName()
								+ "' including everything it contains.");
				try {
					if (confirm) {
						resource.delete(false, null);
						TreeNode myParent = myNode.getParent();
						myParent.removeChildFromNode(myNode);
						myParent.refresh();
					}
				} catch (CoreException e) {
					MQSCScriptsPlugin
							.getDefault()
							.getLog()
							.log(new Status(
									IStatus.ERROR,
									MQSCScriptsPlugin.PLUGIN_ID,
									0,
									"Got Core Exception in DeleteScriptAction.run()",
									e));
				}
			}
		}

	}

	/*
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection != null && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			// Get the selected object
			Object obj = structuredSelection.getFirstElement();
			if (obj != null) {
				if (obj instanceof MQSCScriptsTreeNodeProjectFolder) {
					myNode = (MQSCScriptsTreeNodeProjectFolder) obj;
				}
			}
		}

	}

	class ProjectLabelDecorator implements ILabelDecorator {
		public Image decorateImage(Image image, Object element) {
			ISharedImages myImages = PlatformUI.getWorkbench()
					.getSharedImages();
			return myImages.getImage(IDE.SharedImages.IMG_OBJ_PROJECT);
		}

		@Override
		public String decorateText(String text, Object element) {
			if (text.startsWith("P")) {
				return text.substring(2);
			}
			return text;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {

		}

		@Override
		public void dispose() {
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
		}
	}

}
