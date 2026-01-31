import java.util.ArrayList;
import java.util.Scanner;

public class Sallybot {
//    public static final int MAX_NUM_OF_TASKS = 100;

    public static void main(String[] args) {
        boolean isStillExecuting = true;
        ArrayList<Task> tasks = new ArrayList<>();
        String logo = getLogo();

        drawBorder();
        System.out.println(logo + "\n");
        System.out.println("\t 🌸こんにちは🌸\n");
        System.out.println("\t Hello there✨ I'm Sallybot! Always here to help hehe\n");
        System.out.println("\t What can I do for you today?\n");
        drawBorder();

        while (isStillExecuting) {
            Scanner input = new Scanner(System.in);
            String command = input.nextLine();
            String[] commandArgs = command.trim().split("\\s+", 2);
            String commandName = commandArgs[0];

            switch (commandName) {
            case "list":
                if (commandArgs.length == 1) {
                    drawBorder();
                    System.out.println("\t はい! Here are the tasks in your list:\n");
                    for (Task task : tasks) {
                        System.out.println("\t "
                                + (tasks.indexOf(task) + 1) + ".["
                                + task.getStatusIcon() + "] "
                                + task.getDescription());
                    }
                    drawBorder();
                    break;
                }
            case "mark":
                if (commandArgs.length == 1) {
                    System.out.println("\t すみません🙇‍♀️ Please provide the index of the task you would like to mark.\n");
                    break;
                }
                else {
                    try {
                        int index = Integer.parseInt(commandArgs[1]);
                        if (index >= 1 && index <= tasks.size()) {
                            tasks.get(index - 1).markAsDone();
                            drawBorder();
                            System.out.println("\t はい! I've marked your task as done:\n");
                            System.out.println("\t ["
                                    + tasks.get(index - 1).getStatusIcon() + "] "
                                    + tasks.get(index - 1).getDescription());
                        }
                        else  {
                            drawBorder();
                            System.out.println("\t すみません🙇‍♀️ This index is invalid!\n");
                        }
                        drawBorder();
                        break;
                    }
                    catch (NumberFormatException ignored) {}
                }
            case "unmark":
                if (commandArgs.length == 1) {
                    System.out.println("\t すみません🙇‍♀️ " +
                            "Please provide the index of the task you would like to unmark.\n");
                    break;
                }
                else {
                    try {
                        int index = Integer.parseInt(commandArgs[1]);
                        if (index >= 1 && index <= tasks.size()) {
                            tasks.get(index - 1).markAsNotDone();
                            drawBorder();
                            System.out.println("\t はい! I've marked your task as not done:\n");
                            System.out.println("\t ["
                                    + tasks.get(index - 1).getStatusIcon() + "] "
                                    + tasks.get(index - 1).getDescription());
                        }
                        else  {
                            drawBorder();
                            System.out.println("\t すみません🙇‍♀️ This index is invalid!\n");
                        }
                        drawBorder();
                        break;
                    }
                    catch (NumberFormatException ignored) {}
                }
            case "bye":
                if (commandArgs.length == 1) {
                    isStillExecuting = false;
                    break;
                }
            default:
                tasks.add(new Task(command));
                drawBorder();
                System.out.println("\t はい! Added into the list of tasks: " + command + "\n");
                drawBorder();
                break;
            }
        }

        printByeMessage();
    }

    /**
     * Prints the bye message
     */
    private static void printByeMessage() {
        drawBorder();
        System.out.println("\t じゃあね👋\n");
        System.out.println("\t See you later!\n");
        drawBorder();
    }

    /**
     * Draws a border
     */
    private static void drawBorder() {
        System.out.println("\t____________________________________________________________\n");
    }

    /**
     * @return Displays the ASCII logo of Sallybot
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