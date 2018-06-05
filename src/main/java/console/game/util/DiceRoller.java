package console.game.util;

import lombok.Data;

import java.util.Random;

@Data
public class DiceRoller {

    private static final int NUM_SIDES = 6;

    private final Random random;

    private int diceOne;

    private int diceTwo;

    public DiceRoller() {
        this.diceOne = 1;
        this.diceTwo = 1;
        this.random = new Random();
    }

    public void rollDice(){
        diceOne = random.nextInt(NUM_SIDES) + 1;
        diceTwo = random.nextInt(NUM_SIDES) + 1;
    }

    public boolean isDouble(){
        return diceOne == diceTwo;
    }

    public int getSum(){
        return diceOne + diceTwo;
    }

    @Override
    public String toString(){
        return "[" + diceOne + "] [" + diceTwo + "]";
    }

}
