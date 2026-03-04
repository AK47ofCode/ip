package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.DateTimeUtil;
import sallybot.storage.Storage;
import sallybot.task.Event;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class AddEventCommand extends Command {
    private final String fullCommand;
    private final String[] args;

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
