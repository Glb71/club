package com.snapp.snapppay.club.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {

    public static Timestamp getCurrentDateTime() {
        return Timestamp.from(Instant.now());
    }

    public static Timestamp addSeconds(Timestamp timestamp, Long seconds) {
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(ZoneId.systemDefault());
        return Timestamp.from(zonedDateTime.plusSeconds(seconds).toInstant());
    }

    public static Timestamp addSecondsToCurrentDateTime(Long seconds) {
        return addSeconds(getCurrentDateTime(), seconds);
    }

}
