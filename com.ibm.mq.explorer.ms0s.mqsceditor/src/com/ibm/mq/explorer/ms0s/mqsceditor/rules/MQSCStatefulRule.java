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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;

import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditor;
import com.ibm.mq.explorer.ms0s.mqsceditor.MQSCEditorPlugin;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.IMQSCCommandEventNotifier;
import com.ibm.mq.explorer.ms0s.mqsceditor.events.MQSCCommandEvent;
import com.ibm.mq.explorer.ms0s.mqsceditor.lang.MQSCLanguageConfigurator;

/**
 * 
 * @author Jeff Lowrey
 * 
 */

/**
 * <p>
 * This is the rule that uses state information on the text it had arleady seen to 
 * ensure that the current bits of text are valid.  It uses the MQSCLanugageConfigurator to
 * identify specific data in the text and merely focuses on what state it's in and what type of
 * token is next.  
 **/


public class MQSCStatefulRule implements IRule {
	protected static final int INVALID_STATE = 0;

	protected static final int INITIAL_STATE = 1;

	protected static final int FOUND_COMMAND_STATE = 2;

	protected static final int FOUND_OBJECT_STATE = 3;

	protected static final int FOUND_OBJECT_NAME_STATE = 4;

	protected static final int FOUND_PARAMETER_STATE = 5;

	protected static final int TERMINAL_STATE = 6;

	private int currentState = INITIAL_STATE;

	// private int previousState = INITIAL_STATE;

	/** Internal setting for the uninitialized column constraint */
	private static final int UNDEFINED = -1;

	/** The word detector used by this rule */
	private IWordDetector fDetector;

	/**
	 * The default token to be returned on success and if nothing else has been
	 * specified.
	 */
	private IToken fDefaultToken;

	private IToken fValidObjectToken;

	private IToken fInvalidObjectToken;

	private IToken fInvalidParmToken;

	/** The column constraint */
	private int fColumn = UNDEFINED;

	@SuppressWarnings("rawtypes")
	/** The table of predefined words and token for this rule */
	private Map fCommands = new HashMap();

	/** The table of predefined words and token for this rule */
	@SuppressWarnings("rawtypes")
	private Map fObjects = new HashMap();

	/** The table of predefined words and token for this rule */
	@SuppressWarnings("rawtypes")
	private Map fParameters = new HashMap();

	/** Buffer used for pattern detection */
	private StringBuffer fBuffer = new StringBuffer();

	private String objectType = "";

	private String objectName = "";

	private String cmdName = "";

	private String subType = " ";

	private IMQSCCommandEventNotifier fNotifier;

	private boolean balancedParen = true;

	private String badParen;

	private int parenCount = 0;

	private int lastParenOffset = 0;

	// private boolean unbalancedQuote = false;

	private boolean foundSubType = false;

	/**
	 * @param detector
	 * @param commands
	 * @param objects
	 * @param parameters
	 */
	@SuppressWarnings("rawtypes")
	public MQSCStatefulRule(IWordDetector detector, Map commands, Map objects,
			Map parameters, IMQSCCommandEventNotifier notifier) {
		this(detector, commands, objects, Token.UNDEFINED, Token.UNDEFINED,
				Token.UNDEFINED, Token.UNDEFINED, notifier);
	}

	/**
     */
	@SuppressWarnings("rawtypes")
	public MQSCStatefulRule(IWordDetector detector, Map commands, Map objects,
			IToken validObjectToken, IToken invalidObjectToken,
			IToken invalidParmToken, IToken defaultToken,
			IMQSCCommandEventNotifier notifier) {
		super();
		fDetector = detector;
		fCommands = commands;
		fObjects = objects;
		fValidObjectToken = validObjectToken;
		fInvalidObjectToken = invalidObjectToken;
		fInvalidParmToken = invalidParmToken;
		fDefaultToken = defaultToken;
		fNotifier = notifier;
	}

