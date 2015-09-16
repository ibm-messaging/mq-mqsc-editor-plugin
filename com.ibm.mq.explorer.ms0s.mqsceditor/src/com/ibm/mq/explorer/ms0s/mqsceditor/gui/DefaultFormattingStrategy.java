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

import org.eclipse.jface.text.formatter.IFormattingStrategy;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This implements an iFormattingStrategy to format documents.
 * This formatter does nothing.  
 **/

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
