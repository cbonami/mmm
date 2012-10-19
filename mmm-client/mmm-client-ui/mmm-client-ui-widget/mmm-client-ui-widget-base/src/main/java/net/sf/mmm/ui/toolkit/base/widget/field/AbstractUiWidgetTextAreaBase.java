/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterTextAreaBase;

/**
 * This is the abstract base implementation for
 * {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextArea} and
 * {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetTextAreaBase<ADAPTER extends UiWidgetAdapterTextAreaBase<?, String>> extends
    AbstractUiWidgetTextFieldBase<ADAPTER> implements AttributeWriteHeightInRows {

  /** @see #getHeightInRows() */
  private int heightInTextLines;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetTextAreaBase(AbstractUiWidgetFactory<?> factory) {

    super(factory);
    this.heightInTextLines = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.heightInTextLines != 0) {
      adapter.setHeightInRows(this.heightInTextLines);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInRows() {

    return this.heightInTextLines;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInRows(int lines) {

    this.heightInTextLines = lines;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setHeightInRows(lines);
    }
  }

}