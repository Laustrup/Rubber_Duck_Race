package models;

import services.Printer;

import java.util.*;

public class Game {

    // Attributes
    private LinkedList<Queue> queues;
    private ArrayList<Duck> ducks = new ArrayList<>();
    private TreeSet<Integer> duckIdsRemoved = new TreeSet<>();

    private String gameDetails = new String();
    private int playersDuckId;
    private int round;

    private boolean gg = false;

    // Values predefined for code
    private final static int startAmountOfQueues = 10;

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

        putDucksInQueues();

        gatherRoundInfo();
    }

    public void showGameDetails() {
        new Printer().writeMessage(gameDetails);
    }

    public void nextRound() {

        round++;

        updateValuesForNewRound();

        putDucksInQueues();

        gatherRoundInfo();
    }

    private void updateValuesForNewRound() {
        // Creating a new amount of empty queues
        LinkedList<Queue> tempQueues = new LinkedList<>();
        for (int i = 0; i < queues.size()-1; i++) {
            tempQueues.add(new Queue());
        }
        queues = tempQueues;

        // Creating new ducks of remaining ducks
        ArrayList<Duck> tempDucks = new ArrayList<>();
        for (int i = 0; i < ducks.size();i++) {
            if (!duckIdsRemoved.contains(ducks.get(i).getDuckId())) {
                tempDucks.add(ducks.get(i));
            }
        }
        ducks = tempDucks;

        gameDetails = new String();
    }

    private void putDucksInQueues() {
        HashSet<Integer> duckIds = new HashSet<>();

        for (int i = 0; i < ducks.size(); i++) {
            boolean duckIdIsValid = false;

            while (!duckIdIsValid) {

                int queuesIndex = new Random().nextInt(startAmountOfQueues-round+1);
                Duck duck = ducks.get(new Random().nextInt(ducks.size()));

                 // Only puts ducks in if queue ain't full, and id isn't already taken
                //  The id is only added to the set of ids, if duck is added
                if (queues.get(queuesIndex).getAmountOfDucks()<startAmountOfQueues-round+1 &&
                        !duckIds.contains(duck.getDuckId())) {
                    duckIdIsValid = checkIfRandomDuckIdIsValid(queuesIndex,duck);
                    if (duckIdIsValid) {
                        duckIds.add(duck.getDuckId());
                    }
                }

                 // Todo
                // Checks if there is any ducks left
                if (i+1 > queues.size()*queues.size() && !duckIds.contains(duck.getDuckId())) {
                    for (int j = 0; j < ducks.size();j++) {
                        if (!isDuckInQueues(ducks.get(j))) {
                            duckIds.add(duck.getDuckId());
                        }
                    }
                    break;
                }
            }
        }
    }

    private boolean checkIfRandomDuckIdIsValid(int queuesIndex,Duck duck) {
        boolean duckIsFirst = false;

        for (int i = 0; i < ducks.size();i++) {

            // Checks if the id of the duck is in ducks
            if (ducks.get(i).getDuckId() == duck.getDuckId()) {

                // Checks if duck is first
                if (!queues.get(queuesIndex).isDuckMoved() && !duckIdsRemoved.contains(duck.getDuckId())) {

                    // Checks if the first is the player's duck
                    if (duck.getDuckId()==playersDuckId) {
                        gg = true;
                    }

                    duckIdsRemoved.add(duck.getDuckId());
                    duckIsFirst = true;
                }

                if (!duckIdsRemoved.contains(duck.getDuckId()) || duckIsFirst) {
                    queues.get(queuesIndex).putDuckInQueue(duck);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isDuckInQueues(Duck duck) {

        boolean duckIsFound = false;

        for (int i = 0; i < queues.size();i++) {
            for (int j = 0; j < queues.get(i).getDucks().size();j++) {
                if (duck.getDuckId() == queues.get(i).getDucks().get(j).getDuckId()) {
                    duckIsFound = true;
                    break;
                }
            }
            if (duckIsFound) {
                break;
            }
            if (i == queues.get(i).getDucks().size()-1 && !duckIsFound) {
                duckIdsRemoved.add(duck.getDuckId());
            }
        }
        return duckIsFound;
    }

    private void gatherRoundInfo() {
        for (int i = 0; i < queues.size();i++) {
            int queueNumber = i+1;
            gameDetails += "\nQueue number " + queueNumber + "\n" + queues.get(i).getInfo();
        }
        if (gg) {
            gameDetails += "\n\n\tYou're duck was removed at round " + round + "!\n";
        }
    }

    public boolean hasWon() {
        return gg;
    }

}
