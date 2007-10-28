/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

/**
 * This class represents the three distinct classes of Unix (POSIX) file
 * permissions.
 * 
 * @see #USER
 * @see #GROUP
 * @see #OTHERS
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum FileModeClass {

  /** The class for the permissions of the user (owner). */
  USER,

  /** The class for the permissions of the group. */
  GROUP,

  /** The class for the permissions for all others. */
  OTHERS;

}
