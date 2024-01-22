package Entities;

import Statuses.*;
import Moves.*;

import java.lang.reflect.Array;
import java.util.*;

public abstract class BattleEntity {
    private static final double zero = 0.0;

    protected String name;
    protected boolean named;
    protected int damageTaken;
    protected int baseHealthPoints;
    public int baseAttack;      //baseCritChance = 0.2; baseCritDamage = 0.5
    protected HashMap<String, Double> attributeStats = new HashMap<>(){{put("MELEE", 0.0); put("RANGED", 0.0);
        put("DARK", 0.0); put("LIGHT", 0.0); put("NORMAL", 0.0); put("NONE", zero);}};

    protected Map<StatusType, ArrayList<Status>> statuses;

    public BattleEntity(String name, int maxHealthPoints, int attack) {
        this.name = name;
        named = false;
        damageTaken = 0;
        this.baseHealthPoints = maxHealthPoints;
        this.baseAttack = attack;
        statuses = new HashMap<>();
            statuses.put(StatusType.HEALTH, new ArrayList<>());
            statuses.put(StatusType.ATTACK, new ArrayList<>());
            statuses.put(StatusType.CRITCHANCE, new ArrayList<>());
            statuses.put(StatusType.CRITDAMAGE, new ArrayList<>());
            statuses.put(StatusType.DoT, new ArrayList<>());
    }

    //GETTERS
    public String getName() {
        return "<" + name + ">";
    }

    public int getHealthPoints() {
        return getMaxHealthPointsBattle() - damageTaken;
    }

    public int getBaseHealthPoints() {
        return baseHealthPoints;
    }

