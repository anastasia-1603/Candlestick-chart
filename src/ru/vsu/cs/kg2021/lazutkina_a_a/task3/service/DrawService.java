package ru.vsu.cs.kg2021.lazutkina_a_a.task3.service;

import ru.vsu.cs.kg2021.lazutkina_a_a.task3.*;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.diagram.*;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.Line;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.RealPoint;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.Rectangle;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.ScreenPoint;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.utils.DateUtils;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.view.status.Time;

import java.awt.*;
import java.util.List;

public class DrawService implements LineDrawer {
    public void drawLine(Graphics2D g, ScreenConverter sc, Line line) {
        ScreenPoint p1 = sc.realToScreen(line.getPoint1());
        ScreenPoint p2 = sc.realToScreen(line.getPoint2());
        g.drawLine(p1.getColumn(), p1.getRow(), p2.getColumn(), p2.getRow());
    }

    public void drawRect(Graphics2D g, ScreenConverter sc, Rectangle rect) {
        ScreenPoint p1 = sc.realToScreen(rect.getLeftUpperPoint());
        ScreenPoint p2 = sc.realToScreen(rect.getRightLowerPoint());
        g.drawRect(p1.getColumn(), p1.getRow(),
                Math.abs(p2.getColumn() - p1.getColumn()), Math.abs(p2.getRow() - p1.getRow()));
    }


    public void fillRect(Graphics2D g, ScreenConverter sc, Rectangle rect) {
        ScreenPoint p1 = sc.realToScreen(rect.getLeftUpperPoint());
        ScreenPoint p2 = sc.realToScreen(rect.getRightLowerPoint());
        g.fillRect(p1.getColumn(), p1.getRow(),
                Math.abs(p2.getColumn() - p1.getColumn()), Math.abs(p2.getRow() - p1.getRow()));
    }

    public void drawCandle(Graphics2D g, ScreenConverter sc, Candle candle, int width, int place) {
        double high = candle.getHigh();
        double up = candle.getUp();
        double low = candle.getLow();
        double bottom = candle.getBottom();

        Line upperShadow = new Line(new RealPoint(place, high), new RealPoint(place, up));
        Rectangle realBody = new Rectangle(new RealPoint(place - width * 0.4, up),
                new RealPoint(place + width * 0.4, bottom));
        Line lowerShadow = new Line(new RealPoint(place, bottom), new RealPoint(place, low));

        Color oldC = g.getColor();
        if (candle.getType().color == Color.WHITE) {
            g.setColor(Color.BLACK);
            drawLine(g, sc, upperShadow);
            drawRect(g, sc, realBody);
        } else {
            g.setColor(candle.getType().color);
            drawLine(g, sc, upperShadow);
            fillRect(g, sc, realBody);
        }
        drawLine(g, sc, lowerShadow);
        g.setColor(oldC);

    }

    public void drawDiagram(List<Candle> candles, ScreenConverter sc, Graphics2D g) {
        int x = (int) sc.screenToReal(new ScreenPoint(0, 0)).getX();
        for (int i = x + 1; i < x + sc.getRealWidth(); i++) {
            if (i < candles.size() && i >= 0) {
                drawCandle(g, sc, candles.get(i), 1, i);
            }
        }

        DataService ds = new DataService();
        double min = ds.findMinPrice(candles);
        double max = ds.findMaxPrice(candles);
        double interval = (max - min) / sc.getRealHeight();

        for (double d = min; d <= max; d += interval) {
            drawDashY(g, sc, 4, sc.getScreenWidth() - 45, d);
            drawPrice(g, sc, sc.getScreenWidth() - 38, d, d);
        }
    }

    public void drawDates(Graphics2D g, ScreenConverter sc, List<Candle> candles, Time currTime) {
        switch (currTime) {
            case DAY:
                if (sc.getRealWidth() >= 120) {
                    drawDates(g, sc, candles, "yyyy");
                } else if (sc.getRealWidth() >= 60) {
                    drawDates(g, sc, candles, "MMM yyyy");
                } else if (sc.getRealWidth() >= 20) {
                    drawDates(g, sc, candles, "WW");
                } else {
                    drawDates(g, sc, candles, "dd.MM.yyyy");
                }
                break;
            case MONTH:
                if (sc.getRealWidth() >= 12) {
                    drawDates(g, sc, candles, "yyyy");
                } else {
                    drawDates(g, sc, candles, "MMM yyyy");
                }
                break;
            case WEEK:
                if (sc.getRealWidth() >= 50) {
                    drawDates(g, sc, candles, "yyyy");
                } else if (sc.getRealWidth() >= 20) {
                    drawDates(g, sc, candles, "MMM yyyy");
                } else {
                    drawDates(g, sc, candles, "WW.MM");
                }
                break;
        }
    }

    private void drawDates(Graphics2D g, ScreenConverter sc, List<Candle> candles, String pattern) {
        String date = DateUtils.toString(candles.get(0).getDate(), pattern);
        drawString(g, sc, 1, sc.getScreenHeight() - 4, date);
        for (int i = 0; i < candles.size(); i++) {
            String currDate = DateUtils.toString(candles.get(i).getDate(), pattern);
            if (!currDate.equals(date)) {
                date = currDate;
                drawDashX(g, sc, 4, i, sc.getScreenHeight() - 20);
                drawString(g, sc, i + 1, sc.getScreenHeight() - 4, date);
            }
        }
    }

    public void drawCoordinatePlane(Graphics2D g, ScreenConverter sc) {
        BasicStroke oldStroke = (BasicStroke) g.getStroke();
        g.setStroke(new BasicStroke(3));
        int width = sc.getScreenWidth();
        int height = sc.getScreenHeight();
        int x = width - 40;
        int y = height - 20;

        g.drawLine(x, y, -width, y);
        g.drawLine(x, y, x, -height);
        g.setStroke(oldStroke);
    }

    private void drawDashX(Graphics2D g, ScreenConverter sc, int height, double realX, int screenY) {
        int screenX = sc.realXtoScreenX(realX);
        g.drawLine(screenX, screenY, screenX, screenY - height);
    }

    private void drawDashY(Graphics2D g, ScreenConverter sc, int height, int screenX, double realY) {
        int screenY = sc.realYtoScreenY(realY);
        g.drawLine(screenX, screenY, screenX + height, screenY);
    }

    private void drawString(Graphics2D g, ScreenConverter sc, double realX, int screenY, String text) {
        int screenX = sc.realXtoScreenX(realX);
        g.drawString(text, screenX, screenY);
    }

    private void drawPrice(Graphics2D g, ScreenConverter sc, int screenX, double realY, double price) {
        int screenY = sc.realYtoScreenY(realY);
        String text = String.format("%.2f", price);
        g.drawString(text, screenX, screenY);
    }
}
