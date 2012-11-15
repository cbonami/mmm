/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.query;

import net.sf.mmm.persistence.api.jpa.query.JpqlGroupByClause;
import net.sf.mmm.persistence.api.jpa.query.JpqlOrderByClause;
import net.sf.mmm.persistence.api.jpa.query.JpqlWhereClause;
import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the implementation of {@link JpqlWhereClause}.
 * 
 * @param <E> is the generic type of the {@link #getEntityType() entity type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class JpqlWhereClauseImpl<E> extends AbstractJpqlConditionalExpression<E, JpqlWhereClause<E>> implements
    JpqlWhereClause<E> {

  /**
   * The constructor.
   * 
   * @param context is the {@link JpqlQueryContext}.
   * @param propertyBasePath - see {@link #getPropertyBasePath()}.
   */
  public JpqlWhereClauseImpl(JpqlQueryContext<E> context, String propertyBasePath) {

    super(context, propertyBasePath);
    context.getQueryBuffer().append(JPQL_WHERE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlGroupByClause<E> groupBy() {

    dispose();
    return new JpqlGroupByClauseImpl<E>(getContext(), getPropertyBasePath());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlOrderByClause<E> orderBy(String property) {

    return orderBy(property, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JpqlOrderByClause<E> orderBy(String property, SortOrder order) {

    dispose();
    JpqlOrderByClauseImpl<E> orderByClause = new JpqlOrderByClauseImpl<E>(getContext(), getPropertyBasePath(),
        property, order);
    return orderByClause;
  }

}
