/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.gwt.api;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;

/**
 * This class holds a collection of utility functions for GWT (Google Web Toolkit) using JSNI (JavaScript
 * Native Interface).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JavaScriptUtil {

  /** @see #getInstance() */
  private static JavaScriptUtil instance = new JavaScriptUtil();

  /**
   * The constructor.
   */
  public JavaScriptUtil() {

    super();
  }

  /**
   * @return the singleton instance of {@link JavaScriptUtil}.
   */
  public static JavaScriptUtil getInstance() {

    return instance;
  }

  /**
   * This method allows to replace the default implementation returned by {@link #getInstance()}.
   * 
   * @param customInstance is the new {@link JavaScriptUtil} instance.
   */
  protected static void setInstance(JavaScriptUtil customInstance) {

    instance = customInstance;
  }

  //formatter:off

  /**
   * This method gets the width of the current screen/display also called the horizontal resolution.
   *
   * @return the screen width in pixels.
   */
  public native int getScreenWidth() /*-{
    return $wnd.screen.width;
  }-*/;

  /**
   * This method gets the height of the current screen/display also called the vertical resolution.
   *
   * @return the screen height in pixels.
   */
  public native int getScreenHeight() /*-{
    return $wnd.screen.height;
  }-*/;

  /**
   * This method gets the pixel position of the browser window's left border on the x-axis (horizontal).
   *
   * @return the browsers X position.
   */
  public native int getBrowserPositionX() /*-{
    if ($wnd.screenLeft) {
      return $wnd.screenLeft;
    } else if ($wnd.screenX) {
      return $wnd.screenX;
    }
    return 0;
  }-*/;


  /**
   * This method gets the pixel position of the browser window's top border on the y-axis (vertical).
   *
   * @return the browsers Y position.
   */
  public native int getBrowserPositionY() /*-{
    if ($wnd.screenTop) {
      return $wnd.screenTop;
    } else if ($wnd.screenY) {
      return $wnd.screenY;
    }
    return 0;
  }-*/;

  /**
   * This method gets the width of the browser window in pixel (horizontal size).
   *
   * @return the browsers width.
   */
  public native int getBrowserWidth() /*-{
    if ($wnd.outerwidth) {
      return $wnd.outerwidth;
    } else if ($doc.documentElement.offsetWidth) {
      return $doc.documentElement.offsetWidth + 18;
    }
  }-*/;

  /**
   * This method gets the height of the browser window in pixel (vertical size).
   *
   * @return the browsers height.
   */
  public native int getBrowserHeight() /*-{
    if ($wnd.outerheight) {
      return $wnd.outerheight;
    } else if ($doc.documentElement.offsetHeight) {
      return $doc.documentElement.offsetHeight + 110;
    }
    return 0;
  }-*/;

  /**
   * This method sets the custom validation message of the given input {@link Element}.
   * The Browser needs to support HTML5 for this feature.
   *
   * @param inputElement is the input {@link Element} that has been validated.
   * @param message - the empty string to mark as valid, the validation failure message otherwise.
   * @return <code>true</code> if the browser supports custom validity and it has been set, <code>false</code>
   *         otherwise.
   */
  public native boolean setCustomValidity(Element inputElement, String message) /*-{
    if (inputElement.setCustomValidity) {
      inputElement.setCustomValidity(message);
      return true;
    }
    return false;
  }-*/;

  /**
   * Creates an {@link InputElement} of a custom {@link InputElement#getType() type}. Only used while GWT does NOT
   * directly provide a way to create HTML5 input elements.
   *
   * @param type is the requested {@link InputElement#getType() type} (e.g. "range", "date", etc.).
   * @return the requested {@link InputElement} of the given <code>type</code>.
   */
  public InputElement createInputElement(String type) {

    // return DOMImpl.impl.createInputElement(Document.get(), type);
    InputElement inputElement = Document.get().createTextInputElement();
    setInputElementType(inputElement, type);
    return inputElement;
  }

  /**
   * This method sets the {@link InputElement#getType() type} of an {@link InputElement}.
   *
   * @param inputElement is the input {@link InputElement}.
   * @param type is the new {@link InputElement#getType() type} to set.
   */
  private native void setInputElementType(InputElement inputElement, String type) /*-{
    inputElement.type = type;
  }-*/;

  /**
   * This method creates a {@link Object#clone() clone} of the given <code>template</code>.
   *
   * @param template is the object to clone.
   * @return the cloned object.
   */
  public native Object clone(Object template) /*-{
    var clone = {};

    // create unique object hash code
    @com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(clone);

    for (var i in template) {
      if(!(i in clone)) {
          clone[i] = template[i];
      }
    }
    return clone;
  }-*/;

  /**
   * This method creates a {@link Class#newInstance() new instance} of the given <code>template</code>.
   *
   * @param template is the object create a new instance of.
   * @return the new instance.
   */
  public native Object newInstance(Object template) /*-{
    var clone = {};

    // create unique object hash code
    @com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(clone);

    for (var i in template) {
      if(!(i in clone)) {
          clone[i] = template[i];
      }
    }
    return clone;
  }-*/;

  //formatter:on

}
