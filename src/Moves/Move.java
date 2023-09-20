package Moves;

import Entities.*;

import java.util.ArrayList;
import java.util.Random;

public abstract class Move {
    protected String name;
    protected boolean receiverDirected = true;
    protected boolean isPassive = false;
    protected ArrayList<Move> passiveMoves = new ArrayList<Move>();

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
    public void setPassive(boolean passive) {
        isPassive = passive;
    }

    public void addPassiveMove(Move passiveMove) {
        passiveMoves.add(passiveMove);
    }

    public void doMove(BattleEntity attacker, BattleEntity receiver){
        System.out.println("<"+attacker.getName()+"> used <"+name+">!");
    }
}