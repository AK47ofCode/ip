package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

public class ListCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
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

