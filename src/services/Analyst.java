package services;

import boilerplates.Controller;

public class Analyst {

    private Printer printer = new Printer();

    public boolean analyseChosenDuckId(String typed) {
        int duckId = 0;
        try {
            duckId = Integer.parseInt(typed);
        }
        catch (Exception e) {
            printer.writeErr("That is not an number...");
            return false;
        }

        if (duckId <= 100 && duckId > 0) {
            return true;
        }
        else {
            printer.writeErr("That is not between 1 and 100...");
        }
        return false;
    }

    public boolean analyseMenuChoice(String input) {
        if (input.equalsIgnoreCase("Play game")) {
            new GameService().playMenu();
            new Controller().menu();
        }
        else if (input.equalsIgnoreCase("Exit")) {
            printer.writeMessage("Goodbye...");
            System.exit(1);
        }
        else if (input.equalsIgnoreCase("Exist")) {
            printer.writeMessage("There you go <3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            printer.writeErr("Whoops, that is not an option...");
        }
        return false;
    }
}
