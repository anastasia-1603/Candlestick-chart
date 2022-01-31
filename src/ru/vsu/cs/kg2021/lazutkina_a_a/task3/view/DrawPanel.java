package ru.vsu.cs.kg2021.lazutkina_a_a.task3.view;

import ru.vsu.cs.kg2021.lazutkina_a_a.task3.*;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.diagram.Candle;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.service.DataService;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.service.DrawService;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.RealPoint;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.ScreenPoint;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.view.status.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.List;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private static final DrawService DRAW_SERVICE = new DrawService();
    private static final DataService DATA_SERVICE = new DataService();

    private static final String FILENAME = "data/data.txt";
    private final List<Candle> candles;

    private Time time;

    private ScreenConverter sc;

    private List<Candle> currCandles;
    private double max;
    private double min;


    public DrawPanel(int width, int height) {
        this.setSize(width, height);

        candles = DATA_SERVICE.readListCandles(FILENAME, "dd.MM.yyyy", 5, 0, 4);
        time = Time.WEEK;
        currCandles = DATA_SERVICE.groupBy(candles, Calendar.WEEK_OF_MONTH);

        max = DATA_SERVICE.findMaxPrice(currCandles);
        min = DATA_SERVICE.findMinPrice(currCandles);

        sc = new ScreenConverter(0, max + 1,
                currCandles.size(), max - min + 3,
                this.getWidth(), this.getHeight());

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    protected void paintComponent(Graphics origG) {
        sc.setScreenWidth(this.getWidth());
        sc.setScreenHeight(this.getHeight());

        setData();
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));

        DRAW_SERVICE.drawDiagram(currCandles, sc, g);
        DRAW_SERVICE.drawDates(g, sc, currCandles, time);
        DRAW_SERVICE.drawCoordinatePlane(g, sc);

        origG.drawImage(bi, 0, 0, null);
        g.dispose();
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
        setData();
        sc.setCx(0);
        sc.setRealWidth(currCandles.size() + 1);
        repaint();
    }

    private void groupDataByDay() {
        currCandles = candles;
    }

    private void groupDataByWeek() {
        currCandles = DATA_SERVICE.groupBy(candles, Calendar.WEEK_OF_MONTH);
    }

    private void groupDataByMonth() {
        currCandles = DATA_SERVICE.groupBy(candles, Calendar.MONTH);
    }

    public void setData() {
        switch (time) {
            case DAY -> groupDataByDay();
            case WEEK -> groupDataByWeek();
            case MONTH -> groupDataByMonth();
        }
    }

    private void setDefaultScreenConverter() {
        sc.setCx(0);
        sc.setCy(max + 1);
        sc.setRealWidth(currCandles.size() + 1);
        sc.setRealHeight(max - min + 3);
        sc.setScreenWidth(this.getWidth());
        sc.setScreenHeight(this.getHeight());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && !e.isConsumed() && SwingUtilities.isLeftMouseButton(e)) {
            e.consume();
            setDefaultScreenConverter();
            repaint();
        }
    }

    private ScreenPoint prevPoint = null;

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            prevPoint = new ScreenPoint(e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            prevPoint = null;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            ScreenPoint currPoint = new ScreenPoint(e.getX(), e.getY());
            RealPoint p1 = sc.screenToReal(currPoint);
            RealPoint p2 = sc.screenToReal(prevPoint);
            RealPoint delta = p2.minus(p1);
            int x = sc.realXtoScreenX(sc.getCx() + sc.getRealWidth() + delta.getX());

            if (sc.getCx() + delta.getX() >= 0 && sc.getCx() + sc.getRealWidth() + delta.getX() < currCandles.size() + 5) {
                sc.moveCornerX(delta);
            }

            prevPoint = currPoint;
        }
        repaint();
    }


    private int x;
    private int y;

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    private static final double SCALE_STEP = 0.1;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = 1 + SCALE_STEP * (clicks < 0 ? -1 : 1);
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale *= coef;
        }

        sc.changeScaleX(scale);

        repaint();
    }
}
