/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import java.awt.Dimension;
import java.lang.reflect.Array;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.sf.mmm.ui.toolkit.api.event.UiEventType;
import net.sf.mmm.ui.toolkit.api.model.data.UiTreeMvcModel;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTree;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.model.TreeModelAdapter;

/**
 * This class is the implementation of the UITree interface using Swing as the
 * UI toolkit.
 * 
 * @param <N> is the templated type of the tree-nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITreeImpl<N> extends AbstractUiWidget implements UiTree<N> {

  /** the swing scroll pane */
  private final JScrollPane scrollPanel;

  /** the unwrapped swing tree */
  private final JTree tree;

  /** the tree model adapter */
  private TreeModelAdapter<N> modelAdapter;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>
   *        ).
   */
  public UITreeImpl(UIFactorySwing uiFactory, UiComposite<? extends UiElement> parentObject) {

    super(uiFactory, parentObject);
    this.tree = new JTree();
    this.tree.setExpandsSelectedPaths(true);
    this.tree.setShowsRootHandles(true);
    this.scrollPanel = new JScrollPane(this.tree);
    this.scrollPanel.setMinimumSize(new Dimension(40, 40));
    this.modelAdapter = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public UiTreeMvcModel<N> getModel() {

    if (this.modelAdapter == null) {
      return null;
    } else {
      return this.modelAdapter.getModel();
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public void setModel(UiTreeMvcModel<N> newModel) {

    if (this.modelAdapter != null) {
      this.modelAdapter.dispose();
    }
    this.modelAdapter = new TreeModelAdapter(newModel);
    this.tree.setModel(this.modelAdapter);
  }

  /**
   * This method sets the selection mode of this tree.
   * 
   * @param multiSelection - if <code>true</code> the user can select multiple
   *        items, else ony one.
   */
  public void setMultiSelection(boolean multiSelection) {

    int mode = TreeSelectionModel.SINGLE_TREE_SELECTION;
    if (multiSelection) {
      mode = TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION;
    }
    this.tree.getSelectionModel().setSelectionMode(mode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.scrollPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected JComponent getActiveSwingComponent() {

    return this.tree;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMultiSelection() {

    int mode = this.tree.getSelectionModel().getSelectionMode();
    return (mode == TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
  }

  /**
   * {@inheritDoc}
   */
  public N getSelection() {

    TreePath selection = this.tree.getSelectionPath();
    if (selection != null) {
      return (N) selection.getLastPathComponent();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public N[] getSelections() {

    TreePath[] selections = this.tree.getSelectionPaths();
    int len = 0;
    if (selections != null) {
      len = selections.length;
    }
    N[] result = (N[]) Array.newInstance(getModel().getNodeType(), len);
    for (int i = 0; i < len; i++) {
      result[i] = (N) selections[i].getLastPathComponent();
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.tree.addTreeSelectionListener(new TreeSelectionListener() {

      public void valueChanged(TreeSelectionEvent e) {

        fireEvent(UiEventType.CLICK);
      }
    });
    return true;
  }
}
