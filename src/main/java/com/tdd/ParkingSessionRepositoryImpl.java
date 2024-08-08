package com.tdd;

public class ParkingSessionRepositoryImpl implements ParkingSessionRepository {

    private ParkingSession parkingSession;
    public ParkingSessionRepositoryImpl() {
    }

    @Override
    public void save(ParkingSession pSession) {
        parkingSession = pSession;
    }

    @Override
    public ParkingSession find(String plate) {
        return parkingSession;
    }
}
