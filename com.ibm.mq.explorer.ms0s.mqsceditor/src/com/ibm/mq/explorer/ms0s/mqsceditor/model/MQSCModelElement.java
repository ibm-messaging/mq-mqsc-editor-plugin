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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This class is a single element in the MQSCDocumentModel tree. 
 **/

public class MQSCModelElement {
    private int fOffset; // The offset of the partition that contains this
                         // command

    private int fLength; // The length of the partition that contains this
                         // command
    private int fIndex; // the index of the partition that contains this command

    private String fCommand; // The MQSC Command verb : alter, define, etc.

    private String fObject; // The MQSC Object name : queue, security, process,
                            // etc.

    private String fObjectValue; // The MQSC Object value:
                                 // 'SYSTEM.DEF.SVRCONN','MY.TEST.QUEUE', etc.

    private String fSubTypeName; // The MQSC paramter name that identifies the
                                 // subtype: chltype, type, etc.

    private String fSubType; // The MQSC subtype value: 'svrconn' in
                             // 'chltype(svrconn)' for example.
    @SuppressWarnings("rawtypes")
    private HashMap fParamValueMap; // Parameter/value map: 'MONQ'->"OFF",
                                    // 'MAXMSGL'->'4194304', etc.

    @SuppressWarnings("rawtypes")
    private List fInvalidEvents; // list of invalid events associated with this
                                 // partition.

    // private String rawText;
    // private String formattedMQSCCommand;
    // private PCFMessage commandAsPCF;
    @SuppressWarnings("rawtypes")
    private HashMap fFlags; // A generic container for any kind of state
                            // information that other classes might need to save

    private boolean fComplete;

    @SuppressWarnings("rawtypes")
    public MQSCModelElement() {
        super();
        fCommand = "";
        fObject = "";
        fObjectValue = "";
        fSubTypeName = "";
        fSubType = "";
        fParamValueMap = new HashMap();
        fFlags = new HashMap();
        fInvalidEvents = new ArrayList();
    }

    public boolean containsParameter(Object key) {
        return fParamValueMap.containsKey(key);
    }

    public Object getParamValue(Object key) {
        return fParamValueMap.get(key);
    }

    @SuppressWarnings({ "rawtypes" })
    public Set ParamNames() {
        return fParamValueMap.keySet();
    }

