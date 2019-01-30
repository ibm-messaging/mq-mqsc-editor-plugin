package com.ibm.mq.explorer.ms0s.mqscscripts;


public class U {
	
	private static boolean debug = System.getProperty("MS0S_DEBUG") != null;
	public static void debug(String s) {
		if (debug)
		System.out.println(s);
	}
}
