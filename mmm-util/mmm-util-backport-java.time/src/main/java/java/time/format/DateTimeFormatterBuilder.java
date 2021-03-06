/*
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package java.time.format;

import static java.time.calendrical.ChronoField.INSTANT_SECONDS;
import static java.time.calendrical.ChronoField.NANO_OF_SECOND;
import static java.time.calendrical.ChronoField.OFFSET_SECONDS;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.calendrical.ChronoField;
import java.time.calendrical.DateTimeAccessor.Query;
import java.time.calendrical.DateTimeBuilder;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.chrono.Chrono;
import java.time.chrono.ISOChrono;
import java.time.format.SimpleDateTimeTextProvider.LocaleStore;
import java.time.jdk8.Jdk7Methods;
import java.time.jdk8.Jdk8Methods;
import java.time.zone.ZoneRulesProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Builder to create date-time formatters.
 * <p>
 * This allows a {@code DateTimeFormatter} to be created. All date-time formatters are created ultimately
 * using this builder.
 * <p>
 * The basic elements of date-time can all be added:
 * <p>
 * <ul>
 * <li>Value - a numeric value</li>
 * <li>Fraction - a fractional value including the decimal place. Always use this when outputting fractions to
 * ensure that the fraction is parsed correctly</li>
 * <li>Text - the textual equivalent for the value</li>
 * <li>OffsetId/Offset - the {@link ZoneOffset zone offset}</li>
 * <li>ZoneId - the {@link ZoneId time-zone} id</li>
 * <li>ZoneText - the name of the time-zone</li>
 * <li>Literal - a text literal</li>
 * <li>Nested and Optional - formats can be nested or made optional</li>
 * <li>Other - the printer and parser interfaces can be used to add user supplied formatting</li>
 * </ul>
 * <p>
 * In addition, any of the elements may be decorated by padding, either with spaces or any other character.
 * <p>
 * Finally, a shorthand pattern, mostly compatible with {@code java.text.SimpleDateFormat SimpleDateFormat}
 * can be used, see {@link #appendPattern(String)}. In practice, this simply parses the pattern and calls
 * other methods on the builder.
 * 
 * <h4>Implementation notes</h4> This class is a mutable builder intended for use from a single thread.
 */
public final class DateTimeFormatterBuilder {

  /**
   * The currently active builder, used by the outermost builder.
   */
  private DateTimeFormatterBuilder active = this;

  /**
   * The parent builder, null for the outermost builder.
   */
  private final DateTimeFormatterBuilder parent;

  /**
   * The list of printers that will be used.
   */
  private final List<DateTimePrinterParser> printerParsers = new ArrayList<DateTimePrinterParser>();

  /**
   * Whether this builder produces an optional formatter.
   */
  private final boolean optional;

  /**
   * The width to pad the next field to.
   */
  private int padNextWidth;

  /**
   * The character to pad the next field with.
   */
  private char padNextChar;

  /**
   * The index of the last variable width value parser.
   */
  private int valueParserIndex = -1;

  /**
   * Constructs a new instance of the builder.
   */
  public DateTimeFormatterBuilder() {

    super();
    this.parent = null;
    this.optional = false;
  }

  /**
   * Constructs a new instance of the builder.
   * 
   * @param parent the parent builder, not null
   * @param optional whether the formatter is optional, not null
   */
  private DateTimeFormatterBuilder(DateTimeFormatterBuilder parent, boolean optional) {

    super();
    this.parent = parent;
    this.optional = optional;
  }

  // -----------------------------------------------------------------------
  /**
   * Changes the parse style to be case sensitive for the remainder of the formatter.
   * <p>
   * Parsing can be case sensitive or insensitive - by default it is case sensitive. This controls how text is
   * compared.
   * <p>
   * When used, this method changes the parsing to be case sensitive from this point onwards. As case
   * sensitive is the default, this is normally only needed after calling {@link #parseCaseInsensitive()}. The
   * change will remain in force until the end of the formatter that is eventually constructed or until
   * {@code parseCaseInsensitive} is called.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder parseCaseSensitive() {

    appendInternal(SettingsParser.SENSITIVE);
    return this;
  }

  /**
   * Changes the parse style to be case insensitive for the remainder of the formatter.
   * <p>
   * Parsing can be case sensitive or insensitive - by default it is case sensitive. This controls how text is
   * compared.
   * <p>
   * When used, this method changes the parsing to be case insensitive from this point onwards. The change
   * will remain in force until the end of the formatter that is eventually constructed or until
   * {@code parseCaseSensitive} is called.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder parseCaseInsensitive() {

    appendInternal(SettingsParser.INSENSITIVE);
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Changes the parse style to be strict for the remainder of the formatter.
   * <p>
   * Parsing can be strict or lenient - by default its strict. This controls the degree of flexibility in
   * matching the text and sign styles.
   * <p>
   * When used, this method changes the parsing to be strict from this point onwards. As strict is the
   * default, this is normally only needed after calling {@link #parseLenient()}. The change will remain in
   * force until the end of the formatter that is eventually constructed or until {@code parseLenient} is
   * called.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder parseStrict() {

    appendInternal(SettingsParser.STRICT);
    return this;
  }

  /**
   * Changes the parse style to be lenient for the remainder of the formatter. Note that case sensitivity is
   * set separately to this method.
   * <p>
   * Parsing can be strict or lenient - by default its strict. This controls the degree of flexibility in
   * matching the text and sign styles. Applications calling this method should typically also call
   * {@link #parseCaseInsensitive()}.
   * <p>
   * When used, this method changes the parsing to be strict from this point onwards. The change will remain
   * in force until the end of the formatter that is eventually constructed or until {@code parseStrict} is
   * called.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder parseLenient() {

    appendInternal(SettingsParser.LENIENT);
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the value of a date-time field to the formatter using a normal output style.
   * <p>
   * The value of the field will be output during a print. If the value cannot be obtained then an exception
   * will be thrown.
   * <p>
   * The value will be printed as per the normal print of an integer value. Only negative numbers will be
   * signed. No padding will be added.
   * <p>
   * The parser for a variable width value such as this normally behaves greedily, accepting as many digits as
   * possible. This behavior can be affected by 'adjacent value parsing'. See
   * {@link #appendValue(DateTimeField, int)} for full details.
   * 
   * @param field the field to append, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendValue(DateTimeField field) {

    Jdk7Methods.Objects_requireNonNull(field, "field");
    this.active.valueParserIndex = appendInternal(new NumberPrinterParser(field, 1, 19, SignStyle.NORMAL));
    return this;
  }

  /**
   * Appends the value of a date-time field to the formatter using a fixed width, zero-padded approach.
   * <p>
   * The value of the field will be output during a print. If the value cannot be obtained then an exception
   * will be thrown.
   * <p>
   * The value will be zero-padded on the left. If the size of the value means that it cannot be printed
   * within the width then an exception is thrown. If the value of the field is negative then an exception is
   * thrown during printing.
   * <p>
   * This method supports a special technique of parsing known as 'adjacent value parsing'. This technique
   * solves the problem where a variable length value is followed by one or more fixed length values. The
   * standard parser is greedy, and thus it would normally steal the digits that are needed by the fixed width
   * value parsers that follow the variable width one.
   * <p>
   * No action is required to initiate 'adjacent value parsing'. When a call to {@code appendValue} with a
   * variable width is made, the builder enters adjacent value parsing setup mode. If the immediately
   * subsequent method call or calls on the same builder are to this method, then the parser will reserve
   * space so that the fixed width values can be parsed.
   * <p>
   * For example, consider {@code builder.appendValue(YEAR).appendValue(MONTH_OF_YEAR, 2);} The year is a
   * variable width parse of between 1 and 19 digits. The month is a fixed width parse of 2 digits. Because
   * these were appended to the same builder immediately after one another, the year parser will reserve two
   * digits for the month to parse. Thus, the text '201106' will correctly parse to a year of 2011 and a month
   * of 6. Without adjacent value parsing, the year would greedily parse all six digits and leave nothing for
   * the month.
   * <p>
   * Adjacent value parsing applies to each set of fixed width not-negative values in the parser that
   * immediately follow any kind of variable width value. Calling any other append method will end the setup
   * of adjacent value parsing. Thus, in the unlikely event that you need to avoid adjacent value parsing
   * behavior, simply add the {@code appendValue} to another {@code DateTimeFormatterBuilder} and add that to
   * this builder.
   * <p>
   * If the four-parameter version of {@code appendValue} is called with equal minimum and maximum widths and
   * a sign style of not-negative then it delegates to this method.
   * 
   * @param field the field to append, not null
   * @param width the width of the printed field, from 1 to 19
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if the width is invalid
   */
  public DateTimeFormatterBuilder appendValue(DateTimeField field, int width) {

    Jdk7Methods.Objects_requireNonNull(field, "field");
    if (width < 1 || width > 19) {
      throw new IllegalArgumentException("The width must be from 1 to 19 inclusive but was " + width);
    }
    NumberPrinterParser pp = new NumberPrinterParser(field, width, width, SignStyle.NOT_NEGATIVE);
    return appendFixedWidth(width, pp);
  }

