/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on May 25, 2007
 */
package com.ibm.mq.explorer.ms0s.mqscscripts.threads;

/**
 * @author Jeff Lowrey
 */
/**
 * <p>
 **/
public interface IMQSCResultAccumulator {
    public void newResultSet(String resultSetName);
    public void addResult(Object result);
    public void addResult(String resultSetName,Object result);
    public void addResult(String resultSetName,Object result, String input);
    public void clearResultSet(String resultSetName);
    public void clearResults();
}
