package Weapons;

import Moves.*;

import java.util.ArrayList;
public abstract class Weapon {
    protected String name;
    protected int attack;
    protected double critChance;
    protected double critDamage;
    protected ArrayList<Move> moves = new ArrayList<>();

    public Weapon(String name, int attack, double critChance, double critDamage){
        this.name = name;
        this.attack = attack;
        this.critChance = critChance;
        this.critDamage = critDamage;
    }
    //GETTER
    public String getName() {
        return name;
    }
    public int getAttack() {
        return attack;
    }
    public double getCritChance() {
        return critChance;
    }
    public double getCritDamage() {
        return critDamage;
    }
    public ArrayList<Move> getMoves() {
        return moves;
    }

    public String toString(){
        if(name.equals("Claymore"))  return "Name: "+name+"\tAttack: "+attack+"\tCritical Chance: "+(int)(critChance*100)+"%\t\tCritical Damage: "+(int)(critDamage*100)+"%";
        return "Name: "+name+"\t\tAttack: "+attack+"\tCritical Chance: "+(int)(critChance*100)+"%\tCritical Damage: "+(int)(critDamage*100)+"%";
    }
    public void addMove(Move move){
        moves.add(move);
    }
}
