import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
public class Status {
    static Dictionary statusNames = new Hashtable<Integer, String>(){{put(11, "Health Buff"); put(12, "Attack Buff");
        put(13, "Crit Chance Buff"); put(14, "Crit Damage Buff"); put(21, "Health Decrease"); put(22, "Attack Decrease");
        put(23, "Crit Chance Decrease"); put(24, "Crit Damage Decrease"); put(31, "Poison"); put(32, "Bleed");}};

    String name;
    int typeAndEffect = -1;

    //<Status Types>
        //<Status Effect>
    //1 Buffs:
        //1 Health Buff
        //2 Attack Buff
        //3 Crit Chance Buff
        //4 Crit Damage Buff
    //2 Debuffs:
        //1 Health Debuff
        //2 Attack
        //3 Crit Chance
        //4 Crit Damage
    //3 Damage over Time (DoT): No need Effect Amount
        //1 Poison (-25% of enemy's attack)
        //2 Bleed (-5% of player's max health after buffs)

    int duration;
    double effectAmount = -1;

    Status(int typeAndEffect, int duration, double effectAmount){
        this.typeAndEffect = typeAndEffect;
        this.duration = duration;
        this.effectAmount = effectAmount;

        name = (String) statusNames.get(typeAndEffect);
    }

    public String toString(){
        return "Name: "+name+"\tType And Effect: "+typeAndEffect+"\tEffect Amount: "+effectAmount;
    }
    public boolean isEquals(Status status){
        return this.toString().equals(status.toString());
    }

    public void statusDecrement(BattleEntity battleEntity){
        if(duration == 999){
            return;
        }
        duration--;
        if(typeAndEffect > 30){
            if(battleEntity instanceof Player){
                System.out.print("You're");
            }
            else{
                System.out.print("The <"+battleEntity.name+"> is");
            }
            System.out.println(" damaged by "+(int)effectAmount+" points due to "+name);
            battleEntity.healthPoints -= (int)effectAmount;
        }
    }
    static String getStatusName(int num){
        return (String) statusNames.get(num);
    }
}
