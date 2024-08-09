package com.tdd;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

public class ParkingSessionRepositoryImpl implements ParkingSessionRepository {

    private Map<String,ParkingSession> parkingSessionsOld = new HashMap<>();
    private Map<String,ParkingSessionPO> parkingSession = new HashMap<>();
    public ParkingSessionRepositoryImpl() {

    }

    @Override
    public void save(ParkingSession pSession) {

        ParkingSessionPO parkingSessionPO = new ParkingSessionPO();
        parkingSessionPO.setPlate(pSession.getPlate());
        parkingSessionPO.setStart(pSession.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        parkingSessionPO.setEnd(pSession.getEnd() == null
                ? null
                : pSession.getEnd().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        parkingSession.put(pSession.getPlate(),parkingSessionPO);
        parkingSessionsOld.put(pSession.getPlate(),pSession);
    }

    @Override
    public ParkingSession find(String plate) {
        ParkingSessionPO parkingSessionPO = parkingSession.get(plate);

        if(parkingSessionPO == null) {
            return null;
        }

        ParkingSession parkingSession = new ParkingSession(
                parkingSessionPO.getPlate(),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(parkingSessionPO.getStart()), ZoneId.systemDefault()),
                parkingSessionPO.getEnd() == null
                    ? null
                    : LocalDateTime.ofInstant(Instant.ofEpochMilli(parkingSessionPO.getEnd()), ZoneId.systemDefault()));

        return parkingSession;
        //return parkingSessionsOld.get(plate);
    }
}
