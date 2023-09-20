package Items;

import Entities.BattleEntity;
import Moves.StatusM;

public class StatusI extends Item{

    StatusM statusM;

    public StatusI(String name, StatusM statusM) {
        super(name);
        this.statusM = statusM;
    }

    @Override
    public void useItem(BattleEntity battleEntity) {
        super.useItem(battleEntity);
        statusM.doMove(battleEntity, battleEntity);
    }
}
