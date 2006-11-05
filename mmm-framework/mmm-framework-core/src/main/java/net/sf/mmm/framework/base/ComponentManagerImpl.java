/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ContainerException;

/**
 * This is the default implementation of the {@link ComponentManager}. It is
 * individually created per component and creates the {@link DependencyNode} of
 * that component to track the complete dependency graph.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentManagerImpl extends AbstractComponentManager {

  /** the IoC-container where to delegate the calls */
  private final AbstractIocContainer container;

  /** the dependency node of the component this manager acts for */
  private DependencyNode<?> sourceNode;

  /**
   * The constructor.
   * 
   * @param iocContainer
   * @param node
   */
  public ComponentManagerImpl(AbstractIocContainer iocContainer, DependencyNode<?> node) {

    super();
    this.container = iocContainer;
    this.sourceNode = node;
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#hasComponent(java.lang.Class)
   */
  public <S> boolean hasComponent(Class<S> specification) {

    return this.container.hasComponent(specification);
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#releaseComponent(java.lang.Object)
   */
  public synchronized <S> void releaseComponent(S component) {

    this.container.releaseComponent(component, this.sourceNode);
  }

  /**
   * @see net.sf.mmm.framework.api.ComponentManager#requestComponent(java.lang.Class,
   *      java.lang.String)
   */
  public synchronized <S> S requestComponent(Class<S> specification, String instanceId)
      throws ComponentException, ContainerException {

    if ((specification == ComponentManager.class) && (DEFAULT_INSTANCE_ID.equals(instanceId))) {
      return (S) this;
    }
    return this.container.requestComponent(specification, instanceId, this.sourceNode);
  }
}
