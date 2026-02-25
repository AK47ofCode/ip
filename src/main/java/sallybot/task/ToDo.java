package sallybot.task;

/**
 * ToDo is a type of Task that does not have a time associated with it. <br>
 * It stores the description and the status of the task (whether it is done or not).
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString(){
        return "[T]" + super.toString();
    }
}
