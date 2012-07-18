/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHtmlId;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStyles;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteVisible;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetAtomic;
import net.sf.mmm.ui.toolkit.api.widget.composite.UiWidgetComposite;

/**
 * This is the interface for an adapter to a physical <em>widget</em> (or potentially a composition of such
 * widgets) of the underlying native widget toolkit.<br/>
 * A widget is any object of the UI (user interface) including both {@link UiWidgetAtomic atomic widgets} as
 * well a {@link UiWidgetComposite composite widgets}. <br/>
 * For advanced usage see also <code>net.sf.mmm.ui.toolkit.api.element.UiElement</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidget extends AttributeWriteHtmlId, AttributeWriteVisible, AttributeWriteTooltip,
    AttributeWriteEnabled, AttributeWriteStyles {

  // nothing to add, just a combination of attribute/feature interfaces...

}
