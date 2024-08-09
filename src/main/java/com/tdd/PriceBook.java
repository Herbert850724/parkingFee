package com.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PriceBook {
    public PriceBook() {
        nationalHolidays.add(LocalDate.of(2024, 1,1));
        nationalHolidays.add(LocalDate.of(2024,2,28));
        nationalHolidays.add(LocalDate.of(2024,10,10));
    }
    private Duration THIRTY_MINUTES = Duration.ofMinutes(30L);
    private Set<LocalDate> nationalHolidays = new HashSet<>();
    boolean isHoliday(LocalDate today) {


        return nationalHolidays.contains(today) || List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(today.getDayOfWeek());
    }

    Long getDailyFee(DailySession dailySession){
        return getRegularFee(dailySession);
    }

    Long getRegularFee(DailySession dailySession){
        Long period = BigDecimal.valueOf(dailySession.getTodayDuration().toNanos())
                .divide(BigDecimal.valueOf(THIRTY_MINUTES.toNanos()), RoundingMode.UP)
                .longValue();

        int unitPrice = isHoliday(dailySession.getToday())
                ? 50
                : 30;
        Long fee = period * unitPrice;

        Long dailyLimit = isHoliday(dailySession.getToday())
                ? 2400L
                : 150L;
        return Math.min(fee, dailyLimit);
    }
}