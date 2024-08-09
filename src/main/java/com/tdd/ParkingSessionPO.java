package com.tdd;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class ParkingSessionPO {

    private String plate;
    private Long start;
    private Long end;

    public static ParkingSessionPO getParkSessionPO(ParkingSession pSession) {
        ParkingSessionPO parkingSessionPO = new ParkingSessionPO();
        parkingSessionPO.setPlate(pSession.getPlate());
        parkingSessionPO.setStart(pSession.getStart().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        parkingSessionPO.setEnd(pSession.getEnd() == null
                ? null
                : pSession.getEnd().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        return parkingSessionPO;
    }

    public static ParkingSession toEntity(ParkingSessionPO parkingSessionPO) {


        ParkingSession parkingSession = new ParkingSession(
                parkingSessionPO.getPlate(),
                LocalDateTime.ofInstant(Instant.ofEpochMilli(parkingSessionPO.getStart()), ZoneId.systemDefault()),
                parkingSessionPO.getEnd() == null
                        ? null
                        : LocalDateTime.ofInstant(Instant.ofEpochMilli(parkingSessionPO.getEnd()), ZoneId.systemDefault()));

        return parkingSession;
    }
}
