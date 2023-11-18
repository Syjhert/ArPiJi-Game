package Entities;

import Moves.*;
import Statuses.DoT;
import Statuses.StatusType;

public class Zombie extends Enemy{

    public Zombie(){
        super("Zombie", 60, 10, 0.5f);
        Move move;
        addMoves(new DamagingM("Arm Swing", 0.8, "MELEE", "NONE"));
        move = new DamagingM("Bite", 0.4, "MELEE", "NONE");
//        ((DamagingM)move.addPassiveMove(new StatusM("Bite", new DoT("Bitten", 32)));
        move.addPassiveMove(new StatusM(new DoT("Bitten", StatusType.BLEED)));
        addMoves(move);
        move = new DamagingM("Infect", 0.4, "MELEE", "NONE");
        move.addPassiveMove(new StatusM(new DoT("Infected", StatusType.POISON)));
        addMoves(move);
    }
    public Zombie(String name, int healthPoints, int attack, float trapRate) {
        super(name, healthPoints, attack, trapRate);
    }
}
