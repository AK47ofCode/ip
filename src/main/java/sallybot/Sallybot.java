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
 * @version 0.2
 * @since 2025-01-27
 */
public class Sallybot {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Initializes the Sallybot chatbot with the specified file path for storing tasks.
     * The constructor initializes the UI, storage, and task list.
     * It attempts to load existing tasks from the specified file path.
     * If the file does not exist or there is an error during loading, it initializes an empty task list
     * and shows a loading error message to the user.
     *
     * @param filePath The file path where the chatbot will load and save tasks. If the file does not exist, it will be created.
     */
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

    /**
     * Runs the main loop of the Sallybot chatbot.
     * The method starts by showing a welcome message to the user.
     * It then enters a loop where it continuously reads user commands, parses them, and executes them until the user decides to exit.
     * For each command, it checks if the command execution indicates that the tasks should be saved,
     * and if so, it saves the current task list to the specified file.
     * The loop also checks if the command indicates that the chatbot should exit, in which case it breaks the loop
     * and shows a goodbye message before closing the UI.
     */
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

    /**
     * The main method of the Sallybot chatbot application.
     * This method initializes a new instance of the Sallybot chatbot with a specified file path for storing tasks
     * and starts the chatbot's main loop by calling the run() method.
     *
     * @param args The command-line arguments.
     *             This program does not use any command-line arguments, so this parameter is ignored.
     */
    public static void main(String[] args) {
        new Sallybot("data/sallybot.txt").run();
    }
}