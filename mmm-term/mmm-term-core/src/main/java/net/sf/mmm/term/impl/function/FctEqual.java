/* $Id: FctEqual.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements the binary function that tests if two
 * arguments equal each other (according to
 * {@link java.lang.Object#equals(java.lang.Object)}).<br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctEqual {

    /**
     * the
     * {@link net.sf.mmm.term.api.FunctionIF#getOperatorSymbol() "operator symbol"}
     */
    public static final String SYMBOL = "==";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getName() name} of this
     * function
     */
    public static final String NAME = "equal";

    /**
     * the {@link net.sf.mmm.term.api.FunctionIF#getOperatorPriority() priority}
     * of this function
     */
    public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

    /**
     * The constructor.
     */
    private FctEqual() {

        super();
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return <code>true</code> iff both arguments are equal.
     */
    public static Boolean equal(Number argument1, Number argument2) {

        boolean result;
        if (argument1 == null) {
            result = (argument2 == null);
        } else {
            result = (argument1.doubleValue() == argument2.doubleValue());
        }
        return Boolean.valueOf(result);
    }

    /**
     * The function implementation for the given signature.
     * 
     * @param argument1
     *        is the first argument.
     * @param argument2
     *        is the second argument.
     * @return <code>true</code> if both arguments are equal,
     *         <code>false</code> .
     */
    public static Boolean equal(Object argument1, Object argument2) {

        boolean result;
        if (argument1 == null) {
            result = (argument2 == null);
        } else {
            result = argument1.equals(argument2);
        }
        return Boolean.valueOf(result);
    }

}