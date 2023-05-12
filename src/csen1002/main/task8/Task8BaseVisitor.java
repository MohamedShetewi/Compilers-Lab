// Generated from /media/mohamedshetewi/07D07BE67A0F25DC/Projects/Compilers-Lab/grammars/Task8.g4 by ANTLR 4.12.0
package csen1002.main.task8;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link Task8Visitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
@SuppressWarnings("CheckReturnValue")
public class Task8BaseVisitor<T> extends AbstractParseTreeVisitor<T> implements Task8Visitor<T> {
	/**
	 * {@inheritDoc}
	 *
	 * <p>The default implementation returns the result of calling
	 * {@link #visitChildren} on {@code ctx}.</p>
	 */
	@Override public T visitTest(Task8Parser.TestContext ctx) { return visitChildren(ctx); }
}