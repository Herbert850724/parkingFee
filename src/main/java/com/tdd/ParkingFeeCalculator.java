package com.tdd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class ParkingFeeCalculator {

    private final HolidayBook holidayBook;

    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);

    public ParkingFeeCalculator() {
        holidayBook = new HolidayBook();
    }

    public Long calculate(ParkingSession parkingSession) {

        Duration duration = parkingSession.getTotalDuration();

        if (isShort(duration)) {
            return 0L;
        }
        Long totalFee = 0L;

        List<DailySession> dailySessions = parkingSession.getDailySessions();
        for(DailySession dailySession : dailySessions){
            totalFee += holidayBook.getDailyFee(dailySession);
        }
        return totalFee;
    }



    private boolean isShort(Duration duration){

        return duration.compareTo(FIFTY_MINUTES) <= 0;
    }









}