	/*
     */
	@SuppressWarnings("rawtypes")
	public IToken evaluate(ICharacterScanner scanner) {
		// TODO: deal with two word parameters, like 'cancel offload'?
		char closeParen, openParen;
		IToken token;
		MQSCDefaultScanner mqscScanner = (MQSCDefaultScanner) scanner;
		int startPos = mqscScanner.getOffset();
		int partStart = mqscScanner.getPartitionStart();
		int partLength = mqscScanner.getPartitionLength();
		int partIndex = mqscScanner.getPartitionNumber();
		int c = scanner.read();
		char cC = (char) c;
		char lookAhead;

		Object objList = null;
		// Handle special characters
		printState(0, startPos);
		switch (cC) {
		case ';':
		case '\n':
		case '\r':
			if (currentState != INVALID_STATE) {
				currentState = TERMINAL_STATE;
				printState(1, startPos);
				return generateToken(fDefaultToken,
						MQSCCommandEvent.TERMINAL_EVENT, "",
						mqscScanner.getOffset(), partStart, partLength,
						partIndex);
			}
			break;
		case ' ':
		case '\u0009':
		case '\u000B':
		case '\u000C':
		case '\u001C':
		case '\u001D':
		case '\u001E':
		case '\u001F':
			if (currentState != INVALID_STATE) {
				printState(2, startPos);
				return fDefaultToken;
			}
			break;
		case '*':
			if (currentState != FOUND_PARAMETER_STATE
					&& currentState != FOUND_OBJECT_NAME_STATE) {
				printState(4, startPos);
				skipComment(scanner);
			}
			if (currentState == FOUND_OBJECT_NAME_STATE) {
				printState(42, startPos);
				while (c != ICharacterScanner.EOF && (char) c != ')') {
					if (((char) c == '+') || ((char) c == '-')) {
						skipContinuationCharacter(scanner, (char) c);
						c = scanner.read();
					}
					fBuffer.append((char) c);
					c = scanner.read();
				}
				scanner.unread();
				objectName = fBuffer.toString();
				fBuffer.setLength(0);
				return generateToken(fDefaultToken,
						MQSCCommandEvent.OBJECT_NAME_EVENT, objectName,
						startPos, partStart, partLength, partIndex);

			}
			if (currentState != INVALID_STATE) {
				printState(5, startPos);
				return fDefaultToken;
			}
			break;
		case '+':
		case '-':
			printState(6, startPos);
			lookAhead = (char) scanner.read();
			while (lookAhead == ' ')
				lookAhead = (char) scanner.read();

			if (lookAhead == '\r') {
				if (currentState != INVALID_STATE) {
					printState(7, startPos);
					lookAhead = (char) scanner.read();
					if (lookAhead != '\n')
						scanner.unread();
					return fDefaultToken;
				}
			} else if (lookAhead == '\n') {
				if (currentState != INVALID_STATE) {
					printState(8, startPos);
					return fDefaultToken;
				}
			} else {
				printState(9, startPos);
				scanner.unread();
				char[] newVal = { (char) c, lookAhead };
				String eventVal = new String(newVal);
				// currentState = TERMINAL_STATE;
				return generateToken(fInvalidObjectToken,
						MQSCCommandEvent.INVALID_EVENT, eventVal,
						mqscScanner.getOffset() - 1, partStart, partLength,
						partIndex);
			}
			break;
		case ')':
			parenCount--;
			if ((parenCount == 1 || parenCount == -1) && balancedParen) {
				lastParenOffset = mqscScanner.getOffset();
				balancedParen = false;
				badParen = ")";
			}
			if (currentState == FOUND_PARAMETER_STATE) {
				currentState = FOUND_OBJECT_STATE;
				printState(10, startPos);
				fBuffer.setLength(0);
				return fDefaultToken;
			} else if (currentState == FOUND_OBJECT_NAME_STATE) {
				currentState = FOUND_OBJECT_STATE;
				printState(11, startPos);
				fBuffer.setLength(0);
				return fDefaultToken;
			} else if (currentState != INVALID_STATE) {
				printState(12, startPos);
				fBuffer.setLength(0);
				return fDefaultToken;
			}
			break;
		case '(':
			if (currentState != INVALID_STATE) {
				printState(13, startPos);
				fBuffer.setLength(0);
				parenCount++;
				if ((parenCount == 2 || parenCount == 0) && balancedParen) {
					lastParenOffset = mqscScanner.getOffset();
					balancedParen = false;
					badParen = "(";
				}
				return fDefaultToken;
			}
			break;
		case '\'':
			printState(14, startPos);
			fBuffer.setLength(0);
			startPos = mqscScanner.getOffset();
			fBuffer.append((char) c);
			c = scanner.read();
			while (c != ICharacterScanner.EOF && c != '\'') {
				fBuffer.append((char) c);
				c = scanner.read();
				if (c == '\'') {
					lookAhead = (char) scanner.read();
					if (lookAhead == '\'') {
						c = scanner.read();
					} else {
						scanner.unread();
					}
				}
			}
		}
		if (currentState == INVALID_STATE) {
			currentState = INITIAL_STATE;
			printState(15, startPos);
			return Token.UNDEFINED;
		}
		// Handle regular characters
		if (fDetector.isWordStart((char) c)) {
			if (fColumn == UNDEFINED) {
				{
					// Begin State Machine
					printState(16, startPos);
					switch (currentState) {
					case FOUND_PARAMETER_STATE:
						while (c != ICharacterScanner.EOF && (char) c != ')') {
							if (((char) c == '+') || ((char) c == '-')) {
								skipContinuationCharacter(scanner, (char) c);
								c = scanner.read();
							}
							fBuffer.append((char) c);
							c = scanner.read();
						}
						scanner.unread();
						break;
					case FOUND_OBJECT_NAME_STATE:
						printState(17, startPos);
						while (c != ICharacterScanner.EOF && (char) c != ')') {
							if (((char) c == '+') || ((char) c == '-')) {
								skipContinuationCharacter(scanner, (char) c);
								c = scanner.read();
							}
							fBuffer.append((char) c);
							c = scanner.read();
						}
						scanner.unread();
						objectName = fBuffer.toString();
						fBuffer.setLength(0);
						break;
					case INVALID_STATE:
						currentState = INITIAL_STATE;
						printState(18, startPos);
						return Token.UNDEFINED;
					case TERMINAL_STATE:
						currentState = INITIAL_STATE;
						printState(19, startPos);
						break;
					default:
						printState(20, startPos);
						fBuffer.setLength(0);
						startPos = mqscScanner.getOffset() - 1;
						do {
							if (((char) c == '+') || ((char) c == '-')) {
								skipContinuationCharacter(scanner, (char) c);
							} else {
								fBuffer.append((char) c);
							}
							c = scanner.read();
						} while (c != ICharacterScanner.EOF
								&& fDetector.isWordPart((char) c) && c != '(');
						scanner.unread();
						break;
					}
					switch (currentState) {
					case INITIAL_STATE:
						printState(21, startPos);
						cmdName = fBuffer.toString();
						token = (IToken) fCommands.get(cmdName.toUpperCase());
						if (token != null) {
							currentState = FOUND_COMMAND_STATE;
							printState(22, startPos);
							return generateToken(token,
									MQSCCommandEvent.COMMAND_WORD_EVENT,
									fBuffer.toString(), startPos, partStart,
									partLength, partIndex);
						} else {
							currentState = INVALID_STATE;
							printState(23, startPos);
							// return Token.UNDEFINED;
							return generateToken(fInvalidObjectToken,
									MQSCCommandEvent.INVALID_EVENT,
									fBuffer.toString(), startPos, partStart,
									partLength, partIndex);
						}
					case FOUND_COMMAND_STATE:
						printState(24, startPos);
						objList = fObjects
								.get(fBuffer.toString().toUpperCase());
						if (objList != null) {
							// look ahead for (, if not found, look for another
							// parameter
							openParen = (char) scanner.read();
							while (Character.isWhitespace(openParen)
									|| openParen == '+' || openParen == '-'
									&& openParen != '\r' && openParen != '\n'
									&& openParen != ICharacterScanner.EOF) {
								if (openParen == '+' || openParen == '-') {
									skipContinuationCharacter(scanner,
											openParen);
								}
								openParen = (char) scanner.read();
							}
							scanner.unread();
							objectType = fBuffer.toString();
							fBuffer.setLength(0);
							if (openParen == '(') {
								currentState = FOUND_OBJECT_NAME_STATE;
								printState(25, startPos);
							} else {
								currentState = FOUND_OBJECT_STATE;
								printState(26, startPos);
							}
							return generateToken(fValidObjectToken,
									MQSCCommandEvent.OBJECT_VALUE_EVENT,
									objectType, startPos, partStart,
									partLength, partIndex);
						} else {
							currentState = INVALID_STATE;
							printState(27, startPos);
							// return fInvalidObjectToken;
							return generateToken(fInvalidObjectToken,
									MQSCCommandEvent.INVALID_EVENT,
									fBuffer.toString(),
									mqscScanner.getOffset(), partStart,
									partLength, partIndex);

						}
					case FOUND_OBJECT_STATE:
						printState(28, startPos);

						fParameters = (HashMap) fObjects.get(objectType
								.toUpperCase());
						if (fParameters == null) {
							return generateToken(fInvalidParmToken,
									MQSCCommandEvent.INVALID_EVENT, objectType,
									startPos, partStart, partLength, partIndex);
						}
						// look ahead for (, if not found, look for another
						// parameter
						openParen = (char) scanner.read();
						while ((Character.isWhitespace(openParen)
								|| openParen == '+' || openParen == '-')
								&& openParen != '\r'
								&& openParen != '\n'
								&& openParen != ICharacterScanner.EOF) {
							if (openParen == '+' || openParen == '-') {
								skipContinuationCharacter(scanner, openParen);
							}
							openParen = (char) scanner.read();
						}
						scanner.unread();
						if (openParen == '(') {
							currentState = FOUND_PARAMETER_STATE;
							printState(29, startPos);
						} else {
							currentState = FOUND_OBJECT_STATE;
							printState(30, startPos);
						}
						String paramName = fBuffer.toString().toUpperCase();
						ArrayList subTypes = MQSCLanguageConfigurator
								.getLanguageConfiguration()
								.getSubTypesForObject(cmdName.toUpperCase(),
										objectType.toUpperCase());
						if (subTypes != null
								&& subTypes.contains(paramName.toUpperCase())) {
							foundSubType = true;
							subType = " ";
						}

						// adjust to use subType value;
						if (subTypes != null) {
							if (subType == null)
								subType = " ";
							fParameters = (HashMap) (fParameters.get(subType
									.toUpperCase()));
							if (fParameters == null) {
								fParameters = (HashMap) fObjects.get(objectType
										.toUpperCase());
							}
						}
						token = (IToken) fParameters.get(fBuffer.toString()
								.toUpperCase());
						if (token != null) {
							if (foundSubType) {
								return generateToken(token,
										MQSCCommandEvent.SUBTYPE_NAME_EVENT,
										fBuffer.toString(), startPos,
										partStart, partLength, partIndex);
							} else {
								return generateToken(token,
										MQSCCommandEvent.PARAMETER_NAME_EVENT,
										fBuffer.toString(), startPos,
										partStart, partLength, partIndex);
							}
						} else {
							// currentState = INVALID_STATE;
							return generateToken(fInvalidParmToken,
									MQSCCommandEvent.INVALID_EVENT,
									fBuffer.toString(), startPos, partStart,
									partLength, partIndex);
						}
					case FOUND_OBJECT_NAME_STATE:
						printState(31, startPos);
						closeParen = (char) scanner.read();
						scanner.unread();
						if (closeParen == ')') {
							currentState = FOUND_OBJECT_STATE;
							printState(32, startPos);
							return generateToken(fDefaultToken,
									MQSCCommandEvent.OBJECT_NAME_EVENT,
									objectName, startPos, partStart,
									partLength, partIndex);
						} else {
							currentState = INVALID_STATE;
							printState(33, startPos);
							return generateToken(fInvalidObjectToken,
									MQSCCommandEvent.INVALID_EVENT, objectName,
									startPos, partStart, partLength, partIndex);
						}
					case FOUND_PARAMETER_STATE:
						printState(34, startPos);
						closeParen = (char) scanner.read();
						scanner.unread();

						if (closeParen == ')') {
							currentState = FOUND_OBJECT_STATE;
							if (foundSubType) {
								subType = fBuffer.toString().toUpperCase();
								subType = subType.replace('\'', ' ');
								subType = subType.trim();
								foundSubType = false;
								return generateToken(fDefaultToken,
										MQSCCommandEvent.SUBTYPE_VALUE_EVENT,
										fBuffer.toString(),
										mqscScanner.getOffset(), partStart,
										partLength, partIndex);
							} else {
								printState(35, startPos);
								return generateToken(fDefaultToken,
										MQSCCommandEvent.PARAMETER_VALUE_EVENT,
										fBuffer.toString(),
										mqscScanner.getOffset(), partStart,
										partLength, partIndex);
							}
						} else { // Maybe we found another parameter, instead;
							token = (IToken) fParameters
									.get(fBuffer.toString());
							if (token != null) {
								return generateToken(token,
										MQSCCommandEvent.PARAMETER_NAME_EVENT,
										fBuffer.toString(), startPos,
										partStart, partLength, partIndex);
							} else {
								// currentState = INVALID_STATE;
								printState(36, startPos);
								// return Token.UNDEFINED;
								return generateToken(fInvalidObjectToken,
										MQSCCommandEvent.INVALID_EVENT,
										fBuffer.toString(), startPos,
										partStart, partLength, partIndex);
							}
						}
					case TERMINAL_STATE:
						printState(37, startPos);
						break;
					default:
						printState(38, startPos);
						break;
					}
					if (fDefaultToken.isUndefined())
						unreadBuffer(scanner);
					printState(39, startPos);
					return fDefaultToken;
				}
			}
			printState(40, startPos);
			return fDefaultToken;
		}
		currentState = INITIAL_STATE;
		printState(41, startPos);
		return Token.UNDEFINED;
	}

