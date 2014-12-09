/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *

 * Created on Jan 12, 2007
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;



public class MQSCFormattingStrategy extends DefaultFormattingStrategy
{

	private static final String lineSeparator = System.getProperty("line.separator");

	public MQSCFormattingStrategy()
	{
		super();
	}

	public String format(String content, boolean isLineStart, String indentation, int[] positions)
	{
		if (indentation.length() == 0)
			return content;
		return " +" + lineSeparator + content.trim() + lineSeparator + indentation;
	}

}