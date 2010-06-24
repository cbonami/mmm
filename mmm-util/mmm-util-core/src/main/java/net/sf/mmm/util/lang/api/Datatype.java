/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a <em>datatype</em>. A datatype is an
 * <em>immutable</em> object representing a simple value of a specific type.<br>
 * Common generic datatypes are {@link String}, {@link Boolean}, {@link Long},
 * {@link Integer}, {@link Double} and {@link java.util.Date}. They should be
 * accepted as datatypes even though they do NOT implement this interface.<br>
 * An implementation of this interface should declare all its
 * {@link java.lang.reflect.Field member variables} as final and bind them at
 * {@link java.lang.reflect.Constructor construction}. An {@link Enum}
 * implementing this interface should also offer a static method called
 * <code>fromValue(V value)</code> that returns the appropriate {@link Enum}
 * instance or <code>null</code> if no such instance exists.
 * 
 * @param <V> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface Datatype<V> {

  /**
   * This method returns the raw value of this datatype. This will typically be
   * a common <code>java.lang</code> datatype. In case of a composed datatype it
   * is also legal that this method returns the datatype instance itself.
   * 
   * @return the value of this datatype.
   */
  V getValue();

  /**
   * This method gets the <em>title</em> of this datatype. The title is a string
   * representation intended to be displayed to end-users (i18n will be done
   * externally - see {@link net.sf.mmm.util.nls.api.NlsMessage}).<br>
   * Since the general contract of {@link #toString()} is quite weak, this
   * method is added to explicitly express the presence of the title and to
   * ensure implementors of this interface can NOT miss to implement this.
   * 
   * @see #toString()
   * 
   * @return the display title of this datatype.
   */
  String getTitle();

  /**
   * This method needs to return the same result a {@link #getTitle()}.
   * 
   * {@inheritDoc}
   * 
   * @return the display title of this datatype.
   */
  String toString();

}