package Moves;

import Entities.BattleEntity;
import Statuses.*;

public class StatusM extends Move{ //Status is always a solo move without passive or a passive to another move
    private final Status status;
    public StatusM(String name, Status status){ //Pure status move
        super(name);
        this.status = status;
    }
    public StatusM(String name, Status status, boolean receiverDirected){ //Pure SELF status move
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
    public int doMove(BattleEntity attacker, BattleEntity receiver) {
        if(!isPassive) System.out.println("<"+attacker.getName()+"> used <"+name+">!");
        if(receiverDirected){
            if(status instanceof DoT) ((DoT) status).setDamage(attacker, receiver);
            if(attacker != receiver) System.out.println(attacker.getName()+ " inflicted "+receiver.getName()+ " with the " +status.getName());
            status.statusInflict(receiver);
        }
        else {
            if(status instanceof DoT) ((DoT) status).setDamage(receiver, receiver);
            System.out.println(attacker.getName()+ " got the " + status.getName());
            status.statusInflict(attacker);
        }
        if(attacker.isDead()) return -1;
        else if(receiver.isDead()) return 1;
        else return 0;
    }
}
