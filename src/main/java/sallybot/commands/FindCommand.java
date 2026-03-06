package sallybot.commands;

import sallybot.exception.SallyException;
import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

import java.util.List;

/**
 * Handles the "find" command, which searches for tasks containing a specified keyword and displays them to the user.
 * This is a non-modifying command that does not require saving after execution.
 */
public class FindCommand extends Command {
    private final String fullCommand;
    private final String[] args;

    /**
     * Creates a new FindCommand with the given full command string and arguments.
     *
     * @param fullCommand The full command string entered by the user, e.g. "find homework"
     * @param args The command arguments split by spaces, e.g. ["find", "homework"]
     */
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
