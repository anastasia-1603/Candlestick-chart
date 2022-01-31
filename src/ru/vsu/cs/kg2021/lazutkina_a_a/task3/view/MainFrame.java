package ru.vsu.cs.kg2021.lazutkina_a_a.task3.view;

import ru.vsu.cs.kg2021.lazutkina_a_a.task3.view.status.Time;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel panelMain;
    private DrawPanel drawPanel;
    private JButton buttonDay;
    private JButton buttonWeek;
    private JButton buttonMonth;

    private JPanel panelButtonsTime;

    public MainFrame() throws HeadlessException {
        panelMain = new JPanel();
        drawPanel = new DrawPanel(this.getWidth(), this.getHeight());
        initButtons();
        initPanelButtonsTime();

        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setSize(this.getWidth(), this.getHeight());
        addComponentsToPanel();
        addListeners();
        changeButtons();

        this.setTitle("Candlestick chart");
        this.add(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initButtons() {
        buttonDay = new JButton("Day");
        buttonWeek = new JButton("Week");
        buttonMonth = new JButton("Month");
    }

    private void addListeners() {
        buttonDay.addActionListener(e ->
        {
            drawPanel.setTime(Time.DAY);
            changeButtons();
        });

        buttonWeek.addActionListener(e ->
        {
            drawPanel.setTime(Time.WEEK);
            changeButtons();
        });

        buttonMonth.addActionListener(e ->
        {
            drawPanel.setTime(Time.MONTH);
            changeButtons();
        });
    }

    private void changeButtons() {
        setColorPressedButtonsTime();
        changeEnabledButtonsTime();
    }

    private void changeEnabledButtonsTime() {
        switch (drawPanel.getTime()) {
            case DAY -> {
                buttonDay.setEnabled(false);
                buttonWeek.setEnabled(true);
                buttonMonth.setEnabled(true);
            }
            case WEEK -> {
                buttonDay.setEnabled(true);
                buttonWeek.setEnabled(false);
                buttonMonth.setEnabled(true);
            }
            case MONTH -> {
                buttonDay.setEnabled(true);
                buttonWeek.setEnabled(true);
                buttonMonth.setEnabled(false);
            }
        }
    }

    private void setColorPressedButtonsTime() {
        switch (drawPanel.getTime()) {
            case DAY -> {
                buttonDay.setBackground(Color.BLUE);
                buttonWeek.setBackground(null);
                buttonMonth.setBackground(null);
            }
            case WEEK -> {
                buttonDay.setBackground(null);
                buttonWeek.setBackground(Color.BLUE);
                buttonMonth.setBackground(null);
            }
            case MONTH -> {
                buttonDay.setBackground(null);
                buttonWeek.setBackground(null);
                buttonMonth.setBackground(Color.BLUE);
            }
        }
    }

    private void initPanelButtonsTime() {
        panelButtonsTime = new JPanel();
        panelButtonsTime.setLayout(new BoxLayout(panelButtonsTime, BoxLayout.X_AXIS));
        panelButtonsTime.add(buttonDay);
        panelButtonsTime.add(buttonWeek);
        panelButtonsTime.add(buttonMonth);
    }

    private void addComponentsToPanel() {
        panelMain.add(panelButtonsTime);
        panelMain.add(drawPanel);
    }
}
