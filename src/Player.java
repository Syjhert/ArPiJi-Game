import java.util.ArrayList;
public class Player extends BattleEntity{
    Player(String name){
        this.name = name;
        healthPoints = 100;
        maxHealthPoints = 100;
        maxHealthPointsBattle = 100;
        attack = 10;
        attackBattle = 10;
        critChance = 0.2;
        critChanceBattle = 0.2;
        moves.add(new Move("Punch", 1));
    }
    public void equipWeapon(Weapon weapon){
        attack += weapon.attack;
        attackBattle = attack;
        critChance += weapon.critChance;
        critChanceBattle = critChance;
        critDamage += weapon.critDamage;
        critDamageBattle = critDamage;
        moves = (ArrayList<Move>) weapon.moves.clone();
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
