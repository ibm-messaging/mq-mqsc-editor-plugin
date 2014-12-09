/*
 * SupportPac MS0S (c) Copyright IBM Corp. 2007. All rights reserved.
 * 
 * Created on Jan 12, 2007
 *  
 */
package com.ibm.mq.explorer.ms0s.mqsceditor.lang;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCPartitionScanner;

public class MQSCLanguageConfigurator {
    // NOTODO: Build hashmap of objects & params, Read from file.
    // above NOTTODO is removed. REALTODO is to create a machine consumable
    // definition and use that to generate a parser.
    // This thing is crappy, but barely functional.

    // start review for 7.5 support 3/07/13
    private static final String[] commandNames = { "*", "alter", "alt",
            "archive", "arc", "backup", "clear", "define", "def", "delete",
            "display", "dis", "move", "ping", "purge", "recover", "rec",
            "refresh", "ref", "reset", "resolve", "resume", "res", "rverify",
            "rev", "reverify", "set", "start", "sta", "stop", "suspend", };
    private static final String[] altCommandNames = { "alter", "alt", };
    private static final String[] arcCommandNames = { "archive", "arc", };
    private static final String[] bkpCommandNames = { "backup", };
    private static final String[] clrCommandNames = { "clear", };
    private static final String[] cmtCommandNames = { "*", };
    private static final String[] defCommandNames = { "define", "def", };
    private static final String[] delCommandNames = { "delete", };
    private static final String[] disCommandNames = { "display", "dis", };
    private static final String[] endCommandNames = { "end", };
    private static final String[] moveCommandNames = { "move", };
    private static final String[] pingCommandNames = { "ping", };
    private static final String[] purgeCommandNames = { "purge", };
    private static final String[] recCommandNames = { "recover", "rec", };
    private static final String[] refCommandNames = { "refresh", "ref", };
    private static final String[] resCommandNames = { "resume", "res", };
    private static final String[] rstCommandNames = { "reset", };
    private static final String[] rsvCommandNames = { "resolve", };
    private static final String[] revCommandNames = { "rverify", "rev", };
    private static final String[] setCommandNames = { "set", };
    private static final String[] staCommandNames = { "start", "sta", };
    private static final String[] stoCommandNames = { "stop", };
    private static final String[] susCommandNames = { "suspend", };
    private static final String[] alterObjectNames = { "authinfo", "buffpool",
            "cfstruct","channel", "comminfo", "listener", "namelist",
            "process", "psid", "qalias", "qlocal", "qmgr", "qmodel", "qremote",
            "security", "service", "stgclass", "smds", "sub", "topic", "trace" };
    private static final String[] archiveObjectNames = { "log", };
    private static final String[] backupObjectNames = { "cfstruct", };
    private static final String[] clearObjectNames = { "qlocal", "topicstr", };
    private static final String[] defineObjectNames = { "authinfo", "buffpool",
            "cfstruct", "channel", "comminfo", "listener", "log", "maxmsgs",
            "namelist", "process", "psid", "qalias", "qlocal", "qmodel",
            "qremote", "service", "stgclass", "sub", "topic", };
    private static final String[] deleteObjectNames = { "authinfo", "authrec",
            "buffpool", "cfstruct", "channel", "comminfo", "listener",
            "namelist", "policy", "process", "psid", "qalias", "qlocal", "qmodel",
            "qremote", "service", "sub", "stgclass", "topic", };
    private static final String[] displayObjectNames = { "archive", "authinfo",
            "authrec", "authserv", "cfstatus", "cfstruct", "channel", "chinit",
            "chlauth", "chstatus", "clusqmgr", "cmdserv", "comminfo", "conn",
            "entauth", "group", "listener", "log", "lsstatus", "maxsmsgs",
            "namelist", "policy","process", "pubsub", "qmgr", "qmstatus", "qstatus",
            "qalias", "qcluster", "qlocal", "qmodel", "qremote", "queue",
            "sbstatus", "security", "service", "smds", "smdsconn", "stgclass",
            "sub", "svstatus", "system", "tcluster", "thread", "topic", 
            "trace", "tpstatus", "usage" };
    private static final String[] endObjectNames = {};
    private static final String[] moveObjectNames = { "qlocal", "ql", };
    private static final String[] pingObjectNames = { "channel", "qmgr", };
    private static final String[] purgeObjectNames = { "channel", };
    private static final String[] recoverObjectNames = { "bsds", "cfstruct", };
    private static final String[] refreshObjectNames = { "cluster", "qmgr",
            "security", };
    private static final String[] resetObjectNames = { "cfstruct", "channel",
            "cluster", "qmgr", "qstats", "smds", "tpipe" };
    private static final String[] resolveObjectNames = { "channel", "indoubt" };
    private static final String[] resumeObjectNames = { "qmgr", };
    private static final String[] rverifyObjectNames = { "security", "sec", };
    private static final String[] setObjectNames = { "archive", "authrec","authinfo",
            "chlauth", "log", "policy", "system", };
    private static final String[] startObjectNames = { "channel", "chinit",
            "cmdserv", "listener", "qmgr", "service", "smdsconn", "trace", };
    private static final String[] stopObjectNames = { "channel", "chinit",
            "cmdserv", "conn", "listener", "qmgr", "service", "smdsconn",
            "trace", };
    private static final String[] suspendObjectNames = { "qmgr", };
    @SuppressWarnings("rawtypes")
    private static ArrayList authinfoSubTypes = null;

    private static final String[] alterAuthInfoCRLLDAPParameters = {
            "authtype", "cmdscope", "conname", "descr", "ldappwd", "ldapuser",
            "qsgdisp", };
    private static final String[] alterAuthInfoOCSPParameters = { "authtype",
            "cmdscope", "descr", "ocspurl", "qsgdisp", };
    private static final String[] alterAuthInfoIDPWOSParameters = { "authtype","adoptctx",
        "chckclnt","chcklocl","cmdscope", "descr", "faildlay","ocspurl", "qsgdisp", };
    private static final String[] alterAuthInfoIDPWLDAPParameters = { "authtype","adoptctx",
        "basednu","chckclnt","chcklocl","classusr","cmdscope", "conname", 
        "descr", "faildlay","ldappwd","ocspurl", "qsgdisp","seccomm","shortusr","usrfield", };
    private static final String[] alterBuffPoolParameters = { "buffers", "loc","location","pageclas",};
    private static final String[] alterCfstructParameters = { "descr",
            "cflevel", "cfconlos", "recover","recauto", "offload", "offld1sz",
            "offld2sz", "offld3sz", "offld1th", "offld2th", "offld3th",
            "dsgroup", "dsblock", "dsbufs", "expand" };
    private static final String[] alterClntconnChannelParameters = {
            "affinity", "clntwght", "chltype", "cmdscope", "comphdr",
            "compmsg", "conname", "descr", "hbint", "kaint", "locladdr",
            "maxmsgl", "modename", "password", "qmname", "qsgdisp", "rcvdata",
            "rcvexit", "scydata", "scyexit", "senddata", "sendexit", 
            "sharecnv", "sslciph", "sslpeer", "tpname", "trptype", "userid", };
    private static final String[] alterClusrcvrChannelParameters = { "batchhb",
            "batchint", "batchsz", "chltype", "clusnl", "cluster", "clwlprty",
            "clwlrank", "clwlwght", "cmdscope", "comphdr", "compmsg",
            "conname", "convert", "descr", "discint", "hbint", "kaint",
            "locladdr", "longrty", "longtmr", "maxmsgl", "mcaname", "mcatype",
            "mcauser", "modename", "monchl", "mrdata", "mrexit", "mrrty",
            "mrtmr", "msgdata", "msgexit", "netprty", "npmspeed", "propctl",
            "putaut", "qsgdisp", "rcvdata", "rcvexit", "scydata", "scyexit",
            "senddata", "sendexit", "seqwrap", "shortrty", "shorttmr",
            "sslcauth", "sslciph", "sslpeer", "statchl", "tpname", "trptype",
            "usedlq", };
    private static final String[] alterClussdrChannelParameters = { "batchhb",
            "batchint", "batchsz", "chltype", "clusnl", "cluster", "clwlprty",
            "clwlrank", "clwlwght", "cmdscope", "comphdr", "compmsg",
            "conname", "convert", "descr", "discint", "hbint", "kaint",
            "locladdr", "longrty", "longtmr", "maxmsgl", "mcaname", "mcatype",
            "mcauser", "modename", "monchl", "msgdata", "msgexit", "npmspeed",
            "password", "propctl", "qsgdisp", "rcvdata", "rcvexit", "scydata",
            "scyexit", "senddata", "sendexit", "seqwrap", "shortrty",
            "shorttmr", "sslciph", "sslpeer", "statchl", "tpname", "trptype",
            "userid", "usedlq", };
    private static final String[] alterRcvrChannelParameters = { "autostart",
            "batchsz", "chltype", "cmdscope", "comphdr", "compmsg", "defcdisp",
            "descr", "hbint", "kaint", "maxmsgl", "mcauser", "monchl",
            "mrdata", "mrexit", "mrrty", "mrtmr", "msgdata", "msgexit",
            "npmspeed", "putaut", "qsgdisp", "rcvdata", "rcvexit", "scydata",
            "scyexit", "senddata", "sendexit", "seqwrap", "sslcauth",
            "sslciph", "sslpeer", "statchl", "trptype", "usedlq", };
    private static final String[] alterRqstrChannelParameters = { "autostart",
            "batchsz", "chltype", "cmdscope", "comphdr", "compmsg", "conname",
            "defcdisp", "descr", "hbint", "kaint", "locladdr", "maxmsgl",
            "mcaname", "mcatype", "mcauser", "modename", "monchl", "mrdata",
            "mrexit", "mrrty", "mrtmr", "msgdata", "msgexit", "npmspeed",
            "password", "putaut", "qsgdisp", "rcvdata", "rcvexit", "scydata",
            "scyexit", "senddata", "sendexit", "seqwrap", "sslcauth",
            "sslciph", "sslpeer", "statchl", "tpname", "trptype", "usedlq",
            "userid", };
    private static final String[] alterSdrChannelParameters = { "batchhb",
            "batchint", "batchsz", "chltype", "cmdscope", "comphdr", "compmsg",
            "conname", "convert", "defcdisp", "descr", "discint", "hbint",
            "kaint", "locladdr", "longrty", "longtmr", "maxmsgl", "mcaname",
            "mcatype", "mcauser", "modename", "monchl", "msgdata", "msgexit",
            "npmspeed", "password", "propctl", "qsgdisp", "rcvdata", "rcvexit",
            "scydata", "scyexit", "senddata", "sendexit", "seqwrap",
            "shortrty", "shorttmr", "sslciph", "sslpeer", "statchl", "tpname",
            "trptype", "userid", "usedlq", "xmitq", };
    private static final String[] alterSvrChannelParameters = { "autostart",
            "batchhb", "batchint", "batchsz", "chltype", "cmdscope", "comphdr",
            "compmsg", "conname", "convert", "defcdisp", "descr", "discint",
            "hbint", "kaint", "locladdr", "longrty", "longtmr", "maxmsgl",
            "mcaname", "mcatype", "mcauser", "modename", "monchl", "msgdata",
            "msgexit", "npmspeed", "password", "propctl", "qsgdisp", "rcvdata",
            "rcvexit", "scydata", "scyexit", "senddata", "sendexit", "seqwrap",
            "shortrty", "shorttmr", "sslcauth", "sslciph", "sslpeer",
            "statchl", "tpname", "trptype", "userid", "usedlq", "xmitq", };
    private static final String[] alterSvrconnChannelParameters = { "chltype",
            "cmdscope", "comphdr", "compmsg", "defcdisp", "descr", "discint",
            "hbint", "kaint", "maxinst", "maxinstc", "maxmsgl", "mcauser",
            "monchl", "putaut", "qsgdisp", "rcvdata", "rcvexit", "scydata",
            "scyexit", "senddata", "sendexit", "sharecnv", "sslcauth",
            "sslciph", "sslpeer", "trptype", };
    private static final String[] alterMqttChannelParamters = { "chltype",
            "backlog", "jaascfg", "localaddr", "mcauser", "port","protocol", "sslciph",
            "sslcauth", "sslkeyp", "sslkeyr", "trptype", "usecltid" };
    /*
     * private static final String[] alterListenerParameters = {
     * "adapter","backlog"
     * ,"commands","control","descr","ipaddr","loclname","ntbnames","port",
     * "sessions","socket","tpname","trptype",};
     */
    private static final String[] alterComminfoParameters = { "bridge",
            "ccsid", "commev", "descr", "encoding", "grpaddr", "mchbint",
            "mcprop", "monint", "msghist", "nsubhist", "port" };
    private static final String[] alterLsrLuParameters = { "control", "descr",
            "tpname", };
    private static final String[] alterLsrNetbiosParameters = { "control",
            "descr", "adapter", "commands", "loclname", "ntbnames", "sessions", };
    private static final String[] alterLsrSpxParameters = { "control", "descr",
            "backlog", "socket", };
    private static final String[] alterLsrTCPParameters = { "control", "descr",
            "backlog", "ipaddr", "port", };

