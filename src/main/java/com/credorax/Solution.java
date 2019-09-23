package com.credorax;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {

    //Assume we have in a non leap years
    public static final long numOfMsInYear = 31536000000L;
    //TODO read it from configuration
    private static final long heapSize = 8589934592L;
    public static final long memoryToUse = heapSize / 2;
    private static final int sizeOfArray = 4;// memoryToUse / 4;
    //    public static final long numOfIteration = numOfMsInYear / sizeOfArray + 1;


    private long startIndex;
    private long endIndex;
    private int max;
    private Set<Long> overflowEndMilliseconds = new HashSet<>();


    public String findBusiestTime(String pathToFile) {

        int iterationNumber = 1;
        int[] countArray = new int[sizeOfArray];
        try (BufferedReader br = new BufferedReader(new FileReader(new File(pathToFile)))) {
            String line;
            long start, end;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("-");
                start = Long.parseLong(values[0]);
                end = Long.parseLong(values[1]);
                //check if we need to calculate maximum and create new array
                if (start > (iterationNumber * sizeOfArray)) {
                    calculateMaxAndTime(countArray, iterationNumber);
                    iterationNumber++;
                    countArray = new int[sizeOfArray];
                    updateArrayWithOverflow(countArray, iterationNumber);
                } else {
                    ++countArray[(int) (start % sizeOfArray)];
                    if (end >= countArray.length * iterationNumber) {
                        overflowEndMilliseconds.add(end);
                    } else {
                        --countArray[(int) (end % sizeOfArray)];
                    }

                }
            }
            calculateMaxAndTime(countArray, iterationNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return startIndex + "-" + endIndex;
    }

    private void updateArrayWithOverflow(int[] countArray, int iterationNumber) {
        List<Long> overflowToRemove = overflowEndMilliseconds.stream().
                filter(overflow -> overflow < sizeOfArray * iterationNumber).collect(Collectors.toList());
        overflowToRemove.forEach(overflow -> --countArray[(int) (overflow % countArray.length)]);
        overflowEndMilliseconds.removeAll(overflowToRemove);
    }

    private void calculateMaxAndTime(int[] array, int iterationNumber) {
        int maxy = Integer.MIN_VALUE;
        int cur = 0, idx = 0;

        for (int i = 0; i < array.length; i++) {
            cur += array[i];
            if (maxy < cur) {
                maxy = cur;
                idx = i;

            }
        }
        if (this.max < maxy) {
            max = maxy;
            this.startIndex = idx + array.length * --iterationNumber;

        }
    }

}
