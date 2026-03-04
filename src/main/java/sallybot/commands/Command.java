package sallybot.commands;

/**
 * Represents a user command.
 * Each command encapsulates the logic needed to carry out a user request.
 */
public abstract class Command implements CommandInterface {

    /**
     * Determines whether the command requests the app to exit or not.
     * By default, commands do not request the app to exit.
     * Override this method in a subclass if the command should request an exit.
     *
     * @return true if this command is an exit command, false otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}

