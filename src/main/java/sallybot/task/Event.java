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

    /**
     * The constructor initializes the Event with the given description, start time and end time.
     *
     * @param description The description of the event task.
     * @param from The start time of the event task as a LocalDateTime object.
     * @param to The end time of the event task as a LocalDateTime object.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Backward-compatible constructor for values loaded from storage.
     * This expects storage ISO-8601 format (e.g. 2019-12-02T18:00).
     *
     * @param description The description of the event task.
     * @param from The start time of the event task as a string in storage format.
     * @param to The end time of the event task as a string in storage format.
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
     * This method returns the start time of the event task as a string formatted for storage.
     *
     * @return The start time of the event task.
     */
    public String getFrom() {
        return DateTimeUtil.formatForStorage(from);
    }

    /**
     * This method returns the end time of the event task as a string formatted for storage.
     *
     * @return The end time of the event task.
     */
    public String getTo() {
        return DateTimeUtil.formatForStorage(to);
    }

}
