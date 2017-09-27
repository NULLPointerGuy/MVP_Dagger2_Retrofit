package com.karthik.corecommon.Views;

/**
 * Created by karthikrk on 27/09/17.
 */

public interface DateTimeSelectionView {
    void showDatePickerDialog();
    void showTimePickerDialog();
    void setDefaultDateAndTime(String formattedDate, String formattedTime);
    long getSetTimeInMilli();
}
