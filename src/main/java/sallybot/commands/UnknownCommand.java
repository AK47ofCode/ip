package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Represents an invalid/unknown command.
 * Parser generally throws before creating this, but it can be useful as a fallback.
 */
public class UnknownCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
    }
}

