/* $Id$ */
package net.sf.mmm.term.base;

import net.sf.mmm.term.api.OperatorPriority;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the abstract base implementation of a unary
 * {@link net.sf.mmm.term.api.Function function}. This means that the function
 * takes exactly <code>1</code> argument.
 * 
 * @see net.sf.mmm.term.api.Function#calculate(net.sf.mmm.context.api.Context,
 *      net.sf.mmm.term.api.Term...)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UnaryFunction extends BasicFunction {

  /**
   * The constructor.
   */
  public UnaryFunction() {

    super();
  }

  /**
   * @see net.sf.mmm.term.api.Function#getOperatorPriority()
   */
  @Override
  public OperatorPriority getOperatorPriority() {

    return OperatorPriority.MAXIMUM;
  }

  /**
   * @see net.sf.mmm.term.api.Function#getMinimumArgumentCount()
   */
  public int getMinimumArgumentCount() {

    return 1;
  }

  /**
   * @see net.sf.mmm.term.api.Function#getMaximumArgumentCount()
   */
  public int getMaximumArgumentCount() {

    return 1;
  }

  /**
   * @see net.sf.mmm.term.base.BasicFunction#calculate(Object)
   */
  @Override
  public abstract Object calculate(Object argument) throws ValueException;

}
