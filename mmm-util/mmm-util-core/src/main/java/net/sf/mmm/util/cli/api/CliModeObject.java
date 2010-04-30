/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import java.util.Set;

/**
 * This is a container for a {@link CliMode} together with additional associated
 * information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface CliModeObject {

  /**
   * The {@link CliMode#id() ID} of the {@link #getMode() mode}.<br>
   * <b>ATTENTION:</b><br>
   * Please use this method to get the ID as {@link #getMode()} may return
   * <code>null</code>.
   * 
   * @return the ID.
   */
  String getId();

  /**
   * This method gets the actual {@link CliMode}.
   * 
   * @return the {@link CliMode} or <code>null</code> if no {@link CliMode}
   *         annotation was present.
   */
  CliMode getMode();

  /**
   * This method gets the {@link Class} that was annotated with the
   * {@link #getMode() mode}.
   * 
   * @return the annotated {@link Class} or <code>null</code> if
   *         {@link #getMode()} is <code>null</code>.
   */
  Class<?> getAnnotatedClass();

  /**
   * This method gets the {@link Set} of {@link CliModeObject modes} that are
   * {@link CliMode#parentIds() extended} by this {@link #getMode() mode}.
   * 
   * @return the extended {@link CliModeObject modes}.
   */
  Set<? extends CliModeObject> getExtendedModes();

}
