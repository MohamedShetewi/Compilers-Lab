/**
 * Write your info here
 *
 * @name Mohamed Shetewi
 * @id 46-13908
 * @labNumber 21
 */

grammar Task8;

/**
 * This rule is to check your grammar using "ANTLR Preview"
 */
test: (IF | ELSE | COMP | NUM | ID | LP | RP | LIT)+ EOF; //Replace the non-fragment lexer rules here

// Write all the necessary lexer rules and fragment lexer rules here

IF options {caseInsensitive=true;}: 'if';
ELSE options {caseInsensitive=true;} : 'else';
COMP : '>'| '<' | '>=' | '<=' | '==' | '!=';
NUM: DIGIT+ DECIMAL? EXP?;
ID: (UNDERSCORE | CHAR) (DIGIT | CHAR | UNDERSCORE)*;
WS: [ \r\t\n]+ -> skip;
LP:'(';
RP:')';
LIT: '"' (ASCII | SPECIAL_ASCII)* '"';

fragment DIGIT: [0-9];
fragment CHAR: [a-zA-Z];
fragment UNDERSCORE: '_';
fragment DECIMAL: '.' DIGIT+;
fragment EXP: ('e'|'E') ('+' | '-')? DIGIT+;
fragment ASCII: ~('"'| '\\');
fragment SPECIAL_ASCII: ('\\"' | '\\\\');