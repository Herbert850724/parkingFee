package com.tdd;

import java.time.Duration;
import java.util.List;

public class CalculateParkingFeeService {

    private Duration FIFTY_MINUTES = Duration.ofMinutes(15L);
    private PriceBookRepository priceBookRepository;
    private ParkingSessionRepository parkingSessionRepository;

    public CalculateParkingFeeService() {

//      priceBook = new PriceBook();
        priceBookRepository = priceBookRepository;

    }

    public CalculateParkingFeeService(PriceBookRepository priceBookRepository, ParkingSessionRepository parkingSessionRepository){
        this.priceBookRepository = priceBookRepository;
        this.parkingSessionRepository = parkingSessionRepository;
    }

    public Long calculate(ParkingSession pSession) {

        ParkingSessionRepository parkingSessionRepository = new ParkingSessionRepositoryImpl();
        parkingSessionRepository.save(pSession);
        ParkingSession parkingSession = parkingSessionRepository.find();

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
