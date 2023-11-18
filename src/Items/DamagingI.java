package Items;

import Entities.BattleEntity;
import Moves.*;

public class DamagingI extends Item{

    int damage;
    StatusM statusM = null;

    public DamagingI(String name, int damage) {
        super(name);
        this.damage = damage;
    }
    public DamagingI(String name, int damage, StatusM statusM) {   // Damaging Item that has a passive Status move
        super(name);
        this.damage = damage;
        this.statusM = statusM;
    }

    //OVERLOAD?
    public int useItem(BattleEntity attacker, BattleEntity receiver) {
        super.useItem(attacker);
        int damageReceived = (attacker.getAttackBattle()*damage);
        System.out.println(attacker.getName()+" inflicted "+damageReceived+" damage to " +receiver.getName()+"!");
        return receiver.doDamage(damageReceived);
    }
}
