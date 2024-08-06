package com.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ParkingApplicationTests {

    private void runTest(String start,String end,Long expect){
        ParkingFeeCalculator pfc = new ParkingFeeCalculator();

        Long actual = pfc.calculate(
                LocalDateTime.parse(start)
                ,LocalDateTime.parse(end));

        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void freeFor15Min() {
        runTest("2024-01-01T00:00:00","2024-01-01T00:14:00",0L);
    }

    @Test
    void feeFor15Min(){
        runTest("2024-01-01T00:00:00","2024-01-01T00:15:00",30L);
    }

    @Test
    void over30under60Fee(){
        runTest("2024-01-01T00:00:00","2024-01-01T00:45:00",60L);
    }

    @Test
    void over60under90Fee(){
        runTest("2024-01-01T00:00:00","2024-01-01T01:00:00",90L);
    }

    @Test
    void over150Pay150(){
        runTest("2024-01-01T00:00:00","2024-01-01T19:00:00",150L);
    }

}
