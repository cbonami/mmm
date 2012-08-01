/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteImage;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetImage;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapterTab<WIDGET> extends UiWidgetAdapterSingleMutableComposite<WIDGET, UiWidgetRegular>,
    UiWidgetAdapterWithLabel<WIDGET>, AttributeWriteImage<UiWidgetImage> {

  // nothing to add

}
