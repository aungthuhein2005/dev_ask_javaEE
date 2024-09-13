package com.devask.utilis;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static String formatTimeAgo(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        long diffInMinutes = duration.toMinutes();
        long diffInHours = duration.toHours();

        if (diffInMinutes < 60) {
            return diffInMinutes + " min ago";
        } else if (diffInHours < 24) {
            return diffInHours + " hr ago";
        } else {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            return createdAt.format(dateFormatter);
        }
    }
}

