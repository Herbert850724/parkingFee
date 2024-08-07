package com.tdd;

import java.time.Duration;
import java.util.List;

public class ParkingFeeCalculator {

    private final PriceBook priceBook;

    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);

    public ParkingFeeCalculator() {
        priceBook = new PriceBook();
    }

    public Long calculate(ParkingSession parkingSession) {

        Duration duration = parkingSession.getTotalDuration();

        if (isShort(duration)) {
            return 0L;
        }
//        Long totalFee = 0L;
//
          List<DailySession> dailySessions = parkingSession.getDailySessions();
//        for(DailySession dailySession : dailySessions){
//            totalFee += holidayBook.getDailyFee(dailySession);
//        }
//        return totalFee;

        return dailySessions.stream().mapToLong(priceBook::getDailyFee).sum();
    }



    private boolean isShort(Duration duration){

        return duration.compareTo(FIFTY_MINUTES) <= 0;
    }









}
