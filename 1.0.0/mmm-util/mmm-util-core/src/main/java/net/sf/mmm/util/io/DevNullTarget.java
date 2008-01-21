/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.io.OutputStream;

/**
 * This is an implementation of <code>/dev/null</code> as {@link OutputStream}.
 * In other words the {@link DevNullTarget} is a dummy {@link OutputStream} that
 * ignores all the data written.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class DevNullTarget extends OutputStream {

  /** The singleton instance. */
  public static final DevNullTarget INSTANCE = new DevNullTarget();

  /**
   * The constructor.
   */
  private DevNullTarget() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(int b) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(byte[] b, int off, int len) {

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() {

  // do nothing...
  }

}
