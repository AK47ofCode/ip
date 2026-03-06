package sallybot.ui;

import java.util.Scanner;

/**
 * Ui is responsible for all interactions with the user (printing messages + reading input).
 * It provides methods to display various messages and prompts to the user, as well as a method to read user input.
 * The Ui class also includes a method to close the Scanner resource when it is no longer needed.
 * The Ui class is designed to be used by the main Sallybot class and other components to facilitate user interaction
 * in a consistent and user-friendly manner.
 */
public class Ui {
    private static final String DIVIDER = "\t___________________________________________________________________________";

    private final Scanner scanner;

    /**
     * Constructs a new Ui instance and initializes the Scanner for reading user input.
     * The Scanner is set to read from the standard input stream (System.in).
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line of user input from the console and returns it as a String.
     *
     * @return Next line of user input as a String.
     * This method prompts the user for input and waits until the user enters a line of text.
     */
    public String readCommand() {
        System.out.println();
        return scanner.nextLine();
    }

    /**
     * Closes the Scanner resource used for reading user input. <br>
     * This method should be called when the Ui is no longer needed to free up system resources
     * associated with the Scanner. <br>
     * It is important to call this method to prevent resource leaks,
     * especially in applications that may create multiple Ui instances or run for an extended period of time.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Displays a divider line to the console. <br>
     * This method is used to visually separate different sections of output in the console,
     * making it easier for the user to read and understand the information being presented.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Displays a welcome message to the user when the chatbot is started. <br>
     * This method is called at the beginning of the chatbot's main loop to greet the user
     * and set a friendly tone for the interaction.
     */
    public void showWelcome() {
        showLine();
        System.out.println(getLogo());
        System.out.println("\t 🌸こんにちは🌸");
        System.out.println("\t Hello there✨ I'm Sallybot! Always here to help hehe");
        System.out.println("\t 皆さんが日々ちょっとでも笑顔になる理由になりたいです❤");
        System.out.println("\t I'm in the form of a bot because ᵗʰᵉ ᶦᵈᵒˡ ᵇᵘˢᶦⁿᵉˢˢ ᵈᵒᵉˢⁿ’ᵗ ᵖᵃʸ ᵐᵉ ᵉⁿᵒᵘᵍʰ\n");
        System.out.println("\t What can I do for you today?");
        showLine();
    }

    /**
     * Displays a goodbye message to the user when the chatbot is exiting. <br>
     * This method is called at the end of the chatbot's main loop to bid farewell to the user
     * and provide a pleasant closing to the interaction.
     */
    public void showBye() {
        showLine();
        System.out.println("\t じゃあね👋");
        System.out.println("\t See you later!");
        showLine();
    }

    /**
     * Displays a help message to the user, listing all available commands and their descriptions. <br>
     * This method is called when the user enters the "help" command to provide guidance on how to use the chatbot
     * and what commands are available. <br>
     * The help message is formatted in a clear and organized manner to make it easy for the user to understand
     * the functionality of each command and how to use them effectively.
     */
    public void showHelp() {
        showLine();
        System.out.println("\t はい! Here are the available commands:");
        System.out.println("\t 1. help");
        System.out.println("\t    \t Shows this help message.");
        System.out.println("\t 2. list");
        System.out.println("\t    \t Lists all tasks.");
        System.out.println("\t 3. todo <description>");
        System.out.println("\t    \t Adds a ToDo task.");
        System.out.println("\t 4. deadline <description> /by <date>");
        System.out.println("\t    \t Adds a Deadline task.");
        System.out.println("\t 5. event <description> /from <start> /to <end>");
        System.out.println("\t    \t Adds an Event task.");
        System.out.println("\t 6. mark <index>");
        System.out.println("\t    \t Marks the task at index as done.");
        System.out.println("\t 7. unmark <index>");
        System.out.println("\t    \t Marks the task at index as not done.");
        System.out.println("\t 8. delete <index>");
        System.out.println("\t    \t Deletes the task at index.");
        System.out.println("\t 9. find <keyword>");
        System.out.println("\t    \t Finds tasks that contain the keyword.");
        System.out.println("\t 10. socials");
        System.out.println("\t    \t Shows my socials and 22/7's socials! 🌸");
        System.out.println("\t 11. kiriko");
        System.out.println("\t    \t Shows a fun Kiriko voiceline! 🦊");
        System.out.println("\t 12. carol");
        System.out.println("\t    \t Shows a fun Carol voiceline! 👧");
        System.out.println("\t 13. bye");
        System.out.println("\t    \t Exits the program.");
        showLine();
    }

