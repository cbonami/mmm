/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.color;

import net.sf.mmm.util.lang.api.AbstractDatatype;
import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.lang.base.GwtHelper;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsParseException;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a {@link Color} based on {@link Factor factors}. <br/>
 * <b>Note:</b><br/>
 * Use {@link Color} for simple and efficient representation and transport of color information. However, if
 * precision is required or for transformation between different color models use this class instead. <br/>
 * <b>Credits:</b><br/>
 * The algorithms for transformation of the color models are mainly taken from <a
 * href="http://en.wikipedia.org/wiki/HSL_and_HSV">HSL and HSV on wikipedia</a>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class GenericColor extends AbstractDatatype implements SimpleDatatype<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = 3175467633850341788L;

  /**
   * The {@link java.util.regex.Pattern} for a valid string representation.
   * 
   * @see #valueOf(String)
   * @see #toString(ColorModel)
   */
  private static final String PATTERN = "#RRGGBB|(rgb|hsl|hsv|hsb)[a](<num>,<num>,<num>[,<num>])";

  /** @see #getAlpha() */
  private Alpha alpha;

  /** @see #getHue() */
  private Hue hue;

  /** @see #getSaturationHsb() */
  private Saturation saturationHsb;

  /** @see #getSaturationHsl() */
  private Saturation saturationHsl;

  /** @see #getBrightness() */
  private Brightness brightness;

  /** @see #getLightness() */
  private Lightness lightness;

  /** @see #getChroma() */
  private Chroma chroma;

  /** @see #getRed() */
  private Red red;

  /** @see #getBlue() */
  private Blue blue;

  /** @see #getGreen() */
  private Green green;

  /**
   * The constructor. Use <code>valueOf</code> methods to instantiate.
   */
  protected GenericColor() {

    super();
  }

  /**
   * Parses the {@link GenericColor} given as {@link String} representation.
   * 
   * @param colorString is the color as {@link String}.
   * @return the parsed {@link GenericColor}.
   * @throws NlsParseException if the syntax is invalid.
   */
  public static GenericColor valueOf(String colorString) throws NlsParseException {

    NlsNullPointerException.checkNotNull("color", colorString);
    Color namedColor = Color.fromName(colorString);
    if (namedColor != null) {
      return valueOf(namedColor);
    }
    int length = colorString.length();
    Throwable cause = null;
    try {
      // "#RRGGBB" / #AARRGGBB
      Color hexColor = Color.parseHexString(colorString);
      if (hexColor != null) {
        return valueOf(hexColor);
      }
      // "rgb(1,1,1)" = 10
      if (length >= 7) {
        String model = GwtHelper.toUpperCase(colorString.substring(0, 3));
        ColorModel colorModel = ColorModel.valueOf(model);
        int index = 3;
        boolean hasAlpha = false;
        char c = Character.toLowerCase(colorString.charAt(index));
        if (c == 'a') {
          hasAlpha = true;
          index++;
          c = colorString.charAt(index);
        }
        if (c == '(') {
          index++;
          int endIndex = colorString.indexOf(',', index);
          if (endIndex > 0) {
            String firstSegment = colorString.substring(index, endIndex).trim();
            index = endIndex + 1;
            endIndex = colorString.indexOf(',', index);
            if (endIndex > 0) {
              String secondSegment = colorString.substring(index, endIndex).trim();
              index = endIndex + 1;
              if (hasAlpha) {
                endIndex = colorString.indexOf(',', index);
              } else {
                endIndex = length - 1;
              }
              if (endIndex > 0) {
                String thirdSegment = colorString.substring(index, endIndex).trim();
                Alpha alpha;
                if (hasAlpha) {
                  alpha = new Alpha(colorString.substring(endIndex + 1, length - 1));
                } else {
                  alpha = Alpha.OPAQUE;
                }
                switch (colorModel) {
                  case RGB:
                    return valueOf(new Red(firstSegment), new Green(secondSegment), new Blue(thirdSegment), alpha);
                  case HSL:
                    return valueOf(new Hue(firstSegment), new Saturation(secondSegment), new Lightness(thirdSegment),
                        alpha);
                  case HSV:
                  case HSB:
                    return valueOf(new Hue(firstSegment), new Saturation(secondSegment), new Brightness(thirdSegment),
                        alpha);
                  default :
                    throw new IllegalCaseException(ColorModel.class, colorModel);
                }
              }
            }
          }
        }
      }
    } catch (RuntimeException e) {
      cause = e;
    }
    throw new NlsParseException(cause, colorString, PATTERN, GenericColor.class);
  }

  /**
   * Converts the given {@link Color} to a {@link GenericColor}.
   * 
   * @param color is the discrete RGBA {@link Color}.
   * @return the corresponding {@link GenericColor}.
   */
  public static GenericColor valueOf(Color color) {

    NlsNullPointerException.checkNotNull(Color.class, color);
    Red red = new Red(color.getRed());
    Green green = new Green(color.getGreen());
    Blue blue = new Blue(color.getBlue());
    Alpha alpha = new Alpha(color.getAlpha());
    return valueOf(red, green, blue, alpha);
  }

  /**
   * Creates a {@link GenericColor} from the given {@link Segment}s of {@link ColorModel#RGB}.
   * 
   * @param red is the {@link Red} part.
   * @param green is the {@link Green} part.
   * @param blue is the {@link Blue} part.
   * @param alpha is the {@link Alpha} value.
   * @return the {@link GenericColor}.
   */
  public static GenericColor valueOf(Red red, Green green, Blue blue, Alpha alpha) {

    NlsNullPointerException.checkNotNull(Red.class, red);
    NlsNullPointerException.checkNotNull(Green.class, green);
    NlsNullPointerException.checkNotNull(Blue.class, blue);
    NlsNullPointerException.checkNotNull(Alpha.class, alpha);
    GenericColor genericColor = new GenericColor();
    genericColor.red = red;
    genericColor.green = green;
    genericColor.blue = blue;
    genericColor.alpha = alpha;
    // calculate min/max
    double r = red.getValueAsFactor();
    double g = green.getValueAsFactor();
    double b = blue.getValueAsFactor();
    double max = r;
    if (g > max) {
      max = g;
    }
    if (b > max) {
      max = b;
    }
    double min = r;
    if (g < min) {
      min = g;
    }
    if (b < min) {
      min = b;
    }
    double chroma = max - min;
    genericColor.chroma = new Chroma(chroma);

    double hue = calculateHue(r, g, b, max, chroma);
    genericColor.hue = new Hue(hue);
    double s;
    if (max == 0) {
      s = 0;
    } else {
      s = chroma / max;
    }
    genericColor.saturationHsb = new Saturation(s);
    double lightness = (max + min) / 2;
    genericColor.lightness = new Lightness(lightness);
    double saturationHsl = calculateSaturationHsl(chroma, lightness);
    genericColor.saturationHsl = new Saturation(saturationHsl);
    genericColor.brightness = new Brightness(max);
    return genericColor;
  }

  /**
   * Calculate the {@link Saturation} for {@link ColorModel#HSL}.
   * 
   * @param chroma is the {@link Chroma} value.
   * @param lightness is the {@link Lightness} value.
   * @return the {@link Saturation}.
   */
  private static double calculateSaturationHsl(double chroma, double lightness) {

    double d = 1 - Math.abs(2 * lightness - 1);
    if (d == 0) {
      return 0;
    }
    return chroma / d;
  }

  /**
   * Calculate the {@link Hue}.
   * 
   * @param red is the {@link Red} value.
   * @param green is the {@link Green} value.
   * @param blue is the {@link Blue} value.
   * @param max is the maximum of RGB.
   * @param chroma is the {@link Chroma} value.
   * @return the {@link Saturation}.
   */
  private static double calculateHue(double red, double green, double blue, double max, double chroma) {

    if (chroma == 0) {
      return 0;
    } else {
      double hue;
      if (red == max) {
        hue = (green - blue) / chroma;
      } else if (green == max) {
        hue = (blue - red) / chroma + 2;
      } else {
        hue = (red - green) / chroma + 4;
      }
      hue = hue * 60.0;
      if (hue < 0) {
        hue = hue + Hue.MAX_VALUE;
      }
      return hue;
    }
  }

  /**
   * Creates a {@link GenericColor} from the given {@link Segment}s of {@link ColorModel#HSB}.
   * 
   * @param hue is the {@link Hue} part.
   * @param saturation is the {@link Saturation} part.
   * @param brightness is the {@link Brightness} part.
   * @param alpha is the {@link Alpha} value.
   * @return the {@link GenericColor}.
   */
  public static GenericColor valueOf(Hue hue, Saturation saturation, Brightness brightness, Alpha alpha) {

    NlsNullPointerException.checkNotNull(Hue.class, hue);
    NlsNullPointerException.checkNotNull(Saturation.class, saturation);
    NlsNullPointerException.checkNotNull(Brightness.class, brightness);
    NlsNullPointerException.checkNotNull(Alpha.class, alpha);
    GenericColor genericColor = new GenericColor();
    genericColor.hue = hue;
    genericColor.saturationHsb = saturation;
    genericColor.brightness = brightness;
    genericColor.alpha = alpha;
    double b = brightness.getValueAsFactor();
    double chroma = b * saturation.getValueAsFactor();
    genericColor.chroma = new Chroma(chroma);
    double min = b - chroma;
    double lightness = (min + b) / 2;
    genericColor.lightness = new Lightness(lightness);
    double saturationHsl = calculateSaturationHsl(chroma, lightness);
    genericColor.saturationHsl = new Saturation(saturationHsl);
    calculateRgb(genericColor, hue, min, chroma);
    return genericColor;
  }

  /**
   * Creates a {@link GenericColor} from the given {@link Segment}s of {@link ColorModel#HSL}.
   * 
   * @param hue is the {@link Hue} part.
   * @param saturation is the {@link Saturation} part.
   * @param lightness is the {@link Lightness} part.
   * @param alpha is the {@link Alpha} value.
   * @return the {@link GenericColor}.
   */
  public static GenericColor valueOf(Hue hue, Saturation saturation, Lightness lightness, Alpha alpha) {

    NlsNullPointerException.checkNotNull(Hue.class, hue);
    NlsNullPointerException.checkNotNull(Saturation.class, saturation);
    NlsNullPointerException.checkNotNull(Lightness.class, lightness);
    NlsNullPointerException.checkNotNull(Alpha.class, alpha);
    GenericColor genericColor = new GenericColor();
    genericColor.hue = hue;
    genericColor.saturationHsl = saturation;
    genericColor.lightness = lightness;
    genericColor.alpha = alpha;
    double l = lightness.getValueAsFactor();
    double chroma;
    if (l >= 0.5) {
      chroma = saturation.getValueAsFactor() * (2 - 2 * l);
    } else {
      chroma = saturation.getValueAsFactor() * 2 * l;
    }
    double m = l - (chroma / 2);
    double saturationHsb;
    double b = chroma + m;
    genericColor.brightness = new Brightness(b);
    if (l == 0) {
      saturationHsb = 0;
    } else {
      saturationHsb = chroma / b;
    }
    genericColor.saturationHsb = new Saturation(saturationHsb);
    calculateRgb(genericColor, hue, m, chroma);
    return genericColor;
  }

  /**
   * Calculates and the RGB values and sets them in the given {@link GenericColor}.
   * 
   * @param genericColor is the {@link GenericColor} to complete.
   * @param hue is the {@link Hue} value.
   * @param min is the minimum {@link Factor} of R/G/B.
   * @param chroma is the {@link Chroma} value.
   */
  private static void calculateRgb(GenericColor genericColor, Hue hue, double min, double chroma) {

    genericColor.chroma = new Chroma(chroma);
    double hueX = hue.getValue().doubleValue() / 60;
    double x = chroma * (1 - Math.abs((hueX % 2) - 1));
    double red, green, blue;
    // if (hueX == 0) {
    // red = min;
    // green = min;
    // blue = min;
    // } else
    if (hueX < 1) {
      red = chroma + min;
      green = x + min;
      blue = min;
    } else if (hueX < 2) {
      red = x + min;
      green = chroma + min;
      blue = min;
    } else if (hueX < 3) {
      red = min;
      green = chroma + min;
      blue = x + min;
    } else if (hueX < 4) {
      red = min;
      green = x + min;
      blue = chroma + min;
    } else if (hueX < 5) {
      red = x + min;
      green = min;
      blue = chroma + min;
    } else {
      red = chroma + min;
      green = min;
      blue = x + min;
    }
    genericColor.red = new Red(red);
    genericColor.green = new Green(green);
    genericColor.blue = new Blue(blue);
  }

  /**
   * @return the {@link Alpha alpha value as factor}.
   */
  public Alpha getAlpha() {

    return this.alpha;
  }

  /**
   * @return the {@link Hue}.
   */
  public Hue getHue() {

    return this.hue;
  }

  /**
   * @see ColorSegmentType#SATURATION_HSB
   * @return the {@link Saturation} in {@link ColorModel#HSB}/{@link ColorModel#HSV} color model (hexcone).
   */
  public Saturation getSaturationHsb() {

    return this.saturationHsb;
  }

  /**
   * @see ColorSegmentType#SATURATION_HSL
   * @return the {@link Saturation} in {@link ColorModel#HSL} {@link ColorModel color model} (bi-hexcone).
   */
  public Saturation getSaturationHsl() {

    return this.saturationHsl;
  }

  /**
   * @return the brightness
   */
  public Brightness getBrightness() {

    return this.brightness;
  }

  /**
   * @return the lightness
   */
  public Lightness getLightness() {

    return this.lightness;
  }

  /**
   * @return the chroma
   */
  public Chroma getChroma() {

    return this.chroma;
  }

  /**
   * @return the red
   */
  public Red getRed() {

    return this.red;
  }

  /**
   * @return the blue
   */
  public Blue getBlue() {

    return this.blue;
  }

  /**
   * @return the green
   */
  public Green getGreen() {

    return this.green;
  }

  /**
   * @param type is the {@link ColorSegmentType} identifying the requested {@link Segment}.
   * @return the {@link Segment} of the given <code>type</code>.
   */
  public AbstractDoubleSegment getSegment(ColorSegmentType type) {

    switch (type) {
      case RED:
        return this.red;
      case GREEN:
        return this.green;
      case BLUE:
        return this.blue;
      case HUE:
        return this.hue;
      case SATURATION_HSB:
        return this.saturationHsb;
      case SATURATION_HSL:
        return this.saturationHsl;
      case BRIGHTNESS:
        return this.brightness;
      case LIGHTNESS:
        return this.lightness;
      case ALPHA:
        return this.alpha;
      default :
        throw new IllegalCaseException(ColorSegmentType.class, type);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return getTitle();
  }

  /**
   * @return the converted {@link Color} corresponding to this {@link GenericColor}.
   */
  public Color toColor() {

    return new Color(this.red.getValueAsByte(), this.green.getValueAsByte(), this.blue.getValueAsByte(),
        this.alpha.getValueAsByte());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return toString(ColorModel.RGB);
  }

  /**
   * @param colorModel is the {@link ColorModel}.
   * @return this color as {@link String} in notation of the given {@link ColorModel} (e.g.
   *         "rgba(255, 128, 64, 1.0)" for {@link ColorModel#RGB}).
   */
  public String toString(ColorModel colorModel) {

    StringBuilder buffer = new StringBuilder(GwtHelper.toLowerCase(colorModel.getTitle()));
    buffer.append("a(");
    buffer.append(getSegment(colorModel.getFirstSegmentType()));
    buffer.append(',');
    buffer.append(getSegment(colorModel.getSecondSegmentType()));
    buffer.append(',');
    buffer.append(getSegment(colorModel.getThirdSegmentType()));
    buffer.append(',');
    buffer.append(this.alpha);
    buffer.append(')');
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.alpha == null) ? 0 : this.alpha.hashCode());
    result = prime * result + ((this.blue == null) ? 0 : this.blue.hashCode());
    result = prime * result + ((this.brightness == null) ? 0 : this.brightness.hashCode());
    result = prime * result + ((this.chroma == null) ? 0 : this.chroma.hashCode());
    result = prime * result + ((this.green == null) ? 0 : this.green.hashCode());
    result = prime * result + ((this.hue == null) ? 0 : this.hue.hashCode());
    result = prime * result + ((this.lightness == null) ? 0 : this.lightness.hashCode());
    result = prime * result + ((this.red == null) ? 0 : this.red.hashCode());
    result = prime * result + ((this.saturationHsb == null) ? 0 : this.saturationHsb.hashCode());
    result = prime * result + ((this.saturationHsl == null) ? 0 : this.saturationHsl.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    GenericColor other = (GenericColor) obj;
    if (this.alpha == null) {
      if (other.alpha != null) {
        return false;
      }
    } else if (!this.alpha.equals(other.alpha)) {
      return false;
    }
    if (this.blue == null) {
      if (other.blue != null) {
        return false;
      }
    } else if (!this.blue.equals(other.blue)) {
      return false;
    }
    if (this.brightness == null) {
      if (other.brightness != null) {
        return false;
      }
    } else if (!this.brightness.equals(other.brightness)) {
      return false;
    }
    if (this.chroma == null) {
      if (other.chroma != null) {
        return false;
      }
    } else if (!this.chroma.equals(other.chroma)) {
      return false;
    }
    if (this.green == null) {
      if (other.green != null) {
        return false;
      }
    } else if (!this.green.equals(other.green)) {
      return false;
    }
    if (this.hue == null) {
      if (other.hue != null) {
        return false;
      }
    } else if (!this.hue.equals(other.hue)) {
      return false;
    }
    if (this.lightness == null) {
      if (other.lightness != null) {
        return false;
      }
    } else if (!this.lightness.equals(other.lightness)) {
      return false;
    }
    if (this.red == null) {
      if (other.red != null) {
        return false;
      }
    } else if (!this.red.equals(other.red)) {
      return false;
    }
    if (this.saturationHsb == null) {
      if (other.saturationHsb != null) {
        return false;
      }
    } else if (!this.saturationHsb.equals(other.saturationHsb)) {
      return false;
    }
    if (this.saturationHsl == null) {
      if (other.saturationHsl != null) {
        return false;
      }
    } else if (!this.saturationHsl.equals(other.saturationHsl)) {
      return false;
    }
    return true;
  }

}
