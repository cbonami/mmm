/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.field;

import java.util.List;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetListBase;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetOptionsField options field widget} that represents a
 * <em>list box</em>. Such widget is a simple list that allows to select one or multiple items out of a list
 * of options.
 * 
 * @see #ARIA_ROLE_LISTBOX
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiWidgetListBox<VALUE> extends UiWidgetListBase<VALUE>, UiWidgetField<List<VALUE>>, UiWidgetReal {

  // nothing to add...

}
