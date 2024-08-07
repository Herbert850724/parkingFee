package com.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class ParkingFeeCalculator {

    private final HolidayBook holidayBook = new HolidayBook();
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
            Long todayFee = getRegularFee(dailySession);
            totalFee += todayFee;
        }
        return totalFee;
    }



    private boolean isShort(Duration duration){

        return duration.compareTo(FIFTY_MINUTES) <= 0;
    }

    private Long getRegularFee(DailySession dailySession){
        Long period = BigDecimal.valueOf(dailySession.getTodayDuration().toNanos())
                .divide(BigDecimal.valueOf(THIRTY_MINUTES.toNanos()), RoundingMode.UP)
                .longValue();

        int unitPrice = holidayBook.isHoliday(dailySession.getToday())
                ? 50
                : 30;
        Long fee = period * unitPrice;

        Long dailyLimit = holidayBook.isHoliday(dailySession.getToday())
                ? 2400L
                : 150L;
        return Math.min(fee, dailyLimit);
    }





}
