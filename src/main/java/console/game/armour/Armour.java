package console.game.armour;

import console.game.attack.Attack;
import lombok.Data;

@Data
public class Armour {

    private boolean broken;

    private int durability;

    private int protection;

    public Armour(){}

    public Armour(int durability, int protection) {
        this.durability = durability;
        this.protection = protection;
        this.broken = false;
    }

    public Attack protect(Attack attack){
        final int damage = attack.calculateDamage();
        attack.setDamage(filterDamage(damage));
        return attack;
    }

    protected int filterDamage(int damage){
        int shave = damage - protection;
        if (shave >= 0){
            return shave;
        } else return 0;
    }

}