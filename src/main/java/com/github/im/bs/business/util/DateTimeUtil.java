/*
 * 3/25/20, 5:00 PM
 * ivan
 */

package com.github.im.bs.business.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

@Component
public class DateTimeUtil {
    public LocalDateTime monthStartTime(Month month) {
        LocalDate monthStartDate = LocalDate.of(LocalDateTime.now().getYear(), month, 1);
        return monthStartDate.atStartOfDay();
    }

    public LocalDateTime monthEndTime(Month month) {
        LocalDate monthEndDate = LocalDate.of(LocalDateTime.now().getYear(), month.plus(1), 1).minusDays(1);
        return monthEndDate.atTime(LocalTime.MAX);
    }
}
