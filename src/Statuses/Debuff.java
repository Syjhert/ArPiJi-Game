package Statuses;

import Entities.*;

import java.util.Dictionary;
import java.util.Hashtable;

public class Debuff extends Status{

    //DEBUFF (2) TYPES:
    //1 Health Decrease
    //2 Attack Decrease
    //3 Crit Chance Decrease
    //4 Crit Damage Decrease

    private float amount;

    public Debuff(String name, int type, float amount) { //Default duration 3 turns
        super(name, type);
        this.amount = amount;
    }
    public Debuff (String name, int duration, int type, float amount){
        super(name, duration, type);
        this.amount = amount;
    }
    public Debuff (String name, boolean isPermanent, int type, float amount){
        super(name, isPermanent, type);
        this.amount = amount;
    }

    //GETTER
    public float getAmount() {
        return amount;
    }
    //SETTER
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString(){
        String statusDesc = super.toString() + (int)(amount*100)+"%\t";
        if(getStatusName().length() < 16) statusDesc += "\t";
        if(getStatusName().length() < 24) statusDesc += "\t";
        statusDesc += "Duration: "+duration+" turns";
        return statusDesc;
    };
    @Override
    public boolean isEquals(Status status){
        if(status instanceof Debuff && status.type == type) return true;
        else return false;
    }
    @Override
    public void statusInflict(BattleEntity bE) {
        super.statusInflict(bE);
        System.out.println(bE.getName() + " had gotten a " + getStatusName() + "!");
        if(type == 21) {
            bE.setMaxHealthPointsBattle(bE.getMaxHealthPoints() - (int)(bE.getMaxHealthPoints()*amount));
            if(bE.getHealthPoints() > bE.getMaxHealthPointsBattle())
                bE.setHealthPoints(bE.getMaxHealthPoints());
            System.out.println(bE.getName()+"'s max health is decreased by "+(int)(bE.getMaxHealthPointsBattle() * amount)+"!");
            System.out.println(bE.getName()+"'s current health is now "+ bE.getHealthPoints()+" out of "+bE.getMaxHealthPointsBattle()+"!");

        }
        else if(type == 22) {
            bE.setAttackBattle(bE.getAttack() - (int)(bE.getAttack() * amount));
            System.out.println(bE.getName()+"'s attack is decreased by "+(int)(bE.getAttack() * amount)+"!");
            System.out.println(bE.getName()+"'s current attack is now "+bE.getAttackBattle()+"!");
        }
        else if(type == 23) {
            bE.setCritChanceBattle(bE.getCritChanceBattle() - amount);
            System.out.printf(bE.getName()+" critical chance is decreased by %.2f%%!\n", amount*100);
            System.out.printf(bE.getName()+" current critical chance is now %.2f%%!\n", bE.getCritChanceBattle()*100);
        }
        else if(type == 24) {
            bE.setCritDamageBattle(bE.getCritDamageBattle() - amount);
            System.out.printf(bE.getName()+" critical damage is decreased by %.2f%%!\n", amount*100);
            System.out.printf(bE.getName()+" current critical damage is now %.2f%%!\n", bE.getCritDamageBattle()*100);
        }
    }
    @Override
    public void statusDecrement(BattleEntity battleEntity){
        super.statusDecrement(battleEntity);
        //add change stats logic
    }
}
