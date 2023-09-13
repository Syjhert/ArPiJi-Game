import java.util.ArrayList;
public class Weapon {
    String name;
    int attack;
    double critChance;
    double critDamage;
    ArrayList<Move> moves = new ArrayList<Move>();



    Weapon(String name, int attack, double critChance, double critDamage){
        this.name = name;
        this.attack = attack;
        this.critChance = critChance;
        this.critDamage = critDamage;
    }
    public String toString(){
        if(name.equals("Claymore"))  return "Name: "+name+"\tAttack: "+attack+"\tCritical Chance: "+(int)(critChance*100)+"%\tCritical Damage: "+(int)(critDamage*100)+"%";
        return "Name: "+name+"\t\tAttack: "+attack+"\tCritical Chance: "+(int)(critChance*100)+"%\tCritical Damage: "+(int)(critDamage*100)+"%";
    }
    public void addMoves(String name, double damage){
        Move move = new Move(name, damage);
        moves.add(move);
    }
    public void addMoves(String name, double damage, int typeAndEffect, double effectAmount, boolean receiverDirected){
        Move move = new Move(name, damage, typeAndEffect, effectAmount, receiverDirected);
        moves.add(move);
    }
}
