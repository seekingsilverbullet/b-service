/*
 * 3/26/20, 2:16 PM
 * ivan
 */

package com.github.im.bs.business.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DateTimeUtilTest {
    @Autowired
    private DateTimeUtil dateTimeUtil;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now();
    }

    @Test
    void monthStartTime() {
        LocalDateTime monthStartTime = dateTimeUtil.monthStartTime(now.getMonth());

        assertEquals(now.getMonth(), monthStartTime.getMonth());
        assertEquals(1, monthStartTime.getDayOfMonth());
        assertEquals(0, monthStartTime.getHour());
        assertEquals(0, monthStartTime.getMinute());
        assertEquals(0, monthStartTime.getSecond());
    }

    @Test
    void monthEndTime() {
        int lastDayOfMonth = LocalDate.of(LocalDateTime.now().getYear(),
                now.getMonth().plus(1), 1).minusDays(1).getDayOfMonth();

        LocalDateTime monthEndTime = dateTimeUtil.monthEndTime(now.getMonth());

        assertEquals(now.getMonth(), monthEndTime.getMonth());
        assertEquals(lastDayOfMonth, monthEndTime.getDayOfMonth());
        assertEquals(23, monthEndTime.getHour());
        assertEquals(59, monthEndTime.getMinute());
        assertEquals(59, monthEndTime.getSecond());
    }
}