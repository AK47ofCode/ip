package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

public class MarkCommand extends Command {
    private final String[] args;

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