	private void skipComment(ICharacterScanner scanner) {
		// char c;
		char lookAhead;
		lookAhead = (char) scanner.read();
		while ((lookAhead != '\r') && (lookAhead != '\n')
				&& lookAhead != ICharacterScanner.EOF
				&& ((int) lookAhead != 65535)) {
			lookAhead = (char) scanner.read();
		}
		if (lookAhead == '\r') {
			lookAhead = (char) scanner.read();
			if (lookAhead != '\n')
				scanner.unread();
		}
	}

	private void skipContinuationCharacter(ICharacterScanner scanner,
			char contChar) {
		// char c;
		char lookAhead;
		if (contChar == '-') {
			lookAhead = (char) scanner.read();
			if ((lookAhead != ' ') && (lookAhead != '\r')
					&& (lookAhead != '\n')) {
				scanner.unread();
				return;
			}
			while (lookAhead == ' ') {
				lookAhead = (char) scanner.read();
			}
			while ((lookAhead == '\r') || (lookAhead == '\n')) {
				lookAhead = (char) scanner.read();
				if (lookAhead == '*') {
					skipComment(scanner);
					lookAhead = (char) scanner.read();
				}
			}
			scanner.unread();
		} else if (contChar == '+') {
			lookAhead = (char) scanner.read();
			if ((lookAhead != ' ') && (lookAhead != '\r')
					&& (lookAhead != '\n')) {
				scanner.unread();
				return;
			}
			while (lookAhead == ' ') {
				lookAhead = (char) scanner.read();
			}
			while ((lookAhead == '\r') || (lookAhead == '\n')
					|| (lookAhead == ' ')) {
				lookAhead = (char) scanner.read();
				if (lookAhead == '*') {
					skipComment(scanner);
					lookAhead = (char) scanner.read();
				}

			}
			scanner.unread();
		}
	}

