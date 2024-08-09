package com.tdd;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ParkingSessionRepositoryImpl implements ParkingSessionRepository {

    private Map<String,ParkingSessionPO> parkingSession = new HashMap<>();
    public ParkingSessionRepositoryImpl() {

    }

    @Override
    public void save(ParkingSession pSession) {

        ParkingSessionPO parkingSessionPO = ParkingSessionPO.getParkSessionPO(pSession);
        parkingSession.put(pSession.getPlate(),parkingSessionPO);
    }



    @Override
    public ParkingSession find(String plate) {
        ParkingSessionPO parkingSessionPO = parkingSession.get(plate);

        if(parkingSessionPO == null) {
            return null;
        }
        ParkingSession parkingSession = parkingSessionPO.toEntity(parkingSessionPO);
        return parkingSession;
    }


}
