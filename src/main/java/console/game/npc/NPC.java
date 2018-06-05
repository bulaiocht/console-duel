package console.game.npc;

import console.game.armour.Armour;
import console.game.attack.Attack;
import console.game.weapon.Weapon;
import lombok.Data;

import java.util.Random;

@Data
public class NPC {

    private String name;

    private int health;

    private Weapon weapon;

    private Armour armour;

    public Attack attack(NPC target){
        return new Attack(this, target);
    }

    public Attack parry(Attack attack){
        attack.setParried(true);
        return attack;
    }

    public Attack block(Attack attack){
        attack.setBlocked(true);
        return attack;
    }

    public void takeDamage(Attack attack){
        this.health -= attack.calculateDamage();
    }

    public int critical(int initial){
        return weapon.getCriticalDamage();
    }

    protected boolean lucky(int chance){
        Random random = new Random();
        return random.nextInt(101) <= chance;
    }
}
