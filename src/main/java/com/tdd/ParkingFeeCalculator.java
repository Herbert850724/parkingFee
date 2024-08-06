package com.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingFeeCalculator {

    private Duration THIRTY_MINUTES = Duration.ofMinutes(30L);
    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);
    public Long calculate(LocalDateTime start,LocalDateTime end) {


        Duration duration = Duration.between(start, end);
        if (isShort(duration)) {
            return 0L;
        }
        if(start.toLocalDate().equals(end.toLocalDate())){
            return getRegularFee(duration);
        }
        LocalDateTime todayStart = start.toLocalDate().atStartOfDay();
        Long totalFee = 0L;
        while(todayStart.isBefore(end)){

            if(start.isAfter(todayStart) && end.isAfter(todayStart.plusDays(1))){
                LocalDateTime todaySessionStart = start;
                LocalDateTime todaySessionEnd = todayStart.plusDays(1);

                Duration todayDuration = Duration.between(todaySessionStart,todaySessionEnd);
                totalFee += getRegularFee(todayDuration);
            }else{
                totalFee += 150L;
            }

            todayStart = todayStart.plusDays(1);
        }

        return totalFee;
    }

    private boolean isShort(Duration duration){
        if(duration.compareTo(FIFTY_MINUTES) <= 0){
            return true;
        }
        return false;
    }

    private Long getRegularFee(Duration duration){
        Long period = BigDecimal.valueOf(duration.toNanos())
                .divide(BigDecimal.valueOf(THIRTY_MINUTES.toNanos()), RoundingMode.UP)
                .longValue();

        Long fee = period * 30;
        return Math.min(fee, 150L);
    }
}
