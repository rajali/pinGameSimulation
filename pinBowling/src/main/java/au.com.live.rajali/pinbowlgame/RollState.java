package au.com.live.rajali.pinbowlgame;

public enum RollState {
    ROLLING_FIRST_BALL(0),
    ROLLING_SECOND_BALL(1),
    STRIKE_LAST_BALL(2),
    TWO_CONSEC_STRIKES(3),
    STRIKE_2_BALLS_AGO(4),
    SPARE_LAST_BALL(5);

    private int value;

    RollState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
