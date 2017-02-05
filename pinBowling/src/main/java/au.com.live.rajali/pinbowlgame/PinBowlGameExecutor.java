package au.com.live.rajali.pinbowlgame;

import au.com.live.rajali.pinbowlgame.exception.RollException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PinBowlGameExecutor {

    public static void main(String[] args) {

        System.out.println("Please enter space separated no. of pins hit on each roll");

        Scanner scanner = new Scanner(System.in);
        List<Integer> ballPins = new ArrayList<>();
        while (scanner.hasNextInt()) {
            ballPins.add(scanner.nextInt());
        }

        PinBowlGame pinBowlGame = new PinBowlGame();
        try {
            for (Integer roll: ballPins) {
                pinBowlGame.roll(roll);
            }

            System.out.println("The score for the input ball rolls is: " + pinBowlGame.getScoreSoFar());
        } catch(RollException ex) {
            System.out.println("Illegal State has occurred, score so far is: " + pinBowlGame.getScoreSoFar());
        }
    }
}
