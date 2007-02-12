/* $Id$ */
package net.sf.mmm.util;

import java.util.regex.Pattern;

/**
 * This class is a collection of utility functions for {@link String} handling
 * and manipulation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StringUtil {

  /**
   * Forbidden constructor.
   */
  private StringUtil() {

    super();
  }

  /** a string representing the boolean value <code>true</code> */
  public static final String TRUE = String.valueOf(true);

  /** a string representing the boolean value <code>false</code> */
  public static final String FALSE = String.valueOf(false);

  /**
   * This method parses a boolean value given as string.
   * 
   * @param booleanValue
   *        is the boolean value as string.
   * @return <code>true</code> if the given string
   *         {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #TRUE true}, <code>false</code> if it
   *         {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #FALSE false} and <code>null</code> in any other case.
   */
  public static Boolean parseBoolean(String booleanValue) {

    if (TRUE.equalsIgnoreCase(booleanValue)) {
      return Boolean.TRUE;
    } else if (FALSE.equalsIgnoreCase(booleanValue)) {
      return Boolean.FALSE;
    } else {
      return null;
    }
  }

  /**
   * This method replaces all occurences of the string <code>match</code> with
   * the string <code>replace</code> in the given string.
   * 
   * @param string
   *        is the string where to replace.
   * @param match
   *        is the string that is searched and replaced.
   * @param replace
   *        is the string <code>match</code> is substituted with.
   * @return the given string whith all occurences of <code>match</code>
   *         replaced by <code>replace</code>.
   */
  public static String replace(String string, String match, String replace) {

    StringBuffer result = new StringBuffer();
    int matchLen = match.length();
    int oldPos = 0;
    int pos = string.indexOf(match);
    while (pos >= 0) {
      if (pos > oldPos) {
        result.append(string.substring(oldPos, pos));
      }
      result.append(replace);
      oldPos = pos + matchLen;
      pos = string.indexOf(match, oldPos);
    }
    if (oldPos < string.length()) {
      result.append(string.substring(oldPos, string.length()));
    }
    return result.toString();
  }

  /**
   * IsEmpty with trim set to <code>true</code>.
   * 
   * @see #isEmpty(String, boolean)
   * 
   * @param string
   *        is the string to check.
   * @return <code>true</code> if the given string is <code>null</code> or
   *         has a trimmed length of zero, <code>false</code> otherwise.
   */
  public static boolean isEmpty(String string) {

    return isEmpty(string, true);
  }

  /**
   * This method determines if the given string contains no information.
   * 
   * @param string
   *        is the string to check.
   * @param trim
   *        if whitespaces should be ignored and a string with a trimmed length
   *        of zero is considered as emtpy.
   * @return <code>true</code> if the given string is <code>null</code> or
   *         has a (trimmed) length of zero, <code>false</code> otherwise.
   */
  public static boolean isEmpty(String string, boolean trim) {

    if (string == null) {
      return true;
    }
    if (trim) {
      string = string.trim();
    }
    return (string.length() == 0);
  }

  /**
   * This method compiles the given <code>pattern</code> to a
   * {@link java.util.regex.Pattern} interpreting it as glob-pattern. In a
   * glob-pattern only the wildcard characters "*" and "?" are treated special.
   * The asterisk ("*") can match any string including the empty string and the
   * questionmark ("?") can match any single character.
   * 
   * @param pattern
   *        is the glob pattern to compile.
   * @return the compiled pattern.
   */
  public static Pattern compileGlobPattern(String pattern) {

    return compileGlobPattern(pattern, false);
  }

  /**
   * This method compiles the given <code>pattern</code> to a
   * {@link java.util.regex.Pattern} interpreting it as glob-pattern. In a
   * glob-pattern only the wildcard characters "*" and "?" are treated special.
   * The asterisk ("*") can match any string including the empty string and the
   * questionmark ("?") can match any single character.
   * 
   * @param pattern
   *        is the glob pattern to compile.
   * @param requireWildcard -
   *        if <code>true</code> the given <code>pattern</code> needs to
   *        have a wildcard character in order to be compiled,
   *        <code>false</code> otherwise.
   * @return the compiled pattern or <code>null</code> if
   *         <code>requireWildcard</code> is <code>true</code> but the given
   *         <code>pattern</code> does NOT contain any wildcard.
   */
  public static Pattern compileGlobPattern(String pattern, boolean requireWildcard) {

    char[] chars = pattern.toCharArray();
    StringBuffer buffer = new StringBuffer(pattern.length() + 8);
    boolean hasWildcard = false;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (c == '*') {
        hasWildcard = true;
        buffer.append(".*");
      } else if (c == '?') {
        hasWildcard = true;
        buffer.append('.');
      } else if (c == '.') {
        buffer.append("\\.");
      } else if (c == '\\') {
        buffer.append("\\\\");
      } else {
        buffer.append(c);
      }
    }
    if (requireWildcard && !hasWildcard) {
      return null;
    } else {
      return Pattern.compile(buffer.toString());      
    }
  }

  /**
   * This method formats a positive number to a string with at least the given
   * number of digits padding it with leading zeros.<br>
   * Examples:
   * <ul>
   * <li><code>padNumber(5, 3)</code> will return <code>"005"</code></li>
   * <li><code>padNumber(25, 3)</code> will return <code>"025"</code></li>
   * <li><code>padNumber(100, 3)</code> will return <code>"100"</code></li>
   * </ul>
   * 
   * @param number
   *        is the positive number to format.
   * @param digits
   *        is the (minimum) number of digits required.
   * @return the number as string with the length of (at least)
   *         <code>digits</code>. If the number is less, leading zeros are
   *         appended.
   */
  public static String padNumber(long number, int digits) {

    String result = Long.toString(number);
    int leadingZeros = digits - result.length();
    if (leadingZeros > 0) {
      int capacity = result.length() + leadingZeros;
      StringBuffer buffer = new StringBuffer(capacity);
      buffer.append(result);
      while (leadingZeros > 0) {
        buffer.append('0');
        leadingZeros--;
      }
      result = buffer.toString();
    }
    return result;
  }

}
