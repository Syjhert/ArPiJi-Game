package Statuses;

import Entities.*;

public class Buff extends Status{

    //BUFF (1) TYPES:
    //1 Health Buff
    //2 Attack Buff
    //3 Crit Chance Buff
    //4 Crit Damage Buff

    private final float multiplier;

    public Buff(String name, StatusType type, float multiplier){ //Default duration 3 turns
        super(name, type);
        if(type == StatusType.BLEED || type == StatusType.POISON){
            System.out.println("WARNING: THE BUFF " + name + " HAS BEEN ASSIGNED WITH THE TYPE POISON OR BLEED");
        }
        this.multiplier = multiplier;
    }
    public Buff (String name, int duration, StatusType type, float multiplier){
        this(name, type, multiplier);
        this.duration = duration;
    }
    public float getMultiplier() {
        return multiplier;
    }


    @Override
    public String toString(){
        String statusDesc = super.toString() + " Buff : " + (int)(multiplier *100)+"%\t\t";
        if(getStatusName().length() < 16) statusDesc += "\t";
        if(getStatusName().length() < 24) statusDesc += "\t";
        statusDesc += "Duration: "+duration+" turns";
        return statusDesc;
    }
    @Override
    public void statusInflict(BattleEntity bE) {
        super.statusInflict(bE);
        System.out.println(bE.getName() + " was blessed with a " + getStatusName() + " Buff!");
        switch (type) {         //the message pop up
            case HEALTH:
                System.out.println(bE.getName() + "'s max health is increased by " + (int) (bE.getBaseHealthPoints() * multiplier) + "!");
                System.out.println(bE.getName() + "'s current health is now " + bE.getHealthPoints() + " out of " + bE.getMaxHealthPointsBattle() + "!");
                break;
            case ATTACK:
                System.out.println(bE.getName() + "'s attack is increased by " + (int) (bE.getBaseAttack() * multiplier) + "!");
                System.out.println(bE.getName() + "'s current attack is now " + bE.getAttackBattle() + "!");
                break;
            case CRITCHANCE:
                System.out.printf(bE.getName() + " critical chance is increased by %.2f%%!\n", multiplier * 100);
                System.out.printf(bE.getName() + " current critical chance is now %.2f%%!\n", bE.getCritChanceBattle() * 100);
                break;
            case CRITDAMAGE:
                System.out.printf(bE.getName() + " critical damage is increased by %.2f%%!\n", multiplier * 100);
                System.out.printf(bE.getName() + " current critical damage is now %.2f%%!\n", bE.getCritDamageBattle() * 100);
                break;
            default:
                System.out.println("STATUSTYPE BUFF ERROR");
                break;
        }
    }

    @Override
    public int statusDecrement(BattleEntity battleEntity){
        if(duration == INFINITE){
            return 0;
        }
        duration--;
        return 0;
    }

    @Override
    public Status cloneStatus() {
        return new Buff(name, duration, type, multiplier);
    }


}
