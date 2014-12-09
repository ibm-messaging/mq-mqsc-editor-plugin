/*
 * SupportPac MS0S
 * (c) Copyright IBM Corp. 2007. All rights reserved.
 *
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.util.MQSCColorProvider;
import com.ibm.mq.explorer.ms0s.mqsceditor.util.MQSCWordDetector;

class MQSCDefaultScanner extends MQSCCodeScanner {
    private int fPartStart, fPartLength,fPartIndex;
    public MQSCDefaultScanner(TextAttribute attribute) {
        setDefaultReturnToken(new Token(attribute));
    }
    public MQSCDefaultScanner(MQSCColorProvider provider) {
        
        super(provider);
        
        final String[] commandNames = {,};
//        final String[] objectNames = {, };
//        final String[] validParameters = {,};
 //       final String[] invalidParameters = {,};
//        final String[] validValues = {,};
//        final String[] invalidValues= {,};
        @SuppressWarnings("rawtypes")
        HashMap defaultMap = new HashMap();
        setMyRules(commandNames,defaultMap);
        if (MQSCEditorPlugin.getDefault().isDebugging()) {
            MQSCEditorPlugin.getDefault().getLog().log( new Status(IStatus.INFO,MQSCEditor.PLUGIN_ID,0, "Created new "+this.getClass(), null)); 
        }
    }
    public MQSCDefaultScanner(MQSCColorProvider provider,String[] commandNames,@SuppressWarnings("rawtypes") Map defaultMap) {
        super(provider);
        setMyRules(commandNames,defaultMap);
    }
    @SuppressWarnings("rawtypes")
    public MQSCDefaultScanner(MQSCColorProvider provider,String[] commandNames,Map defaultMap,ITypedRegion partition,int partitionIndex) {
        this(provider,commandNames,defaultMap);
        fPartStart = partition.getOffset();
        fPartLength = partition.getLength();
        fPartIndex = partitionIndex;
    }    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void setMyRules(String[] commandNames,Map objParamMap) {
        IToken command_name = new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_COMMAND_NAME)));
        IToken obj_name = new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_OBJECT_NAME)));
        IToken invalid_obj = new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_INVALID_OBJECT)));
        IToken valid_parm= new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_VALID_PARAMETER)));
//        IToken valid_val= new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_VALID_VALUE)));
        IToken invalid_parm= new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_INVALID_PARAMETER)));
        IToken invalid_val= new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.MQSC_INVALID_VALUE)));
        IToken default_token = new MQSCToken(new TextAttribute(myProvider.getColor(MQSCColorProvider.DEFAULT)));
        List rules= new ArrayList<MQSCStatefulRule>();
        HashMap mCommands, mObjects,mParameters;
        
        mCommands = new HashMap();
        for (int i= 0; i < commandNames.length; i++)	{
            mCommands.put(commandNames[i].toUpperCase(),command_name);
            mCommands.put(commandNames[i].toLowerCase(),command_name);
        }
        
        mObjects = new HashMap();
        for (Iterator objIter= objParamMap.keySet().iterator(); objIter.hasNext();) { 
            String objectName = (String) objIter.next();
            Object parmTable = objParamMap.get(objectName);
            String[] validParameters = null;
            if (parmTable instanceof HashMap) {
                HashMap mSubTypes = new HashMap();
                for (Iterator subTypeIter = ((HashMap)parmTable).keySet().iterator(); subTypeIter.hasNext();) {
                    String subTypeName = (String)subTypeIter.next();
                    validParameters = ((String[]) ((HashMap)parmTable).get(subTypeName));
                    mParameters = new HashMap();
                    for (int i = 0; i < validParameters.length; i++) {
                        String currParm = validParameters[i];
                        mParameters.put(currParm.toUpperCase(),valid_parm);
                    }
                    mSubTypes.put(subTypeName.toUpperCase(),mParameters);
                }
                mObjects.put(objectName.toUpperCase(),mSubTypes);
            }else {
                validParameters = (String[]) parmTable;
                mParameters = new HashMap();
                for (int i = 0; i < validParameters.length; i++) {
                    String currParm = validParameters[i];
                    mParameters.put(currParm.toUpperCase(),valid_parm);
                }
                mObjects.put(objectName.toUpperCase(),mParameters);
            }
        }
        
        MQSCStatefulRule myRule = new MQSCStatefulRule(new MQSCWordDetector(false),mCommands,mObjects,obj_name,invalid_obj,invalid_parm,default_token,this);
        rules.add(myRule);
        
        IRule[] result= new IRule[rules.size()];
        rules.toArray(result);
        setRules(result);
        setDefaultReturnToken(invalid_val);
        
    }
    public int getOffset() {
        // use this to pass offset information to StatefulRule so that it can
        // build events with correct locations.
        return this.fOffset;
    }
    public int getPartitionStart() {
        // use this to pass offset information to StatefulRule so that it can
        // build events with correct locations.
        return this.fPartStart;
    }
    public int getPartitionLength() {
        // use this to pass offset information to StatefulRule so that it can
        // build events with correct locations.
        return this.fPartLength;
    }
    public int getPartitionNumber()   {
        return this.fPartIndex;
    }
    public ITypedRegion getPartition(int offset) throws BadLocationException 	{
        return fDocument.getPartition(offset);
    }
}