    private static final String[] alterNamelistParameters = { "cmdscope",
            "qsgdisp", "descr", "names", "nltype", };

    private static final String[] alterProcessParameters = { "applicid",
            "appltype", "cmdscope", "descr", "envrdata", "userdata", "qsgdisp", };
    private static final String[] alterPsidParameters = { "expand", };

    private static final String[] alterQmgrParameters = { "force", "acctcono","acctint", 
            "acctmqi", "acctq", "actchl", "activrec", "actvcono", "actvtrc",
            "adoptchk","adoptmca", "authorev", "bridgeev", "ccsid", "certlabl",
            "certqsgl","certvpol",
            "cfconlos", "chad", "chadev", "chadexit", "chiadaps", "chidisps",
            "chiservp", "chlauth", "chlev", "clwldata", "clwlexit", "clwllen",
            "clwlmruc", "clwluseq", "cmdev", "cmdscope", "configev","connauth", "custom",
            "deadq", "defxmitq", "defclxq", "descr", "dnsgroup", "dnswlm",
            "expryint", "groupur", "igq", "igqaut", "igquser", "inhibtev",
            "ipaddrv", "localev", "loggerev", "lstrtmr", "lugroup", "luname",
            "lu62arm", "lu62chl", "markint", "maxchl", "maxhands", "maxmsgl",
            "maxpropl", "maxumsgs", "monacls", "monchl", "monq", "oportmax",
            "oportmin", "parent", "perfmev", "psclus", "psmode", "psnpmsg",
            "psnpres", "psrtycnt", "pssyncpt", "rcvtime", "rcvtmin",
            "rcvttype", "remoteev", "repos", "reposnl","revdns", "routerec", "schinit",
            "scmdserv", "scycase", "sqqmname", "sslcrlnl", "sslcryp", "sslev",
            "sslfips", "sslkeyr", "sslrkeyc", "ssltasks", "statacls",
            "statchl", "statint", "statmqi", "statq", "strstpev", "suiteb",
            "tcpchl", "tcpkeep", "tcpname", "tcpstack", "traxstr", "traxtbl",
            "treelife", "trigint", }; // v7

    private static final String[] alterQAliasParameters = { "clusnl", "custom",
            "cluster", "clwlprty", "clwlrank", "cmdscope", "defbind",
            "defpresp", "defreada", "defprty", "defpsist", "descr", "propctl",
            "force", "get", "put", "qsgdisp", "scope", "target", "targtype", };
    private static final String[] alterQLocalParameters = { "acctq", "boqname",
            "bothresh", "clchname", "cfstruct", "clusnl", "cluster",
            "clwlprty", "custom", "clwlrank", "clwluseq", "cmdscope",
            "defbind", "defprty", "defpresp", "defreada", "defpsist",
            "defsopt", "descr", "distl", "force", "get", "hardenbo",
            "nohardenbo", "indxtype", "initq", "maxdepth", "maxmsgl", "monq",
            "msgdlvsq", "noshare", "notrigger", "npmclass", "process",
            "propctl", "put", "qdepthhi", "qdepthlo", "qdphiev", "qdploev",
            "qdpmaxev", "qsgdisp", "qsvciev", "qsvcint", "retintvl", "scope",
            "share", "statq", "stgclass", "trigdata", "trigdpth", "trigger",
            "trigmpri", "trigtype", "usage", };

    private static final String[] alterQModelParameters = { "custom",
        "defprty","defpsist", "descr", "propctl", "put", "cmdscope", "qsgdisp", 
        "acctq", "boqname",
            "bothresh", "clchname", "cfstruct", 
            "defpresp", "defreada", "defsopt",
            "deftype", "distl", "force", "get", "hardenbo",
            "nohardenbo", "indxtype", "initq", "maxdepth", "maxmsgl", "monq",
            "msgdlvsq", "noshare", "notrigger", "npmclass", "process",
            "qdepthhi", "qdepthlo", "qdphiev", "qdploev",
            "qdpmaxev", "qsvciev", "qsvcint", "retintvl", 
            "share", "statq", "stgclass", "trigdata", "trigdpth", "trigger",
            "trigmpri", "trigtype", "usage", };

    private static final String[] alterQRemoteParameters = { "clusnl",
            "cluster", "clwlprty", "clwlrank", "cmdscope", "defbind", "custom",
            "defprty", "defpsist", "defpresp", "descr", "force", 
            "put", "qsgdisp", "rname", "rqmname", "scope", "xmitq", };

    private static final String[] alterSecurityParameters = { "cmdscope",
            "interval", "timeout", };
    private static final String[] alterServiceParameters = { "control",
            "descr", "servtype", "startarg", "startcmd", "stderr", "stdout",
            "stoparg", "stopcmd", };
    private static final String[] alterSmdsParameters = { "cfstruct", "dsbufs",
            "dsexpand", };
    private static final String[] alterStgclassParameters = { "cmdscope",
            "descr", "passtkta", "psid", "qsgdisp", "xcfgname", "xcfmname", };
    private static final String[] alterSubParameters = { "subid", "cmdscope",
            "dest", "destcorl", "destqmgr", "expiry", "psprop",
            "pubacct", "pubappid", "pubprty", "reqonly", "subuser",
            "userdata", "varuser", };
    private static final String[] alterTopicParameters = { "type", "cmdscope",
            "qsgdisp", "cluster", "clroute","comminfo", "custom", "defprty", "defpsist",
            "defpresp", "descr", "dursub", "mcast", "mdurmdl", "mndurmdl",
            "npmsgdlv", "pmsgdlv", "proxysub", "pub", "pubscope", "sub",
            "subscope", "usedlq", "wildcard", };
    private static final String[] alterTraceParameters = { "tno", "cmdscope",
            "class", "comment", "ifcid", };

    private static final String[] archiveLogParameters = { "cmdscope", "mode",
            "cancel", "offload", "time", "wait", };

    private static final String[] backupCFstructParameters = { "cmdscope",
            "exclint", };

    @SuppressWarnings("rawtypes")
    private static ArrayList channelSubTypes = null;
    @SuppressWarnings("rawtypes")
    private static HashMap commandObjectNameMap = null;

    private static final String[] authinfoSubTypeParameters = { "authtype", };
    private static final String[] chlSubTypeParameters = { "chltype", };

    private static final String[] clearQLocalParameters = { "cmdscope",
            "qsgdisp", };
    private static final String[] clearTopicStrParameters = { "cmdscope",
            "clrtype", "scope" };

    private static final String[] defineAuthInfoCRLLDAPParameters = {
            "authtype", "cmdscope", "classusr", "conname", "descr", "faildlay","ldappwd", "ldapuser",
            "qsgdisp", "like","replace","noreplace" };
    private static final String[] defineAuthInfoIDPWOSParameters = { "authtype",
        "cmdscope", "descr", "adoptctx","chckclnt", "chcklocal", "faildlay", "qsgdisp", 
        "like","replace","noreplace", };
    private static final String[] defineAuthInfoIDPWLDAPParameters = { "authtype","adoptctx",
        "basednu", "chckclnt","chcklocal", "classusr", "conname", "faildlay", 
        "ldappwd", "ldapusr", "seccomm", "shortusr", "usrfield", 
        "cmdscope", "descr", "qsgdisp", "like","replace","noreplace", };
    private static final String[] defineAuthInfoOCSPParameters = { "authtype",
        "cmdscope", "descr", "ocspurl", "qsgdisp", "like","replace","noreplace", };

    private static final String[] defineBuffPoolParameters = { "buffers", "loc",
        "location", "pageclas", "replace", "noreplace", };
    private static final String[] defineCfstructParameters = { "cfconlos", 
            "descr", "recauto", "offload", "offld1sz", "offld1th", "offld2sz",
            "offld2th", "offld3sz", "offld3th", "dsgroup", "dsbufs", "dsblock",
            "dsexpand", "replace", "noreplace", "cflevel", "recover", "like", };