	private IToken generateToken(IToken myToken, int EventType,
			String EventValue, int startPos, int partitionStart,
			int partitionLength, int partitionIndex) {
		MQSCCommandEvent myEvent;
		int tokLength;
		try {
			tokLength = EventValue.length();
			if (myToken instanceof MQSCToken) {
				myEvent = new MQSCCommandEvent(this, EventType, EventValue,
						startPos, tokLength, partitionStart, partitionLength,
						partitionIndex);

				validateToken(myToken, myEvent);
				((MQSCToken) myToken).setEvent(myEvent);
				fNotifier.fireMQSCEventFound(myEvent);
			}
			return myToken;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void validateToken(IToken _myToken, MQSCCommandEvent myEvent) {
		IToken myToken = _myToken;
		switch (myEvent.getType()) {
		case MQSCCommandEvent.TERMINAL_EVENT:
			if (parenCount != 0) {
				myToken = fInvalidParmToken;
				myEvent.setEventType(MQSCCommandEvent.BAD_PAREN_EVENT);
				myEvent.setEventValue(badParen);
				myEvent.setOffset(lastParenOffset);
				return;
			}
			break;
		default:
			break;
		}
	}

	protected void unreadBuffer(ICharacterScanner scanner) {
		for (int i = fBuffer.length() - 1; i >= 0; i--) {
			scanner.unread();
		}
	}

	public void setColumnConstraint(int _column) {
		int column = _column;
		if (column < 0)
			column = UNDEFINED;
		fColumn = column;
	}

	public void setNotifier(IMQSCCommandEventNotifier notifier) {
		fNotifier = notifier;
	}

	private void printState(int point, int offset) {
		if (MQSCEditorPlugin.getDefault().isDebugging()) {
			switch (currentState) {
			case INITIAL_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : INITIAL STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			case INVALID_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : INVALID STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			case FOUND_COMMAND_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : FOUND COMMAND STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			case FOUND_OBJECT_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : FOUND OBJECT STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			case FOUND_OBJECT_NAME_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : FOUND OBJECT NAME STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			case FOUND_PARAMETER_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : FOUND PARAMETER STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			case TERMINAL_STATE:
				MQSCEditorPlugin
						.getDefault()
						.getLog()
						.log(new Status(IStatus.INFO, MQSCEditor.PLUGIN_ID, 0,
								"MQSCStatefuleRule.evaluate : TERMINAL STATE"
										+ " at codepoint " + point + " offset "
										+ offset, null));
				break;
			default:
				break;
			}
		}
	}
}
