package console.game.attack;

import console.game.npc.NPC;
import lombok.Data;

@Data
public class Attack {

    private int damage;

    private boolean blocked;

    private boolean parried;

    private boolean critical;

    private NPC attacker;

    private NPC target;

    private int attackRoll;

    public Attack(NPC attacker, NPC target) {
        this.attacker = attacker;
        this.target = target;
    }

    public int calculateDamage(){

        int initDamage = attacker.getWeapon().getDamage();

        if (critical){
            this.damage = attacker.critical(initDamage);
        }

        if (parried){
            return 0;
        }

        if (blocked){
            return this.damage;
        }

        return initDamage;
    }
}
