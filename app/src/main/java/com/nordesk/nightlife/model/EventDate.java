package com.nordesk.nightlife.model;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

/**
 * Class to represent the time and date of an event.
 * The value is stored in long format, counting seconds from 1970
 * The functions it needs to have is to convert from
 */
public class EventDate {

    // Save into epoch seconds format
    // Convert into

    private LocalDateTime dateTime;

    public static EventDate fromString(String date) {
        return new EventDate(LocalDateTime.parse(date));
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public EventDate(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static EventDate generateDate(int day, int month, int year, int hour, int minute) {
        LocalDateTime datetime = LocalDateTime.of(year, month, day, hour, minute);
        return new EventDate(datetime);
    }
}