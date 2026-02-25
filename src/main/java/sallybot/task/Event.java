package sallybot.task;

/**
 * Event is a type of Task that has a start time and an end time. <br>
 * It stores the description, the status of the task (whether it is done or not),
 * the start time and the end time of the event.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString(){
        return "[E]" + super.toString() + " (from: " + from + ", to: " + to + ")";
    }

    /**
     * @return the start time of the event task.
     */
    public String getFrom() {
        return from;
    }

    /**
     * @return the end time of the event task.
     */
    public String getTo() {
        return to;
    }
}
