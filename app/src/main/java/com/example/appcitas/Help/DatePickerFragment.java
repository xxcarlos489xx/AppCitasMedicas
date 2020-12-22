package com.example.appcitas.Help;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment  {
    private DatePickerDialog.OnDateSetListener listener;
    private static String minDate="";
    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // Create a new instance of DatePickerDialog and return it
        Date fecha = new Date();
        //System.out.println("La fecha de hoy es: "+fecha.toString());
        Date newfecha = addDays(fecha, 1);
        //System.out.println("La fecha después de sumar "+1+" días: "+newfecha.toString());
        minDate = sdf.format(newfecha);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), listener, year, month, day);
        //Define una fecha minima.
        //Esto deshabilita fechas anteriores.
        datePicker.getDatePicker().setMinDate(convertDateToMillis(minDate));

        return datePicker;
    }

    //givenDateString must be defined in format dd/MM/yyyy
    private Long convertDateToMillis(String givenDateString){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long timeInMilliseconds = System.currentTimeMillis() - 1000;
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milliseconds: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeInMilliseconds;
    }

    public static Date addDays(Date fecha, int dias) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, dias);
        return cal.getTime();
    }

}
