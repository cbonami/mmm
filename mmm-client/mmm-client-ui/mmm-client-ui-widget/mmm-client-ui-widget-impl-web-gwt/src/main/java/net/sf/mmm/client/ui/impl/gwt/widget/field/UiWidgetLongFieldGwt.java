/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtLongField;

/**
 * This is the implementation of {@link UiWidgetLongField} using GWT based on
 * {@link UiWidgetAdapterGwtLongField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLongFieldGwt extends
    AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtLongField<Long>, Long, Long> implements UiWidgetLongField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetLongFieldGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtLongField<Long> createWidgetAdapter() {

    return new UiWidgetAdapterGwtLongField<Long>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetLongField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLongField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLongField create(UiContext context) {

      return new UiWidgetLongFieldGwt(context);
    }

  }

}
