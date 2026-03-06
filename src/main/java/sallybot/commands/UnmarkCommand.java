package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Handles the "unmark" command, which marks a task as not done. <br>
 * This is a modifying command that requires saving after execution. <br>
 * The command format is "unmark [index]", where [index] is the 1-based index of the task to unmark in the task list. <br>
 * If the index is missing or not a valid number, an error message will be shown to the user,
 * and the command will still return a save result to ensure that any other changes are preserved.
 */
public class UnmarkCommand extends Command {
    private final String[] args;

    /**
     * Creates an UnmarkCommand with the given arguments.
     * The args array is expected to contain at least two elements: the command "unmark" and the index of the task to unmark.
     *
     * @param args The arguments passed to the command, where args[0] is expected to be "unmark" and args[1] should be the index of the task to unmark.
     */
    public UnmarkCommand(String[] args) {
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (args.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to unmark.");
        }
        try {
            int index = Integer.parseInt(args[1]);
            Task updated = tasks.unmark(index);
            ui.showMarkedTask(updated.toString(), false);
        } catch (NumberFormatException e) {
            ui.showError("\t すみません🙇‍♀️ The parameter must be a number!");
        }
        // Preserve existing behavior: save even after NumberFormatException.
        return CommandResult.save();
    }
}

