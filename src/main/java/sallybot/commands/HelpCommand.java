package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

public class HelpCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showHelp();
        return CommandResult.none();
    }
}

