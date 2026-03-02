package sallybot.commands;

import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.task.ToDo;
import sallybot.ui.Ui;

public class AddTodoCommand extends Command {
    private final String fullCommand;
    private final String[] args;

    public AddTodoCommand(String fullCommand, String[] args) {
        this.fullCommand = fullCommand;
        this.args = args;
    }

    @Override
    public CommandResult execute(TaskList tasks, Ui ui, Storage storage) {
        String desc = Parser.parseTodoDescription(fullCommand, args);
        tasks.add(new ToDo(desc));
        ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
        return CommandResult.save();
    }
}

