package com.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingFeeCalculator {

    private Duration THIRTY_MINUTES = Duration.ofMinutes(30L);
    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);

    public Long calculate(LocalDateTime start,LocalDateTime end) {


        Duration duration = Duration.between(start, end);
        if (isShort(duration)) {
            return 0L;
        }

        LocalDateTime todayStart = start.toLocalDate().atStartOfDay();

        Long totalFee = 0L;
        while(todayStart.isBefore(end)){
            LocalDateTime tomorrowStart = todayStart.plusDays(1L);
            LocalDateTime todaySessionStart = start.isAfter(todayStart)?start:todayStart;
            LocalDateTime todaySessionEnd = end.isBefore(tomorrowStart)?end:tomorrowStart;

            Duration todayDuration = Duration.between(todaySessionStart, todaySessionEnd);
            totalFee += getRegularFee(todayDuration);

            todayStart = tomorrowStart;
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
