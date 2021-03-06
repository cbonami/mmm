/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTimeField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTimeField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestTimeField;

/**
 * This is the implementation of {@link UiWidgetTimeField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTimeFieldTestImpl extends AbstractUiWidgetTimeField<UiWidgetAdapterTestTimeField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetTimeFieldTestImpl(UiContext context) {

    super(context, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestTimeField createWidgetAdapter() {

    return new UiWidgetAdapterTestTimeField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetTimeField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTimeField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTimeField create(UiContext context) {

      return new UiWidgetTimeFieldTestImpl(context);
    }

  }

}
