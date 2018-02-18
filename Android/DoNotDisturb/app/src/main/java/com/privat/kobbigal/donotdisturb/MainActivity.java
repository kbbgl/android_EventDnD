package com.privat.kobbigal.donotdisturb;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.security.Permission;
import java.util.Date;
import java.util.List;

import me.everything.providers.android.calendar.Calendar;
import me.everything.providers.android.calendar.CalendarProvider;

public class MainActivity extends AppCompatActivity {

//    private final int REQUEST_PERMISSIONS = 1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, 1);

        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {

                ContentResolver contentResolver = getContentResolver();
                Uri eventsURI = CalendarContract.Events.CONTENT_URI;
                Uri calendarsURI = CalendarContract.Calendars.CONTENT_URI;

                // Calendars
//                Cursor cursor = getContentResolver().query(CalendarContract.Calendars.CONTENT_URI, null, null, null);
//
//                while (cursor.moveToNext()){
//
//                    int idIndex = cursor.getColumnIndex(CalendarContract.Calendars._ID);
//                    int idIndexValue = cursor.getInt(idIndex);
//
//                    int calendarNameIndex = cursor.getColumnIndex(CalendarContract.Calendars.NAME);
//                    String calendarNameValue = cursor.getString(calendarNameIndex);
//
//                    Log.i("Calendar Details", calendarNameValue);
//
//                }
//                cursor.close();


                // Events
                long systemTime = System.currentTimeMillis();
                Date time = new Date(systemTime);

                /*
                Filter:
                // future
                // non-all day
                // confirmed
                */
                String selection = "(" +
                        "( " + CalendarContract.Events.DTSTART + " >= ? )" +
                        "AND " +
                        "( " + CalendarContract.Events.ALL_DAY + " != 1 )" +
                        "AND " +
                        "( " + CalendarContract.Events.STATUS_CONFIRMED + " == 1 )" +
                                    ")";


                String selectionArgs[] = new String[] {String.valueOf(systemTime)};

                Cursor cursor = contentResolver.query(eventsURI, null, selection, selectionArgs, CalendarContract.Events.DTSTART + " ASC");

                assert cursor != null;
                while (cursor.moveToNext()) {

                    int titleIndex = cursor.getColumnIndex(CalendarContract.Events.TITLE);
                    String titleValue = cursor.getString(titleIndex);

                    int descriptionIndex = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
                    String descriptionValue = cursor.getString(descriptionIndex);

                    int startTimeIndex = cursor.getColumnIndex(CalendarContract.Events.DTSTART);
                    long startTimeValue = Long.parseLong(cursor.getString(startTimeIndex));
                    Date eventStartDate = new Date(startTimeValue);

                    Log.i("Event Details", titleValue + ", " + descriptionValue + ", " + eventStartDate.toString());
                }

                cursor.close();
                Log.i("systemTime", time.toString());
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()){
            if (cursor != null){


                int id_1 = cursor.getColumnIndex(CalendarContract.Events._ID);
                int id_2 = cursor.getColumnIndex(CalendarContract.Events.TITLE);
                int id_3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
                int id_4 = cursor.getColumnIndex(CalendarContract.Events.DTSTART);

                String idValue = cursor.getString(id_1);
                String titleValue = cursor.getString(id_2);
                String descriptionValue = cursor.getString(id_3);
                long startTimeValue = Long.parseLong(cursor.getString(id_4));

                Log.i("Event Details", idValue + ", " + titleValue + ", " + descriptionValue + ", " + startTimeValue);
            }

            else {
                Toast.makeText(this, "Event not present", Toast.LENGTH_SHORT).show();
                cursor.close();
            }
        }


    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void printCalendars() {
//
//        CalendarProvider provider = new CalendarProvider(getApplicationContext());
//        List<Calendar> calendars = provider.getCalendars().getList();
//
//        Cursor cursor = getApplicationContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, null, null, null);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                int id_1 = cursor.getColumnIndex(CalendarContract.Events._ID);
//                int id_2 = cursor.getColumnIndex(CalendarContract.Events.TITLE);
//                int id_3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
//                int id_4 = cursor.getColumnIndex(CalendarContract.Events.DTSTART);
//
//                String idValue = cursor.getString(id_1);
//                String titleValue = cursor.getString(id_2);
//                String descriptionValue = cursor.getString(id_3);
//                String startTimeValue = cursor.getString(id_4);
//
//                Toast.makeText(this, idValue + ", " + titleValue + ", " + descriptionValue + ", " + startTimeValue, Toast.LENGTH_SHORT).show();
//            }
//        }
//        else {
//                    Toast.makeText(this, "Event not present", Toast.LENGTH_SHORT).show();
//                    cursor.close();
//        }
//    }
}

