package au.com.live.rajali.pinbowlgame;

import au.com.live.rajali.pinbowlgame.exception.RollException;
import static au.com.live.rajali.pinbowlgame.RollState.*;

public class PinBowlGame {

    private static final int MAX_PINS = 10;
    private static final int MAX_FRAMES = 10;

    private int rollingFrame;
    private int[] frameScores;
    private int lastFrameNumber;
    private int firstBallInFrame;
    private RollState rollState;
    private int totalScore;

    public PinBowlGame() {
        lastFrameNumber = MAX_FRAMES;
        rollState = ROLLING_FIRST_BALL;
        rollingFrame = 1;
        totalScore = 0;
        frameScores = new int[0];
    }

    public int[] roll(final int ball) {
        switch (rollState) {
            case ROLLING_FIRST_BALL:
                if (ball == MAX_PINS) {
                    rollingFrame++;
                    rollState = STRIKE_LAST_BALL;
                } else {
                    firstBallInFrame = ball;
                    rollState = ROLLING_SECOND_BALL;
                }
                break;

            case ROLLING_SECOND_BALL:
                if (firstBallInFrame + ball == MAX_PINS) {
                    rollingFrame++;
                    rollState = SPARE_LAST_BALL;
                } else {
                    rollingFrame++;
                    addFrame(firstBallInFrame + ball);
                    rollState = ROLLING_FIRST_BALL;
                }
                break;

            case SPARE_LAST_BALL:
                addFrame(MAX_PINS + ball);
                if (ball == MAX_PINS) {
                    rollingFrame++;
                    rollState = SPARE_LAST_BALL;
                } else {
                    firstBallInFrame = ball;
                    rollState = ROLLING_SECOND_BALL;
                }
                break;

            case STRIKE_LAST_BALL:
                if (ball == MAX_PINS) {
                    rollingFrame++;
                    rollState = TWO_CONSEC_STRIKES;
                } else {
                    firstBallInFrame = ball;
                    rollState = STRIKE_2_BALLS_AGO;
                }
                break;

            case TWO_CONSEC_STRIKES:
                addFrame(20 + ball);
                if (ball == MAX_PINS) {
                    rollingFrame++;
                } else {
                    firstBallInFrame = ball;
                    rollState = STRIKE_2_BALLS_AGO;
                }
                break;

            case STRIKE_2_BALLS_AGO:
                addFrame(MAX_PINS + firstBallInFrame + ball);
                if (firstBallInFrame + ball == MAX_PINS) {
                    rollingFrame++;
                    rollState = SPARE_LAST_BALL;
                } else {
                    rollingFrame++;
                    addFrame(firstBallInFrame + ball);
                    rollState = ROLLING_FIRST_BALL;
                }
                break;

            default:
                throw new RollException("Invalid state: " + rollState.name());
        }
        return  frameScores;
    }

    public int getFrameNumber() {

        if (frameScores.length == lastFrameNumber) {
            //game ends
            return lastFrameNumber + 1;
        } else if (rollingFrame > lastFrameNumber) {
            //last frame
            return lastFrameNumber;
        } else {
            return rollingFrame;
        }
    }

    public int getScoreSoFar() {
        if (frameScores.length == lastFrameNumber) {
            return frameScores[lastFrameNumber - 1];
        } else {
            return totalScore;
        }
    }

    private void addFrame(int toAdd) {
        totalScore = totalScore  + toAdd;
        if (frameScores.length < lastFrameNumber) {
            int [] temp = new int [frameScores.length + 1];
            System.arraycopy(frameScores, 0, temp, 0, frameScores.length);
            temp[frameScores.length] = totalScore;
            frameScores = temp;
        }
    }
}