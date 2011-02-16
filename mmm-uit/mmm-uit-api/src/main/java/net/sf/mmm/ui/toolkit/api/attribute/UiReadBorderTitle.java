/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the
 * {@link #getBorderTitle() border-title} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiReadBorderTitle {

  /**
   * This method gets the title of the objects border.
   * 
   * @return the border title or <code>null</code> if the object has no titled
   *         border.
   */
  String getBorderTitle();

}
