package Moves;

import Entities.BattleEntity;

import java.util.ArrayList;

public class HealingM extends Move{ //can be main move or a damaging passive
    protected int amount;
    protected ArrayList<Move> passiveMoves = new ArrayList<>();

    public HealingM(String name, int amount) { //RECEIVERDIRECTED is false default cuz you mostly heal YOURSELF
        super(name);
        this.amount = amount;
        receiverDirected = false;
    }
    public HealingM(String name, int amount, boolean receiverDirected) {  //Pure Healing move to enemy
        super(name);
        this.amount = amount;
        this.receiverDirected = receiverDirected;
    }
    public HealingM(int amount) {  //Passive healing move
        this.amount = amount;
    }
    public HealingM(int amount, ArrayList<Move> passiveMoves) {   // Healing move with passive Status move
        this.amount = amount;
        this.passiveMoves = passiveMoves;
    }

    @Override
    public int doMove(BattleEntity attacker, BattleEntity receiver) {
        if(!isPassive) System.out.println("<"+attacker.getName()+"> used <"+name+">!");
        BattleEntity toHeal;
        if(!receiverDirected) toHeal = attacker;
        else toHeal = receiver;
        int healedAmount = toHeal.getHealthPoints();
        toHeal.heal(amount);
        healedAmount = toHeal.getHealthPoints() - healedAmount;
        System.out.println(toHeal.getName()+" was healed by "+healedAmount+" points");

        for(Move move : passiveMoves){
            move.doMove(attacker, receiver);
        }
        if(attacker.isDead()) return -1;
        else if(receiver.isDead()) return 1;
        else return 0;
    }
}
