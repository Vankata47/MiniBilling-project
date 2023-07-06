package org.example.tests;

import org.example.entity.Price;
import org.example.entity.Reading;
import org.example.entity.User;
import org.example.service.Billing;
import org.example.service.BillingGenerator;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BillingGeneratorTest1 {
    private BillingGenerator billingGenerator;
    private int docnum = 10000;
    private User test = new User(1, "Ivan Ivanov", "1", 2 );

    private List<Reading> readings = Arrays.asList(
            new Reading(ZonedDateTime.parse("2022-11-01T13:23:00+02:00", DateTimeFormatter.ISO_DATE_TIME), 0, "1", "gas"),
            new Reading(ZonedDateTime.parse("2022-11-30T15:20:00+02:00", DateTimeFormatter.ISO_DATE_TIME), 120, "1", "gas")
    );

    private List<Price> prices = Arrays.asList(
            new Price(1L, 2, "gas", LocalDate.parse("2022-10-25"), LocalDate.parse("2022-11-06"), 0.30),
            new Price(2L, 2, "gas", LocalDate.parse("2022-11-07"), LocalDate.parse("2022-11-18"), 0.35),
            new Price(3L, 2, "gas", LocalDate.parse("2022-11-19"), LocalDate.parse("2022-12-04"), 0.32)
    );

    @Before
    public void setUp() {
        billingGenerator = new BillingGenerator();
    }

    @Test
    public void generateBillingTest(){
        Billing billing = billingGenerator.generateBilling(test, docnum, readings, prices);
        System.out.println(billing.toFormattedString());

        assertEquals("2022-11-01T13:23:00+02:00,2022-11-06T23:59:59+02:00,24.00,0.30\n" +
                "2022-11-07T00:00:00+02:00,2022-11-18T23:59:59+02:00,48.00,0.35\n" +
                "2022-11-19T00:00:00+02:00,2022-11-30T15:20:00+02:00,48.00,0.32", billing.toFormattedString());
    }
}