package Items;

import Entities.BattleEntity;
import Moves.*;

public class HealingI extends Item{

    HealingM healingM;

    public HealingI(String name, HealingM healingM) {
        super(name);
        this.healingM = healingM;
    }

    @Override
    public void useItem(BattleEntity battleEntity){
        super.useItem(battleEntity);
        healingM.doMove(battleEntity, battleEntity);
    }
    //OVERRIDE OVERLOAD
    public void useItem(BattleEntity attacker, BattleEntity receiver){ //for future enemy healing
        super.useItem(attacker);
        healingM.doMove(attacker, receiver);
    }
}
