package sallybot.parser;

import sallybot.exception.SallyException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * DateTimeUtil centralizes all date/time parsing and formatting rules used by commands and tasks.
 * This ensures consistent handling of date/time across the app and makes it easier to maintain and extend in the future.
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

    /**
     * Default constructor is private to prevent instantiation, as this class is intended to be a static utility class.
     * No instances of DateTimeUtil should be created, and all methods should be accessed statically.
     */
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
     * @param raw the raw user input to parse.
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

    /**
     * Parses a day-of-week token from the input, or returns null if no day-of-week is found.
     * The method looks for the first token and checks if it matches a day of week (e.g., "Monday 9pm" or "Friday 4").
     * If a day-of-week token is found, it returns the next occurrence of that day (including today if it matches).
     * If no day-of-week token is found, it returns null, and the caller can default to using today's date.
     *
     * @param raw The raw user input to parse, e.g. "Monday 9pm", "6pm", "Friday 4".
     * @return The parsed LocalDate representing the next occurrence of the specified day of week (including today if it matches),
     * or null if no day-of-week token is found.
     */
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

    /**
     * Parses a time token from the input, or returns null if no time is found.
     * The caller can default to midnight if needed.
     * The method looks for the first time-like token in the input, so it can handle inputs like "Monday 9pm" or "Friday 4".
     *
     * @param raw The raw user input to parse, e.g. "Monday 9pm", "6pm", "Friday 4".
     * @return The parsed LocalTime, or null if no time token is found (e.g., "Monday" or "Friday" without a time).
     * If the time is not specified, the caller can default to midnight.
     */
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

    /**
     * Tries to parse a single token as a time. Returns null if the token is not a valid time.
     * The parsing is flexible to accommodate common user inputs, but it is not locale-dependent. <p>
     * Supported formats: <br>
     * - 4-digit 24h time, e.g. "1800" -> 18:00 <br>
     * - am/pm, e.g. "6pm" -> 18:00, "9am" -> 09:00 <br>
     * - Plain hour, e.g. "4" -> 04:00 (assumed 24h) </p>
     *
     * @param token The token to parse, e.g. "9pm", "6pm", "4", "1800".
     * @return The parsed LocalTime, or null if the token is not a valid time.
     */
    private static LocalTime tryParseTimeToken(String token) {
        String t = token.trim().toLowerCase(Locale.ROOT);
        if (t.isEmpty()) {
            return null;
        }

        // 4-digit 24h time, e.g. 1800
        if (t.matches("\\d{4}")) {
            int hour = Integer.parseInt(t.substring(0, 2));
            int min = Integer.parseInt(t.substring(2, 4));
            if (hour >= 0 && hour <= 23 && min >= 0 && min <= 59) {
                return LocalTime.of(hour, min);
            }
            return null;
        }

        // am/pm, e.g. 6pm, 9am
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

        // Plain hour, e.g. "4" (assume 24h)
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
     * This is a strict format used for storage, and it is not locale-dependent.
     * It should not be used for parsing user input.
     *
     * @param raw The raw string to parse, expected to be in ISO-8601 format (e.g., "2019-12-02T18:00").
     * @return The parsed LocalDateTime.
     * @throws DateTimeParseException If the input cannot be parsed as a LocalDateTime in ISO-8601 format.
     */
    public static LocalDateTime parseStorageDateTime(String raw) throws DateTimeParseException {
        return LocalDateTime.parse(raw.trim());
    }

    /**
     * Formats a LocalDateTime for user display.
     * If the time is 00:00, only the date is shown.
     * Otherwise, both date and time are shown in a user-friendly format (e.g., "Dec 02 2019" or "Dec 02 2019 6:00PM").
     *
     * @param dateTime The LocalDateTime to format for display.
     * @return A user-friendly string representation of the date/time, or an empty string if the input is null.
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
     * Formats a LocalDateTime for storage.
     * Uses ISO-8601 so it can be reliably parsed across locales.
     *
     * @param dateTime The LocalDateTime to format for storage.
     *                 If the input is null, an empty string is returned to indicate no date/time.
     * @return A string representation of the date/time in ISO-8601 format (e.g., "2019-12-02T18:00"),
     * or an empty string if the input is null.
     */
    public static String formatForStorage(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.toString();
    }
}
