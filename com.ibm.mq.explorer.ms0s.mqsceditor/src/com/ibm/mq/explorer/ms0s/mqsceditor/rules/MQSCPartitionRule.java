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
package com.ibm.mq.explorer.ms0s.mqsceditor.rules;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This defines the rules necessary to break an MQSC document into partitions that
 * hold individual MQSC comands.  
 * It handles both MQSC line continuations : "+" for "allow space after the continuation, and
 * "-" for don't allow space after the continuation.   So "DEF+   INE" becomes "DEFINE", 
 * but "DEF-   INE" becomes "DEF   INE". 
 **/


public class MQSCPartitionRule implements IPredicateRule {

    protected String fStartSequence;

    protected IToken fReturnToken;

    public IToken evaluate(ICharacterScanner scanner) {
        return evaluate(scanner, false);
    }

    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        //read characters from input for the length of the startSequence
        int j = fStartSequence.length();
        char[] fStartArry = fStartSequence.toCharArray();
        int i = 0;
        int readCount = 0;
        boolean ruleEnd = false;
        boolean lineEnd = false;
        StringBuffer lineBuf = new StringBuffer();
        String lineText;
        char cC;
        int c;
        c = scanner.read();
        readCount++;
        if (c == ICharacterScanner.EOF) {
            scanner.unread();
            return Token.UNDEFINED;
        }
        String cString;//, startString;
        while (i < j && c != ICharacterScanner.EOF) {
            if (c == '+' || c == '-') {
                skipContinuationCharacter(scanner, (char) c);
                c = scanner.read();
            }
            if (c == '*') {
                skipComment(scanner);
                c = scanner.read();
            }
            cString = "" + (char) c;
            if (!(cString.equalsIgnoreCase("" + fStartArry[i]))) {
                unwind(readCount, scanner);
                return Token.UNDEFINED;
            }
            lineBuf.append((char) c);
            c = scanner.read();
            readCount++;
            ++i;
        }
        lineBuf.append((char) c);
        while (!ruleEnd) {
            c = scanner.read();
            cC = (char) c;
            readCount++;
            if (c == ICharacterScanner.EOF) {
                return fReturnToken;
            }
            lineEnd = false;
            if (c == '\r') {
                lineEnd = true;
                int ret = scanner.read();
                if (ret != '\n')
                    scanner.unread();
            } else if (c == '\n') {
                lineEnd = true;
            }
            //TODO: semicolon in the middle of the line?
            if (lineEnd) {
                //if at line end.
                // group into a line.
                lineText = lineBuf.toString();
                if (lineText.endsWith(";")) {
                    return fReturnToken;
                }
                boolean endsWithPlus = lineText.endsWith("+");
                boolean endsWithMinus = lineText.endsWith("-");
                //Need to check if *previous* line ended with + or -.
                if ((!endsWithPlus) && (!endsWithMinus)) {
                    // if line doesn't end with +,-
                    //    check if line is blank, if not then stop
                    //    check if line starts with *, if not stop
                    if (!lineText.startsWith("*")) {
                        if (lineText.trim().length() != 0) {
                            ruleEnd = true;
                        }
                    }
                }
                // reset line buffer
                lineBuf.setLength(0);

            } else {
                lineBuf.append(cC);
            }
        }
        return fReturnToken;
    }

    /**
     *  
     */
    public MQSCPartitionRule(String startSequence, IToken token) {
        super();
        fStartSequence = startSequence;
        fReturnToken = token;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.text.rules.IPredicateRule#getSuccessToken()
     */
    public IToken getSuccessToken() {
        return fReturnToken;
    }

    private void unwind(int count, ICharacterScanner scanner) {
        for (int i = 0; i < count; i++) {
            scanner.unread();
        }
    }

    private void skipComment(ICharacterScanner scanner) {
//        char c;
        char lookAhead;
        lookAhead = (char) scanner.read();
        while ((lookAhead != '\r') && (lookAhead != '\n')
                && lookAhead != ICharacterScanner.EOF
                & ((int) lookAhead != 65535)) {
            lookAhead = (char) scanner.read();
        }
        if (lookAhead == '\r') {
            lookAhead = (char) scanner.read();
            if (lookAhead != '\n')
                scanner.unread();
        }
    }

    private void skipContinuationCharacter(ICharacterScanner scanner,
            char contChar) {
//        char c;
        char lookAhead;
        if (contChar == '-') {
            lookAhead = (char) scanner.read();
            if ((lookAhead != ' ') && (lookAhead != '\r')
                    && (lookAhead != '\n')) {
                scanner.unread();
                return;
            }
            while (lookAhead == ' ') {
                lookAhead = (char) scanner.read();
            }
            while ((lookAhead == '\r') || (lookAhead == '\n')) {
                lookAhead = (char) scanner.read();
                if (lookAhead == '*') {
                    skipComment(scanner);
                    lookAhead = (char) scanner.read();
                }
            }
            scanner.unread();
        } else if (contChar == '+') {
            lookAhead = (char) scanner.read();
            if ((lookAhead != ' ') && (lookAhead != '\r')
                    && (lookAhead != '\n')) {
                scanner.unread();
                return;
            }
            while (lookAhead == ' ') {
                lookAhead = (char) scanner.read();
            }
            while ((lookAhead == '\r') || (lookAhead == '\n')
                    || (lookAhead == ' ')) {
                lookAhead = (char) scanner.read();
                if (lookAhead == '*') {
                    skipComment(scanner);
                    lookAhead = (char) scanner.read();
                }

            }
            scanner.unread();
        }
    }
}