    /**
     * Displays an error message to the user. <br>
     * This method is called when an exception is caught in the main loop of the chatbot,
     * such as when the user enters an invalid command or when there is an issue with loading/saving tasks.
     *
     * @param message A message to be displayed to the user, typically an error message or feedback on an invalid command.
     */
    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /**
     * Displays a loading error message to the user when there is an issue with loading saved tasks from the file.
     */
    public void showLoadingError() {
        showError("\t すみません🙇‍♀️ I'm unable to load saved tasks. Starting with an empty list...");
    }

    /**
     * Displays a message to the user confirming that a task has been added to the list,
     * along with the details of the task and the current number of tasks in the list.
     *
     * @param taskString The string representation of the task that was added, typically including the task type,
     *                   description, and status.
     * @param size The current number of tasks in the list after the new task has been added.
     *             This is used to inform the user of how many tasks they have in total.
     */
    public void showAddedTask(String taskString, int size) {
        showLine();
        System.out.println("\t はい! I've added this task:");
        System.out.println("\t   " + taskString);
        System.out.println("\t Now you have " + size + " tasks in the list. 🌸");
        showLine();
    }

    /**
     * Displays a message to the user confirming that a task has been deleted from the list,
     * along with the details of the deleted task and the current number of tasks in the list.
     *
     * @param taskString The string representation of the task that was deleted, typically including the task type,
     *                  description, and status.
     * @param size The current number of tasks in the list after the task has been deleted.
     */
    public void showDeletedTask(String taskString, int size) {
        showLine();
        System.out.println("\t はい! I've deleted this task:");
        System.out.println("\t " + taskString);
        System.out.println("\t Now you have " + size + " in the list.");
        showLine();
    }

    /**
     * Displays a message to the user confirming that a task has been marked as done or not done,
     * along with the details of the task and its new status.
     *
     * @param taskString The string representation of the task that was marked/unmarked, typically including the task type,
     *                   description, and status.
     * @param isDone A boolean indicating whether the task is now marked as done (true) or not done (false).
     */
    public void showMarkedTask(String taskString, boolean isDone) {
        showLine();
        if (isDone) {
            System.out.println("\t はい! I've marked your task as done:");
        } else {
            System.out.println("\t はい! I've marked your task as not done:");
        }
        System.out.println("\t " + taskString);
        showLine();
    }

    /**
     * Displays a header message to the user when listing all tasks in the list.
     * This method is called before iterating through the list of tasks to provide a clear introduction to the list output.
     */
    public void showListHeader() {
        showLine();
        System.out.println("\t はい! Here are the tasks in your list:");
    }

    /**
     * Displays a header message to the user when listing tasks that match a search keyword.
     * This method is called before iterating through the list of tasks that match the search keyword
     * to provide a clear introduction to the search results output.
     *
     * @param keyword A string representing the keyword that the user searched for when using the "find" command.
     *                This keyword is included in the header message to indicate what tasks are being listed.
     */
    public void showFoundTasksHeader(String keyword) {
        showLine();
        System.out.println("\t はい! Here are the tasks that match your search for \"" + keyword + "\":");
    }

    /**
     * Displays a message to the user indicating that their task list is empty when they attempt to list tasks
     * but there are none in the list. <br>
     * This method is called when the user enters the "list" command but there are no tasks in the list to display.
     * It provides a friendly prompt to encourage the user to add some tasks to their list.
     */
    public void showEmptyList() {
        showLine();
        System.out.println("\t Your task list is empty! Let's add some tasks! 🌸");
        showLine();
    }

