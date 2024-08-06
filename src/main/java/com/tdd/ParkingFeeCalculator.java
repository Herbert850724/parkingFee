package com.tdd;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingFeeCalculator {

    public Long calculate(LocalDateTime start,LocalDateTime end){

        long minsBetween = ChronoUnit.MINUTES.between(start, end);
        if(minsBetween<15){
            return 0L;
        }
        if(minsBetween<30){
            return 30L;
        }
        if(minsBetween<60){
            return 60L;
        }
        return 90L;
    }
}
