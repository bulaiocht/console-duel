package console.game.npc;

import console.game.attack.Attack;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Mercenary extends NPC {

    private static final int mercenaryChance = 30;

    private int rage;

    @Override
    public Attack attack(NPC target) {
        return super.attack(target);
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
        if (lucky(mercenaryChance)){
            return this.rage + initial;
        }
        return initial;
    }

    public Attack critical(Attack attack){
        if (lucky(mercenaryChance)){
            int damage = getWeapon().getCriticalDamage() + rage;
            attack.setDamage(damage);
            attack.setCritical(true);
        }
        return attack;
    }
}
