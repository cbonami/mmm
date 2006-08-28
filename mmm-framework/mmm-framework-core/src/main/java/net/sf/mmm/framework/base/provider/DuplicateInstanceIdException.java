/* $Id: DuplicateInstanceIdException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;

/**
 * A {@link DuplicateInstanceIdException} is thrown if the
 * {@link net.sf.mmm.framework.api.ComponentInstanceContainerIF#getInstanceId() instance-ID}
 * is NOT unique and a duplicate was about to be created. This indicates a bug
 * in the according {@link net.sf.mmm.framework.api.ComponentProviderIF}
 * implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DuplicateInstanceIdException extends ComponentException {

    /** UID for serialization */
    private static final long serialVersionUID = 8793855410381174579L;

    /**
     * The constructor.
     * 
     * @param instanceId
     *        is the instance-ID that is already in use.
     * @param descriptor
     *        is the descriptor of the component.
     */
    public DuplicateInstanceIdException(String instanceId, ComponentDescriptorIF descriptor) {

        super(NlsResourceBundle.ERR_COMPONENT_DUPLICATE_INSTANCE_ID, instanceId, descriptor
                .getSpecification());
    }

}
