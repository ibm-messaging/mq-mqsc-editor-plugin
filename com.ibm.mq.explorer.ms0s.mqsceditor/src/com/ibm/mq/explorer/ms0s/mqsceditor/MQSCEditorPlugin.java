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

import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEventNotifier;
import com.ibm.mq.explorer.ms0s.mqsceditor.lang.MQSCLanguageConfigurator;
import com.ibm.mq.explorer.ms0s.mqsceditor.model.MQSCDocumentModel;
import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCCodeScanner;
import com.ibm.mq.explorer.ms0s.mqsceditor.util.MQSCColorProvider;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This extends AbstractUIPlugin to provide the main plugin class. 
 * It creates the necessary editor components for working with MQSC text.  
 **/

public class MQSCEditorPlugin extends AbstractUIPlugin {

    public final static String MQSC_PARTITIONING = "__mqsc_partitioning";

    private static MQSCEditorPlugin fgInstance;

    private static MQSCPartitionScanner fPartitionScanner;

    private static MQSCColorProvider fColorProvider;

    private static MQSCCommandEventNotifier fNotifier;

    private MQSCCodeScanner fCodeScanner;
    
    private MQSCDocumentModel fDocModel;

    /**
     * Creates a new plugin instance.
     * 
     * @param descriptor
     */
    public MQSCEditorPlugin() {
        super();
        fgInstance = this;
    }

    /**
     * Returns the default plugin instance.
     * 
     * @return the default plugin instance
     */
    public static MQSCEditorPlugin getDefault() {
        return fgInstance;
    }

    /**
     * Return a scanner for creating MQSC partitions.
     */
    public MQSCPartitionScanner getMQSCPartitionScanner() {
        if (fPartitionScanner == null) {
            fPartitionScanner = new MQSCPartitionScanner();
        }
        return fPartitionScanner;
    }

    /**
     * Returns the singleton scanner.
     */
    public RuleBasedScanner getMQSCCodeScanner() {
        if (fCodeScanner == null)
            fCodeScanner = new MQSCCodeScanner(getMQSCColorProvider());
        return fCodeScanner;
    }

    /**
     * Returns the singleton color provider.
     */
    public MQSCColorProvider getMQSCColorProvider() {
        if (fColorProvider == null)
            fColorProvider = new MQSCColorProvider();
        return fColorProvider;
    }

    public MQSCLanguageConfigurator getMQSCLanguageConfiguration() {
        return MQSCLanguageConfigurator.getLanguageConfiguration();
    }

    public MQSCCommandEventNotifier getMQSCCommandEventNotifier() {
        if (fNotifier == null)
            fNotifier = new MQSCCommandEventNotifier();
        return fNotifier;
    }
    
    public MQSCDocumentModel getMQSCDocumentModel()	{
        if (fDocModel == null) {
            fDocModel = new MQSCDocumentModel();
        }
        return fDocModel;
    }

}
