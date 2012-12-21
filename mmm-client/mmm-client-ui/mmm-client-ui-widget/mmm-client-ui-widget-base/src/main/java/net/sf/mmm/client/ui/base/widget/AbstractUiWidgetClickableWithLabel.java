/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActiveWithLabel;

/**
 * This is the abstract base implementation of {@link AbstractUiWidgetClickable} in combination with a
 * {@link #getLabel() label}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetClickableWithLabel<ADAPTER extends UiWidgetAdapterActiveWithLabel<?>> extends
    AbstractUiWidgetClickable<ADAPTER> implements AttributeWriteLabel {

  /** @see #getLabel() */
  private String label;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetClickableWithLabel(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.label != null) {
      adapter.setLabel(this.label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.label == null) {
      return super.toString();
    }
    return super.toString() + "[" + this.label + "]";
  }

}
