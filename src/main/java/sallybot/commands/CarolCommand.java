package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * A command that shows the character Carol Olston saying "Hey. Stop doing that." <br>
 * This is a non-modifying command that does not require saving after execution.
 * The message is intended to be humorous and lighthearted, and is not meant to be taken seriously.
 */
public class CarolCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showStopDoingThat();
        return CommandResult.none();
    }
}
