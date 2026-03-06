package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.DateTimeUtil;
import sallybot.storage.Storage;
import sallybot.task.Deadline;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * Handles the "deadline" command, which adds a new deadline task to the task list. <br>
 * The command should be in the format: "deadline <description> /by <date/time>" <br>
 * The description is the text before the "/by" subcommand, and the date/time is the text after the "/by" subcommand. <br>
 * The date/time should be in a format that can be parsed by the DateTimeUtil.parseUserDateTime method. <br>
 * This is a modifying command that requires saving after execution.
 */
public class AddDeadlineCommand extends Command {
    private final String fullCommand;
    private final String[] args;

    /**
     * Creates an AddDeadlineCommand with the full command string and its arguments.
     * The full command string is needed to extract the description and the date/time, which may contain spaces and cannot be easily reconstructed from the args array.
     * The args array contains the individual components of the command, but the description and date/time may be single strings that include multiple args.
     *
     * @param fullCommand The full command string entered by the user, e.g. "deadline submit report /by 2024-12-31 23:59".
     * @param args The arguments extracted from the command, e.g. ["submit", "report", "/by", "2024-12-31", "23:59"].
     */
    public AddDeadlineCommand(String fullCommand, String[] args) {
        this.fullCommand = fullCommand;
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (!fullCommand.contains("/by")) {
            throw new SallyException("\t すみません🙇‍♀️ You need the /by command!");
        }
        if (Pattern.compile(Pattern.quote("/by")).matcher(fullCommand).results().count() > 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please include only one /by subcommand!");
        }

        int byIndex = fullCommand.indexOf("/by");
        String desc = fullCommand.substring(args[0].length(), byIndex).trim();
        String byRaw = fullCommand.substring(byIndex + "/by".length()).trim();

        if (desc.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (byRaw.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a to-do date!");
        }

        LocalDateTime by = DateTimeUtil.parseUserDateTime(byRaw);

        tasks.add(new Deadline(desc, by));
        ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
        return CommandResult.save();
    }
}
