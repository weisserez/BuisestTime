package com.credorax;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    private static final long heapSize = 8589934592L;
    // max integer in java is about ~2GB
    private static final int sizeOfArray = Integer.MAX_VALUE;

    private long startIndex;
    private int max;
    private Map<Long, Integer> overflowEndMilliseconds = new HashMap<>();


    public long findBusiestTime(String pathToFile) {
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
                    Arrays.fill(countArray, 0);
                    updateArrayWithOverflow(countArray, iterationNumber);
                } else {
                    ++countArray[(int) (start % sizeOfArray)];
                    if (end >= countArray.length * iterationNumber) {
                        if (overflowEndMilliseconds.containsKey(end)) {
                            overflowEndMilliseconds.put(end, overflowEndMilliseconds.get(end) + 1);
                        } else {
                            overflowEndMilliseconds.put(end, 1);
                        }
                    } else {
                        --countArray[(int) (end % sizeOfArray)];
                    }

                }
            }
            calculateMaxAndTime(countArray, iterationNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return startIndex;
    }

    private void updateArrayWithOverflow(int[] countArray, int iterationNumber) {
        List<Long> overflowToRemove = overflowEndMilliseconds.keySet().stream().
                filter(overflow -> overflow < sizeOfArray * iterationNumber).collect(Collectors.toList());
        overflowToRemove.forEach(overflow -> {
            countArray[(int) (overflow % countArray.length)] -= overflowEndMilliseconds.get(overflow);
            overflowEndMilliseconds.remove(overflow);
        });

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
