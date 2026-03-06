package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Represents an invalid/unknown command. <br>
 * This is a non-modifying command that does not require saving after execution, but it will throw an exception to indicate the error.
 * The error message will be shown to the user, and no further commands will be processed until a valid command is entered.
 */
public class UnknownCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
    }
}

