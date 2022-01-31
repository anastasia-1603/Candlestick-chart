package ru.vsu.cs.kg2021.lazutkina_a_a.task3.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * Выводит в строковом виде {@link GregorianCalendar c} в заданном формате
     * @param c
     * @param pattern формат даты
     * @return String
     */
    public static String toString(GregorianCalendar c, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = c.getTime();
        return format.format(date);
    }

    /**
     * Читает дату из строки в заданном формате
     * @param date
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static GregorianCalendar readCalendar(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return dateToCalendar(format.parse(date));
    }

    /**
     * Преобразует объект типа Date в тип Calendar
     * @param date {@link Date}
     * @return calendar {@link GregorianCalendar}
     */
    public static GregorianCalendar dateToCalendar(Date date) {
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
