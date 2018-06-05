package console.game.armour;

import console.game.attack.Attack;

public class ParryShield extends Armour {

    private final int chance;

    public ParryShield(int chance, int durability, int protection) {
        super(durability, protection);
        this.chance = chance;
    }

    @Override
    public Attack protect(Attack attack) {
        return null;
    }
}