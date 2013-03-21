/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the marker interface for {@link UiWidget}s that can be {@link UiWidgetFactoryNative#create(Class)
 * created} via {@link UiWidgetFactoryNative}.
 * 
 * @see net.sf.mmm.client.ui.api.UiContext#getWidgetFactory()
 * @see UiWidgetFactoryNative#create(Class)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetReal extends UiWidget {

  // nothing to add

}
