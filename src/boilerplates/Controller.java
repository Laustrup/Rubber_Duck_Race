package boilerplates;

import services.GameService;
import services.Printer;

import java.util.Scanner;

public class Controller {

    private Scanner scanner = new Scanner(System.in);
    private Printer printer = new Printer();
    private GameService gameService = new GameService();

    public void menu() {

        while (true) {

            boolean typedIsValid = false;

            printer.writeMessage("Welcome to Rubber Duck Race!\n");

            while (!typedIsValid) {
                printer.writeMessage("What do you wish to do?:\n\n" +

                                    "\tPlay game\n" +
                                    "\tExit\n");

                String typed = scanner.nextLine();

                if (typed.equalsIgnoreCase("Play game")) {
                    gameService.playMenu();
                    typedIsValid = true;
                }
                else if (typed.equalsIgnoreCase("Exit")) {
                    System.exit(1);
                }
                else {
                    printer.writeErr("Whoops, that is not an option...");
                }
            }
        }
    }



}
