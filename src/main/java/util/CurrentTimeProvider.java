package util;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTimeProvider {
    public static String getCurrentTime() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm a");
            LocalDateTime dateTime = LocalDateTime.now();
           return dateTimeFormatter.format(dateTime);
    }
}
