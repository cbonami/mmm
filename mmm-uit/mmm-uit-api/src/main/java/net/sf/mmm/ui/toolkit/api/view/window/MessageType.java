/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

/**
 * This enum contains the available types for message dialogs.
 * 
 * @see net.sf.mmm.ui.toolkit.api.view.window.UiWindow#showMessage(String, String,
 *      MessageType)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum MessageType {

  /** the error message type */
  ERROR,

  /** the warning message type */
  WARNING,

  /** the info message type */
  INFO;

}
