/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;

import net.sf.mmm.framework.api.Dependency.Type;

/**
 * This is the interface for the descriptor of a
 * {@link ComponentProvider component}. Such object explains a generic
 * {@link ComponentProvider provider} how to manage a
 * {@link ExtendedComponentInstanceContainer#getPrivateInstance() component instance}.
 * 
 * @param <S>
 *        is the component {@link #getSpecification() specification}
 * @param <I>
 *        is the component {@link #getImplementation() implementation}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ExtendedComponentDescriptor<S, I extends S> extends ComponentDescriptor<S> {

  /** an empty lifecycle method array */
  LifecycleMethod[] NO_LIFECYCLE = new LifecycleMethod[0];

  /** an empty dependency array */
  Dependency<?>[] NO_DEPENDENCY = new Dependency<?>[0];

  /**
   * This is the {@link #getInstantiationPolicy() instantiation policy} for a
   * singleton-style component. It is also called a classic or "static"
   * singleton. For such component only a single instance will be created on the
   * first request and is shared within the application. This instance will live
   * until the owning {@link IocContainer IoC container} is
   * {@link MutableIocContainer#stop() stopped}.<br>
   * This policy should only be used if the
   * {@link #getImplementation() implementation} is thread-safe (or the
   * component is guaranteed not to be used concurrently).
   * 
   * @see javax.annotation.Resource#shareable()
   */
  public static final String INSTANTIATION_POLICY_SINGLETON = "singleton";

  /**
   * This is the {@link #getInstantiationPolicy() instantiation policy} for a
   * dynamic singleton component. For such component no more than one single
   * instance will be created. If it does NOT exist, it will be created as
   * requested. Further requests will result in the same instance that is shared
   * within the application. If all requests have been released, the shared
   * instance will be shut down.<br>
   * This policy should only be used if the
   * {@link #getImplementation() implementation} is thread-safe (or the
   * component is guaranteed not to be used concurrently).
   * 
   * @see javax.annotation.Resource#shareable()
   */
  public static final String INSTANTIATION_POLICY_DYNAMIC_SINGLETON = "dynamic-singleton";

  /**
   * This is the {@link #getInstantiationPolicy() instantiation policy} for a
   * component that will NOT be shared. For each time the component is requested
   * (e.g. for {@link #getDependencies() injection}) a new instance of the
   * {@link #getImplementation() implementation} is created.
   * 
   * @see javax.annotation.Resource#shareable()
   */
  public static final String INSTANTIATION_POLICY_PER_REQUEST = "per-request";

  /**
   * This method typically gets a non-abstract class implementation and
   * fulfilling the {@link #getSpecification() specification}. If the component
   * is NOT constructed regulary (e.g. the component is a remote service) this
   * method may return any type the {@link #getSpecification() specification} is
   * {@link Class#isAssignableFrom(Class) assignable} from.
   * 
   * @return the implementation of the component.
   */
  Class<I> getImplementation();

  /**
   * This method gets the constructor of the component. It is used to
   * instantiate the component directly. The constructor
   * {@link Constructor#getParameterTypes() arguments} must match the
   * {@link Dependency.Type#CONSTRUCTOR constructor}-{@link #getDependencies(Type) dependencies}
   * is the same order.
   * 
   * @return the constructor of the component. May be <code>null</code> if a
   *         custom implementation is written to instantiate the component.
   */
  Constructor<I> getConstructor();

  /**
   * This method gets the instantiation policy of the
   * {@link #getImplementation() implementation}.<br>
   * For a maximum of flexibilty no enum is used here.
   * 
   * @see #INSTANTIATION_POLICY_SINGLETON
   * @see #INSTANTIATION_POLICY_PER_REQUEST
   * 
   * @return the instantiation policy.
   */
  public String getInstantiationPolicy();

  /**
   * This method gets all lifecycle methods of the
   * {@link ComponentProvider component}. They represent the lifecycle phases
   * supported and required by the
   * {@link ExtendedComponentInstanceContainer#getPrivateInstance() component}.
   * If a {@link LifecycleManager#getLifecyclePhases() lifecycle phase} is NOT
   * in the returned list, it will simply be omitted (as if it was available but
   * had an empty method body).
   * 
   * @return the lifecycle methods.
   */
  Iterator<LifecycleMethod> getLifecycleMethods();

  /**
   * This method gets the {@link LifecycleMethod lifecycle-method} of the
   * {@link ExtendedComponentInstanceContainer#getPrivateInstance() component}
   * that is associated with the given
   * {@link LifecycleMethod#getLifecyclePhase() lifecycle-phase}. Such method
   * is typically non-arg (has no {@link Method#getParameterTypes() arguments})
   * even though NOT neccessarily required.
   * 
   * @see #getLifecycleMethods()
   * 
   * @param phase
   *        is the {@link LifecycleMethod#getLifecyclePhase() lifecycle-phase}
   *        of the requested method.
   * @return the method associated with the given
   *         {@link LifecycleMethod#getLifecyclePhase() phase} or
   *         <code>null</code>, if the given <code>phase</code> is not
   *         supported by the
   *         {@link ExtendedComponentInstanceContainer#getPrivateInstance() component}
   *         and should be omitted.
   */
  LifecycleMethod getLifecycleMethod(String phase);

  /**
   * This method gets all dependencies of the
   * {@link #getImplementation() component-implementation}. The dependencies
   * will automatically be injected into the
   * {@link #getImplementation() component}.<br>
   * The iterator should iterate the dependencies as the concatenation of the
   * {@link #getDependencies(Type) iterators} for the types
   * {@link Dependency.Type#CONSTRUCTOR}, {@link Dependency.Type#FIELD}, and
   * {@link Dependency.Type#SETTER} in the given order.
   * 
   * @see #getDependencies(Type)
   * 
   * @return a read-only iterator of the dependencies.
   */
  Iterator<Dependency> getDependencies();

  /**
   * This method gets the {@link #getDependencies() dependencies} of the
   * {@link #getImplementation() component-implementation} for the given
   * {@link Dependency#getInjectionType() type}. The dependencies will
   * automatically be injected into the {@link #getImplementation() component}.<br>
   * The dependencies of the type {@link Dependency.Type#CONSTRUCTOR} should be
   * iterated in the order of their
   * {@link Constructor#getParameterTypes() appearence} in the
   * {@link #getConstructor()}. If no {@link #getConstructor() constructor} is
   * present (<code>null</code>), the {@link Dependency.Type#CONSTRUCTOR}
   * dependencies should be empty.
   * 
   * @see Dependency#getInjectionType()
   * 
   * @param type
   *        is the {@link Dependency#getInjectionType() type} of the requested
   *        dependencies.
   * @return a read-only iterator of the dependencies.
   */
  Iterator<Dependency> getDependencies(Type type);

}
