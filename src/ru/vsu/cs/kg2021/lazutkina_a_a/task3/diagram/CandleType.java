package ru.vsu.cs.kg2021.lazutkina_a_a.task3.diagram;

import java.awt.*;

public enum CandleType {
    DECREASING(new Color(255, 105, 105)),
    INCREASING(new Color(87, 170, 127));

    public Color color;

    CandleType(Color color) {
        this.color = color;
    }
}
