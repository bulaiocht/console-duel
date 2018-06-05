package console.game.logic;

import console.game.armour.BigShield;
import console.game.attack.Attack;
import console.game.config.Config;
import console.game.io.ConsoleReader;
import console.game.io.ConsolePrinter;
import console.game.npc.Mercenary;
import console.game.npc.NPC;
import console.game.npc.Thief;
import console.game.util.DiceRoller;
import console.game.weapon.Weapon;

import static console.game.io.ConsolePrinter.*;

public class Game {

    /**
     * Rolls your dices.
     */
    private DiceRoller diceRoller;

    /**
     * Prints console messages.
     */
    private ConsolePrinter printer;

    /**
     * Reads input.
     */
    private ConsoleReader reader;

    /**
     * First player.
     */
    private NPC playerOne = null;

    /**
     * Second player/
     */
    private NPC playerTwo = null;

    public Game() {
        this.diceRoller = new DiceRoller();
        this.reader = ConsoleReader.getInstance();
        this.printer = new ConsolePrinter();
    }

    /**
     * Runs the game.
     */
    public void run() {

        setUpFight();

        boolean fighting = true;

        NPC attacker = playerOne;

        NPC target = playerTwo;

        while (fighting){

            printer.printHealth(playerOne, playerTwo);

            Attack attack = attacker.attack(target);

            action(attack);

            analyze(counterAction(attack));

            //Checking if fight is still going.
            fighting = stillFighting(attacker, target);

            if (fighting){
                NPC temp = attacker;
                attacker = target;
                target = temp;
            } else {
                announceWinner();
                reader.stopReading();
            }
        }


    }

    private void action(Attack attack) {

        printer.printMessage(PLAYER_ATTACK,
                attack.getAttacker().getName(), attack.getTarget().getName());

        diceRoller.rollDice();

        printer.printMessage(DICE_ROLL, diceRoller);

        attack.setAttackRoll(diceRoller.getSum());
        if (diceRoller.isDouble()){
            attack.setCritical(true);
            printer.printMessage(CRITICAL_ATTACK);
        }
    }

    /**
     * Prints the winner.
     */
    private void announceWinner() {
        int healthOne = playerOne.getHealth();
        int healthTwo = playerTwo.getHealth();
        if (healthOne>healthTwo){
            printer.printMessage(WINNER, playerOne.getName());
        } else printer.printMessage(WINNER, playerTwo.getName());
    }

    /**
     * Checks if fight can continue.
     * @param attacker current attacker.
     * @param target current target.
     * @return {@code true} if fight can continue and {@code false} otherwise
     */
    private boolean stillFighting(NPC attacker, NPC target) {
        return attacker.getHealth() > 0 && target.getHealth() > 0;
    }

    /**
     * Analyzes current attack and apply damage to parties.
     * @param attack current attack
     */
    private void analyze(Attack attack) {

        NPC attacker = attack.getAttacker();

        NPC target = attack.getTarget();

        if (attack.isParried()){

            Attack counterAttack = target.attack(attacker);
            printer.printMessage(ConsolePrinter.COUNTER_ATTACK, target.getName());
            int takenDamage = counterAttack.calculateDamage();
            attacker.takeDamage(counterAttack);
            printer.printMessage(ConsolePrinter.DAMAGE, attacker.getName(), takenDamage);

        } else {
            int damage = attack.calculateDamage();
            target.takeDamage(attack);
            printer.printMessage(ConsolePrinter.DAMAGE, target.getName(), damage);
        }

    }

    /**
     * Assigns characters and names to players.
     */
    private void setUpFight() {

        printer.printMessage(AVAILABLE_CHARACTERS);
        printer.printMessage(PICK_CHARACTER, 1);
        playerOne = setCharacter();

        printer.printMessage(AVAILABLE_CHARACTERS);
        printer.printMessage(PICK_CHARACTER, 2);
        playerTwo = setCharacter();

        printer.printMessage(PICK_NAME, 1);
        setCharacterName(playerOne);

        printer.printMessage(PICK_NAME,2);
        setCharacterName(playerTwo);

    }

