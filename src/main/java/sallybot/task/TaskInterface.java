package sallybot.task;

/**
 * Interface for Task, which is implemented by Task, ToDo, Deadline and Event. <br>
 * It contains the methods that are common to all types of tasks.
 */
public interface TaskInterface {
    /**
     * Gets the status icon of the task.
     *
     * @return The status icon of the task, which is "X" if the task is done and " " if the task is not done.
     */
    String getStatusIcon();

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    String getDescription();

    /**
     * Marks the task as done.
     */
    void markAsDone();

    /**
     * Marks the task as not done.
     */
    void markAsNotDone();

    /**
     * Returns the status of the task.
     *
     * @return True if the task is done, false otherwise.
     */
    boolean getIsDone();

    /**
     * Defines the string representation of the task.
     *
     * @return The string representation of the task, which is in the format of [status icon] description.
     */
    @Override
    String toString();
}
