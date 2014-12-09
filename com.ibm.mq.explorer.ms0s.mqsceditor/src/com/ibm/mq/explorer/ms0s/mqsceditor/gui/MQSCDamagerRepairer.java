/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 * Created on Dec 11, 2006
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;

/**
 *
 */
public class MQSCDamagerRepairer extends DefaultDamagerRepairer {

    public MQSCDamagerRepairer(ITokenScanner scanner) {
        super(scanner);
    }

    public IRegion getDamageRegion(ITypedRegion partition, DocumentEvent e,
            boolean documentPartitioningChanged) {
        return partition;
    }
}
