package ru.vsu.cs.kg2021.lazutkina_a_a.task3.shapes;

public class Rectangle {
    RealPoint leftUpperPoint;
    RealPoint rightLowerPoint;

    public Rectangle(RealPoint leftUpperPoint, RealPoint rightLowerPoint) {
        this.leftUpperPoint = leftUpperPoint;
        this.rightLowerPoint = rightLowerPoint;
    }

    public RealPoint getLeftUpperPoint() {
        return leftUpperPoint;
    }

    public RealPoint getRightLowerPoint() {
        return rightLowerPoint;
    }
}
