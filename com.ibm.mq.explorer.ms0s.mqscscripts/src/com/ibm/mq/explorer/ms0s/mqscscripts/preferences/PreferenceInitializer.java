package com.ibm.mq.explorer.ms0s.mqscscripts.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.ibm.mq.explorer.ms0s.mqscscripts.MQSCScriptsPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = MQSCScriptsPlugin.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_HIDEPROJECT , false);
//		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
//		store.setDefault(PreferenceConstants.P_STRING,
//				"Default value");
	}

}