    /**
     * Displays a single task item in the list to the user, formatted with its index and string representation.
     * This method is called for each task in the list when the user enters the "list" command or when displaying search results.
     *
     * @param indexOneBased An integer representing the 1-based index of the task in the list.
     *                      This is used to display the task number in the list output, which is more user-friendly
     *                      than a 0-based index.
     * @param taskString The string representation of the task to be displayed, typically including the task type,
     *                   description, and status.
     */
    public void showListItem(int indexOneBased, String taskString) {
        System.out.println("\t " + indexOneBased + "." + taskString);
    }

    /**
     * Displays a footer message to the user after listing all tasks in the list. <br>
     * This method is called at the end of the task listing output to provide a clear conclusion to the list
     * and visually separate it from any subsequent output. <br>
     * It helps to enhance the readability of the console output by providing a consistent format for the beginning
     * and end of the task list display.
     */
    public void showListFooter() {
        showLine();
    }

    /**
     * Displays a message to the user with links to Sally Amaki's socials, 22/7's socials, and the Anime English Club podcast. <br>
     * This method is called when the user enters the "socials" command.
     */
    public void showSocials() {
        showLine();
        System.out.println("\t If you want to know more about me, check out my socials! 🌸");
        System.out.println("\t No not as Sallybot HAHA, but as Sally Amaki!");
        System.out.println("\t X/Twitter: https://x.com/sally_amaki");
        System.out.println("\t Instagram: https://www.instagram.com/sallyamaki/");
        System.out.println("\t TikTok: https://www.tiktok.com/@sally_amaki");
        System.out.println("\t YouTube: https://www.youtube.com/@sallyamakiofficial");
        System.out.println();
        System.out.println("\t Please do check out 22/7's socials too! 🌸");
        System.out.println("\t 22/7 is the idol group that I am a part of, and we have lots of fun content on our socials!");
        System.out.println("\t X/Twitter: https://x.com/227_staff");
        System.out.println("\t YouTube: https://www.youtube.com/c/227SMEJ");
        System.out.println("\t Official Website: https://www.nanabunnonijyuuni.com/");
        System.out.println();
        System.out.println("\t Oh and please don't also forget to see my Anime English Club podcast! 🌸");
        System.out.println("\t YouTube: https://www.youtube.com/@anime-english-club");
        System.out.println("\t Spotify: https://open.spotify.com/show/3cc98Fciw33tyxZe636cKr");
        showLine();
    }

    /**
     * Extra fun method to show a Kiriko voiceline to the user. <br>
     * This is designed as a fun Easter egg for fans of the character Kiriko from the game Overwatch, and is not related to any specific command. <br>
     * It can be called at any time to provide a lighthearted moment for the user and add some personality to the chatbot's interactions.
     */
    public void showKirikoVoiceline() {
        showLine();
        System.out.println("\t Let the Kitsune guide you! 🦊");
        showLine();
    }

    /**
     * Extra fun method to show a "Hey, stop doing that!" message to the user. <br>
     * This is an Easter egg which is a reference to a popular internet meme of Carol Olston from Tomo-chan is a Girl!
     * saying "Hey, stop doing that!" in a disapproving tone.
     */
    public void showStopDoingThat() {
        showLine();
        System.out.println("\t Hey.");
        System.out.println("\t Stop doing that.");
        System.out.println("\t https://imgur.com/a/hey-stop-doing-that-t4qaboS");
        showLine();
    }

    /**
     * Displays the ASCII art logo of Sallybot to the user when the chatbot is started. <br>
     * The logo adds a fun and personalized touch to the chatbot's interface, making it more engaging and visually appealing for the user.
     *
     * @return The ASCII art logo of Sallybot.
     */
    private static String getLogo() {
        return """
                \t  ____   __   __    __    _  _  ____   __  ____\s
                \t / ___) / _\\ (  )  (  )  ( \\/ )(  _ \\ /  \\(_  _)
                \t \\___ \\/    \\/ (_/\\/ (_/\\ )  /  ) _ ((  O ) )(
                \t (____/\\_/\\_/\\____/\\____/(__/  (____/ \\__/ (__)\s
                """;
    }
}

