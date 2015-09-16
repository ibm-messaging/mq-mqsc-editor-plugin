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
package com.ibm.mq.explorer.ms0s.mqsceditor.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.IMQSCCommandEventListener;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;

/**
* @author Jeff Lowrey
**/

/**
* <p>
* This builds an internal model of the MQSC document and publishes events as that document is parsed.  
**/


public class MQSCDocumentModel implements IMQSCCommandEventListener {

    SortedMap<Object, Object> fModel;

    private int lastParamIndex = 0;

    private MQSCCommandEvent lastParamName;

    class MQSCModelComparator implements Comparator<Object> {
        public int compare(Object o1, Object o2) {

            int o1Offset, o2Offset;
            o1Offset = 0;
            o2Offset = 0;
            o1Offset = new Integer((String) o1).intValue();
            o2Offset = new Integer((String) o2).intValue();
            /*
             * not correct - sorting done on hashkey, not on element pointed to
             * by hashkey... MQSCModelElement o1ME, o2ME; if (!(o1 instanceof
             * MQSCModelElement) && !(o2 instanceof MQSCModelElement)) { return
             * o1 == o2; } try { o1ME = (MQSCModelElement) o1; o2ME =
             * (MQSCModelElement) o2; o1Offset = o1ME.getOffset(); o2Offset =
             * o2ME.getOffset(); } catch (ClassCastException e) { if
             * (MQSCEditorPlugin.getDefault().isDebugging()) {
             * MQSCEditorPlugin.getDefault().getLog().log( new
             * Status(IStatus.ERROR, MQSCEditor.PLUGIN_ID, 0, "Class Cast
             * Exception", e)); } }
             */
            return o1Offset - o2Offset;
        }

        public boolean equals(Object obj) {
            return (obj instanceof MQSCModelComparator);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public MQSCDocumentModel() {
        super();
        fModel = Collections.synchronizedSortedMap(new TreeMap(
                new MQSCModelComparator()));
    }

    public void mqscTermFound(MQSCCommandEvent eventFound) {
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            "MQSCDocumentModel.mqscTermFound : eventFound = "
                                    + eventFound.toString(), null));
        }
        if (eventFound.getPartitionOffset() == 0
                && eventFound.getPartitionLength() == 0)
            return;
        int hashKey = eventFound.getPartitionIndex();
        if (hashKey == -1 || eventFound.getPartitionLength() == 0)
            return;
        // if (fModel.containsKey("" + hashKey)) {
        MQSCModelElement el = (MQSCModelElement) fModel.remove("" + hashKey);
        if (el == null) {
            el = new MQSCModelElement();
            el.setIndex(eventFound.getPartitionIndex());
            el.setOffset(eventFound.getPartitionOffset());
            el.setLength(eventFound.getPartitionLength());
        }
        // Set length = length of all values in model, not partition?
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            "MQSCDocumentModel.mqscTermFound : Model Element = "
                                    + el.toString(), null));
        }

        int currIndex;
        switch (eventFound.getType()) {
        case MQSCCommandEvent.COMMAND_WORD_EVENT:
//            el.clear();
            el.setCommand(eventFound.getValue());
            break;
        case MQSCCommandEvent.OBJECT_NAME_EVENT:
            el.setObjectValue(eventFound.getValue());
            break;
        case MQSCCommandEvent.OBJECT_VALUE_EVENT:
            el.setObject(eventFound.getValue());
            break;
        case MQSCCommandEvent.SUBTYPE_NAME_EVENT:
            currIndex = eventFound.getPartitionIndex();
            if (currIndex != lastParamIndex) {
                lastParamIndex = currIndex;
                lastParamName = eventFound;
            }
            el.setSubTypeName(eventFound.getValue());
            break;
        case MQSCCommandEvent.SUBTYPE_VALUE_EVENT:
            el.setSubType(eventFound.getValue());
            break;
        case MQSCCommandEvent.PARAMETER_NAME_EVENT:
            currIndex = eventFound.getPartitionIndex();
            if (currIndex != lastParamIndex) {
                lastParamIndex = currIndex;
                lastParamName = eventFound;
            }
            if (MQSCEditorPlugin.getDefault().getMQSCLanguageConfiguration()
                    .hasNoValue().contains(eventFound.getValue().toLowerCase())) {
                el.putParameterValue(eventFound.getValue(), "");
            }
            break;
        case MQSCCommandEvent.PARAMETER_VALUE_EVENT:
            if (lastParamName == null) {
                if (MQSCEditorPlugin.getDefault().isDebugging()) {
                    MQSCEditorPlugin
                            .getDefault()
                            .getLog()
                            .log(
                                    new Status(
                                            IStatus.ERROR,
                                            MQSCEditor.PLUGIN_ID,
                                            0,
                                            "MQSCDocumentModel.mqscTermFound : Found Parameter Value without Parameter Name in MQSCDocumentModel in event "
                                                    + eventFound.toString(),
                                            null));
                }
            } else {
                el.putParameterValue(lastParamName.getValue(), eventFound
                        .getValue());
            }
            break;
        case MQSCCommandEvent.INVALID_EVENT:
        case MQSCCommandEvent.BAD_PAREN_EVENT:
        case MQSCCommandEvent.BAD_QUOTE_EVENT:
            el.addInvalidEvent(eventFound);
        }
        if (el.getCommand() != "" && el.getCommand() != null
                && el.getObject() != "" & el.getObject() != null) {
            /*
             * test for object name... ignore for now. jrl-03/09/2008
             */
            if (el.getObject().equalsIgnoreCase("qmgr")
                    || (el.getObjectValue() != "" && el.getObjectValue() != null))
                el.setComplete(true);

        }
        put("" + hashKey, el);

    }

    public boolean containsKey(Object key) {
        return fModel.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return fModel.containsValue(value);
    }
    @SuppressWarnings("rawtypes")
    public Set entrySet() {
        return fModel.entrySet();
    }

    public Object firstKey() {
        return fModel.firstKey();
    }

    public Object get(Object key) {
        return fModel.get(key);
    }

    public boolean isEmpty() {
        return fModel.isEmpty();
    }

    public Set<Object> keySet() {
        return fModel.keySet();
    }

    public Object lastKey() {
        return fModel.lastKey();
    }

    public synchronized Object put(Object key, Object value) {
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log(
                    new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
                            "MQSCDocumentModel.put : Adding new element at key:"
                                    + (String) key + " value: "
                                    + ((MQSCModelElement) value).toString(),
                            null));
        }

        return fModel.put(key, value);
    }

    @SuppressWarnings({"rawtypes","unchecked"})
    public synchronized void putAll(Map t) {
        fModel.putAll(t);
    }
    public synchronized Object remove(Object key) {
       return fModel.remove(key);
    }

    public void clear() {
        fModel.clear();
    }

}
