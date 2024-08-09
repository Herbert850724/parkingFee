package com.tdd;

import lombok.Data;

@Data
public class ParkingSessionPO {

    private String plate;
    private Long start;
    private Long end;
}
