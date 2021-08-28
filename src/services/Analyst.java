package services;

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
}
