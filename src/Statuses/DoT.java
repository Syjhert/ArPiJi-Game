package Statuses;

import Entities.*;

import java.util.Dictionary;
import java.util.Hashtable;

public class DoT extends Status{

    //Damage over Time (DoT) (3) TYPES:
    //1 Poison (-25% of enemy's attack)
    //2 Bleed (-5% of player's max health after buffs)

    //Poison = 25% of attacker's attackBattle;
    //Bleed = 10% of receiver's maxHealthBattle;

    private int damage;

    public DoT(String name, int type) { //Default duration 3 turns
        super(name, type);
    }
    public DoT (String name, int duration, int type){
        super(name, duration, type);
    }
    public DoT (String name, boolean isPermanent, int type){
        super(name, isPermanent, type);
    }
    //SETTER
    public void setDamage(BattleEntity attacker, BattleEntity receiver) {
        if(type == 31) damage = (int)(attacker.getAttackBattle() * .25);
        else if(type == 32) damage = (int)(receiver.getMaxHealthPointsBattle() * .10);
    }

    @Override
    public String toString(){
        String statusDesc = super.toString() + damage + " damage per turn\t";
        if(getStatusName().length() < 16) statusDesc += "\t";
        statusDesc += "Duration: "+duration+" turns";
        return statusDesc;
    };
    @Override
    public boolean isEquals(Status status){
        if(status instanceof DoT && status.type == type) return true;
        else return false;
    }

    @Override
    public void statusInflict(BattleEntity battleEntity) {
        super.statusInflict(battleEntity);

    }

    @Override
    public void statusDecrement(BattleEntity battleEntity){
        super.statusDecrement(battleEntity);
        battleEntity.setHealthPoints(battleEntity.getHealthPoints()-damage);
        System.out.println(battleEntity.getName() + "is damaged by the "+getStatusName());
    }
}
