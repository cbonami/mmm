/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.io.InputStream;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;

/**
 * This is the abstract base implementation of the {@link DataResource}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractDataResource implements DataResource {

  /**
   * The constructor.
   */
  public AbstractDataResource() {

    super();
  }

  /**
   * This method gets the <em>scheme-prefix</em> of absolute URIs for this type
   * of {@link DataResource}. The scheme-prefix has the following form:
   * <code>{@link java.net.URI#getScheme() &lt;scheme&gt;}:&lt;suffix&gt;</code>
   * where <code>&lt;suffix&gt;</code> is the empty string or something like
   * <code>//</code>.
   * 
   * @return the scheme-prefix of this resource.
   */
  public abstract String getSchemePrefix();

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation. Override if there is a more performing
   * way to implement this.
   */
  public long getSize() throws ResourceNotAvailableException {

    try {
      return getUrl().openConnection().getContentLength();
    } catch (IOException e) {
      throw new ResourceNotAvailableException(e, getPath());
    }
  }

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation. Override if there is a more performing
   * way to implement this.
   */
  public InputStream openStream() throws ResourceNotAvailableException, IOException {

    return getUrl().openStream();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String simpleName = getClass().getSimpleName();
    String path = getPath();
    StringBuilder result = new StringBuilder(simpleName.length() + path.length() + 2);
    result.append(simpleName);
    result.append('[');
    result.append(path);
    result.append(']');
    return result.toString();
  }

}
