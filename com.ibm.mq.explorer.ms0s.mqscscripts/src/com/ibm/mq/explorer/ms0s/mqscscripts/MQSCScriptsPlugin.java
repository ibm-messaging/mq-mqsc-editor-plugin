/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * Created on February 14, 2007
 */
package com.ibm.mq.explorer.ms0s.mqscscripts;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 * The main plugin class for an Eclipse Plugin.
 * <p>
 * This initializes the necessary resources for the function of the MQSC Script
 * Projects folder element of the MQExplorer navigation tree. It also provides
 * methods for other classes in this plugin to access those resources.
 **/

public class MQSCScriptsPlugin extends AbstractUIPlugin {

	/*
	 */
	// The shared instance.
	private static MQSCScriptsPlugin plugin;
	// Resource bundle.
	private ResourceBundle resourceBundle;
	@SuppressWarnings("rawtypes")
	private static List qmgrList;
	public static final String PLUGIN_ID = "com.ibm.mq.explorer.ms0s.mqscscripts";

	/**
	 * The constructor.
	 */
	@SuppressWarnings("rawtypes")
	public MQSCScriptsPlugin() {
		super();
		plugin = this;
		try {
			resourceBundle = ResourceBundle
					.getBundle("com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPluginResources");
			if (qmgrList == null) {
				qmgrList = new ArrayList();
			}
		} catch (MissingResourceException x) {
			resourceBundle = null;
		}
	}

	@SuppressWarnings("rawtypes")
	public List getQmgrList() {
		if (qmgrList == null) {
			qmgrList = new ArrayList();
		}
		return qmgrList;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static MQSCScriptsPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the string from the plugin's resource bundle, or 'key' if not
	 * found.
	 */
	public static String getResourceString(String key) {
		ResourceBundle bundle = MQSCScriptsPlugin.getDefault()
				.getResourceBundle();
		try {
			return (bundle != null) ? bundle.getString(key) : key;
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Returns the plugin's resource bundle,
	 */
	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}
}
