package com.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class ParkingApplicationTests {

    @Test
    void freeFor15Min() {
        ParkingFeeCalculator pfc = new ParkingFeeCalculator();

        Long actual = pfc.calculate(
                LocalDateTime.of(2024,1,1,0,0,0)
                ,LocalDateTime.of(2024,1,1,0,14,0));

        Assertions.assertThat(actual).isEqualTo(0L);
    }

    @Test
    void feeFor15Min(){
        ParkingFeeCalculator pfc = new ParkingFeeCalculator();

        Long actual = pfc.calculate(
                LocalDateTime.of(2024,1,1,0,0,0)
                ,LocalDateTime.of(2024,1,1,0,15,0)
        );

        Assertions.assertThat(actual).isEqualTo(30L);
    }

    @Test
    void over30under60Fee(){

        ParkingFeeCalculator pfc = new ParkingFeeCalculator();

        Long actual = pfc.calculate(
                LocalDateTime.of(2024,1,1,0,0,0)
                ,LocalDateTime.of(2024,1,1,0,45,0)
        );

        Assertions.assertThat(actual).isEqualTo(60L);
    }

    @Test
    void over60under90Fee(){
        ParkingFeeCalculator pfc = new ParkingFeeCalculator();

        Long actual = pfc.calculate(
                LocalDateTime.of(2024,1,1,0,0,0)
                ,LocalDateTime.of(2024,1,1,1,0,0)
        );

        Assertions.assertThat(actual).isEqualTo(90L);
    }

    @Test
    void over150Pay150(){
        ParkingFeeCalculator pfc = new ParkingFeeCalculator();

        Long actual = pfc.calculate(
                LocalDateTime.of(2024,1,1,0,0,0)
                ,LocalDateTime.of(2024,1,1,9,0,0)
        );

        Assertions.assertThat(actual).isEqualTo(150L);
    }

}
