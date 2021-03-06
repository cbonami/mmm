/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the
 * {@link VersionIdentifier#getLabel() label}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionIdentifierFormatterLabel extends AbstractVersionIdentifierFormatterString {

  /**
   * The constructor.
   * 
   * @param prefix is the static prefix to append before the {@link VersionIdentifier#getLabel() label}. Will
   *        be omitted if {@link VersionIdentifier#getLabel() label} is <code>null</code>.
   * @param maximumLength is the maximum number of letters for the {@link VersionIdentifier#getLabel() label}.
   *        The default is {@link Integer#MAX_VALUE}.
   */
  public VersionIdentifierFormatterLabel(String prefix, int maximumLength) {

    super(prefix, maximumLength);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getString(VersionIdentifier value) {

    return value.getLabel();
  }

}
