package sallybot.commands;

/**
 * CommandResult tells the main loop what to do after a command executes.
 */
public record CommandResult(boolean shouldExit, boolean shouldSave) {

    public static CommandResult none() {
        return new CommandResult(false, false);
    }

    public static CommandResult save() {
        return new CommandResult(false, true);
    }

    public static CommandResult exit() {
        return new CommandResult(true, false);
    }
}

