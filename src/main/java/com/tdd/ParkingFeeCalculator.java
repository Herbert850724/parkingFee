package com.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class ParkingFeeCalculator {

    private Duration THIRTY_MINUTES = Duration.ofMinutes(30L);
    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);

    public Long calculate(ParkingSession parkingSession) {

        Duration duration = parkingSession.getTotalDuration();

        if (isShort(duration)) {
            return 0L;
        }
        Long totalFee = 0L;

        List<DailySession> dailySessions = parkingSession.getDailySessions();
        for(DailySession dailySession : dailySessions){
            Long todayFee = getRegularFee(dailySession.getTodayDuration(),dailySession.getToday());
            totalFee += todayFee;
        }
        return totalFee;
    }



    private boolean isShort(Duration duration){

        return duration.compareTo(FIFTY_MINUTES) <= 0;
    }

    private Long getRegularFee(Duration duration, LocalDate today){
        Long period = BigDecimal.valueOf(duration.toNanos())
                .divide(BigDecimal.valueOf(THIRTY_MINUTES.toNanos()), RoundingMode.UP)
                .longValue();

        int unitPrice = List.of(DayOfWeek.SATURDAY,DayOfWeek.SUNDAY).contains(today.getDayOfWeek())
                ? 50
                : 30;
        Long fee = period * unitPrice;

        Long dailyLimit = List.of(DayOfWeek.SATURDAY,DayOfWeek.SUNDAY).contains(today.getDayOfWeek())
                ? 2400L
                : 100L;
        return Math.min(fee, dailyLimit);
    }



}
