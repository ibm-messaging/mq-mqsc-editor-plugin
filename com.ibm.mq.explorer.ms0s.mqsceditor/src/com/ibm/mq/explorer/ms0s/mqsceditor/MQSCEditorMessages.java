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
package com.ibm.mq.explorer.ms0s.mqsceditor;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This loads any messages from the resource bundle for the plugin.
 **/


public class MQSCEditorMessages {

	private static final String RESOURCE_BUNDLE= "com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorMessages";

	private static ResourceBundle fgResourceBundle= ResourceBundle.getBundle(RESOURCE_BUNDLE);

	private MQSCEditorMessages() {
	}

	public static String getString(String key) {
		try {
			return fgResourceBundle.getString(key);
		} catch (MissingResourceException e) {
	        if (MQSCEditorPlugin.getDefault().isDebugging()) {
	            MQSCEditorPlugin.getDefault().getLog().log( new Status(IStatus.ERROR,MQSCEditor.PLUGIN_ID,0, "Missing Resource Exception" + " !" + key + "! ", e)); 
	        }
			return "!" + key + "!";
		}
	}
	
	public static ResourceBundle getResourceBundle() {
		return fgResourceBundle;
	}
}
