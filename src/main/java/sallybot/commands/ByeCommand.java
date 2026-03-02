package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

public class ByeCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        return CommandResult.exit();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

