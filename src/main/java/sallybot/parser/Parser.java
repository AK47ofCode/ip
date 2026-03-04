package sallybot.parser;

import sallybot.commands.*;
import sallybot.exception.SallyException;

import java.util.regex.Pattern;

/**
 * Parser is responsible for making sense of the user commands.
 */
public class Parser {
    /**
     * Parses the user input into a {@link Command}.
     *
     * @param fullCommand raw user input
     * @return a concrete {@link Command}
     * @throws SallyException if the command is invalid
     */
    public static Command parse(String fullCommand) {
        if (fullCommand == null) {
            throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
        }

        String trimmed = fullCommand.trim();
        if (trimmed.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
        }

        String[] commandArgs = trimmed.split("\\s+");
        String head = commandArgs[0];

        return switch (head) {
        case "help" -> new HelpCommand();
        case "todo" -> new AddTodoCommand(trimmed, commandArgs);
        case "deadline" -> new AddDeadlineCommand(trimmed, commandArgs);
        case "event" -> new AddEventCommand(trimmed, commandArgs);
        case "list" -> new ListCommand();
        case "mark" -> new MarkCommand(commandArgs);
        case "unmark" -> new UnmarkCommand(commandArgs);
        case "delete" -> new DeleteCommand(commandArgs);
        case "find" -> new FindCommand(fullCommand, commandArgs);
        case "bye" -> new ByeCommand();
        default -> new UnknownCommand();
        };
    }

    public static String parseTodoDescription(String fullCommand, String[] args) {
        String desc = fullCommand.replaceFirst(Pattern.quote(args[0]), "").trim();
        if (desc.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You didn't provide a ToDo description!");
        }
        return desc;
    }

    public static String[] parseDeadlineParts(String fullCommand, String[] args) {
        String rest = fullCommand.replaceFirst(Pattern.quote(args[0]), "");
        // Split on the literal '/by' token (not regex '/by') so dates like 2/12/2019 aren't broken by '/'.
        return rest.split("\\s*" + Pattern.quote("/by") + "\\s*");
    }

    public static String[] parseEventParts(String fullCommand, String[] args) {
        String rest = fullCommand.replaceFirst(Pattern.quote(args[0]), "");
        // Use a regex that matches the whole tokens '/from' or '/to' (not just '/')
        // so dates like 12/11/2026 are preserved.
        return rest.split("\\s*(?:/from|/to)\\s*");
    }

    public static String parseKeyword(String fullCommand, String[] args) {
        String rest = fullCommand.replaceFirst(Pattern.quote(args[0]), "").trim();
        if (rest.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You didn't provide a search keyword!");
        }
        return rest;
    }
}
