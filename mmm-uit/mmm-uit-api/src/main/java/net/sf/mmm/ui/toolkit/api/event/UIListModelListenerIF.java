/* $Id: UIListModelListenerIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.event;

import java.util.EventListener;

/**
 * This is the interface of a list model listener. Such a listener gets 
 * notified about any change of list items from the list model.
 * @see net.sf.mmm.ui.toolkit.api.model.UIListModelIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIListModelListenerIF extends EventListener {

    /**
     * This method is called by the list model if the list has changed (items
     * have been updated, inserted or removed).
     * 
     * @param event notifies about the change of the list.
     */
    void listModelChanged(UIListModelEvent event);
    
}
