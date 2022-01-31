package ru.vsu.cs.kg2021.lazutkina_a_a.task3.diagram;

import java.util.GregorianCalendar;

public class Candle implements Comparable<Candle> {
    private final double close;
    private final double high;
    private final double low;
    private final double open;
    private final GregorianCalendar date;

    private final CandleType type;

    private final double bottom;
    private final double up;

    public Candle(double open, double high, double low, double close, GregorianCalendar date) {

        if (close > open) {
            type = CandleType.INCREASING;
            up = close;
            bottom = open;
        } else {
            type = CandleType.DECREASING;
            up = open;
            bottom = close;
        }

        this.open = open;
        this.close = close;
        this.date = date;
        this.high = high;
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getBottom() {
        return bottom;
    }

    public double getUp() {
        return up;
    }

    public CandleType getType() {
        return type;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    @Override
    public int compareTo(Candle o) {
        return date.compareTo(o.date);
    }
}
