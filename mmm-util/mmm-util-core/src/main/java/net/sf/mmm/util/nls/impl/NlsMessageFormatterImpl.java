/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.nls.api.NlsArgument;
import net.sf.mmm.util.nls.api.NlsArgumentParser;
import net.sf.mmm.util.nls.api.NlsFormatter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsMessageFormatter;
import net.sf.mmm.util.scanner.base.CharSequenceScanner;
import net.sf.mmm.util.text.api.Justification;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.nls.api.NlsMessageFormatter} interface.<br>
 * <b>NOTE:</b><br>
 * This is more or less a rewrite of {@link java.text.MessageFormat} and is
 * syntax-compatible with the
 * {@link java.text.MessageFormat#applyPattern(String) MessageFormat-pattern}
 * -format. Some special (and somewhat sick) features as modifying internal
 * {@link java.text.Format}s or {@link java.text.FieldPosition} are NOT
 * supported. Currently also parsing is NOT supported.<br>
 * Instead this implementation is immutable and thread-safe. Further it works on
 * any {@link Appendable} rather than only on {@link StringBuffer}. It also uses
 * the same {@link Appendable} for recursive invocations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class NlsMessageFormatterImpl extends AbstractNlsMessageFormatter {

  /** The parsed segments of the message pattern. */
  private final PatternSegment[] segments;

  /** The suffix (last segment) of the pattern. */
  private final String suffix;

  /** The {@link NlsArgumentParser} instance to use. */
  private NlsArgumentParser argumentParser;

  /**
   * The constructor.
   * 
   * @param pattern is the pattern of the message to format. It is
   *        syntax-compatible with {@link java.text.MessageFormat}.
   * @param argumentParser is used to
   *        {@link NlsArgumentParser#parse(CharSequenceScanner) parse} the
   *        {@link NlsArgument argument parts of the message}.
   */
  public NlsMessageFormatterImpl(String pattern, NlsArgumentParser argumentParser) {

    super();
    NlsNullPointerException.checkNotNull(NlsArgumentParser.class, argumentParser);
    this.argumentParser = argumentParser;
    List<PatternSegment> segmentList = new ArrayList<PatternSegment>();
    CharSequenceScanner scanner = new CharSequenceScanner(pattern);
    String prefix = scanner.readUntil(NlsArgumentParser.START_EXPRESSION, true, SYNTAX);
    while (scanner.hasNext()) {
      NlsArgument argument = this.argumentParser.parse(scanner);
      PatternSegment segment = new PatternSegment(prefix, argument);
      segmentList.add(segment);
      prefix = scanner.readUntil(NlsArgumentParser.START_EXPRESSION, true, SYNTAX);
    }
    this.suffix = prefix;
    this.segments = segmentList.toArray(new PatternSegment[segmentList.size()]);
  }

  /**
   * {@inheritDoc}
   */
  public final void format(Void nothing, Locale locale, Map<String, Object> arguments,
      Appendable buffer, NlsTemplateResolver resolver) throws IOException {

    for (PatternSegment segment : this.segments) {
      buffer.append(segment.prefix);
      NlsArgument argument = segment.argument;
      Object value = null;
      if (arguments != null) {
        value = arguments.get(argument.getKey());
      }
      if (value == null) {
        buffer.append(NlsArgumentParser.START_EXPRESSION);
        buffer.append(argument.getKey());
        buffer.append(NlsArgumentParser.END_EXPRESSION);
      } else {
        @SuppressWarnings("unchecked")
        NlsFormatter<Object> formatter = (NlsFormatter<Object>) argument.getFormatter();
        Justification justification = argument.getJustification();
        if (justification == null) {
          formatter.format(value, locale, arguments, buffer, resolver);
        } else {
          StringBuilder sb = new StringBuilder();
          formatter.format(value, locale, arguments, sb, resolver);
          justification.justify(sb, buffer);
        }
      }
    }
    buffer.append(this.suffix);
  }

  /**
   * This inner class represents a segment out of the parsed message-pattern.<br>
   * E.g. if the message-pattern is "Hi {0} you have {1} items!" then it is
   * parsed into two {@link PatternSegment}s. The first has a {@link #prefix} of
   * <code>"Hi "</code> and {@link #argument} of <code>{0}</code> and the second
   * has <code>" you have "</code> as {@link #prefix} and {@link #argument} of
   * <code>{1}</code>. The rest of the pattern which is <code>" items!"</code>
   * will be stored in {@link NlsMessageFormatterImpl#suffix}.
   */
  protected static class PatternSegment {

    /** @see #getPrefix() */
    private final String prefix;

    /** @see #getArgument() */
    private final NlsArgument argument;

    /**
     * The constructor.
     * 
     * @param prefix is the {@link #getPrefix() prefix}.
     * @param argument is the {@link #getArgument() argument}.
     */
    public PatternSegment(String prefix, NlsArgument argument) {

      super();
      this.prefix = prefix;
      this.argument = argument;
    }

    /**
     * This method gets the prefix. This is the raw part of the message-pattern
     * (until the next '{') that will be taken as is.
     * 
     * @return the prefix
     */
    public String getPrefix() {

      return this.prefix;
    }

    /**
     * This method gets the {@link NlsArgument}.
     * 
     * @return the argument.
     */
    public NlsArgument getArgument() {

      return this.argument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      StringBuilder sb = new StringBuilder();
      sb.append(this.prefix);
      sb.append(this.argument);
      return sb.toString();
    }

  }

}
