/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteColumnSpan;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteRowSpan;

/**
 * A {@link AbstractTableCell} is a {@link SingleCompositePanel} that represents a table cell in a
 * {@link TableRow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractTableCell extends SingleCompositePanel implements AttributeWriteColumnSpan,
    AttributeWriteRowSpan {

  /**
   * The constructor.
   */
  public AbstractTableCell() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getColumnSpan() {

    String colspan = getElement().getAttribute(ATTRIBUTE_COLSPAN);
    if ((colspan == null) || colspan.isEmpty()) {
      return 1;
    }
    return Integer.parseInt(colspan);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumnSpan(int columnSpan) {

    getElement().setAttribute(ATTRIBUTE_COLSPAN, Integer.toString(columnSpan));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getRowSpan() {

    String rowspan = getElement().getAttribute(ATTRIBUTE_ROWSPAN);
    if ((rowspan == null) || rowspan.isEmpty()) {
      return 1;
    }
    return Integer.parseInt(rowspan);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setRowSpan(int rowSpan) {

    getElement().setAttribute(ATTRIBUTE_ROWSPAN, Integer.toString(rowSpan));
  }
}
