package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Handles the "help" command, which shows the user a list of available commands and their usage.
 * This is a non-modifying command that does not require saving after execution.
 */
public class HelpCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showHelp();
        return CommandResult.none();
    }
}