    private static final String[] defineClntconnChannelParameters = {
            "affinity", "certlabl", "clntwght", "chltype", "cmdscope", "comphdr",
            "compmsg", "conname", "defrecon", "descr", "hbint", "kaint",
            "like", "locladdr", "maxmsgl", "modename", "password", "qmname",
            "qsgdisp", "replace", "noreplace", "rcvdata", "rcvexit", "scydata",
            "scyexit", "senddata", "sendexit", "sharecnv", "sslciph",
            "sslpeer", "tpname", "trptype", "userid", };
    private static final String[] defineSvrconnChannelParameters = { "chltype",
            "certlabl", "cmdscope", "comphdr", "compmsg", "defcdisp", "descr", "discint",
            "hbint", "kaint", "like", "maxinst", "maxinstc", "maxmsgl",
            "mcauser", "monchl", "putaut", "qsgdisp", "replace", "noreplace",
            "rcvdata", "rcvexit", "scydata", "scyexit", "senddata", "sendexit",
            "sharecnv", "sslcauth", "sslciph", "sslpeer", "trptype", };

    private static final String[] defineClussdrChannelParameters = { "batchhb",
            "batchint", "batchsz", "conname", "chltype", "certlabl", "clusnl",
            "cluster", "clwlprty","clwlrank", "clwlwght", "cmdscope", "comphdr",
            "compmsg", "convert", "descr", "discint", "hbint", "kaint", "like",
            "locladdr", "longrty", "longtmr", "maxmsgl", "mcaname", "mcatype",
            "mcauser", "modename", "monchl", "msgdata", "msgexit", "npmspeed",
            "password", "propctl", "qsgdisp", "replace", "noreplace",
            "rcvdata", "rcvexit", "scydata", "scyexit", "senddata", "sendexit",
            "seqwrap", "shortrty", "shorttmr", "sslciph", "sslpeer", "statchl",
            "tpname", "trptype", "userid", "usedlq", };

    private static final String[] defineClusrcvrChannelParameters = {
            "batchhb", "batchint", "batchsz", "certlabl", "chltype", "clusnl", "cluster",
            "clwlprty", "clwlrank", "clwlwght", "cmdscope", "comphdr",
            "compmsg", "conname", "convert", "descr", "discint", "hbint",
            "kaint", "like", "locladdr", "longrty", "longtmr", "maxmsgl",
            "mcaname", "mcatype", "mcauser", "modename", "monchl", "mrdata",
            "mrexit", "mrrty", "mrtmr", "msgdata", "msgexit", "netprty",
            "npmspeed", "propctl", "putaut", "qsgdisp", "replace", "noreplace",
            "rcvdata", "rcvexit", "scydata", "scyexit", "senddata", "sendexit",
            "seqwrap", "shortrty", "shorttmr", "sslcauth", "sslciph",
            "sslpeer", "statchl", "tpname", "trptype", "usedlq", };

    private static final String[] defineSdrChannelParameters = { "batchhb",
            "batchint", "batchsz", "certlabl", "chltype", "cmdscope", "comphdr",
            "compmsg","conname", "convert", "defcdisp", "descr", "discint", "hbint",
            "kaint", "like", "locladdr", "longrty", "longtmr", "maxmsgl",
            "mcaname", "mcatype", "mcauser", "modename", "monchl", "msgdata",
            "msgexit", "npmspeed", "password", "propctl", "qsgdisp", "replace",
            "noreplace", "rcvdata", "rcvexit", "scydata", "scyexit",
            "senddata", "sendexit", "seqwrap", "shortrty", "shorttmr",
            "sslciph", "sslpeer", "statchl", "tpname", "trptype", "userid",
            "xmitq", "usedlq", };
    private static final String[] defineRcvrChannelParameters = { "autostart",
            "batchsz", "chltype", "certlabl", "cmdscope", "comphdr", "compmsg", "defcdisp",
            "descr", "hbint", "kaint", "like", "maxmsgl", "mcauser", "monchl",
            "mrdata", "mrexit", "mrrty", "mrtmr", "msgdata", "msgexit",
            "npmspeed", "putaut", "qsgdisp", "replace", "noreplace", "rcvdata",
            "rcvexit", "scydata", "scyexit", "senddata", "sendexit", "seqwrap",
            "sslcauth", "sslciph", "sslpeer", "statchl", "trptype", "usedlq", };

    private static final String[] defineSvrChannelParameters = { "autostart",
            "batchhb", "batchint", "batchsz", "certlabl", "chltype", "cmdscope", "comphdr",
            "compmsg", "conname", "convert", "defcdisp", "descr", "discint",
            "hbint", "kaint", "like", "locladdr", "longrty", "longtmr",
            "maxmsgl", "mcaname", "mcatype", "mcauser", "modename", "monchl",
            "msgdata", "msgexit", "npmspeed", "password", "propctl", "qsgdisp",
            "replace", "noreplace", "rcvdata", "rcvexit", "scydata", "scyexit",
            "senddata", "sendexit", "seqwrap", "shortrty", "shorttmr",
            "sslcauth", "sslciph", "sslpeer", "statchl", "tpname", "trptype",
            "userid", "xmitq", "usedlq", };

    private static final String[] defineRqstrChannelParameters = { "autostart",
            "batchsz", "chltype", "certlabl", "cmdscope", "comphdr", "compmsg", "conname",
            "defcdisp", "descr", "hbint", "kaint", "like", "locladdr",
            "maxmsgl", "mcaname", "mcatype", "mcauser", "modename", "monchl",
            "mrdata", "mrexit", "mrrty", "mrtmr", "msgdata", "msgexit",
            "npmspeed", "password", "putaut", "qsgdisp", "replace",
            "noreplace", "rcvdata", "rcvexit", "scydata", "scyexit",
            "senddata", "sendexit", "seqwrap", "sslcauth", "sslciph",
            "sslpeer", "statchl", "tpname", "trptype", "userid", "usedlq", };

    private static final String[] defineMqttChannelParameters = { "chltype",
            "trptype", "backlog", "jaascfg", "like", "locladdr", "mcauser",
            "port", "protocol", "sslcauth", "sslciph", "sslkeyp", "sslkeyr", 
            "usecltid", };
    /*
     * private static final String[] defineListenerParameters = {
     * "adapter","backlog"
     * ,"commands","control","descr","ipaddr","like","loclname"
     * ,"ntbnames","port",
     * "replace","noreplace","sessions","socket","tpname","trptype",};
     */
    private static final String[] defineComminfoParameters = { "type",
            "bridge", "ccsid", "commev", "descr", "encoding", "grpaddr",
            "mchbint", "mcprop", "monint", "msghist", "nsubhist", "port",
            "like", "replace", "noreplace", };
    private static final String[] defineLsrLuParameters = { "trptype",
            "control", "descr", "replace", "noreplace", "like", "tpname", };
    private static final String[] defineLsrNetbiosParameters = { "trptype",
            "control", "descr", "replace", "noreplace", "like", "adapter",
            "commands", "loclname", "ntbnames", "sessions", };
    private static final String[] defineLsrSpxParameters = { "trptype",
            "control", "descr", "replace", "noreplace", "like", "backlog",
            "socket", };
    private static final String[] defineLsrTCPParameters = { "trptype",
            "control", "descr", "replace", "noreplace", "like", "backlog",
            "ipaddr", "port", };
    private static final String[] defineLogParameters = { "copy", "cmdscope", };
    private static final String[] defineMaxSmsgsParameters = { "cmdscope", };
    private static final String[] defineNamelistParameters = { "cmdscope",
            "like", "descr", "qsgdisp", "replace", "noreplace", "names",
            "nltype", };
    private static final String[] defineProcessParameters = { "applicid",
            "appltype", "cmdscope", "descr", "envrdata", "userdata", "qsgdisp",
            "like", "replace", "noreplace", };
    private static final String[] definePsidParameters = { "buffpool", "dsn",
            "expand", };

    private static final String[] defineQAliasParameters = { "clusnl",
            "custom", "cluster", "clwlprty", "clwlrank", "cmdscope", "defbind",
            "defprty", "defpsist", "defpresp", "defreada", "descr", "get",
            "like", "noreplace", "put", "propctl", "qsgdisp", "replace",
            "scope", "target", "targtype", };
    private static final String[] defineQLocalParameters = { "qsgdisp",
            "cmdscope", "like", "noreplace", "replace", "custom", "defprty",
            "defpsist", "descr", "put", "acctq", "boqname", "bothresh",
            "cfstruct", "clusnl", "cluster", "clwlprty", "clchname",
            "clwlrank", "clwluseq", "defbind", "defpresp", "defreada",
            "defsopt", "distl", "get", "hardenbo", "nohardenbo", "indxtype",
            "initq", "maxdepth", "maxmsgl", "monq", "msgdlvsq", "noshare",
            "notrigger", "npmclass", "process", "propctl", "qdepthhi",
            "qdepthlo", "qdphiev", "qdploev", "qdpmaxev", "qsvciev", "qsvcint",
            "retintvl", "scope", "share", "statq", "stgclass", "trigdata",
            "trigdpth", "trigger", "trigmpri", "trigtype", "usage", };
    private static final String[] defineQModelParameters = { "cmdscope",
            "custom", "qsgdisp", "like", "defprty", "defpsist", "descr", "put",
            "acctq", "boqname", "bothresh", "cfstruct", "clchname", "defpresp",
            "defreada", "defsopt", "deftype", "distl", "get", "hardenbo",
            "nohardenbo", "indxtype", "initq", "maxdepth", "maxmsgl", "monq",
            "msgdlvsq", "noreplace", "noshare", "notrigger", "npmclass",
            "process", "propctl", "qdepthhi", "qdepthlo", "qdphiev", "qdploev",
            "qdpmaxev", "qsvciev", "qsvcint", "replace", "retintvl", "share",
            "statq", "stgclass", "trigdata", "trigdpth", "trigger", "trigmpri",
            "trigtype", "usage", };
    private static final String[] defineQRemoteParameters = { "cmdscope",
            "custom", "qsgdisp", "like", "noreplace", "replace", "defprty",
            "defpsist", "descr", "put", "clusnl", "cluster", "clwlprty",
            "clwlrank", "defbind", "defpresp", "rname", "rqmname", "scope",
            "xmitq", };

    private static final String[] defineServiceParameters = { "control",
            "descr", "like", "noreplace", "replace", "servtype", "startarg",
            "startcmd", "stderr", "stdout", "stoparg", "stopcmd", };
    private static final String[] defineStgclassParameters = { "cmdscope",
            "descr", "like", "noreplace", "passtkta", "psid", "qsgdisp",
            "replace", "xcfgname", "xcfmname", };

