package ru.vsu.cs.kg2021.lazutkina_a_a.task3.service;

import ru.vsu.cs.kg2021.lazutkina_a_a.task3.ScreenConverter;
import ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes.Line;

import java.awt.*;

public interface LineDrawer
{
    void drawLine(Graphics2D g, ScreenConverter sc, Line line);
}
