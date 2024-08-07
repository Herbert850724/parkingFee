package com.tdd;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailySession {
    private final LocalDate today;
    private final Duration todayDuration;
}
