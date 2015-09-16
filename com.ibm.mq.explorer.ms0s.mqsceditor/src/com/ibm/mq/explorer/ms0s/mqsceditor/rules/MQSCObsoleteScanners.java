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
package com.ibm.mq.explorer.ms0s.mqsceditor.rules;


/**
 * @author Jeff Lowrey
 *<p>
 * This file holds code to build partition specific scanners using inline data elements.
 * All of these became obsolete once MQSCCodeScanner and MQSCDefaultScanner were converted
 * to use MQSCLanguageConfiguarator.
 * 
 * Obsolete code never gets deleted, it just gets sidelined.
 * 
 * THIS CODE IS NOT USED ANYWHERE.  IT'S MAINTAINED FOR HISTORICAL PORPOISES.
 * IT ALMOST CERTAINLY HAS COMPILE ERRORS.
 * 
 */


public class MQSCObsoleteScanners {
/*    protected final String[] authInfoParameters = {
            "authtype","cmdscope","conname","descr","ldappwd","ldapuser","like","qsgdisp","replace",
            "noreplace","where",
    };
    protected final String[] buffPoolParameters = {"buffers", };
    protected final String[] cfstructParameters= {"descr","replace","noreplace","cflevel","recover","like",  };
    protected final String[] channelParameters = {
            "autostart","batchhb","batchint","batchsz",
            "chltype","clusnl","cluster","clwlprty","clwlrank","clwlwght","comphdr","compmsg","conname",
            "convert","descr","discint","hbint","kaint","like","locladdr","longrty","longtmr","maxmsgl",
            "mcaname","mcatype","mcauser","modename","monchl","mrdata","mrexit","mrrty","mrtmr","msgdata",
            "msgexit","netprty","npmspeed","password","putaut","qmname","replace","noreplace","rcvdata",
            "rcvexit","scydata","scyexit","senddata","sendexit","seqwrap","shortrty","shorttmr","sslcauth",
            "sslciph","sslpeer","statchl","tpname","trptype","userid","xmitq",
    };
    protected final String[] listenerParameters = {
            "adapter","backlog","commands","control","descr","ipaddr","like","loclname","ntbnames","port",
            "replace","noreplace","sessions","socket","tpname","trptype",};
    protected final String[] logParameters = {"copy","cmdscope",};
    protected final String[] maxsmsgsParameters = {"cmdscope",};
    protected final String[] namelistParameters = {"cmdscope","descr","like","qsgdisp","replace","noreplace","names",
            "nltype",};
    protected final String[] processParameters = {"applicid","appltype","cmdscope","descr","envrdata",
            "userdata","qsgdisp","like","replace","noreplace"   };
    protected final String[] psidParameters = {"buffpool","dsn","expand",};
    protected final String[] qmgrParameters = {"force",
            "acctcono","acctint","acctmqi","acctq","actchl","activrec","adoptchk","adoptmca","authorev",
            "bridgeev","ccsid","chad","chadev","chadexit","chiadaps","chidisps","chiservp","chlev","clwldata",
            "clwlexit","clwllen","clwlmruc","clwluseq","cmdev","cmdscope","configev","deadq","defxmitq","descr",
            "dnsgroup","dnswlm","expryint","igq","igqaut","igquser","inhibtev","ipaddrv","localev","loggerev",
            "lstrtmr","lugroup","luname","lu62arm","lu62chl","maxchl","maxhands","maxmsgl","maxumsgs","monacls",
            "monchl","monq","oportmax","oportmin","perfmev","rcvtime","rcvtmin","rcvttype","remoteev","repos",
            "reposnl","routerec","schinit","scmdserv","sqqmname","sslcrlnl","sslcryp","sslev","sslfips",
            "sslkeyr","sslrkeyc","ssltasks","statacls","statchl","statint","statmqi","statq","strstpev",
            "tcpchl","tcpkeep","tcpname","tcpstack","traxstr","traxtbl","trigint",};
    protected final String[] qaliasParameters = {
            "clusnl","cluster","clwlprty","clwlrank","cmdscope","defbind","defprty","defpsist",
            "descr","force","get","like","noreplace","put","qsgdisp","replace","scope",
            "targq","trigdata","trigdpth","trigger",
            };
    protected final String[] qlocalParameters = {
            "acctq","boqname","bothresh","cfstruct","clusnl","cluster",
            "clwlprty","clwlrank","clwluseq","cmdscope","defbind","defprty","defpsist","defsopt","deftype",
            "descr","distl","force","get","hardenbo","nohardenbo","indxtype","initq","like","maxdepth",
            "maxmsgl","monq","msgdlvsq","noreplace","noshare","notrigger","npmclass","process","put","qdepthhi",
            "qdepthlo","qdphiev","qdploev","qdpmaxev","qsgdisp","qsvciev","qsvcint","replace","retintvl",
            "scope","share","statq","stgclass","trigdata","trigdpth","trigger",
            "trigmpri","trigtype","usage",
    		};
    protected final String[] qmodelParameters = {
            "acctq","boqname","bothresh","cfstruct","cmdscope","defprty","defpsist",
            "defsopt","deftype","descr","distl","get","hardenbo","nohardenbo","indxtype","initq","like",
            "maxdepth","maxmsgl","monq","msgdlvsq","noreplace","noshare","notrigger","npmclass",
            "process","put","qdepthhi","qdepthlo","qdphiev","qdploev","qdpmaxev","qsgdisp",
            "qsvciev","qsvcint","replace","retintvl","scope","share","statq","stgclass","trigdata",
            "trigdpth","trigger","trigmpri","trigtype","usage",
    		};
    protected final String[] qremoteParameters = {
            "clusnl","cluster","clwlprty","clwlrank","cmdscope","defbind","defprty","defpsist","defsopt",
            "deftype","descr","force","like","noreplace","process","put","qsgdisp","replace","rname",
            "rqmname","scope","xmitq",
    		};
    protected final String[] securityParameters = {
            "cmdscope","interval","timeout"};
    protected final String[] serviceParameters = {
            "control","descr","like","noreplace","replace","servtype","startarg","startcmd","stderr",
            "stdout","stoparg","stopcmd",
            };
    protected final String[] stgclassParameters = {
            "cmdscope","descr","like","noreplace","passtkta","psid","qsgdisp",
            "replace","xcfgname","xcfmname",};
    protected final String[] traceParameters = {
            "acctg","global","stat","tno","cmdscope","class","comment","ifcid",};
    public MQSCObsoleteScanners() {
        super();
    }
    

    /*
     * Command specific subtype scanners
     


    class MQSCAlterScanner	extends MQSCDefaultScanner  {
        
        private final String[] commandNames = {"alter","alt",};
        private final String[] objectNames = {"authinfo",
                "buffpool", "bp", "cfstruct", "channel", "chl",
                "listener","lstr", "namelist", "nl", "process",
                "pro", "psid", "qmgr", "qalias", "qa", "qlocal", "ql", "qmodel", "qm", "qremote", "qr",
                "security", "sec", "service", "stgclass", "stc", "trace", };
        
        public MQSCAlterScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap alterMap = new HashMap();
            alterMap.put("authinfo",authInfoParameters);
            alterMap.put("buffpool",buffPoolParameters);
            alterMap.put("bp",buffPoolParameters);
            alterMap.put("cfstruct",cfstructParameters);
            alterMap.put("channel",channelParameters);
            alterMap.put("chl",channelParameters);
            alterMap.put("listener",listenerParameters);
            alterMap.put("lstr",listenerParameters);
            alterMap.put("namelist",namelistParameters);
            alterMap.put("nl",namelistParameters);
            alterMap.put("process",processParameters);
            alterMap.put("psid",psidParameters);
            alterMap.put("qmgr",qmgrParameters);
            alterMap.put("qalias",qaliasParameters);
            alterMap.put("qa",qaliasParameters);
            alterMap.put("qlocal",qlocalParameters);
            alterMap.put("ql",qlocalParameters);
            alterMap.put("qmodel",qmodelParameters);
            alterMap.put("qm",qmodelParameters);
            alterMap.put("qremote",qremoteParameters);
            alterMap.put("qr",qremoteParameters);
            alterMap.put("security",securityParameters);
            alterMap.put("sec",securityParameters);
            alterMap.put("service",serviceParameters);
            alterMap.put("stgclass",stgclassParameters);
            alterMap.put("stc",stgclassParameters);
            alterMap.put("trace",traceParameters);
            setMyRules(commandNames,(Map)alterMap);
        }
    }

    class MQSCArchiveScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"archive","arc",};
        private final String[] objectNames = {"log", };
        private final String[] validParameters = {"cmdscope","mode","cancel", "offload","time","wait",};
        
        public MQSCArchiveScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap archiveMap = new HashMap();
            archiveMap.put("log",validParameters);
            setMyRules(commandNames,archiveMap);
        }
    }

    class MQSCBackupScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"backup"};
        private final String[] objectNames = {"cfstruct", };
        private final String[] validParameters = {"cmdscope","exclint",};

        public MQSCBackupScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap backupMap = new HashMap();
            backupMap.put("cfstruct",validParameters);
            setMyRules(commandNames,backupMap);
        }
    }

    class MQSCClearScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"clear",};
        private final String[] objectNames = {"qlocal","ql" };
        private final String[] validParameters = {"cmdscope","qsgdisp",};   
        
        public MQSCClearScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap clearMap = new HashMap();
            clearMap.put("ql",validParameters);
            clearMap.put("qlocal",validParameters);
            setMyRules(commandNames,clearMap);
        }
    }

    class MQSCDefineScanner	extends MQSCDefaultScanner  {
        
        private final String[] commandNames = {"define","def",};
        private final String[] objectNames = {"authinfo","buffpool", "bp", 
                "cfstruct", "channel", "chl",
                "listener", "lstr","log","maxsmsgs","namelist", "nl", "process",
                "pro", "psid", "qmgr", "qalias", "qa", "qlocal", "ql", "qmodel", "qm",
                "qremote", "qr", "security", "sec", "service", "stgclass", "stc", };
        
        public MQSCDefineScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap defineMap = new HashMap();
            defineMap.put("authinfo",authInfoParameters);
            defineMap.put("buffpool",buffPoolParameters);
            defineMap.put("bp",buffPoolParameters);
            defineMap.put("cfstruct",cfstructParameters);
            defineMap.put("channel",channelParameters);
            defineMap.put("chl",channelParameters);
            defineMap.put("listener",listenerParameters);
            defineMap.put("lstr",listenerParameters);
            defineMap.put("log",logParameters);
            defineMap.put("maxsmsgs",maxsmsgsParameters);
            defineMap.put("namelist",namelistParameters);
            defineMap.put("nl",namelistParameters);
            defineMap.put("process",processParameters);
            defineMap.put("pro",processParameters);
            defineMap.put("psid",psidParameters);
            defineMap.put("qmgr",qmgrParameters);
            defineMap.put("qalias",qaliasParameters);
            defineMap.put("qa",qaliasParameters);
            defineMap.put("qlocal",qlocalParameters);
            defineMap.put("ql",qlocalParameters);
            defineMap.put("qmodel",qmodelParameters);
            defineMap.put("qm",qmodelParameters);
            defineMap.put("qremote",qremoteParameters);
            defineMap.put("qr",qremoteParameters);
            defineMap.put("security",securityParameters);
            defineMap.put("sec",securityParameters);
            defineMap.put("service",serviceParameters);
            defineMap.put("stgclass",stgclassParameters);
            defineMap.put("stc",stgclassParameters);
            setMyRules(commandNames,(Map)defineMap);
        }
    }

    class MQSCDeleteScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"delete",};
        private final String[] objectNames = {"authinfo","buffpool", "bp", 
                "cfstruct", "channel", "chl",
                "listener", "lstr","namelist", "nl", "process",
                "pro", "psid", "qalias", "qa", "qlocal", "ql", "qmodel", "qm",
                "qremote", "qr","service", "stgclass", "stc", };
        private final String[] deleteAuthInfoParameters = {
                "cmdscope","qsgdisp"
        };
        private final String[] deleteBuffPoolParameters = {, };
        private final String[] deleteCfstructParameters= {,};
        private final String[] deleteChannelParameters = {
                "chltable","cmdscope","qsgdisp",
        };
        private final String[] deleteListenerParameters = {,};
        private final String[] deleteNamelistParameters = {"cmdscope","qsgdisp",};
        private final String[] deleteProcessParameters = {"cmdscope","qsgdisp",};
        private final String[] deletePsidParameters = {,};
        private final String[] deleteQAliasParameters = {"cmdscope","qsgdisp",};
        private final String[] deleteQLocalParameters = {"cmdscope","qsgdisp","purge","nopurge",};
        private final String[] deleteQModelParameters = {"cmdscope","qsgdisp",};
        private final String[] deleteQRemoteParameters = {"cmdscope","qsgdisp",};
        private final String[] deleteServiceParameters = {,};
        private final String[] deleteStgclassParameters = {"cmdscope","qsgdisp",};
        
        public MQSCDeleteScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap deleteMap = new HashMap();
            deleteMap.put("authinfo",deleteAuthInfoParameters);
            deleteMap.put("buffpool",deleteBuffPoolParameters);
            deleteMap.put("bp",deleteBuffPoolParameters);
            deleteMap.put("cfstruct",deleteCfstructParameters);
            deleteMap.put("channel",deleteChannelParameters);
            deleteMap.put("chl",deleteChannelParameters);
            deleteMap.put("listener",deleteListenerParameters);
            deleteMap.put("lstr",deleteListenerParameters);
            deleteMap.put("namelist",deleteNamelistParameters);
            deleteMap.put("nl",deleteNamelistParameters);
            deleteMap.put("process",deleteProcessParameters);
            deleteMap.put("pro",deleteProcessParameters);
            deleteMap.put("psid",deletePsidParameters);
            deleteMap.put("qalias",deleteQAliasParameters);
            deleteMap.put("qa",deleteQAliasParameters);
            deleteMap.put("qlocal",deleteQLocalParameters);
            deleteMap.put("ql",deleteQLocalParameters);
            deleteMap.put("qmodel",deleteQModelParameters);
            deleteMap.put("qm",deleteQModelParameters);
            deleteMap.put("qremote",deleteQRemoteParameters);
            deleteMap.put("qr",deleteQRemoteParameters);
            deleteMap.put("service",deleteServiceParameters);
            deleteMap.put("stgclass",deleteStgclassParameters);
            deleteMap.put("stc",deleteStgclassParameters);
            setMyRules(commandNames,(Map)deleteMap);
        }
    }

    class MQSCDisplayScanner	extends MQSCDefaultScanner  {
        
        private final String[] disAuthInfoParameters = {"altdate","alttime","authtype","cmdscope","conname","descr",
                "ldappwd","ldapuser","qsgdisp","where","all",};
        private final String[] disArchiveParameters = {"cmdscope",};    
        private final String[] disChinitParameters = {"cmdscope",};    
        private final String[] disCfstatusParameters = {"where","type",};
        
        private final String[] disCfstructParameters = {"where","all","altdate","alttime","cflevel","descr",
                "recover"};    
        private final String[] disChannelParameters = {"cmdscope","qsgdisp","type","altdate","alttime","autostart","batchhb","batchint","batchsz",
                "chltype","clusnl","cluster","clwlprty","clwlrank","clwlwght","comphdr","compmsg","conname",
                "convert","descr","discint","hbint","kaint","like","localaddr","longrty","longtmr","maxmsgl",
                "mcaname","mcatype","mcauser","modename","monchl","mrdata","mrexit","mrrty","mrtmr","msgdata",
                "msgexit","netprty","npmspeed","password","putaut","qmname","replace","noreplace","rcvdata",
                "rcvexit","scydata","scyexit","senddata","sendexit","seqwrap","shortrty","shorttmr","sslcauth",
                "sslciph","sslpeer","statchl","tpname","trptype","userid","xmitq","where","all",};
        private final String[] disChstatusParameters = {"cmdscope","where","all","common","short","saved",
                "chldisp","conname","xmitq","monitor","chltype","curluwid","curmsgs","curseqno","indoubt",
                "lstluwid","lstseqno","status","batches","batchsz","bufsrcvd","bufssent","bytsrcvd",
                "bytssent","chstada","chstati","comphdr","compmsg","comprate","comptime","exittime",
                "hbint","jobname","kaint","localaddr","longrts","lstmsgda","lstmsgti","maxmsgl","mcastat",
                "mcauser","monchl","msgs","nettime","npmspeed","rappltag","rqmname","shortrts","sslcerti",
                "sslcertu","sslkeyda","sllkeyti","sslpeer","sslrkeys","stopreq","substate","xbatchsz",
                "xqmsgsa","xqtime","qmname",};    
        private final String[] disClusqmgrParameters = {"where","all","channel","cluster","cmdscope",
                "clusdate","clustime","deftype","qmid","qmtype","status","suspend","altdate","alttime",
                "batchhb","batchint","batchsz","clwlprty","clwlrank","clwlwght","comphdr","compmsg","conname",
                "convert","descr","discint","hbint","kaint","localaddr","longrty","longtmr","maxmsgl","mcaname",
                "mcatype","mcauser","modename","monchl","mrdata","mrexit","mrrty","mrtmr","msgdata","msgexit",
                "netprty","npmspeed","password","putaut","rcvdata","rcvexit","scydata","scyexit","senddata"
                ,"sendexit","seqwrap","shortrty","shorttmr","sslcauth","sslciph","sslpeer","statchl",
                "tpname","trptype","userid",};    
        private final String[] disCmdservParameters = {,};    
        private final String[] disConnParameters = {"where","all","extconn","cmdscope","type","appltag","appltype",
                "asid","channel","conname","connopts","exturid","nid","pid","psbname","pstid","qmurid","taskno",
                "tid","transid","uowlog","uowlogda","uowlogti","uowstate","uowstda","uowstti","urtype","userid",
                "hstate","objname","objtype","openopts","qsgdisp",};
        private final String[] disGroupParameters = {,};
        private final String[] disListenerParameters = {"adapter","backlog","commands","control","descr","ipaddr",
                "loclname","ntbnames","port","sessions","socket","tpname","trptype","where","all","altdate",
                "alttime",};
        private final String[] disLogParameters = {"cmdscope",};
        private final String[] disLsstatusParameters = {"adapter","backlog","commands","control","descr","ipaddr",
                "loclname","ntbnames","pid","port","sessions","socket","tpname","trptype","where","all","status",
                "startda","startti",};
        private final String[] disMaxSmsgsParameters = {"cmdscope",};
        private final String[] disNamelistParameters = {"where","all","cmdscope","qsgdisp","names",
                "nltype","altdate","alttime","descr","namcount",};
        private final String[] disProcessParameters = {"where","all","cmdscope","qsgdisp",
                "altdate","alttime","applicid","appltype","descr","envrdata",
                "userdata",};
        private final String[] disQmgrParameters = {"where","all","altdate","alttime",
                "acctcono","acctint","acctmqi","acctq","actchl","activerec","adoptchk","adoptmca","authorev",
                "bridgeev","ccsid","chad","chadev","chadexit","chiadaps","chidisps","chiservp","chlev","clwldata",
                "clwlexit","clwllen","clwlmruc","clwluseq","cmdev","cmdscope","configev","deadq","defxmitq","desc",
                "dnsgroup","dnswlm","expryint","igq","igqaut","igquser","inhibtev","ipaddrv","localev","loggerev",
                "lstrtmr","lugroup","luname","lu62arm","lu62chl","maxchl","maxhands","maxmsgl","maxumsgs","monacls",
                "monchl","monq","oportmax","oportmin","perfmev","rcvtime","rcvtmin","rcvttype","remoteev","repos",
                "reposnl","routerec","schinit","scmdserv","sqqmname","sslcrlnl","sslcryp","sslev","sslfips",
                "sslkeyr","sslrkeyc","ssltasks","statacls","statchl","statint","statmqi","statq","strstpev",
                "tcpchl","tcpkeep","tcpname","tcpstack","traxstr","traxtbl","trigint",
                "system","event","chinit","cluster",};
        private final String[] disQmgrStatusParameters = {"all","chinit","cmdserv","cons","currlog",
                "medialog","reclog"};
        private final String[] disQueueStatusParameters = {"where","all","cmdscope","type","monitor",
                "curdepth","ipprocs","lgetdate","lgettime","lputdate","lputtime","medialog","monq","msgage",
                "opprocs","qsgdisp","qtime","uncom","appltag","appltype","asid","browse","channel","conname",
                "hstate","input","inquire","output","pid","psbname","pstid","qmurid","qsgdisp","set","taskno",
                "tid","transid","urid","urtype","userid",};
        private final String[] disQueueParameters = {"where","all","cmdscope","qsgdisp",
                "targq","altdate","alttime",
                "acctq","boqname","bothresh","cfstruct","clusnl","cluster",
                "clwlprty","clwlrank","clwluseq","defbind","defprty","defpsist","defsopt","deftype",
                "descr","distl","get","hardenbo","indxtype","initq","maxdepth",
                "maxmsgl","monq","msgdlvsq","npmclass","process","put","qdepthhi",
                "qdepthlo","qdphiev","qpdloev","qdpmaxev","qsvciev","qsvcint","replace","retintvl",
                "scope","share","statq","stgclass","trigdata","trigdpth","trigger",
                "trigmpri","trigtype","usage",
                "rname","rqmname","xmitq","clusdate","clusqmgr","clusqt","clusttime","qmid","qtype",
        		};
        private final String[] disQaliasParameters = {"where","all","cmdscope","qsgdisp",
                "altdate","alttime","clusnl","cluster","clwlprty","clwlrank","defbind","defprty","defpsist",
                "descr","get","put","scope",
                "targq","trigdata","trigdpth","trigger","qtype"
                };
        private final String[] disQclusterParameters = {"where","all","cmdscope","qsgdisp",
                "altdate","alttime","cluster","clwlprty","clwlrank","defbind","defprty","defpsist",
                "descr","put",
                "clusdate","clusqmgr","clusqt","clusttime","qmid","qtype"};
        private final String[] disQlocalParameters = {"where","all","cmdscope","qsgdisp",
                "acctq","boqname","bothresh","cfstruct","clusnl","cluster",
                "clwlprty","clwlrank","clwluseq","defbind","defprty","defpsist","defsopt","deftype",
                "descr","distl","get","hardenbo","indxtype","initq","maxdepth",
                "maxmsgl","monq","msgdlvsq","npmclass","process","put","qdepthhi",
                "qdepthlo","qdphiev","qpdloev","qdpmaxev","qsvciev","qsvcint","replace","retintvl",
                "scope","share","statq","stgclass","trigdata","trigdpth","trigger",
                "trigmpri","trigtype","usage",
                "altdate","alttime","qtype",};
        private final String[] disQmodelParameters = {"where","all","cmdscope","qsgdisp",
                "acctq","boqname","bothresh","cfstruct","defprty","defpsist",
                "defsopt","deftype","descr","distl","get","hardenbo","indxtype","initq",
                "maxdepth","maxmsgl","monq","msgdlvsq","npmclass",
                "process","put","qdepthhi","qdepthlo","qdphiev","qpdloev","qdpmaxev",
                "qsvciev","qsvcint","replace","retintvl","scope","share","statq","stgclass","trigdata",
                "trigdpth","trigger","trigmpri","trigtype","usage",
                "altdate","alttime","qtype",};
        private final String[] disQremoteParameters = {"where","all","cmdscope","qsgdisp",
                "clusnl","cluster","clwlprty","clwlrank","defbind","defprty","defpsist","defsopt",
                "deftype","descr","process","put","rname","rqmname","scope","xmitq",
                "altdate","alttime","qtype",};
        private final String[] disSecurityParameters = {
                "cmdscope","interval","timeout","all","switches"};
        private final String[] disServiceParameters = {
                "control","descr","servtype","startarg","startcmd","stderr",
                "stdout","stoparg","stopcmd","altdate","alttime","where","all",
                };
        private final String[] disStgclassParameters = {"where","all","altdate","alttime",
                "cmdscope","descr","passtkta","psid","qsgdisp","xcfgname","xcfmname",};
        private final String[] disSvcStatusParameters = {
                "where","all","control","descr","pid","servtype","startarg","startcmd","startda","startti",
                "status","stderr","stdout","stoparg","stopcmd",};
        private final String[] disSystemParameters = {"cmdscope",};
        private final String[] disThreadParameters = {"cmdscope","type","qmname",};
        private final String[] disTraceParameters = {"cmdscope","acctg","chinit","global","stat",
                "comment","detail","dest","class","rmid","tno","userid",};
        private final String[] disUsageParameters = {"cmdscope","psid","type",};
        
        private final String[] commandNames = {"display","dis",};
        private final String[] objectNames = {"archive","authinfo","cfstatus","cfstruct", "channel", "chl",
                "chinit","chi","dqm","chstatus","clusqmgr","cmdserv","cs","conn","group",
                "listener", "lstr","log","lsstatus","maxmsgs","maxsm","namelist", "nl", "process",
                "pro", "psid", "qmgr", "qmstatus","queue","q","qstats","qs","qalias", "qa", "qlocal", "ql", "qmodel", "qm",
                "qremote", "qr","security","sec","service", "stgclass", "stc", "svstatus","system","thread","thd",
                "trace","usage",};

        public MQSCDisplayScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap displayMap = new HashMap();
            displayMap.put("authinfo",disAuthInfoParameters);
            displayMap.put("archive",disArchiveParameters);
            displayMap.put("cfstatus",disCfstatusParameters);
            displayMap.put("cfstruct",disCfstructParameters);
            displayMap.put("channel",disChannelParameters);
            displayMap.put("chl",disChannelParameters);
            displayMap.put("chinit",disChinitParameters);
            displayMap.put("chi",disChinitParameters);
            displayMap.put("dqm",disChinitParameters);
            displayMap.put("chstatus",disChstatusParameters);   
            displayMap.put("clusqmgr",disClusqmgrParameters);
            displayMap.put("cmdserv",disCmdservParameters);
            displayMap.put("cs",disCmdservParameters);
            displayMap.put("conn",disConnParameters);
            displayMap.put("group",disGroupParameters);
            displayMap.put("listener",disListenerParameters);
            displayMap.put("lstr",disListenerParameters);        
            displayMap.put("log",disLogParameters);
            displayMap.put("lsstatus",disLsstatusParameters);
            displayMap.put("maxsmsgs",disMaxSmsgsParameters);
            displayMap.put("maxsm",disMaxSmsgsParameters);        
            displayMap.put("namelist",disNamelistParameters);
            displayMap.put("nl",disNamelistParameters);
            displayMap.put("process",disProcessParameters);
            displayMap.put("pro",disProcessParameters);
            displayMap.put("qmgr",disQmgrParameters);        
            displayMap.put("qmstatus",disQmgrStatusParameters);
            displayMap.put("qstatus",disQueueStatusParameters);
            displayMap.put("qs",disQueueStatusParameters);
            displayMap.put("queue",disQueueParameters);
            displayMap.put("q",disQueueParameters);        
            displayMap.put("qalias",disQaliasParameters);
            displayMap.put("qa",disQaliasParameters);
            displayMap.put("qcluster",disQclusterParameters);
            displayMap.put("qc",disQclusterParameters);
            displayMap.put("qlocal",disQlocalParameters);
            displayMap.put("ql",disQlocalParameters);
            displayMap.put("qmodel",disQmodelParameters);
            displayMap.put("qm",disQmodelParameters);
            displayMap.put("qremote",disQremoteParameters);
            displayMap.put("qr",disQremoteParameters);
            displayMap.put("security",disSecurityParameters);
            displayMap.put("sec",disSecurityParameters);
            displayMap.put("service",disServiceParameters);
            displayMap.put("stgclass",disStgclassParameters);
            displayMap.put("stc",disStgclassParameters);
            displayMap.put("svstatus",disSvcStatusParameters);
            displayMap.put("system",disSystemParameters);
            displayMap.put("thread",disThreadParameters);
            displayMap.put("thd",disThreadParameters);
            displayMap.put("trace",disTraceParameters);
            displayMap.put("usage",disUsageParameters);
            setMyRules(commandNames,(Map)displayMap,0);
        }
    }

    class MQSCMoveScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"move",};
        private final String[] objectNames = {"qlocal","ql" };
        private final String[] validParameters = {"cmdscope","qsgdisp","type","toqlocal",};   
        
        public MQSCMoveScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap moveMap = new HashMap();
            moveMap.put("ql",validParameters);
            moveMap.put("qlocal",validParameters);
            setMyRules(commandNames,moveMap,0);
        }
    }

    class MQSCPingScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"ping",};
        private final String[] objectNames = {"channel","chl","qmgr" };
        private final String[] pingChannelParameters = {"cmdscope","chldisp","datalen",};   
        private final String[] pingQmgrParameters = {,};   
        
        public MQSCPingScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap pingMap = new HashMap();
            pingMap.put("chl",pingChannelParameters);
            pingMap.put("channel",pingChannelParameters);
            pingMap.put("qmgr",pingQmgrParameters);
            setMyRules(commandNames,pingMap,0);
        }
    }

    class MQSCRecoverScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"reocver","rec"};
        private final String[] objectNames = {"bsds","cfstruct",};
        private final String[] recCFStructParameters = {"cmdscope","type",};   
        private final String[] recBsdsParameters = {"cmdscope",};   
        
        public MQSCRecoverScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap recoverMap = new HashMap();
            recoverMap.put("cfstruct",recCFStructParameters);
            recoverMap.put("bsds",recBsdsParameters);
            setMyRules(commandNames,recoverMap,0);
        }
    }

    class MQSCRefreshScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"refresh","ref"};
        private final String[] objectNames = {"cluster","qmgr","security","sec",};
        private final String[] refClusterParameters = {"cmdscope","repos",};   
        private final String[] refQmgrParameters = {"cmdscope","type","inclint","name","object"};   
        private final String[] refSecurityParameters = {"cmdscope","type"};   
        
        public MQSCRefreshScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap refreshMap = new HashMap();
            refreshMap.put("cluster",refClusterParameters);
            refreshMap.put("qmgr",refQmgrParameters);
            refreshMap.put("security",refSecurityParameters);
            refreshMap.put("sec",refSecurityParameters);
            setMyRules(commandNames,refreshMap,0);
        }
    }

    class MQSCResetScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"reset"};
        private final String[] objectNames = {"channel","chl","cluster","qmgr","qstats","tpipe",};
        private final String[] resetChannelParameters = {"cmdscope","chldisp","seqnum",};   
        private final String[] resetClusterParameters = {"cmdscope","action","qmname","qmid","queues",};   
        private final String[] resetQmgrParameters = {"type",};   
        private final String[] resetQstatsParameters = {"cmdscope",};   
        private final String[] resetTpipeParameters = {"cmdscope","action","sendseq","xcfgname","rcvseq",
                "xcfmname",};
        
        public MQSCResetScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap resetMap = new HashMap();
            resetMap.put("channel",resetChannelParameters);
            resetMap.put("chl",resetChannelParameters);
            resetMap.put("cluster",resetClusterParameters);
            resetMap.put("qmgr",resetQmgrParameters);
            resetMap.put("qstats",resetQstatsParameters);
            resetMap.put("tpipe",resetTpipeParameters);
            setMyRules(commandNames,resetMap,0);
        }
    }

    class MQSCResolveScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"resolve","res",};
        private final String[] objectNames = {"channel","chl","indoubt",};
        private final String[] resolveChannelParameters = {"cmdscope","chldisp","action",};   
        private final String[] resolveIndoubtParameters = {"cmdscope","action","nid","qmname",};
        
        public MQSCResolveScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap resolveMap = new HashMap();
            resolveMap.put("channel",resolveChannelParameters);
            resolveMap.put("chl",resolveChannelParameters);
            resolveMap.put("indoubt",resolveIndoubtParameters);
            setMyRules(commandNames,resolveMap,0);
        }
    }

    class MQSCResumeScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"resume",};
        private final String[] objectNames = {"qmgr",};
        private final String[] resumeQmgrParameters = {"cmdscope","cluster","clusnl","facility","log"};   
        
        public MQSCResumeScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap resumeMap = new HashMap();
            resumeMap.put("qmgr",resumeQmgrParameters);
            setMyRules(commandNames,resumeMap,0);
        }
    }

    class MQSCRverifyScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"rverify","rev",};
        private final String[] objectNames = {"security","sec"};
        private final String[] revSecurityParameters = {"cmdscope"};   
        
        public MQSCRverifyScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap rverifyMap = new HashMap();
            rverifyMap.put("qmgr",revSecurityParameters);
            setMyRules(commandNames,rverifyMap,0);
        }
    }

    class MQSCSetScanner extends MQSCDefaultScanner  {
        private final String[] commandNames = {"set",};
        private final String[] objectNames = {"archive","arc","log","system",};
        private final String[] setArchiveParameters = {"cmdscope","default","alcunit","arcpfx1","arcpfx2",
               "arcretn","arcwrtc","arcwtor","blksize","catalog","compact","priqty","protect","quiesce","secqty",
                "tstamp","unit","unit2",};   
        private final String[] setLogParameters = {"cmdscope","default","deallct","maxarch","maxrtu","wrthrsh",};   
        private final String[] setSystemParameters = {"cmdscope","default","cthread","idback","idfore","logload",
                "service","statime","tractbl",};   
        
        public MQSCSetScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap setMap = new HashMap();
            setMap.put("archive",setArchiveParameters);
            setMap.put("arc",setArchiveParameters);
            setMap.put("log",setLogParameters);
            setMap.put("system",setSystemParameters);
            setMyRules(commandNames,setMap,0);
        }
    }


    class MQSCStartScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"start","sta",};
        private final String[] objectNames = {"channel","chl","chinit","cmdserv","cs","listener","lstr","qmgr"
                ,"service","trace",};
        private final String[] startChannelParameters = {"cmdscope","chldisp",};   
        private final String[] startChinitParameters = {"initq","cmdscope","envparm",};   
        private final String[] startCmdServParameters = {,};   
        private final String[] startListenerParameters = {"cmdscope","indisp","ipaddr","luname","port","trptype",};   
        private final String[] startQmgrParameters = {"envparm","parm",};   
        private final String[] startServiceParameters = {,};   
        private final String[] startTraceParameters = {"cmdscope","comment","dest","class","rmid","ifcid","tdata"
                ,"userid"};   
        
        public MQSCStartScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap startMap = new HashMap();
            startMap.put("channel",startChannelParameters);
            startMap.put("chl",startChannelParameters);
            startMap.put("chinit",startChinitParameters);
            startMap.put("cmdserv",startCmdServParameters);
            startMap.put("cs",startCmdServParameters);
            startMap.put("listener",startListenerParameters);
            startMap.put("lstr",startListenerParameters);
            startMap.put("qmgr",startQmgrParameters);
            startMap.put("service",startServiceParameters);
            startMap.put("trace",startTraceParameters);
            setMyRules(commandNames,startMap,0);
        }
    }

    class MQSCStopScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"stop",};
        private final String[] objectNames = {"channel","chl","chinit","cmdserv","cs","conn","listener","lstr",
                "qmgr","service","trace",};
        private final String[] stopChannelParameters = {"cmdscope","chldisp","conname","qmname","status","mode",};   
        private final String[] stopChinitParameters = {"cmdscope","shared",};   
        private final String[] stopCmdServParameters = {,};   
        private final String[] stopConnParameters = {"extconn",};   
        private final String[] stopListenerParameters = {"cmdscope","indisp","ipaddr","port","trptype",};   
        private final String[] stopQmgrParameters = {"cmdscope","mode"};   
        private final String[] stopServiceParameters = {,};   
        private final String[] stopTraceParameters = {"cmdscope","comment","dest","class","rmid","tno","userid"};   

        public MQSCStopScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap stopMap = new HashMap();
            stopMap.put("channel",stopChannelParameters);
            stopMap.put("chl",stopChannelParameters);
            stopMap.put("chinit",stopChinitParameters);
            stopMap.put("cmdserv",stopCmdServParameters);
            stopMap.put("cs",stopCmdServParameters);
            stopMap.put("conn",stopConnParameters);
            stopMap.put("listener",stopListenerParameters);
            stopMap.put("lstr",stopListenerParameters);
            stopMap.put("qmgr",stopQmgrParameters);
            stopMap.put("service",stopServiceParameters);
            stopMap.put("trace",stopTraceParameters);
            setMyRules(commandNames,stopMap,0);
        }
    }

    class MQSCSuspendScanner	extends MQSCDefaultScanner  {
        private final String[] commandNames = {"suspend",};
        private final String[] objectNames = {"qmgr",};
        private final String[] suspendQmgrParameters = {"cmdscope","cluster","clusnl","facility","log","mode",};   
        
        public MQSCSuspendScanner(MQSCColorProvider provider) {
            super(provider);
            HashMap suspendMap = new HashMap();
            suspendMap.put("qmgr",suspendQmgrParameters);
            setMyRules(commandNames,suspendMap,0);
        }
    }

*/}
