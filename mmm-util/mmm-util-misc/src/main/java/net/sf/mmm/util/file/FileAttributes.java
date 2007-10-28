/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import net.sf.mmm.util.BasicUtil;

/**
 * This class represents the attributes of a file as defined in a Unix/Posix
 * Filesystem. They consist of a {@link #getUser() user}, a
 * {@link #getGroup() group} and a {@link #getModeMask() mode mask} (modifiers).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileAttributes implements Cloneable {

  /** @see #getUser() */
  private String user;

  /** @see #getGroup() */
  private String group;

  /** @see #getModeMask() */
  private FileModeMask modeMask;

  /**
   * The constructor.
   */
  public FileAttributes() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FileAttributes clone() {

    try {
      FileAttributes clone = (FileAttributes) super.clone();
      if (clone.modeMask != null) {
        clone.setModeMask(clone.modeMask.clone());
      }
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * @return the user
   */
  public String getUser() {

    return this.user;
  }

  /**
   * @param user the user to set
   */
  public void setUser(String user) {

    this.user = user;
  }

  /**
   * @return the group
   */
  public String getGroup() {

    return this.group;
  }

  /**
   * @param group the group to set
   */
  public void setGroup(String group) {

    this.group = group;
  }

  /**
   * @return the userPermissions
   */
  public FileModeMask getModeMask() {

    return this.modeMask;
  }

  /**
   * @param mask the userPermissions to set
   */
  public void setModeMask(FileModeMask mask) {

    this.modeMask = mask;
  }

  /**
   * {@inheritDoc}
   */
  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (FileAttributes.class != obj.getClass()) {
      return false;
    }
    FileAttributes otherAttributes = (FileAttributes) obj;
    if (!BasicUtil.isEqual(this.user, otherAttributes.user)) {
      return false;
    }
    if (!BasicUtil.isEqual(this.group, otherAttributes.group)) {
      return false;
    }
    if (!BasicUtil.isEqual(this.modeMask, otherAttributes.modeMask)) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public int hashCode() {

    int hash = 0;
    if (this.user != null) {
      hash = this.user.hashCode();
    }
    if (this.group != null) {
      hash = hash * 31 + this.group.hashCode();
    }
    if (this.modeMask != null) {
      hash = hash * 31 + this.modeMask.hashCode();
    }
    return super.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  public String toString() {

    // TODO Auto-generated method stub
    return super.toString();
  }

}
