package sallybot.parser;

import sallybot.exception.SallyException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * DateTimeUtil centralizes all date/time parsing and formatting rules used by commands and tasks.
 */
public final class DateTimeUtil {
    // Preferred strict formats (e.g., 2019-12-02 or 2019-12-02 1800). These are unambiguous and locale-independent.
    private static final DateTimeFormatter USER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter USER_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    // d/M/yyyy forms (accepted by assignment examples). Interprets 2/12/2019 as 2 Dec 2019.
    private static final DateTimeFormatter USER_DMY_DATE = DateTimeFormatter.ofPattern("d/M/uuuu");
    private static final DateTimeFormatter USER_DMY_DATE_TIME = DateTimeFormatter.ofPattern("d/M/uuuu HHmm");

    // Display formats (e.g., Dec 02 2019 or Dec 02 2019 6:00PM). These are more user-friendly but not used for parsing.
    private static final DateTimeFormatter DISPLAY_DATE = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private static final DateTimeFormatter DISPLAY_DATE_TIME = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma");

    private DateTimeUtil() {
        // utility class
    }

    /**
     * Parses user input into a {@link LocalDateTime}.<p>
     *
     * Supported inputs:
     * <ul>
     *     <li><b>yyyy-MM-dd</b> (date only; time defaults to 00:00)</li>
     *     <li><b>yyyy-MM-dd HHmm</b> (date + 24h time, e.g. 2019-12-02 1800)</li>
     *     <li><b>Legacy flexible inputs</b> used by the original bot (best-effort):
     *         <ul>
     *             <li>"Monday 9pm" (next Monday at 21:00)</li>
     *             <li>"Friday 4" (next Friday at 16:00)</li>
     *             <li>"6pm" (today at 18:00)</li>
     *         </ul>
     *     </li>
     * </ul>
     *
     * @param raw the raw user input to parse
     * @return the parsed LocalDateTime.
     * @throws SallyException if the value cannot be parsed.
     */
    public static LocalDateTime parseUserDateTime(String raw) throws SallyException {
        if (raw == null || raw.trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide a date/time.");
        }

        // Normalize: trim and collapse whitespace; also strip common wrapping punctuation
        String value = raw.trim().replaceAll("\\s+", " ")
                .replaceAll("^[,;]+|[,;]+$", "");

        // Preferred strict formats
        try {
            if (value.matches("\\d{4}-\\d{2}-\\d{2}$")) {
                LocalDate date = LocalDate.parse(value, USER_DATE);
                return date.atStartOfDay();
            }
            if (value.matches("\\d{4}-\\d{2}-\\d{2}\\s+\\d{4}$")) {
                return LocalDateTime.parse(value, USER_DATE_TIME);
            }

            if (value.matches("\\d{1,2}/\\d{1,2}/\\d{4}$")) {
                LocalDate date = LocalDate.parse(value, USER_DMY_DATE);
                return date.atStartOfDay();
            }
            if (value.matches("\\d{1,2}/\\d{1,2}/\\d{4}\\s+\\d{4}$")) {
                return LocalDateTime.parse(value, USER_DMY_DATE_TIME);
            }
        } catch (DateTimeParseException ignored) {
            // Fall through, by right shouldn't happen due to regex checks
            // But just in case of weird inputs like "2019-13-40"
        }

        // Legacy parsing uses the normalized value too
        LocalDate baseDate = parseDayOfWeekOrToday(value);
        LocalTime time = parseTimeOrMidnight(value);

        if (baseDate == null && time == null) {
            throw new SallyException("""
                    \t すみません🙇‍♀️ Invalid date/time. Please use yyyy-MM-dd (e.g., 2019-10-15)
                    \t or yyyy-MM-dd HHmm (e.g., 2019-12-02 1800), or d/M/yyyy (e.g., 2/12/2019)
                    \t optionally with HHmm, or phrases like 'Monday 9pm'.""");
        }

        LocalDate date = baseDate != null ? baseDate : LocalDate.now();
        LocalTime finalTime = time != null ? time : LocalTime.MIDNIGHT;
        return date.atTime(finalTime);
    }

    private static LocalDate parseDayOfWeekOrToday(String raw) {
        // Extract an initial day-of-week token if present (e.g., "Monday 9pm", "Friday 4")
        String[] tokens = raw.trim().toLowerCase(Locale.ROOT).split("\\s+");
        if (tokens.length == 0) {
            return null;
        }

        DayOfWeek dow = switch (tokens[0]) {
        case "mon", "monday" -> DayOfWeek.MONDAY;
        case "tue", "tues", "tuesday" -> DayOfWeek.TUESDAY;
        case "wed", "wednesday" -> DayOfWeek.WEDNESDAY;
        case "thu", "thurs", "thursday" -> DayOfWeek.THURSDAY;
        case "fri", "friday" -> DayOfWeek.FRIDAY;
        case "sat", "saturday" -> DayOfWeek.SATURDAY;
        case "sun", "sunday" -> DayOfWeek.SUNDAY;
        default -> null;
        };

        if (dow == null) {
            return null;
        }

        // Choose the next occurrence of that day (including today if it matches)
        LocalDate today = LocalDate.now();
        return today.with(TemporalAdjusters.nextOrSame(dow));
    }

    private static LocalTime parseTimeOrMidnight(String raw) {
        // Look for a simple time token (e.g., 9pm, 6pm, 4, 1800)
        // Check each whitespace token and parse the first time-like one
        String[] tokens = raw.trim().toLowerCase(Locale.ROOT).split("\\s+");
        for (String token : tokens) {
            LocalTime t = tryParseTimeToken(token);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    private static LocalTime tryParseTimeToken(String token) {
        String t = token.trim().toLowerCase(Locale.ROOT);
        if (t.isEmpty()) {
            return null;
        }

        // 4-digit 24h time like 1800
        if (t.matches("\\d{4}")) {
            int hour = Integer.parseInt(t.substring(0, 2));
            int min = Integer.parseInt(t.substring(2, 4));
            if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
                return LocalTime.of(hour, min);
            }
            return null;
        }

        // am/pm like 6pm, 9am
        if (t.matches("\\d{1,2}(am|pm)")) {
            int hour = Integer.parseInt(t.replaceAll("(am|pm)$", ""));
            boolean pm = t.endsWith("pm");
            if (hour < 1 || hour > 12) {
                return null;
            }
            int h24 = hour % 12;
            if (pm) {
                h24 += 12;
            }
            return LocalTime.of(h24, 0);
        }

        // Plain hour like "4" (assume 24h)
        if (t.matches("\\d{1,2}")) {
            int hour = Integer.parseInt(t);
            if (hour >= 0 && hour <= 23) {
                return LocalTime.of(hour, 0);
            }
            return null;
        }

        return null;
    }

    /**
     * Parses stored ISO-8601 values written by LocalDateTime#toString(), e.g. 2019-12-02T18:00.
     */
    public static LocalDateTime parseStorageDateTime(String raw) throws DateTimeParseException {
        return LocalDateTime.parse(raw.trim());
    }

    /**
     * Formats a LocalDateTime for user display.
     * If the time is 00:00, only the date is shown.
     */
    public static String formatForDisplay(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.toLocalDate().format(DISPLAY_DATE);
        }
        return dateTime.format(DISPLAY_DATE_TIME);
    }

    /**
     * Formats a LocalDateTime for storage. Uses ISO-8601 so it can be reliably parsed across locales.
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.toString();
    }
}
