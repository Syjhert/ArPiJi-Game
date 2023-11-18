package Entities;

import Moves.*;
import Statuses.DoT;
import Statuses.StatusType;

public class Slime extends Enemy{

    public Slime() {
        super("Slime", 50, 20, 0.1f);
        Move move;
        addMoves(new DamagingM("Slimeball", 0.8, "RANGED", "NONE"));
        move = new DamagingM("Sludge", 0.2, "RANGED", "NONE");
        move.addPassiveMove(new StatusM(new DoT("Sludged", StatusType.POISON)));
        addMoves(move);
    }
    public Slime(String name, int healthPoints, int attack, float trapRate) {
        super(name, healthPoints, attack, trapRate);
    }
}
