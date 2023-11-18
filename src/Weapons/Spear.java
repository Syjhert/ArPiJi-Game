package Weapons;

import Moves.*;
import Statuses.Buff;
import Statuses.DoT;
import Statuses.StatusType;

public class Spear extends Weapon{

    public Spear() {
        super("Spear", 10, .40, .40);
        Move move;
        move = new DamagingM("Dire Sharpening", 0.6, "MELEE", "NONE");
        move.addPassiveMove(new StatusM(new Buff("Sharpened", StatusType.CRITCHANCE, 0.1f), false));
        addMove(move);
        move = new DamagingM("Toxic Poke", 0.8, "MELEE", "NONE");
        move.addPassiveMove(new StatusM(new DoT("Poison Sting", StatusType.POISON)));
        addMove(move);
        addMove(new DamagingM("Impact Thrust", 1.0, "MELEE", "NONE"));
    }
}
