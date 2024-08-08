package com.tdd;

public interface
ParkingSessionRepository {
    void save(ParkingSession pSession);

    ParkingSession find();
}
