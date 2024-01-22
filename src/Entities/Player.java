package Entities;

import Statuses.Status;
import Statuses.StatusType;
import Weapons.Weapon;
import Moves.*;

import java.util.*;
public class Player extends BattleEntity{
    private Weapon weapon;
    public Player(String name){
        super(name.compareTo("") == 0 ? "Traveler" : name, 100, 10);
        weapon = null;
    }
    @Override
    public int getBaseHealthPoints() {
        return weapon != null ? super.getBaseHealthPoints() + weapon.getHealthPoints() : super.getBaseHealthPoints();
    }
    @Override
    public int getBaseAttack() {
        return weapon != null ? super.getBaseAttack() + weapon.getAttack() : super.getBaseAttack();
    }
    @Override
    public float getBaseCritChance() {
        return weapon != null ? super.getBaseCritChance() + weapon.getCritChance() : super.getBaseCritChance();
    }
    @Override
    public float getBaseCritDamage() {
        return weapon != null ? super.getBaseCritDamage() + weapon.getCritDamage() : super.getBaseCritDamage();
    }

    @Override
    public ArrayList<Move> getMoves() {
        if(weapon == null || weapon.getMoves().isEmpty()){
            return new ArrayList<>(){{add(new DamagingM("Punch", 1, "MELEE", "NONE"));}};
        }
        return weapon.getMoves();
    }

    public void equipWeapon(Weapon weapon){
        this.weapon = weapon;
    }
    public void resetStats(){
        if(getHealthPoints() < 0){
            damageTaken = 0;
        }
        for (Map.Entry<StatusType, ArrayList<Status>> entry: statuses.entrySet()) {
            entry.getValue().clear();
        }
    }
}