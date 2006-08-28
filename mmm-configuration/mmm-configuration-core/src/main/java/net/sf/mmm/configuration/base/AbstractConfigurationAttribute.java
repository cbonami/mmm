/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.Iterator;

import net.sf.mmm.configuration.api.ConfigurationException;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.ConfigurationIF} interface for the
 * {@link #getType() type} {@link Type#ATTRIBUTE attribute}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationAttribute extends BasicConfiguration {

    /**
     * The constructor.
     * 
     * @param parentConfiguration
     *        is the parent configuration.
     */
    public AbstractConfigurationAttribute(AbstractConfiguration parentConfiguration) {

        super(parentConfiguration);
    }

    /**
     * @see net.sf.mmm.configuration.api.ConfigurationIF#getType()
     * {@inheritDoc}
     */
    public Type getType() {

        return Type.ATTRIBUTE;
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChild(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public AbstractConfiguration getChild(String name, String namespace)
            throws IllegalArgumentException {

        return null;
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(net.sf.mmm.configuration.api.ConfigurationIF.Type)
     * {@inheritDoc}
     */
    public Iterator<AbstractConfiguration> getChildren(Type childType) {

        return EmptyConfigurationIterator.getInstance();
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#getChildren(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    @Override
    public Iterator<AbstractConfiguration> getChildren(String name, String namespaceUri) {

        return EmptyConfigurationIterator.getInstance();
    }

    /**
     * @see AbstractConfiguration#doCreateChild(String, String)
     * {@inheritDoc}
     */
    @Override
    AbstractConfiguration doCreateChild(String name, String namespace)
            throws ConfigurationException {

        throw new ConfigurationException("Attribute cannot have children!");
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#addChild(net.sf.mmm.configuration.base.AbstractConfiguration)
     * {@inheritDoc}
     */
    @Override
    protected void addChild(AbstractConfiguration child) {

        throw new ConfigurationException("Attribute cannot have children!");
    }

    /**
     * @see net.sf.mmm.configuration.base.AbstractConfiguration#removeChild(net.sf.mmm.configuration.base.AbstractConfiguration)
     * {@inheritDoc}
     */
    @Override
    protected void removeChild(AbstractConfiguration child) {

        throw new ConfigurationException("Attribute cannot have children!");
    }

}
