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

    public void menu() {

        while (true) {

            boolean typedIsValid = false;

            printer.writeMessage("Welcome to Rubber Duck Race!");

            while (!typedIsValid) {
                printer.writeMessage("What do you wish to do?:\n\n" +

                                    "_______________________________\n" +
                                    "\tPlay game\n" +
                                    "\tExit\n" +
                                    "\tExist\n" +
                                    "_______________________________");

                String typed = scanner.nextLine();

                typedIsValid = analyst.analyseMenuChoice(typed);

            }
        }
    }

    public String pendAnotherRound() {
        printer.writeMessage("Up for another round?\n(Type yes/y to restart or no/n to exit)\n");
        return new Scanner(System.in).nextLine();
    }



}
