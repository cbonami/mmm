/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtIntegerField;

/**
 * This is the implementation of {@link UiWidgetIntegerField} using GWT based on
 * {@link UiWidgetAdapterGwtIntegerField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerFieldGwt extends
    AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtIntegerField<Integer>, Integer, Integer> implements UiWidgetIntegerField {

  /**
   * The constructor.
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetIntegerFieldGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtIntegerField<Integer> createWidgetAdapter() {

    return new UiWidgetAdapterGwtIntegerField<Integer>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetIntegerField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetIntegerField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetIntegerField create(AbstractUiContext context) {

      return new UiWidgetIntegerFieldGwt(context);
    }

  }

}
