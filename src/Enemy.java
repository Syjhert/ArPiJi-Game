public class Enemy extends BattleEntity{
    double trapRate;
    Enemy(){
    }
    Enemy(String name, int healthPoints, int attack, double trapRate){
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
    public void addMoves(String name, double damage){
        Move move = new Move(name, damage);
        moves.add(move);
    }
    public void addMoves(String name, double damage, int typeAndEffect, double effectAmount, boolean receiverDirected){
        Move move = new Move(name, damage, typeAndEffect, effectAmount, receiverDirected);
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
