/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.gui;

import org.eclipse.jface.text.formatter.IFormattingStrategy;


/**
 * 
 * @author Jeff Lowrey
 */
public class DefaultFormattingStrategy implements IFormattingStrategy
{
	protected static final String lineSeparator = System.getProperty("line.separator");

	public DefaultFormattingStrategy()
	{
		super();
	}

	public void formatterStarts(String initialIndentation)
	{
	}

	public String format(String content, boolean isLineStart, String indentation, int[] positions)
	{
		return "";
	}

	public void formatterStops()
	{
	}

}
