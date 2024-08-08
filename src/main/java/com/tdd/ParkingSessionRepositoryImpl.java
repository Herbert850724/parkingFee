package com.tdd;

import java.util.HashMap;
import java.util.Map;

public class ParkingSessionRepositoryImpl implements ParkingSessionRepository {

    private ParkingSession parkingSession;
    private Map<String,ParkingSession> parkingSessions = new HashMap<>();
    public ParkingSessionRepositoryImpl() {
    }

    @Override
    public void save(ParkingSession pSession) {
        parkingSessions.put(pSession.getPlate(),pSession);
    }

    @Override
    public ParkingSession find(String plate) {
        return parkingSessions.get(plate);
    }
}
