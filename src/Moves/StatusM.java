package Moves;

import Entities.BattleEntity;
import Statuses.*;

public class StatusM extends Move{ //Status is always a solo move without passive or a passive to another move
    private Status status;
    public StatusM(String name, Status status){
        super(name);
        this.status = status;
    }
    public StatusM(String name, Status status, boolean receiverDirected){
        super(name);
        this.receiverDirected = receiverDirected;
        this.status = status;
    }
    public StatusM(Status status){
        super();
        this.status = status;
    }
    public StatusM(Status status, boolean receiverDirected){
        super();
        this.status = status;
        this.receiverDirected = receiverDirected;
    }

    //SETTER

    @Override
    public void doMove(BattleEntity attacker, BattleEntity receiver) {
        if(!isPassive) super.doMove(attacker, receiver);
        if(receiverDirected){
            if(attacker != receiver) System.out.println(attacker.getName()+ " inflicted "+receiver.getName()+ " with the " +status.getName());
            status.statusInflict(receiver);
            if(status instanceof DoT) ((DoT) status).setDamage(attacker, receiver);
        }
        else if(!receiverDirected) {
            System.out.println(attacker.getName()+ " got the " + status.getName());
            status.statusInflict(attacker);
            if(status instanceof DoT) ((DoT) status).setDamage(receiver, receiver);
        }
    }
}
