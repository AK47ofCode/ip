package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Handles the "bye" command, which exits the application. <br>
 * This is a non-modifying command that does not require saving after execution. <br>
 * The application will exit after this command is executed, so no further commands will be processed.
 */
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

