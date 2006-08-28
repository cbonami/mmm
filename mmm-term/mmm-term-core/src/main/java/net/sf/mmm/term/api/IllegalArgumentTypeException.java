/* $Id: IllegalArgumentTypeException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.api;

import net.sf.mmm.term.NlsResourceBundle;
import net.sf.mmm.util.reflect.Arguments;
import net.sf.mmm.util.reflect.Signature;

/**
 * This exception represents an error that occured during the calculation of a
 * function. There can be various reasons for a calculation exception e.g. zero
 * divide, or incompatible types.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalArgumentTypeException extends CalculationException {

    /** uid for serialization */
    private static final long serialVersionUID = -8654301245551735738L;

    /**
     * The constructor.
     * 
     * @param function
     *        the function that declared the signature as illegal.
     * @param signature
     *        is the signature that was illegal.
     */
    public IllegalArgumentTypeException(FunctionIF function, Signature signature) {

        super(NlsResourceBundle.ERR_ILLEGAL_SIGNATURE, function, signature);
    }

    /**
     * The constructor.
     * 
     * @param function
     *        the function that declared the argument as illegal.
     * @param arguments
     *        are the arguments that were illegal.
     */
    public IllegalArgumentTypeException(FunctionIF function, Arguments arguments) {

        super(NlsResourceBundle.ERR_ILLEGAL_ARGUMENTS, function, arguments);
    }

    /**
     * The constructor.
     * 
     * @param function
     *        the function that declared the argument as illegal.
     * @param arguments
     *        are the arguments that were illegal.
     * @param nested
     *        is the throwable that {@link Throwable#getCause() caused} this
     *        exception.
     */
    public IllegalArgumentTypeException(FunctionIF function, Arguments arguments, Throwable nested) {

        super(nested, NlsResourceBundle.ERR_ILLEGAL_ARGUMENTS, function, arguments);
    }

    /**
     * This method gets the function that threw this error and declared the
     * argument(s) it was given as calculation input as illegal.
     * 
     * @return the function.
     */
    public FunctionIF getFunction() {

        return ((FunctionIF) getNlsMessage().getArgument(0));
    }

}