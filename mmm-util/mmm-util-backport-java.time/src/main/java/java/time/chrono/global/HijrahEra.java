/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
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
package java.time.chrono.global;

import static java.time.calendrical.ChronoField.ERA;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.calendrical.ChronoField;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Era;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * An era in the Hijrah calendar system.
 * <p>
 * The Hijrah calendar system has two eras.
 * The date {@code 0001-01-01 (Hijrah)} is {@code 622-06-19 (ISO)}.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code HijrahEra}.
 * Use {@code getValue()} instead.</b>
 *
 * <h4>Implementation notes</h4>
 * This is an immutable and thread-safe enum.
 */
enum HijrahEra implements Era<HijrahChrono> {

    /**
     * The singleton instance for the era before the current one, 'Before Anno Hegirae',
     * which has the value 0.
     */
    BEFORE_AH,
    /**
     * The singleton instance for the current era, 'Anno Hegirae', which has the value 1.
     */
    AH;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code HijrahEra} from a value.
     * <p>
     * The current era (from ISO date 622-06-19 onwards) has the value 1
     * The previous era has the value 0.
     *
     * @param hijrahEra  the era to represent, from 0 to 1
     * @return the HijrahEra singleton, never null
     * @throws DateTimeException if the era is invalid
     */
    public static HijrahEra of(int hijrahEra) {
        switch (hijrahEra) {
            case 0:
                return BEFORE_AH;
            case 1:
                return AH;
            default:
                throw new DateTimeException("HijrahEra not valid");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the era numeric value.
     * <p>
     * The current era (from ISO date 622-06-19 onwards) has the value 1.
     * The previous era has the value 0.
     *
     * @return the era value, from 0 (BEFORE_AH) to 1 (AH)
     */
    @Override
    public int getValue() {
        return ordinal();
    }

    @Override
    public HijrahChrono getChrono() {
        return HijrahChrono.INSTANCE;
    }

    // JDK8 default methods:
    //-----------------------------------------------------------------------
    @Override
    public ChronoLocalDate<HijrahChrono> date(int year, int month, int day) {
        return getChrono().date(this, year, month, day);
    }

    @Override
    public ChronoLocalDate<HijrahChrono> dateYearDay(int year, int dayOfYear) {
        return getChrono().dateYearDay(this, year, dayOfYear);
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean isSupported(DateTimeField field) {
        if (field instanceof ChronoField) {
            return field == ERA;
        }
        return field != null && field.doIsSupported(this);
    }

    @Override
    public DateTimeValueRange range(DateTimeField field) {
        if (field == ERA) {
            return field.range();
        } else if (field instanceof ChronoField) {
            throw new DateTimeException("Unsupported field: " + field.getName());
        }
        return field.doRange(this);
    }

    @Override
    public int get(DateTimeField field) {
        if (field == ERA) {
            return getValue();
        }
        return range(field).checkValidIntValue(getLong(field), field);
    }

    @Override
    public long getLong(DateTimeField field) {
        if (field == ERA) {
            return getValue();
        } else if (field instanceof ChronoField) {
            throw new DateTimeException("Unsupported field: " + field.getName());
        }
        return field.doGet(this);
    }

    //-------------------------------------------------------------------------
    @Override
    public DateTime doWithAdjustment(DateTime dateTime) {
        return dateTime.with(ERA, getValue());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> R query(Query<R> query) {
        if (query == Query.CHRONO) {
            return (R) getChrono();
        }
        return query.doQuery(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public String getText(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder().appendText(ERA, style).toFormatter(locale).print(this);
    }

    /**
     * Returns the proleptic year from this era and year of era.
     *
     * @param yearOfEra the year of Era
     * @return the computed prolepticYear
     */
    int prolepticYear(int yearOfEra) {
        return (this == HijrahEra.AH ? yearOfEra : 1 - yearOfEra);
    }

    //-----------------------------------------------------------------------
    private Object writeReplace() {
        return new Ser(Ser.HIJRAH_ERA_TYPE, this);
    }

    void writeExternal(DataOutput out) throws IOException {
        out.writeByte(this.getValue());
    }

    static HijrahEra readExternal(DataInput in) throws IOException {
        byte eraValue = in.readByte();
        return HijrahEra.of(eraValue);
    }

}
