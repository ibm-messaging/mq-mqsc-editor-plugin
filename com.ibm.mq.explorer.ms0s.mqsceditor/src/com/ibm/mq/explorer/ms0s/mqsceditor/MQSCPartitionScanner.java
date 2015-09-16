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
package com.ibm.mq.explorer.ms0s.mqsceditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

import com.ibm.mq.explorer.ms0s.mqsceditor.rules.MQSCPartitionRule;

/**
 * @author Jeff Lowrey
 */

/**
 * <p>
 * This creates a RuleBasedPartitionScanner to identify the individual 
 * MQSC Commands in an MQSC document.  
 * 
 * It currently doesn't use the MQSCLanguageConfigurator to determine what 
 * the main verbs in MQSC are.  This might be complicated to implement, given
 * that we also need to build constants for each partition type.
 * 
 * It could be looked into.
 * 
 **/


public class MQSCPartitionScanner extends RuleBasedPartitionScanner {
    public final static String MQSC_COMMENT = "__mqsc_comment";

    public final static String MQSC_ALTER_COMMAND = "__mqsc_alter_command";

    public final static String MQSC_ALT_COMMAND = "__mqsc_alter_command";

    public final static String MQSC_ARCHIVE_COMMAND = "__mqsc_archive_command";

    public final static String MQSC_ARC_COMMAND = "__mqsc_archive_command";

    public final static String MQSC_BACKUP_COMMAND = "__mqsc_backup_command";

    public final static String MQSC_CLEAR_COMMAND = "__mqsc_clear_command";

    public final static String MQSC_CL_COMMAND = "__mqsc_clear_command";

    public final static String MQSC_DEFINE_COMMAND = "__mqsc_define_command";

    public final static String MQSC_DEF_COMMAND = "__mqsc_define_command";

    public final static String MQSC_DELETE_COMMAND = "__mqsc_delete_command";

    public final static String MQSC_DISPLAY_COMMAND = "__mqsc_display_command";

    public final static String MQSC_DIS_COMMAND = "__mqsc_display_command";

    public final static String MQSC_END_COMMAND = "__mqsc_end_command";

    public final static String MQSC_MOVE_COMMAND = "__mqsc_move_command";

    public final static String MQSC_PING_COMMAND = "__mqsc_ping_command";

    public final static String MQSC_PURGE_COMMAND = "__mqsc_purge_command";

    public final static String MQSC_RECOVER_COMMAND = "__mqsc_recover_command";

    public final static String MQSC_REC_COMMAND = "__mqsc_recover_command";

    public final static String MQSC_REFRESH_COMMAND = "__mqsc_refresh_command";

    public final static String MQSC_REF_COMMAND = "__mqsc_refresh_command";

    public final static String MQSC_RESET_COMMAND = "__mqsc_reset_command";

    public final static String MQSC_RESOLVE_COMMAND = "__mqsc_resolve_command";

    public final static String MQSC_RES_COMMAND = "__mqsc_resolve_command";

    public final static String MQSC_RESUME_COMMAND = "__mqsc_resume_command";

    public final static String MQSC_RVERIFY_COMMAND = "__mqsc_rverify_command";

    public final static String MQSC_REV_COMMAND = "__mqsc_rverify_command";

    public final static String MQSC_SET_COMMAND = "__mqsc_set_command";

    public final static String MQSC_START_COMMAND = "__mqsc_start_command";

    public final static String MQSC_STA_COMMAND = "__mqsc_start_command";

    public final static String MQSC_STOP_COMMAND = "__mqsc_stop_command";

    public final static String MQSC_SUSPEND_COMMAND = "__mqsc_suspend_command";

    public final static String[] MQSC_PARTITION_TYPES = new String[] {
            MQSC_COMMENT, MQSC_ALTER_COMMAND, MQSC_ALT_COMMAND,
            MQSC_ARCHIVE_COMMAND, MQSC_ARC_COMMAND, MQSC_BACKUP_COMMAND,
            MQSC_CLEAR_COMMAND, MQSC_CL_COMMAND, MQSC_DEFINE_COMMAND,
            MQSC_DEF_COMMAND, MQSC_DELETE_COMMAND, MQSC_DISPLAY_COMMAND,
            MQSC_DIS_COMMAND, MQSC_END_COMMAND, MQSC_MOVE_COMMAND, MQSC_PING_COMMAND, MQSC_PURGE_COMMAND,
            MQSC_RECOVER_COMMAND, MQSC_REC_COMMAND, MQSC_REFRESH_COMMAND,
            MQSC_REF_COMMAND, MQSC_RESET_COMMAND, MQSC_RESOLVE_COMMAND,
            MQSC_RES_COMMAND, MQSC_RESUME_COMMAND, MQSC_RVERIFY_COMMAND,
            MQSC_REV_COMMAND, MQSC_SET_COMMAND, MQSC_START_COMMAND,
            MQSC_STA_COMMAND, MQSC_STOP_COMMAND, MQSC_SUSPEND_COMMAND, };

    public static String[] mqscCommandWords = { "*", "alter", "alt",
            "archive", "arc", "backup", "clear", "cl", "define", "def",
            "delete", "display", "dis", "end","move", "ping", "purge","recover",
            "rec", "refresh", "ref", "reset", "resolve", "res",
            "resume", "rverify", "rev", "set", "start", "sta", "stop",
            "suspend", };

    /**
     * Detector for empty comments.
     */
    static class EmptyCommentDetector implements IWordDetector {

        /*
         * (non-Javadoc) Method declared on IWordDetector
         */
        public boolean isWordStart(char c) {
            return (c == '/');
        }

        /*
         * (non-Javadoc) Method declared on IWordDetector
         */
        public boolean isWordPart(char c) {
            return (c == '*' || c == '/');
        }
    }

    /**
     *  
     */
    static class WordPredicateRule extends WordRule implements IPredicateRule {

        private IToken fSuccessToken;

        public WordPredicateRule(IToken successToken) {
            super(new EmptyCommentDetector());
            fSuccessToken = successToken;
            addWord("/**/", fSuccessToken); //$NON-NLS-1$
        }

        /*
         * @see org.eclipse.jface.text.rules.IPredicateRule#evaluate(ICharacterScanner,
         *      boolean)
         */
        public IToken evaluate(ICharacterScanner scanner, boolean resume) {
            return super.evaluate(scanner);
        }

        /*
         * @see org.eclipse.jface.text.rules.IPredicateRule#getSuccessToken()
         */
        public IToken getSuccessToken() {
            return fSuccessToken;
        }
    }

    /**
     * Creates the partitioner and sets up the appropriate rules.
     */
    @SuppressWarnings({ "rawtypes","unchecked"})
    public MQSCPartitionScanner() {
        super();

        List rules = new ArrayList();

        rules.add(new EndOfLineRule("*", new Token(MQSC_COMMENT)));

        for (int i = 0; i < MQSC_PARTITION_TYPES.length; i++) {
            IToken temp = new Token(MQSC_PARTITION_TYPES[i]);
            rules.add(new MQSCPartitionRule(mqscCommandWords[i], temp));
        }

        IPredicateRule[] result = new IPredicateRule[rules.size()];
        rules.toArray(result);
        setPredicateRules(result);
    }
}
