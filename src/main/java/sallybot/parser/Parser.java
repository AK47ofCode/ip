package sallybot.parser;

import sallybot.exception.SallyException;

import java.util.regex.Pattern;

/**
 * Parser is responsible for making sense of the user commands.
 */
public class Parser {
    public enum CommandWord {
        HELP, TODO, DEADLINE, EVENT, LIST, MARK, UNMARK, DELETE, BYE
    }

    public record ParsedCommand(CommandWord word, String fullCommand, String[] args) {
    }

    public static ParsedCommand parse(String fullCommand) {
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
        case "help" -> new ParsedCommand(CommandWord.HELP, trimmed, commandArgs);
        case "todo" -> new ParsedCommand(CommandWord.TODO, trimmed, commandArgs);
        case "deadline" -> new ParsedCommand(CommandWord.DEADLINE, trimmed, commandArgs);
        case "event" -> new ParsedCommand(CommandWord.EVENT, trimmed, commandArgs);
        case "list" -> new ParsedCommand(CommandWord.LIST, trimmed, commandArgs);
        case "mark" -> new ParsedCommand(CommandWord.MARK, trimmed, commandArgs);
        case "unmark" -> new ParsedCommand(CommandWord.UNMARK, trimmed, commandArgs);
        case "delete" -> new ParsedCommand(CommandWord.DELETE, trimmed, commandArgs);
        case "bye" -> new ParsedCommand(CommandWord.BYE, trimmed, commandArgs);
        default -> throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
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
        return rest.split("/by");
    }

    public static String[] parseEventParts(String fullCommand, String[] args) {
        String rest = fullCommand.replaceFirst(Pattern.quote(args[0]), "");
        return rest.split("/from|/to");
    }
}

