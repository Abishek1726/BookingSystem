package com.test.bookingsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static final Long _1_DAY_IN_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
    public static final Long  _1_HOUR_IN_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
    public static final Long _1_MINUTES_IN_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);

    public static final String YYYY_MM_DD_FORMAT = "yyyy-MM-dd";

    public static Integer dateDiff(Date d1, Date d2) {
        Long diffInMillis = Math.abs(d1.getTime() - d2.getTime());
        return new Long(TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)).intValue();
    }

    public static Date incrementDateBy(Date date, Integer numberOfDays) {
        return new Date( date.getTime() + (numberOfDays * _1_DAY_IN_MILLIS) );
    }

    public static Optional<Date> getDate(String dateStr, String dateFormat) {
        try {
            return Optional.of(new SimpleDateFormat(dateFormat).parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
