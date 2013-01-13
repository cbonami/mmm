/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.gwt.dialog;

import net.sf.mmm.client.ui.dialog.api.ApplicationWindow;
import net.sf.mmm.client.ui.dialog.api.DialogPlace;
import net.sf.mmm.client.ui.dialog.base.AbstractDialogManager;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DialogManagerImpl extends AbstractDialogManager {

  /**
   * The constructor.
   */
  public DialogManagerImpl() {

    super();
    History.addValueChangeHandler(new HistoryListener());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void navigateBack() {

    History.back();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void navigateForward() {

    History.forward();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void navigateTo(DialogPlace place) {

    History.newItem(place.toString(), true);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ApplicationWindow openWindow(DialogPlace place) {

    String url = Location.getHref();
    String hash = Location.getHash();
    if ((hash != null) && (url.endsWith(hash))) {
      url = url.substring(0, url.length() - hash.length());
    }
    url = url + "#" + place.toString();
    String name = "_blank";
    String features = "";
    Window.open(url, name, features);

    // TODO
    return null;
  }

  /**
   * This method gets called whenever the {@link DialogPlace} changes.
   * 
   * @param place is the new place.
   */
  protected void onNavigate(String place) {

    DialogPlace dialogPlace = DialogPlace.fromString(place);
    show(dialogPlace);
  }

  /**
   * This inner class is the listener that gets notified whenever the {@link DialogPlace} changes.
   */
  private class HistoryListener implements ValueChangeHandler<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onValueChange(ValueChangeEvent<String> event) {

      onNavigate(event.getValue());
    }
  }

}
