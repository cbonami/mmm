/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteTitle;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface for a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite} that
 * can hold one child
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite} that
 * is surrounded by a {@link #setTitle(String) titled} border.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiBorderPanel<E extends UiElement> extends UiSingleComposite<E>, UiWriteTitle {

  /** the type of this object */
  String TYPE = "BorderPanel";

}
