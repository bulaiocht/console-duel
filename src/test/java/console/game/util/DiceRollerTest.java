package console.game.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Assertions;

class DiceRollerTest {

    private static DiceRoller roller;

    @BeforeAll
    static void setUp(){
        roller = new DiceRoller();
    }

    @RepeatedTest(100)
    void rollDiceTest(){
        roller.rollDice();
        assertThat(roller.getDiceOne()).isBetween(1, 6);
        assertThat(roller.getDiceTwo()).isBetween(1, 6);
    }

}