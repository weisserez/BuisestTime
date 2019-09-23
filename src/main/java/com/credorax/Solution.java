package com.credorax;

import java.io.*;

public class Solution {

    //Assume we have in a non leap years
    public static final long numOfMsInYear = 31536000000L;
    //TODO read it from configuration
    public static final long heapSize = 8589934592L;
    public static final long memoryToUse = heapSize / 2;
    public static final long sizeOfArray =4L;// memoryToUse / 4;
    public static final long numOfIteration = numOfMsInYear / sizeOfArray + 1;
    private int buisestTime = 0;
    private int maxPhoneCall = 0;
    private long startIndex;
    private long endIndex;
    private int max;


    public String findBuisestTime(String pathToFile) {
        int iterationNumber = 1;
        int[] countArray = new int[(int) sizeOfArray];
        try (BufferedReader br = new BufferedReader(new FileReader(new File(pathToFile)))) {
            String line;
            Long start, end;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("-");
                start = Long.parseLong(values[0]);
                end = Long.parseLong(values[1]);
                //check if we need to calculate maximum and create new array
                if (start > (iterationNumber * sizeOfArray)) {
                    calculateMaxAndTime(countArray, iterationNumber);
                    iterationNumber++;
                    countArray = new int[(int) sizeOfArray];
                } else {
                    ++countArray[(int) (start % sizeOfArray)];
                    if (end >= countArray.length){

                    }else {
                        --countArray[(int) (end % sizeOfArray)];
                    }

                }
            }
            calculateMaxAndTime(countArray,iterationNumber);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return startIndex + "-" + endIndex;
    }

    public void calculateMaxAndTime(int[] array, int iterationNumber) {
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
            this.startIndex = idx +  array.length * --iterationNumber;

        }
    }

}
