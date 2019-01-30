/*                                                                   
 *   <copyright                                                      
 *   notice="oco-source"                                             
 *   pids="5724-H72"                                                 
 *   years="1994,2018"                                               
 *   crc="970759065" >                                               
 *   IBM Confidential                                                
 *                                                                   
 *   OCO Source Materials                                            
 *                                                                   
 *   5724-H72                                                        
 *                                                                   
 *   (C) Copyright IBM Corp. 1994, 2018
 *                                                                   
 *   The source code for the program is not published                
 *   or otherwise divested of its trade secrets,                     
 *   irrespective of what has been deposited with the                
 *   U.S. Copyright Office.                                          
 *   </copyright>                                                    
 *                                                                   
 */
package com.ibm.mq.explorer.ms0s.mqsceditor;

/**
 * @author metaylor
 *
 */
public class U {
  private static boolean debug = System.getProperty("MS0S_DEBUG") != null;
  public static void debug(String s) {
    if (debug)
      System.out.println(s);
  }
}
