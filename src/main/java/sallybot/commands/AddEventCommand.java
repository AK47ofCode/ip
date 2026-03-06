package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.DateTimeUtil;
import sallybot.storage.Storage;
import sallybot.task.Event;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * Handles the "event" command, which adds an event task to the task list. <br>
 * The command should be in the format: <code>event [description] /from [start time] /to [end time]</code>. <br>
 * The description, start time and end time are extracted from the command string. <br>
 * The start time and end time should be in a format that can be parsed by the DateTimeUtil.parseUserDateTime method. <br>
 * This is a modifying command that requires saving after execution.
 */
public class AddEventCommand extends Command {
    private final String fullCommand;
    private final String[] args;

    /**
     * Creates an AddEventCommand with the full command string and its arguments.
     * The full command string is needed to extract the description, start time and end time, which may contain spaces and cannot be easily reconstructed from the args array.
     * The args array contains the individual components of the command, but the description, start time and end time may be single strings that include multiple args.
     *
     * @param fullCommand The full command string entered by the user, e.g. "event project meeting /from 2024-12-01 14:00 /to 2024-12-01 15:00".
     * @param args The arguments extracted from the command, e.g. ["project", "meeting", "/from", "2024-12-01", "14:00", "/to", "2024-12-01", "15:00"].
     */
    public AddEventCommand(String fullCommand, String[] args) {
        this.fullCommand = fullCommand;
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (Pattern.compile(Pattern.quote("/from")).matcher(fullCommand).results().count() > 1
                || Pattern.compile(Pattern.quote("/to")).matcher(fullCommand).results().count() > 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please include only one /from and one /to subcommand!");
        }
        if (!fullCommand.contains("/from")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to include the /from command!");
        }
        if (!fullCommand.contains("/to")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to include the /to command!");
        }

        int fromIndex = fullCommand.indexOf("/from");
        int toIndex = fullCommand.indexOf("/to");

        String desc;
        String fromRaw;
        String toRaw;

        if (fromIndex < toIndex) {
            desc = fullCommand.substring(args[0].length(), fromIndex).trim();
            fromRaw = fullCommand.substring(fromIndex + "/from".length(), toIndex).trim();
            toRaw = fullCommand.substring(toIndex + "/to".length()).trim();
        } else {
            desc = fullCommand.substring(args[0].length(), toIndex).trim();
            toRaw = fullCommand.substring(toIndex + "/to".length(), fromIndex).trim();
            fromRaw = fullCommand.substring(fromIndex + "/from".length()).trim();
        }

        if (desc.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (fromRaw.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a starting date!");
        }
        if (toRaw.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me an ending date!");
        }

        LocalDateTime from = DateTimeUtil.parseUserDateTime(fromRaw);
        LocalDateTime to = DateTimeUtil.parseUserDateTime(toRaw);

        tasks.add(new Event(desc, from, to));
        ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
        return CommandResult.save();
    }
}
