package Items;

import Entities.*;
import Statuses.*;

public abstract class Item {
    protected String name;

    public Item(){

    }
    public Item(String name){
        this.name = name;
    }

    //GETTER
    public String getName() {
        return name;
    }
    //SETTER
    public void setName(String name) {
        this.name = name;
    }

    public void useItem(BattleEntity battleEntity) {
        System.out.println(battleEntity.getName() + " used " + name);

    }
}