  /**
   * Appends the value of a date-time field to the formatter providing full control over printing.
   * <p>
   * The value of the field will be output during a print. If the value cannot be obtained then an exception
   * will be thrown.
   * <p>
   * This method provides full control of the numeric formatting, including zero-padding and the
   * positive/negative sign.
   * <p>
   * The parser for a variable width value normally behaves greedily, accepting as many digits as possible.
   * This behavior can be affected by 'adjacent value parsing'. See {@link #appendValue(DateTimeField, int)}
   * for full details.
   * 
   * @param field the field to append, not null
   * @param minWidth the minimum field width of the printed field, from 1 to 19
   * @param maxWidth the maximum field width of the printed field, from 1 to 19
   * @param signStyle the positive/negative output style, not null
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if the widths are invalid
   */
  public DateTimeFormatterBuilder appendValue(DateTimeField field, int minWidth, int maxWidth, SignStyle signStyle) {

    if (minWidth == maxWidth && signStyle == SignStyle.NOT_NEGATIVE) {
      return appendValue(field, maxWidth);
    }
    Jdk7Methods.Objects_requireNonNull(field, "field");
    Jdk7Methods.Objects_requireNonNull(signStyle, "signStyle");
    if (minWidth < 1 || minWidth > 19) {
      throw new IllegalArgumentException("The minimum width must be from 1 to 19 inclusive but was " + minWidth);
    }
    if (maxWidth < 1 || maxWidth > 19) {
      throw new IllegalArgumentException("The maximum width must be from 1 to 19 inclusive but was " + maxWidth);
    }
    if (maxWidth < minWidth) {
      throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " + maxWidth
          + " < " + minWidth);
    }
    NumberPrinterParser pp = new NumberPrinterParser(field, minWidth, maxWidth, signStyle);
    if (minWidth == maxWidth) {
      appendInternal(pp);
    } else {
      this.active.valueParserIndex = appendInternal(pp);
    }
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the reduced value of a date-time field to the formatter.
   * <p>
   * This is typically used for printing and parsing a two digit year. The {@code width} is the printed and
   * parsed width. The {@code baseValue} is used during parsing to determine the valid range.
   * <p>
   * For printing, the width is used to determine the number of characters to print. The rightmost characters
   * are output to match the width, left padding with zero.
   * <p>
   * For parsing, exactly the number of characters specified by the width are parsed. This is incomplete
   * information however, so the base value is used to complete the parse. The base value is the first valid
   * value in a range of ten to the power of width.
   * <p>
   * For example, a base value of {@code 1980} and a width of {@code 2} will have valid values from
   * {@code 1980} to {@code 2079}. During parsing, the text {@code "12"} will result in the value {@code 2012}
   * as that is the value within the range where the last two digits are "12".
   * <p>
   * This is a fixed width parser operating using 'adjacent value parsing'. See
   * {@link #appendValue(DateTimeField, int)} for full details.
   * 
   * @param field the field to append, not null
   * @param width the width of the printed and parsed field, from 1 to 18
   * @param baseValue the base value of the range of valid values
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if the width or base value is invalid
   */
  public DateTimeFormatterBuilder appendValueReduced(DateTimeField field, int width, int baseValue) {

    Jdk7Methods.Objects_requireNonNull(field, "field");
    ReducedPrinterParser pp = new ReducedPrinterParser(field, width, baseValue);
    appendFixedWidth(width, pp);
    return this;
  }

  /**
   * Appends a fixed width printer-parser.
   * 
   * @param width the width
   * @param pp the printer-parser, not null
   * @return this, for chaining, not null
   */
  private DateTimeFormatterBuilder appendFixedWidth(int width, NumberPrinterParser pp) {

    if (this.active.valueParserIndex >= 0) {
      NumberPrinterParser basePP = (NumberPrinterParser) this.active.printerParsers.get(this.active.valueParserIndex);
      basePP = basePP.withSubsequentWidth(width);
      int activeValueParser = this.active.valueParserIndex;
      this.active.printerParsers.set(this.active.valueParserIndex, basePP);
      appendInternal(pp);
      this.active.valueParserIndex = activeValueParser;
    } else {
      appendInternal(pp);
    }
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the fractional value of a date-time field to the formatter.
   * <p>
   * The fractional value of the field will be output including the preceeding decimal point. The preceeding
   * value is not output. For example, the second-of-minute value of 15 would be output as {@code .25}.
   * <p>
   * The width of the printed fraction can be controlled. Setting the minimum width to zero will cause no
   * output to be generated. The printed fraction will have the minimum width necessary between the minimum
   * and maximum widths - trailing zeroes are omitted. No rounding occurs due to the maximum width - digits
   * are simply dropped.
   * <p>
   * When parsing in strict mode, the number of parsed digits must be between the minimum and maximum width.
   * When parsing in lenient mode, the minimum width is considered to be zero and the maximum is nine.
   * <p>
   * If the value cannot be obtained then an exception will be thrown. If the value is negative an exception
   * will be thrown. If the field does not have a fixed set of valid values then an exception will be thrown.
   * If the field value in the date-time to be printed is invalid it cannot be printed and an exception will
   * be thrown.
   * 
   * @param field the field to append, not null
   * @param minWidth the minimum width of the field excluding the decimal point, from 0 to 9
   * @param maxWidth the maximum width of the field excluding the decimal point, from 1 to 9
   * @param decimalPoint whether to output the localized decimal point symbol
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if the field has a variable set of valid values
   * @throws IllegalArgumentException if either width is invalid
   */
  public DateTimeFormatterBuilder appendFraction(DateTimeField field, int minWidth, int maxWidth, boolean decimalPoint) {

    appendInternal(new FractionPrinterParser(field, minWidth, maxWidth, decimalPoint));
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the text of a date-time field to the formatter using the full text style.
   * <p>
   * The text of the field will be output during a print. The value must be within the valid range of the
   * field. If the value cannot be obtained then an exception will be thrown. If the field has no textual
   * representation, then the numeric value will be used.
   * <p>
   * The value will be printed as per the normal print of an integer value. Only negative numbers will be
   * signed. No padding will be added.
   * 
   * @param field the field to append, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendText(DateTimeField field) {

    return appendText(field, TextStyle.FULL);
  }

  /**
   * Appends the text of a date-time field to the formatter.
   * <p>
   * The text of the field will be output during a print. The value must be within the valid range of the
   * field. If the value cannot be obtained then an exception will be thrown. If the field has no textual
   * representation, then the numeric value will be used.
   * <p>
   * The value will be printed as per the normal print of an integer value. Only negative numbers will be
   * signed. No padding will be added.
   * 
   * @param field the field to append, not null
   * @param textStyle the text style to use, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendText(DateTimeField field, TextStyle textStyle) {

    Jdk7Methods.Objects_requireNonNull(field, "field");
    Jdk7Methods.Objects_requireNonNull(textStyle, "textStyle");
    appendInternal(new TextPrinterParser(field, textStyle, DateTimeFormatters.getTextProvider()));
    return this;
  }

  /**
   * Appends the text of a date-time field to the formatter using the specified map to supply the text.
   * <p>
   * The standard text outputting methods use the localized text in the JDK. This method allows that text to
   * be specified directly. The supplied map is not validated by the builder to ensure that printing or
   * parsing is possible, thus an invalid map may throw an error during later use.
   * <p>
   * Supplying the map of text provides considerable flexibility in printing and parsing. For example, a
   * legacy application might require or supply the months of the year as "JNY", "FBY", "MCH" etc. These do
   * not match the standard set of text for localized month names. Using this method, a map can be created
   * which defines the connection between each value and the text:
   * 
   * <pre>
     * Map&lt;Long, String&gt; map = new HashMap&lt;&gt;();
     * map.put(1, "JNY");
     * map.put(2, "FBY");
     * map.put(3, "MCH");
     * ...
     * builder.appendText(MONTH_OF_YEAR, map);
     * </pre>
   * <p>
   * Other uses might be to output the value with a suffix, such as "1st", "2nd", "3rd", or as Roman numerals
   * "I", "II", "III", "IV".
   * <p>
   * During printing, the value is obtained and checked that it is in the valid range. If text is not
   * available for the value then it is output as a number. During parsing, the parser will match against the
   * map of text and numeric values.
   * 
   * @param field the field to append, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendText(DateTimeField field, Map<Long, String> textLookup) {

    Jdk7Methods.Objects_requireNonNull(field, "field");
    Jdk7Methods.Objects_requireNonNull(textLookup, "textLookup");
    Map<Long, String> copy = new LinkedHashMap<Long, String>(textLookup);
    Map<TextStyle, Map<Long, String>> map = Collections.singletonMap(TextStyle.FULL, copy);
    final LocaleStore store = new LocaleStore(map);
    DateTimeTextProvider provider = new DateTimeTextProvider() {

      @Override
      public String getText(DateTimeField field, long value, TextStyle style, Locale locale) {

        return store.getText(value, style);
      }

      @Override
      public Iterator<Entry<String, Long>> getTextIterator(DateTimeField field, TextStyle style, Locale locale) {

        return store.getTextIterator(style);
      }

      @Override
      public Locale[] getAvailableLocales() {

        throw new UnsupportedOperationException();
      }
    };
    appendInternal(new TextPrinterParser(field, TextStyle.FULL, provider));
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends an instant using ISO-8601 to the formatter.
   * <p>
   * Instants have a fixed output format. They are converted to a date-time with a zone-offset of UTC and
   * printed using the standard ISO-8601 format.
   * <p>
   * An alternative to this method is to print/parse the instant as a single epoch-seconds value. That is
   * achieved using {@code appendValue(INSTANT_SECONDS)}.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendInstant() {

    appendInternal(new InstantPrinterParser());
    return this;
  }

  /**
   * Appends the zone offset, such as '+01:00', to the formatter.
   * <p>
   * The zone offset ID will be output during a print. If the offset cannot be obtained then an exception will
   * be thrown. The format is defined by {@link ZoneOffset#getId()}.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendOffsetId() {

    return appendOffset("+HH:MM:ss", "Z");
  }

  /**
   * Appends the zone offset, such as '+01:00', to the formatter.
   * <p>
   * The zone offset will be output during a print. If the offset cannot be obtained then an exception will be
   * thrown.
   * <p>
   * The output format is controlled by a pattern which must be one of the following:
   * <p>
   * <ul>
   * <li>{@code +HH} - hour only, truncating any minute
   * <li>{@code +HHMM} - hour and minute, no colon
   * <li>{@code +HH:MM} - hour and minute, with colon
   * <li>{@code +HHMMss} - hour and minute, with second if non-zero and no colon
   * <li>{@code +HH:MM:ss} - hour and minute, with second if non-zero and colon
   * <li>{@code +HHMMSS} - hour, minute and second, no colon
   * <li>{@code +HH:MM:SS} - hour, minute and second, with colon
   * </ul>
   * <p>
   * The "no offset" text controls what text is printed when the offset is zero. Example values would be 'Z',
   * '+00:00', 'UTC' or 'GMT'. Three formats are accepted for parsing UTC - the "no offset" text, and the plus
   * and minus versions of zero defined by the pattern.
   * <p>
   * The include colon parameter controls whether a colon should separate the numeric fields or not.
   * <p>
   * The allow seconds parameter controls whether seconds may be output. If false then seconds are never
   * output. If true then seconds are only output if non-zero.
   * 
   * @param pattern the pattern to use
   * @param noOffsetText the text to use when the offset is zero, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendOffset(String pattern, String noOffsetText) {

    appendInternal(new ZoneOffsetPrinterParser(noOffsetText, pattern));
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the time-zone ID, such as 'Europe/Paris' or '+02:00', to the formatter.
   * <p>
   * A {@link ZoneId} can be either a {@code ZoneOffset} or an ID of a region such as 'America/New_York'. This
   * prints and parses both types. If the zone cannot be obtained then an exception will be thrown.
   * 
   * @return this, for chaining, not null
   * @see #appendZoneRegionId()
   */
  public DateTimeFormatterBuilder appendZoneId() {

    appendInternal(new ZoneIdPrinterParser(false));
    return this;
  }

  /**
   * Appends the time-zone region ID, such as 'Europe/Paris', to the formatter, printing nothing if the zone
   * ID is a {@code ZoneOffset}.
   * <p>
   * A {@link ZoneId} can be either a {@code ZoneOffset} or an ID of a region such as 'America/New_York'. This
   * only prints if the ID is a region. Both types are parsed, however the parsing of the offset is optional.
   * If the zone cannot be obtained then an exception will be thrown.
   * 
   * @return this, for chaining, not null
   * @see #appendZoneId()
   */
  public DateTimeFormatterBuilder appendZoneRegionId() {

    appendInternal(new ZoneIdPrinterParser(true));
    return this;
  }

  /**
   * Appends the time-zone name, such as 'British Summer Time', to the formatter.
   * <p>
   * The time-zone name will be output during a print. If the zone cannot be obtained then an exception will
   * be thrown.
   * <p>
   * The zone name is obtained from the formatting symbols. Different names may be output depending on whether
   * daylight saving time applies.
   * <p>
   * If the date, time or offset cannot be obtained it may not be possible to determine which text to output.
   * In this case, the text representing time without daylight savings (winter time) will be used.
   * 
   * @param textStyle the text style to use, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendZoneText(TextStyle textStyle) {

    appendInternal(new ZoneTextPrinterParser(textStyle));
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the chronology ID to the formatter.
   * <p>
   * The chronology ID will be output during a print. If the chronology cannot be obtained then an exception
   * will be thrown.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendChronoId() {

    appendInternal(new ChronoPrinterParser(null));
    return this;
  }

  /**
   * Appends the chronology name to the formatter.
   * <p>
   * The calendar system name will be output during a print. If the chronology cannot be obtained then an
   * exception will be thrown. The calendar system name is obtained from the formatting symbols.
   * 
   * @param textStyle the text style to use, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendChronoText(TextStyle textStyle) {

    Jdk7Methods.Objects_requireNonNull(textStyle, "textStyle");
    appendInternal(new ChronoPrinterParser(textStyle));
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends a localized date-time pattern to the formatter.
   * <p>
   * The pattern is resolved lazily using the locale being used during the print/parse (stored in
   * {@link DateTimeFormatter}.
   * <p>
   * The pattern can vary by chronology, although typically it doesn't. This method uses the standard ISO
   * chronology patterns.
   * 
   * @param dateStyle the date style to use, null means no date required
   * @param timeStyle the time style to use, null means no time required
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendLocalized(FormatStyle dateStyle, FormatStyle timeStyle) {

    return appendLocalized(dateStyle, timeStyle, ISOChrono.INSTANCE);
  }

  /**
   * Appends a localized date-time pattern to the formatter.
   * <p>
   * The pattern is resolved lazily using the locale being used during the print/parse (stored in
   * {@link DateTimeFormatter}.
   * <p>
   * The pattern can vary by chronology, although typically it doesn't. This method allows the chronology to
   * be specified.
   * 
   * @param dateStyle the date style to use, null means no date required
   * @param timeStyle the time style to use, null means no time required
   * @param chrono the chronology to use, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendLocalized(FormatStyle dateStyle, FormatStyle timeStyle, Chrono<?> chrono) {

    Jdk7Methods.Objects_requireNonNull(chrono, "chrono");
    if (dateStyle != null || timeStyle != null) {
      appendInternal(new LocalizedPrinterParser(dateStyle, timeStyle, chrono));
    }
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends a character literal to the formatter.
   * <p>
   * This character will be output during a print.
   * 
   * @param literal the literal to append, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendLiteral(char literal) {

    appendInternal(new CharLiteralPrinterParser(literal));
    return this;
  }

  /**
   * Appends a string literal to the formatter.
   * <p>
   * This string will be output during a print.
   * <p>
   * If the literal is empty, nothing is added to the formatter.
   * 
   * @param literal the literal to append, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendLiteral(String literal) {

    Jdk7Methods.Objects_requireNonNull(literal, "literal");
    if (literal.length() > 0) {
      if (literal.length() == 1) {
        appendInternal(new CharLiteralPrinterParser(literal.charAt(0)));
      } else {
        appendInternal(new StringLiteralPrinterParser(literal));
      }
    }
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends all the elements of a formatter to the builder.
   * <p>
   * This method has the same effect as appending each of the constituent parts of the formatter directly to
   * this builder.
   * 
   * @param formatter the formatter to add, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder append(DateTimeFormatter formatter) {

    Jdk7Methods.Objects_requireNonNull(formatter, "formatter");
    appendInternal(formatter.toPrinterParser(false));
    return this;
  }

  /**
   * Appends a formatter to the builder which will optionally print/parse.
   * <p>
   * This method has the same effect as appending each of the constituent parts directly to this builder
   * surrounded by an {@link #optionalStart()} and {@link #optionalEnd()}.
   * <p>
   * The formatter will print if data is available for all the fields contained within it. The formatter will
   * parse if the string matches, otherwise no error is returned.
   * 
   * @param formatter the formatter to add, not null
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder appendOptional(DateTimeFormatter formatter) {

    Jdk7Methods.Objects_requireNonNull(formatter, "formatter");
    appendInternal(formatter.toPrinterParser(true));
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends the elements defined by the specified pattern to the builder.
   * <p>
   * All letters 'A' to 'Z' and 'a' to 'z' are reserved as pattern letters. The following pattern letters are
   * defined:
   * 
   * <pre>
     *  Symbol  Meaning                     Presentation      Examples
     *  ------  -------                     ------------      -------
     *   G       era                         number/text       1; 01; AD; Anno Domini
     *   y       year                        year              2004; 04
     *   D       day-of-year                 number            189
     *   M       month-of-year               number/text       7; 07; Jul; July; J
     *   d       day-of-month                number            10
     *
     *   Q       quarter-of-year             number/text       3; 03; Q3
     *   Y       week-based-year             year              1996; 96
     *   w       week-of-week-based-year     number            27
     *   E       day-of-week                 number/text       2; Tue; Tuesday; T
     *   F       week-of-month               number            3
     *
     *   a       am-pm-of-day                text              PM
     *   h       clock-hour-of-am-pm (1-12)  number            12
     *   K       hour-of-am-pm (0-11)        number            0
     *   k       clock-hour-of-am-pm (1-24)  number            0
     *
     *   H       hour-of-day (0-23)          number            0
     *   m       minute-of-hour              number            30
     *   s       second-of-minute            number            55
     *   S       fraction-of-second          fraction          978
     *   A       milli-of-day                number            1234
     *   n       nano-of-second              number            987654321
     *   N       nano-of-day                 number            1234000000
     *
     *   I       time-zone ID                zoneId            America/Los_Angeles
     *   z       time-zone name              text              Pacific Standard Time; PST
     *   Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
     *   X       zone-offset 'Z' for zero    offset-X          Z; -0800; -08:00;
     *
     *   p       pad next                    pad modifier      1
     *
     *   '       escape for text             delimiter
     *   ''      single quote                literal           '
     *   [       optional section start
     *   ]       optional section end
     * </pre>
   * <p>
   * The count of pattern letters determine the format.
   * <p>
   * <b>Text</b>: The text style is determined based on the number of pattern letters used. Less than 4
   * pattern letters will use the {@link TextStyle#SHORT short form}. Exactly 4 pattern letters will use the
   * {@link TextStyle#FULL full form}. Exactly 5 pattern letters will use the {@link TextStyle#NARROW narrow
   * form}.
   * <p>
   * <b>Number</b>: If the count of letters is one, then the value is printed using the minimum number of
   * digits and without padding as per {@link #appendValue(DateTimeField)}. Otherwise, the count of digits is
   * used as the width of the output field as per {@link #appendValue(DateTimeField, int)}.
   * <p>
   * <b>Number/Text</b>: If the count of pattern letters is 3 or greater, use the Text rules above. Otherwise
   * use the Number rules above.
   * <p>
   * <b>Fraction</b>: Outputs the nano-of-second field as a fraction-of-second. The nano-of-second value has
   * nine digits, thus the count of pattern letters is from 1 to 9. If it is less than 9, then the
   * nano-of-second value is truncated, with only the most significant digits being output. When parsing in
   * strict mode, the number of parsed digits must match the count of pattern letters. When parsing in lenient
   * mode, the number of parsed digits must be at least the count of pattern letters, up to 9 digits.
   * <p>
   * <b>Year</b>: The count of letters determines the minimum field width below which padding is used. If the
   * count of letters is two, then a {@link #appendValueReduced reduced} two digit form is used. For printing,
   * this outputs the rightmost two digits. For parsing, this will parse using the base value of 2000,
   * resulting in a year within the range 2000 to 2099 inclusive. If the count of letters is less than four
   * (but not two), then the sign is only output for negative years as per {@link SignStyle#NORMAL}.
   * Otherwise, the sign is output if the pad width is exceeded, as per {@link SignStyle#EXCEEDS_PAD}
   * <p>
   * <b>ZoneId</b>: 'I' outputs the zone ID, such as 'Europe/Paris'.
   * <p>
   * <b>Offset X</b>: This formats the offset using 'Z' when the offset is zero. One letter outputs just the
   * hour', such as '+01' Two letters outputs the hour and minute, without a colon, such as '+0130'. Three
   * letters outputs the hour and minute, with a colon, such as '+01:30'. Four letters outputs the hour and
   * minute and optional second, without a colon, such as '+013015'. Five letters outputs the hour and minute
   * and optional second, with a colon, such as '+01:30:15'.
   * <p>
   * <b>Offset Z</b>: This formats the offset using '+0000' or '+00:00' when the offset is zero. One or two
   * letters outputs the hour and minute, without a colon, such as '+0130'. Three letters outputs the hour and
   * minute, with a colon, such as '+01:30'.
   * <p>
   * <b>Zone names</b>: Time zone names ('z') cannot be parsed.
   * <p>
   * <b>Optional section</b>: The optional section markers work exactly like calling {@link #optionalStart()}
   * and {@link #optionalEnd()}.
   * <p>
   * <b>Pad modifier</b>: Modifies the pattern that immediately follows to be padded with spaces. The pad
   * width is determined by the number of pattern letters. This is the same as calling {@link #padNext(int)}.
   * <p>
   * For example, 'ppH' outputs the hour-of-day padded on the left with spaces to a width of 2.
   * <p>
   * Any unrecognized letter is an error. Any non-letter character, other than '[', ']' and the single quote
   * will be output directly. Despite this, it is recommended to use single quotes around all characters that
   * you want to output directly to ensure that future changes do not break your application.
   * <p>
   * The pattern string is similar, but not identical, to {@link java.text.SimpleDateFormat SimpleDateFormat}.
   * Pattern letters 'E' and 'u' are merged, which changes the meaning of "E" and "EE" to be numeric. Pattern
   * letter 'W' is not available. Pattern letters 'Z' and 'X' are extended. Pattern letter 'y' and 'Y' parse
   * years of two digits and more than 4 digits differently. Pattern letters 'n', 'A', 'N', 'I' and 'p' are
   * added. Number types will reject large numbers. The pattern string is also similar, but not identical, to
   * that defined by the Unicode Common Locale Data Repository (CLDR).
   * 
   * @param pattern the pattern to add, not null
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if the pattern is invalid
   */
  public DateTimeFormatterBuilder appendPattern(String pattern) {

    Jdk7Methods.Objects_requireNonNull(pattern, "pattern");
    parsePattern(pattern);
    return this;
  }

  private void parsePattern(String pattern) {

    for (int pos = 0; pos < pattern.length(); pos++) {
      char cur = pattern.charAt(pos);
      if ((cur >= 'A' && cur <= 'Z') || (cur >= 'a' && cur <= 'z')) {
        int start = pos++;
        for (; pos < pattern.length() && pattern.charAt(pos) == cur; pos++) {
          ; // short loop
        }
        int count = pos - start;
        // padding
        if (cur == 'p') {
          int pad = 0;
          if (pos < pattern.length()) {
            cur = pattern.charAt(pos);
            if ((cur >= 'A' && cur <= 'Z') || (cur >= 'a' && cur <= 'z')) {
              pad = count;
              start = pos++;
              for (; pos < pattern.length() && pattern.charAt(pos) == cur; pos++) {
                ; // short loop
              }
              count = pos - start;
            }
          }
          if (pad == 0) {
            throw new IllegalArgumentException("Pad letter 'p' must be followed by valid pad pattern: " + pattern);
          }
          padNext(pad); // pad and continue parsing
        }
        // main rules
        DateTimeField field = FIELD_MAP.get(cur);
        if (field != null) {
          parseField(cur, count, field);
        } else if (cur == 'z') {
          if (count < 4) {
            appendZoneText(TextStyle.SHORT);
          } else {
            appendZoneText(TextStyle.FULL);
          }
        } else if (cur == 'I') {
          appendZoneId();
        } else if (cur == 'Z') {
          if (count > 3) {
            throw new IllegalArgumentException("Too many pattern letters: " + cur);
          }
          if (count < 3) {
            appendOffset("+HHMM", "+0000");
          } else {
            appendOffset("+HH:MM", "+00:00");
          }
        } else if (cur == 'X') {
          if (count > 5) {
            throw new IllegalArgumentException("Too many pattern letters: " + cur);
          }
          appendOffset(ZoneOffsetPrinterParser.PATTERNS[count - 1], "Z");
        } else {
          throw new IllegalArgumentException("Unknown pattern letter: " + cur);
        }
        pos--;

      } else if (cur == '\'') {
        // parse literals
        int start = pos++;
        for (; pos < pattern.length(); pos++) {
          if (pattern.charAt(pos) == '\'') {
            if (pos + 1 < pattern.length() && pattern.charAt(pos + 1) == '\'') {
              pos++;
            } else {
              break; // end of literal
            }
          }
        }
        if (pos >= pattern.length()) {
          throw new IllegalArgumentException("Pattern ends with an incomplete string literal: " + pattern);
        }
        String str = pattern.substring(start + 1, pos);
        if (str.length() == 0) {
          appendLiteral('\'');
        } else {
          appendLiteral(str.replace("''", "'"));
        }

      } else if (cur == '[') {
        optionalStart();

      } else if (cur == ']') {
        if (this.active.parent == null) {
          throw new IllegalArgumentException("Pattern invalid as it contains ] without previous [");
        }
        optionalEnd();

      } else {
        appendLiteral(cur);
      }
    }
  }

  private void parseField(char cur, int count, DateTimeField field) {

    switch (cur) {
      case 'y':
      case 'Y':
        if (count == 2) {
          appendValueReduced(field, 2, 2000);
        } else if (count < 4) {
          appendValue(field, count, 19, SignStyle.NORMAL);
        } else {
          appendValue(field, count, 19, SignStyle.EXCEEDS_PAD);
        }
        break;
      case 'G':
      case 'M':
      case 'Q':
      case 'E':
        switch (count) {
          case 1:
            appendValue(field);
            break;
          case 2:
            appendValue(field, 2);
            break;
          case 3:
            appendText(field, TextStyle.SHORT);
            break;
          case 4:
            appendText(field, TextStyle.FULL);
            break;
          case 5:
            appendText(field, TextStyle.NARROW);
            break;
          default :
            throw new IllegalArgumentException("Too many pattern letters: " + cur);
        }
        break;
      case 'a':
        switch (count) {
          case 1:
          case 2:
          case 3:
            appendText(field, TextStyle.SHORT);
            break;
          case 4:
            appendText(field, TextStyle.FULL);
            break;
          case 5:
            appendText(field, TextStyle.NARROW);
            break;
          default :
            throw new IllegalArgumentException("Too many pattern letters: " + cur);
        }
        break;
      case 'S':
        appendFraction(NANO_OF_SECOND, count, count, false);
        break;
      default :
        if (count == 1) {
          appendValue(field);
        } else {
          appendValue(field, count);
        }
        break;
    }
  }

  /** Map of letters to fields. */
  private static final Map<Character, DateTimeField> FIELD_MAP = new HashMap<Character, DateTimeField>();
  static {
    FIELD_MAP.put('G', ChronoField.ERA); // Java, CLDR (different to both for 1/2 chars)
    FIELD_MAP.put('y', ChronoField.YEAR); // CLDR
    // FIELD_MAP.put('y', ChronoField.YEAR_OF_ERA); // Java, CLDR // TODO redefine from above
    // FIELD_MAP.put('u', ChronoField.YEAR); // CLDR // TODO
    // FIELD_MAP.put('Y', ISODateTimeField.WEEK_BASED_YEAR); // Java7, CLDR (needs localized week number) //
    // TODO
    // FIELD_MAP.put('Q', QuarterYearField.QUARTER_OF_YEAR); // CLDR (removed quarter from 310)
    // FIELD_MAP.put('q', QuarterYearField.QUARTER_OF_YEAR); // CLDR (needs standalone data) // TODO
    FIELD_MAP.put('M', ChronoField.MONTH_OF_YEAR); // Java, CLDR
    // FIELD_MAP.put('L', ChronoField.MONTH_OF_YEAR); // Java, CLDR (needs standalone data) // TODO
    // FIELD_MAP.put('w', ISODateTimeField.WEEK_OF_WEEK_BASED_YEAR); // Java, CLDR (needs localized week
    // number) // TODO
    FIELD_MAP.put('D', ChronoField.DAY_OF_YEAR); // Java, CLDR
    FIELD_MAP.put('d', ChronoField.DAY_OF_MONTH); // Java, CLDR
    FIELD_MAP.put('F', ChronoField.ALIGNED_WEEK_OF_MONTH); // Java, CLDR
    FIELD_MAP.put('E', ChronoField.DAY_OF_WEEK); // Java, CLDR (different to both for 1/2 chars)
    // FIELD_MAP.put('e', ChronoField.DAY_OF_WEEK); // CLDR (needs localized week number) // TODO
    // FIELD_MAP.put('c', ChronoField.DAY_OF_WEEK); // CLDR (needs standalone data) // TODO
    FIELD_MAP.put('a', ChronoField.AMPM_OF_DAY); // Java, CLDR
    FIELD_MAP.put('H', ChronoField.HOUR_OF_DAY); // Java, CLDR
    FIELD_MAP.put('k', ChronoField.CLOCK_HOUR_OF_DAY); // Java, CLDR
    FIELD_MAP.put('K', ChronoField.HOUR_OF_AMPM); // Java, CLDR
    FIELD_MAP.put('h', ChronoField.CLOCK_HOUR_OF_AMPM); // Java, CLDR
    FIELD_MAP.put('m', ChronoField.MINUTE_OF_HOUR); // Java, CLDR
    FIELD_MAP.put('s', ChronoField.SECOND_OF_MINUTE); // Java, CLDR
    FIELD_MAP.put('S', ChronoField.NANO_OF_SECOND); // CLDR (Java uses milli-of-second number)
    FIELD_MAP.put('A', ChronoField.MILLI_OF_DAY); // CLDR
    FIELD_MAP.put('n', ChronoField.NANO_OF_SECOND); // 310
    FIELD_MAP.put('N', ChronoField.NANO_OF_DAY); // 310
    // reserved - z,Z,X,I,p
    // Java - X - compatible, but extended to 4 and 5 letters
    // Java - u - clashes with CLDR, go with CLDR (year-proleptic) here
    // CLDR - U - cycle year name, not supported by 310 yet
    // CLDR - l - deprecated
    // CLDR - W - week-of-month following CLDR rules
    // CLDR - j - not relevant
    // CLDR - g - modified-julian-day
    // CLDR - z - time-zone names // TODO properly
    // CLDR - Z - different approach here // TODO bring 310 in line with CLDR
    // CLDR - v,V - extended time-zone names
    // 310 - I - time-zone id
    // 310 - p - prefix for padding
  }

  // -----------------------------------------------------------------------
  /**
   * Causes the next added printer/parser to pad to a fixed width using a space.
   * <p>
   * This padding will pad to a fixed width using spaces.
   * <p>
   * An exception will be thrown during printing if the pad width is exceeded.
   * 
   * @param padWidth the pad width, 1 or greater
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if pad width is too small
   */
  public DateTimeFormatterBuilder padNext(int padWidth) {

    return padNext(padWidth, ' ');
  }

  /**
   * Causes the next added printer/parser to pad to a fixed width.
   * <p>
   * This padding is intended for padding other than zero-padding. Zero-padding should be achieved using the
   * appendValue methods.
   * <p>
   * An exception will be thrown during printing if the pad width is exceeded.
   * 
   * @param padWidth the pad width, 1 or greater
   * @param padChar the pad character
   * @return this, for chaining, not null
   * @throws IllegalArgumentException if pad width is too small
   */
  public DateTimeFormatterBuilder padNext(int padWidth, char padChar) {

    if (padWidth < 1) {
      throw new IllegalArgumentException("The pad width must be at least one but was " + padWidth);
    }
    this.active.padNextWidth = padWidth;
    this.active.padNextChar = padChar;
    this.active.valueParserIndex = -1;
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Mark the start of an optional section.
   * <p>
   * The output of printing can include optional sections, which may be nested. An optional section is started
   * by calling this method and ended by calling {@link #optionalEnd()} or by ending the build process.
   * <p>
   * All elements in the optional section are treated as optional. During printing, the section is only output
   * if data is available in the {@code DateTimeAccessor} for all the elements in the section. During parsing,
   * the whole section may be missing from the parsed string.
   * <p>
   * For example, consider a builder setup as
   * {@code builder.appendValue(HOUR_OF_DAY,2).optionalStart().appendValue(MINUTE_OF_HOUR,2)}. The optional
   * section ends automatically at the end of the builder. During printing, the minute will only be output if
   * its value can be obtained from the date-time. During parsing, the input will be successfully parsed
   * whether the minute is present or not.
   * 
   * @return this, for chaining, not null
   */
  public DateTimeFormatterBuilder optionalStart() {

    this.active.valueParserIndex = -1;
    this.active = new DateTimeFormatterBuilder(this.active, true);
    return this;
  }

  /**
   * Ends an optional section.
   * <p>
   * The output of printing can include optional sections, which may be nested. An optional section is started
   * by calling {@link #optionalStart()} and ended using this method (or at the end of the builder).
   * <p>
   * Calling this method without having previously called {@code optionalStart} will throw an exception.
   * Calling this method immediately after calling {@code optionalStart} has no effect on the formatter other
   * than ending the (empty) optional section.
   * <p>
   * All elements in the optional section are treated as optional. During printing, the section is only output
   * if data is available in the {@code DateTimeAccessor} for all the elements in the section. During parsing,
   * the whole section may be missing from the parsed string.
   * <p>
   * For example, consider a builder setup as
   * {@code builder.appendValue(HOUR_OF_DAY,2).optionalStart().appendValue(MINUTE_OF_HOUR,2).optionalEnd()}.
   * During printing, the minute will only be output if its value can be obtained from the date-time. During
   * parsing, the input will be successfully parsed whether the minute is present or not.
   * 
   * @return this, for chaining, not null
   * @throws IllegalStateException if there was no previous call to {@code optionalStart}
   */
  public DateTimeFormatterBuilder optionalEnd() {

    if (this.active.parent == null) {
      throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
    }
    if (this.active.printerParsers.size() > 0) {
      CompositePrinterParser cpp = new CompositePrinterParser(this.active.printerParsers, this.active.optional);
      this.active = this.active.parent;
      appendInternal(cpp);
    } else {
      this.active = this.active.parent;
    }
    return this;
  }

  // -----------------------------------------------------------------------
  /**
   * Appends a printer and/or parser to the internal list handling padding.
   * 
   * @param pp the printer-parser to add, not null
   * @return the index into the active parsers list
   */
  private int appendInternal(DateTimePrinterParser pp) {

    Jdk7Methods.Objects_requireNonNull(pp, "pp");
    if (this.active.padNextWidth > 0) {
      if (pp != null) {
        pp = new PadPrinterParserDecorator(pp, this.active.padNextWidth, this.active.padNextChar);
      }
      this.active.padNextWidth = 0;
      this.active.padNextChar = 0;
    }
    this.active.printerParsers.add(pp);
    this.active.valueParserIndex = -1;
    return this.active.printerParsers.size() - 1;
  }

  // -----------------------------------------------------------------------
  /**
   * Completes this builder by creating the DateTimeFormatter using the default locale.
   * <p>
   * This will create a formatter with the default locale. Numbers will be printed and parsed using the
   * standard non-localized set of symbols.
   * <p>
   * Calling this method will end any open optional sections by repeatedly calling {@link #optionalEnd()}
   * before creating the formatter.
   * <p>
   * This builder can still be used after creating the formatter if desired, although the state may have been
   * changed by calls to {@code optionalEnd}.
   * 
   * @return the created formatter, not null
   */
  public DateTimeFormatter toFormatter() {

    return toFormatter(Locale.getDefault());
  }

  /**
   * Completes this builder by creating the DateTimeFormatter using the specified locale.
   * <p>
   * This will create a formatter with the specified locale. Numbers will be printed and parsed using the
   * standard non-localized set of symbols.
   * <p>
   * Calling this method will end any open optional sections by repeatedly calling {@link #optionalEnd()}
   * before creating the formatter.
   * <p>
   * This builder can still be used after creating the formatter if desired, although the state may have been
   * changed by calls to {@code optionalEnd}.
   * 
   * @param locale the locale to use for formatting, not null
   * @return the created formatter, not null
   */
  public DateTimeFormatter toFormatter(Locale locale) {

    Jdk7Methods.Objects_requireNonNull(locale, "locale");
    while (this.active.parent != null) {
      optionalEnd();
    }
    return new DateTimeFormatter(new CompositePrinterParser(this.printerParsers, false), locale,
        DateTimeFormatSymbols.STANDARD);
  }

  // -----------------------------------------------------------------------
  /**
   * Strategy for printing/parsing date-time information.
   * <p>
   * The printer may print any part, or the whole, of the input date-time object. Typically, a complete print
   * is constructed from a number of smaller units, each outputting a single field.
   * <p>
   * The parser may parse any piece of text from the input, storing the result in the context. Typically, each
   * individual parser will just parse one field, such as the day-of-month, storing the value in the context.
   * Once the parse is complete, the caller will then convert the context to a {@link DateTimeBuilder} to
   * merge the parsed values to create the desired object, such as a {@code LocalDate}.
   * <p>
   * The parse position will be updated during the parse. Parsing will start at the specified index and the
   * return value specifies the new parse position for the next parser. If an error occurs, the returned index
   * will be negative and will have the error position encoded using the complement operator.
   * 
   * <h4>Implementation notes</h4>
   * This interface must be implemented with care to ensure other classes operate correctly. All
   * implementations that can be instantiated must be final, immutable and thread-safe.
   * <p>
   * The context is not a thread-safe object and a new instance will be created for each print that occurs.
   * The context must not be stored in an instance variable or shared with any other threads.
   */
  interface DateTimePrinterParser {

    /**
     * Prints the date-time object to the buffer.
     * <p>
     * The context holds information to use during the print. It also contains the date-time information to be
     * printed.
     * <p>
     * The buffer must not be mutated beyond the content controlled by the implementation.
     * 
     * @param context the context to print using, not null
     * @param buf the buffer to append to, not null
     * @return false if unable to query the value from the date-time, true otherwise
     * @throws DateTimeException if the date-time cannot be printed successfully
     */
    boolean print(DateTimePrintContext context, StringBuilder buf);

    /**
     * Parses text into date-time information.
     * <p>
     * The context holds information to use during the parse. It is also used to store the parsed date-time
     * information.
     * 
     * @param context the context to use and parse into, not null
     * @param text the input text to parse, not null
     * @param position the position to start parsing at, from 0 to the text length
     * @return the new parse position, where negative means an error with the error position encoded using the
     *         complement ~ operator
     * @throws NullPointerException if the context or text is null
     * @throws IndexOutOfBoundsException if the position is invalid
     */
    int parse(DateTimeParseContext context, CharSequence text, int position);
  }

  // -----------------------------------------------------------------------
  /**
   * Composite printer and parser.
   */
  static final class CompositePrinterParser implements DateTimePrinterParser {

    private final DateTimePrinterParser[] printerParsers;

    private final boolean optional;

    public CompositePrinterParser(List<DateTimePrinterParser> printerParsers, boolean optional) {

      this(printerParsers.toArray(new DateTimePrinterParser[printerParsers.size()]), optional);
    }

    CompositePrinterParser(DateTimePrinterParser[] printerParsers, boolean optional) {

      this.printerParsers = printerParsers;
      this.optional = optional;
    }

    /**
     * Returns a copy of this printer-parser with the optional flag changed.
     * 
     * @param optional the optional flag to set in the copy
     * @return the new printer-parser, not null
     */
    public CompositePrinterParser withOptional(boolean optional) {

      if (optional == this.optional) {
        return this;
      }
      return new CompositePrinterParser(this.printerParsers, optional);
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      int length = buf.length();
      if (this.optional) {
        context.startOptional();
      }
      try {
        for (DateTimePrinterParser pp : this.printerParsers) {
          if (pp.print(context, buf) == false) {
            buf.setLength(length); // reset buffer
            return true;
          }
        }
      } finally {
        if (this.optional) {
          context.endOptional();
        }
      }
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      if (this.optional) {
        context.startOptional();
        int pos = position;
        for (DateTimePrinterParser pp : this.printerParsers) {
          pos = pp.parse(context, text, pos);
          if (pos < 0) {
            context.endOptional(false);
            return position; // return original position
          }
        }
        context.endOptional(true);
        return pos;
      } else {
        for (DateTimePrinterParser pp : this.printerParsers) {
          position = pp.parse(context, text, position);
          if (position < 0) {
            break;
          }
        }
        return position;
      }
    }

    @Override
    public String toString() {

      StringBuilder buf = new StringBuilder();
      if (this.printerParsers != null) {
        buf.append(this.optional ? "[" : "(");
        for (DateTimePrinterParser pp : this.printerParsers) {
          buf.append(pp);
        }
        buf.append(this.optional ? "]" : ")");
      }
      return buf.toString();
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Pads the output to a fixed width.
   */
  static final class PadPrinterParserDecorator implements DateTimePrinterParser {

    private final DateTimePrinterParser printerParser;

    private final int padWidth;

    private final char padChar;

    /**
     * Constructor.
     * 
     * @param printerParser the printer, not null
     * @param padWidth the width to pad to, 1 or greater
     * @param padChar the pad character
     */
    public PadPrinterParserDecorator(DateTimePrinterParser printerParser, int padWidth, char padChar) {

      // input checked by DateTimeFormatterBuilder
      this.printerParser = printerParser;
      this.padWidth = padWidth;
      this.padChar = padChar;
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      int preLen = buf.length();
      if (this.printerParser.print(context, buf) == false) {
        return false;
      }
      int len = buf.length() - preLen;
      if (len > this.padWidth) {
        throw new DateTimePrintException("Cannot print as output of " + len + " characters exceeds pad width of "
            + this.padWidth);
      }
      for (int i = 0; i < this.padWidth - len; i++) {
        buf.insert(preLen, this.padChar);
      }
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      if (position > text.length()) {
        throw new IndexOutOfBoundsException();
      }
      int endPos = position + this.padWidth;
      if (endPos > text.length()) {
        return ~position; // not enough characters in the string to meet the parse width
      }
      int pos = position;
      while (pos < endPos && text.charAt(pos) == this.padChar) {
        pos++;
      }
      text = text.subSequence(0, endPos);
      int firstError = 0;
      while (pos >= position) {
        int resultPos = this.printerParser.parse(context, text, pos);
        if (resultPos < 0) {
          // parse of decorated field had an error
          if (firstError == 0) {
            firstError = resultPos;
          }
          // loop around in case the decorated parser can handle the padChar at the start
          pos--;
          continue;
        }
        if (resultPos != endPos) {
          return ~position; // parse of decorated field didn't parse to the end
        }
        return resultPos;
      }
      // loop runs at least once, so firstError must be set by the time we get here
      return firstError; // return error from first parse of decorated field
    }

    @Override
    public String toString() {

      return "Pad(" + this.printerParser + "," + this.padWidth
          + (this.padChar == ' ' ? ")" : ",'" + this.padChar + "')");
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Enumeration to apply simple parse settings.
   */
  static enum SettingsParser implements DateTimePrinterParser {
    SENSITIVE, INSENSITIVE, STRICT, LENIENT;

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      return true; // nothing to do here
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      switch (this) {
        case SENSITIVE:
          context.setCaseSensitive(true);
          break;
        case INSENSITIVE:
          context.setCaseSensitive(false);
          break;
        case STRICT:
          context.setStrict(true);
          break;
        case LENIENT:
          context.setStrict(false);
          break;
      }
      return position;
    }

    @Override
    public String toString() {

      switch (this) {
        case SENSITIVE:
          return "ParseCaseSensitive(true)";
        case INSENSITIVE:
          return "ParseCaseSensitive(false)";
        case STRICT:
          return "ParseStrict(true)";
        case LENIENT:
          return "ParseStrict(false)";
      }
      throw new IllegalStateException("Unreachable");
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a character literal.
   */
  static final class CharLiteralPrinterParser implements DateTimePrinterParser {

    private final char literal;

    public CharLiteralPrinterParser(char literal) {

      this.literal = literal;
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      buf.append(this.literal);
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      int length = text.length();
      if (position == length) {
        return ~position;
      }
      char ch = text.charAt(position);
      if (ch != this.literal) {
        if (context.isCaseSensitive()
            || (Character.toUpperCase(ch) != Character.toUpperCase(this.literal) && Character.toLowerCase(ch) != Character
                .toLowerCase(this.literal))) {
          return ~position;
        }
      }
      return position + 1;
    }

    @Override
    public String toString() {

      if (this.literal == '\'') {
        return "''";
      }
      return "'" + this.literal + "'";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a string literal.
   */
  static final class StringLiteralPrinterParser implements DateTimePrinterParser {

    private final String literal;

    public StringLiteralPrinterParser(String literal) {

      this.literal = literal; // validated by caller
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      buf.append(this.literal);
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      int length = text.length();
      if (position > length || position < 0) {
        throw new IndexOutOfBoundsException();
      }
      if (context.subSequenceEquals(text, position, this.literal, 0, this.literal.length()) == false) {
        return ~position;
      }
      return position + this.literal.length();
    }

    @Override
    public String toString() {

      String converted = this.literal.replace("'", "''");
      return "'" + converted + "'";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints and parses a numeric date-time field with optional padding.
   */
  static class NumberPrinterParser implements DateTimePrinterParser {

    /**
     * Array of 10 to the power of n.
     */
    static final int[] EXCEED_POINTS = new int[] { 0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000,
        1000000000, };

    final DateTimeField field;

    final int minWidth;

    private final int maxWidth;

    private final SignStyle signStyle;

    private final int subsequentWidth;

    /**
     * Constructor.
     * 
     * @param field the field to print, not null
     * @param minWidth the minimum field width, from 1 to 19
     * @param maxWidth the maximum field width, from minWidth to 19
     * @param signStyle the positive/negative sign style, not null
     */
    public NumberPrinterParser(DateTimeField field, int minWidth, int maxWidth, SignStyle signStyle) {

      // validated by caller
      this.field = field;
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.signStyle = signStyle;
      this.subsequentWidth = 0;
    }

    /**
     * Constructor.
     * 
     * @param field the field to print, not null
     * @param minWidth the minimum field width, from 1 to 19
     * @param maxWidth the maximum field width, from minWidth to 19
     * @param signStyle the positive/negative sign style, not null
     * @param subsequentWidth the width of subsequent non-negative numbers, 0 or greater
     */
    private NumberPrinterParser(DateTimeField field, int minWidth, int maxWidth, SignStyle signStyle,
        int subsequentWidth) {

      // validated by caller
      this.field = field;
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.signStyle = signStyle;
      this.subsequentWidth = subsequentWidth;
    }

    /**
     * Returns a new instance with an updated subsequent width.
     * 
     * @param subsequentWidth the width of subsequent non-negative numbers, 0 or greater
     * @return a new updated printer-parser, not null
     */
    NumberPrinterParser withSubsequentWidth(int subsequentWidth) {

      return new NumberPrinterParser(this.field, this.minWidth, this.maxWidth, this.signStyle, this.subsequentWidth
          + subsequentWidth);
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      Long valueLong = context.getValue(this.field);
      if (valueLong == null) {
        return false;
      }
      long value = getValue(valueLong);
      DateTimeFormatSymbols symbols = context.getSymbols();
      String str = (value == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(value)));
      if (str.length() > this.maxWidth) {
        throw new DateTimePrintException("Field " + this.field.getName() + " cannot be printed as the value " + value
            + " exceeds the maximum print width of " + this.maxWidth);
      }
      str = symbols.convertNumberToI18N(str);

      if (value >= 0) {
        switch (this.signStyle) {
          case EXCEEDS_PAD:
            if (this.minWidth < 19 && value >= EXCEED_POINTS[this.minWidth]) {
              buf.append(symbols.getPositiveSign());
            }
            break;
          case ALWAYS:
            buf.append(symbols.getPositiveSign());
            break;
        }
      } else {
        switch (this.signStyle) {
          case NORMAL:
          case EXCEEDS_PAD:
          case ALWAYS:
            buf.append(symbols.getNegativeSign());
            break;
          case NOT_NEGATIVE:
            throw new DateTimePrintException("Field " + this.field.getName() + " cannot be printed as the value "
                + value + " cannot be negative according to the SignStyle");
        }
      }
      for (int i = 0; i < this.minWidth - str.length(); i++) {
        buf.append(symbols.getZeroDigit());
      }
      buf.append(str);
      return true;
    }

    /**
     * Gets the value to output.
     * 
     * @param value the base value of the field, not null
     * @return the value
     */
    long getValue(long value) {

      return value;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      int length = text.length();
      if (position == length) {
        return ~position;
      }
      char sign = text.charAt(position); // IOOBE if invalid position
      boolean negative = false;
      boolean positive = false;
      if (sign == context.getSymbols().getPositiveSign()) {
        positive = true;
        switch (this.signStyle) {
          case ALWAYS:
          case EXCEEDS_PAD:
            position++;
            break;
          default :
            if (context.isStrict() || (this.signStyle != SignStyle.NORMAL && this.minWidth == this.maxWidth)) {
              return ~position;
            }
            position++;
            break;
        }
      } else if (sign == context.getSymbols().getNegativeSign()) {
        negative = true;
        switch (this.signStyle) {
          case ALWAYS:
          case EXCEEDS_PAD:
          case NORMAL:
            position++;
            break;
          default :
            if (context.isStrict() || this.minWidth == this.maxWidth) {
              return ~position;
            }
            position++;
            break;
        }
      } else {
        if (this.signStyle == SignStyle.ALWAYS && context.isStrict()) {
          return ~position;
        }
      }
      int minEndPos = position + this.minWidth;
      if (minEndPos > length) {
        return ~position;
      }
      int effMaxWidth = this.maxWidth + this.subsequentWidth;
      long total = 0;
      BigInteger totalBig = null;
      int pos = position;
      for (int pass = 0; pass < 2; pass++) {
        int maxEndPos = Math.min(pos + effMaxWidth, length);
        while (pos < maxEndPos) {
          char ch = text.charAt(pos++);
          int digit = context.getSymbols().convertToDigit(ch);
          if (digit < 0) {
            pos--;
            if (pos < minEndPos) {
              return ~position; // need at least min width digits
            }
            break;
          }
          if ((pos - position) > 18) {
            if (totalBig == null) {
              totalBig = BigInteger.valueOf(total);
            }
            totalBig = totalBig.multiply(BigInteger.TEN).add(BigInteger.valueOf(digit));
          } else {
            total = total * 10 + digit;
          }
        }
        if (this.subsequentWidth > 0 && pass == 0) {
          // re-parse now we know the correct width
          int parseLen = pos - position;
          effMaxWidth = Math.max(this.minWidth, parseLen - this.subsequentWidth);
          pos = position;
          total = 0;
          totalBig = null;
        } else {
          break;
        }
      }
      if (negative) {
        if (totalBig != null) {
          if (totalBig.equals(BigInteger.ZERO) && context.isStrict()) {
            return ~(position - 1); // minus zero not allowed
          }
          totalBig = totalBig.negate();
        } else {
          if (total == 0 && context.isStrict()) {
            return ~(position - 1); // minus zero not allowed
          }
          total = -total;
        }
      } else if (this.signStyle == SignStyle.EXCEEDS_PAD && context.isStrict()) {
        int parseLen = pos - position;
        if (positive) {
          if (parseLen <= this.minWidth) {
            return ~(position - 1); // '+' only parsed if minWidth exceeded
          }
        } else {
          if (parseLen > this.minWidth) {
            return ~position; // '+' must be parsed if minWidth exceeded
          }
        }
      }
      if (totalBig != null) {
        if (totalBig.bitLength() > 63) {
          // overflow, parse 1 less digit
          totalBig = totalBig.divide(BigInteger.TEN);
          pos--;
        }
        setValue(context, totalBig.longValue());
      } else {
        setValue(context, total);
      }
      return pos;
    }

    /**
     * Stores the value.
     * 
     * @param context the context to store into, not null
     * @param value the value
     */
    void setValue(DateTimeParseContext context, long value) {

      context.setParsedField(this.field, value);
    }

    @Override
    public String toString() {

      if (this.minWidth == 1 && this.maxWidth == 19 && this.signStyle == SignStyle.NORMAL) {
        return "Value(" + this.field.getName() + ")";
      }
      if (this.minWidth == this.maxWidth && this.signStyle == SignStyle.NOT_NEGATIVE) {
        return "Value(" + this.field.getName() + "," + this.minWidth + ")";
      }
      return "Value(" + this.field.getName() + "," + this.minWidth + "," + this.maxWidth + "," + this.signStyle + ")";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints and parses a reduced numeric date-time field.
   */
  static final class ReducedPrinterParser extends NumberPrinterParser {

    private final int baseValue;

    private final int range;

    /**
     * Constructor.
     * 
     * @param field the field to print, validated not null
     * @param width the field width, from 1 to 18
     * @param baseValue the base value
     */
    public ReducedPrinterParser(DateTimeField field, int width, int baseValue) {

      super(field, width, width, SignStyle.NOT_NEGATIVE);
      if (width < 1 || width > 18) {
        throw new IllegalArgumentException("The width must be from 1 to 18 inclusive but was " + width);
      }
      if (field.range().isValidValue(baseValue) == false) {
        throw new IllegalArgumentException("The base value must be within the range of the field");
      }
      this.baseValue = baseValue;
      this.range = EXCEED_POINTS[width];
      if ((((long) baseValue) + this.range) > Integer.MAX_VALUE) {
        throw new DateTimeException("Unable to add printer-parser as the range exceeds the capacity of an int");
      }
    }

    @Override
    long getValue(long value) {

      return Math.abs(value % this.range);
    }

    @Override
    void setValue(DateTimeParseContext context, long value) {

      int lastPart = this.baseValue % this.range;
      if (this.baseValue > 0) {
        value = this.baseValue - lastPart + value;
      } else {
        value = this.baseValue - lastPart - value;
      }
      if (value < this.baseValue) {
        value += this.range;
      }
      context.setParsedField(this.field, value);
    }

    @Override
    public String toString() {

      return "ReducedValue(" + this.field.getName() + "," + this.minWidth + "," + this.baseValue + ")";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints and parses a numeric date-time field with optional padding.
   */
  static final class FractionPrinterParser implements DateTimePrinterParser {

    private final DateTimeField field;

    private final int minWidth;

    private final int maxWidth;

    private final boolean decimalPoint;

    /**
     * Constructor.
     * 
     * @param field the field to output, not null
     * @param minWidth the minimum width to output, from 0 to 9
     * @param maxWidth the maximum width to output, from 0 to 9
     * @param decimalPoint whether to output the localized decimal point symbol
     */
    public FractionPrinterParser(DateTimeField field, int minWidth, int maxWidth, boolean decimalPoint) {

      Jdk7Methods.Objects_requireNonNull(field, "field");
      if (field.range().isFixed() == false) {
        throw new IllegalArgumentException("Field must have a fixed set of values: " + field.getName());
      }
      if (minWidth < 0 || minWidth > 9) {
        throw new IllegalArgumentException("Minimum width must be from 0 to 9 inclusive but was " + minWidth);
      }
      if (maxWidth < 1 || maxWidth > 9) {
        throw new IllegalArgumentException("Maximum width must be from 1 to 9 inclusive but was " + maxWidth);
      }
      if (maxWidth < minWidth) {
        throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + maxWidth
            + " < " + minWidth);
      }
      this.field = field;
      this.minWidth = minWidth;
      this.maxWidth = maxWidth;
      this.decimalPoint = decimalPoint;
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      Long value = context.getValue(this.field);
      if (value == null) {
        return false;
      }
      DateTimeFormatSymbols symbols = context.getSymbols();
      BigDecimal fraction = convertToFraction(value);
      if (fraction.scale() == 0) { // scale is zero if value is zero
        if (this.minWidth > 0) {
          if (this.decimalPoint) {
            buf.append(symbols.getDecimalSeparator());
          }
          for (int i = 0; i < this.minWidth; i++) {
            buf.append(symbols.getZeroDigit());
          }
        }
      } else {
        int outputScale = Math.min(Math.max(fraction.scale(), this.minWidth), this.maxWidth);
        fraction = fraction.setScale(outputScale, RoundingMode.FLOOR);
        String str = fraction.toPlainString().substring(2);
        str = symbols.convertNumberToI18N(str);
        if (this.decimalPoint) {
          buf.append(symbols.getDecimalSeparator());
        }
        buf.append(str);
      }
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      int effectiveMin = (context.isStrict() ? this.minWidth : 0);
      int effectiveMax = (context.isStrict() ? this.maxWidth : 9);
      int length = text.length();
      if (position == length) {
        // valid if whole field is optional, invalid if minimum width
        return (effectiveMin > 0 ? ~position : position);
      }
      if (this.decimalPoint) {
        if (text.charAt(position) != context.getSymbols().getDecimalSeparator()) {
          // valid if whole field is optional, invalid if minimum width
          return (effectiveMin > 0 ? ~position : position);
        }
        position++;
      }
      int minEndPos = position + effectiveMin;
      if (minEndPos > length) {
        return ~position; // need at least min width digits
      }
      int maxEndPos = Math.min(position + effectiveMax, length);
      int total = 0; // can use int because we are only parsing up to 9 digits
      int pos = position;
      while (pos < maxEndPos) {
        char ch = text.charAt(pos++);
        int digit = context.getSymbols().convertToDigit(ch);
        if (digit < 0) {
          if (pos < minEndPos) {
            return ~position; // need at least min width digits
          }
          pos--;
          break;
        }
        total = total * 10 + digit;
      }
      BigDecimal fraction = new BigDecimal(total).movePointLeft(pos - position);
      long value = convertFromFraction(fraction);
      context.setParsedField(this.field, value);
      return pos;
    }

    /**
     * Converts a value for this field to a fraction between 0 and 1.
     * <p>
     * The fractional value is between 0 (inclusive) and 1 (exclusive). It can only be returned if the
     * {@link DateTimeField#range() value range} is fixed. The fraction is obtained by calculation from the
     * field range using 9 decimal places and a rounding mode of {@link RoundingMode#FLOOR FLOOR}. The
     * calculation is inaccurate if the values do not run continuously from smallest to largest.
     * <p>
     * For example, the second-of-minute value of 15 would be returned as 0.25, assuming the standard
     * definition of 60 seconds in a minute.
     * 
     * @param value the value to convert, must be valid for this rule
     * @return the value as a fraction within the range, from 0 to 1, not null
     * @throws DateTimeException if the value cannot be converted to a fraction
     */
    private BigDecimal convertToFraction(long value) {

      DateTimeValueRange range = this.field.range();
      if (range.isFixed() == false) {
        throw new DateTimeException("Unable to obtain fraction as field range is not fixed: " + this.field.getName());
      }
      range.checkValidValue(value, this.field);
      BigDecimal minBD = BigDecimal.valueOf(range.getMinimum());
      BigDecimal rangeBD = BigDecimal.valueOf(range.getMaximum()).subtract(minBD).add(BigDecimal.ONE);
      BigDecimal valueBD = BigDecimal.valueOf(value).subtract(minBD);
      BigDecimal fraction = valueBD.divide(rangeBD, 9, RoundingMode.FLOOR);
      // stripTrailingZeros bug
      return fraction.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : fraction.stripTrailingZeros();
    }

    /**
     * Converts a fraction from 0 to 1 for this field to a value.
     * <p>
     * The fractional value must be between 0 (inclusive) and 1 (exclusive). It can only be returned if the
     * {@link DateTimeField#range() value range} is fixed. The value is obtained by calculation from the field
     * range and a rounding mode of {@link RoundingMode#FLOOR FLOOR}. The calculation is inaccurate if the
     * values do not run continuously from smallest to largest.
     * <p>
     * For example, the fractional second-of-minute of 0.25 would be converted to 15, assuming the standard
     * definition of 60 seconds in a minute.
     * 
     * @param fraction the fraction to convert, not null
     * @return the value of the field, valid for this rule
     * @throws DateTimeException if the value cannot be converted
     */
    private long convertFromFraction(BigDecimal fraction) {

      DateTimeValueRange range = this.field.range();
      if (range.isFixed() == false) {
        throw new DateTimeException("Unable to obtain fraction as field range is not fixed: " + this.field.getName());
      }
      BigDecimal minBD = BigDecimal.valueOf(range.getMinimum());
      BigDecimal rangeBD = BigDecimal.valueOf(range.getMaximum()).subtract(minBD).add(BigDecimal.ONE);
      BigDecimal valueBD = fraction.multiply(rangeBD).setScale(0, RoundingMode.FLOOR).add(minBD);
      long value = valueBD.longValueExact();
      range.checkValidValue(value, this.field);
      return value;
    }

    @Override
    public String toString() {

      String decimal = (this.decimalPoint ? ",DecimalPoint" : "");
      return "Fraction(" + this.field.getName() + "," + this.minWidth + "," + this.maxWidth + decimal + ")";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses field text.
   */
  static final class TextPrinterParser implements DateTimePrinterParser {

    private final DateTimeField field;

    private final TextStyle textStyle;

    private final DateTimeTextProvider provider;

    /**
     * The cached number printer parser. Immutable and volatile, so no synchronization needed.
     */
    private volatile NumberPrinterParser numberPrinterParser;

    /**
     * Constructor.
     * 
     * @param field the field to output, not null
     * @param textStyle the text style, not null
     * @param provider the text provider, not null
     */
    public TextPrinterParser(DateTimeField field, TextStyle textStyle, DateTimeTextProvider provider) {

      // validated by caller
      this.field = field;
      this.textStyle = textStyle;
      this.provider = provider;
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      Long value = context.getValue(this.field);
      if (value == null) {
        return false;
      }
      String text = this.provider.getText(this.field, value, this.textStyle, context.getLocale());
      if (text == null) {
        return numberPrinterParser().print(context, buf);
      }
      buf.append(text);
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence parseText, int position) {

      int length = parseText.length();
      if (position < 0 || position > length) {
        throw new IndexOutOfBoundsException();
      }
      TextStyle style = (context.isStrict() ? this.textStyle : null);
      Iterator<Entry<String, Long>> it = this.provider.getTextIterator(this.field, style, context.getLocale());
      if (it != null) {
        while (it.hasNext()) {
          Entry<String, Long> entry = it.next();
          String itText = entry.getKey();
          if (context.subSequenceEquals(itText, 0, parseText, position, itText.length())) {
            context.setParsedField(this.field, entry.getValue());
            return position + itText.length();
          }
        }
        if (context.isStrict()) {
          return ~position;
        }
      }
      return numberPrinterParser().parse(context, parseText, position);
    }

    /**
     * Create and cache a number printer parser.
     * 
     * @return the number printer parser for this field, not null
     */
    private NumberPrinterParser numberPrinterParser() {

      if (this.numberPrinterParser == null) {
        this.numberPrinterParser = new NumberPrinterParser(this.field, 1, 19, SignStyle.NORMAL);
      }
      return this.numberPrinterParser;
    }

    @Override
    public String toString() {

      if (this.textStyle == TextStyle.FULL) {
        return "Text(" + this.field.getName() + ")";
      }
      return "Text(" + this.field.getName() + "," + this.textStyle + ")";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses an ISO-8601 instant.
   */
  static final class InstantPrinterParser implements DateTimePrinterParser {

    InstantPrinterParser() {

    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      // TODO: implement this from INSTANT_SECONDS, handling big numbers
      Instant instant = Instant.from(context.getDateTime());
      ZonedDateTime odt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
      buf.append(odt);
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      // TODO: implement this from INSTANT_SECONDS, handling big numbers
      DateTimeFormatter f = DateTimeFormatters.isoOffsetDateTime();
      ZonedDateTime odt = f.parse(text.subSequence(position, text.length()), ZonedDateTime.class);
      context.setParsedField(INSTANT_SECONDS, odt.getLong(INSTANT_SECONDS));
      context.setParsedField(NANO_OF_SECOND, odt.getLong(NANO_OF_SECOND));
      return text.length();
    }

    @Override
    public String toString() {

      return "Instant()";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a zone offset.
   */
  static final class ZoneOffsetPrinterParser implements DateTimePrinterParser {

    static final String[] PATTERNS = new String[] { "+HH", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS",
        "+HH:MM:SS", }; // order used in pattern builder

    private final String noOffsetText;

    private final int type;

    /**
     * Constructor.
     * 
     * @param noOffsetText the text to use for UTC, not null
     * @param pattern the pattern
     */
    public ZoneOffsetPrinterParser(String noOffsetText, String pattern) {

      Jdk7Methods.Objects_requireNonNull(noOffsetText, "noOffsetText");
      Jdk7Methods.Objects_requireNonNull(pattern, "pattern");
      this.noOffsetText = noOffsetText;
      this.type = checkPattern(pattern);
    }

    private int checkPattern(String pattern) {

      for (int i = 0; i < PATTERNS.length; i++) {
        if (PATTERNS[i].equals(pattern)) {
          return i;
        }
      }
      throw new IllegalArgumentException("Invalid zone offset pattern");
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      Long offsetSecs = context.getValue(OFFSET_SECONDS);
      if (offsetSecs == null) {
        return false;
      }
      int totalSecs = Jdk8Methods.safeToInt(offsetSecs);
      if (totalSecs == 0) {
        buf.append(this.noOffsetText);
      } else {
        int absHours = Math.abs((totalSecs / 3600) % 100); // anything larger than 99 silently dropped
        int absMinutes = Math.abs((totalSecs / 60) % 60);
        int absSeconds = Math.abs(totalSecs % 60);
        buf.append(totalSecs < 0 ? "-" : "+").append((char) (absHours / 10 + '0')).append((char) (absHours % 10 + '0'));
        if (this.type >= 1) {
          buf.append((this.type % 2) == 0 ? ":" : "").append((char) (absMinutes / 10 + '0'))
              .append((char) (absMinutes % 10 + '0'));
          if (this.type >= 5 || (this.type >= 3 && absSeconds > 0)) {
            buf.append((this.type % 2) == 0 ? ":" : "").append((char) (absSeconds / 10 + '0'))
                .append((char) (absSeconds % 10 + '0'));
          }
        }
      }
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      int length = text.length();
      int utcLen = this.noOffsetText.length();
      if (utcLen == 0) {
        if (position == length) {
          context.setParsedField(OFFSET_SECONDS, 0);
          return position;
        }
      } else {
        if (position == length) {
          return ~position;
        }
        if (context.subSequenceEquals(text, position, this.noOffsetText, 0, utcLen)) {
          context.setParsedField(OFFSET_SECONDS, 0);
          return position + utcLen;
        }
      }

      char sign = text.charAt(position); // IOOBE if invalid position
      if (sign == '+' || sign == '-') {
        int negative = (sign == '-' ? -1 : 1);
        int[] array = new int[4];
        array[0] = position + 1;
        if (parseNumber(array, 1, text, true) || parseNumber(array, 2, text, true)
            || parseNumber(array, 3, text, false)) {
          return ~position;
        }
        long offsetSecs = negative * (array[1] * 3600L + array[2] * 60L + array[3]);
        context.setParsedField(OFFSET_SECONDS, offsetSecs);
        return array[0];
      } else {
        if (utcLen == 0) {
          context.setParsedField(OFFSET_SECONDS, 0);
          return position + utcLen;
        }
        return ~position;
      }
    }

    /**
     * Parse a two digit zero-prefixed number.
     * 
     * @param array the array of parsed data, 0=pos,1=hours,2=mins,3=secs, not null
     * @param arrayIndex the index to parse the value into
     * @param parseText the offset ID, not null
     * @param required whether this number is required
     * @return true if an error occurred
     */
    private boolean parseNumber(int[] array, int arrayIndex, CharSequence parseText, boolean required) {

      if ((this.type + 3) / 2 < arrayIndex) {
        return false; // ignore seconds/minutes
      }
      int pos = array[0];
      if ((this.type % 2) == 0 && arrayIndex > 1) {
        if (pos + 1 > parseText.length() || parseText.charAt(pos) != ':') {
          return required;
        }
        pos++;
      }
      if (pos + 2 > parseText.length()) {
        return required;
      }
      char ch1 = parseText.charAt(pos++);
      char ch2 = parseText.charAt(pos++);
      if (ch1 < '0' || ch1 > '9' || ch2 < '0' || ch2 > '9') {
        return required;
      }
      int value = (ch1 - 48) * 10 + (ch2 - 48);
      if (value < 0 || value > 59) {
        return required;
      }
      array[arrayIndex] = value;
      array[0] = pos;
      return false;
    }

    @Override
    public String toString() {

      String converted = this.noOffsetText.replace("'", "''");
      return "Offset('" + converted + "'," + PATTERNS[this.type] + ")";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a zone ID.
   */
  static final class ZoneTextPrinterParser implements DateTimePrinterParser {

    // TODO: remove this as it is incomplete
    /** The text style to output. */
    private final TextStyle textStyle;

    ZoneTextPrinterParser(TextStyle textStyle) {

      this.textStyle = Jdk7Methods.Objects_requireNonNull(textStyle, "textStyle");
    }

    // -----------------------------------------------------------------------
    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      ZoneId zone = context.getValue(Query.ZONE_ID);
      if (zone == null) {
        return false;
      }
      // TODO: fix getText(textStyle, context.getLocale())
      buf.append(zone.getId()); // TODO: Use symbols
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {

      return "ZoneText(" + this.textStyle + ")";
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a zone ID.
   */
  static final class ZoneIdPrinterParser implements DateTimePrinterParser {

    private final boolean regionOnly;

    ZoneIdPrinterParser(boolean regionOnly) {

      this.regionOnly = regionOnly;
    }

    // -----------------------------------------------------------------------
    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      ZoneId zone = context.getValue(Query.ZONE_ID);
      if (zone == null || (this.regionOnly && zone instanceof ZoneOffset)) {
        return false;
      }
      buf.append(zone.getId());
      return true;
    }

    // -----------------------------------------------------------------------
    /**
     * The cached tree to speed up parsing.
     */
    private static volatile Entry<Integer, SubstringTree> cachedSubstringTree;

    /**
     * This implementation looks for the longest matching string. For example, parsing Etc/GMT-2 will return
     * Etc/GMC-2 rather than just Etc/GMC although both are valid.
     * <p>
     * This implementation uses a tree to search for valid time-zone names in the parseText. The top level
     * node of the tree has a length equal to the length of the shortest time-zone as well as the beginning
     * characters of all other time-zones.
     */
    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      // TODO case insensitive?
      int length = text.length();
      if (position > length) {
        throw new IndexOutOfBoundsException();
      }

      // handle fixed time-zone IDs
      String remainder = text.subSequence(position, text.length()).toString();
      if (remainder.length() >= 1) {
        char nextChar = remainder.charAt(0);
        if (nextChar == '+' || nextChar == '-') {
          DateTimeParseContext newContext = new DateTimeParseContext(context.getLocale(),
              DateTimeFormatSymbols.STANDARD);
          int endPos = new ZoneOffsetPrinterParser("Z", "+HH:MM:ss").parse(newContext, text, position);
          if (endPos < 0) {
            return endPos;
          }
          int offset = (int) (long) newContext.getParsed(OFFSET_SECONDS);
          ZoneId zone = ZoneOffset.ofTotalSeconds(offset);
          context.setParsed(zone);
          return endPos;
        }
      }

      // prepare parse tree
      Set<String> regionIds = ZoneRulesProvider.getAvailableZoneIds();
      final int regionIdsSize = regionIds.size();
      Entry<Integer, SubstringTree> cached = cachedSubstringTree;
      if (cached == null || cached.getKey() != regionIdsSize) {
        synchronized (this) {
          cached = cachedSubstringTree;
          if (cached == null || cached.getKey() != regionIdsSize) {
            // cachedSubstringTree = cached = new SimpleImmutableEntry<>(regionIdsSize,
            // prepareParser(regionIds));
            cachedSubstringTree = cached = new SimpleImmutableEntry<Integer, SubstringTree>(regionIdsSize,
                prepareParser(regionIds));
          }
        }
      }
      SubstringTree tree = cached.getValue();

      // parse
      String parsedZoneId = null;
      while (tree != null) {
        int nodeLength = tree.length;
        if (position + nodeLength > length) {
          break;
        }
        parsedZoneId = text.subSequence(position, position + nodeLength).toString();
        tree = tree.get(parsedZoneId);
      }

      if (parsedZoneId == null || regionIds.contains(parsedZoneId) == false) {
        if (remainder.startsWith("Z")) {
          context.setParsed(ZoneOffset.UTC);
          return position + 1;
        }
        return ~position;
      }
      context.setParsed(ZoneId.of(parsedZoneId));
      return position + parsedZoneId.length();
    }

    // -----------------------------------------------------------------------
    /**
     * Model a tree of substrings to make the parsing easier. Due to the nature of time-zone names, it can be
     * faster to parse based in unique substrings rather than just a character by character match.
     * <p>
     * For example, to parse America/Denver we can look at the first two character "Am". We then notice that
     * the shortest time-zone that starts with Am is America/Nome which is 12 characters long. Checking the
     * first 12 characters of America/Denver gives America/Denv which is a substring of only 1 time-zone:
     * America/Denver. Thus, with just 3 comparisons that match can be found.
     * <p>
     * This structure maps substrings to substrings of a longer length. Each node of the tree contains a
     * length and a map of valid substrings to sub-nodes. The parser gets the length from the root node. It
     * then extracts a substring of that length from the parseText. If the map contains the substring, it is
     * set as the possible time-zone and the sub-node for that substring is retrieved. The process continues
     * until the substring is no longer found, at which point the matched text is checked against the real
     * time-zones.
     */
    private static final class SubstringTree {

      /**
       * The length of the substring this node of the tree contains. Subtrees will have a longer length.
       */
      final int length;

      /**
       * Map of a substring to a set of substrings that contain the key.
       */
      private final Map<CharSequence, SubstringTree> substringMap = new HashMap<CharSequence, SubstringTree>();

      /**
       * Constructor.
       * 
       * @param length the length of this tree
       */
      private SubstringTree(int length) {

        this.length = length;
      }

      private SubstringTree get(CharSequence substring2) {

        return this.substringMap.get(substring2);

      }

      /**
       * Values must be added from shortest to longest.
       * 
       * @param newSubstring the substring to add, not null
       */
      private void add(String newSubstring) {

        int idLen = newSubstring.length();
        if (idLen == this.length) {
          this.substringMap.put(newSubstring, null);
        } else if (idLen > this.length) {
          String substring = newSubstring.substring(0, this.length);
          SubstringTree parserTree = this.substringMap.get(substring);
          if (parserTree == null) {
            parserTree = new SubstringTree(idLen);
            this.substringMap.put(substring, parserTree);
          }
          parserTree.add(newSubstring);
        }
      }
    }

    /**
     * Builds an optimized parsing tree.
     * 
     * @param availableIDs the available IDs, not null, not empty
     * @return the tree, not null
     */
    private static SubstringTree prepareParser(Set<String> availableIDs) {

      // sort by length
      List<String> ids = new ArrayList<String>(availableIDs);
      Collections.sort(ids, LENGTH_SORT);

      // build the tree
      SubstringTree tree = new SubstringTree(ids.get(0).length());
      for (String id : ids) {
        tree.add(id);
      }
      return tree;
    }

    // -----------------------------------------------------------------------
    @Override
    public String toString() {

      return (this.regionOnly ? "ZoneRegionId()" : "ZoneId()");
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a chronology.
   */
  static final class ChronoPrinterParser implements DateTimePrinterParser {

    /** The text style to output, null means the ID. */
    private final TextStyle textStyle;

    ChronoPrinterParser(TextStyle textStyle) {

      // validated by caller
      this.textStyle = textStyle;
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      Chrono<?> chrono = context.getValue(Query.CHRONO);
      if (chrono == null) {
        return false;
      }
      if (this.textStyle == null) {
        buf.append(chrono.getId());
      } else {
        buf.append(chrono.getId()); // TODO: Use symbols
      }
      return true;
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      return ~position; // TODO, inlcuding case insensitive
    }
  }

  // -----------------------------------------------------------------------
  /**
   * Prints or parses a localized pattern.
   */
  static final class LocalizedPrinterParser implements DateTimePrinterParser {

    private final FormatStyle dateStyle;

    private final FormatStyle timeStyle;

    private final Chrono<?> chrono;

    /**
     * Constructor.
     * 
     * @param dateStyle the date style to use, may be null
     * @param timeStyle the time style to use, may be null
     * @param chrono the chronology to use, not null
     */
    LocalizedPrinterParser(FormatStyle dateStyle, FormatStyle timeStyle, Chrono<?> chrono) {

      // validated by caller
      this.dateStyle = dateStyle;
      this.timeStyle = timeStyle;
      this.chrono = chrono;
    }

    @Override
    public boolean print(DateTimePrintContext context, StringBuilder buf) {

      return formatter(context.getLocale()).toPrinterParser(false).print(context, buf);
    }

    @Override
    public int parse(DateTimeParseContext context, CharSequence text, int position) {

      return formatter(context.getLocale()).toPrinterParser(false).parse(context, text, position);
    }

    /**
     * Gets the formatter to use.
     * 
     * @param locale the locale to use, not null
     * @return the formatter, not null
     * @throws IllegalArgumentException if the formatter cannot be found
     */
    private DateTimeFormatter formatter(Locale locale) {

      return DateTimeFormatters.getFormatStyleProvider().getFormatter(this.dateStyle, this.timeStyle, this.chrono,
          locale);
    }

    @Override
    public String toString() {

      return "Localized(" + (this.dateStyle != null ? this.dateStyle : "") + ","
          + (this.timeStyle != null ? this.timeStyle : "") + "," + this.chrono.getId() + ")";
    }
  }

  // -------------------------------------------------------------------------
  /**
   * Length comparator.
   */
  static final Comparator<String> LENGTH_SORT = new Comparator<String>() {

    @Override
    public int compare(String str1, String str2) {

      return str1.length() == str2.length() ? str1.compareTo(str2) : str1.length() - str2.length();
    }
  };

  public static class SimpleImmutableEntry<K, V> implements Entry<K, V>, java.io.Serializable {

    private static final long serialVersionUID = 7138329143949025153L;

    private final K key;

    private final V value;

    public SimpleImmutableEntry(K key, V value) {

      this.key = key;
      this.value = value;
    }

    public SimpleImmutableEntry(Entry<? extends K, ? extends V> entry) {

      this.key = entry.getKey();
      this.value = entry.getValue();
    }

    public K getKey() {

      return this.key;
    }

    public V getValue() {

      return this.value;
    }

    public V setValue(V value) {

      throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {

      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry e = (Map.Entry) o;
      return Jdk7Methods.Objects_equals(this.key, e.getKey()) && Jdk7Methods.Objects_equals(this.value, e.getValue());
    }

    @Override
    public int hashCode() {

      return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
    }

    @Override
    public String toString() {

      return this.key + "=" + this.value;
    }

  }

}
