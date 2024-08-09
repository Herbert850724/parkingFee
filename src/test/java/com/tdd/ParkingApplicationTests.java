package com.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
class ParkingApplicationTests {

    private Long actual;
    private CalculateParkingFeeService pfc;
    private ParkingSessionRepository parkingSessionRepository = new ParkingSessionRepositoryImpl();

    //在儲存時間可以改的可讀性更高 不要有T
    @BeforeEach
    void setUp(){
        pfc = new CalculateParkingFeeService(new PriceBookRepositoryImpl(new PriceBook()),parkingSessionRepository);
    }
    private void carDriveIn(String start, String plate){
        parkingSessionRepository.save(ParkingSession.start(start,plate));
    }
    private void carDriveOut(String end,String plate){

        ParkingSession parkingSession = parkingSessionRepository.find(plate);
        parkingSession.end(LocalDateTime.parse(end));
        parkingSessionRepository.save(parkingSession);
    }

    private void calculated(String plate){
        actual = pfc.calculate(plate);
    }

    private void shouldPay(Long expect){
        Assertions.assertThat(actual).isEqualTo(expect);
    }

    @Test
    void freeFor15Min() {
        carDriveIn("2024-01-02T00:00:00","ABC-8888");
        carDriveOut("2024-01-02T00:15:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(0L);
    }

    @Test
    void feeFor15Min(){
        carDriveIn("2024-01-02T00:00:00", "ABC-8888");
        carDriveOut("2024-01-02T00:30:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(30L);

    }

    @Test
    void over30under60Fee(){
        carDriveIn("2024-01-02T00:01:00", "ABC-8888");
        carDriveOut("2024-01-02T01:01:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(60L);
    }

    @Test
    void over60under90Fee(){
        carDriveIn("2024-01-02T00:00:00", "ABC-8888");
        carDriveOut("2024-01-02T01:30:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(90L);
    }

    @Test
    void over150Pay150(){
        carDriveIn("2024-01-02T00:00:00", "ABC-8888");
        carDriveOut("2024-01-02T18:00:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(150L);
    }

    @Test
    void anotherDay(){
        carDriveIn("2024-01-02T00:00:00", "ABC-8888");
        carDriveOut("2024-01-04T00:00:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(150L+150L);
    }

    @Test
    void partialDayAndWholeDay(){
        carDriveIn("2024-01-02T23:50:00", "ABC-8888");
        carDriveOut("2024-01-04T00:00:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(30L+150L);
    }

    @Test
    void wholeDayAndPartialDay(){
        carDriveIn("2024-01-02T00:00:00", "ABC-8888");
        carDriveOut("2024-01-03T00:10:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(150L+30L);
    }

    @Test
    void feeFor15MinSaturday(){

        carDriveIn("2024-01-06T00:00:00", "ABC-8888");
        carDriveOut("2024-01-06T00:15:01","ABC-8888");
        calculated("ABC-8888");
        shouldPay(50L);
    }

    @Test
    void feeFor15MinSunday(){
        carDriveIn("2024-01-07T00:00:00", "ABC-8888");
        carDriveOut("2024-01-07T00:15:01","ABC-8888");
        calculated("ABC-8888");
        shouldPay(50L);
    }

    @Test
    void saturdayAndSundayNoLimit(){
        carDriveIn("2024-01-06T00:00:00", "ABC-8888");
        carDriveOut("2024-01-07T00:00:00","ABC-8888");
        calculated("ABC-8888");
        shouldPay(2400L);
    }

    @Test
    void nationalHoliday15MinsFee(){
        carDriveIn("2024-01-01T00:00:00", "ABC-8888");
        carDriveOut("2024-01-01T00:15:01","ABC-8888");
        calculated("ABC-8888");
        shouldPay(50L);
    }

    @Test
    void anotherCar(){

        carDriveIn("2024-01-06T00:00:00", "ABC-8888");
        carDriveOut("2024-01-06T00:15:01","ABC-8888");
        calculated("VIP-6666");
        shouldPay(0L);
    }


}
