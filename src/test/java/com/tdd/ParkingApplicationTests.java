package com.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ParkingApplicationTests {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long actual;
    private ParkingFeeCalculator pfc;

    @BeforeEach
    void setUp(){
        pfc = new ParkingFeeCalculator();
    }
    private void parkingStartAt(String start){
        startTime = LocalDateTime.parse(start);
    }
    private void parkingEndAt(String end){
        endTime = LocalDateTime.parse(end);
    }

    private void calculated(){
        actual = pfc.calculate(startTime,endTime);
    }

    private void shouldPay(Long expect){
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void freeFor15Min() {
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-02T00:15:00");
        calculated();
        shouldPay(0L);
    }

    @Test
    void feeFor15Min(){
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-02T00:30:00");
        calculated();
        shouldPay(30L);

    }

    @Test
    void over30under60Fee(){
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-02T01:00:00");
        calculated();
        shouldPay(60L);
    }

    @Test
    void over60under90Fee(){
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-02T01:30:00");
        calculated();
        shouldPay(90L);
    }

    @Test
    void over150Pay150(){
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-02T18:00:00");
        calculated();
        shouldPay(150L);
    }

    @Test
    void anotherDay(){
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-04T00:00:00");
        calculated();
        shouldPay(150L+150L);
    }

}
