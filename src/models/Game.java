package models;

import services.Printer;

import java.util.ArrayList;
import java.util.HashSet;
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

        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */

        putDucksInQueues();

        gatherRoundInfo();
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

        gameDetails = new String();

        play();
    }

    private void play() {

        HashSet<Integer> duckIds = new HashSet<>();
        int queueLength = startAmountOfQueues-round-1;

        putDucksInQueues();

        gatherRoundInfo();
    }

    // Way too big big O...
    private void putDucksInQueues() {
        HashSet<Integer> duckIds = new HashSet<>();

        for (int i = 0; i < ducks.size(); i++) {
            boolean duckIdIsvalid = false;

            while (!duckIdIsvalid) {

                int queuesIndex = new Random().nextInt(startAmountOfQueues-round+1);
                Duck duck = ducks.get(new Random().nextInt(ducks.size()));

                 // Only puts ducks in if queue ain't full, and id isn't already taken
                //  The id is only added to the set of ids, if duck is added
                if (queues.get(queuesIndex).getAmountOfDucks()<queues.size() &&
                        !duckIds.contains(duck.getDuckId())) {
                    duckIdIsvalid = checkIfRandomDuckIdIsValid(queuesIndex,duck,duckIds);
                    if (duckIdIsvalid) {
                        duckIds.add(duck.getDuckId());
                    }
                }
            }
        }
    }

    private boolean checkIfRandomDuckIdIsValid(int queuesIndex,Duck duck,HashSet<Integer> duckIds) {
        for (int i = 0; i < ducks.size();i++) {

            // Checks if the id of the duck is in ducks
            if (ducks.get(i).getDuckId() == duck.getDuckId()) {

                // Checks if duck is first
                if (!queues.get(queuesIndex).isDuckMoved()) {

                    // Checks if the first is the player's duck
                    if (duck.getDuckId()==playersDuckId) {
                        gg = true;
                    }
                    ducks.remove(duck.getDuckId()-1);
                }
                queues.get(queuesIndex).putDuckInQueue(duck);
                return true;
            }
        }
        return false;
    }

    private void gatherRoundInfo() {
        for (int i = 0; i < queues.size();i++) {
            int queueNumber = i+1;
            gameDetails += "\nQueue number " + queueNumber + "\n" + queues.get(i).getInfo();
        }
        if (gg) {
            gameDetails += "\n\n\tYour're duck was removed at round " + round + "!\n";
        }
    }

    public boolean hasWon() {
        return gg;
    }

}
