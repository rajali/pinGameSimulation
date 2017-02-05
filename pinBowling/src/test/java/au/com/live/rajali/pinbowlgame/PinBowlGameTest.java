package au.com.live.rajali.pinbowlgame;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PinBowlGameTest {

    @Test
    public void testConstructor() {
        PinBowlGame pinBowlGame = new PinBowlGame();
        assertEquals ("frame number for new Scorer is wrong", 1, pinBowlGame.getFrameNumber());
        assertEquals ("score for new Scorer is wrong", 0, pinBowlGame.getScoreSoFar( ));
    }

    @Test
    public void test1stRoll () {
        PinBowlGame pbg =  new PinBowlGame();
        int[] result = pbg.roll(1);

        assertEquals("result of 1st roll is wrong", 0, result.length);
        assertEquals("frame # after 1st ball is wrong", 1, pbg.getFrameNumber());
        assertEquals("score after first ball is wrong", 0, pbg.getScoreSoFar());
    }

    //Test second roll that leaves pins standing
    @Test
    public void testFrame1Miss () {
        PinBowlGame pbg =  new PinBowlGame();
        int[] result = pbg.roll(1);
        result = pbg.roll(2);

        assertEquals("bad result after frame 1", 1, result.length);
        assertEquals("bad result[0] after frame 1", 3, result[0]);
        assertEquals("frame # after frame 1 is wrong", 2, pbg.getFrameNumber());
        assertEquals("score after frame 1 is wrong", 3, pbg.getScoreSoFar());
    }

    @Test
    public void test3rdBall () {
        PinBowlGame pbg =  new PinBowlGame();
        int[] result;
        result = pbg.roll(1);
        result = pbg.roll(2);
        result = pbg.roll(4);

        assertEquals("bad result after ball 3", 1, result.length);
        assertEquals("bad result[0] after ball 3", 3, result[0]);
        assertEquals("frame # after ball 3 is wrong", 2, pbg.getFrameNumber());
        assertEquals("score after ball 3 is wrong", 3, pbg.getScoreSoFar());
    }

    @Test
    public void testFrame2Miss () {
        PinBowlGame pbg =  new PinBowlGame();
        int[] result = pbg.roll(1);
        result = pbg.roll(2);
        result = pbg.roll(4);
        result = pbg.roll(1);

        assertEquals("bad result after frame 2", 2, result.length);
        //The above test fails
        /*
        The length of the result array after four rolls is 1 rather than 2
        The problem is that frameScores must grow as the game progresses, and
        must also retain scores of earlier frames.
         */

        assertEquals("bad result[0] after frame 2", 3, result[0]);
        assertEquals("bad result[1] after frame 2", 8, result[1]);
        assertEquals("frame # after frame 2 is wrong", 3, pbg.getFrameNumber());
        assertEquals("score after frame 2 is wrong", 8, pbg.getScoreSoFar());
    }

    @Test
    public void testStrikeStrikeMiss ( ) {
        PinBowlGame s = new PinBowlGame();
        int[] balls = {10, 10, 3, 4};
        int[] lengths = {0, 0, 1, 3};
        int[] frames = {2, 3, 3, 4};
        int[] finalResult = {23, 40, 47};
        testGame (s, balls, lengths, frames, finalResult);
    }

    @Test
    public void testSpareSpareMiss ( ) {
        PinBowlGame s = new PinBowlGame();
        int[] balls = {6, 4, 3, 7, 5, 2};
        int[] lengths = {0, 0, 1, 1, 2, 3};
        int[] frames = {1, 2, 2, 3, 3, 4};
        int[] finalResult = {13, 28, 35};
        testGame (s, balls, lengths, frames, finalResult);
    }

    @Test
    public void testExampleInput1FourMissBalls() {
        PinBowlGame pinBowlGame = new PinBowlGame();
        int[] pins = {1, 2, 3, 4};
        int[] lengths = {0, 1, 1, 2};
        int[] frames = {1, 2, 2, 3};
        int[] finalResult = {3, 10};
        testGame(pinBowlGame, pins, lengths, frames, finalResult);
    }

    //This will only check result as 19 since, the next spare's score was never finalised; since missing a turn.
    @Test
    public void testExampleInput2SpareSpare() {
        PinBowlGame pinBowlGame = new PinBowlGame();
        int[] pins = {9, 1, 9, 1};
        int[] lengths = {0, 0, 1, 1};
        int[] frames = {1, 2, 2, 3};
        int[] finalResult = {19, 29};
        testGame(pinBowlGame, pins, lengths, frames, finalResult);
    }

    @Test
    public void testExample2ModifiedSpareSpareMiss() {
        PinBowlGame pinBowlGame = new PinBowlGame();
        int[] pins = {9, 1, 9, 1, 0};
        int[] lengths = {0, 0, 1, 1, 2};
        int[] frames = {1, 2, 2, 3, 3};
        int[] finalResult = {19, 29};
        testGame(pinBowlGame, pins, lengths, frames, finalResult);
    }

    @Test
    public void testExampleInput3MissMissStrikeMiss() {
        PinBowlGame pinBowlGame = new PinBowlGame();
        int[] pins = {1, 1, 1, 1, 10, 1, 1};
        int[] lengths = {0, 1, 1, 2, 2, 2, 4};
        int[] frames = {1, 2, 2, 3, 4, 4, 5};
        int[] finalResult = {2, 4, 16, 18};
        testGame(pinBowlGame, pins, lengths, frames, finalResult);
    }

    @Test
    public void testExampleInput4AllStrike() {
        PinBowlGame pinBowlGame = new PinBowlGame();
        int[] pins = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        int[] lengths = {0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] frames = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 11};
        int[] finalResult = {30, 60, 90, 120, 150, 180, 210, 240, 270, 300};
        testGame(pinBowlGame, pins, lengths, frames, finalResult);
    }

    private void testGame(PinBowlGame game, int[] pins, int [] lengths, int [] frames, int [ ] finalResult) {
        int [] result;
        for (int k = 0; k < pins.length; k++) {
            result = game.roll(pins[k]);
            assertEquals ("checking length of result of game " + k, lengths[k], result.length);

            for (int j = 0; j < result.length; j++) {
                if (j==1) {
                    assertEquals ("checking frame " + j + " in result of game " + k, finalResult[j], result[j]);
                }
                assertEquals ("checking frame " + j + " in result of game " + k, finalResult[j], result[j]);
            }
            assertEquals ("checking frame number after ball " + k, frames[k], game.getFrameNumber());

            if (lengths[k] == 0) {
                assertEquals ("checking score after ball " + k, 0, game.getScoreSoFar());
            } else {
                assertEquals ("checking score after ball " + k, result[result.length-1], game.getScoreSoFar());
            }
        }
    }
}