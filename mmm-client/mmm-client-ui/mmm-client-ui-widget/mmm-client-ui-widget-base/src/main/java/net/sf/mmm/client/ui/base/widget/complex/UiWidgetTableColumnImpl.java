/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn}.
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTableColumnImpl<ROW, CELL> extends
    AbstractUiWidgetTableColumn<UiWidgetAdapterTableColumn, ROW, CELL> {

  /** @see #getTypedProperty() */
  private final TypedProperty<CELL> typedProperty;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param listTable is the {@link AbstractUiWidgetAbstractListTable list table} this column is connected to.
   * @param typedProperty is the {@link #getTypedProperty() typed property} of the column to create. May be
   *        <code>null</code>.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetTableColumnImpl(UiContext context, AbstractUiWidgetAbstractDataTable<?, ROW> listTable,
      TypedProperty<CELL> typedProperty, UiWidgetAdapterTableColumn widgetAdapter) {

    super(context, listTable, widgetAdapter);
    this.typedProperty = typedProperty;
  }

  /**
   * @return the {@link TypedProperty} identifying the property managed with this column. May be
   *         <code>null</code> if the column was created with an explicit {@link #getPropertyAccessor()
   *         property accessor}. In such case the {@link #getWidgetFactory() widget factory} shall not be
   *         <code>null</code> or cells with <code>null</code> values can not be handled.
   */
  public TypedProperty<CELL> getTypedProperty() {

    return this.typedProperty;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTableColumn createWidgetAdapter() {

    return getListTable().createTableColumnAdapter(this);
  }

}
