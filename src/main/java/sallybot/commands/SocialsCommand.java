package sallybot.commands;

import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * Command to show the user the socials of the namesake of the bot, Sally Amaki. <br>
 * This is a non-modifying command that does not require saving after execution.
 */
public class SocialsCommand extends Command {
    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showSocials();
        return CommandResult.none();
    }
}
