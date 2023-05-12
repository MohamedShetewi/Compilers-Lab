// Generated from /media/mohamedshetewi/07D07BE67A0F25DC/Projects/Compilers-Lab/grammars/Task8.g4 by ANTLR 4.12.0
package csen1002.main.task8;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class Task8Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, COMP=3, NUM=4, ID=5, WS=6, LP=7, RP=8, LIT=9;
	public static final int
		RULE_test = 0;
	private static String[] makeRuleNames() {
		return new String[] {
			"test"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "COMP", "NUM", "ID", "WS", "LP", "RP", "LIT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Task8.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public Task8Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TestContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(Task8Parser.EOF, 0); }
		public List<TerminalNode> IF() { return getTokens(Task8Parser.IF); }
		public TerminalNode IF(int i) {
			return getToken(Task8Parser.IF, i);
		}
		public List<TerminalNode> ELSE() { return getTokens(Task8Parser.ELSE); }
		public TerminalNode ELSE(int i) {
			return getToken(Task8Parser.ELSE, i);
		}
		public List<TerminalNode> COMP() { return getTokens(Task8Parser.COMP); }
		public TerminalNode COMP(int i) {
			return getToken(Task8Parser.COMP, i);
		}
		public List<TerminalNode> NUM() { return getTokens(Task8Parser.NUM); }
		public TerminalNode NUM(int i) {
			return getToken(Task8Parser.NUM, i);
		}
		public List<TerminalNode> ID() { return getTokens(Task8Parser.ID); }
		public TerminalNode ID(int i) {
			return getToken(Task8Parser.ID, i);
		}
		public List<TerminalNode> LP() { return getTokens(Task8Parser.LP); }
		public TerminalNode LP(int i) {
			return getToken(Task8Parser.LP, i);
		}
		public List<TerminalNode> RP() { return getTokens(Task8Parser.RP); }
		public TerminalNode RP(int i) {
			return getToken(Task8Parser.RP, i);
		}
		public List<TerminalNode> LIT() { return getTokens(Task8Parser.LIT); }
		public TerminalNode LIT(int i) {
			return getToken(Task8Parser.LIT, i);
		}
		public TestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof Task8Listener ) ((Task8Listener)listener).enterTest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof Task8Listener ) ((Task8Listener)listener).exitTest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof Task8Visitor ) return ((Task8Visitor<? extends T>)visitor).visitTest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestContext test() throws RecognitionException {
		TestContext _localctx = new TestContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_test);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(3); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(2);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 958L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
				}
				setState(5); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 958L) != 0) );
			setState(7);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\t\n\u0002\u0000\u0007\u0000\u0001\u0000\u0004\u0000\u0004"+
		"\b\u0000\u000b\u0000\f\u0000\u0005\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0000\u0000\u0001\u0000\u0000\u0001\u0002\u0000\u0001\u0005\u0007\t\t"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0002\u0004\u0007\u0000\u0000\u0000"+
		"\u0003\u0002\u0001\u0000\u0000\u0000\u0004\u0005\u0001\u0000\u0000\u0000"+
		"\u0005\u0003\u0001\u0000\u0000\u0000\u0005\u0006\u0001\u0000\u0000\u0000"+
		"\u0006\u0007\u0001\u0000\u0000\u0000\u0007\b\u0005\u0000\u0000\u0001\b"+
		"\u0001\u0001\u0000\u0000\u0000\u0001\u0005";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}