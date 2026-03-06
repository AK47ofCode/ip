package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Represents a user command.
 * Each command encapsulates the logic needed to carry out a user request.
 */
public abstract class Command {

    /**
     * Executes the command.
     *
     * @return The result of the execution (e.g., whether to exit, whether to save).
     */
    public abstract CommandResult execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Determines whether the command requests the app to exit or not.
     * By default, commands do not request the app to exit.
     * Override this method in a subclass if the command should request an exit.
     *
     * @return True if this command is an exit command, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