    public int getMaxHealthPointsBattle() {
        int total = getBaseHealthPoints();
        int addend;
        try{
            for(Status status : statuses.get(StatusType.HEALTH)){
                if(status instanceof Buff){
                    addend = (int) (((Buff)status).getMultiplier() * getBaseHealthPoints());
                }else if(status instanceof Debuff){
                    addend = (int) -(((Debuff)status).getMultiplier() * getBaseHealthPoints());
                }else{
                    throw new ClassCastException("A DoT got involved in a stat calculation (HEALTH)");
                }
                total += addend;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return total;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getAttackBattle() {
        int total = getBaseAttack();
        int addend;
        try{
            for(Status status : statuses.get(StatusType.ATTACK)){
                if(status instanceof Buff){
                    addend = (int) (((Buff)status).getMultiplier() * getBaseAttack());
                }else if(status instanceof Debuff){
                    addend = (int) -(((Debuff)status).getMultiplier() * getBaseAttack());
                }else{
                    throw new ClassCastException("A DoT got involved in a stat calculation (ATTACK)");
                }
                total += addend;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return total;
    }

    public float getBaseCritChance(){
        return 0.2f;
    }

    public float getCritChanceBattle() {       //unlike int stats, double stats will ADD the status multiplier
        float total = getBaseCritChance();     //  instead of adding the product of the multiplier and baseStat
        try{
            for(Status status : statuses.get(StatusType.CRITCHANCE)){
                if(status instanceof Buff){
                    total += ((Buff)status).getMultiplier();
                }else if(status instanceof Debuff){
                    total -= ((Debuff)status).getMultiplier();
                }else{
                    throw new ClassCastException("A DoT got involved in a stat calculation (CRITCHANCE)");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return total;
    }

    public float getBaseCritDamage(){
        return 0.5f;
    }
    public double getCritDamageBattle() {
        float total = getBaseCritDamage();
        try{
            for(Status status : statuses.get(StatusType.CRITDAMAGE)){
                if(status instanceof Buff){
                    total += ((Buff)status).getMultiplier();
                }else if(status instanceof Debuff){
                    total -= ((Debuff)status).getMultiplier();
                }else{
                    throw new ClassCastException("A DoT got involved in a stat calculation (CRITDAMAGE)");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return total;
    }

    public boolean isDead() {
        return getHealthPoints() <= 0;
    }

    public abstract ArrayList<Move> getMoves();

    public Map<StatusType, ArrayList<Status>> getStatuses() {
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
        takeDamage((int)(getAttackBattle() * damage));
        System.out.println(getName()+" has inflicted "+(int)(getAttackBattle() * damage)+
                " damage to themselves");
        if(isDead()) {
            if(this instanceof Player){
                return -1;
            }
        }
        return 0;
    }
    public int doDamage(DamagingM move, BattleEntity receiver){
        int damageReceived = (int)(getAttackBattle() * move.getDamage());
        double multipliers = 0;
        for(String attribute : move.getAttributes()){
            multipliers += attributeStats.get(attribute);
        }
        damageReceived *= (1+multipliers);
        Random random = new Random();
        if(random.nextDouble() <= getCritChanceBattle()){
            System.out.println(getName()+" INFLICTED A CRITICAL HIT!");
            damageReceived *= (1 + getCritDamageBattle());
            System.out.println(getName()+" INFLICTED " + damageReceived + " DAMAGE TO "+ receiver.getName()+"!");
        }
        else{
            System.out.println(getName()+" inflicted " + damageReceived + " damage to " +receiver.getName()+"!");
        }
        return receiver.takeDamage(damageReceived);
    }
    public int takeDoT(DoT dot){
        System.out.println(name + " is damaged by the "+dot.getName());
        return takeDamage(dot.getDamage());
    }

    private int takeDamage(int damage){ //no "is damaged by - points" cuz crit can happen
        damageTaken += damage;
        if(isDead() && this instanceof Player){
            return -1;
        }
        else if(isDead() && this instanceof Enemy){
            return 1;
        }
        return 0;
    }
    public void heal(int amount){
        damageTaken -= amount;
        if(damageTaken < 0) damageTaken = 0;
    }

    public void displayStats(){
        System.out.println(name+"'s Stats:");
        System.out.printf("Current HP: %d/%d\t\tAttack: %d\nCrit Chance: %.2f%%\t\tCrit Damage: %.2f%%\n", getHealthPoints(),
                getMaxHealthPointsBattle(), getAttackBattle(), getCritChanceBattle()*100, getCritDamageBattle()*100);
        System.out.println("Statuses:");
        if(statuses.size() == 0) System.out.println("None");
        else{
//            for(Map.Entry<StatusType, ArrayList<Status>> entry : statuses.entrySet()){
//                System.out.println(entry.getKey());
//                for(Status s : entry.getValue()){
//                    System.out.println("*" + s);
//                }
//            }
            for(Status s : statuses.get(StatusType.HEALTH)) displayStatus(s);
            for(Status s : statuses.get(StatusType.ATTACK)) displayStatus(s);
            for(Status s : statuses.get(StatusType.CRITCHANCE)) displayStatus(s);
            for(Status s : statuses.get(StatusType.CRITDAMAGE)) displayStatus(s);
            for(Status s : statuses.get(StatusType.DoT)) displayStatus(s);
        }
    }
    private void displayStatus(Status s){ //displayStats helper
        System.out.println("*" + s);
    }
    public void addStatus(Status status){ //add the status in the player statuses (not apply status)
        if(status instanceof DoT){
            statuses.get(StatusType.DoT).add(status);
        }else{
            try{
                statuses.get(status.getType()).add(status);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    public int statusesDecrement(){
        int state = 0;
        ArrayList<Status> removedStatuses;
        for(ArrayList<Status> entry : statuses.values()){
            removedStatuses = new ArrayList<>();
            for(Status s : entry){
                if((state = s.statusDecrement(this)) != 0) return state;   //also has 0/1/-1 states cuz DoT can kill
                if(s.getDuration() <= 0) removedStatuses.add(s);
            }
            entry.removeAll(removedStatuses);
        }
        return state;
    }
    public void showMoves(){
        System.out.println(name+"'s moves:");
        for(int i=1; i<=getMoves().size(); i++){
            System.out.print("["+i+"] "+getMoves().get(i-1).getName());
            if(i == getMoves().size()) break;
            if(i%2 == 0 || i+1 == getMoves().size()){
                System.out.print("\n");
                continue;
            }
            for(int j=(getMoves().get(i-1).getName().length())/4; j<=4; j++){
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
                if(choice > 0 && choice <= attacker.getMoves().size()) break;
                System.out.println("Your input is invalid!");
            }while(true);
        }
        else{
            Random random = new Random();
            choice = random.nextInt(this.getMoves().size()-1) + 1;
        }
        return attacker.getMoves().get(choice-1).doMove(attacker, receiver);
    }
}
