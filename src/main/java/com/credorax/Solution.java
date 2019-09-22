package com.credorax;

public class Solution {
    public static final int numOfMsInDaY = 8640000;
    //Assume we have in a non leap years
    public static final long numOfMsInYear = 31536000000L;
    //TODO read it from configuration
    public static final long heapSize = (long) (8 * Math.pow(1024, 3));
    public static final long memoryToUse = heapSize / 2;
    public static final long sizeOfArray = memoryToUse / 4;
    public static final long numOfIteration = numOfMsInYear/sizeOfArray;


}
