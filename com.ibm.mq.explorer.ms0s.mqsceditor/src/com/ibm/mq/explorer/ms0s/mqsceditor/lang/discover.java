package com.ibm.mq.explorer.ms0s.mqsceditor.lang;

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
public class discover {

    private static final String[] alterObjectNames = { "authinfo", "channel",
            "listener", "namelist", "process", "qalias", "qlocal", "qmgr",
            "qmodel", "qremote", "service", "sub", "topic", };
    private static final String[] clearObjectNames = { "qlocal", "topicstr", };
    private static final String[] defineObjectNames = { "authinfo", "channel",
            "listener", "namelist", "process", "qalias", "qlocal", "qmodel",
            "qremote", "service", "sub", "topic", };
    private static final String[] deleteObjectNames = { "authinfo", "channel",
            "listener", "namelist", "process", "qalias", "qlocal", "qmodel",
            "qremote", "service", "sub", "topic", };
    private static final String[] displayObjectNames = { "authinfo", "channel",
            "chstatus", "clusqmgr", "conn", "listener", "lsstatus", "namelist",
            "process", "pubsub", "qalias", "qcluster", "qlocal", "qmgr",
            "qmodel", "qmstatus", "qremote", "qstatus", "queue", "sbstatus",
            "service", "sub", "svstatus", "tcluster", "topic", "tpstatus", };
    private static final String[] endObjectNames = {};
    private static final String[] pingObjectNames = { "channel", "qmgr", };
    private static final String[] refreshObjectNames = { "cluster", "qmgr",
            "security", };
    private static final String[] resetObjectNames = { "channel", "cluster",
            "qmgr", };
    private static final String[] resolveObjectNames = { "channel", };
    private static final String[] resumeObjectNames = { "qmgr", };
    private static final String[] startObjectNames = { "channel", "chinit",
            "listener", "service", };
    private static final String[] stopObjectNames = { "channel", "conn",
            "listener", "service", };
    private static final String[] suspendObjectNames = { "qmgr", };
    /**
     * 
     */
    public discover() {
        super();
        doNothing();
    }
    private static void doNothing() {
        if (alterObjectNames == null || 
                clearObjectNames == null ||
                defineObjectNames==null ||
                deleteObjectNames==null ||
                displayObjectNames==null ||
                endObjectNames == null ||
                pingObjectNames == null ||
                refreshObjectNames == null ||
                resetObjectNames ==null ||
                resolveObjectNames == null ||
                resumeObjectNames == null ||
                startObjectNames==null ||
                stopObjectNames == null||
                suspendObjectNames == null
                ) return;
    }
    
}