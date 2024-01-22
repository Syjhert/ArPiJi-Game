package Weapons;

import Moves.*;

import java.util.ArrayList;
public abstract class Weapon {
    protected String name;
    protected int healthPoints;
    protected int attack;
    protected float critChance;
    protected float critDamage;
    protected ArrayList<Move> moves;

    public Weapon(String name, int healthPoints, int attack, float critChance, float critDamage){
        this.healthPoints = healthPoints;
        this.name = name;
        this.attack = attack;
        this.critChance = critChance;
        this.critDamage = critDamage;
        moves = new ArrayList<>();
    }
    //GETTER
    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
    public int getAttack() {
        return attack;
    }
    public float getCritChance() {
        return critChance;
    }
    public float getCritDamage() {
        return critDamage;
    }
    public ArrayList<Move> getMoves() {
        return moves;
    }

    public String toString(){
        if(name.equals("Claymore"))  return "Name: "+name+"\tHP: "+healthPoints+"\tAttack: "+attack+"\tCritical Chance: "+(int)(critChance*100)+"%\t\tCritical Damage: "+(int)(critDamage*100)+"%";
        return "Name: "+name+"\t\tHP: "+healthPoints+"\tAttack: "+attack+"\tCritical Chance: "+(int)(critChance*100)+"%\tCritical Damage: "+(int)(critDamage*100)+"%";
    }
    public void addMove(Move move){
        moves.add(move);
    }
}
