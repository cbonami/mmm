/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the {@link #isModal() modal} state of an
 * object.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIDialogIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadModalIF {

    /**
     * This method determines if this object (dialog) is modal. If a modal
     * dialog is opened all previous windows of the application are blocked
     * until the window is closed.
     * 
     * @return <code>true</code> if modal, <code>false</code> otherwise.
     */
    boolean isModal();

}
