package sallybot.task;

/**
 * Creates an object that represents a Task.<br>
 * It stores the description and the status of the task (whether it is done or not).
 */
public class Task implements TaskInterface {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    @Override
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void markAsDone() {
        isDone = true;
    }

    @Override
    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public boolean getIsDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
