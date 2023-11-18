package Moves;

import Entities.*;

import java.util.ArrayList;

public abstract class Move {
    protected String name;
    protected boolean receiverDirected = true;
    protected boolean isPassive = false;
    protected ArrayList<Move> passiveMoves = new ArrayList<>();

    public Move(String name){
        this.name = name;
    }
    public Move(){
        this.name = "None";
        isPassive = true;
    }

    //GETTER
    public String getName() {
        return name;
    }

    //SETTER
    public void setPassive(boolean passive) { //maybe useful?
        isPassive = passive;
    }

    public void addPassiveMove(Move passiveMove) {
        passiveMoves.add(passiveMove);
    }

    public abstract int doMove(BattleEntity attacker, BattleEntity receiver);
}