    @SuppressWarnings({ "unchecked" })
    public Object putParameterValue(Object key, Object value) {
        return fParamValueMap.put(key, value);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void putAll(Map t) {
        fParamValueMap.putAll(t);
    }

    @SuppressWarnings({ "rawtypes" })
    public Collection ParamValues() {
        return fParamValueMap.values();
    }

    public int getIndex() {
        return fIndex;
    }

    public int getOffset() {
        return fOffset;
    }

    public int getLength() {
        return fLength;
    }

    public String getCommand() {
        return fCommand;
    }

    public String getObject() {
        return fObject;
    }

    public String getObjectValue() {
        return fObjectValue;
    }

    public String getSubType() {
        return fSubType;
    }

    public String getSubTypeName() {
        return fSubTypeName;
    }

    public synchronized boolean flagsContainsKey(Object flagName) {
        return fFlags.containsKey(flagName);
    }

    public synchronized Object getFlag(Object flagName) {
        return fFlags.get(flagName);
    }
    @SuppressWarnings("unchecked")
    public synchronized Object putFlag(Object flagName, Object flagValue) {
        return fFlags.put(flagName, flagValue);
    }

    public synchronized Object removeFlag(Object flagName) {
        return fFlags.remove(flagName);
    }

    public void setIndex(int index) {
        fOffset = index;
    }

    public void setOffset(int offset) {
        fOffset = offset;
    }

    public void setLength(int length) {
        fLength = length;
    }

    public void setCommand(String command) {
        fCommand = command;
    }

    public void setObject(String object) {
        fObject = object;
    }

    public void setObjectValue(String objectValue) {
        fObjectValue = objectValue;
    }

    public void setSubType(String subType) {
        fSubType = subType;
    }

    public void setSubTypeName(String subTypeName) {
        fSubTypeName = subTypeName;
    }

    public boolean isInvalid() {
        return !(fInvalidEvents.isEmpty());
    }

    @SuppressWarnings({ "rawtypes" })
    public List getInvalidEvents() {
        return (List) fInvalidEvents;
    }

    @SuppressWarnings({ "unchecked" })
    public void addInvalidEvent(MQSCCommandEvent event) {
        if (!(fInvalidEvents.contains(event)))
            fInvalidEvents.add((Object) event);
    }

    public void clearInvalidStatus() {
        fInvalidEvents.clear();
    }

    public void clear() {
        fCommand = "";
        fObject = "";
        fObjectValue = "";
        fSubTypeName = "";
        fSubType = "";
        // fParamValueMap.clear();
        // fFlags.clear();
        // fInvalidEvents.clear();

    }

    public boolean isComplete() {
        return fComplete;
    }

    public void setComplete(boolean complete) {
        fComplete = complete;
    }

    @SuppressWarnings({ "rawtypes" })
    public String toString() {
        StringBuffer outBuff = new StringBuffer();
        outBuff.append("MQSC Model Element:");
        outBuff.append(" Index = " + fIndex);
        outBuff.append(" Offset = " + fOffset);
        outBuff.append(" Length = " + fLength);
        outBuff.append(" Command = " + fCommand);
        outBuff.append(" Object = " + fObject);
        outBuff.append(" Object Value = " + fObjectValue);
        outBuff.append(" Subtype = " + fSubType);
        outBuff.append(" Subtype Name = " + fSubTypeName);
        outBuff.append(" Complete = " + isComplete());
        outBuff.append(" Invalid = " + isInvalid());
        outBuff.append(" Parameters = ");
        for (Iterator iter = fParamValueMap.keySet().iterator(); iter.hasNext();) {
            String ParmName = (String) iter.next();
            outBuff.append(" " + ParmName + " = "
                    + fParamValueMap.get(ParmName) + ";");
        }
        outBuff.append(" Invalid Events = ");
        for (Iterator iter = fInvalidEvents.iterator(); iter.hasNext();) {
            MQSCCommandEvent ParmName = (MQSCCommandEvent) iter.next();
            outBuff.append(" " + ParmName + " ; ");
        }
        return outBuff.toString();
    }

    public boolean equals(Object obj) {
        boolean result = true;
        if (!(obj instanceof MQSCModelElement))
            return false;
        MQSCModelElement other = (MQSCModelElement) obj;
        result &= (this.getLength() == other.getLength());
        result &= (this.getOffset() == other.getOffset());
        if (this.getCommand() != null && other.getCommand() != null)
            result &= (this.getCommand().equalsIgnoreCase(other.getCommand()));
        else
            result = false;
        if (this.getObject() != null && other.getObject() != null)
            result &= (this.getObject().equalsIgnoreCase(other.getObject()));
        else
            result = false;
        if (this.getObjectValue() != null && other.getObjectValue() != null)
            result &= (this.getObjectValue().equalsIgnoreCase(other
                    .getObjectValue()));
        else
            result = false;
        if (this.getSubType() != null && other.getSubType() != null)
            result &= (this.getSubType().equalsIgnoreCase(other.getSubType()));
        else
            result = false;
        if (this.getSubTypeName() != null && other.getSubTypeName() != null)
            result &= (this.getSubTypeName().equalsIgnoreCase(other
                    .getSubTypeName()));
        else
            result = false;
        result &= (this.isComplete() == other.isComplete());
        result &= (this.isInvalid() == other.isInvalid());
        result &= this.fFlags.equals(other.fFlags);
        result &= this.fParamValueMap.equals(other.fParamValueMap);
        result &= this.fInvalidEvents.equals(other.fInvalidEvents);
        return result;
    }

}
