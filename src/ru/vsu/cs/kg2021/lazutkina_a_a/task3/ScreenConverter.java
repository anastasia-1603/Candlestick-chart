package ru.vsu.cs.kg2021.lazutkina_a_a.task3;

import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.RealPoint;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.ScreenPoint;

public class ScreenConverter {
    private double cx, cy, realWidth, realHeight;
    private int screenWidth, screenHeight;

    public ScreenConverter(double cx, double cy, double realWidth, double realHeight, int screenWidth, int screenHeight) {
        this.cx = cx;
        this.cy = cy;
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void moveCornerX(RealPoint delta) {
        cx += delta.getX();
    }

    public void changeScaleX(double scale) {
        double deltaX = (realWidth - realWidth * scale);
        cx += deltaX;
        realWidth *= scale;
    }

    public ScreenPoint realToScreen(RealPoint point) {
        double x = (point.getX() - cx) / realWidth * screenWidth;
        double y = (cy - point.getY()) / realHeight * screenHeight;
        return new ScreenPoint((int) x, (int) y);
    }

    public RealPoint screenToReal(ScreenPoint p) {
        double x = cx + p.getColumn() * realWidth / screenWidth;
        double y = cy - p.getRow() * realHeight / screenHeight;
        return new RealPoint(x, y);
    }

    public int realXtoScreenX(double x) {
        return (int) ((x - cx) / realWidth * screenWidth);
    }

    public int realYtoScreenY(double y) {
        return (int) ((cy - y) / realHeight * screenHeight);
    }

    public double getCx() {
        return cx;
    }

    public void setCx(double cx) {
        this.cx = cx;
    }

    public void setCy(double cy) {
        this.cy = cy;
    }

    public double getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    public double getRealHeight() {
        return realHeight;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
