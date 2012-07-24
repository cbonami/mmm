/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetTextField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtTextBox;

/**
 * This is the implementation of {@link UiWidgetTextField} using GWT based on
 * {@link UiWidgetAdapterGwtTextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTextFieldGwt extends AbstractUiWidgetTextField<UiWidgetAdapterGwtTextBox<String>> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetTextFieldGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTextBox<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtTextBox<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTextField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTextField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTextField create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetTextFieldGwt(factory);
    }

  }

}
