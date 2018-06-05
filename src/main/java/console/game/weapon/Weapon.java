package console.game.weapon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weapon {

    private int durability;

    private int damage;

    private int criticalDamage;

}
