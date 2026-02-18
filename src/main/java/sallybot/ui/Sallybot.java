package sallybot.ui;

import sallybot.exception.SallyException;
import sallybot.task.Deadline;
import sallybot.task.Event;
import sallybot.task.Task;
import sallybot.task.ToDo;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Sallybot {
    public static void main(String[] args) {
        boolean isPrompting = true;
        ArrayList<Task> tasks = new ArrayList<>();
        String logo = getLogo();
        Scanner input = new Scanner(System.in);

        // Greeting message that is executed when the program starts
        printHelloMessage(logo);

        // While loop continuously prompts the user until the user enters the "bye" command
        while (isPrompting) {
            System.out.println();
            String command = input.nextLine();
            String[] commandArgs = command.trim().split("\\s+");
            String commandInput;
            String[] commandInputs;

            // Switch statement to handle the various commands
            try {
                switch (commandArgs[0]) {
                    case "help":
                        printHelpMessage();
                        break;
                    case "todo":
                        processTodo(command, commandArgs, tasks);
                        break;
                    case "deadline":
                        commandInput = command.replace(commandArgs[0], "");
                        commandInputs = commandInput.split("/by");

                        processDeadline(commandInputs, command, tasks);
                        break;
                    case "event":
                        commandInput = command.replace(commandArgs[0], "");
                        commandInputs = commandInput.split("/from|/to");

                        processEvent(commandInputs, command, tasks);
                        break;
                    case "list":
                        processList(tasks);
                        break;
                    case "mark":
                        processMark(commandArgs, tasks);
                        break;
                    case "unmark":
                        processUnmark(commandArgs, tasks);
                        break;
                    case "delete":
                        processDelete(commandArgs, tasks);
                        break;
                    case "bye":
                        isPrompting = false;
                        break;
                    default:
                        throw new SallyException("\t すみません🙇‍♀️ This command is invalid!");
                }
            } catch (SallyException e) {
                drawBorder();
                System.out.println(e.getMessage());
                drawBorder();
            }
        }

        // Summons the bye message ones the program breaks from the while loop and is about to end
        printByeMessage();
        input.close();
    }

    // HELPER METHODS FOR PROCESSING COMMANDS

    private static void processDelete(String[] commandArgs, ArrayList<Task> tasks) {
        if (commandArgs.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to delete.");
        }
        try {
            int index = Integer.parseInt(commandArgs[1]);
            if (index >= 1 && index <= tasks.size()) {
                String deletedTask = tasks.get(index - 1).toString();
                tasks.remove(index - 1);
                drawBorder();
                System.out.println("\t はい! I've deleted this task:");
                System.out.println("\t " + deletedTask);
                System.out.println("\t Now you have " + tasks.size() + " in the list.");
                drawBorder();
            }
            else if (tasks.isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You have no tasks!");
            }
            else {
                throw new SallyException("\t すみません🙇‍♀️ This index is invalid!");
            }
        } catch (NumberFormatException e) {
            drawBorder();
            System.out.println("\t すみません🙇‍♀️ The parameter must be a number!");
            drawBorder();
        }
    }

    private static void processUnmark(String[] commandArgs, ArrayList<Task> tasks) {
        if (commandArgs.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ " +
                    "Please provide the index of the task you would like to unmark.");
        }
        try {
            int index = Integer.parseInt(commandArgs[1]);
            if (index >= 1 && index <= tasks.size()) {
                tasks.get(index - 1).markAsNotDone();
                drawBorder();
                System.out.println("\t はい! I've marked your task as not done:");
                System.out.println("\t " + tasks.get(index - 1).toString());
                drawBorder();
            } else {
                throw new SallyException("\t すみません🙇‍♀️ This index is invalid!");
            }
        } catch (NumberFormatException e) {
            drawBorder();
            System.out.println("\t すみません🙇‍♀️ The parameter must be a number!");
            drawBorder();
        }
    }

    private static void processMark(String[] commandArgs, ArrayList<Task> tasks) {
        if (commandArgs.length == 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please provide the index of the task you would like to mark.");
        }
        try {
            int index = Integer.parseInt(commandArgs[1]);
            if (index >= 1 && index <= tasks.size()) {
                tasks.get(index - 1).markAsDone();
                drawBorder();
                System.out.println("\t はい! I've marked your task as done:");
                System.out.println("\t " + tasks.get(index - 1).toString());
                drawBorder();
            } else {
                throw new SallyException("\t すみません🙇‍♀️ This index is invalid!");
            }
        } catch (NumberFormatException e) {
            drawBorder();
            System.out.println("\t すみません🙇‍♀️ The parameter must be a number!");
            drawBorder();
        }
    }

    private static void processList(ArrayList<Task> tasks) {
        drawBorder();
        System.out.println("\t はい! Here are the tasks in your list:");
        for (Task task : tasks) {
            System.out.println("\t " + (tasks.indexOf(task) + 1) + "." + task.toString());
        }
        drawBorder();
    }

    private static void processEvent(String[] commandInputs, String command, ArrayList<Task> tasks) {
        if (commandInputs.length > 3 || Pattern.compile(Pattern.quote("/from"))
                .matcher(command).results().count() > 1 || Pattern.compile(Pattern.quote("/to"))
                .matcher(command).results().count() > 1 ) {
            throw new SallyException("\t すみません🙇‍♀️ Please include only one /from and one /to subcommand!");
        }
        if (commandInputs[0].trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (!command.contains("/from")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to include the /from command!");
        }
        if (!command.contains("/to")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to include the /to command!");
        }
        if (command.indexOf("/from") < command.indexOf("/to")) {
            if (commandInputs[1].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me a starting date!");
            }
            if (commandInputs.length == 2 || commandInputs[2].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me an ending date!");
            }
            tasks.add(new Event(commandInputs[0].trim(), commandInputs[1].trim(), commandInputs[2].trim()));
            getNewlyAddedTask(tasks);
            return;
        }
        if (command.indexOf("/from") > command.indexOf("/to")) {
            if (commandInputs[1].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me an ending date!");
            }
            if (commandInputs.length == 2 || commandInputs[2].trim().isEmpty()) {
                throw new SallyException("\t すみません🙇‍♀️ You need to give me a starting date!");
            }
            tasks.add(new Event(commandInputs[0].trim(), commandInputs[2].trim(), commandInputs[1].trim()));
            getNewlyAddedTask(tasks);
            return;
        }

        throw new SallyException("\t すみません🙇‍♀️ An unknown error occurred!");
    }

    private static void processDeadline(String[] commandInputs, String command, ArrayList<Task> tasks) {
        if (commandInputs.length == 0 && command.contains("/by")) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (commandInputs.length > 2 || Pattern.compile(Pattern.quote("/by"))
                .matcher(command)
                .results()
                .count() > 1) {
            throw new SallyException("\t すみません🙇‍♀️ Please include only one /by subcommand!");
        }
        if (!command.contains("/by")) {
            throw new SallyException("\t すみません🙇‍♀️ You need the /by command!");
        }
        if (commandInputs[0].trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a description!");
        }
        if (commandInputs.length == 1 || command.contains("/by") && commandInputs[1].trim().isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You need to give me a to-do date!");
        }

        tasks.add(new Deadline(commandInputs[0].trim(), commandInputs[1].trim()));
        getNewlyAddedTask(tasks);
    }

    private static void processTodo(String command, String[] commandArgs, ArrayList<Task> tasks) {
        String commandInput;
        commandInput = command.replace(commandArgs[0], "").trim();
        if (commandInput.isEmpty()) {
            throw new SallyException("\t すみません🙇‍♀️ You didn't provide a ToDo description!");
        }
        tasks.add(new ToDo(commandInput));
        getNewlyAddedTask(tasks);
    }

    // HELPER METHODS FOR PRINTING MESSAGES AND LOGO

    private static void printHelpMessage() {
        drawBorder();
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
        drawBorder();
    }

    /**
     * Gets the newly added task in the array of tasks and displays it to the user.
     * @param tasks A dynamic array of the Task class type that stores task objects.
     */
    private static void getNewlyAddedTask(ArrayList<Task> tasks) {
        drawBorder();
        System.out.println("\t はい! I've added this task:");
        System.out.println("\t   " + tasks.get(tasks.size() - 1).toString());
        System.out.println("\t Now you have " + tasks.size() + " tasks in the list. 🌸");
        drawBorder();
    }

    /**
     * Prints the hello message.
     * @param logo A string of text that represents the logo of Sallybot.
     */
    private static void printHelloMessage(String logo) {
        drawBorder();
        System.out.println(logo);
        System.out.println("\t 🌸こんにちは🌸");
        System.out.println("\t Hello there✨ I'm Sallybot! Always here to help hehe");
        System.out.println("\t 皆さんが日々ちょっとでも笑顔になる理由になりたいです❤");
        System.out.println("\t I'm in the form of a bot because ᵗʰᵉ ᶦᵈᵒˡ ᵇᵘˢᶦⁿᵉˢˢ ᵈᵒᵉˢⁿ’ᵗ ᵖᵃʸ ᵐᵉ ᵉⁿᵒᵘᵍʰ\n");
        System.out.println("\t What can I do for you today?");
        drawBorder();
    }

    /**
     * Prints the bye message.
     */
    private static void printByeMessage() {
        drawBorder();
        System.out.println("\t じゃあね👋");
        System.out.println("\t See you later!");
        drawBorder();
    }

    /**
     * Draws a border.
     */
    private static void drawBorder() {
        System.out.println("\t___________________________________________________________________________");
    }

    /**
     * Renders the ASCII logo of Sallybot.
     * @return The ASCII logo of Sallybot as a String.
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