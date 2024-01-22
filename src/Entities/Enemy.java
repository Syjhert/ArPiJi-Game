package Entities;

import Moves.*;

import java.util.ArrayList;

public abstract class Enemy extends BattleEntity{
    protected float trapRate;
    protected ArrayList<Move> moves;
    public Enemy(String name, int healthPoints, int attack, float trapRate){ //TO DO MAKE ENEMY SUBCLASSES
        super(name, healthPoints, attack);
        this.trapRate = trapRate;
        moves = new ArrayList<>();
    }
    //GETTER
    public float getTrapRate() {
        return trapRate;
    }

    @Override
    public ArrayList<Move> getMoves() {
        return moves;
    }

    public void addMove(Move move){
        moves.add(move);
    }
}
