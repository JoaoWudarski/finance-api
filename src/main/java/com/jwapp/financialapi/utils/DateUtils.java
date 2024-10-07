package com.jwapp.financialapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMAT_BRAZIL = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private DateUtils() {
    }

    public static String toBrazilFormat(LocalDate date) {
        return date.format(DATE_FORMAT_BRAZIL);
    }
}
