package br.com.trama.popularmoviesapp.util;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by trama on 04/07/16.
 */
public class DateHelper {

    public Calendar strToCalendar(String releaseStr, String pattern) {
        Calendar instance = Calendar.getInstance();

        try {

            SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
            Date date = simpleDateFormat.parse(releaseStr);

            instance.setTime(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
        return instance;
    }

    public String calendarToStr(Calendar calendar, String pattern) {
        SimpleDateFormat simpleDateFormat = getSimpleDateFormat(pattern);
        return simpleDateFormat.format(calendar.getTime());
    }

    @NonNull
    private SimpleDateFormat getSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }
}
