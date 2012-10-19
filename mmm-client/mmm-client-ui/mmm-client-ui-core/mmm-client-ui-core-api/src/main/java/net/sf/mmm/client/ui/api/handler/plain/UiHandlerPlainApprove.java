/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

/**
 * This is the {@link UiHandlerPlain} for the action {@link #onApprove() approve}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerPlainApprove extends UiHandlerPlain {

  /**
   * This method is invoked for the action <em>approve</em>. This means that some change or step is confirmed.
   * Approval is often workflow related and may require a particular role (following the double-check or
   * four-eye principle).
   */
  void onApprove();

}
