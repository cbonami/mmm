/* $Id$ */
package net.sf.mmm.framework.api;

/**
 * A {@link ComponentException} is thrown if a
 * {@link ComponentProviderIF component} itself or reflecting on a component
 * caused an error.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentException extends IocException {

    /** UID for serialization */
    private static final long serialVersionUID = -3274177722975395418L;

    /**
     * @see IocException#IocException(String, Object[])
     * {@inheritDoc}
     */
    public ComponentException(String internaitionalizedMessage, Object... arguments) {

        super(internaitionalizedMessage, arguments);
    }

    /**
     * @see IocException#IocException(Throwable, String, Object[])
     * {@inheritDoc}
     */
    public ComponentException(Throwable nested, String internaitionalizedMessage,
            Object... arguments) {

        super(nested, internaitionalizedMessage, arguments);
    }

}
