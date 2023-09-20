package Entities;

import Moves.*;

public class Enemy extends BattleEntity{
    protected float trapRate;
    public Enemy(){
    }
    public Enemy(String name, int healthPoints, int attack, float trapRate){
        this.name = name;
        this.healthPoints = healthPoints;
        maxHealthPoints = healthPoints;
        maxHealthPointsBattle = healthPoints;
        this.attack = attack;
        attackBattle = attack;
        critChance = 0.1;
        critChanceBattle = critChance;
        this.trapRate = trapRate;
    }
    //GETTER
    public float getTrapRate() {
        return trapRate;
    }

    public void addMoves(Move move){
        moves.add(move);
    }
    public void copy(Enemy enemy){
        name = enemy.name;
        healthPoints = enemy.healthPoints;
        maxHealthPoints = enemy.maxHealthPoints;
        maxHealthPointsBattle = enemy.maxHealthPointsBattle;
        attack = enemy.attack;
        attackBattle = enemy.attackBattle;
        critChance = enemy.critChance;
        critChanceBattle = enemy.critChanceBattle;
        critDamage = enemy.critDamage;
        critDamageBattle = enemy.critDamageBattle;
        trapRate = enemy.trapRate;
        moves = enemy.moves;
    }
}
