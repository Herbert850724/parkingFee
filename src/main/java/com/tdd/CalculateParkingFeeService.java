package com.tdd;

import java.time.Duration;
import java.util.List;

public class CalculateParkingFeeService {

    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);
    private PriceBookRepository priceBookRepository;
    private ParkingSessionRepository parkingSessionRepository;

    public CalculateParkingFeeService() {

        priceBookRepository = priceBookRepository;

    }

    public CalculateParkingFeeService(PriceBookRepository priceBookRepository, ParkingSessionRepository parkingSessionRepository){
        this.priceBookRepository = priceBookRepository;
        this.parkingSessionRepository = parkingSessionRepository;
    }

    public Long calculate() {


        ParkingSession parkingSession = parkingSessionRepository.find("ABC-8888");

        PriceBook priceBook = priceBookRepository.getPriceBook();

        Duration duration = parkingSession.getTotalDuration();

        if (isShort(duration)) {
            return 0L;
        }
          List<DailySession> dailySessions = parkingSession.getDailySessions();

        return dailySessions.stream().mapToLong(priceBook::getDailyFee).sum();
    }

    private boolean isShort(Duration duration){

        return duration.compareTo(FIFTY_MINUTES) <= 0;
    }









}
