package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.Event;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

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
        String[] commandInputs = Parser.parseEventParts(fullCommand, args);

        if (commandInputs.length > 3 || Pattern.compile(Pattern.quote("/from"))
                .matcher(fullCommand).results().count() > 1 || Pattern.compile(Pattern.quote("/to"))
                .matcher(fullCommand).results().count() > 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please include only one /from and one /to subcommand!");
        }
        if (commandInputs[0].trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (!fullCommand.contains("/from")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to include the /from command!");
        }
        if (!fullCommand.contains("/to")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to include the /to command!");
        }
        if (fullCommand.indexOf("/from") < fullCommand.indexOf("/to")) {
            if (commandInputs[1].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me a starting date!");
            }
            if (commandInputs.length == 2 || commandInputs[2].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me an ending date!");
            }
            tasks.add(new Event(commandInputs[0].trim(), commandInputs[1].trim(), commandInputs[2].trim()));
            ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
            return CommandResult.save();
        }
        if (fullCommand.indexOf("/from") > fullCommand.indexOf("/to")) {
            if (commandInputs[1].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me an ending date!");
            }
            if (commandInputs.length == 2 || commandInputs[2].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me a starting date!");
            }
            tasks.add(new Event(commandInputs[0].trim(), commandInputs[2].trim(), commandInputs[1].trim()));
            ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
            return CommandResult.save();
        }

        throw new SallyException("\t すみません🙇‍♀️ An unknown error occurred!");
    }
}

