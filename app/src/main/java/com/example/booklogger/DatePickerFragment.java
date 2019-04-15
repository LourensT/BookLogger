package com.example.booklogger;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

import com.example.sqltut.myDBHandler;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    int id;
    int page;
    dateInterface callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        callback = (dateInterface) getContext();

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public interface dateInterface{
        public void onDateSelected(String date, int id, int page);
    }

    public void setValues(int id, int page) {
        this.id = id;
        this.page = page;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String fullDate = String.valueOf(year) + "-" + String.valueOf(month)+ '-' + String.valueOf(day);
        callback.onDateSelected(fullDate, id, page);


    }
}