    /**
     * Assigns character to player.
     * @return initialized {@link NPC} object
     */
    private NPC setCharacter() {
        while (true){
            String type;
            switch (type = reader.readInput().toUpperCase()){
                case MERCENARY:
                    return createNPC(Type.MERCENARY);
                case THIEF:
                    return createNPC(Type.THIEF);
                default:
                    printer.printMessage(NO_SUCH_CHARACTER_TYPE, type);
                    printer.printMessage(AVAILABLE_CHARACTERS);
                    break;
            }
        }
    }

    /**
     * Sets name to {@link NPC}
     * @param player current player.
     */
    private void setCharacterName(NPC player){
        String name = reader.readInput();
        if (name == null || name.trim().isEmpty()){
            player.setName("NONAME");
        } else player.setName(name.toUpperCase());
    }

    /**
     * Creates and initializes {@link Mercenary} or {@link Thief} object.
     * @param type {@link NPC} type.
     * @return one of the {@link NPC} objects.
     * @see Type
     */
    private NPC createNPC(Type type) {
        if (type.equals(Type.MERCENARY)) return createMercenary();
        if (type.equals(Type.THIEF)) return createThief();
        else return null;
    }

    /**
     * Creates {@link Mercenary} object.
     * @return {@link NPC}
     */
    private NPC createMercenary(){
        Mercenary mercenary = new Mercenary();
        mercenary.setHealth(Config.STANDARD_CHAR_HEALTH);
        mercenary.setRage(Config.STANDARD_RAGE);
        mercenary.setArmour(new BigShield());
        mercenary.setWeapon(new Weapon(
                Config.WEAP_DURAB,
                Config.WEAP_DAMAGE,
                Config.WEAP_CRIT
        ));
        return mercenary;
    }

    /**
     * Creates {@link Thief} object.
     * @return {@link NPC}
     */
    private NPC createThief(){
        Thief thief = new Thief();
        thief.setHealth(Config.STANDARD_CHAR_HEALTH);
        thief.setAcid(Config.STANDARD_ACID);
        thief.setArmour(new BigShield());
        thief.setWeapon(new Weapon(
                Config.WEAP_DURAB,
                Config.WEAP_DAMAGE,
                Config.WEAP_CRIT
        ));
        return thief;
    }

    /**
     * Performs player's counter-action.
     * @param attack current attack
     * @return attack
     */
    private Attack counterAction(Attack attack){
        printer.printMessage(CHOSE_COUNTER, attack.getTarget().getName());
        printer.printMessage(AVAILABLE_ACTIONS);

        while (true){
            String action;
            switch (action = reader.readInput().toUpperCase()){
                case PARRY:
                    return parryAttack(attack);
                case BLOCK:
                    return blockAttack(attack);
                default:
                    printer.printMessage(NO_SUCH_ACTION, action);
                    printer.printMessage(AVAILABLE_ACTIONS);
                    break;
            }
        }

    }

    /**
     * Performs blocking action.
     * @param attack current attack
     * @return attack
     */
    private Attack blockAttack(Attack attack) {
        final NPC target = attack.getTarget();
        printer.printMessage(ConsolePrinter.TRY_BLOCK, attack.getTarget().getName());
        diceRoller.rollDice();
        printer.printMessage(ConsolePrinter.DICE_ROLL, diceRoller);

        int blockSum = diceRoller.getSum();
        if (blockSum >= attack.getAttackRoll()){
            printer.printMessage(ConsolePrinter.BLOCKED);
            return target.block(attack);
        } else printer.printMessage(ConsolePrinter.NOT_BLOCKED);
        return attack;
    }

    /**
     * Performs parry action.
     * @param attack current attack
     * @return attack
     */
    private Attack parryAttack(Attack attack) {
        NPC target = attack.getTarget();
        printer.printMessage(ConsolePrinter.TRY_PARRY, attack.getTarget().getName());
        diceRoller.rollDice();
        printer.printMessage(ConsolePrinter.DICE_ROLL, diceRoller);

        int parrySum = diceRoller.getSum();
        if (parrySum > attack.getAttackRoll()){
            printer.printMessage(ConsolePrinter.PARRIED);
            return target.parry(attack);
        } else printer.printMessage(ConsolePrinter.NOT_PARRIED);
        return attack;
    }

    /**
     * Types of NPC's
     */
    private enum Type {

        MERCENARY,

        THIEF

    }

}
