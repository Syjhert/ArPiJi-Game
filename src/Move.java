import java.util.Random;
public class Move {
    String name;
    double damage;
    int typeAndEffect = -1;
    double effectAmount = -1;
    boolean receiverDirected;

    //Poison from enemy = 25% of enemy's attackBattle;
    //Bleed from enemy = 5% of player's maxHealthBattle;
    //Poison from player = 10% of player's attackBattle;
    //Bleed from player = 5% of enemy's maxHealthBattle;
    Move(String name, double damage){
        this.name = name;
        this.damage = damage;
    }
    Move(String name, double damage, int typeAndEffect, double effectAmount, boolean receiverDirected){
        this.name = name;
        this.damage = damage;
        this.typeAndEffect = typeAndEffect;
        this.effectAmount = effectAmount;
        this.receiverDirected = receiverDirected;
    }

    public void doMove(BattleEntity attacker, BattleEntity receiver){
        String attackerCallout = "";
        String receiverCallout = "";
        if(attacker instanceof Player) {
            attackerCallout = "you";
            receiverCallout = "the " + receiver.name;
        }
        else if(attacker instanceof Enemy) {
            attackerCallout = "the " + attacker.name;
            receiverCallout = "you";
        }
        Random random = new Random();
        System.out.println("<"+attacker.name+"> used <"+this.name+">!");
        if(random.nextDouble(1) <= attacker.critChanceBattle){
            System.out.println(attackerCallout.toUpperCase()+" INFLICTED A CRITICAL HIT!");
            double temp = (attacker.attackBattle*damage);
            System.out.println(attackerCallout.toUpperCase()+" INFLICTED "+(int)((temp)+(temp*attacker.critDamageBattle))+" DAMAGE TO "+
                    receiverCallout.toUpperCase()+"!");
            receiver.healthPoints -= (int)((temp)+(temp*attacker.critDamageBattle));
        }
        else{
            System.out.println(capitalize(attackerCallout)+" inflicted "+(int)(attacker.attackBattle*damage)+" damage to "
                    +receiverCallout+"!");
            receiver.healthPoints -= (int)(attacker.attackBattle*damage);
        }
        if(typeAndEffect != -1){
            if(receiverDirected){
                System.out.print(capitalize(receiverCallout));
            }else{
                System.out.print(capitalize(attackerCallout));
            }
            if(typeAndEffect < 21){
                System.out.print(" were blessed with a ");
            }
            else if(typeAndEffect < 31){
                System.out.print(" got a ");
            }
            else if(typeAndEffect < 41){
                System.out.print(" got inflicted by ");
                if(typeAndEffect == 31) effectAmount = (int)(attacker.attackBattle * 0.25);
                else if(typeAndEffect == 32) effectAmount = (int)(receiver.maxHealthPointsBattle * 0.05);
            }
            System.out.println(Status.getStatusName(typeAndEffect)+"!");
            if(receiverDirected){
                receiver.addStatuses(new Status(typeAndEffect, 3, effectAmount));
            }else{
                attacker.addStatuses(new Status(typeAndEffect, 3, effectAmount));
            }
        }
    }
    public static final String capitalize(String str) {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}