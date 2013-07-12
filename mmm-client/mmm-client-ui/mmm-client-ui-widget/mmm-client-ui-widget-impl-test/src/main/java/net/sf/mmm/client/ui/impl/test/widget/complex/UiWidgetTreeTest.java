/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTree;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTree;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.complex.adapter.UiWidgetAdapterTestTree;

/**
 * This is the implementation of {@link UiWidgetTree} using GWT.
 * 
 * @param <NODE> is the generic type of the tree-nodes. E.g. {@link net.sf.mmm.util.collection.api.TreeNode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTreeTest<NODE> extends AbstractUiWidgetTree<UiWidgetAdapterTestTree<NODE>, NODE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetTreeTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestTree<NODE> createWidgetAdapter() {

    return new UiWidgetAdapterTestTree<NODE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetTree> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTree.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTree create(UiContext context) {

      return new UiWidgetTreeTest(context);
    }

  }

}
