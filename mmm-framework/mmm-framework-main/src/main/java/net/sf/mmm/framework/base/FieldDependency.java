package net.sf.mmm.framework.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.framework.api.ComponentManagerIF;

/**
 * This inner class is the implementation of {@link AbstractDependency} for the
 * {@link #getInjectionType() type}
 * {@link net.sf.mmm.framework.api.DependencyIF.Type#FIELD}.
 * 
 * @param <S>
 *        is the templated type of the {@link #getSpecification()}.
 */
public final class FieldDependency<S> extends AbstractDependency<S> {

    /** @see #getInjectionField() */
    private final Field field;

    /**
     * The constructor.
     * 
     * @param injectionField
     *        is the {@link #getInjectionField() field} where to inject the
     *        dependency.
     */
    @SuppressWarnings("all")
    public FieldDependency(Field injectionField) {

        this(injectionField, (Class<S>) injectionField.getType());
    }

    /**
     * The constructor.
     * 
     * @param injectionField
     *        is the {@link #getInjectionField() field} where to inject the
     *        dependency.
     * @param specification
     *        is the {@link #getSpecification() specification} of the
     *        dependency.
     */
    public FieldDependency(Field injectionField, Class<S> specification) {

        this(injectionField, specification, ComponentManagerIF.DEFAULT_INSTANCE_ID);
    }

    /**
     * The constructor.
     * 
     * @param injectionField
     *        is the {@link #getInjectionField() field} where to inject the
     *        dependency.
     * @param specification
     *        is the {@link #getSpecification() specification} of the
     *        dependency.
     * @param instanceId
     *        is the
     *        {@link ComponentInstanceContainer#getInstanceId() instance-ID} of
     *        the dependency.
     */
    public FieldDependency(Field injectionField, Class<S> specification, String instanceId) {

        super(specification, instanceId);
        this.field = injectionField;
        if (!this.field.getType().isAssignableFrom(specification)) {
            throw new IllegalArgumentException("Field '" + injectionField.getName()
                    + "' has type '" + injectionField.getType() + "' which is NOT assignable to '"
                    + specification + "'!");
        }
    }

    /**
     * @see net.sf.mmm.framework.api.DependencyIF#getInjectionField()
     * {@inheritDoc}
     */
    public Field getInjectionField() {

        return this.field;
    }

    /**
     * @see net.sf.mmm.framework.api.DependencyIF#getInjectionMethod()
     * {@inheritDoc}
     */
    public Method getInjectionMethod() {

        return null;
    }

    /**
     * @see net.sf.mmm.framework.api.DependencyIF#getInjectionType()
     * {@inheritDoc}
     */
    public Type getInjectionType() {

        return Type.FIELD;
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractDependency#getInjectionTargetDescription()
     * {@inheritDoc}
     */
    @Override
    public String getInjectionTargetDescription() {

        return this.field.getName();
    }

}