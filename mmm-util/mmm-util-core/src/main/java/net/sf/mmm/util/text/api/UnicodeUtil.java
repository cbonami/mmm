/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.api;


/**
 * This is the interface for a collection of utility functions that help with
 * unicode characters and texts.
 * 
 * @see DiacriticalMark
 * @see net.sf.mmm.util.xml.api.XmlUtil#resolveEntity(String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface UnicodeUtil {
  char SPACE = 0x0020;
  char EXCLAMATION_MARK = 0x0021;
  char QUOTATION_MARK = 0x0022;
  char NUMBER_SIGN = 0x0023;
  char DOLLAR_SIGN = 0x0024;
  char PERCENT_SIGN = 0x0025;
  char AMPERSAND = 0x0026;
  char APOSTROPHE = 0x0027;
  char LEFT_PARENTHESIS = 0x0028;
  char RIGHT_PARENTHESIS = 0x0029;
  char ASTERISK = 0x002A;
  char PLUS_SIGN = 0x002B;
  char COMMA = 0x002C;
  char HYPHEN_MINUS = 0x002D;
  char FULL_STOP = 0x002E;
  char SOLIDUS = 0x002F;
  char DIGIT_ZERO = 0x0030;
  char DIGIT_ONE = 0x0031;
  char DIGIT_TWO = 0x0032;
  char DIGIT_THREE = 0x0033;
  char DIGIT_FOUR = 0x0034;
  char DIGIT_FIVE = 0x0035;
  char DIGIT_SIX = 0x0036;
  char DIGIT_SEVEN = 0x0037;
  char DIGIT_EIGHT = 0x0038;
  char DIGIT_NINE = 0x0039;
  char COLON = 0x003A;
  char SEMICOLON = 0x003B;
  char LESS_THAN_SIGN = 0x003C;
  char EQUALS_SIGN = 0x003D;
  char GREATER_THAN_SIGN = 0x003E;
  char QUESTION_MARK = 0x003F;
  char COMMERCIAL_AT = 0x0040;
  char LATIN_CAPITAL_LETTER_A = 0x0041;
  char LATIN_CAPITAL_LETTER_B = 0x0042;
  char LATIN_CAPITAL_LETTER_C = 0x0043;
  char LATIN_CAPITAL_LETTER_D = 0x0044;
  char LATIN_CAPITAL_LETTER_E = 0x0045;
  char LATIN_CAPITAL_LETTER_F = 0x0046;
  char LATIN_CAPITAL_LETTER_G = 0x0047;
  char LATIN_CAPITAL_LETTER_H = 0x0048;
  char LATIN_CAPITAL_LETTER_I = 0x0049;
  char LATIN_CAPITAL_LETTER_J = 0x004A;
  char LATIN_CAPITAL_LETTER_K = 0x004B;
  char LATIN_CAPITAL_LETTER_L = 0x004C;
  char LATIN_CAPITAL_LETTER_M = 0x004D;
  char LATIN_CAPITAL_LETTER_N = 0x004E;
  char LATIN_CAPITAL_LETTER_O = 0x004F;
  char LATIN_CAPITAL_LETTER_P = 0x0050;
  char LATIN_CAPITAL_LETTER_Q = 0x0051;
  char LATIN_CAPITAL_LETTER_R = 0x0052;
  char LATIN_CAPITAL_LETTER_S = 0x0053;
  char LATIN_CAPITAL_LETTER_T = 0x0054;
  char LATIN_CAPITAL_LETTER_U = 0x0055;
  char LATIN_CAPITAL_LETTER_V = 0x0056;
  char LATIN_CAPITAL_LETTER_W = 0x0057;
  char LATIN_CAPITAL_LETTER_X = 0x0058;
  char LATIN_CAPITAL_LETTER_Y = 0x0059;
  char LATIN_CAPITAL_LETTER_Z = 0x005A;
  char LEFT_SQUARE_BRACKET = 0x005B;
  char REVERSE_SOLIDUS = 0x005C;
  char RIGHT_SQUARE_BRACKET = 0x005D;
  char CIRCUMFLEX_ACCENT = 0x005E;
  char LOW_LINE = 0x005F;
  char GRAVE_ACCENT = 0x0060;
  char LATIN_SMALL_LETTER_A = 0x0061;
  char LATIN_SMALL_LETTER_B = 0x0062;
  char LATIN_SMALL_LETTER_C = 0x0063;
  char LATIN_SMALL_LETTER_D = 0x0064;
  char LATIN_SMALL_LETTER_E = 0x0065;
  char LATIN_SMALL_LETTER_F = 0x0066;
  char LATIN_SMALL_LETTER_G = 0x0067;
  char LATIN_SMALL_LETTER_H = 0x0068;
  char LATIN_SMALL_LETTER_I = 0x0069;
  char LATIN_SMALL_LETTER_J = 0x006A;
  char LATIN_SMALL_LETTER_K = 0x006B;
  char LATIN_SMALL_LETTER_L = 0x006C;
  char LATIN_SMALL_LETTER_M = 0x006D;
  char LATIN_SMALL_LETTER_N = 0x006E;
  char LATIN_SMALL_LETTER_O = 0x006F;
  char LATIN_SMALL_LETTER_P = 0x0070;
  char LATIN_SMALL_LETTER_Q = 0x0071;
  char LATIN_SMALL_LETTER_R = 0x0072;
  char LATIN_SMALL_LETTER_S = 0x0073;
  char LATIN_SMALL_LETTER_T = 0x0074;
  char LATIN_SMALL_LETTER_U = 0x0075;
  char LATIN_SMALL_LETTER_V = 0x0076;
  char LATIN_SMALL_LETTER_W = 0x0077;
  char LATIN_SMALL_LETTER_X = 0x0078;
  char LATIN_SMALL_LETTER_Y = 0x0079;
  char LATIN_SMALL_LETTER_Z = 0x007A;
  char LEFT_CURLY_BRACKET = 0x007B;
  char VERTICAL_LINE = 0x007C;
  char RIGHT_CURLY_BRACKET = 0x007D;
  char TILDE = 0x007E;
  /**
   * The soft hyphen that indicates a word-wrap position (for hyphenation).
   * Similar to ASCII hyphen-minus ('-').
   */
  char SOFT_HYPHEN = 173;

  /** A space that should NOT be wrapped. */
  char NON_BREAKING_SPACE = 160;

  char MODIFIER_LETTER_SMALL_H = 0x02B0;
  char MODIFIER_LETTER_SMALL_H_WITH_HOOK = 0x02B1;
  char MODIFIER_LETTER_SMALL_J = 0x02B2;
  char MODIFIER_LETTER_SMALL_R = 0x02B3;
  char MODIFIER_LETTER_SMALL_TURNED_R = 0x02B4;
  char MODIFIER_LETTER_SMALL_TURNED_R_WITH_HOOK = 0x02B5;
  char MODIFIER_LETTER_SMALL_CAPITAL_INVERTED_R = 0x02B6;
  char MODIFIER_LETTER_SMALL_W = 0x02B7;
  char MODIFIER_LETTER_SMALL_Y = 0x02B8;
  char MODIFIER_LETTER_PRIME = 0x02B9;
  char MODIFIER_LETTER_DOUBLE_PRIME = 0x02BA;
  char MODIFIER_LETTER_TURNED_COMMA = 0x02BB;
  char MODIFIER_LETTER_APOSTROPHE = 0x02BC;
  char MODIFIER_LETTER_REVERSED_COMMA = 0x02BD;
  char MODIFIER_LETTER_RIGHT_HALF_RING = 0x02BE;
  char MODIFIER_LETTER_LEFT_HALF_RING = 0x02BF;
  char MODIFIER_LETTER_GLOTTAL_STOP = 0x02C0;
  char MODIFIER_LETTER_REVERSED_GLOTTAL_STOP = 0x02C1;
  char MODIFIER_LETTER_LEFT_ARROWHEAD = 0x02C2;
  char MODIFIER_LETTER_RIGHT_ARROWHEAD = 0x02C3;
  char MODIFIER_LETTER_UP_ARROWHEAD = 0x02C4;
  char MODIFIER_LETTER_DOWN_ARROWHEAD = 0x02C5;
  char MODIFIER_LETTER_CIRCUMFLEX_ACCENT = 0x02C6;
  char CARON = 0x02C7;
  char MODIFIER_LETTER_VERTICAL_LINE = 0x02C8;
  char MODIFIER_LETTER_MACRON = 0x02C9;
  char MODIFIER_LETTER_ACUTE_ACCENT = 0x02CA;
  char MODIFIER_LETTER_GRAVE_ACCENT = 0x02CB;
  char MODIFIER_LETTER_LOW_VERTICAL_LINE = 0x02CC;
  char MODIFIER_LETTER_LOW_MACRON = 0x02CD;
  char MODIFIER_LETTER_LOW_GRAVE_ACCENT = 0x02CE;
  char MODIFIER_LETTER_LOW_ACUTE_ACCENT = 0x02CF;
  char MODIFIER_LETTER_TRIANGULAR_COLON = 0x02D0;
  char MODIFIER_LETTER_HALF_TRIANGULAR_COLON = 0x02D1;
  char MODIFIER_LETTER_CENTRED_RIGHT_HALF_RING = 0x02D2;
  char MODIFIER_LETTER_CENTRED_LEFT_HALF_RING = 0x02D3;
  char MODIFIER_LETTER_UP_TACK = 0x02D4;
  char MODIFIER_LETTER_DOWN_TACK = 0x02D5;
  char MODIFIER_LETTER_PLUS_SIGN = 0x02D6;
  char MODIFIER_LETTER_MINUS_SIGN = 0x02D7;
  char BREVE = 0x02D8;
  char DOT_ABOVE = 0x02D9;
  char RING_ABOVE = 0x02DA;
  char OGONEK = 0x02DB;
  char SMALL_TILDE = 0x02DC;
  char DOUBLE_ACUTE_ACCENT = 0x02DD;
  char MODIFIER_LETTER_RHOTIC_HOOK = 0x02DE;
  char MODIFIER_LETTER_CROSS_ACCENT = 0x02DF;
  char MODIFIER_LETTER_SMALL_GAMMA = 0x02E0;
  char MODIFIER_LETTER_SMALL_L = 0x02E1;
  char MODIFIER_LETTER_SMALL_S = 0x02E2;
  char MODIFIER_LETTER_SMALL_X = 0x02E3;
  char MODIFIER_LETTER_SMALL_REVERSED_GLOTTAL_STOP = 0x02E4;
  char MODIFIER_LETTER_EXTRA_HIGH_TONE_BAR = 0x02E5;
  char MODIFIER_LETTER_HIGH_TONE_BAR = 0x02E6;
  char MODIFIER_LETTER_MID_TONE_BAR = 0x02E7;
  char MODIFIER_LETTER_LOW_TONE_BAR = 0x02E8;
  char MODIFIER_LETTER_EXTRA_LOW_TONE_BAR = 0x02E9;
  char MODIFIER_LETTER_YIN_DEPARTING_TONE_MARK = 0x02EA;
  char MODIFIER_LETTER_YANG_DEPARTING_TONE_MARK = 0x02EB;
  char MODIFIER_LETTER_VOICING = 0x02EC;
  char MODIFIER_LETTER_UNASPIRATED = 0x02ED;
  char MODIFIER_LETTER_DOUBLE_APOSTROPHE = 0x02EE;
  
  
  char INVERTED_EXCLAMATION_MARK = 0x00A1;
  char CENT_SIGN = 0x00A2;
  char POUND_SIGN = 0x00A3;
  char CURRENCY_SIGN = 0x00A4;
  char YEN_SIGN = 0x00A5;
  char BROKEN_BAR = 0x00A6;
  char SECTION_SIGN = 0x00A7;
  char DIAERESIS = 0x00A8;
  char COPYRIGHT_SIGN = 0x00A9;
  char FEMININE_ORDINAL_INDICATOR = 0x00AA;
  char LEFT_POINTING_DOUBLE_ANGLE_QUOTATION_MARK = 0x00AB;
  char NOT_SIGN = 0x00AC;
  char REGISTERED_SIGN = 0x00AE;
  char MACRON = 0x00AF;
  char DEGREE_SIGN = 0x00B0;
  char PLUS_MINUS_SIGN = 0x00B1;
  char SUPERSCRIPT_TWO = 0x00B2;
  char SUPERSCRIPT_THREE = 0x00B3;
  char ACUTE_ACCENT = 0x00B4;
  char MICRO_SIGN = 0x00B5;
  char PILCROW_SIGN = 0x00B6;
  char MIDDLE_DOT = 0x00B7;
  char CEDILLA = 0x00B8;
  char SUPERSCRIPT_ONE = 0x00B9;
  char MASCULINE_ORDINAL_INDICATOR = 0x00BA;
  char RIGHT_POINTING_DOUBLE_ANGLE_QUOTATION_MARK = 0x00BB;
  char VULGAR_FRACTION_ONE_QUARTER = 0x00BC;
  char VULGAR_FRACTION_ONE_HALF = 0x00BD;
  char VULGAR_FRACTION_THREE_QUARTERS = 0x00BE;
  char INVERTED_QUESTION_MARK = 0x00BF;
  
  
  char LATIN_CAPITAL_LETTER_A_WITH_GRAVE = 0x00C0;
  char LATIN_CAPITAL_LETTER_A_WITH_ACUTE = 0x00C1;
  char LATIN_CAPITAL_LETTER_A_WITH_CIRCUMFLEX = 0x00C2;
  char LATIN_CAPITAL_LETTER_A_WITH_TILDE = 0x00C3;
  char LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS = 0x00C4;
  char LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE = 0x00C5;
  char LATIN_CAPITAL_LETTER_AE  = 0x00C6;
  char LATIN_CAPITAL_LETTER_C_WITH_CEDILLA = 0x00C7;
  char LATIN_CAPITAL_LETTER_E_WITH_GRAVE = 0x00C8;
  char LATIN_CAPITAL_LETTER_E_WITH_ACUTE = 0x00C9;
  char LATIN_CAPITAL_LETTER_E_WITH_CIRCUMFLEX = 0x00CA;
  char LATIN_CAPITAL_LETTER_E_WITH_DIAERESIS = 0x00CB;
  char LATIN_CAPITAL_LETTER_I_WITH_GRAVE = 0x00CC;
  char LATIN_CAPITAL_LETTER_I_WITH_ACUTE = 0x00CD;
  char LATIN_CAPITAL_LETTER_I_WITH_CIRCUMFLEX = 0x00CE;
  char LATIN_CAPITAL_LETTER_I_WITH_DIAERESIS = 0x00CF;
  char LATIN_CAPITAL_LETTER_ETH = 0x00D0;
  char LATIN_CAPITAL_LETTER_N_WITH_TILDE = 0x00D1;
  char LATIN_CAPITAL_LETTER_O_WITH_GRAVE = 0x00D2;
  char LATIN_CAPITAL_LETTER_O_WITH_ACUTE = 0x00D3;
  char LATIN_CAPITAL_LETTER_O_WITH_CIRCUMFLEX = 0x00D4;
  char LATIN_CAPITAL_LETTER_O_WITH_TILDE = 0x00D5;
  char LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS = 0x00D6;
  char MULTIPLICATION_SIGN  = 0x00D7;
  char LATIN_CAPITAL_LETTER_O_WITH_STROKE = 0x00D8;
  char LATIN_CAPITAL_LETTER_U_WITH_GRAVE = 0x00D9;
  char LATIN_CAPITAL_LETTER_U_WITH_ACUTE = 0x00DA;
  char LATIN_CAPITAL_LETTER_U_WITH_CIRCUMFLEX = 0x00DB;
  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS = 0x00DC;
  char LATIN_CAPITAL_LETTER_Y_WITH_ACUTE = 0x00DD;
  char LATIN_CAPITAL_LETTER_THORN = 0x00DE;
  char LATIN_SMALL_LETTER_SHARP_S = 0x00DF;
  char LATIN_SMALL_LETTER_A_WITH_GRAVE = 0x00E0;
  char LATIN_SMALL_LETTER_A_WITH_ACUTE = 0x00E1;
  char LATIN_SMALL_LETTER_A_WITH_CIRCUMFLEX = 0x00E2;
  char LATIN_SMALL_LETTER_A_WITH_TILDE = 0x00E3;
  char LATIN_SMALL_LETTER_A_WITH_DIAERESIS = 0x00E4;
  char LATIN_SMALL_LETTER_A_WITH_RING_ABOVE = 0x00E5;
  char LATIN_SMALL_LETTER_AE  = 0x00E6;
  char LATIN_SMALL_LETTER_C_WITH_CEDILLA = 0x00E7;
  char LATIN_SMALL_LETTER_E_WITH_GRAVE = 0x00E8;
  char LATIN_SMALL_LETTER_E_WITH_ACUTE = 0x00E9;
  char LATIN_SMALL_LETTER_E_WITH_CIRCUMFLEX = 0x00EA;
  char LATIN_SMALL_LETTER_E_WITH_DIAERESIS = 0x00EB;
  char LATIN_SMALL_LETTER_I_WITH_GRAVE = 0x00EC;
  char LATIN_SMALL_LETTER_I_WITH_ACUTE = 0x00ED;
  char LATIN_SMALL_LETTER_I_WITH_CIRCUMFLEX = 0x00EE;
  char LATIN_SMALL_LETTER_I_WITH_DIAERESIS = 0x00EF;
  char LATIN_SMALL_LETTER_ETH = 0x00F0;
  char LATIN_SMALL_LETTER_N_WITH_TILDE = 0x00F1;
  char LATIN_SMALL_LETTER_O_WITH_GRAVE = 0x00F2;
  char LATIN_SMALL_LETTER_O_WITH_ACUTE = 0x00F3;
  char LATIN_SMALL_LETTER_O_WITH_CIRCUMFLEX = 0x00F4;
  char LATIN_SMALL_LETTER_O_WITH_TILDE = 0x00F5;
  char LATIN_SMALL_LETTER_O_WITH_DIAERESIS = 0x00F6;
  char DIVISION_SIGN = 0x00F7;
  char LATIN_SMALL_LETTER_O_WITH_STROKE = 0x00F8;
  char LATIN_SMALL_LETTER_U_WITH_GRAVE = 0x00F9;
  char LATIN_SMALL_LETTER_U_WITH_ACUTE = 0x00FA;
  char LATIN_SMALL_LETTER_U_WITH_CIRCUMFLEX = 0x00FB;
  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS = 0x00FC;
  char LATIN_SMALL_LETTER_Y_WITH_ACUTE = 0x00FD;
  char LATIN_SMALL_LETTER_THORN = 0x00FE;
  char LATIN_SMALL_LETTER_Y_WITH_DIAERESIS = 0x00FF;

  char LATIN_CAPITAL_LETTER_A_WITH_MACRON = 0x0100;
  char LATIN_SMALL_LETTER_A_WITH_MACRON = 0x0101;
  char LATIN_CAPITAL_LETTER_A_WITH_BREVE = 0x0102;
  char LATIN_SMALL_LETTER_A_WITH_BREVE = 0x0103;
  char LATIN_CAPITAL_LETTER_A_WITH_OGONEK = 0x0104;
  char LATIN_SMALL_LETTER_A_WITH_OGONEK = 0x0105;
  char LATIN_CAPITAL_LETTER_C_WITH_ACUTE = 0x0106;
  char LATIN_SMALL_LETTER_C_WITH_ACUTE = 0x0107;
  char LATIN_CAPITAL_LETTER_C_WITH_CIRCUMFLEX = 0x0108;
  char LATIN_SMALL_LETTER_C_WITH_CIRCUMFLEX = 0x0109;
  char LATIN_CAPITAL_LETTER_C_WITH_DOT_ABOVE = 0x010A;
  char LATIN_SMALL_LETTER_C_WITH_DOT_ABOVE = 0x010B;
  char LATIN_CAPITAL_LETTER_C_WITH_CARON = 0x010C;
  char LATIN_SMALL_LETTER_C_WITH_CARON = 0x010D;
  char LATIN_CAPITAL_LETTER_D_WITH_CARON = 0x010E;
  char LATIN_SMALL_LETTER_D_WITH_CARON = 0x010F;
  char LATIN_CAPITAL_LETTER_D_WITH_STROKE = 0x0110;
  char LATIN_SMALL_LETTER_D_WITH_STROKE = 0x0111;
  char LATIN_CAPITAL_LETTER_E_WITH_MACRON = 0x0112;
  char LATIN_SMALL_LETTER_E_WITH_MACRON = 0x0113;
  char LATIN_CAPITAL_LETTER_E_WITH_BREVE = 0x0114;
  char LATIN_SMALL_LETTER_E_WITH_BREVE = 0x0115;
  char LATIN_CAPITAL_LETTER_E_WITH_DOT_ABOVE = 0x0116;
  char LATIN_SMALL_LETTER_E_WITH_DOT_ABOVE = 0x0117;
  char LATIN_CAPITAL_LETTER_E_WITH_OGONEK = 0x0118;
  char LATIN_SMALL_LETTER_E_WITH_OGONEK = 0x0119;
  char LATIN_CAPITAL_LETTER_E_WITH_CARON = 0x011A;
  char LATIN_SMALL_LETTER_E_WITH_CARON = 0x011B;
  char LATIN_CAPITAL_LETTER_G_WITH_CIRCUMFLEX = 0x011C;
  char LATIN_SMALL_LETTER_G_WITH_CIRCUMFLEX = 0x011D;
  char LATIN_CAPITAL_LETTER_G_WITH_BREVE = 0x011E;
  char LATIN_SMALL_LETTER_G_WITH_BREVE = 0x011F;
  char LATIN_CAPITAL_LETTER_G_WITH_DOT_ABOVE = 0x0120;
  char LATIN_SMALL_LETTER_G_WITH_DOT_ABOVE = 0x0121;
  char LATIN_CAPITAL_LETTER_G_WITH_CEDILLA = 0x0122;
  char LATIN_SMALL_LETTER_G_WITH_CEDILLA = 0x0123;
  char LATIN_CAPITAL_LETTER_H_WITH_CIRCUMFLEX = 0x0124;
  char LATIN_SMALL_LETTER_H_WITH_CIRCUMFLEX = 0x0125;
  char LATIN_CAPITAL_LETTER_H_WITH_STROKE = 0x0126;
  char LATIN_SMALL_LETTER_H_WITH_STROKE = 0x0127;
  char LATIN_CAPITAL_LETTER_I_WITH_TILDE = 0x0128;
  char LATIN_SMALL_LETTER_I_WITH_TILDE = 0x0129;
  char LATIN_CAPITAL_LETTER_I_WITH_MACRON = 0x012A;
  char LATIN_SMALL_LETTER_I_WITH_MACRON = 0x012B;
  char LATIN_CAPITAL_LETTER_I_WITH_BREVE = 0x012C;
  char LATIN_SMALL_LETTER_I_WITH_BREVE = 0x012D;
  char LATIN_CAPITAL_LETTER_I_WITH_OGONEK = 0x012E;
  char LATIN_SMALL_LETTER_I_WITH_OGONEK = 0x012F;
  char LATIN_CAPITAL_LETTER_I_WITH_DOT_ABOVE = 0x0130;
  char LATIN_SMALL_LETTER_DOTLESS_I = 0x0131;
  char LATIN_CAPITAL_LIGATURE_IJ = 0x0132;
  char LATIN_SMALL_LIGATURE_IJ  = 0x0133;
  char LATIN_CAPITAL_LETTER_J_WITH_CIRCUMFLEX = 0x0134;
  char LATIN_SMALL_LETTER_J_WITH_CIRCUMFLEX = 0x0135;
  char LATIN_CAPITAL_LETTER_K_WITH_CEDILLA = 0x0136;
  char LATIN_SMALL_LETTER_K_WITH_CEDILLA = 0x0137;
  char LATIN_SMALL_LETTER_KRA = 0x0138;
  char LATIN_CAPITAL_LETTER_L_WITH_ACUTE = 0x0139;
  char LATIN_SMALL_LETTER_L_WITH_ACUTE = 0x013A;
  char LATIN_CAPITAL_LETTER_L_WITH_CEDILLA = 0x013B;
  char LATIN_SMALL_LETTER_L_WITH_CEDILLA = 0x013C;
  char LATIN_CAPITAL_LETTER_L_WITH_CARON = 0x013D;
  char LATIN_SMALL_LETTER_L_WITH_CARON = 0x013E;
  char LATIN_CAPITAL_LETTER_L_WITH_MIDDLE_DOT = 0x013F;
  char LATIN_SMALL_LETTER_L_WITH_MIDDLE_DOT = 0x0140;
  char LATIN_CAPITAL_LETTER_L_WITH_STROKE = 0x0141;
  char LATIN_SMALL_LETTER_L_WITH_STROKE = 0x0142;
  char LATIN_CAPITAL_LETTER_N_WITH_ACUTE = 0x0143;
  char LATIN_SMALL_LETTER_N_WITH_ACUTE = 0x0144;
  char LATIN_CAPITAL_LETTER_N_WITH_CEDILLA = 0x0145;
  char LATIN_SMALL_LETTER_N_WITH_CEDILLA = 0x0146;
  char LATIN_CAPITAL_LETTER_N_WITH_CARON = 0x0147;
  char LATIN_SMALL_LETTER_N_WITH_CARON = 0x0148;
  char LATIN_SMALL_LETTER_N_PRECEDED_BY_APOSTROPHE  = 0x0149;
  char LATIN_CAPITAL_LETTER_ENG = 0x014A;
  char LATIN_SMALL_LETTER_ENG = 0x014B;
  char LATIN_CAPITAL_LETTER_O_WITH_MACRON = 0x014C;
  char LATIN_SMALL_LETTER_O_WITH_MACRON = 0x014D;
  char LATIN_CAPITAL_LETTER_O_WITH_BREVE = 0x014E;
  char LATIN_SMALL_LETTER_O_WITH_BREVE = 0x014F;
  char LATIN_CAPITAL_LETTER_O_WITH_DOUBLE_ACUTE = 0x0150;
  char LATIN_SMALL_LETTER_O_WITH_DOUBLE_ACUTE = 0x0151;
  char LATIN_CAPITAL_LIGATURE_OE = 0x0152;
  char LATIN_SMALL_LIGATURE_OE  = 0x0153;
  char LATIN_CAPITAL_LETTER_R_WITH_ACUTE = 0x0154;
  char LATIN_SMALL_LETTER_R_WITH_ACUTE = 0x0155;
  char LATIN_CAPITAL_LETTER_R_WITH_CEDILLA = 0x0156;
  char LATIN_SMALL_LETTER_R_WITH_CEDILLA = 0x0157;
  char LATIN_CAPITAL_LETTER_R_WITH_CARON = 0x0158;
  char LATIN_SMALL_LETTER_R_WITH_CARON = 0x0159;
  char LATIN_CAPITAL_LETTER_S_WITH_ACUTE = 0x015A;
  char LATIN_SMALL_LETTER_S_WITH_ACUTE = 0x015B;
  char LATIN_CAPITAL_LETTER_S_WITH_CIRCUMFLEX = 0x015C;
  char LATIN_SMALL_LETTER_S_WITH_CIRCUMFLEX = 0x015D;
  char LATIN_CAPITAL_LETTER_S_WITH_CEDILLA = 0x015E;
  char LATIN_SMALL_LETTER_S_WITH_CEDILLA = 0x015F;
  char LATIN_CAPITAL_LETTER_S_WITH_CARON = 0x0160;
  char LATIN_SMALL_LETTER_S_WITH_CARON = 0x0161;
  char LATIN_CAPITAL_LETTER_T_WITH_CEDILLA = 0x0162;
  char LATIN_SMALL_LETTER_T_WITH_CEDILLA = 0x0163;
  char LATIN_CAPITAL_LETTER_T_WITH_CARON = 0x0164;
  char LATIN_SMALL_LETTER_T_WITH_CARON = 0x0165;
  char LATIN_CAPITAL_LETTER_T_WITH_STROKE = 0x0166;
  char LATIN_SMALL_LETTER_T_WITH_STROKE = 0x0167;
  char LATIN_CAPITAL_LETTER_U_WITH_TILDE = 0x0168;
  char LATIN_SMALL_LETTER_U_WITH_TILDE = 0x0169;
  char LATIN_CAPITAL_LETTER_U_WITH_MACRON = 0x016A;
  char LATIN_SMALL_LETTER_U_WITH_MACRON = 0x016B;
  char LATIN_CAPITAL_LETTER_U_WITH_BREVE = 0x016C;
  char LATIN_SMALL_LETTER_U_WITH_BREVE = 0x016D;
  char LATIN_CAPITAL_LETTER_U_WITH_RING_ABOVE = 0x016E;
  char LATIN_SMALL_LETTER_U_WITH_RING_ABOVE = 0x016F;
  char LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_ACUTE = 0x0170;
  char LATIN_SMALL_LETTER_U_WITH_DOUBLE_ACUTE = 0x0171;
  char LATIN_CAPITAL_LETTER_U_WITH_OGONEK = 0x0172;
  char LATIN_SMALL_LETTER_U_WITH_OGONEK = 0x0173;
  char LATIN_CAPITAL_LETTER_W_WITH_CIRCUMFLEX = 0x0174;
  char LATIN_SMALL_LETTER_W_WITH_CIRCUMFLEX = 0x0175;
  char LATIN_CAPITAL_LETTER_Y_WITH_CIRCUMFLEX = 0x0176;
  char LATIN_SMALL_LETTER_Y_WITH_CIRCUMFLEX = 0x0177;
  char LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS = 0x0178;
  char LATIN_CAPITAL_LETTER_Z_WITH_ACUTE = 0x0179;
  char LATIN_SMALL_LETTER_Z_WITH_ACUTE = 0x017A;
  char LATIN_CAPITAL_LETTER_Z_WITH_DOT_ABOVE = 0x017B;
  char LATIN_SMALL_LETTER_Z_WITH_DOT_ABOVE = 0x017C;
  char LATIN_CAPITAL_LETTER_Z_WITH_CARON = 0x017D;
  char LATIN_SMALL_LETTER_Z_WITH_CARON = 0x017E;
  char LATIN_SMALL_LETTER_LONG_S = 0x017F;

  char LATIN_SMALL_LETTER_B_WITH_STROKE = 0x0180;
  char LATIN_CAPITAL_LETTER_B_WITH_HOOK = 0x0181;
  char LATIN_CAPITAL_LETTER_B_WITH_TOPBAR = 0x0182;
  char LATIN_SMALL_LETTER_B_WITH_TOPBAR = 0x0183;
  char LATIN_CAPITAL_LETTER_TONE_SIX = 0x0184;
  char LATIN_SMALL_LETTER_TONE_SIX = 0x0185;
  char LATIN_CAPITAL_LETTER_OPEN_O = 0x0186;
  char LATIN_CAPITAL_LETTER_C_WITH_HOOK = 0x0187;
  char LATIN_SMALL_LETTER_C_WITH_HOOK = 0x0188;
  char LATIN_CAPITAL_LETTER_AFRICAN_D = 0x0189;
  char LATIN_CAPITAL_LETTER_D_WITH_HOOK = 0x018A;
  char LATIN_CAPITAL_LETTER_D_WITH_TOPBAR = 0x018B;
  char LATIN_SMALL_LETTER_D_WITH_TOPBAR = 0x018C;
  char LATIN_SMALL_LETTER_TURNED_DELTA = 0x018D;
  char LATIN_CAPITAL_LETTER_REVERSED_E = 0x018E;
  char LATIN_CAPITAL_LETTER_SCHWA = 0x018F;
  char LATIN_CAPITAL_LETTER_OPEN_E = 0x0190;
  char LATIN_CAPITAL_LETTER_F_WITH_HOOK = 0x0191;
  char LATIN_SMALL_LETTER_F_WITH_HOOK = 0x0192;
  char LATIN_CAPITAL_LETTER_G_WITH_HOOK = 0x0193;
  char LATIN_CAPITAL_LETTER_GAMMA = 0x0194;
  char LATIN_SMALL_LETTER_HV  = 0x0195;
  char LATIN_CAPITAL_LETTER_IOTA = 0x0196;
  char LATIN_CAPITAL_LETTER_I_WITH_STROKE = 0x0197;
  char LATIN_CAPITAL_LETTER_K_WITH_HOOK = 0x0198;
  char LATIN_SMALL_LETTER_K_WITH_HOOK = 0x0199;
  char LATIN_SMALL_LETTER_L_WITH_BAR = 0x019A;
  char LATIN_SMALL_LETTER_LAMBDA_WITH_STROKE = 0x019B;
  char LATIN_CAPITAL_LETTER_TURNED_M = 0x019C;
  char LATIN_CAPITAL_LETTER_N_WITH_LEFT_HOOK = 0x019D;
  char LATIN_SMALL_LETTER_N_WITH_LONG_RIGHT_LEG = 0x019E;
  char LATIN_CAPITAL_LETTER_O_WITH_MIDDLE_TILDE = 0x019F;
  char LATIN_CAPITAL_LETTER_O_WITH_HORN = 0x01A0;
  char LATIN_SMALL_LETTER_O_WITH_HORN = 0x01A1;
  char LATIN_CAPITAL_LETTER_OI  = 0x01A2;
  char LATIN_SMALL_LETTER_OI  = 0x01A3;
  char LATIN_CAPITAL_LETTER_P_WITH_HOOK = 0x01A4;
  char LATIN_SMALL_LETTER_P_WITH_HOOK = 0x01A5;
  char LATIN_LETTER_YR = 0x01A6;
  char LATIN_CAPITAL_LETTER_TONE_TWO = 0x01A7;
  char LATIN_SMALL_LETTER_TONE_TWO = 0x01A8;
  char LATIN_CAPITAL_LETTER_ESH = 0x01A9;
  char LATIN_LETTER_REVERSED_ESH_LOOP = 0x01AA;
  char LATIN_SMALL_LETTER_T_WITH_PALATAL_HOOK = 0x01AB;
  char LATIN_CAPITAL_LETTER_T_WITH_HOOK = 0x01AC;
  char LATIN_SMALL_LETTER_T_WITH_HOOK = 0x01AD;
  char LATIN_CAPITAL_LETTER_T_WITH_RETROFLEX_HOOK = 0x01AE;
  char LATIN_CAPITAL_LETTER_U_WITH_HORN = 0x01AF;
  char LATIN_SMALL_LETTER_U_WITH_HORN = 0x01B0;
  char LATIN_CAPITAL_LETTER_UPSILON = 0x01B1;
  char LATIN_CAPITAL_LETTER_V_WITH_HOOK = 0x01B2;
  char LATIN_CAPITAL_LETTER_Y_WITH_HOOK = 0x01B3;
  char LATIN_SMALL_LETTER_Y_WITH_HOOK = 0x01B4;
  char LATIN_CAPITAL_LETTER_Z_WITH_STROKE = 0x01B5;
  char LATIN_SMALL_LETTER_Z_WITH_STROKE = 0x01B6;
  char LATIN_CAPITAL_LETTER_EZH = 0x01B7;
  char LATIN_CAPITAL_LETTER_EZH_REVERSED = 0x01B8;
  char LATIN_SMALL_LETTER_EZH_REVERSED = 0x01B9;
  char LATIN_SMALL_LETTER_EZH_WITH_TAIL = 0x01BA;
  char LATIN_LETTER_TWO_WITH_STROKE = 0x01BB;
  char LATIN_CAPITAL_LETTER_TONE_FIVE = 0x01BC;
  char LATIN_SMALL_LETTER_TONE_FIVE = 0x01BD;
  char LATIN_LETTER_INVERTED_GLOTTAL_STOP_WITH_STROKE = 0x01BE;
  char LATIN_LETTER_WYNN  = 0x01BF;
  char LATIN_LETTER_DENTAL_CLICK = 0x01C0;
  char LATIN_LETTER_LATERAL_CLICK = 0x01C1;
  char LATIN_LETTER_ALVEOLAR_CLICK = 0x01C2;
  char LATIN_LETTER_RETROFLEX_CLICK = 0x01C3;
  char LATIN_CAPITAL_LETTER_DZ_WITH_CARON = 0x01C4;
  char LATIN_CAPITAL_LETTER_D_WITH_SMALL_LETTER_Z_WITH_CARON = 0x01C5;
  char LATIN_SMALL_LETTER_DZ_WITH_CARON = 0x01C6;
  char LATIN_CAPITAL_LETTER_LJ  = 0x01C7;
  char LATIN_CAPITAL_LETTER_L_WITH_SMALL_LETTER_J = 0x01C8;
  char LATIN_SMALL_LETTER_LJ  = 0x01C9;
  char LATIN_CAPITAL_LETTER_NJ  = 0x01CA;
  char LATIN_CAPITAL_LETTER_N_WITH_SMALL_LETTER_J = 0x01CB;
  char LATIN_SMALL_LETTER_NJ  = 0x01CC;
  char LATIN_CAPITAL_LETTER_A_WITH_CARON = 0x01CD;
  char LATIN_SMALL_LETTER_A_WITH_CARON = 0x01CE;
  char LATIN_CAPITAL_LETTER_I_WITH_CARON = 0x01CF;
  char LATIN_SMALL_LETTER_I_WITH_CARON = 0x01D0;
  char LATIN_CAPITAL_LETTER_O_WITH_CARON = 0x01D1;
  char LATIN_SMALL_LETTER_O_WITH_CARON = 0x01D2;
  char LATIN_CAPITAL_LETTER_U_WITH_CARON = 0x01D3;
  char LATIN_SMALL_LETTER_U_WITH_CARON = 0x01D4;
  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_MACRON = 0x01D5;
  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_MACRON = 0x01D6;
  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_ACUTE  = 0x01D7;
  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_ACUTE  = 0x01D8;
  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_CARON  = 0x01D9;
  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_CARON  = 0x01DA;
  char LATIN_CAPITAL_LETTER_U_WITH_DIAERESIS_AND_GRAVE  = 0x01DB;
  char LATIN_SMALL_LETTER_U_WITH_DIAERESIS_AND_GRAVE  = 0x01DC;
  char LATIN_SMALL_LETTER_TURNED_E = 0x01DD;
  char LATIN_CAPITAL_LETTER_A_WITH_DIAERESIS_AND_MACRON = 0x01DE;
  char LATIN_SMALL_LETTER_A_WITH_DIAERESIS_AND_MACRON = 0x01DF;
  char LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON = 0x01E0;
  char LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE_AND_MACRON = 0x01E1;
  char LATIN_CAPITAL_LETTER_AE_WITH_MACRON = 0x01E2;
  char LATIN_SMALL_LETTER_AE_WITH_MACRON = 0x01E3;
  char LATIN_CAPITAL_LETTER_G_WITH_STROKE = 0x01E4;
  char LATIN_SMALL_LETTER_G_WITH_STROKE = 0x01E5;
  char LATIN_CAPITAL_LETTER_G_WITH_CARON = 0x01E6;
  char LATIN_SMALL_LETTER_G_WITH_CARON = 0x01E7;
  char LATIN_CAPITAL_LETTER_K_WITH_CARON = 0x01E8;
  char LATIN_SMALL_LETTER_K_WITH_CARON = 0x01E9;
  char LATIN_CAPITAL_LETTER_O_WITH_OGONEK = 0x01EA;
  char LATIN_SMALL_LETTER_O_WITH_OGONEK = 0x01EB;
  char LATIN_CAPITAL_LETTER_O_WITH_OGONEK_AND_MACRON  = 0x01EC;
  char LATIN_SMALL_LETTER_O_WITH_OGONEK_AND_MACRON  = 0x01ED;
  char LATIN_CAPITAL_LETTER_EZH_WITH_CARON = 0x01EE;
  char LATIN_SMALL_LETTER_EZH_WITH_CARON = 0x01EF;
  char LATIN_SMALL_LETTER_J_WITH_CARON = 0x01F0;
  char LATIN_CAPITAL_LETTER_DZ  = 0x01F1;
  char LATIN_CAPITAL_LETTER_D_WITH_SMALL_LETTER_Z = 0x01F2;
  char LATIN_SMALL_LETTER_DZ  = 0x01F3;
  char LATIN_CAPITAL_LETTER_G_WITH_ACUTE = 0x01F4;
  char LATIN_SMALL_LETTER_G_WITH_ACUTE = 0x01F5;
  char LATIN_CAPITAL_LETTER_HWAIR = 0x01F6;
  char LATIN_CAPITAL_LETTER_WYNN = 0x01F7;
  char LATIN_CAPITAL_LETTER_N_WITH_GRAVE = 0x01F8;
  char LATIN_SMALL_LETTER_N_WITH_GRAVE = 0x01F9;
  char LATIN_CAPITAL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE = 0x01FA;
  char LATIN_SMALL_LETTER_A_WITH_RING_ABOVE_AND_ACUTE = 0x01FB;
  char LATIN_CAPITAL_LETTER_AE_WITH_ACUTE = 0x01FC;
  char LATIN_SMALL_LETTER_AE_WITH_ACUTE = 0x01FD;
  char LATIN_CAPITAL_LETTER_O_WITH_STROKE_AND_ACUTE = 0x01FE;
  char LATIN_SMALL_LETTER_O_WITH_STROKE_AND_ACUTE = 0x01FF;
  char LATIN_CAPITAL_LETTER_A_WITH_DOUBLE_GRAVE = 0x0200;
  char LATIN_SMALL_LETTER_A_WITH_DOUBLE_GRAVE = 0x0201;
  char LATIN_CAPITAL_LETTER_A_WITH_INVERTED_BREVE = 0x0202;
  char LATIN_SMALL_LETTER_A_WITH_INVERTED_BREVE = 0x0203;
  char LATIN_CAPITAL_LETTER_E_WITH_DOUBLE_GRAVE = 0x0204;
  char LATIN_SMALL_LETTER_E_WITH_DOUBLE_GRAVE = 0x0205;
  char LATIN_CAPITAL_LETTER_E_WITH_INVERTED_BREVE = 0x0206;
  char LATIN_SMALL_LETTER_E_WITH_INVERTED_BREVE = 0x0207;
  char LATIN_CAPITAL_LETTER_I_WITH_DOUBLE_GRAVE = 0x0208;
  char LATIN_SMALL_LETTER_I_WITH_DOUBLE_GRAVE = 0x0209;
  char LATIN_CAPITAL_LETTER_I_WITH_INVERTED_BREVE = 0x020A;
  char LATIN_SMALL_LETTER_I_WITH_INVERTED_BREVE = 0x020B;
  char LATIN_CAPITAL_LETTER_O_WITH_DOUBLE_GRAVE = 0x020C;
  char LATIN_SMALL_LETTER_O_WITH_DOUBLE_GRAVE = 0x020D;
  char LATIN_CAPITAL_LETTER_O_WITH_INVERTED_BREVE = 0x020E;
  char LATIN_SMALL_LETTER_O_WITH_INVERTED_BREVE = 0x020F;
  char LATIN_CAPITAL_LETTER_R_WITH_DOUBLE_GRAVE = 0x0210;
  char LATIN_SMALL_LETTER_R_WITH_DOUBLE_GRAVE = 0x0211;
  char LATIN_CAPITAL_LETTER_R_WITH_INVERTED_BREVE = 0x0212;
  char LATIN_SMALL_LETTER_R_WITH_INVERTED_BREVE = 0x0213;
  char LATIN_CAPITAL_LETTER_U_WITH_DOUBLE_GRAVE = 0x0214;
  char LATIN_SMALL_LETTER_U_WITH_DOUBLE_GRAVE = 0x0215;
  char LATIN_CAPITAL_LETTER_U_WITH_INVERTED_BREVE = 0x0216;
  char LATIN_SMALL_LETTER_U_WITH_INVERTED_BREVE = 0x0217;
  char LATIN_CAPITAL_LETTER_S_WITH_COMMA_BELOW = 0x0218;
  char LATIN_SMALL_LETTER_S_WITH_COMMA_BELOW = 0x0219;
  char LATIN_CAPITAL_LETTER_T_WITH_COMMA_BELOW = 0x021A;
  char LATIN_SMALL_LETTER_T_WITH_COMMA_BELOW = 0x021B;
  char LATIN_CAPITAL_LETTER_YOGH = 0x021C;
  char LATIN_SMALL_LETTER_YOGH  = 0x021D;
  char LATIN_CAPITAL_LETTER_H_WITH_CARON = 0x021E;
  char LATIN_SMALL_LETTER_H_WITH_CARON = 0x021F;
  char LATIN_CAPITAL_LETTER_OU  = 0x0222;
  char LATIN_SMALL_LETTER_OU  = 0x0223;
  char LATIN_CAPITAL_LETTER_Z_WITH_HOOK = 0x0224;
  char LATIN_SMALL_LETTER_Z_WITH_HOOK = 0x0225;
  char LATIN_CAPITAL_LETTER_A_WITH_DOT_ABOVE = 0x0226;
  char LATIN_SMALL_LETTER_A_WITH_DOT_ABOVE = 0x0227;
  char LATIN_CAPITAL_LETTER_E_WITH_CEDILLA = 0x0228;
  char LATIN_SMALL_LETTER_E_WITH_CEDILLA = 0x0229;
  char LATIN_CAPITAL_LETTER_O_WITH_DIAERESIS_AND_MACRON = 0x022A;
  char LATIN_SMALL_LETTER_O_WITH_DIAERESIS_AND_MACRON = 0x022B;
  char LATIN_CAPITAL_LETTER_O_WITH_TILDE_AND_MACRON = 0x022C;
  char LATIN_SMALL_LETTER_O_WITH_TILDE_AND_MACRON = 0x022D;
  char LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE = 0x022E;
  char LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE = 0x022F;
  char LATIN_CAPITAL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON = 0x0230;
  char LATIN_SMALL_LETTER_O_WITH_DOT_ABOVE_AND_MACRON = 0x0231;
  char LATIN_CAPITAL_LETTER_Y_WITH_MACRON = 0x0232;
  char LATIN_SMALL_LETTER_Y_WITH_MACRON = 0x0233;
  ;
  /* IPA extensions */;
  char LATIN_SMALL_LETTER_TURNED_A = 0x0250;
  char LATIN_SMALL_LETTER_ALPHA = 0x0251;
  char LATIN_SMALL_LETTER_TURNED_ALPHA = 0x0252;
  char LATIN_SMALL_LETTER_B_WITH_HOOK = 0x0253;
  char LATIN_SMALL_LETTER_OPEN_O = 0x0254;
  char LATIN_SMALL_LETTER_C_WITH_CURL = 0x0255;
  char LATIN_SMALL_LETTER_D_WITH_TAIL = 0x0256;
  char LATIN_SMALL_LETTER_D_WITH_HOOK = 0x0257;
  char LATIN_SMALL_LETTER_REVERSED_E = 0x0258;
  char LATIN_SMALL_LETTER_SCHWA = 0x0259;
  char LATIN_SMALL_LETTER_SCHWA_WITH_HOOK = 0x025A;
  char LATIN_SMALL_LETTER_OPEN_E = 0x025B;
  char LATIN_SMALL_LETTER_REVERSED_OPEN_E = 0x025C;
  char LATIN_SMALL_LETTER_REVERSED_OPEN_E_WITH_HOOK = 0x025D;
  char LATIN_SMALL_LETTER_CLOSED_REVERSED_OPEN_E  = 0x025E;
  char LATIN_SMALL_LETTER_DOTLESS_J_WITH_STROKE = 0x025F;
  char LATIN_SMALL_LETTER_G_WITH_HOOK = 0x0260;
  char LATIN_SMALL_LETTER_SCRIPT_G = 0x0261;
  char LATIN_LETTER_SMALL_CAPITAL_G = 0x0262;
  char LATIN_SMALL_LETTER_GAMMA = 0x0263;
  char LATIN_SMALL_LETTER_RAMS_HORN = 0x0264;
  char LATIN_SMALL_LETTER_TURNED_H = 0x0265;
  char LATIN_SMALL_LETTER_H_WITH_HOOK = 0x0266;
  char LATIN_SMALL_LETTER_HENG_WITH_HOOK = 0x0267;
  char LATIN_SMALL_LETTER_I_WITH_STROKE = 0x0268;
  char LATIN_SMALL_LETTER_IOTA  = 0x0269;
  char LATIN_LETTER_SMALL_CAPITAL_I = 0x026A;
  char LATIN_SMALL_LETTER_L_WITH_MIDDLE_TILDE = 0x026B;
  char LATIN_SMALL_LETTER_L_WITH_BELT = 0x026C;
  char LATIN_SMALL_LETTER_L_WITH_RETROFLEX_HOOK = 0x026D;
  char LATIN_SMALL_LETTER_LEZH  = 0x026E;
  char LATIN_SMALL_LETTER_TURNED_M = 0x026F;
  char LATIN_SMALL_LETTER_TURNED_M_WITH_LONG_LEG  = 0x0270;
  char LATIN_SMALL_LETTER_M_WITH_HOOK = 0x0271;
  char LATIN_SMALL_LETTER_N_WITH_LEFT_HOOK = 0x0272;
  char LATIN_SMALL_LETTER_N_WITH_RETROFLEX_HOOK = 0x0273;
  char LATIN_LETTER_SMALL_CAPITAL_N = 0x0274;
  char LATIN_SMALL_LETTER_BARRED_O = 0x0275;
  char LATIN_LETTER_SMALL_CAPITAL_OE = 0x0276;
  char LATIN_SMALL_LETTER_CLOSED_OMEGA = 0x0277;
  char LATIN_SMALL_LETTER_PHI = 0x0278;
  char LATIN_SMALL_LETTER_TURNED_R = 0x0279;
  char LATIN_SMALL_LETTER_TURNED_R_WITH_LONG_LEG  = 0x027A;
  char LATIN_SMALL_LETTER_TURNED_R_WITH_HOOK = 0x027B;
  char LATIN_SMALL_LETTER_R_WITH_LONG_LEG = 0x027C;
  char LATIN_SMALL_LETTER_R_WITH_TAIL = 0x027D;
  char LATIN_SMALL_LETTER_R_WITH_FISHHOOK = 0x027E;
  char LATIN_SMALL_LETTER_REVERSED_R_WITH_FISHHOOK  = 0x027F;
  char LATIN_LETTER_SMALL_CAPITAL_R = 0x0280;
  char LATIN_LETTER_SMALL_CAPITAL_INVERTED_R = 0x0281;
  char LATIN_SMALL_LETTER_S_WITH_HOOK = 0x0282;
  char LATIN_SMALL_LETTER_ESH = 0x0283;
  char LATIN_SMALL_LETTER_DOTLESS_J_WITH_STROKE_AND_HOOK = 0x0284;
  char LATIN_SMALL_LETTER_SQUAT_REVERSED_ESH = 0x0285;
  char LATIN_SMALL_LETTER_ESH_WITH_CURL = 0x0286;
  char LATIN_SMALL_LETTER_TURNED_T = 0x0287;
  char LATIN_SMALL_LETTER_T_WITH_RETROFLEX_HOOK = 0x0288;
  char LATIN_SMALL_LETTER_U_BAR = 0x0289;
  char LATIN_SMALL_LETTER_UPSILON = 0x028A;
  char LATIN_SMALL_LETTER_V_WITH_HOOK = 0x028B;
  char LATIN_SMALL_LETTER_TURNED_V = 0x028C;
  char LATIN_SMALL_LETTER_TURNED_W = 0x028D;
  char LATIN_SMALL_LETTER_TURNED_Y = 0x028E;
  char LATIN_LETTER_SMALL_CAPITAL_Y = 0x028F;
  char LATIN_SMALL_LETTER_Z_WITH_RETROFLEX_HOOK = 0x0290;
  char LATIN_SMALL_LETTER_Z_WITH_CURL = 0x0291;
  char LATIN_SMALL_LETTER_EZH = 0x0292;
  char LATIN_SMALL_LETTER_EZH_WITH_CURL = 0x0293;
  char LATIN_LETTER_GLOTTAL_STOP = 0x0294;
  char LATIN_LETTER_PHARYNGEAL_VOICED_FRICATIVE = 0x0295;
  char LATIN_LETTER_INVERTED_GLOTTAL_STOP = 0x0296;
  char LATIN_LETTER_STRETCHED_C = 0x0297;
  char LATIN_LETTER_BILABIAL_CLICK = 0x0298;
  char LATIN_LETTER_SMALL_CAPITAL_B = 0x0299;
  char LATIN_SMALL_LETTER_CLOSED_OPEN_E = 0x029A;
  char LATIN_LETTER_SMALL_CAPITAL_G_WITH_HOOK = 0x029B;
  char LATIN_LETTER_SMALL_CAPITAL_H = 0x029C;
  char LATIN_SMALL_LETTER_J_WITH_CROSSED_TAIL = 0x029D;
  char LATIN_SMALL_LETTER_TURNED_K = 0x029E;
  char LATIN_LETTER_SMALL_CAPITAL_L = 0x029F;
  
  char LATIN_SMALL_LETTER_Q_WITH_HOOK = 0x02A0;
  char LATIN_LETTER_GLOTTAL_STOP_WITH_STROKE = 0x02A1;
  char LATIN_LETTER_REVERSED_GLOTTAL_STOP_WITH_STROKE = 0x02A2;
  char LATIN_SMALL_LETTER_DZ_DIGRAPH = 0x02A3;
  char LATIN_SMALL_LETTER_DEZH_DIGRAPH = 0x02A4;
  char LATIN_SMALL_LETTER_DZ_DIGRAPH_WITH_CURL = 0x02A5;
  char LATIN_SMALL_LETTER_TS_DIGRAPH = 0x02A6;
  char LATIN_SMALL_LETTER_TESH_DIGRAPH = 0x02A7;
  char LATIN_SMALL_LETTER_TC_DIGRAPH_WITH_CURL = 0x02A8;
  char LATIN_SMALL_LETTER_FENG_DIGRAPH = 0x02A9;
  char LATIN_SMALL_LETTER_LS_DIGRAPH = 0x02AA;
  char LATIN_SMALL_LETTER_LZ_DIGRAPH = 0x02AB;
  char LATIN_LETTER_BILABIAL_PERCUSSIVE = 0x02AC;
  char LATIN_LETTER_BIDENTAL_PERCUSSIVE = 0x02AD;

  
  /* combining diacritical marks */

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for { link
   * DiacriticalMark#GRAVE}.
   */
  char COMBINING_GRAVE_ACCENT = 768;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for { link
   * {@link DiacriticalMark#ACUTE} .
   */
  char COMBINING_ACUTE_ACCENT = 769;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#CIRCUMFLEX} .
   */
  char COMBINING_CIRCUMFLEX_ACCENT = 770;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#TILDE} .
   */
  char COMBINING_TILDE = 771;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#MACRON}.
   */
  char COMBINING_MACRON = 772;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#OVERLINE}.
   */
  char COMBINING_OVERLINE = 773;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#BREVE}.
   */
  char COMBINING_BREVE = 774;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#DOT_ABOVE}.
   */
  char COMBINING_DOT_ABOVE = 0x0307;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#DIAERESIS}.
   */
  char COMBINING_DIAERESIS = 0x0308;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#HOOK_ABOVE}.
   */
  char COMBINING_HOOK_ABOVE = 0x0309;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#RING_ABOVE}.
   */
  char COMBINING_RING_ABOVE = 0x030A;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#DOUBLE_ACUTE}.
   */
  char COMBINING_DOUBLE_ACUTE_ACCENT = 0x030B;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#CARON}.
   */
  char COMBINING_CARON = 0x030C;

  char COMBINING_VERTICAL_LINE_ABOVE = 0x030D;

  char COMBINING_DOUBLE_VERTICAL_LINE_ABOVE = 0x030E;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#DOUBLE_GRAVE}.
   */
  char COMBINING_DOUBLE_GRAVE_ACCENT = 0x030F;

  char COMBINING_CANDRABINDU = 0x0310;

  char COMBINING_INVERTED_BREVE = 0x0311;

  char COMBINING_TURNED_COMMA_ABOVE = 0x0312;

  char COMBINING_COMMA_ABOVE = 0x0313;

  char COMBINING_REVERSED_COMMA_ABOVE = 0x0314;

  char COMBINING_COMMA_ABOVE_RIGHT = 0x0315;

  char COMBINING_GRAVE_ACCENT_BELOW = 0x0316;

  char COMBINING_ACUTE_ACCENT_BELOW = 0x0317;

  char COMBINING_LEFT_TACK_BELOW = 0x0318;

  char COMBINING_RIGHT_TACK_BELOW = 0x0319;

  char COMBINING_LEFT_ANGLE_ABOVE = 0x031A;

  /**
   * {@link DiacriticalMark#getCombiningCharacter() combining-char} for
   * {@link DiacriticalMark#HORN_ABOVE}.
   */
  char COMBINING_HORN = 0x031B;

  char COMBINING_LEFT_HALF_RING_BELOW = 0x031C;

  char COMBINING_UP_TACK_BELOW = 0x031D;

  char COMBINING_DOWN_TACK_BELOW = 0x031E;

  char COMBINING_PLUS_SIGN_BELOW = 0x031F;

  char COMBINING_MINUS_SIGN_BELOW = 0x0320;

  char COMBINING_PALATALIZED_HOOK_BELOW = 0x0321;

  char COMBINING_RETROFLEX_HOOK_BELOW = 0x0322;

  char COMBINING_DOT_BELOW = 0x0323;

  char COMBINING_DIAERESIS_BELOW = 0x0324;

  char COMBINING_RING_BELOW = 0x0325;

  char COMBINING_COMMA_BELOW = 0x0326;

  char COMBINING_CEDILLA = 0x0327;

  char COMBINING_OGONEK = 0x0328;

  char COMBINING_VERTICAL_LINE_BELOW = 0x0329;

  char COMBINING_BRIDGE_BELOW = 0x032A;

  char COMBINING_INVERTED_DOUBLE_ARCH_BELOW = 0x032B;

  char COMBINING_CARON_BELOW = 0x032C;

  char COMBINING_CIRCUMFLEX_ACCENT_BELOW = 0x032D;

  char COMBINING_BREVE_BELOW = 0x032E;

  char COMBINING_INVERTED_BREVE_BELOW = 0x032F;

  char COMBINING_TILDE_BELOW = 0x0330;

  char COMBINING_MACRON_BELOW = 0x0331;

  char COMBINING_LOW_LINE = 0x0332;

  char COMBINING_DOUBLE_LOW_LINE = 0x0333;

  char COMBINING_TILDE_OVERLAY = 0x0334;

  char COMBINING_SHORT_STROKE_OVERLAY = 0x0335;

  char COMBINING_LONG_STROKE_OVERLAY = 0x0336;

  char COMBINING_SHORT_SOLIDUS_OVERLAY = 0x0337;

  char COMBINING_LONG_SOLIDUS_OVERLAY = 0x0338;

  char COMBINING_RIGHT_HALF_RING_BELOW = 0x0339;

  char COMBINING_INVERTED_BRIDGE_BELOW = 0x033A;

  char COMBINING_SQUARE_BELOW = 0x033B;

  char COMBINING_SEAGULL_BELOW = 0x033C;

  char COMBINING_X_ABOVE = 0x033D;

  char COMBINING_VERTICAL_TILDE = 0x033E;

  char COMBINING_DOUBLE_OVERLINE = 0x033F;

  char COMBINING_GRAVE_TONE_MARK = 0x0340;

  char COMBINING_ACUTE_TONE_MARK = 0x0341;

  char COMBINING_GREEK_PERISPOMENI = 0x0342;

  char COMBINING_GREEK_KORONIS = 0x0343;

  char COMBINING_GREEK_DIALYTIKA_TONOS = 0x0344;

  char COMBINING_GREEK_YPOGEGRAMMENI = 0x0345;

  char COMBINING_BRIDGE_ABOVE = 0x0346;

  char COMBINING_EQUALS_SIGN_BELOW = 0x0347;

  char COMBINING_DOUBLE_VERTICAL_LINE_BELOW = 0x0348;

  char COMBINING_LEFT_ANGLE_BELOW = 0x0349;

  char COMBINING_NOT_TILDE_ABOVE = 0x034A;

  char COMBINING_HOMOTHETIC_ABOVE = 0x034B;

  char COMBINING_ALMOST_EQUAL_TO_ABOVE = 0x034C;

  char COMBINING_LEFT_RIGHT_ARROW_BELOW = 0x034D;

  char COMBINING_UPWARDS_ARROW_BELOW = 0x034E;

  char COMBINING_DOUBLE_TILDE = 0x0360;

  char COMBINING_DOUBLE_INVERTED_BREVE = 0x0361;

  char COMBINING_DOUBLE_RIGHTWARDS_ARROW_BELOW = 0x0362;

  /** The unicode minus-sign. Similar to ASCII hyphen-minus ('-'). */
  char MINUS_SIGN = 8722;

  /** The hyphen. Similar to ASCII hyphen-minus ('-'). */
  char HYPEN = 8208;

  /** The non-breaking hyphen. */
  char NON_BREAKING_HYPHEN = 8209;

  /** The figure dash (for digits). */
  char FIGURE_DASH = 8210;

  /** The en dash - a shorter dash. */
  char EN_DASH = 8211;

  /** The em dash - a longer dash. */
  char EM_DASH = 8212;

  /** A horizontal bar. */
  char HORIZONTAL_BAR = 8213;

  char LEFT_SINGLE_QUOTATION_MARK = 8216;

  char RIGHT_SINGLE_QUOTATION_MARK = 8217;

  char SINGLE_LOW_9_QUOTATION_MARK = 8218;

  char SINGLE_HIGH_REVERSED_9_QUOTATION_MARK = 8219;

  char LEFT_DOUBLE_QUOTATION_MARK = 8220;

  char RIGHT_DOUBLE_QUOTATION_MARK = 8221;

  char DOUBLE_LOW_9_QUOTATION_MARK = 8222;

  char DOUBLE_HIGH_REVERSED_9_QUOTATION_MARK = 8223;

  char DAGGER = 8224;

  char DOUBLE_DAGGER = 8225;

  char BULLET = 8226;

  char TRIANGULAR_BULLET = 8227;

  char HORIZONTAL_ELLIPSIS = 8230;

  char HYPHENATION_POINT = 8231;

  char PER_MILLE_SIGN = 8240;

  char SINGLE_LEFT_POINTING_ANGLE_QUOTATION_MARK = 8249;

  char SINGLE_RIGHT_POINTING_ANGLE_QUOTATION_MARK = 8250;

  /** The Sign for Currency EUR. */
  char EURO_CURRENCY_SIGN = 8364;
  
  // TODO
  char DOT_BELOW = '\0';
  char HOOK_ABOVE = '\0';
  char DOUBLE_GRAVE_ACCENT = '\0';
  char HORN = '\0';

  /**
   * This method determines an ASCII-representation for the given character if
   * available.
   * 
   * @param character is the special character.
   * @return a sequence of ASCII-characters that represent the given character
   *         or <code>null</code> if the character is already ASCII or there is
   *         no ASCII-representation available.
   */
  String normalize2Ascii(char character);

}
