/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.model;


/**
 * This is the interface for the {@link UiModel model} of a
 * {@link net.sf.mmm.client.ui.api.widget.table.UiWidgetListTable}.<br/>
 * <b>Note:</b><br/>
 * Please read the documentation of {@link AbstractUiTableModel} for further details.
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid. It should be a java-bean
 *        oriented object. Immutable objects (that have no setters) can also be used but only for read-only
 *        tables.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiListTableModel<ROW> extends AbstractUiTableModel<ROW> {

  // nothing to add...

}
