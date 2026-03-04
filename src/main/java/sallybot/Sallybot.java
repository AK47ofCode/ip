package sallybot;

import sallybot.commands.Command;
import sallybot.commands.CommandResult;
import sallybot.exception.SallyException;
import sallybot.parser.Parser;
import sallybot.storage.Storage;
import sallybot.task.TaskList;
import sallybot.ui.Ui;

/**
 * The main class of the Sallybot chatbot.
 * Sallybot is a task management chatbot that allows users to manage their tasks through a command-line interface.
 * It supports adding, listing, marking, and deleting tasks, as well as saving and loading tasks from a file.
 * The main loop of the chatbot reads user commands, executes them,
 * and provides feedback until the user decides to exit the application.
 * The chatbot also handles exceptions gracefully, providing error messages to the user when invalid commands are entered
 * or when there are issues with loading/saving tasks.
 * The main method initializes the chatbot with a specified file path for storing tasks and starts the chatbot's main loop.
 *
 * @author Lee Kuan Yi
 * @version 1.0
 * @since 2025-01
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
                Command command = Parser.parse(fullCommand);

                CommandResult result = command.execute(tasks, ui, storage);
                if (result.shouldSave()) {
                    storage.save(tasks.asUnmodifiableList());
                }

                isExit = result.shouldExit() || command.isExit();
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
}