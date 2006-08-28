/* $ Id: $ */
package net.sf.mmm.value.api;

import net.sf.mmm.value.CoreNlsResourceBundle;

/**
 * This is the exception thrown if a required value was not set.
 * 
 * @see net.sf.mmm.value.api.GenericValueIF#getString()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueNotSetException extends ValueException {

    /** uid for serialization */
    private static final long serialVersionUID = -8722582228766326020L;

    /**
     * The constructor.
     * 
     * @param genericValue
     *        is the required value that is NOT
     *        {@link GenericValueIF#hasValue() set}.
     */
    public ValueNotSetException(GenericValueIF genericValue) {

        super(CoreNlsResourceBundle.ERR_VALUE_NOT_SET, genericValue);
    }

    /**
     * The constructor.
     * 
     * @param valueName
     *        is the name of the required value that is not set.
     */
    public ValueNotSetException(String valueName) {

        super(CoreNlsResourceBundle.ERR_VALUE_NOT_SET, valueName);
    }

}
