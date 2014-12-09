/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 */
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
