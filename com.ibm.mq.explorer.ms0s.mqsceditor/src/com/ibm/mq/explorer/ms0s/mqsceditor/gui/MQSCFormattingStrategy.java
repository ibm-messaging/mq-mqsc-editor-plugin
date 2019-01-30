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
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;


/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This extends the DefaultFormattingStrategy to format MQSC commands to specific line widths.
 *  This uses the MQSC + continuation character.
 **/
public class MQSCFormattingStrategy extends DefaultFormattingStrategy
{

	private static final String myLineSeparator = System.getProperty("line.separator");

	public MQSCFormattingStrategy()
	{
		super();
	}

	public String format(String content, boolean isLineStart, String indentation, int[] positions)
	{
		if (indentation.length() == 0)
			return content;
		return " +" + myLineSeparator + content.trim() + myLineSeparator + indentation;
	}

}