/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetOptionsField options field widget} that represents a
 * <em>combo box</em>. Such widget is like a drop-down where the user can open a menu with the available
 * {@link #getOptions() options} to choose from. If supported by the underlying native toolkit it also allows
 * typing text and offering completions in order to allow better keyboard control.
 * 
 * @see #ARIA_ROLE_COMBOBOX
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiWidgetComboBox<VALUE> extends UiWidgetOptionsField<VALUE>, UiWidgetReal {

  // nothing to add

}
