package ru.vsu.cs.kg2021.lazutkina_a_a.task3.service;

import ru.vsu.cs.kg2021.lazutkina_a_a.task3.diagram.Candle;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.utils.ArrayUtil;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.utils.DateUtils;

import java.util.*;
import java.util.List;

public class DataService {

    /**
     * Возвращет максимальную цену свечей
     *
     * @param candles Список свечей
     * @return double max - максимальная цена
     */
    public double findMaxPrice(List<Candle> candles) {
        double max = -1;
        for (Candle c: candles) {
            double high = c.getHigh();
            if (high > max) {
                max = high;
            }
        }
        return max;
    }

    /**
     * возвращает минимальную цену свечей
     *
     * @param candles список свечей
     * @return double min - минимальная цена
     */
    public double findMinPrice(List<Candle> candles) {
        double min = candles.get(0).getLow();
        for (Candle c: candles) {
            double low = c.getLow();
            if (low < min) {
                min = low;
            }
        }
        return min;
    }

    /**
     * Группирует свечи по заданному времени и возвращает новый список свечей,
     * например, вывести свечи за каждый месяц года
     *
     * @param candles       Список свечей
     * @param calendarConst статическое финализированное поле из класса Calendar,
     *                      обозначающее время, по которому нужно группировать свечи
     * @return List</ Candle> {@link Candle}
     */
    public List<Candle> groupBy(List<Candle> candles, int calendarConst) {

        List<Candle> newCandles = new ArrayList<>();
        int date = candles.get(0).getDate().get(calendarConst);
        List<Candle> slice = new ArrayList<>();

        for (Candle c: candles) {

            int currDate = c.getDate().get(calendarConst);

            if (currDate != date) {
                date = currDate;
                newCandles.add(groupCandles(slice));
                slice.clear();
            }
            slice.add(c);

        }
        return newCandles;
    }

    /**
     * Создает из списка свечей новую свечу
     * ("Схлопывание данных")
     *
     * @param candles список свечей
     * @return Candle candle {@link Candle}
     */
    private Candle groupCandles(List<Candle> candles) {
        double high = findMaxPrice(candles);
        double low = findMinPrice(candles);
        double open = candles.get(0).getOpen();
        double close = candles.get(candles.size() - 1).getClose();
        GregorianCalendar date = candles.get(0).getDate();
        return new Candle(open, high, low, close, date);
    }

    /**
     * Читает данные из файла и возвращает список свечей
     *
     * @param filename   имя файла
     * @param dateFormat формат, в котором представлена дата в файле
     *                   (Например, "dd.MM.yyy")
     * @param indexDate  колонка, в которой написаны даты,
     *                   с учетом того, что нумерация с нуля
     * @param fromData   первая колонка цен
     *                   (идут в порядке open, high, low, close)
     * @param toData     последняя колонка цен
     * @return список свечей {@link Candle}
     */
    public List<Candle> readListCandles(String filename, String dateFormat, int indexDate, int fromData, int toData) {
        List<Candle> candles = new ArrayList<>();
        try {
            String[][] lines = ArrayUtil.toString2DArray(filename);
            if (lines != null) {
                for (String[] s: lines) {
                    GregorianCalendar c = DateUtils.readCalendar(s[indexDate - 1], dateFormat);
                    candles.add(toCandle(ArrayUtil.toPrimitiveDouble(Arrays.copyOfRange(s, fromData, toData)), c));
                }
            } else {
                throw new NullPointerException("lines is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candles;
    }

    /**
     * Создает свечу из данных
     *
     * @param data массив цен в порядке open, high, low, close
     * @param date дата свечи
     * @return свечу {@link Candle}
     */
    private Candle toCandle(double[] data, GregorianCalendar date) {
        return new Candle(data[0], data[1], data[2], data[3], date);
    }
}
