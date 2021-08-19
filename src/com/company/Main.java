package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final Integer divider = 4;

    public static void main(String[] args) {
        List<Integer []> inputData = new ArrayList<>();

        Integer[] line_1 = {8, 3, 4};
        Integer[] line_2 = {4, 8, 12};
        Integer[] line_3 = {9, 5, 6};
        Integer[] line_4 = {2, 8, 3};
        Integer[] line_5 = {12, 3, 5};
        Integer[] line_6 = {1, 4, 12};

        inputData.add(line_1);
        inputData.add(line_2);
        inputData.add(line_3);
        inputData.add(line_4);
        inputData.add(line_5);
        inputData.add(line_6);

        System.out.println("maxSum: " +findMaxSum(inputData));
    }

    public static int findMaxSum(List<Integer[]> inputData) {
        List<List<Integer>> preparedLines = prepareInputLinesToMerge(inputData);
        List<Integer> mergedLines = mergeLines(preparedLines);
        return mergedLines.stream().filter(num -> num >= divider && num % divider == 0)
                .max(Integer::compareTo).orElse(0);
    }

    // 1. convert a line into a sum of its each numbers pair
    // 2. keep only those results necessary for further processing
    private static List<List<Integer>> prepareInputLinesToMerge(List<Integer[]> inputData) {
        List<List<Integer>> preparedLines = new ArrayList<>();
        for (Integer[] line : inputData) {
            int length = line.length;
            Map<Integer, Integer> result = new HashMap<>(3);
            for (int i = 0; i < length - 1; i++) {
                for (int j = i + 1; j < length; j++) {
                    //filter out unnecessary elements
                    Integer sum = line[i] + line[j];
                    Integer reminder = sum % divider;
                    if (sum > result.getOrDefault(reminder, 0)) {
                        result.put(reminder, sum);
                    }
                    //System.out.print(line[i] +" + " +line[j] + " = " + (sum) + " | ");
                }
            }
            preparedLines.add(new ArrayList<>(result.values()));
        }

        System.out.println("-----");
        return preparedLines;
    }

    //merge lines using similar approach as above for selecting only necessary data
    private static List<Integer> mergeLines(List<List<Integer>> lines) {
        //assert lines.isEmpty();

        return lines.stream().reduce((curr, next) -> {
            //max map size cannot be more than 4 (as possible keys (reminders) are 0, 1, 2 ,3)
            Map<Integer, Integer> result = new HashMap<>(4);
            for (Integer num_1 : curr) {
                for (Integer num_2 : next) {
                    Integer sum = num_1 + num_2;
                    Integer reminder = sum % divider;
                    if (sum > result.getOrDefault(reminder, 0)) {
                        result.put(reminder, sum);
                    }
                }
            }
            return new ArrayList<>(result.values());
        }).orElse(new ArrayList<>());
    }
}
