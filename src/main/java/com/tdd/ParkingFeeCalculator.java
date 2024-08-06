package com.tdd;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingFeeCalculator {

    public Long calculate(LocalDateTime start,LocalDateTime end){

        long minsBetween = ChronoUnit.MINUTES.between(start, end);
        if(minsBetween<15){
            return 0L;
        }

        Long regularFee = getRegularFee(minsBetween);
        return Math.min(regularFee,150L);

    }

    private Long getRegularFee(Long minsBetween){
        Long periods = minsBetween/30;
        return (periods+1) * 30L;
    }


}
