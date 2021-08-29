package models;

import services.Printer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Game {

    // Attributes
    private LinkedList<Queue> queues;
    private ArrayList<Duck> ducks = new ArrayList<>();

    private String gameDetails = new String();
    private int playersDuckId;
    private int round;

    private boolean gg = false;

    // Values predefined for code
    private final static int startAmountOfQueues = 10;

    public Game(int playersDuckId) {
        queues = new LinkedList<>();
        for (int i = 0; i < startAmountOfQueues; i++) {
            queues.add(new Queue());
        }
        this.playersDuckId = playersDuckId;
        createDucks();
    }

    public Game(boolean begin, int playersDuckId) {
        queues = new LinkedList<>();
        for (int i = 0; i < startAmountOfQueues; i++) {
            queues.add(new Queue());
        }
        this.playersDuckId = playersDuckId;
        createDucks();
        if (begin) {
            begin();
        }
    }

    private void createDucks() {
        for (int i = 0; i < startAmountOfQueues*startAmountOfQueues;i++) {
            ducks.add(new Duck(i+1));
        }
    }

    public void begin() {
        gameDetails += "Game has begun! Details are:\n\n";
        round = 1;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Puts ducks in queues
        for (int i = 0; i < startAmountOfQueues*startAmountOfQueues; i++) {
            while (true) {
                int queuesIndex = new Random().nextInt(startAmountOfQueues);
                Duck duck = new Duck(i+1);

                // TODO, not 10 for each, the same for play...
                if (queues.get(queuesIndex).getAmountOfDucks()<queues.size()) {
                    if (!queues.get(queuesIndex).isDuckMoved()) {
                        if (duck.getDuckId()==playersDuckId) {
                            gg = true;
                        }
                        ducks.remove(duck.getDuckId()-1);
                    }
                    queues.get(queuesIndex).putDuckInQueue(duck);
                    break;
                }
            }
        }

        // Gathers infos about round
        for (int i = 0; i < queues.size();i++) {
            int queueNumber = i+1;
            gameDetails += "\nQueue number " + queueNumber + "\n" + queues.get(i).getInfo();
        }
        if (gg) {
            gameDetails += "\n\n\tYour're duck was removed at round " + round + "!\n";
        }
    }

    public void showGameDetails() {
        new Printer().writeMessage(gameDetails);
    }

    public void nextRound() {

        round++;

        // Creating a new amount of empty queues
        LinkedList<Queue> tempQueues = new LinkedList<>();
        for (int i = 0; i < queues.size()-1; i++) {
            tempQueues.add(new Queue());
        }
        queues = tempQueues;

        play();
    }

    //TODO
    private void play() {
        // Puts ducks in queues
        for (int i = 0; i < ducks.size(); i++) {
            while (true) {
                int queuesIndex = new Random().nextInt(startAmountOfQueues-round+1);

                if (queues.get(queuesIndex).getAmountOfDucks()<queues.size()) {
                    if (!queues.get(queuesIndex).isDuckMoved()) {
                        if (ducks.get(i).getDuckId()==playersDuckId) {
                            gg = true;
                        }
                        ducks.remove(ducks.get(i).getDuckId()-1);
                    }
                    queues.get(queuesIndex).putDuckInQueue(ducks.get(i));
                    break;
                }
            }
        }

        // Gathers info about round
        for (int i = 0; i < queues.size();i++) {
            gameDetails += "\nQueue number " + i+1 + "\n" + queues.get(i).getInfo();
        }
        if (gg) {
            gameDetails += "\n\n\tYour're duck was removed at round " + round + "!\n";
        }
    }

    public boolean hasWon() {
        return gg;
    }

}
