package ru.vsu.cs.kg2021.lazutkina_a_a.task3.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ArrayUtil {

    /**
     * Преобразует массив строк, каждый элемент в котором -
     * число типа double в строковом представлении,
     * в массив чисел double
     *
     * @param stringArray
     * @return массив чисел double
     */
    public static double[] toPrimitiveDouble(String[] stringArray) {
        Double[] doubleArray = new Double[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            doubleArray[i] = Double.parseDouble(stringArray[i]);
        }
        return toPrimitive(doubleArray);
    }

    /**
     * Читает из файла двумерный массив строк
     *
     * @param filename
     * @return
     */
    public static String[][] toString2DArray(String filename) {
        try {
            return splitStrings(readLinesFromFile(filename));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Разделяет каждую строку из переданного массива
     * на массив строк с разделителями "," или ";"
     *
     * @param lines
     * @return двумерный массив строк
     */
    public static String[][] splitStrings(String[] lines) {
        List<String[]> list = new ArrayList<>();
        for (String s: lines) {
            list.add(s.split("(\\s|[,;])+"));
        }
        return list.toArray(new String[0][]);
    }

    /**
     * Читает из файла массив строк,
     * где разделитель - перенос на следующую строку
     *
     * @param fileName
     * @return массив строк
     * @throws FileNotFoundException
     */
    public static String[] readLinesFromFile(String fileName) throws FileNotFoundException {
        List<String> lines;
        try (Scanner scanner = new Scanner(new File(fileName), "UTF-8")) {
            lines = new ArrayList<>();
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines.toArray(new String[0]);
    }

    /**
     * Приводит массив Double к примитивному типу
     *
     * @param arr
     * @return массив типа double
     */
    private static double[] toPrimitive(Double[] arr) {
        if (arr == null) {
            return null;
        }
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }
}
