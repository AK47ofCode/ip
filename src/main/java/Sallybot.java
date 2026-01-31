import java.util.Scanner;

public class Sallybot {
    public static void main(String[] args) {
        boolean isStillExecuting = true;
        String logo = getLogo();
        drawBorder();
        System.out.println(logo + "\n");
        System.out.println("\t🌸こんにちは🌸\n");
        System.out.println("\tHello there✨ It's Sallybot! Always here to help hehe\n");
        System.out.println("\tWhat can I do for you today?\n");
        drawBorder();

        while (isStillExecuting) {
            Scanner input = new Scanner(System.in);
            String command = input.nextLine();

            switch (command) {
            case "bye":
                isStillExecuting = false;
                break;
            default:
                drawBorder();
                System.out.println("\t" + command + "\n");
                drawBorder();
                break;
            }
        }

        printByeMessage();
    }

    private static void printByeMessage() {
        drawBorder();
        System.out.println("\tじゃあね👋\n");
        System.out.println("\tSee you later!\n");
        drawBorder();
    }

    private static void drawBorder() {
        System.out.println("\t____________________________________________________________\n");
    }

    private static String getLogo() {
        return """
                \t ____   __   __    __    _  _  ____   __  ____\s
                \t/ ___) / _\\ (  )  (  )  ( \\/ )(  _ \\ /  \\(_  _)
                \t\\___ \\/    \\/ (_/\\/ (_/\\ )  /  ) _ ((  O ) )(
                \t(____/\\_/\\_/\\____/\\____/(__/  (____/ \\__/ (__)\s
                """;
    }
}