    private static final String[] defineSubParameters = { "cmdscope", "dest",
            "destclas", "destcorl", "destqmgr", "expiry", "psprop", "pubacct",
            "pubappid", "pubprty", "reqonly", "selector", "sublevel",
            "subscope", "subuser", "topicobj", "topicstr", "userdata",
            "varuser", "wschema", "replace", "noreplace", };

    private static final String[] defineTopicParameters = { "type", "topicstr",
            "cmdscope", "qsgdisp", "like", "replace", "noreplace", "clroute", "cluster",
            "comminfo", "custom", "defprty", "defpsist", "defpresp", "descr",
            "dursub", "mcast", "mdurmdl", "mndurmdl", "npmsgdlv", "pmsgdlv",
            "proxysub", "pub", "pubscope", "sub", "subscope", "wildcard",
            "usedlq", "replace", "noreplace", };

    private static final String[] deleteAuthInfoParameters = { "cmdscope",
            "qsgdisp" };
    private static final String[] deleteAuthRecParameters = { "profile",
            "objtype", "principal", "group" };
    private static final String[] deleteBuffPoolParameters = {,};
    private static final String[] deleteCfstructParameters = {,};
    private static final String[] deleteChannelParameters = { "chltable",
            "cmdscope", "qsgdisp", "chltype", };
    private static final String[] deleteComminfoParameters = {,};
    private static final String[] deleteListenerParameters = {,};
    private static final String[] deleteNamelistParameters = { "cmdscope",
            "qsgdisp", };
    private static final String[] deletePolicyParameters = {"",};
    private static final String[] deleteProcessParameters = { "cmdscope",
            "qsgdisp", };
    private static final String[] deletePsidParameters = {,};
    private static final String[] deleteQAliasParameters = { "authrec",
            "cmdscope", "qsgdisp", };
    private static final String[] deleteQLocalParameters = { "authrec",
            "cmdscope", "qsgdisp", "purge", "nopurge", };
    private static final String[] deleteQModelParameters = { "authrec",
            "cmdscope", "qsgdisp", };
    private static final String[] deleteQRemoteParameters = { "authrec",
            "cmdscope", "qsgdisp", };
    private static final String[] deleteServiceParameters = {,};
    private static final String[] deleteSubParameters = { "subid", "cmdscope", };
    private static final String[] deleteStgclassParameters = { "cmdscope",
            "qsgdisp", };
    private static final String[] deleteTopicParameters = { "authrec",
            "cmdscope", "qsgdisp", };

    private static final String[] disArchiveParameters = { "cmdscope", };
    private static final String[] disAuthInfoParameters = { "where", "all",
            "qsgdisp", "cmdscope", "adoptctx","altdate", "alttime", "authtype", "basednu",
            "chckclnt", "chcklocl", "classusr", "conname", "descr", "faildlay", "ldappwd", 
            "ldapuser", "oscpurl", "seccom", "shortusr", "usrfield"};
    private static final String[] disAuthRecParameters = { "profile",
            "objtype", "principal", "group", "match", "servcomp", "all",
            "authlist", "entity", "enttype", };
    private static final String[] disAuthservParameters = { "all", "ifver",
            "uidsupp", };
    private static final String[] disCfstatusParameters = { "where", "type",
            "smds" };

    private static final String[] disCfstructParameters = { "where", "all",
            "altdate", "alttime", "cflevel", "cfconlos", "descr", "dsblock",
            "dsbufs", "dsexpand", "dsgroup", "offld1sz", "offld1th",
            "offld2sz", "offld2th", "offld3sz", "offld3th", "offload",
            "recauto", "recover" };

    private final String[] disChannelParameters = { "where", "all", "cmdscope",
            "qsgdisp", "type", "affinity", "altdate", "alttime", "batchhb",
            "batchint", "batchlim", "batchsz", "certlabl", "chltype", "clntwght", "clusnl",
            "cluster", "clwlprty", "clwlrank", "clwlwght", "comphdr",
            "compmsg", "conname", "convert", "defcdisp", "defrecon", "descr",
            "discint", "hbint", "kaint", "localaddr", "longrty", "longtmr",
            "maxinst", "maxinstc", "maxmsgl", "mcaname", "mcatype", "mcauser",
            "modename", "monchl", "mrdata", "mrexit", "mrrty", "mrtmr",
            "msgdata", "msgexit", "netprty", "npmspeed", "password", "propctl",
            "putaut", "qmname", "resetseq", "rcvdata", "rcvexit", "scydata",
            "scyexit", "senddata", "sendexit", "seqwrap", "shortrty",
            "shorttmr", "sslcauth", "sslciph", "sslpeer", "sslkeyp", "sslkeyr",
            "statchl", "sharecnv", "tpname", "trptype", "userid", "usedlq",
            "xmitq", "backlog", "jaascfg", "protocol", "usecltid", "port", };
    private static final String[] disChinitParameters = { "cmdscope", };
    private static final String[] disChlauthParameters = { "cmdscope", "type",
            "match", "all", "where", "address", "qmname", "clntuser","chckclnt","chcklocl",
            "sslpeer","sslcerti", "addrlist", "userlist", "mcauser", "altdate", "alttime",
            "descr", "custom", };
    private static final String[] disChstatusParameters = { "cmdscope",
            "where", "all", "current", "short", "saved", "chldisp", "conname",
            "xmitq", "monitor", "chltype", "curluwid", "curmsgs", "curseqno",
            "indoubt", "lstluwid", "lstseqno", "status", "batches", "batchsz",
            "bufsrcvd", "bufssent", "bytsrcvd", "bytssent", "chstada",
            "chstati", "comphdr", "compmsg", "comprate", "comptime",
            "curshcnv", "exittime", "hbint", "jobname", "kaint", "localaddr",
            "longrts", "lstmsgda", "lstmsgti", "maxshcnv", "maxmsgl",
            "mcastat", "mcauser", "monchl", "msgs", "nettime", "npmspeed",
            "rappltag", "rproduct", "rqmname", "rversion", "shortrts",
            "sslcerti", "sslcertu", "sslkeyda", "sslkeyti", "sslpeer",
            "sslrkeys", "stopreq", "substate", "xbatchsz", "xqmsgsa", "xqtime",
            "qmname", "summary", "clientid", "connections", "status", "msgsnt",
            "msgrcvd", "indoubtin", "indoubtout", "pending", "lmsgdate",
            "lmsgtime", "chlsdate", "chlstime", "clntusr",  "protocol", };

    private static final String[] disClusqmgrParameters = { "where", "all",
            "channel", "cluster", "cmdscope", "clusdate", "clustime",
            "deftype", "qmid", "qmtype", "status", "suspend", "version",
            "altdate", "alttime", "batchhb", "batchint", "batchlim", "batchsz",
            "clwlprty", "clwlrank", "clwlwght", "comphdr",
            "compmsg", "conname", "convert", "descr", "discint", "hbint",
            "kaint", "localaddr", "longrty", "longtmr", "maxmsgl", "mcaname",
            "mcatype", "mcauser", "modename", "mrdata", "mrexit", "mrrty",
            "mrtmr", "msgdata", "msgexit", "netprty", "npmspeed", "password",
            "propctl", "putaut", "rcvdata", "rcvexit", "scydata", "scyexit",
            "senddata", "sendexit", "seqwrap", "shortrty", "shorttmr",
            "sslcauth", "sslciph", "sslpeer", "tpname", "trptype", "userid",
            "usedlq", "xmitq",};

    private static final String[] disCmdservParameters = {,};
    private static final String[] disComminfoParameters = { "type", "where",
            "all", "descr", "grpaddr", "altdate", "alttime", "bridge", "ccsid",
            "commev", "descr", "encoding", "grpaddr", "nsubhist", "mchbint",
            "mcprop", "monint", "port", "msghist", };
    
    private static final String[] disConnParameters = { "where", "all",
            "extconn", "cmdscope", "type", "urdisp", "appldesc", "appltag",
            "appltype", "asid", "astate", "channel", "conname", "connopts",
            "exturid", "nid", "pid", "psbname", "pstid", "qmurid", "taskno",
            "tid", "transid", "uowlog", "uowlogda", "uowlogti", "uowstate",
            "uowstda", "uowstti", "urtype", "userid", "dest", "destqmgr",
            "hstate", "objname", "objtype", "openopts", "qsgdisp", "reada",
            "subid", "subname", "topicstr", };
    
    private static final String[] disEntauthParameters = { "principal",
            "group", "objname", "objtype", "servcomp", "all", "authlist",
            "entity", "enttype", };
    private static final String[] disGroupParameters = { "obsmsgs", };

    private static final String[] disLsrAllParameters = { "trptype", "where",
            "all", "adapter", "altdate", "alttime", "backlog", "commands",
            "control", "descr", "ipaddr", "loclname", "ntbnames", "port",
            "sessions", "socket", "tpname", };
/*    private static final String[] disLsrLuParameters = { "control", "descr",
            "tpname", "where", "all", "altdate", "alttime", };
    private static final String[] disLsrNetbiosParameters = { "control",
            "descr", "adapter", "commands", "loclname", "ntbnames", "sessions",
            "where", "all", "altdate", "alttime", };
    private static final String[] disLsrSpxParameters = { "control", "descr",
            "backlog", "socket", "where", "all", "altdate", "alttime", };
    private static final String[] disLsrTCPParameters = { "control", "descr",
            "backlog", "ipaddr", "port", "where", "all", "altdate", "alttime", };
*/
    
    private static final String[] disLogParameters = { "cmdscope", };

    private static final String[] disLsstatusParameters = { "adapter",
            "backlog", "commands", "control", "descr", "ipaddr", "loclname",
            "ntbnames", "pid", "port", "sessions", "socket", "tpname",
            "trptype", "where", "all", "status", "startda", "startti", };
    private static final String[] disMaxSmsgsParameters = { "cmdscope", };
    private static final String[] disNamelistParameters = { "where", "all",
            "cmdscope", "qsgdisp", "names", "nltype", "altdate", "alttime",
            "descr", "namcount", };
    private static final String[] disPolicyParameters= { , };
    
    private static final String[] disProcessParameters = { "where", "all",
            "cmdscope", "qsgdisp", "altdate", "alttime", "applicid",
            "appltype", "descr", "envrdata", "userdata", };

    private static final String[] disPubSubParameters = { "type", "cmdscope",
            "all", "qmname", "status", "subcount", "tpcount", };

