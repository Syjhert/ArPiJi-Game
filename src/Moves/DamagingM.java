package Moves;

import Entities.BattleEntity;

import java.util.ArrayList;

public class DamagingM extends Move{//can be main move or damaging move passive (self damage)
    protected double damage;
    protected ArrayList<String> attributes = new ArrayList<>();

    public DamagingM(String name, double damage, String attribute1, String attribute2){
        super(name);
        this.damage = damage;
        this.attributes.add(attribute1);
        this.attributes.add(attribute2);
    }
    public DamagingM(String name, double damage, boolean receiverDirected){ //when DamagingM is selfdamage
        this(name, damage, "NONE", "NONE");
        this.receiverDirected = receiverDirected;
    }
    public DamagingM(double damage, String attribute1, String attribute2){ //when DamagingM is passive, maybe a secondary effect or multihit
        this("Passive Damage <Not real name>", damage, attribute1, attribute2);
        isPassive = true;

    }
    public DamagingM(double damage, boolean receiverDirected){ //when DamagingM is passive and selfdamage, like a recoil
        this(damage, "NONE", "NONE");
        this.receiverDirected = receiverDirected;
    }

    public double getDamage() {
        return damage;
    }

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    @Override
    public int doMove(BattleEntity attacker, BattleEntity receiver) {
        if(!isPassive) System.out.println("<"+attacker.getName()+"> used <"+name+">!");
        int state; //-1 if player died, 1 if enemy died, 0 if none
        if(receiverDirected){
            if((state = attacker.doDamage(this, receiver)) != 0) return state;
        }
        else{
            if((state = attacker.doDamage(damage)) != 0) return state;
        }
        for(Move move : passiveMoves){
            move.doMove(attacker, receiver);
        }
        return 0;
    }
}
