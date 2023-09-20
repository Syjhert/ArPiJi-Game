package Statuses;

import Entities.*;

import java.util.Dictionary;
import java.util.Hashtable;

public class Buff extends Status{

    //BUFF (1) TYPES:
    //1 Health Buff
    //2 Attack Buff
    //3 Crit Chance Buff
    //4 Crit Damage Buff

    private float amount;

    public Buff(String name, int type, float amount){ //Default duration 3 turns
        super(name, type);
        this.amount = amount;
    }
    public Buff (String name, int duration, int type, float amount){
        super(name, duration, type);
        this.amount = amount;
    }
    public Buff (String name, boolean isPermanent, int type, float amount){
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
        if(status instanceof Buff && status.type == type) return true;
        else return false;
    }

    @Override
    public void statusInflict(BattleEntity bE) {
        super.statusInflict(bE);
        System.out.println(bE.getName() + " was blessed with a " + getStatusName() + "!");
        if(type == 11) {
            bE.setMaxHealthPointsBattle(bE.getMaxHealthPoints() + (int)(bE.getMaxHealthPoints()*amount));
            bE.setHealthPoints(bE.getHealthPoints() + (int)(bE.getMaxHealthPoints()*amount));
            System.out.println(bE.getName()+"'s max health is increased by "+(int)(bE.getMaxHealthPointsBattle() * amount)+"!");
            System.out.println(bE.getName()+"'s current health is now "+ bE.getHealthPoints()+" out of "+bE.getMaxHealthPointsBattle()+"!");

        }
        else if(type == 12) {
            bE.setAttackBattle(bE.getAttack() + (int)(bE.getAttack() * amount));
            System.out.println(bE.getName()+"'s attack is increased by "+(int)(bE.getAttack() * amount)+"!");
            System.out.println(bE.getName()+"'s current attack is now "+bE.getAttackBattle()+"!");
        }
        else if(type == 13) {
            bE.setCritChanceBattle(bE.getCritChanceBattle() + amount);
            System.out.printf(bE.getName()+" critical chance is increased by %.2f%%!\n", amount*100);
            System.out.printf(bE.getName()+" current critical chance is now %.2f%%!\n", bE.getCritChanceBattle()*100);
        }
        else if(type == 14) {
            bE.setCritDamageBattle(bE.getCritDamageBattle() + amount);
            System.out.printf(bE.getName()+" critical damage is increased by %.2f%%!\n", amount*100);
            System.out.printf(bE.getName()+" current critical damage is now %.2f%%!\n", bE.getCritDamageBattle()*100);
        }
    }

    @Override
    public void statusDecrement(BattleEntity battleEntity){
        super.statusDecrement(battleEntity);
        //add change stats logic
    }
    public boolean isPermanent() {
        return super.isPermanent();
    }
}
