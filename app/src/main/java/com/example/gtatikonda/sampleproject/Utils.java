package com.example.gtatikonda.sampleproject;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by gtatikonda on 1/18/2019.
 */

public class Utils {
    public static DateFormat dateformat_US = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
     static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
     static  SimpleDateFormat requiredFormat=new SimpleDateFormat("MMM dd, yyyy");
     static  SimpleDateFormat requiredTimeFormat=new SimpleDateFormat("hh:mm aaa");
     private static String mDateString;
     private static  boolean IS_FORM_EDITED = true;

    static void getDateFromDatePicker(Context context, final String dateString, final TextView textView,final DatePickerButtonClickListener datePickerButtonClickListener){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(Integer.valueOf(dateString.split("/")[2]),Integer.valueOf(dateString.split("/")[1])-1,Integer.valueOf(dateString.split("/")[0]));


        DatePickerDialog datePickerDialog= new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                Calendar calander2 = Calendar.getInstance();
                calander2.setTimeInMillis(0);
                calander2.set(year, month, date);
                Date selectedDate = calander2.getTime();
                String StringDateformat_US = requiredFormat.format(selectedDate);
                textView.setText(StringDateformat_US);
                mDateString= simpleDateFormat.format(selectedDate);
                if(datePickerButtonClickListener!=null){
                    datePickerButtonClickListener.onDialogButtonClick(true);
                }
            }
        }, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

        datePickerDialog.show();


    }

    static void  getTimeFromPicker(Context context, final TextView textView){
    String[] a_strings = requiredFormat.format(Calendar.getInstance().getTime()).split(":");
          final String[] s = {""};
        if (!TextUtils.isEmpty(textView.getText().toString().trim())) {
        a_strings = textView.getText().toString().split(":");

    }
    int hour=Integer.valueOf(a_strings[0]);
    String AMPM=a_strings[1].split(" ")[1];
    if(AMPM.equals("PM")){
        hour=hour+12;
    }
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            String AmPm="AM";
            if(hourOfDay>11){
                AmPm="PM";//PM
                hourOfDay=hourOfDay-12;
            }
            else{
                AmPm="AM";//AM
            }

            String currentTime=String.format("%02d:%02d %s",hourOfDay,minute,AmPm);
            if (!textView.getText().toString().trim().equals(currentTime)) {
                Utils.IS_FORM_EDITED = true;
            }
            textView.setText(currentTime);
        }
    }, hour, Integer.valueOf(a_strings[1].split(" ")[0]), false).show();

    }
    public interface DatePickerButtonClickListener {
        void onDialogButtonClick(boolean isPositiveButton);
    }

     static String getDateString(){
        return mDateString;
    }

}
