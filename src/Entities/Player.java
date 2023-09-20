package Entities;

import Weapons.Weapon;
import Moves.*;

import java.util.ArrayList;
public class Player extends BattleEntity{
    public Player(String name){
        this.name = name;
        healthPoints = 100;
        maxHealthPoints = 100;
        maxHealthPointsBattle = 100;
        attack = 10;
        attackBattle = 10;
        critChance = 0.2;
        critChanceBattle = 0.2;
        moves.add(new DamagingM("Punch", 1));
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
