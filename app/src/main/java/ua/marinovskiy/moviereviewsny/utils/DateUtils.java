package ua.marinovskiy.moviereviewsny.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 26.02.2016.
 */
public class DateUtils {

    public static final String DATE_SCHEME = "yyyy-MM-d";

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_SCHEME, Locale.getDefault());
        return dateFormat.format(date);
    }

}