    private static final String[] disQmgrParameters = { "all", "cmdscope",
            "system", "event", "chinit", "cluster", "pubsub", "acctcono",
            "acctint", "acctq", "acctmqi", "activrec", 
            "actvcono", "actvtrc",
            "altdate", "alttime", "ccsid", "certlabl", "certqsgl",
            "cfconlos", "cmdlevel", "connauth", "commandq", "cpilevel",
            "crdate","crtime", "custom",
            "deadq", "descr", "distl", "expryint", "groupur", "markint",
            "maxhands", "maxmsgl", "maxpropl", "maxprty", "maxumsgs", "monq",
            "platform", "qmname", "qsgname", "routerec", "scmdserv", "scycase",
            "splcap", "sqqmname", "statint", "statmqi", "statq", "syncpt", "trigint",
            "version", "xrcap", "authorev", "bridgeev", "chlev", "cmdev", "configev",
            "inhibtev", "localev", "loggerev", "perfmev", "remoteev", "sslev",
            "strstpev", "actchl", "adoptchk", "adoptmca", "certvpol", "chad",
            "chadev", "chadexit", "chiadaps", "chidisps", "chiservp",
            "chlauth", "defxmitq", "dnsgroup", "dnswlm", "igq", "igqaut",
            "igquser", "ipaddrv", "lstrtmr", "lugroup", "luname", "lu62arm",
            "lu62chl", "maxchl", "monacls", "monchl", "oportmax", "oportmin",
            "qmid", "rcvtime", "rcvtmin", "rcvttype","revdns", "sslev","schinit", "sslcrlnl",
            "sslcryp", "sslfips", "sslkeyr", "sslrkeyc", "ssltasks",
            "statacls", "suiteb", "tcpchl", "tcpkeep", "tcpname", "tcpstack",
            "traxstr", "traxtbl", "certvpol", "clwldata", "clwlexit", "clwllen",
            "clwlmruc", "clwluseq", "defclxq", "monacls", "psclus", "qmid",
            "repos", "reposnl", "parent", "psmode", "psrtycnt", "psnpmsg",
            "psnpres", "pssyncpt", "treelife", };

    private static final String[] disQmgrStatusParameters = { "all", "chinit",
            "cmdserv", "conns", "currlog", "instdesc", "instname", "instpath",
            "medialog","qmname", "reclog", "standby", "status", "startda", "startti", };

    private static final String[] disQueueStatusParameters = { "where", "all",
            "cmdscope", "type", "monitor", "opentype", "curdepth", "ipprocs",
            "lgetdate", "lgettime", "lputdate", "lputtime", "medialog", "monq",
            "msgage", "opprocs", "qsgdisp", "qtime", "uncom", "appldesc",
            "appltag", "appltype", "asid", "astate", "browse", "channel",
            "conname", "hstate", "input", "inquire", "output", "pid",
            "psbname", "pstid", "qmurid", "qsgdisp", "set", "taskno", "tid",
            "transid", "urid", "urtype", "userid", };

    private static final String[] disQueueParameters = { "where", "all",
            "cmdscope", "qsgdisp", "cfstruct", "clusinfo", "clusnl", "cluster",
            "psid", "targtype", "type", "stgclass", "acctq", "altdate",
            "alttime", "boqname", "bothresh", "clchname", "clusdate",
            "clusqmgr", "clusqt", "clustime", "clwlprty", "clwlrank",
            "clwluseq", "crdate", "crtime", "curdepth", "custom", "defbind",
            "defpresp", "defprty", "defpsist", "defreada", "defsoft",
            "deftype", "descr", "distl", "get", "hardenbo", "indxtype",
            "initq", "ipprocs", "maxdepth", "maxmsgl", "monq", "msgdlvsq",
            "npmclass", "opprocs", "process", "propctl", "put", "qdepthhi",
            "qdepthlo", "qdphiev", "qpdloev", "qdpmaxev", "qmid", "qsvciev",
            "qsvcint", "qtype", "retintvl", "rname", "rqmname", "scope",
            "share", "statq", "target", "targtype", "tpipe", "trigdata",
            "trigdpth", "trigger", "trigmpri", "trigtype", "usage", "xmitq", };

    private static final String[] disQaliasParameters = { "where", "all",
            "cmdscope", "qsgdisp", "cfstruct", "clusinfo", "clusnl", "cluster",
            "psid", "targtype", "type", "stgclass", "altdate", "alttime",
            "clusdate", "clusqmgr", "clusqt", "clustime", "defbind",
            "defpresp", "defprty", "defpsist", "defreada", "descr", "get",
            "propctl", "put", "qmid", "qtype", "scope", "target", "targtype",
            "type", "clwlprty", "clwlrank", "custom", };
    private static final String[] disQclusterParameters = { "where", "all",
            "cmdscope", "qsgdisp", "cfstruct", "clchname", "clusinfo",
            "clusnl", "cluster", "psid", "targtype", "type", "stgclass",
            "altdate", "alttime", "clusdate", "clusqmgr", "clusqt", "clustime",
            "defbind", "defpresp", "defprty", "defpsist", "defreada", "descr",
            "propctl", "put", "qtype", "statq", "clwluseq", "custom", };

    private static final String[] disQlocalParameters = { "where", "all",
            "cmdscope", "qsgdisp", "cfstruct", "clchname", "clusinfo",
            "clusnl", "cluster", "psid", "targtype", "type", "stgclass",
            "altdate", "alttime", "boqname", "bothresh", "crdate", "crtime",
            "curdepth", "defbind", "defprty", "defpresp", "defpsist",
            "defreada", "defsopt", "deftype", "descr", "distl", "get",
            "hardenbo", "initq", "ipprocs", "maxdepth", "maxmsgl", "msgdlvsq",
            "opprocs", "process", "propctl", "put", "qdepthhi", "qdepthlo",
            "qdphiev", "qpdloev", "qdpmaxev", "qmid", "qsvciev", "qsvcint",
            "qtype", "retintvl", "scope", "share", "trigdata", "trigdpth",
            "trigger", "trigmpri", "trigtype", "usage", "npmclass", "statq",
            "acctq", "monq", "clwlrank", "clwlprty", "clwluseq", "indxtype",
            "tpipe", "psid", "custom", };

    private static final String[] disQmodelParameters = { "where", "all",
            "cmdscope", "qsgdisp", "cfstruct", "clusinfo", "clusnl", "cluster",
            "psid", "targtype", "type", "stgclass", "acctq", "altdate",
            "alttime", "boqname", "bothresh", "crdate", "crtime", "defprty",
            "defpresp", "defpsist", "defreada", "defsopt", "deftype", "descr",
            "distl", "get", "hardenbo", "indxtype", "initq", "maxdepth",
            "maxmsgl", "monq", "msgdlvsq", "npmclass", "process", "propctl",
            "put", "qdepthhi", "qdepthlo", "qdphiev", "qpdloev", "qdpmaxev",
            "qsvciev", "qsvcint", "qtype", "retintvl", "share", "statq",
            "trigdata", "trigdpth", "trigger", "trigmpri", "trigtype", "usage",
            "custom", };
    private static final String[] disQremoteParameters = { "where", "all",
            "cmdscope", "qsgdisp", "cfstruct", "clusinfo", "clusnl", "cluster",
            "psid", "targtype", "type", "stgclass", "clwlprty", "clwlrank",
            "defbind", "defpresp", "defprty", "defpsist", "defreada", "descr",
            "propctl", "put", "rname", "rqmname", "scope", "xmitq", "altdate",
            "alttime", "qtype", "custom", };

    private static final String[] disSbStatusParameters = { "subid", "where",
            "all", "durable", "subtype", "cmdscope", "actconn", "lmsgdate",
            "lmsgtime", "nummsgs", "mcastrel", "subtype", "resmdate",
            "resmtime", "topicstr", "subuser", };

    private static final String[] disSecurityParameters = { "cmdscope",
            "interval", "timeout", "all", "switches", };

    private static final String[] disServiceParameters = { "control", "descr",
            "servtype", "startarg", "startcmd", "stderr", "stdout", "stoparg",
            "stopcmd", "altdate", "alttime", "where", "all", };
    private static final String[] disSmdsParameters = { "cfstruct", "where",
            "all", "dsbufs", "dsexpand", };


    private static final String[] disSmdsconnParameters = { "where",
            "cmdscope", "cfstruct", };
    private static final String[] disStgclassParameters = { "where", "all",
            "altdate", "alttime", "cmdscope", "descr", "passtkta", "psid",
            "qsgdisp", "xcfgname", "xcfmname", };

    private static final String[] disSubParameters = { "where", "all",
            "durable", "subtype", "cmdscope", "distype", "dest", "destcorl", "destqmgr",
            "sub", "subid", "subtype", "subuser", "topicstr", "altdate",
            "alttime", "crdate", "crttime", "destclas", "expiry", "psprop",
            "pubacct", "pubappid", "pubprty", "reqonly", "selector", "seltype",
            "sublevel", "subscope", "topicobj", "userdata", "varuser", "wschema", };

    private static final String[] disSvcStatusParameters = { "where", "all",
            "control", "descr", "pid", "servtype", "startarg", "startcmd",
            "startda", "startti", "status", "stderr", "stdout", "stoparg",
            "stopcmd", };

    private static final String[] disSystemParameters = { "cmdscope", };
    private static final String[] disTClusterParameters = { "where", "all",
        "cmdscope", "qsgdisp", "cluster", "clroute", "clstate", "clusdate",
        "clusqmgr", "clustime", };
    private static final String[] disThreadParameters = { "cmdscope", "type",
            "qmname", };
    private static final String[] disTopicParameters = { "where", "all",
            "cmdscope", "qsgdisp", "clusinfo", "type", "cluster",
            "altdate", "alttime", "clroute", "clstate","clusdate", 
            "clusqmgr", "clustime", "comminfo",
            "custom", "defprty", "defpsist", "defpresp", "descr", 
            "dursub", "mcast","mdurmdl", "mndurmdl", "npmsgdlv", "pmsgdlv",
            "proxysub", "pub", "pubscope", "qmid", "sub", "subscope", "topicstr",
            "wildcard", "usedlq", };
    private static final String[] disTpStatusParameters = { "where", "all",
            "cmdscope", "type", "admin", "clroute", "cluster", "comminfo", "defpresp",
            "defprty",
            "defpsist", "dursub", "mcast", "mdurmdl", "mndurmdl", "npmsgdlv", "pmsgdlv",
            "pub", "pubcount", "pubscope", "retained", "sub", "subcount",
            "subscope", "usedlq", "actconn", "durable", "lmsgdate", "lmsgtime",
            "mcastrel", "nummsgs", "resmdate", "resmtime", "subid", "subtype",
            "subuser", "lpubdate", "lpubtime", "numpubs", };
    private static final String[] disTraceParameters = { "cmdscope", "comment",
            "detail", "dest", "class", "rmid", "tno", "userid", };
    private static final String[] disUsageParameters = { "cmdscope", "psid",
            "type", };

