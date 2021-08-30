package boilerplates;

import services.Analyst;
import services.GameService;
import services.Printer;

import java.util.Scanner;

public class Controller {

    private Scanner scanner = new Scanner(System.in);

    private Printer printer = new Printer();
    private GameService gameService = new GameService();
    private Analyst analyst = new Analyst();

    public void start() {

        printer.writeMessage("Welcome to Rubber Duck Race!");
        menu();
    }

    public void menu() {
        while (true) {

            boolean typedIsValid = false;

            while (!typedIsValid) {
                printer.writeMessage("What do you wish to do?:\n\n" +

                                    "_______________________________\n" +
                                    "\tPlay game\n" +
                                    "\tView chances\n" +
                                    "\tExist\n" +
                                    "\tExit\n" +
                                    "_______________________________");

                String typed = scanner.nextLine();

                typedIsValid = analyst.analyseMenuChoice(typed);

            }
        }
    }

}
