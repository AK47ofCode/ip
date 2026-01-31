import java.util.ArrayList;
import java.util.Scanner;

public class Sallybot {
    public static final int MAX_NUM_OF_TASKS = 100;

    public static void main(String[] args) {
        boolean isStillExecuting = true;
        ArrayList<String> tasks = new ArrayList<String>();
        String logo = getLogo();

        drawBorder();
        System.out.println(logo + "\n");
        System.out.println("\t 🌸こんにちは🌸\n");
        System.out.println("\t Hello there✨ It's Sallybot! Always here to help hehe\n");
        System.out.println("\t What can I do for you today?\n");
        drawBorder();

        while (isStillExecuting) {
            Scanner input = new Scanner(System.in);
            String command = input.nextLine();

            switch (command) {
            case "list":
                drawBorder();
                for (String task : tasks) {
                    System.out.println("\t " + (tasks.indexOf(task) + 1) + ". " + task);
                }
                drawBorder();
                break;
            case "bye":
                isStillExecuting = false;
                break;
            default:
                tasks.add(command);
                drawBorder();
                System.out.println("\t Added into the list of tasks: " + command + "\n");
                drawBorder();
                break;
            }
        }

        printByeMessage();
    }

    private static void printByeMessage() {
        drawBorder();
        System.out.println("\t じゃあね👋\n");
        System.out.println("\t See you later!\n");
        drawBorder();
    }

    private static void drawBorder() {
        System.out.println("\t____________________________________________________________\n");
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