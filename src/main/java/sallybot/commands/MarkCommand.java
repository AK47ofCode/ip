package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Handles the "mark" command, which marks a task as completed based on its index in the task list. <br>
 * This is a modifying command that requires saving after execution. <br>
 * The command expects a single parameter: the index of the task to mark (1-based index). <br>
 * If the index is missing or not a valid number, an error message will be shown to the user,
 * and the command will still return a save result to ensure that any other changes are preserved. <br>
 * Example usage: "mark 2" will mark the second task in the list as completed.
 */
public class MarkCommand extends Command {
    private final String[] args;

    /**
     * Creates a MarkCommand with the given arguments.
     * The args array is expected to contain at least two elements: the command "mark" and the index of the task to mark.
     *
     * @param args The arguments passed to the command, where args[0] is expected to be "mark" and args[1] should be the index of the task to mark.
     */
    public MarkCommand(String[] args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (args.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to mark.");
        }
        try {
            int index = Integer.parseInt(args[1]);
            Task updated = tasks.mark(index);
            ui.showMarkedTask(updated.toString(), true);
        } catch (NumberFormatException e) {
            ui.showError("\t すみません🙇‍♀️ The parameter must be a number!");
        }
        // Preserve existing behavior: save even after NumberFormatException.
        return CommandResult.save();
    }
}

