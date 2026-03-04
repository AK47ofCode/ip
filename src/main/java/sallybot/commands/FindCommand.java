package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

import java.util.List;

public class FindCommand extends Command {
    private final String fullCommand;
    private final String[] args;

    public FindCommand(String fullCommand, String[] args) {
        this.args = args;
        this.fullCommand = fullCommand;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        if (args.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the search keyword.");
        }

        String keyword = Parser.parseKeyword(fullCommand, args);
        List<Task> foundTasks = tasks.find(keyword);

        ui.showFoundTasksHeader(keyword);
        int i = 1;
        for (Task task : foundTasks) {
            ui.showListItem(i, task.toString());
            i++;
        }
        ui.showListFooter();

        return CommandResult.none();
    }
}
