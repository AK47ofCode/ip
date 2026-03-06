package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * A command that shows a random Kiriko voiceline. <br>
 * This is a non-modifying command that does not require saving after execution.
 */
public class KirikoCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showKirikoVoiceline();
        return CommandResult.none();
    }
}
