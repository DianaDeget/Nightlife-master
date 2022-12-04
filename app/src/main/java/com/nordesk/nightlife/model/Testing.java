package com.nordesk.nightlife.model;

public class Testing {
    public static int randomInt(int start, int end){
        return (int) ((Math.random() * (start - end)) + start);
    }

    public static double randomDouble(int start, int end){
        return (Math.random() * (start - end)) + start;
    }


}
