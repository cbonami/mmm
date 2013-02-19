/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerRangeField;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetIntegerRangeField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtIntegerRangeField;

/**
 * This is the implementation of {@link UiWidgetIntegerRangeField} using GWT based on
 * {@link UiWidgetAdapterGwtIntegerRangeField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerRangeFieldGwt extends
    AbstractUiWidgetIntegerRangeField<UiWidgetAdapterGwtIntegerRangeField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetIntegerRangeFieldGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtIntegerRangeField createWidgetAdapter() {

    return new UiWidgetAdapterGwtIntegerRangeField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetIntegerRangeField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetIntegerRangeField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetIntegerRangeField create(AbstractUiContext context) {

      return new UiWidgetIntegerRangeFieldGwt(context);
    }

  }

}
