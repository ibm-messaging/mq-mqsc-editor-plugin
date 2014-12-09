/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.util;


import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class MQSCWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char character) {
		return Character.isWhitespace(character);
	}
}
