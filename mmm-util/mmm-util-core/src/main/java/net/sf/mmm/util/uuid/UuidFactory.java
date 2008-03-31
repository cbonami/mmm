/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.uuid;

import java.util.UUID;

/**
 * This is the interface for a factory used to create {@link UUID}s. There can
 * be different implementations for the various {@link UUID#variant() variants}
 * and {@link UUID#version() versions} of {@link UUID}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UuidFactory {

  /**
   * This method creates a new {@link UUID}.
   * 
   * @return the new {@link UUID}.
   */
  UUID createUuid();

}
