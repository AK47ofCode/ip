import java.util.ArrayList;
import java.util.Scanner;

public class Sallybot {
    public static void main(String[] args) {
        boolean isPrompting = true;
        ArrayList<Task> tasks = new ArrayList<>();
        String logo = getLogo();
        Scanner input = new Scanner(System.in);

        // Greeting message that is executed when the program starts
        drawBorder();
        System.out.println(logo);
        System.out.println("\t 🌸こんにちは🌸");
        System.out.println("\t Hello there✨ I'm Sallybot! Always here to help hehe\n");
        System.out.println("\t What can I do for you today?");
        drawBorder();

        while (isPrompting) {
            System.out.println();
            String command = input.nextLine();
            String[] commandArgs = command.trim().split("\\s+");
            String commandInput;
            String[] commandInputs;

            switch (commandArgs[0]) {
            case "todo":
                commandInput = command.replace(commandArgs[0], "").trim();
                tasks.add(new ToDo(commandInput));
                drawBorder();
                System.out.println("\t はい! I've added this task:");
                System.out.println("\t   " + tasks.get(tasks.size() - 1).toString());
                System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                drawBorder();
                break;
            case "deadline":
                commandInput = command.replace(commandArgs[0], "").trim();
                commandInputs = commandInput.trim().split("/by");
                tasks.add(new Deadline(commandInputs[0].trim(), commandInputs[1].trim()));
                drawBorder();
                System.out.println("\t はい! I've added this task:");
                System.out.println("\t   " + tasks.get(tasks.size() - 1).toString());
                System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                drawBorder();
                break;
            case "event":
                commandInput = command.replace(commandArgs[0], "").trim();
                commandInputs = commandInput.trim().split("/from|/to");
                tasks.add(new Event(commandInputs[0].trim(), commandInputs[1].trim(), commandInputs[2].trim()));
                drawBorder();
                System.out.println("\t はい! I've added this task:");
                System.out.println("\t   " + tasks.get(tasks.size() - 1).toString());
                System.out.println("\t Now you have " + tasks.size() + " tasks in the list.");
                drawBorder();
                break;
            case "list":
                if (commandArgs.length == 1) {
                    drawBorder();
                    System.out.println("\t はい! Here are the tasks in your list:");
                    for (Task task : tasks) {
                        System.out.println("\t " + (tasks.indexOf(task) + 1) + "." + task.toString());
                    }
                    drawBorder();
                    break;
                }
            case "mark":
                if (commandArgs.length == 1) {
                    System.out.println("\t すみません🙇‍♀️ Please provide the index of the task you would like to mark.");
                    break;
                }
                else {
                    try {
                        int index = Integer.parseInt(commandArgs[1]);
                        if (index >= 1 && index <= tasks.size()) {
                            tasks.get(index - 1).markAsDone();
                            drawBorder();
                            System.out.println("\t はい! I've marked your task as done:");
                            System.out.println("\t " + tasks.get(index - 1).toString());
                        }
                        else  {
                            drawBorder();
                            System.out.println("\t すみません🙇‍♀️ This index is invalid!");
                        }
                        drawBorder();
                        break;
                    } catch (NumberFormatException ignored) {}
                }
            case "unmark":
                if (commandArgs.length == 1) {
                    System.out.println("\t すみません🙇‍♀️ " +
                            "Please provide the index of the task you would like to unmark.");
                    break;
                }
                else {
                    try {
                        int index = Integer.parseInt(commandArgs[1]);
                        if (index >= 1 && index <= tasks.size()) {
                            tasks.get(index - 1).markAsNotDone();
                            drawBorder();
                            System.out.println("\t はい! I've marked your task as not done:");
                            System.out.println("\t " + tasks.get(index - 1).toString());
                        }
                        else  {
                            drawBorder();
                            System.out.println("\t すみません🙇‍♀️ This index is invalid!");
                        }
                        drawBorder();
                        break;
                    }
                    catch (NumberFormatException ignored) {}
                }
            case "bye":
                if (commandArgs.length == 1) {
                    isPrompting = false;
                    break;
                }
            default:
                tasks.add(new Task(command));
                drawBorder();
                System.out.println("\t はい! Added into the list of tasks: " + command);
                drawBorder();
                break;
            }
        }

        printByeMessage();
        input.close();
    }

    // HELPER METHODS
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
        System.out.println("\t____________________________________________________________");
    }

    /**
     * @return Displays the ASCII logo of Sallybot.
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