    @SuppressWarnings("rawtypes")
    private static ArrayList listenerSubTypes = null;

    private static final String[] lsrSubTypeParameters = { "trptype", };

    private static final String[] moveQLocalParameters = { "cmdscope",
            "qsgdisp", "type", "toqlocal", };

    @SuppressWarnings({ "rawtypes" })
    private static HashMap objectParamNameMap = null;

    private static final String[] pingChannelParameters = { "cmdscope",
            "chldisp", "datalen", };

    private static final String[] pingQmgrParameters = {,};
    private static final String[] purgeMQTTChannelParameters = { "clientid",
            "chltype", };

    private static final String[] recBsdsParameters = { "cmdscope", };
    private static final String[] recCFStructParameters = { "cmdscope", "type", };

    private static final String[] refClusterParameters = { "cmdscope", "repos", };

    private static final String[] refQmgrParameters = { "cmdscope", "type",
            "inclint", "name", "object", };
    private static final String[] refSecurityParameters = { "cmdscope", "type", };

    private static final String[] resetCFStructParameters = { "action", };
    private static final String[] resetChannelParameters = { "cmdscope",
            "chldisp", "seqnum", };
    private static final String[] resetClusterParameters = { "cmdscope",
            "action", "qmname", "qmid", "queues", };

    private static final String[] resetQmgrParameters = { "type", "cmdscope",
            "child", "parent", };
    private static final String[] resetQstatsParameters = { "cmdscope", };
    private static final String[] resetSmdsParameters = { "cfstruct", "access",
            "status", };
    private static final String[] resetTpipeParameters = { "cmdscope",
            "action", "sendseq", "xcfgname", "rcvseq", "xcfmname", };

    private static final String[] resolveChannelParameters = { "cmdscope",
            "chldisp", "action", };
    private static final String[] resolveIndoubtParameters = { "cmdscope",
            "action", "nid", "qmname", };
    private static final String[] resumeQmgrParameters = { "cmdscope",
            "cluster", "clusnl", "facility", "log", };

    private static final String[] revSecurityParameters = { "cmdscope" };
    private static final String[] setArchiveParameters = { "cmdscope",
            "default", "alcunit", "arcpfx1", "arcpfx2", "arcretn", "arcwrtc",
            "arcwtor", "blksize", "catalog", "compact", "priqty", "protect",
            "quiesce", "secqty", "tstamp", "unit", "unit2", };
    private static final String[] setAuthrecParameters = { "profile",
            "objtype", "principal", "group", "authadd", "authrmv", "servcomp", };
    private static final String[] setChlauthParameters = { "cmdscope",
            "custom", "action", "descr", "chckclnt", "type", "userlist", "addrlist",
            "warn", "sslpeer", "sslcerti", "clntuser", "qmname", "usersrc", "mcauser",
            "address", };

    private static final String[] setLogParameters = { "cmdscope", "default",
            "complog", "deallct", "maxarch", "maxrtu", "wrthrsh", };
    private static final String[] setPolicyParameters = {"signalg","encalg",
        "signer","recip","enforce","tolerate","action",};
    private static final String[] setSystemParameters = { "cmdscope",
            "default","acelim", "exclmsg", "logload", "service",
            "statime", "tractbl", };

    private static MQSCLanguageConfigurator singleton;
    
    private static final String[] startChannelParameters = { "cmdscope",
            "chldisp", "chltype", };
    private static final String[] startChinitParameters = { "initq",
            "cmdscope", "envparm", };
    private static final String[] startCmdServParameters = {,};
    private static final String[] startListenerParameters = { "cmdscope",
            "indisp", "ipaddr", "luname", "port", "trptype", };
    private static final String[] startQmgrParameters = { "envparm", "parm", };
    private static final String[] startServiceParameters = {,};
    private static final String[] startSmdsconnParameters = { "cfstruct",
            "cmdscope", };
    private static final String[] startTraceParameters = { "cmdscope",
            "comment", "dest", "class", "rmid", "ifcid", "tdata", "userid", };

    private static final String[] stopChannelParameters = { "cmdscope",
            "chldisp", "conname", "qmname", "status", "mode", "chltype",
            "clientid", };
    private static final String[] stopChinitParameters = { "cmdscope",
            "shared", };
    private static final String[] stopCmdServParameters = {,};
    private static final String[] stopConnParameters = { "extconn", };
    private static final String[] stopListenerParameters = { "cmdscope",
            "indisp", "ipaddr", "port", "trptype", };

    private static final String[] stopQmgrParameters = { "cmdscope", "mode" };
    private static final String[] stopServiceParameters = {,};
    private static final String[] stopSmdsconnParameters = { "cfstruct",
            "cmdscope", };
    private static final String[] stopTraceParameters = { "cmdscope",
            "comment", "dest", "class", "rmid", "tno", "userid", };

    private static final String[] suspendQmgrParameters = { "cmdscope",
            "cluster", "clusnl", "facility", "log", "mode", };
    // validated against v7.0.0.0 syntax as of 01/20/09
    // validated against v7.1.0.0 syntax as of 03/03/12
    // validated against v7.5.0.0 syntax as of 03/11/13
    // validated against v8.0.0.1 syntax as of 11/12/2014.

    @SuppressWarnings("rawtypes")
    private static ArrayList takesNoValue = null;

    public static MQSCLanguageConfigurator getLanguageConfiguration() {
        if (singleton == null) {
            singleton = new MQSCLanguageConfigurator();
        }
        return singleton;
    }

    private MQSCLanguageConfigurator() {
        super();
    }

    public String[] getCommandNames() {
        return commandNames;
    }

