package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.Deadline;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

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
        String[] commandInputs = Parser.parseDeadlineParts(fullCommand, args);

        if (commandInputs.length == 0 && fullCommand.contains("/by")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (commandInputs.length > 2 || Pattern.compile(Pattern.quote("/by"))
                .matcher(fullCommand)
                .results()
                .count() > 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please include only one /by subcommand!");
        }
        if (!fullCommand.contains("/by")) {
            throw new SallyException("\t すみません🙇‍♀️ You need the /by command!");
        }
        if (commandInputs[0].trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (commandInputs.length == 1 || fullCommand.contains("/by") && commandInputs[1].trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a to-do date!");
        }

        tasks.add(new Deadline(commandInputs[0].trim(), commandInputs[1].trim()));
        ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
        return CommandResult.save();
    }
}

