package com.tdd;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class ParkingApplicationTests {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long actual;
    private CalculateParkingFeeService pfc;
    private ParkingSessionRepository parkingSessionRepository = new ParkingSessionRepositoryImpl();


    @BeforeEach
    void setUp(){
        pfc = new CalculateParkingFeeService(new PriceBookRepositoryImpl(new PriceBook()),parkingSessionRepository);
    }
    private void parkingStartAt(String start){
        startTime = LocalDateTime.parse(start);
        parkingSessionRepository.save(new ParkingSession(startTime,null));
    }
    private void parkingEndAt(String end){
        endTime = LocalDateTime.parse(end);
        ParkingSession parkingSession = parkingSessionRepository.find();
        parkingSession.setEnd(endTime);
        parkingSessionRepository.save(parkingSession);
    }

    private void calculated(){
        actual = pfc.calculate();
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
        parkingStartAt("2024-01-02T00:01:00");
        parkingEndAt("2024-01-02T01:01:00");
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

    @Test
    void partialDayAndWholeDay(){
        parkingStartAt("2024-01-02T23:50:00");
        parkingEndAt("2024-01-04T00:00:00");
        calculated();
        shouldPay(30L+150L);
    }

    @Test
    void wholeDayAndPartialDay(){
        parkingStartAt("2024-01-02T00:00:00");
        parkingEndAt("2024-01-03T00:10:00");
        calculated();
        shouldPay(150L+30L);
    }

    @Test
    void feeFor15MinSaturday(){

        parkingStartAt("2024-01-06T00:00:00");
        parkingEndAt("2024-01-06T00:15:01");
        calculated();
        shouldPay(50L);
    }

    @Test
    void feeFor15MinSunday(){
        parkingStartAt("2024-01-07T00:00:00");
        parkingEndAt("2024-01-07T00:15:01");
        calculated();
        shouldPay(50L);
    }

    @Test
    void saturdayAndSundayNoLimit(){
        parkingStartAt("2024-01-06T00:00:00");
        parkingEndAt("2024-01-07T00:00:00");
        calculated();
        shouldPay(2400L);
    }

    @Test
    void nationalHoliday15MinsFee(){
        parkingStartAt("2024-01-01T00:00:00");
        parkingEndAt("2024-01-01T00:15:01");
        calculated();
        shouldPay(50L);
    }

    @Test
    void ddddddd(){
        LocalDate today = LocalDate.parse("2024-07-24");
        boolean contains = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(today.getDayOfWeek());
        System.out.println(contains);
    }

}
