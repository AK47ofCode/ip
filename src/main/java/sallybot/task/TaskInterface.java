package sallybot.task;

public interface TaskInterface {
    String getStatusIcon();

    String getDescription();

    void markAsDone();

    void markAsNotDone();

    boolean getIsDone();

    @Override
    String toString();
}
