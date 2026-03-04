package sallybot.task;

import sallybot.parser.DateTimeUtil;

import java.time.LocalDateTime;

/**
 * Event is a type of Task that has a start time and an end time. <br>
 * It stores the description, the status of the task (whether it is done or not),
 * the start time and the end time of the event.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Backward-compatible constructor for values loaded from storage.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = DateTimeUtil.parseStorageDateTime(from);
        this.to = DateTimeUtil.parseStorageDateTime(to);
    }

    @Override
    public String toString(){
        return "[E]" + super.toString()
                + " (from: " + DateTimeUtil.formatForDisplay(from)
                + ", to: " + DateTimeUtil.formatForDisplay(to) + ")";
    }

    /**
     * @return the start time of the event task.
     */
    public String getFrom() {
        return DateTimeUtil.formatForStorage(from);
    }

    /**
     * @return the end time of the event task.
     */
    public String getTo() {
        return DateTimeUtil.formatForStorage(to);
    }

    public LocalDateTime getFromDateTime() {
        return from;
    }

    public LocalDateTime getToDateTime() {
        return to;
    }
}
