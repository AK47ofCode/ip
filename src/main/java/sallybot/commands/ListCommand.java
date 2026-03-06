package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Handles the "list" command, which shows the user a numbered list of all tasks in their task list.
 * This is a non-modifying command that does not require saving after execution.
 */
public class ListCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (tasks.size() == 0) {
            ui.showEmptyList();
            return CommandResult.none();
        }

        ui.showListHeader();
        int i = 1;
        for (Task task : tasks.asUnmodifiableList()) {
            ui.showListItem(i, task.toString());
            i++;
        }
        ui.showListFooter();
        return CommandResult.none();
    }
}

