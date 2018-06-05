package console.game.npc;

import console.game.attack.Attack;
import lombok.Data;

import java.util.Random;

@Data
public class Thief extends NPC {

    private static final int thiefChance = 20;

    private int acid;

    private Random random = new Random();

    @Override
    public Attack attack(NPC target) {
        Attack attack = super.attack(target);
        int weaponDamage = getWeapon().getDamage();
        attack.setDamage(weaponDamage);
        return attack;
    }

    @Override
    public Attack parry(Attack attack) {
        return super.parry(attack);
    }

    @Override
    public Attack block(Attack attack) {
        return getArmour().protect(super.block(attack));
    }

    @Override
    public void takeDamage(Attack attack) {
        super.takeDamage(attack);
    }

    @Override
    public int critical(int initial){
        if (lucky(thiefChance)){
            return (getWeapon().getCriticalDamage() * thiefChance)/100;
        }
        return initial;
    }

    public Attack critical(Attack attack){
        if (lucky(thiefChance)){
            int damage = (getWeapon().getCriticalDamage() * thiefChance)/100;
            attack.setDamage(damage);
            attack.setCritical(true);
        }
        return attack;
    }
}
