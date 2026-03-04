package sallybot.task;

import sallybot.parser.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Deadline is a type of Task that has a deadline. <br>
 * It stores the description, the status of the task (whether it is done or not) and the deadline of the task.
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Backward-compatible constructor for callers still providing a raw string.
     * This expects storage ISO-8601 format (e.g. 2019-12-02T18:00).
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = DateTimeUtil.parseStorageDateTime(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by) + ")";
    }

    /**
     * @return the deadline of the deadline task.
     */
    public String getBy() {
        return DateTimeUtil.formatForStorage(by);
    }

    public LocalDateTime getByDateTime() {
        return by;
    }
}
