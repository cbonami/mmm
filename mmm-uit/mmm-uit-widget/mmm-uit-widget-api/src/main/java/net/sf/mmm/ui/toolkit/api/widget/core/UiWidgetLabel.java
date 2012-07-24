/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.core;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularAtomic;

/**
 * This is the interface for a {@link UiWidgetRegularAtomic regular atomic widget} that represents a label. A
 * label is a very simple widget that displays some static text.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetLabel extends UiWidgetRegularAtomic, AttributeWriteLabel, UiWidgetReal {

  // nothing to add

}
