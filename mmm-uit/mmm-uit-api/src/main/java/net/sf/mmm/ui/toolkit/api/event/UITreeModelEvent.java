/* $Id$ */
package net.sf.mmm.ui.toolkit.api.event;

/**
 * This class represents the event sent by the
 * {@link net.sf.mmm.ui.toolkit.api.model.UITreeModel tree-model} to its
 * {@link net.sf.mmm.ui.toolkit.api.event.UITreeModelListener listeners} in
 * order to notify about changes of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UITree tree}.
 * 
 * @param <N>
 *        is the type of the node that changed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UITreeModelEvent<N> extends UIModelEvent {

  /** the node that changed */
  private final N treeNode;

  /**
   * The constructor.
   * 
   * @param eventType
   *        is the type for the new event.
   * @param node
   *        is the node that changed.
   */
  public UITreeModelEvent(Type eventType, N node) {

    super(eventType);
    this.treeNode = node;
  }

  /**
   * This method gets the tree node that has changed.
   * 
   * @return the changed node.
   */
  public N getTreeNode() {

    return this.treeNode;
  }

}
