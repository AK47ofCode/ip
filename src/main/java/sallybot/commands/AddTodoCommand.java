package sallybot.commands;

import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.task.ToDo;
import sallybot.ui.Ui;

/**
 * Handles the "todo" command, which adds a new ToDo task to the user's task list. <br>
 * This is a modifying command that requires saving after execution. <br>
 * The command format is: "todo <description>" <br>
 * The description is required and cannot be empty. <br>
 * Example: "todo read book" will add a ToDo task with the description "read book".
 */
public class AddTodoCommand extends Command {
    private final String fullCommand;
    private final String[] args;

    /**
     * Creates an AddTodoCommand with the full command string and its arguments.
     * The full command string is needed to extract the description, which may contain spaces and cannot be easily reconstructed from the args array.
     * The args array contains the individual components of the command, but the description may be a single string that includes multiple args.
     *
     * @param fullCommand The full command string entered by the user, e.g. "todo read book".
     * @param args The arguments extracted from the command, e.g. ["read", "book"].
     */
    public AddTodoCommand(String fullCommand, String[] args) {
        this.fullCommand = fullCommand;
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        String desc = Parser.parseTodoDescription(fullCommand, args);
        tasks.add(new ToDo(desc));
        ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
        return CommandResult.save();
    }
}

