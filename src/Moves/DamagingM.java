package Moves;

import Entities.BattleEntity;
import Statuses.*;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
public class DamagingM extends Move{//can be main move or damaging move passive (self damage)
    protected double damage;

    public DamagingM(String name, double damage){
        super(name);
        this.damage = damage;
    }
    public DamagingM(String name, double damage, boolean receiverDirected){
        super(name);
        this.receiverDirected = receiverDirected;
        this.damage = damage;
    }
    public DamagingM(double damage){
        super();
        this.damage = damage;
    }
    public DamagingM(double damage, boolean receiverDirected){
        super();
        this.damage = damage;
        this.receiverDirected = receiverDirected;
    }

    @Override
    public void doMove(BattleEntity attacker, BattleEntity receiver) {
        if(!isPassive) super.doMove(attacker, receiver);
        if(receiverDirected){
            int damageReceived;
            Scanner sc = new Scanner(System.in);
            Random random = new Random();
            if(random.nextDouble(1) <= attacker.getCritChanceBattle()){
                System.out.println(attacker.getName()+" INFLICTED A CRITICAL HIT!");
                damageReceived = (int)((attacker.getAttackBattle()*damage) + ((attacker.getAttackBattle()*damage)* attacker.getCritDamageBattle()));
                System.out.println(attacker.getName()+" INFLICTED "+damageReceived+" DAMAGE TO "+ receiver.getName()+"!");
                receiver.setHealthPoints(receiver.getHealthPoints() - damageReceived);
            }
            else{
                damageReceived = (int)(attacker.getAttackBattle()*damage);
                System.out.println(attacker.getName()+" inflicted "+damageReceived+" damage to " +receiver.getName()+"!");
                receiver.setHealthPoints(receiver.getHealthPoints() - damageReceived);
            }
        }
        else if(!receiverDirected){
            attacker.setHealthPoints((int)(attacker.getHealthPoints()-
                    (attacker.getAttackBattle()*damage)));
            System.out.println(attacker.getName()+" has inflicted "+(int)(attacker.getAttackBattle()*damage)+
                    " damage to themselves");
        }
        for(Move move : passiveMoves){
            move.doMove(attacker, receiver);
        }
    }
}
