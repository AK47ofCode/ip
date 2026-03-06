package sallybot.parser;

import sallybot.commands.*;
import sallybot.exception.SallyException;

import java.util.regex.Pattern;

/**
 * Parser is responsible for making sense of the user commands. <br>
 * The Parser is a crucial component of the application, as it serves as the bridge between the raw user input and the internal command execution logic,
 * allowing the application to understand and respond to user commands effectively.
 */
public class Parser {
    /**
     * Parses the user input into a {@link Command}. <br>
     * The Parser class is designed to be easily extensible, allowing for new commands to be added in the future
     * by simply adding new cases to the switch expression and creating the corresponding Command classes.
     * The Parser class also centralizes the command parsing logic, making it easier to maintain and update as the application evolves.
     * Overall, the Parser class plays a critical role in enabling the application to understand and respond to user commands effectively,
     * contributing to a smooth and intuitive user experience.
     *
     * @param fullCommand Raw user input.
     * @return A concrete {@link Command}.
     * @throws SallyException If the command is invalid.
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

    /**
     * Parses the description of a ToDo task from the full command string. <br>
     * The description is the part of the command after the command keyword (e.g. "todo") and may contain spaces. <br>
     * The full command string is needed to extract the description, which may contain spaces and cannot be easily reconstructed from the args array.
     *
     * @param fullCommand The full command string entered by the user, e.g. "todo read book".
     * @param args The arguments extracted from the command, e.g. ["read", "book"].
     * @return The description of the ToDo task, e.g. "read book".
     */
    public static String parseTodoDescription(String fullCommand, String[] args) {
        String desc = fullCommand.replaceFirst(Pattern.quote(args[0]), "").trim();
        if (desc.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You didn't provide a ToDo description!");
        }
        return desc;
    }

    /**
     * Parses the search keyword from the full command string for the "find" command. <br>
     * The keyword is the part of the command after the "find" keyword and may contain spaces. <br>
     * The full command string is needed to extract the keyword, which may contain spaces and cannot be easily reconstructed from the args array.
     *
     * @param fullCommand The full command string entered by the user, e.g. "find homework".
     * @param args The arguments extracted from the command, e.g. ["find", "homework"].
     * @return The search keyword, e.g. "homework".
     */
    public static String parseKeyword(String fullCommand, String[] args) {
        String rest = fullCommand.replaceFirst(Pattern.quote(args[0]), "").trim();
        if (rest.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You didn't provide a search keyword!");
        }
        return rest;
    }
}
