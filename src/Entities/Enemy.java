package Entities;

import Moves.*;

public abstract class Enemy extends BattleEntity{
    protected float trapRate;
    public Enemy(String name, int healthPoints, int attack, float trapRate){ //TO DO MAKE ENEMY SUBCLASSES
        super(name, healthPoints, attack);
        this.trapRate = trapRate;
    }
    //GETTER
    public float getTrapRate() {
        return trapRate;
    }

    public void addMoves(Move move){
        moves.add(move);
    }
}
