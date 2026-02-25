package sallybot.task;

/**
 * Deadline is a type of Task that has a deadline. <br>
 * It stores the description, the status of the task (whether it is done or not) and the deadline of the task.
 */
public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * @return the deadline of the deadline task.
     */
    public String getBy() {
        return by;
    }
}
