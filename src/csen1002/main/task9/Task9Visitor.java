// Generated from /media/mohamedshetewi/07D07BE67A0F25DC/Projects/Compilers-Lab/grammars/Task9.g4 by ANTLR 4.12.0
package csen1002.main.task9;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Task9Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Task9Visitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Task9Parser#s}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitS(Task9Parser.SContext ctx);
	/**
	 * Visit a parse tree produced by {@link Task9Parser#a}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitA(Task9Parser.AContext ctx);
	/**
	 * Visit a parse tree produced by {@link Task9Parser#b}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitB(Task9Parser.BContext ctx);
	/**
	 * Visit a parse tree produced by {@link Task9Parser#c}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitC(Task9Parser.CContext ctx);
}