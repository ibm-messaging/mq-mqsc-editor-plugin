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
package com.ibm.mq.explorer.ms0s.mqsceditor.util;

import org.eclipse.jface.text.rules.IWordDetector;
public class MQSCWordDetector implements IWordDetector {
    private boolean allowWhiteSpace;

    /**
     * @param allowWhiteSpace
     */
    public MQSCWordDetector() {
        super();
    }
    public MQSCWordDetector(boolean allowWhiteSpace) {
        super();
        this.allowWhiteSpace = allowWhiteSpace;
    }

    public boolean isWordPart(char character) {
        boolean result = false;
        result = Character.isLetterOrDigit(character);
        result |= (character == '\'') | (character == '.');
        result |= (character == '-') | (character == '+');
        result |= (character == '\"') | (character == '_') | (character == '-');
        if (allowWhiteSpace) {result |= Character.isWhitespace(character);}
        return result;

    }

    public boolean isWordStart(char character) {
        boolean result = false;
        result = Character.isLetterOrDigit(character);
        result |= (character == '\'');
        result |= (character == '(');
        result |= (character == ')');
        return result;
    }
}
