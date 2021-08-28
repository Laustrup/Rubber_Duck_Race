package models;

import services.Printer;

import java.util.LinkedList;
import java.util.Random;

public class Game {

    // Attributes
    private LinkedList<Queue> queues;
    private String gameDetails = new String();

    // Values predefined for code
    private final static int startAmountOfQueues = 10;

    public Game() {
        queues = new LinkedList<>();
        for (int i = 0; i < startAmountOfQueues; i++) {
            queues.add(new Queue());
        }
    }

    public Game(boolean begin) {
        queues = new LinkedList<>();
        for (int i = 0; i < startAmountOfQueues; i++) {
            queues.add(new Queue());
        }
        if (begin) {
            begin();
        }
    }

    private void begin() {
        gameDetails += "Game has begun! Details are:\n\n";

        for (int i = 0; i < 100; i++) {
            while (true) {
                int queueId = new Random().nextInt(10);
                Duck duck = new Duck(i+1);

                if (queues.get(queueId).getAmountOfDucks()<=queues.size()) {
                    queues.get(queueId).putDuckInQueue(duck);
                    break;
                }
            }
        }
        for (int i = 0; i < queues.size();i++) {
            int queueNumber = i+1;
            gameDetails += "\nQueue number " + queueNumber + "\n" + queues.get(i).getInfo();
        }
    }

    public void showGameDetails() {
        new Printer().writeMessage(gameDetails);
    }

    public void nextRound() {

        /*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */

        LinkedList<Queue> tempQueues = new LinkedList<>();

        for (int i = 0; i < queues.size()-1; i++) {
            tempQueues.add(new Queue());
        }
        queues = tempQueues;

        play();
    }

    private void play() {
        for (int i = 0; i < queues.size()*queues.size(); i++) {
            while (true) {
                int queueId = new Random().nextInt(queues.size());
                Duck duck = new Duck(i+1);
                if (queues.get(queueId).getAmountOfDucks()<queues.size()) {
                    queues.get(queueId).putDuckInQueue(duck);
                    break;
                }
            }
        }
        for (int i = 0; i < queues.size();i++) {
            gameDetails += "Queue number " + i+1 + "\n" + queues.get(i).getInfo();
        }
    }

    public LinkedList<Queue> getQueues() {
        return queues;
    }

}
