/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;

/**
 * An instance of this class represents a specific
 * {@link PojoPropertyAccessorMode accessor-mode} for a
 * {@link PojoPropertyAccessorNonArg non-arg accessor} of a
 * {@link PojoPropertyDescriptor property}. This abstract base class acts like
 * an {@link Enum} but allows you to define your own custom mode by extending
 * this class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class PojoPropertyAccessorNonArgMode extends
    PojoPropertyAccessorMode<PojoPropertyAccessorNonArg> {

  /**
   * The mode for a regular {@link PojoPropertyAccessorNonArg reader} of a
   * property.
   */
  public static final PojoPropertyAccessorNonArgMode GET = new PojoPropertyAccessorNonArgMode("get") {};

  /**
   * The mode for a {@link PojoPropertyAccessorNonArg reader} of the size of a
   * property that holds a container type (array or {@link java.util.Collection}).
   */
  public static final PojoPropertyAccessorNonArgMode SIZE = new PojoPropertyAccessorNonArgMode(
      "size") {};

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of this mode.
   */
  public PojoPropertyAccessorNonArgMode(String name) {

    super(name);
  }

}
