// Generated from /media/mohamedshetewi/07D07BE67A0F25DC/Projects/Compilers-Lab/grammars/Task8.g4 by ANTLR 4.12.0
package csen1002.main.task8;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class Task8Lexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, COMP=3, NUM=4, ID=5, WS=6, LP=7, RP=8, LIT=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"IF", "ELSE", "COMP", "NUM", "ID", "WS", "LP", "RP", "LIT", "DIGIT", 
			"CHAR", "UNDERSCORE", "DECIMAL", "EXP", "ASCII", "SPECIAL_ASCII"
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


	public Task8Lexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Task8.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\t}\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0003\u00023\b\u0002\u0001\u0003\u0004\u00036\b\u0003\u000b\u0003"+
		"\f\u00037\u0001\u0003\u0003\u0003;\b\u0003\u0001\u0003\u0003\u0003>\b"+
		"\u0003\u0001\u0004\u0001\u0004\u0003\u0004B\b\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0005\u0004G\b\u0004\n\u0004\f\u0004J\t\u0004\u0001"+
		"\u0005\u0004\u0005M\b\u0005\u000b\u0005\f\u0005N\u0001\u0005\u0001\u0005"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0005\bZ\b\b\n\b\f\b]\t\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0004\fi\b\f\u000b\f\f\fj\u0001"+
		"\r\u0001\r\u0003\ro\b\r\u0001\r\u0004\rr\b\r\u000b\r\f\rs\u0001\u000e"+
		"\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f"+
		"|\b\u000f\u0000\u0000\u0010\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\u0000\u0015\u0000"+
		"\u0017\u0000\u0019\u0000\u001b\u0000\u001d\u0000\u001f\u0000\u0001\u0000"+
		"\u000b\u0002\u0000IIii\u0002\u0000FFff\u0002\u0000EEee\u0002\u0000LLl"+
		"l\u0002\u0000SSss\u0002\u0000<<>>\u0003\u0000\t\n\r\r  \u0001\u000009"+
		"\u0002\u0000AZaz\u0002\u0000++--\u0002\u0000\"\"\\\\\u0087\u0000\u0001"+
		"\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005"+
		"\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001"+
		"\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000"+
		"\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000"+
		"\u0000\u0000\u0001!\u0001\u0000\u0000\u0000\u0003$\u0001\u0000\u0000\u0000"+
		"\u00052\u0001\u0000\u0000\u0000\u00075\u0001\u0000\u0000\u0000\tA\u0001"+
		"\u0000\u0000\u0000\u000bL\u0001\u0000\u0000\u0000\rR\u0001\u0000\u0000"+
		"\u0000\u000fT\u0001\u0000\u0000\u0000\u0011V\u0001\u0000\u0000\u0000\u0013"+
		"`\u0001\u0000\u0000\u0000\u0015b\u0001\u0000\u0000\u0000\u0017d\u0001"+
		"\u0000\u0000\u0000\u0019f\u0001\u0000\u0000\u0000\u001bl\u0001\u0000\u0000"+
		"\u0000\u001du\u0001\u0000\u0000\u0000\u001f{\u0001\u0000\u0000\u0000!"+
		"\"\u0007\u0000\u0000\u0000\"#\u0007\u0001\u0000\u0000#\u0002\u0001\u0000"+
		"\u0000\u0000$%\u0007\u0002\u0000\u0000%&\u0007\u0003\u0000\u0000&\'\u0007"+
		"\u0004\u0000\u0000\'(\u0007\u0002\u0000\u0000(\u0004\u0001\u0000\u0000"+
		"\u0000)3\u0007\u0005\u0000\u0000*+\u0005>\u0000\u0000+3\u0005=\u0000\u0000"+
		",-\u0005<\u0000\u0000-3\u0005=\u0000\u0000./\u0005=\u0000\u0000/3\u0005"+
		"=\u0000\u000001\u0005!\u0000\u000013\u0005=\u0000\u00002)\u0001\u0000"+
		"\u0000\u00002*\u0001\u0000\u0000\u00002,\u0001\u0000\u0000\u00002.\u0001"+
		"\u0000\u0000\u000020\u0001\u0000\u0000\u00003\u0006\u0001\u0000\u0000"+
		"\u000046\u0003\u0013\t\u000054\u0001\u0000\u0000\u000067\u0001\u0000\u0000"+
		"\u000075\u0001\u0000\u0000\u000078\u0001\u0000\u0000\u00008:\u0001\u0000"+
		"\u0000\u00009;\u0003\u0019\f\u0000:9\u0001\u0000\u0000\u0000:;\u0001\u0000"+
		"\u0000\u0000;=\u0001\u0000\u0000\u0000<>\u0003\u001b\r\u0000=<\u0001\u0000"+
		"\u0000\u0000=>\u0001\u0000\u0000\u0000>\b\u0001\u0000\u0000\u0000?B\u0003"+
		"\u0017\u000b\u0000@B\u0003\u0015\n\u0000A?\u0001\u0000\u0000\u0000A@\u0001"+
		"\u0000\u0000\u0000BH\u0001\u0000\u0000\u0000CG\u0003\u0013\t\u0000DG\u0003"+
		"\u0015\n\u0000EG\u0003\u0017\u000b\u0000FC\u0001\u0000\u0000\u0000FD\u0001"+
		"\u0000\u0000\u0000FE\u0001\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000"+
		"HF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000I\n\u0001\u0000\u0000"+
		"\u0000JH\u0001\u0000\u0000\u0000KM\u0007\u0006\u0000\u0000LK\u0001\u0000"+
		"\u0000\u0000MN\u0001\u0000\u0000\u0000NL\u0001\u0000\u0000\u0000NO\u0001"+
		"\u0000\u0000\u0000OP\u0001\u0000\u0000\u0000PQ\u0006\u0005\u0000\u0000"+
		"Q\f\u0001\u0000\u0000\u0000RS\u0005(\u0000\u0000S\u000e\u0001\u0000\u0000"+
		"\u0000TU\u0005)\u0000\u0000U\u0010\u0001\u0000\u0000\u0000V[\u0005\"\u0000"+
		"\u0000WZ\u0003\u001d\u000e\u0000XZ\u0003\u001f\u000f\u0000YW\u0001\u0000"+
		"\u0000\u0000YX\u0001\u0000\u0000\u0000Z]\u0001\u0000\u0000\u0000[Y\u0001"+
		"\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\^\u0001\u0000\u0000\u0000"+
		"][\u0001\u0000\u0000\u0000^_\u0005\"\u0000\u0000_\u0012\u0001\u0000\u0000"+
		"\u0000`a\u0007\u0007\u0000\u0000a\u0014\u0001\u0000\u0000\u0000bc\u0007"+
		"\b\u0000\u0000c\u0016\u0001\u0000\u0000\u0000de\u0005_\u0000\u0000e\u0018"+
		"\u0001\u0000\u0000\u0000fh\u0005.\u0000\u0000gi\u0003\u0013\t\u0000hg"+
		"\u0001\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000"+
		"\u0000jk\u0001\u0000\u0000\u0000k\u001a\u0001\u0000\u0000\u0000ln\u0007"+
		"\u0002\u0000\u0000mo\u0007\t\u0000\u0000nm\u0001\u0000\u0000\u0000no\u0001"+
		"\u0000\u0000\u0000oq\u0001\u0000\u0000\u0000pr\u0003\u0013\t\u0000qp\u0001"+
		"\u0000\u0000\u0000rs\u0001\u0000\u0000\u0000sq\u0001\u0000\u0000\u0000"+
		"st\u0001\u0000\u0000\u0000t\u001c\u0001\u0000\u0000\u0000uv\b\n\u0000"+
		"\u0000v\u001e\u0001\u0000\u0000\u0000wx\u0005\\\u0000\u0000x|\u0005\""+
		"\u0000\u0000yz\u0005\\\u0000\u0000z|\u0005\\\u0000\u0000{w\u0001\u0000"+
		"\u0000\u0000{y\u0001\u0000\u0000\u0000| \u0001\u0000\u0000\u0000\u000f"+
		"\u000027:=AFHNY[jns{\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}