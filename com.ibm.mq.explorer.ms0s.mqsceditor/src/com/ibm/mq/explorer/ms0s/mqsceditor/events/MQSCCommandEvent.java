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
package com.ibm.mq.explorer.ms0s.mqsceditor.events;

import java.util.EventObject;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This defines the contents of an MQSC Command Event and methods to access
 * the fields in it. 
 * 
 **/
public class MQSCCommandEvent extends EventObject {

    /**
     * 
     */
    private static final long serialVersionUID = 4890279502019895601L;

    public static final int COMMAND_WORD_EVENT = 0;

    public static final int OBJECT_NAME_EVENT = COMMAND_WORD_EVENT + 1;

    public static final int OBJECT_VALUE_EVENT = OBJECT_NAME_EVENT + 1;

    public static final int PARAMETER_NAME_EVENT = OBJECT_VALUE_EVENT + 1;

    public static final int PARAMETER_VALUE_EVENT = PARAMETER_NAME_EVENT + 1;

    public static final int SUBTYPE_NAME_EVENT = PARAMETER_VALUE_EVENT + 1;

    public static final int SUBTYPE_VALUE_EVENT = SUBTYPE_NAME_EVENT + 1;

    public static final int INVALID_EVENT = SUBTYPE_VALUE_EVENT + 1;

    public static final int BAD_QUOTE_EVENT = INVALID_EVENT + 1;

    public static final int BAD_PAREN_EVENT = BAD_QUOTE_EVENT + 1;

    public static final int TERMINAL_EVENT = BAD_PAREN_EVENT + 1;

    private int eventType;

    private String eventValue;

    private int length;

    private int offset;

    private String documentPath;

    private int partitionOffset;

    private int partitionLength;

    private int partitionIndex;

    public MQSCCommandEvent(Object source, int newType, String newValue,
            int startPos, int length, int partStart, int partLength,
            int partIndx) {
        super(source);
        eventType = newType;
        eventValue = newValue;
        this.length = length;
        offset = startPos;
        partitionOffset = partStart;
        partitionLength = partLength;
        partitionIndex = partIndx;
    }

    public int getType() {
        return eventType;
    }

    public String getValue() {
        return eventValue;
    }

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset;
    }

    public String toString() {
        StringBuffer outBuff = new StringBuffer();
        outBuff.append("MQSC Command Event: Type = ");
        outBuff.append(eventType + " ");
        switch (eventType) {
        case COMMAND_WORD_EVENT:
            outBuff.append("Command Name ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case OBJECT_VALUE_EVENT:
            outBuff.append("Object Type ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case OBJECT_NAME_EVENT:
            outBuff.append("Object Name ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case SUBTYPE_NAME_EVENT:
            outBuff.append("Subtype Keyword ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case SUBTYPE_VALUE_EVENT:
            outBuff.append("Subtype Value");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case PARAMETER_NAME_EVENT:
            outBuff.append("Parameter Keyword ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case PARAMETER_VALUE_EVENT:
            outBuff.append("Parameter Value ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case INVALID_EVENT:
            outBuff.append("Invalid MQSC ");
            outBuff.append(" = ");
            outBuff.append(eventValue);
            break;
        case TERMINAL_EVENT:
            outBuff.append("Command Termination");
            break;

        }
        outBuff.append(" offset = ");
        outBuff.append(offset);
        outBuff.append(" length = ");
        outBuff.append(length);
        outBuff.append(" partition  index = ");
        outBuff.append(partitionIndex);
        outBuff.append(" partition offset = ");
        outBuff.append(partitionOffset);
        outBuff.append(" partition  length = ");
        outBuff.append(partitionLength);
        return outBuff.toString();
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public void setEventValue(String eventValue) {
        this.eventValue = eventValue;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public int getPartitionLength() {
        return partitionLength;
    }

    public int getPartitionOffset() {
        return partitionOffset;
    }

    public int getPartitionIndex() {
        return partitionIndex;
    }

    public void setPartitionLength(int partitionLength) {
        this.partitionLength = partitionLength;
    }

    public void setPartitionOffset(int partitionOffset) {
        this.partitionOffset = partitionOffset;
    }

    public void setPartitionIndex(int partitionIndex) {
        this.partitionIndex = partitionIndex;
    }

    public boolean equals(Object obj) {
        boolean result = true;
        if (!(obj instanceof MQSCCommandEvent))
            return false;
        MQSCCommandEvent other = (MQSCCommandEvent) obj;
        result &= (this.getType() == other.getType());
        if (this.getDocumentPath() != null && other.getDocumentPath() != null)
            result &= (this.getDocumentPath().equalsIgnoreCase(other
                    .getDocumentPath()));
        else
            result = false;
        result &= (this.getLength() == other.getLength());
        result &= (this.getOffset() == other.getOffset());
        result &= (this.getPartitionLength() == other.getPartitionLength());
        result &= (this.getPartitionOffset() == other.getPartitionOffset());
        if (this.getValue() != null && other.getValue() != null)
            result &= (this.getValue().equalsIgnoreCase(other.getValue()));
        else
            result = false;
        return result;
    }

}
