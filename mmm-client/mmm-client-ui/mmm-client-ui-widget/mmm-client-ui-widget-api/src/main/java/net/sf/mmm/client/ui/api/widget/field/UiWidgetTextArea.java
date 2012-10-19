/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetTextualInputField input field widget} that represents a text area
 * field. Such field is a multi-line variant of {@link UiWidgetTextField}. The user can enter line breaks by
 * hitting [enter] or [return].
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetTextArea extends UiWidgetTextualInputField<String>, AttributeWriteKeyboardFilter,
    AttributeWriteHeightInRows, UiWidgetReal {

  // nothing to add...

}
