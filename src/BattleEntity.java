import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public abstract class BattleEntity {
    String name;
    int healthPoints;
    int maxHealthPoints;
    int maxHealthPointsBattle;
    int attack;
    int attackBattle;
    double critChance;
    double critChanceBattle;
    double critDamage = .5;
    double critDamageBattle = critDamage;
    ArrayList<Move> moves = new ArrayList<Move>();
    ArrayList<Status> statuses = new ArrayList<Status>();

    public void displayStats(){
        System.out.println(name+"'s Stats:");
        System.out.printf("Current HP: %d/%d\t\tAttack: %d\nCrit Chance: %.2f%%\t\tCrit Damage: %.2f%%\n", healthPoints,
                maxHealthPointsBattle, attackBattle, critChanceBattle*100, critDamageBattle*100);
        System.out.println("Statuses:");
        if(statuses.size() == 0) System.out.println("None");
        else{
            for(int i=0; i<statuses.size(); i++){
                System.out.print("*"+statuses.get(i).name);
                for(int j=(statuses.get(i).name.length())/5; j<=4; j++){
                    System.out.print("\t");
                }
                if(statuses.get(i).duration == 999) System.out.println("permanent");
                else System.out.println(statuses.get(i).duration+" turns left");
            }
        }
    }
    public void addStatuses(Status status){
        String receiverCallout = "";
        if(this instanceof Player) receiverCallout = "Your";
        else if(this instanceof Enemy) receiverCallout = "The "+name+"'s";
        boolean skip = false;
        for(int i=0; i<statuses.size(); i++){
            if(this.statuses.get(i).isEquals(status)){
                if(status.typeAndEffect > 30) break;
                this.statuses.get(i).effectAmount += status.effectAmount;
                skip = true;
            }
        }
        if(!skip) statuses.add(status);
        if(status.typeAndEffect == 11){
            maxHealthPointsBattle += (int)(maxHealthPoints * status.effectAmount);
            healthPoints += (int)(maxHealthPoints * status.effectAmount);
            System.out.println(receiverCallout+" max health is increased by "+(int)(maxHealthPoints * status.effectAmount)+"!");
            System.out.println(receiverCallout+" current health is now "+healthPoints+" out of "+maxHealthPointsBattle+"!");
        }
        else if(status.typeAndEffect == 12) {
            attackBattle += (int)(attack * status.effectAmount);
            System.out.println(receiverCallout+" attack is increased by "+(int)(attack * status.effectAmount)+"!");
            System.out.println(receiverCallout+" current attack is now "+attackBattle+"!");
        }
        else if(status.typeAndEffect == 13) {
            critChanceBattle += status.effectAmount;
            System.out.printf(receiverCallout+" critical chance is increased by %.2f%%!\n", status.effectAmount*100);
            System.out.printf(receiverCallout+" current critical chance is now %.2f%%!\n", critChanceBattle*100);
        }
        else if(status.typeAndEffect == 14) {
            critDamageBattle += status.effectAmount;
            System.out.printf(receiverCallout+" critical damage is increased by %.2f%%!\n", status.effectAmount*100);
            System.out.printf(receiverCallout+" current critical damage is now %.2f%%!\n", critDamageBattle*100);
        }

        else if(status.typeAndEffect == 21) {
            maxHealthPointsBattle -= (int)(maxHealthPoints * status.effectAmount);
            if(healthPoints > maxHealthPoints) healthPoints = maxHealthPoints;
            System.out.println(receiverCallout+" max health is decreased by "+(int)(maxHealthPoints * status.effectAmount)+"!");
            System.out.println(receiverCallout+" current health is now "+healthPoints+" out of "+maxHealthPointsBattle+"!");
        }
        else if(status.typeAndEffect == 22) {
            attackBattle -= (int)(attack * status.effectAmount);
            System.out.println(receiverCallout+" attack is decreased by "+(int)(attack * status.effectAmount)+"!");
            System.out.println(receiverCallout+" current attack is now "+attackBattle+"!");
        }
        else if(status.typeAndEffect == 23) {
            critChanceBattle -= status.effectAmount;
            System.out.printf(receiverCallout+" critical chance is decreased by %.2f%%!\n", status.effectAmount*100);
            System.out.printf(receiverCallout+" current critical chance is now %.2f%%!\n", critChanceBattle*100);
        }
        else if(status.typeAndEffect == 24) {
            critDamageBattle -= status.effectAmount;
            System.out.printf(receiverCallout+" critical damage is increased by %.2f%%!\n", status.effectAmount*100);
            System.out.printf(receiverCallout+" current critical damage is now %.2f%%!\n", critDamageBattle*100);
        }
    }
    public void statusesDecrement(){
        for(int i=0; i<statuses.size(); i++){
            statuses.get(i).statusDecrement(this);
        }
    }
    public void showMoves(){
        System.out.println(name+"'s moves:");
        for(int i=1; i<=moves.size(); i++){
            System.out.print("["+i+"] "+moves.get(i-1).name);
            if(i == moves.size()) break;
            if(i%2 == 0 || i+1 == moves.size()){
                System.out.print("\n");
                continue;
            }
            for(int j=(moves.get(i-1).name.length())/4; j<=4; j++){
                System.out.print("\t");
            }
        }
        System.out.println();
    }
    public void chooseMove(BattleEntity attacker, BattleEntity receiver){
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
            choice = random.nextInt(1, this.moves.size());
        }
        attacker.moves.get(choice-1).doMove(attacker, receiver);
    }

}
