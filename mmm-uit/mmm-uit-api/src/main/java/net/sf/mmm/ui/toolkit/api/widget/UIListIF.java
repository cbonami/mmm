/* $Id: UIListIF.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.ui.toolkit.api.widget;

import net.sf.mmm.ui.toolkit.api.state.UIReadMultiSelectionFlagIF;

/**
 * This is the interface for a list UI. A list is used to display items in a
 * list so the user can select one or multiple of these items.<br>
 * The list shows several items in rows. If the number of items is too large to
 * fit, a scrollbar is displayed.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIListIF<E> extends UIListWidgetIF<E>, UIReadMultiSelectionFlagIF {

    /** the type of this object */
    String TYPE = "List";

    /**
     * This method gets the indices of all selected items.
     * 
     * @return the indices of the selected items.
     */
    int[] getSelectedIndices();

}
