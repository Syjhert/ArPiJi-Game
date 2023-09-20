package Entities;

import Statuses.*;
import Moves.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public abstract class BattleEntity {
    protected String name;
    protected int healthPoints;
    protected int maxHealthPoints;
    protected int maxHealthPointsBattle;
    protected int attack;
    protected int attackBattle;
    protected double critChance;
    protected double critChanceBattle;
    protected double critDamage = .5;
    protected double critDamageBattle = critDamage;
    protected ArrayList<Move> moves = new ArrayList<Move>();
    protected ArrayList<Status> statuses = new ArrayList<Status>();

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

    public double getCritChance() {
        return critChance;
    }

    public double getCritChanceBattle() {
        return critChanceBattle;
    }

    public double getCritDamage() {
        return critDamage;
    }

    public double getCritDamageBattle() {
        return critDamageBattle;
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public ArrayList<Status> getStatuses() {
        return statuses;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setMaxHealthPoints(int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
    }

    public void setMaxHealthPointsBattle(int maxHealthPointsBattle) {
        this.maxHealthPointsBattle = maxHealthPointsBattle;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setAttackBattle(int attackBattle) {
        this.attackBattle = attackBattle;
    }

    public void setCritChance(double critChance) {
        this.critChance = critChance;
    }

    public void setCritChanceBattle(double critChanceBattle) {
        this.critChanceBattle = critChanceBattle;
    }

    public void setCritDamage(double critDamage) {
        this.critDamage = critDamage;
    }

    public void setCritDamageBattle(double critDamageBattle) {
        this.critDamageBattle = critDamageBattle;
    }

    public void setMoves(ArrayList<Move> moves) {
        this.moves = moves;
    }

    public void setStatuses(ArrayList<Status> statuses) {
        this.statuses = statuses;
    }


    public void displayStats(){
        System.out.println(name+"'s Stats:");
        System.out.printf("Current HP: %d/%d\t\tAttack: %d\nCrit Chance: %.2f%%\t\tCrit Damage: %.2f%%\n", healthPoints,
                maxHealthPointsBattle, attackBattle, critChanceBattle*100, critDamageBattle*100);
        System.out.println("Statuses:");
        if(statuses.size() == 0) System.out.println("None");
        else{
            for(int i=0; i<statuses.size(); i++){
                System.out.println("*"+statuses.get(i).toString());
            }
        }
    }
    public void addStatuses(Status status){
        boolean skip = false;
        for(Status s : statuses){
            if(s.isEquals(status)){
                if(!(status instanceof DoT)) break;
                if(status instanceof Buff) {
                    Buff buff = (Buff)s;
                    Buff buff2 = (Buff)status;
                    buff.setAmount((float)(buff.getAmount() + buff2.getAmount()));
                }
                if(status instanceof Debuff) {
                    Debuff debuff = (Debuff)s;
                    Debuff debuff2 = (Debuff)status;
                    debuff.setAmount(debuff.getAmount() + debuff2.getAmount());
                }
                skip = true;
            }
        }
        if(!skip) statuses.add(status);
    }
    public void statusesDecrement(){
        for(Status s : statuses){
            s.statusDecrement(this);
        }
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

    public void statusRemove(Status status){
        statuses.remove(status);
    }
}
