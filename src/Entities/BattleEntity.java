package Entities;

import Statuses.*;
import Moves.*;

import java.util.*;

public abstract class BattleEntity {

    private static final double zero = 0.0;

    protected String name;
    protected boolean named;
    protected int healthPoints;
    protected int maxHealthPoints;
    protected int maxHealthPointsBattle;
    protected int attack;
    protected int attackBattle;
    protected double critChance;
    protected double critChanceBattle;
    protected double critDamage;
    protected double critDamageBattle;
    protected HashMap<String, Double> attributeStats = new HashMap<>(){{put("MELEE", 0.0); put("RANGED", 0.0);
        put("DARK", 0.0); put("LIGHT", 0.0); put("NORMAL", 0.0); put("NONE", zero);}};
    protected ArrayList<Move> moves;
    protected ArrayList<Status> statuses;

    public BattleEntity(String name, int maxHealthPoints, int attack) {
        this.name = name;
        named = false;
        healthPoints = maxHealthPoints;
        this.maxHealthPoints = maxHealthPoints;
        maxHealthPointsBattle = maxHealthPoints;
        this.attack = attack;
        attackBattle = attack;
        this.critChance = 0.2;
        critChanceBattle = 0.2;
        this.critDamage = 0.5;
        critDamageBattle = 0.5;
        moves = new ArrayList<>();
        statuses = new ArrayList<>();

    }

    //GETTERS
    public String getName() {
        return "<" + name + ">";
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getMaxHealthPointsBattle() {
        return maxHealthPointsBattle;
    }

    public int getAttack() {
        return attack;
    }

    public int getAttackBattle() {
        return attackBattle;
    }

    public double getCritChanceBattle() {
        return critChanceBattle;
    }

    public double getCritDamageBattle() {
        return critDamageBattle;
    }

    public boolean isDead() {
        return healthPoints <= 0;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    //SETTERS
    public void setName(String name) {
        if(!named){
            this.name = name;
            named = true;
        }
        else{
            System.out.println("Current player is already name <" +name+ ">");
        }
    }
    public int doDamage(double damage){ //one parameter = Self damage
        takeDamage((int)(attackBattle*damage));
        System.out.println(getName()+" has inflicted "+(int)(attackBattle*damage)+
                " damage to themselves");
        if(isDead()) {
            if(this instanceof Player){
                return -1;
            }
        }
        return 0;
    }
    public int doDamage(DamagingM move, BattleEntity receiver){
        int damageReceived = (int)(attackBattle*move.getDamage());
        double multipliers = 0;
        for(String attribute : move.getAttributes()){
            multipliers += attributeStats.get(attribute);
        }
        damageReceived *= (1+multipliers);
        Random random = new Random();
        if(random.nextDouble() <= critChanceBattle){
            System.out.println(getName()+" INFLICTED A CRITICAL HIT!");
            damageReceived *= (1 + critDamageBattle);
            System.out.println(getName()+" INFLICTED "+damageReceived+" DAMAGE TO "+ receiver.getName()+"!");
        }
        else{
            System.out.println(getName()+" inflicted "+damageReceived+" damage to " +receiver.getName()+"!");
        }
        return receiver.takeDamage(damageReceived);
    }
    public int takeDoT(DoT dot){
        System.out.println(name + " is damaged by the "+dot.getName());
        return takeDamage(dot.getDamage());
    }

    private int takeDamage(int damage){ //no "is damaged by - points" cuz crit can happen
        healthPoints -= damage;
        if(isDead() && this instanceof Player){
            return -1;
        }
        else if(isDead() && this instanceof Enemy){
            return 1;
        }
        return 0;
    }
    public void heal(int amount){
        healthPoints += amount;
        if(healthPoints > maxHealthPointsBattle) healthPoints = maxHealthPointsBattle;
    }

    public void buffApply(StatusType statusType, double rate){
        switch(statusType){
            case HEALTH:
                int add = (int)(maxHealthPoints * rate);
                maxHealthPointsBattle += add;
                if(add > 0 || (healthPoints + add > 100 && add < 0)){
                    healthPoints += add;
                }
                break;
            case ATTACK:
                attackBattle += attack * rate;
                break;
            case CRITCHANCE:
                critChanceBattle += rate;
                break;
            case CRITDAMAGE:
                critDamageBattle += rate;
                break;
        }
    }


    public void displayStats(){
        System.out.println(name+"'s Stats:");
        System.out.printf("Current HP: %d/%d\t\tAttack: %d\nCrit Chance: %.2f%%\t\tCrit Damage: %.2f%%\n", healthPoints,
                maxHealthPointsBattle, attackBattle, critChanceBattle*100, critDamageBattle*100);
        System.out.println("Statuses:");
        if(statuses.size() == 0) System.out.println("None");
        else{
            for(Status s : statuses){
                System.out.println("*" + s);
            }
        }
    }
    public void addStatus(Status status){ //add the status in the player statuses (not apply status)
        statuses.add(status);
    }
    public int statusesDecrement(){
        int state = 0;
        ArrayList<Status> removedStatuses = new ArrayList<>();
        for(Status s : statuses){
            if((state = s.statusDecrement(this)) != 0) return state;   //also has 0/1/-1 states cuz DoT can kill
            if(s.getDuration() <= 0) removedStatuses.add(s);
        }
        statuses.removeAll(removedStatuses);
        return state;
    }
    public void showMoves(){
        System.out.println(name+"'s moves:");
        for(int i=1; i<=moves.size(); i++){
            System.out.print("["+i+"] "+moves.get(i-1).getName());
            if(i == moves.size()) break;
            if(i%2 == 0 || i+1 == moves.size()){
                System.out.print("\n");
                continue;
            }
            for(int j=(moves.get(i-1).getName().length())/4; j<=4; j++){
                System.out.print("\t");
            }
        }
        System.out.println();
    }
    public int chooseMove(BattleEntity attacker, BattleEntity receiver){
        Scanner scanner = new Scanner(System.in);
        int choice;
        if(this instanceof Player){
            do{
                System.out.println("What move do you want to make?");
                attacker.showMoves();
                choice = scanner.nextInt();
                if(choice > 0 && choice <= attacker.moves.size()) break;
                System.out.println("Your input is invalid!");
            }while(true);
        }
        else{
            Random random = new Random();
            choice = random.nextInt(this.moves.size()-1) + 1;
        }
        return attacker.moves.get(choice-1).doMove(attacker, receiver);
    }
}
