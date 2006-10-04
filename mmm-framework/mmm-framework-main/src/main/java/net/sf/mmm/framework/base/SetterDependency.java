package net.sf.mmm.framework.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.mmm.framework.api.ComponentManagerIF;

/**
 * This inner class is the implementation of {@link AbstractDependency} for the
 * {@link #getInjectionType() type}
 * {@link net.sf.mmm.framework.api.DependencyIF.Type#SETTER}.
 * 
 * @param <S>
 *        is the templated type of the {@link #getSpecification()}.
 */
public final class SetterDependency<S> extends AbstractDependency<S> {

    /** @see #getInjectionField() */
    private final Method method;

    /**
     * The constructor.
     * 
     * @param setter
     *        is the {@link #getInjectionMethod() setter} where to inject the
     *        dependency.
     */
    @SuppressWarnings("all")
    public SetterDependency(Method setter) {

        this(setter, (Class<S>) getSetterArgument(setter));
    }

    /**
     * The constructor.
     * 
     * @param setter
     *        is the {@link #getInjectionMethod() setter} where to inject the
     *        dependency.
     * @param specification
     *        is the {@link #getSpecification() specification} of the
     *        dependency.
     */
    public SetterDependency(Method setter, Class<S> specification) {

        this(setter, specification, ComponentManagerIF.DEFAULT_INSTANCE_ID);
    }

    /**
     * The constructor.
     * 
     * @param setter
     *        is the {@link #getInjectionMethod() setter} where to inject the
     *        dependency.
     * @param specification
     *        is the {@link #getSpecification() specification} of the
     *        dependency.
     * @param instanceId
     *        is the
     *        {@link ComponentInstanceContainer#getInstanceId() instance-ID} of
     *        the dependency.
     */
    public SetterDependency(Method setter, Class<S> specification, String instanceId) {

        super(specification, instanceId);
        this.method = setter;
        Class<?> arg = getSetterArgument(setter);
        if (!arg.isAssignableFrom(specification)) {
            throw new IllegalArgumentException("Setter '" + setter.getName()
                    + "' has argument type '" + arg + "' which is NOT assignable to '"
                    + specification + "'!");
        }
    }

    /**
     * This method gets the type of the single argument of the given setter
     * method.
     * 
     * @param setter
     * @return the argument of the given setter method.
     */
    private static Class<?> getSetterArgument(Method setter) {

        Class<?>[] types = setter.getParameterTypes();
        if (types.length != 1) {
            throw new IllegalArgumentException("Setter " + setter.getName()
                    + " must take a single argument!");
        }
        return types[0];
    }

    /**
     * @see net.sf.mmm.framework.api.DependencyIF#getInjectionField()
     * 
     */
    public Field getInjectionField() {

        return null;
    }

    /**
     * @see net.sf.mmm.framework.api.DependencyIF#getInjectionMethod()
     * 
     */
    public Method getInjectionMethod() {

        return this.method;
    }

    /**
     * @see net.sf.mmm.framework.api.DependencyIF#getInjectionType()
     * 
     */
    public Type getInjectionType() {

        return Type.SETTER;
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractDependency#getInjectionTargetDescription()
     * 
     */
    @Override
    public String getInjectionTargetDescription() {

        return this.method.getName();
    }

}