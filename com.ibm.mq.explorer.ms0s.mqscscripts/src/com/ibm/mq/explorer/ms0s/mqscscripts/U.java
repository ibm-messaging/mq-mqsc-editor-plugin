/*******************************************************************************
 * Copyright (c) 2007,2019 IBM Corporation and other Contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jeff Lowrey - Initial Contribution
 *******************************************************************************/
package com.ibm.mq.explorer.ms0s.mqscscripts;


public class U {
	
	private static boolean debug = System.getProperty("MS0S_DEBUG") != null;
	public static void debug(String s) {
		if (debug)
		System.out.println(s);
	}
}
