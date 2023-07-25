package media.wepg.prototype.es.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final int INITIAL_HOUR = 0;
    private static final int INITIAL_MINUTE = 0;


    public static LocalDateTime convert(String dateInput) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        LocalDate convertedDate = LocalDate.parse(dateInput, formatter);

        LocalTime time = LocalTime.of(INITIAL_HOUR, INITIAL_MINUTE);
        return LocalDateTime.of(convertedDate, time);
    }
}
