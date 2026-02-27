package sallybot;

import sallybot.exception.SallyException;
import sallybot.parser.Parser;
import sallybot.parser.Parser.ParsedCommand;
import sallybot.storage.Storage;
import sallybot.task.Deadline;
import sallybot.task.Event;
import sallybot.task.Task;
import sallybot.task.TaskList;
import sallybot.task.ToDo;
import sallybot.ui.Ui;

import java.util.regex.Pattern;

/**
 * The main class of the Sallybot chatbot.
 */
public class Sallybot {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Sallybot(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (SallyException e) {
            ui.showLoadingError();
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ParsedCommand parsed = Parser.parse(fullCommand);

                switch (parsed.word()) {
                case HELP -> ui.showHelp();
                case LIST -> processList();
                case TODO -> processTodo(parsed.fullCommand(), parsed.args());
                case DEADLINE -> processDeadline(parsed.fullCommand(), parsed.args());
                case EVENT -> processEvent(parsed.fullCommand(), parsed.args());
                case MARK -> processMark(parsed.args());
                case UNMARK -> processUnmark(parsed.args());
                case DELETE -> processDelete(parsed.args());
                case BYE -> isExit = true;
                default -> throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
                }
            } catch (SallyException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showBye();
        ui.close();
    }

    public static void main(String[] args) {
        new Sallybot("data/sallybot.txt").run();
    }

    // Command handlers

    private void processDelete(String[] commandArgs) {
        if (commandArgs.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to delete.");
        }
        try {
            int index = Integer.parseInt(commandArgs[1]);
            Task deleted = tasks.delete(index);
            ui.showDeletedTask(deleted.toString(), tasks.size());
        } catch (NumberFormatException e) {
            ui.showError("\t すみません🙇‍♀️ The parameter must be a number!");
        } finally {
            storage.save(tasks.asUnmodifiableList());
        }
    }

    private void processUnmark(String[] commandArgs) {
        if (commandArgs.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to unmark.");
        }
        try {
            int index = Integer.parseInt(commandArgs[1]);
            Task updated = tasks.unmark(index);
            ui.showMarkedTask(updated.toString(), false);
        } catch (NumberFormatException e) {
            ui.showError("\t すみません🙇‍♀️ The parameter must be a number!");
        } finally {
            storage.save(tasks.asUnmodifiableList());
        }
    }

    private void processMark(String[] commandArgs) {
        if (commandArgs.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to mark.");
        }
        try {
            int index = Integer.parseInt(commandArgs[1]);
            Task updated = tasks.mark(index);
            ui.showMarkedTask(updated.toString(), true);
        } catch (NumberFormatException e) {
            ui.showError("\t すみません🙇‍♀️ The parameter must be a number!");
        } finally {
            storage.save(tasks.asUnmodifiableList());
        }
    }

    private void processList() {
        ui.showListHeader();
        int i = 1;
        for (Task task : tasks.asUnmodifiableList()) {
            ui.showListItem(i, task.toString());
            i++;
        }
        ui.showListFooter();
    }

    private void processEvent(String fullCommand, String[] commandArgs) {
        String[] commandInputs = Parser.parseEventParts(fullCommand, commandArgs);

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
            storage.save(tasks.asUnmodifiableList());
            return;
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
            storage.save(tasks.asUnmodifiableList());
            return;
        }

        throw new SallyException("\t すみません🙇‍♀️ An unknown error occurred!");
    }

    private void processDeadline(String fullCommand, String[] commandArgs) {
        String[] commandInputs = Parser.parseDeadlineParts(fullCommand, commandArgs);

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
        storage.save(tasks.asUnmodifiableList());
    }

    private void processTodo(String fullCommand, String[] commandArgs) {
        String desc = Parser.parseTodoDescription(fullCommand, commandArgs);
        tasks.add(new ToDo(desc));
        ui.showAddedTask(tasks.get(tasks.size()).toString(), tasks.size());
        storage.save(tasks.asUnmodifiableList());
    }
}