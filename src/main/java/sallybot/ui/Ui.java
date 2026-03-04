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

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        System.out.println();
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

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

    public void showBye() {
        showLine();
        System.out.println("\t じゃあね👋");
        System.out.println("\t See you later!");
        showLine();
    }

    public void showHelp() {
        showLine();
        System.out.println("\t はい! Here are the available commands:");
        System.out.println("\t 1) help");
        System.out.println("\t    \t Shows this help message.");
        System.out.println("\t 2) list");
        System.out.println("\t    \t Lists all tasks.");
        System.out.println("\t 3) todo <description>");
        System.out.println("\t    \t Adds a ToDo task.");
        System.out.println("\t 4) deadline <description> /by <date>");
        System.out.println("\t    \t Adds a Deadline task.");
        System.out.println("\t 5) event <description> /from <start> /to <end>");
        System.out.println("\t    \t Adds an Event task.");
        System.out.println("\t 6) mark <index>");
        System.out.println("\t    \t Marks the task at index as done.");
        System.out.println("\t 7) unmark <index>");
        System.out.println("\t    \t Marks the task at index as not done.");
        System.out.println("\t 8) delete <index>");
        System.out.println("\t    \t Deletes the task at index.");
        System.out.println("\t 9) bye");
        System.out.println("\t    \t Exits the program.");
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showLoadingError() {
        showError("\t Unable to load saved tasks. Starting with an empty list.");
    }

    public void showAddedTask(String taskString, int size) {
        showLine();
        System.out.println("\t はい! I've added this task:");
        System.out.println("\t   " + taskString);
        System.out.println("\t Now you have " + size + " tasks in the list. 🌸");
        showLine();
    }

    public void showDeletedTask(String taskString, int size) {
        showLine();
        System.out.println("\t はい! I've deleted this task:");
        System.out.println("\t " + taskString);
        System.out.println("\t Now you have " + size + " in the list.");
        showLine();
    }

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

    public void showListHeader() {
        showLine();
        System.out.println("\t はい! Here are the tasks in your list:");
    }

    public void showListItem(int indexOneBased, String taskString) {
        System.out.println("\t " + indexOneBased + "." + taskString);
    }

    public void showListFooter() {
        showLine();
    }

    private static String getLogo() {
        return """
                \t  ____   __   __    __    _  _  ____   __  ____\s
                \t / ___) / _\\ (  )  (  )  ( \\/ )(  _ \\ /  \\(_  _)
                \t \\___ \\/    \\/ (_/\\/ (_/\\ )  /  ) _ ((  O ) )(
                \t (____/\\_/\\_/\\____/\\____/(__/  (____/ \\__/ (__)\s
                """;
    }
}

