package models;

import services.Printer;

import java.util.LinkedList;

public class Queue {

    private LinkedList<Duck> ducks;
    private boolean duckIsMoved;
    private String info;

    public Queue() {
        this.ducks = new LinkedList<>();
        duckIsMoved = false;
        info = "Queue consists of:\n\n";
    }

    public boolean putDuckInQueue(Duck duck) {
        try {
            ducks.add(duck);
            if (!duckIsMoved) {
                removeFrontDuck();
                info += "\t\t" + "First duck in queue is duck number " + duck.getDuckId() + "!\n\n";
            }
            else {
                int place = ducks.size() + 1;
                info += "\t\tDuck number " + duck.getDuckId() + " is at placement " + place + "\n";
            }
        }
        catch (Exception e) {
            new Printer().writeErr("Couldn't put duck in queue...");
            return false;
        }
        return true;
    }

    private void removeFrontDuck() {
        ducks.remove(0);
        duckIsMoved = true;
    }

    public int getAmountOfDucks() {
        if (duckIsMoved) {
            return ducks.size()+1;
        }
        else {
            return ducks.size();
        }
    }

    public String getInfo() {
        return info;
    }

    public boolean isDuckMoved() {
        return duckIsMoved;
    }
}
