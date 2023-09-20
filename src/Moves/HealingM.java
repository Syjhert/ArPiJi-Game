package Moves;

import Entities.BattleEntity;

import java.util.ArrayList;

public class HealingM extends Move{ //can be main move or a damaging passive
    protected int amount;
    protected ArrayList<Move> passiveMoves = new ArrayList<Move>();

    public HealingM(String name, int amount) {
        super(name);
        this.amount = amount;
        receiverDirected = false;
    }
    public HealingM(String name, int amount, boolean receiverDirected) {
        super(name);
        this.amount = amount;
        this.receiverDirected = receiverDirected;
    }
    public HealingM(int amount) {
        this.amount = amount;
    }
    public HealingM(int amount, ArrayList<Move> passiveMoves) {
        this.amount = amount;
        this.passiveMoves = passiveMoves;
    }

    @Override
    public void doMove(BattleEntity attacker, BattleEntity receiver) {
        if(!isPassive) super.doMove(attacker, receiver);
        BattleEntity toHeal;
        if(!receiverDirected) toHeal = attacker;
        else toHeal = receiver;
        int healedAmount = toHeal.getHealthPoints();
        toHeal.setHealthPoints(toHeal.getHealthPoints() + amount);
        if(toHeal.getHealthPoints() > toHeal.getMaxHealthPointsBattle())
            toHeal.setHealthPoints(toHeal.getMaxHealthPointsBattle());
        healedAmount = toHeal.getHealthPoints() - healedAmount;
        System.out.println(toHeal.getName()+" was healed by "+healedAmount+" points");

        for(Move move : passiveMoves){
            move.doMove(attacker, receiver);
        }
    }
}
