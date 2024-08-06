package com.tdd;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingFeeCalculator {

    public Long calculate(LocalDateTime start,LocalDateTime end){

        long minsBetween = ChronoUnit.MINUTES.between(start, end);
        if(minsBetween<15){
            return 0L;
        }

        Long periods = minsBetween/30;
        Long fee = (periods+1) * 30L;

        return fee;

    }
}
