/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.model.UIListModel;
import net.sf.mmm.ui.toolkit.api.widget.UIComboBox;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.model.ComboBoxModelAdapter;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncComboAccess;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIComboBox} interface using SWT as
 * the UI toolkit.
 * 
 * @param <E>
 *        is the templated type of the elements that can be selected with this
 *        widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIComboBoxImpl<E> extends AbstractUIWidget implements UIComboBox<E> {

  /** the synchron access to the combo */
  private final SyncComboAccess syncAccess;

  /** the listener */
  private final ComboBoxModelAdapter modelAdapter;

  /** the model */
  private UIListModel<E> model;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param editableFlag
   *        is the (initial) value of the
   *        {@link net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable() editable-flag}.
   * @param listModel
   *        is the model defining the the selectable elements.
   */
  public UIComboBoxImpl(UIFactorySwt uiFactory, UISwtNode parentObject, boolean editableFlag,
      UIListModel<E> listModel) {

    super(uiFactory, parentObject);
    int style = SWT.DEFAULT;
    if (editableFlag) {
      style = style ^ SWT.READ_ONLY;
    }
    this.syncAccess = new SyncComboAccess(uiFactory, style);
    this.model = listModel;
    this.modelAdapter = new ComboBoxModelAdapter(this.syncAccess, this.model);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   * 
   */
  @Override
  public SyncComboAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   * 
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#create()
   * 
   */
  @Override
  public void create() {

    super.create();
    this.modelAdapter.initialize();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#getModel()
   * 
   */
  public UIListModel<E> getModel() {

    return this.model;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionIndex#getSelectedIndex()
   * 
   */
  public int getSelectedIndex() {

    return this.syncAccess.getSelection();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionIndex#setSelectedIndex(int)
   * 
   */
  public void setSelectedIndex(int newIndex) {

    this.syncAccess.setText(this.modelAdapter.getModel().getElementAsString(newIndex));
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UIListWidget#setModel(net.sf.mmm.ui.toolkit.api.model.UIListModel)
   * 
   */
  public void setModel(UIListModel<E> newModel) {

    this.modelAdapter.setModel(newModel);
    this.model = newModel;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#isEditable()
   * 
   */
  public boolean isEditable() {

    return this.syncAccess.hasStyle(SWT.READ_ONLY);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteEditable#setEditable(boolean)
   * 
   */
  public void setEditable(boolean editableFlag) {

  // not supported - maybe throw away comboBox and create new-one?
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   * 
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   * 
   */
  public void setText(String text) {

    this.syncAccess.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteSelectionValue#setSelectedValue(Object)
   * 
   */
  public void setSelectedValue(E newValue) {

    int index = this.model.getIndexOf(newValue);
    if (index >= 0) {
      setSelectedIndex(index);
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadSelectionValue#getSelectedValue()
   * 
   */
  public E getSelectedValue() {

    int index = getSelectedIndex();
    if (index >= 0) {
      return this.model.getElement(index);
    }
    return null;
  }

}
