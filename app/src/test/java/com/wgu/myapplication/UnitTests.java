package com.wgu.myapplication;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.wgu.myapplication.utilities.DateValidation;

import org.junit.Test;

import java.util.Date;

public class UnitTests {

    @Test
    public void invalidDates() {
        long startDate = 1704196800000L;
        long endDate =1704110400000L;

        boolean result = DateValidation.dateCheck(startDate, endDate);

        assertFalse("The dates should be invalid because the end date is before the start date. ",result);
    }

    @Test
    public void validDates() {
        long startDate =1704110400000L;
        long endDate = 1704196800000L;

        boolean result = DateValidation.dateCheck(startDate, endDate);

        assertTrue("The dates should be valid because the end date is before the start date. ",result);
    }

    @Test
    public void invalidSidequestDate() {
        Date journeyStartDate = new Date(2024, 1, 1);
        Date journeyEndDate = new Date(2024, 1, 10);
        Date selectedDate = new Date(2024, 1, 11);

        boolean result = DateValidation.isSidequestDateValid(selectedDate, journeyStartDate, journeyEndDate);

        assertFalse("The selected date should be invalid because it's after the journey's end date. ", result);
    }

    @Test
    public void validSidequestDate() {
        Date journeyStartDate = new Date(2024, 1, 1);
        Date journeyEndDate = new Date(2024, 1, 10);
        Date selectedDate = new Date(2024, 1, 9);

        boolean result = DateValidation.isSidequestDateValid(selectedDate, journeyStartDate, journeyEndDate);

        assertTrue("The selected date should be valid because it's after the journey's end date. ", result);
    }
}
