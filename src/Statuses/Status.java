package Statuses;

import Entities.*;

import java.util.Dictionary;
import java.util.Hashtable;
public abstract class Status {
    static Dictionary statusNames = new Hashtable<Integer, String>(){{put(11, "Health Buff"); put(12, "Attack Buff");
        put(13, "Crit Chance Buff"); put(14, "Crit Damage Buff"); put(21, "Health Decrease"); put(22, "Attack Decrease");
        put(23, "Crit Chance Decrease"); put(24, "Crit Damage Decrease"); put(31, "Poison"); put(32, "Bleed");}};

    String name;
    boolean isPermanent = false;
    int duration = 3;
    int type;

    //GETTERS
    public String getName() {
        return name;
    }
    public boolean isPermanent() {
        return isPermanent;
    }
    public int getDuration() {
        return duration;
    }
    public int getType() {
        return type;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public void setType(int type) {
        this.type = type;
    }

    public Status(String name, int type){ //Default duration 3 turns
        this.name = name;
        this.type = type;
    }

    public Status(String name, int duration, int type){
        this.name = name;
        this.duration = duration;
        this.type = type;
    }
    public Status(String name, boolean isPermanent, int type){
        this.name = name;
        this.isPermanent = isPermanent;
        this.type = type;
    }

    public String toString(){
        //melancholy
        String statusDesc = "Name: " + name + "\t";
        if(name.length() < 16) statusDesc += "\t";
        statusDesc += "Type: " + getStatusName() + " : ";
        return statusDesc;
    }
    public abstract boolean isEquals(Status status);
    public void statusInflict(BattleEntity battleEntity){
        battleEntity.addStatuses(this);
    }
    public void statusDecrement(BattleEntity battleEntity){
        if(isPermanent){
            return;
        }
        duration--;
        if(duration == 0) battleEntity.statusRemove(this);
    }
    public String getStatusName(){
        return (String) statusNames.get(type);
    }
}
