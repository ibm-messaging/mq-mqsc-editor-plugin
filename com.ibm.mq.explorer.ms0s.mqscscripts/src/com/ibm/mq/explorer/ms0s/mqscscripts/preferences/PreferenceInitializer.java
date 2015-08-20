package com.ibm.mq.explorer.ms0s.mqscscripts.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 * Class used to initialize default preference values.
 **/
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = MQSCScriptsPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault(PreferenceConstants.P_HIDEPROJECT, false);
	}

}
