/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtDoubleField;

/**
 * This is the implementation of {@link UiWidgetDoubleField} using GWT based on
 * {@link UiWidgetAdapterGwtDoubleField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDoubleFieldGwt extends
    AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtDoubleField<Double>, Double, Double> implements UiWidgetDoubleField {

  /**
   * The constructor.
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetDoubleFieldGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtDoubleField<Double> createWidgetAdapter() {

    return new UiWidgetAdapterGwtDoubleField<Double>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetDoubleField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetDoubleField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetDoubleField create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetDoubleFieldGwt(factory);
    }

  }

}
