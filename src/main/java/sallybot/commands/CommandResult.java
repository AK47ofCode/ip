package sallybot.commands;

/**
 * CommandResult tells the main loop what to do after a command executes.
 * This allows commands to control the flow of the program without needing to throw exceptions or call System.exit() directly.
 * For example, a ByeCommand would return CommandResult.exit() to indicate that the program should exit,
 * while a command that modifies the task list might return CommandResult.save() to indicate that the program should save
 * the updated task list to storage.
 *
 * @param shouldExit Whether the program should exit after executing the command.
 * @param shouldSave Whether the program should save the current state to storage after executing the command.
 */
public record CommandResult(boolean shouldExit, boolean shouldSave) {

    /**
     * Default CommandResult indicating that no special action is needed (neither exit nor save).
     *
     * @return A CommandResult indicating that no special action is needed (neither exit nor save).
     */
    public static CommandResult none() {
        return new CommandResult(false, false);
    }

    /**
     * A CommandResult indicating that the program should save the current state to storage after executing the command.
     *
     * @return A CommandResult indicating that the program should save the current state to storage after executing the command.
     */
    public static CommandResult save() {
        return new CommandResult(false, true);
    }

    /**
     * A CommandResult indicating that the program should exit after executing the command.
     *
     * @return A CommandResult indicating that the program should exit after executing the command.
     */
    public static CommandResult exit() {
        return new CommandResult(true, false);
    }
}

