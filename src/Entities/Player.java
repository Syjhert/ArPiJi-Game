package Entities;

import Weapons.Weapon;
import Moves.*;

import java.util.ArrayList;
public class Player extends BattleEntity{
    public Player(String name){
        super(name.compareTo("") == 0 ? "Traveler" : name, 100, 10);
        moves.add(new DamagingM("Punch", 1, "MELEE", "NONE"));
    }
    public void equipWeapon(Weapon weapon){
        attack += weapon.getAttack();
        attackBattle = attack;
        critChance += weapon.getCritChance();
        critChanceBattle = critChance;
        critDamage += weapon.getCritDamage();
        critDamageBattle = critDamage;
        moves = (ArrayList<Move>) weapon.getMoves().clone();
    }
    public void resetStats(){
        maxHealthPointsBattle = maxHealthPoints;
        if(healthPoints < 0){
            healthPoints = maxHealthPoints;
        }
        attackBattle = attack;
        critChanceBattle = critChance;
        critDamageBattle = critDamage;
        statuses.clear();
    }
}
