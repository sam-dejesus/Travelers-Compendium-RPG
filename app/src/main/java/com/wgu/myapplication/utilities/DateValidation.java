package com.wgu.myapplication.utilities;

import java.util.Date;

public class DateValidation {

    public static boolean dateCheck(long startDate, long endDate) {
        return startDate < endDate;
    }
    public static boolean isSidequestDateValid(Date selectedDate, Date journeyStartDate, Date journeyEndDate){

        return selectedDate.after(journeyStartDate) && selectedDate.before(journeyEndDate);

    }




}
