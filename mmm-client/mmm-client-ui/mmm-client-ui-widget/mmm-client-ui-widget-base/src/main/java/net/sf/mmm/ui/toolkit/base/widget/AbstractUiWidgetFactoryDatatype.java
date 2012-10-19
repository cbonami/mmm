/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactoryDatatype;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetLongField;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRadioButtons;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextField;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.BooleanFormatter;
import net.sf.mmm.util.lang.base.FormatterToString;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link UiWidgetFactoryDatatype}. It already contains the
 * implementations for the {@link #registerStandardDatatypes() standard datatypes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetFactoryDatatype extends AbstractLoggableComponent implements
    UiWidgetFactoryDatatype {

  /** @see #createForDatatype(Class) */
  private final Map<Class<?>, UiSingleWidgetFactoryDatatype<?>> datatype2subFactoryMap;

  /** @see #getFactory() */
  private AbstractUiWidgetFactory<?> factory;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactoryDatatype() {

    super();
    this.datatype2subFactoryMap = new HashMap<Class<?>, UiSingleWidgetFactoryDatatype<?>>();
  }

  /**
   * This method registers the given {@link UiSingleWidgetFactoryReal} as sub-factory of this factory.
   * 
   * @param subFactory is the {@link UiSingleWidgetFactoryReal} to register.
   */
  protected void register(UiSingleWidgetFactoryDatatype<?> subFactory) {

    UiSingleWidgetFactoryDatatype<?> oldFactory = this.datatype2subFactoryMap.put(subFactory.getDatatype(), subFactory);
    if (oldFactory != null) {
      throw new DuplicateObjectException(subFactory, subFactory.getDatatype(), oldFactory);
    }
  }

  /**
   * This method {@link #register(UiSingleWidgetFactoryDatatype) registers} the
   * {@link UiWidgetFactoryDatatype} instances for the java standard datatypes ({@link String}, {@link Long},
   * etc.).
   */
  protected void registerStandardDatatypes() {

    register(new UiSingleWidgetFactoryDatatypeString());
    register(new UiSingleWidgetFactoryDatatypeLong());
    register(new UiSingleWidgetFactoryDatatypeDouble());
    register(new UiSingleWidgetFactoryDatatypeBoolean());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetField<VALUE> createForDatatype(Class<VALUE> datatype) {

    UiSingleWidgetFactoryDatatype<VALUE> subFactory = (UiSingleWidgetFactoryDatatype<VALUE>) this.datatype2subFactoryMap
        .get(datatype);
    if (subFactory == null) {
      throw new ObjectNotFoundException(UiSingleWidgetFactoryDatatype.class, datatype);
    }
    UiWidgetField<VALUE> widget = subFactory.create(this.factory);
    NlsNullPointerException.checkNotNull(UiWidget.class, widget);
    return widget;
  }

  /**
   * @return the {@link AbstractUiWidgetFactory} instance.
   */
  protected AbstractUiWidgetFactory<?> getFactory() {

    return this.factory;
  }

  /**
   * @param factory is the {@link AbstractUiWidgetFactory} to inject.
   */
  public void setFactory(AbstractUiWidgetFactory<?> factory) {

    this.factory = factory;
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype factory} for the datatype
   * {@link String}.
   */
  public static class UiSingleWidgetFactoryDatatypeString extends AbstractUiSingleWidgetFactoryDatatype<String> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeString() {

      super(String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<String> create(AbstractUiWidgetFactory<?> factory) {

      return factory.create(UiWidgetTextField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype factory} for the datatype
   * {@link Long}.
   */
  public static class UiSingleWidgetFactoryDatatypeLong extends AbstractUiSingleWidgetFactoryDatatype<Long> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeLong() {

      super(Long.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Long> create(AbstractUiWidgetFactory<?> factory) {

      return factory.create(UiWidgetLongField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype factory} for the datatype
   * {@link Double}.
   */
  public static class UiSingleWidgetFactoryDatatypeDouble extends AbstractUiSingleWidgetFactoryDatatype<Double> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeDouble() {

      super(Double.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Double> create(AbstractUiWidgetFactory<?> factory) {

      return factory.create(UiWidgetDoubleField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype factory} for the datatype
   * {@link Long}.
   */
  public static class UiSingleWidgetFactoryDatatypeBoolean extends AbstractUiSingleWidgetFactoryDatatype<Boolean> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeBoolean() {

      super(Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Boolean> create(AbstractUiWidgetFactory<?> factory) {

      UiWidgetRadioButtons<Boolean> radioButtons = factory.create(UiWidgetRadioButtons.class);
      radioButtons.setFormatter(BooleanFormatter.getInstance());
      radioButtons.setOptions(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
      return radioButtons;
    }
  }

  /**
   * This inner class is the abstract {@link AbstractUiSingleWidgetFactoryDatatype factory} for {@link Enum}
   * datatypes.
   * 
   * @param <E> is the generic type of the {@link Enum}.
   */
  public static class UiSingleWidgetFactoryDatatypeEnum<E extends Enum<E>> extends
      AbstractUiSingleWidgetFactoryDatatype<E> {

    /** @see #create(AbstractUiWidgetFactory) */
    private final Formatter<E> formatter;

    /**
     * The constructor.
     * 
     * @param enumType is the type of the {@link Enum}.
     */
    public UiSingleWidgetFactoryDatatypeEnum(Class<E> enumType) {

      this(enumType, FormatterToString.<E> getInstance());
    }

    /**
     * The constructor.
     * 
     * @param enumType is the type of the {@link Enum}.
     * @param formatter is the {@link Formatter} used to get the display titles of the {@link Enum} constants.
     */
    public UiSingleWidgetFactoryDatatypeEnum(Class<E> enumType, Formatter<E> formatter) {

      super(enumType);
      this.formatter = formatter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<E> create(AbstractUiWidgetFactory<?> factory) {

      UiWidgetComboBox<E> radioButtons = factory.create(UiWidgetComboBox.class);
      radioButtons.setFormatter(this.formatter);
      radioButtons.setOptions(getOptionList());
      return radioButtons;
    }

    /**
     * @return the {@link List} of enum-options. Override to filter options or to add <code>null</code>.
     */
    protected List<E> getOptionList() {

      return Arrays.asList(getDatatype().getEnumConstants());
    }
  }

}