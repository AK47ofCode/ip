package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Handles the "delete" command, which deletes a task from the task list based on its index. <br>
 * This is a modifying command that requires saving after execution. <br>
 * The command format is "delete [index]", where [index] is the 1-based index of the task to delete in the task list. <br>
 * If the index is missing or not a valid number, an error message will be shown to the user,
 * and the command will still return a save result to ensure that any other changes are preserved. <br>
 * Example usage: "delete 3" will delete the third task in the list.
 */
public class DeleteCommand extends Command {
    private final String[] args;

    public DeleteCommand(String[] args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (args.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to delete.");
        }
        try {
            int index = Integer.parseInt(args[1]);
            Task deleted = tasks.delete(index);
            ui.showDeletedTask(deleted.toString(), tasks.size());
        } catch (NumberFormatException e) {
            ui.showError("\t すみません🙇‍♀️ The parameter must be a number!");
        }
        // Preserve existing behavior: save even after NumberFormatException.
        return CommandResult.save();
    }
}

