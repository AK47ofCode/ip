package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

public interface CommandInterface {
    /**
     * Executes the command.
     *
     * @return the result of the execution (e.g., whether to exit, whether to save).
     */
    CommandResult execute(TaskList tasks, Ui ui, Storage storage);

    /**
     * Indicates whether this command is an exit command.
     * This is used by the main application loop to determine when to terminate.
     *
     * @return true if this command is an exit command, false otherwise.
     */
    boolean isExit();
}