    public String[] getCommandsForPartitionType(String partitionType) {
        if (partitionType.equals(MQSCPartitionScanner.MQSC_COMMENT)) {
            return cmtCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_ALTER_COMMAND)) {
            return altCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_ARCHIVE_COMMAND)) {
            return arcCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_BACKUP_COMMAND)) {
            return bkpCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_CLEAR_COMMAND)) {
            return clrCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_DEFINE_COMMAND)) {
            return defCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_DELETE_COMMAND)) {
            return delCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_DISPLAY_COMMAND)) {
            return disCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_END_COMMAND)) {
            return endCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_MOVE_COMMAND)) {
            return moveCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_PING_COMMAND)) {
            return pingCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_PURGE_COMMAND)) {
            return purgeCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_RECOVER_COMMAND)) {
            return recCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_REFRESH_COMMAND)) {
            return refCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_RESET_COMMAND)) {
            return rstCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_RESOLVE_COMMAND)) {
            return rsvCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_RESUME_COMMAND)) {
            return resCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_RVERIFY_COMMAND)) {
            return revCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_SET_COMMAND)) {
            return setCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_START_COMMAND)) {
            return staCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_STOP_COMMAND)) {
            return stoCommandNames;
        }
        if (partitionType.equals(MQSCPartitionScanner.MQSC_SUSPEND_COMMAND)) {
            return susCommandNames;
        }
        return new String[] {,};

    }

    @SuppressWarnings({ "rawtypes" })
    public HashMap getObjectParamMap(String commandName) {
        if (objectParamNameMap == null) {
            initOPNMap();
        }
        String cmdName = (commandName.toUpperCase());
        HashMap hm = (HashMap) objectParamNameMap.get((Object) cmdName);
        return (HashMap) hm;
    }

    @SuppressWarnings({ "rawtypes" })
    public Object getObjectParamNames(String commandName, String objectName) {
        if (objectParamNameMap == null) {
            initOPNMap();
        }
        // HashMap cmdMap =
        // ((HashMap)objectParamNameMap.get((Object)commandName.toUpperCase()));
        // Object opnMap = cmdMap.get((Object)objectName.toUpperCase());
        return ((HashMap) objectParamNameMap.get((Object) commandName
                .toUpperCase())).get((Object) objectName.toUpperCase());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String[] getObjectsForCommand(String commandName) {
        if (commandObjectNameMap == null) {
            commandObjectNameMap = new HashMap();
            commandObjectNameMap.put("ALTER", alterObjectNames);
            commandObjectNameMap.put("ALT", alterObjectNames);
            commandObjectNameMap.put("ARCHIVE", archiveObjectNames);
            commandObjectNameMap.put("ARC", archiveObjectNames);
            commandObjectNameMap.put("BACKUP", backupObjectNames);
            commandObjectNameMap.put("CLEAR", clearObjectNames);
            commandObjectNameMap.put("DEFINE", defineObjectNames);
            commandObjectNameMap.put("DEF", defineObjectNames);
            commandObjectNameMap.put("DELETE", deleteObjectNames);
            commandObjectNameMap.put("DISPLAY", displayObjectNames);
            commandObjectNameMap.put("DIS", displayObjectNames);
            commandObjectNameMap.put("END", endObjectNames);
            commandObjectNameMap.put("MOVE", moveObjectNames);
            commandObjectNameMap.put("PING", pingObjectNames);
            commandObjectNameMap.put("PURGE", purgeObjectNames);
            commandObjectNameMap.put("RECOVER", recoverObjectNames);
            commandObjectNameMap.put("REC", recoverObjectNames);
            commandObjectNameMap.put("REFRESH", refreshObjectNames);
            commandObjectNameMap.put("REF", refreshObjectNames);
            commandObjectNameMap.put("RESET", resetObjectNames);
            commandObjectNameMap.put("RESOLVE", resolveObjectNames);
            commandObjectNameMap.put("RES", resolveObjectNames);
            commandObjectNameMap.put("RESUME", resumeObjectNames);
            commandObjectNameMap.put("RVERIFY", rverifyObjectNames);
            commandObjectNameMap.put("REV", rverifyObjectNames);
            commandObjectNameMap.put("REVERIFY", rverifyObjectNames);
            commandObjectNameMap.put("SET", setObjectNames);
            commandObjectNameMap.put("START", startObjectNames);
            commandObjectNameMap.put("STA", startObjectNames);
            commandObjectNameMap.put("STOP", stopObjectNames);
            commandObjectNameMap.put("SUSPEND", suspendObjectNames);

        }
        return (String[]) commandObjectNameMap.get(commandName.toUpperCase());
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList getSubTypesForObject(String commandName, String objectName) {
        if ((objectName.equalsIgnoreCase("channel"))
                || (objectName.equalsIgnoreCase("chl"))) {
            if (channelSubTypes == null) {
                channelSubTypes = new ArrayList();
                channelSubTypes.add("CHLTYPE");
            }
            return channelSubTypes;
        }
        if ((objectName.equalsIgnoreCase("listener"))
                || (objectName.equalsIgnoreCase("lstr"))
                && (!(commandName.equalsIgnoreCase("display")))) {
            if (listenerSubTypes == null) {
                listenerSubTypes = new ArrayList();
                listenerSubTypes.add("TRPTYPE");
            }
            return listenerSubTypes;

        }
        if (objectName.equalsIgnoreCase("authinfo")) {
            if (authinfoSubTypes == null) {
                authinfoSubTypes = new ArrayList();
                authinfoSubTypes.add("AUTHTYPE");
            }
            return authinfoSubTypes;

        }
        return null;
        /*
         * if
         * ((commandName.equalsIgnoreCase("dis"))||(commandName.equalsIgnoreCase
         * ("display"))) { if
         * ((objectName.equalsIgnoreCase("channel"))||(objectName
         * .equalsIgnoreCase("chl"))) { if (disChannelSubTypes == null ) {
         * disChannelSubTypes = new ArrayList(); disChannelSubTypes.add("TYPE");
         * } return disChannelSubTypes; } } else
         */
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public ArrayList hasNoValue() {
        if (takesNoValue == null) {
            takesNoValue = new ArrayList();
            takesNoValue.add("force");
            takesNoValue.add("replace");
            takesNoValue.add("noreplace");
            takesNoValue.add("trigger");
            takesNoValue.add("notrigger");
            takesNoValue.add("all");
            takesNoValue.add("purge");
            takesNoValue.add("nopurge");
            takesNoValue.add("qmgr");
            takesNoValue.add("chinit");
            takesNoValue.add("cmdserv");
            takesNoValue.add("group");
            takesNoValue.add("bsds");
            takesNoValue.add("share");
            takesNoValue.add("noshare");
            // takesNoValue.add("force");
        }
        return takesNoValue;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void initOPNMap() {
        objectParamNameMap = new HashMap();
        HashMap subTypeMap = null;
        subTypeMap = new HashMap();
        HashMap alterMap = new HashMap();
        subTypeMap.put(" ", authinfoSubTypeParameters);
        subTypeMap.put("CRLLDAP", alterAuthInfoCRLLDAPParameters);
        subTypeMap.put("OSCP", alterAuthInfoOCSPParameters);
        subTypeMap.put("IDPWDOS", alterAuthInfoIDPWOSParameters);
        subTypeMap.put("IDPWLDAP", alterAuthInfoIDPWLDAPParameters);
        alterMap.put("AUTHINFO", subTypeMap);
        alterMap.put("BUFFPOOL", alterBuffPoolParameters);
        alterMap.put("BP", alterBuffPoolParameters);
        alterMap.put("CFSTRUCT", alterCfstructParameters);
        subTypeMap = new HashMap();
        subTypeMap.put(" ", chlSubTypeParameters);
        subTypeMap.put("SDR", alterSdrChannelParameters);
        subTypeMap.put("RCVR", alterRcvrChannelParameters);
        subTypeMap.put("SVR", alterSvrChannelParameters);
        subTypeMap.put("RQSTR", alterRqstrChannelParameters);
        subTypeMap.put("CLNTCONN", alterClntconnChannelParameters);
        subTypeMap.put("SVRCONN", alterSvrconnChannelParameters);
        subTypeMap.put("CLUSSDR", alterClussdrChannelParameters);
        subTypeMap.put("CLUSRCVR", alterClusrcvrChannelParameters);
        subTypeMap.put("MQTT", alterMqttChannelParamters);
        alterMap.put("CHANNEL", subTypeMap);
        alterMap.put("CHL", subTypeMap);
        subTypeMap = new HashMap();
        subTypeMap.put(" ", lsrSubTypeParameters);
        subTypeMap.put("LU62", alterLsrLuParameters);
        subTypeMap.put("NETBIOS", alterLsrNetbiosParameters);
        subTypeMap.put("SPX", alterLsrSpxParameters);
        subTypeMap.put("TCP", alterLsrTCPParameters);
        alterMap.put("COMMINFO", alterComminfoParameters);

        alterMap.put("LISTENER", subTypeMap);
        alterMap.put("LSTR", subTypeMap);
        alterMap.put("NAMELIST", alterNamelistParameters);
        alterMap.put("NL", alterNamelistParameters);
        alterMap.put("PROCESS", alterProcessParameters);
        alterMap.put("PSID", alterPsidParameters);
        alterMap.put("QMGR", alterQmgrParameters);
        alterMap.put("QALIAS", alterQAliasParameters);
        alterMap.put("QA", alterQAliasParameters);
        alterMap.put("QLOCAL", alterQLocalParameters);
        alterMap.put("QL", alterQLocalParameters);
        alterMap.put("QMODEL", alterQModelParameters);
        alterMap.put("QM", alterQModelParameters);
        alterMap.put("QREMOTE", alterQRemoteParameters);
        alterMap.put("QR", alterQRemoteParameters);
        alterMap.put("SMDS", alterSmdsParameters);
        alterMap.put("SECURITY", alterSecurityParameters);
        alterMap.put("SEC", alterSecurityParameters);
        alterMap.put("SERVICE", alterServiceParameters);
        alterMap.put("STGCLASS", alterStgclassParameters);
        alterMap.put("STC", alterStgclassParameters);
        alterMap.put("SUB", alterSubParameters);
        alterMap.put("TOPIC", alterTopicParameters);
        alterMap.put("TRACE", alterTraceParameters);
        HashMap archiveMap = new HashMap();
        archiveMap.put("LOG", archiveLogParameters);
        HashMap backupMap = new HashMap();
        backupMap.put("CFSTRUCT", backupCFstructParameters);
        HashMap clearMap = new HashMap();
        clearMap.put("QL", clearQLocalParameters);
        clearMap.put("QLOCAL", clearQLocalParameters);
        clearMap.put("TOPICSTR", clearTopicStrParameters);
        HashMap defineMap = new HashMap();
        subTypeMap = new HashMap();
        subTypeMap.put(" ", authinfoSubTypeParameters);
        subTypeMap.put("CRLLDAP", defineAuthInfoCRLLDAPParameters);
        subTypeMap.put("OSCP", defineAuthInfoOCSPParameters);
        subTypeMap.put("IDPWOS", defineAuthInfoIDPWOSParameters);
        subTypeMap.put("IDPWLDAP", defineAuthInfoIDPWLDAPParameters);
        defineMap.put("AUTHINFO", subTypeMap);
        defineMap.put("BUFFPOOL", defineBuffPoolParameters);
        defineMap.put("BP", defineBuffPoolParameters);
        defineMap.put("CFSTRUCT", defineCfstructParameters);
        defineMap.put("COMMINFO", defineComminfoParameters);
        subTypeMap = new HashMap();
        subTypeMap.put(" ", chlSubTypeParameters);
        subTypeMap.put("SDR", defineSdrChannelParameters);
        subTypeMap.put("RCVR", defineRcvrChannelParameters);
        subTypeMap.put("SVR", defineSvrChannelParameters);
        subTypeMap.put("RQSTR", defineRqstrChannelParameters);
        subTypeMap.put("CLNTCONN", defineClntconnChannelParameters);
        subTypeMap.put("SVRCONN", defineSvrconnChannelParameters);
        subTypeMap.put("CLUSSDR", defineClussdrChannelParameters);
        subTypeMap.put("CLUSRCVR", defineClusrcvrChannelParameters);
        subTypeMap.put("MQTT", defineMqttChannelParameters);

        defineMap.put("CHANNEL", subTypeMap);
        defineMap.put("CHL", subTypeMap);

        subTypeMap = new HashMap();
        subTypeMap.put(" ", lsrSubTypeParameters);
        subTypeMap.put("LU62", defineLsrLuParameters);
        subTypeMap.put("NETBIOS", defineLsrNetbiosParameters);
        subTypeMap.put("SPX", defineLsrSpxParameters);
        subTypeMap.put("TCP", defineLsrTCPParameters);

        defineMap.put("LISTENER", subTypeMap);
        defineMap.put("LSTR", subTypeMap);

        // defineMap.put("LISTENER",defineListenerParameters);
        // defineMap.put("LSTR",defineListenerParameters);

        defineMap.put("LOG", defineLogParameters);
        defineMap.put("MAXSMSGS", defineMaxSmsgsParameters);
        defineMap.put("NAMELIST", defineNamelistParameters);
        defineMap.put("NL", defineNamelistParameters);
        defineMap.put("PROCESS", defineProcessParameters);
        defineMap.put("PRO", defineProcessParameters);
        defineMap.put("PSID", definePsidParameters);
        defineMap.put("QALIAS", defineQAliasParameters);
        defineMap.put("QA", defineQAliasParameters);
        defineMap.put("QLOCAL", defineQLocalParameters);
        defineMap.put("QL", defineQLocalParameters);
        defineMap.put("QMODEL", defineQModelParameters);
        defineMap.put("QM", defineQModelParameters);
        defineMap.put("QREMOTE", defineQRemoteParameters);
        defineMap.put("QR", defineQRemoteParameters);
        defineMap.put("SERVICE", defineServiceParameters);
        defineMap.put("SUB", defineSubParameters);
        defineMap.put("STGCLASS", defineStgclassParameters);
        defineMap.put("STC", defineStgclassParameters);
        defineMap.put("TOPIC", defineTopicParameters);

        HashMap deleteMap = new HashMap();
        deleteMap.put("AUTHINFO", deleteAuthInfoParameters);
        deleteMap.put("AUTHREC", deleteAuthRecParameters);
        deleteMap.put("BUFFPOOL", deleteBuffPoolParameters);
        deleteMap.put("BP", deleteBuffPoolParameters);
        deleteMap.put("CFSTRUCT", deleteCfstructParameters);
        deleteMap.put("CHANNEL", deleteChannelParameters);
        deleteMap.put("CHL", deleteChannelParameters);
        deleteMap.put("COMMINFO", deleteComminfoParameters);
        deleteMap.put("LISTENER", deleteListenerParameters);
        deleteMap.put("LSTR", deleteListenerParameters);
        deleteMap.put("NAMELIST", deleteNamelistParameters);
        deleteMap.put("NL", deleteNamelistParameters);
        deleteMap.put("POLICY", deletePolicyParameters);
        deleteMap.put("PROCESS", deleteProcessParameters);
        deleteMap.put("PRO", deleteProcessParameters);
        deleteMap.put("PSID", deletePsidParameters);
        deleteMap.put("QALIAS", deleteQAliasParameters);
        deleteMap.put("QA", deleteQAliasParameters);
        deleteMap.put("QLOCAL", deleteQLocalParameters);
        deleteMap.put("QL", deleteQLocalParameters);
        deleteMap.put("QMODEL", deleteQModelParameters);
        deleteMap.put("QM", deleteQModelParameters);
        deleteMap.put("QREMOTE", deleteQRemoteParameters);
        deleteMap.put("QR", deleteQRemoteParameters);
        deleteMap.put("SERVICE", deleteServiceParameters);
        deleteMap.put("SUB", deleteSubParameters);
        deleteMap.put("STGCLASS", deleteStgclassParameters);
        deleteMap.put("STC", deleteStgclassParameters);
        deleteMap.put("TOPIC", deleteTopicParameters);

        HashMap displayMap = new HashMap();
        displayMap.put("AUTHINFO", disAuthInfoParameters);
        displayMap.put("AUTHREC", disAuthRecParameters);
        displayMap.put("AUTHSERV", disAuthservParameters);
        displayMap.put("ARCHIVE", disArchiveParameters);
        displayMap.put("CFSTATUS", disCfstatusParameters);
        displayMap.put("CFSTRUCT", disCfstructParameters);

        displayMap.put("CHANNEL", disChannelParameters);
        displayMap.put("CHL", disChannelParameters);

        displayMap.put("CHINIT", disChinitParameters);
        displayMap.put("CHI", disChinitParameters);
        displayMap.put("CHLAUTH", disChlauthParameters);
        displayMap.put("DQM", disChinitParameters);
        displayMap.put("CHSTATUS", disChstatusParameters);
        displayMap.put("CLUSQMGR", disClusqmgrParameters);
        displayMap.put("COMMINFO", disComminfoParameters);
        displayMap.put("CMDSERV", disCmdservParameters);
        displayMap.put("CS", disCmdservParameters);
        displayMap.put("CONN", disConnParameters);
        displayMap.put("ENTAUTH", disEntauthParameters);
        displayMap.put("GROUP", disGroupParameters);

//        subTypeMap = new HashMap();
//        subTypeMap.put(" ", lsrSubTypeParameters);
//        subTypeMap.put("LU62", disLsrLuParameters);
//        subTypeMap.put("NETBIOS", disLsrNetbiosParameters);
//        subTypeMap.put("SPX", disLsrSpxParameters);
//        subTypeMap.put("TCP", disLsrTCPParameters);
//        subTypeMap.put("ALL", disLsrAllParameters);

        displayMap.put("LISTENER", disLsrAllParameters);
        displayMap.put("LSTR", disLsrAllParameters);

        // displayMap.put("LISTENER",disListenerParameters);
        // displayMap.put("LSTR",disListenerParameters);

        displayMap.put("LOG", disLogParameters);
        displayMap.put("LSSTATUS", disLsstatusParameters);
        displayMap.put("MAXSMSGS", disMaxSmsgsParameters);
        displayMap.put("MAXSM", disMaxSmsgsParameters);
        displayMap.put("NAMELIST", disNamelistParameters);
        displayMap.put("NL", disNamelistParameters);
        displayMap.put("POLICY", disPolicyParameters);
        displayMap.put("PUBSUB", disPubSubParameters);
        displayMap.put("PROCESS", disProcessParameters);
        displayMap.put("PRO", disProcessParameters);
        displayMap.put("QMGR", disQmgrParameters);
        displayMap.put("QMSTATUS", disQmgrStatusParameters);
        displayMap.put("QSTATUS", disQueueStatusParameters);
        displayMap.put("QS", disQueueStatusParameters);
        displayMap.put("QUEUE", disQueueParameters);
        displayMap.put("Q", disQueueParameters);
        displayMap.put("QALIAS", disQaliasParameters);
        displayMap.put("QA", disQaliasParameters);
        displayMap.put("QCLUSTER", disQclusterParameters);
        displayMap.put("QC", disQclusterParameters);
        displayMap.put("QLOCAL", disQlocalParameters);
        displayMap.put("QL", disQlocalParameters);
        displayMap.put("QMODEL", disQmodelParameters);
        displayMap.put("QM", disQmodelParameters);
        displayMap.put("QREMOTE", disQremoteParameters);
        displayMap.put("QR", disQremoteParameters);
        displayMap.put("SBSTATUS", disSbStatusParameters);
        displayMap.put("SECURITY", disSecurityParameters);
        displayMap.put("SEC", disSecurityParameters);
        displayMap.put("SERVICE", disServiceParameters);
        displayMap.put("SMDS", disSmdsParameters);
        displayMap.put("SMDSCONN", disSmdsconnParameters);
        displayMap.put("SUB", disSubParameters);
        displayMap.put("STGCLASS", disStgclassParameters);
        displayMap.put("STC", disStgclassParameters);
        displayMap.put("SVSTATUS", disSvcStatusParameters);
        displayMap.put("SYSTEM", disSystemParameters);
        displayMap.put("TCLUSTER", disTClusterParameters);
        displayMap.put("THREAD", disThreadParameters);
        displayMap.put("THD", disThreadParameters);
        displayMap.put("TOPIC", disTopicParameters);
        displayMap.put("TPS", disTpStatusParameters);
        displayMap.put("TPSTATUS", disTpStatusParameters);
        displayMap.put("TRACE", disTraceParameters);
        displayMap.put("USAGE", disUsageParameters);
        HashMap moveMap = new HashMap();
        moveMap.put("QL", moveQLocalParameters);
        moveMap.put("QLOCAL", moveQLocalParameters);
        HashMap pingMap = new HashMap();
        pingMap.put("CHL", pingChannelParameters);
        pingMap.put("CHANNEL", pingChannelParameters);
        pingMap.put("QMGR", pingQmgrParameters);
        HashMap purgeMap = new HashMap();
        purgeMap.put("CHANNEL", purgeMQTTChannelParameters);
        HashMap recoverMap = new HashMap();
        recoverMap.put("CFSTRUCT", recCFStructParameters);
        recoverMap.put("BSDS", recBsdsParameters);
        HashMap refreshMap = new HashMap();
        refreshMap.put("CLUSTER", refClusterParameters);
        refreshMap.put("QMGR", refQmgrParameters);
        refreshMap.put("SECURITY", refSecurityParameters);
        refreshMap.put("SEC", refSecurityParameters);
        HashMap resetMap = new HashMap();
        resetMap.put("CHANNEL", resetChannelParameters);
        resetMap.put("CHL", resetChannelParameters);
        resetMap.put("CFSTRUCT", resetCFStructParameters);
        resetMap.put("CLUSTER", resetClusterParameters);
        resetMap.put("QMGR", resetQmgrParameters);
        resetMap.put("SMDS", resetSmdsParameters);
        resetMap.put("QSTATS", resetQstatsParameters);
        resetMap.put("TPIPE", resetTpipeParameters);
        HashMap resolveMap = new HashMap();
        resolveMap.put("CHANNEL", resolveChannelParameters);
        resolveMap.put("CHL", resolveChannelParameters);
        resolveMap.put("INDOUBT", resolveIndoubtParameters);
        HashMap resumeMap = new HashMap();
        resumeMap.put("QMGR", resumeQmgrParameters);
        HashMap rverifyMap = new HashMap();
        rverifyMap.put("QMGR", revSecurityParameters);
        HashMap setMap = new HashMap();
        setMap.put("ARCHIVE", setArchiveParameters);
        setMap.put("ARC", setArchiveParameters);
        setMap.put("AUTHREC", setAuthrecParameters);
        setMap.put("CHLAUTH", setChlauthParameters);
        setMap.put("LOG", setLogParameters);
        setMap.put("POLICY", setPolicyParameters);
        setMap.put("SYSTEM", setSystemParameters);
        HashMap startMap = new HashMap();
        startMap.put("CHANNEL", startChannelParameters);
        startMap.put("CHL", startChannelParameters);
        startMap.put("CHINIT", startChinitParameters);
        startMap.put("CMDSERV", startCmdServParameters);
        startMap.put("CS", startCmdServParameters);
        startMap.put("LISTENER", startListenerParameters);
        startMap.put("LSTR", startListenerParameters);
        startMap.put("QMGR", startQmgrParameters);
        startMap.put("SERVICE", startServiceParameters);
        startMap.put("SMDSCONN", startSmdsconnParameters);
        startMap.put("TRACE", startTraceParameters);
        HashMap stopMap = new HashMap();
        stopMap.put("CHANNEL", stopChannelParameters);
        stopMap.put("CHL", stopChannelParameters);
        stopMap.put("CHINIT", stopChinitParameters);
        stopMap.put("CMDSERV", stopCmdServParameters);
        stopMap.put("CS", stopCmdServParameters);
        stopMap.put("CONN", stopConnParameters);
        stopMap.put("LISTENER", stopListenerParameters);
        stopMap.put("LSTR", stopListenerParameters);
        stopMap.put("QMGR", stopQmgrParameters);
        stopMap.put("SMDSCONN", stopSmdsconnParameters);
        stopMap.put("SERVICE", stopServiceParameters);
        stopMap.put("TRACE", stopTraceParameters);
        HashMap suspendMap = new HashMap();
        suspendMap.put("QMGR", suspendQmgrParameters);
        HashMap endMap = new HashMap();
        objectParamNameMap.put("ALTER", alterMap);
        objectParamNameMap.put("ALT", alterMap);
        objectParamNameMap.put("ARCHIVE", archiveMap);
        objectParamNameMap.put("ARC", archiveMap);
        objectParamNameMap.put("BACKUP", backupMap);
        objectParamNameMap.put("CLEAR", clearMap);
        objectParamNameMap.put("DEFINE", defineMap);
        objectParamNameMap.put("DEF", defineMap);
        objectParamNameMap.put("DELETE", deleteMap);
        objectParamNameMap.put("DISPLAY", displayMap);
        objectParamNameMap.put("DIS", displayMap);
        objectParamNameMap.put("END", endMap);
        objectParamNameMap.put("MOVE", moveMap);
        objectParamNameMap.put("PING", pingMap);
        objectParamNameMap.put("PURGE", purgeMap);
        objectParamNameMap.put("RECOVER", recoverMap);
        objectParamNameMap.put("REC", recoverMap);
        objectParamNameMap.put("REFRESH", refreshMap);
        objectParamNameMap.put("REF", refreshMap);
        objectParamNameMap.put("RESET", resetMap);
        objectParamNameMap.put("RESOLVE", resolveMap);
        objectParamNameMap.put("RES", resolveMap);
        objectParamNameMap.put("RESUME", resumeMap);
        objectParamNameMap.put("RVERIFY", rverifyMap);
        objectParamNameMap.put("REV", rverifyMap);
        objectParamNameMap.put("SET", setMap);
        objectParamNameMap.put("START", startMap);
        objectParamNameMap.put("STA", startMap);
        objectParamNameMap.put("STOP", stopMap);
        objectParamNameMap.put("SUSPEND", suspendMap);
    }
}
