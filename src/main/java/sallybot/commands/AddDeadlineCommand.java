package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.DateTimeUtil;
import sallybot.storage.Storage;
import sallybot.task.Deadline;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class AddDeadlineCommand extends Command {
    private final String fullCommand;
    private final String[] args;

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
