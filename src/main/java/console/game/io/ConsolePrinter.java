package console.game.io;

import console.game.npc.NPC;
import console.game.util.DiceRoller;

public class ConsolePrinter {

    public static final String PICK_NAME = "Player # %d chose your name: \n";
    public static final String PICK_CHARACTER = "Player # %d pick your character: \n";
    public static final String NO_SUCH_CHARACTER_TYPE = "No character type %s \n";
    public static final String NO_SUCH_ACTION = "No such action type %s \n";

    public static final String PLAYER_ATTACK = "%s attacking %s!\n";
    public static final String CHOSE_COUNTER = "Chose your action, %s: \n";
    public static final String CRITICAL_ATTACK = "Critical attack!\n";
    //public static final String CRITICAL_HIT = "Critical hit! -%d points!";
    public static final String DAMAGE = "%s damaged! -%d points!\n";
    public static final String BLOCKED = "Attack blocked!\n";
    public static final String NOT_BLOCKED = "Attack not blocked!\n";
    public static final String PARRIED = "Attack parried!\n";
    public static final String NOT_PARRIED = "Attack not parried!\n";
    public static final String DICE_ROLL = "Dice rolled: %s \n";
    public static final String TRY_PARRY = "%s trying to parry attack!\n";
    public static final String TRY_BLOCK = "%s trying to block attack!\n";
    public static final String COUNTER_ATTACK = "%s counter attacking!\n";

    public static final String AVAILABLE_CHARACTERS = "Mercenary: \"M\" or Thief: \"T\"\n";
    public static final String AVAILABLE_ACTIONS = "Block: \"B\" or Parry: \"P\"\n";
    public static final String MERCENARY = "M";
    public static final String THIEF = "T";
    public static final String BLOCK = "B";
    public static final String PARRY = "P";

    public static final String WINNER = "Winner winner chicken dinner! Congrats, %s!";

    public void printMessage(String message){
        System.out.print(message);
    }

    public void printMessage(String message, Object...arguments){
        System.out.printf(message, arguments);
    }

    public void printHealth(NPC first, NPC second){
        final String firstName = first.getName();
        final int firstHealth = first.getHealth();
        final String secondName = second.getName();
        final int secondHealth = second.getHealth();
        System.out.println("=============================");
        System.out.printf("%s: %d   vs   %s: %d \n", firstName, firstHealth, secondName, secondHealth);
        System.out.println("=============================");
    }

    public void winner(NPC player) {
        System.out.printf(WINNER, player.getName());
    }
}
