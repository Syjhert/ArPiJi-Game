public class Item {
    String name;
    int typeAndEffect;
    double effectValue;

    Item(){

    }
    Item(String name, int typeAndEffect, double effectValue){
        this.name = name;
        this.typeAndEffect = typeAndEffect;
        this.effectValue = effectValue;
    }
    public void useItem(Player player){
        System.out.println("You used "+name+"!");
        if(typeAndEffect == 10){
            player.healthPoints += (int)effectValue;
            if(player.healthPoints > player.maxHealthPointsBattle) player.healthPoints = player.maxHealthPointsBattle;
            System.out.println("You were healed by "+(int)effectValue+" points!");
            System.out.println("Your current health is now "+player.healthPoints+" out of "+player.maxHealthPointsBattle+"!");
            return;
        }
        player.addStatuses(new Status(typeAndEffect, 999, effectValue));
    }
    public void useItem(Enemy enemy){
        System.out.println("You used "+name+"!");
        if(typeAndEffect == 1){
            enemy.healthPoints -= (int)effectValue;
            System.out.println("The enemy <"+enemy.name+"> has been damaged by "+(int)effectValue);
            if(enemy.healthPoints <= 0){
                System.out.println("\nCongratulations! You have defeated the enemy!");
            }
            System.out.println("The enemy "+enemy.name+"'s health is now "+enemy.healthPoints);
        }
    }

    public void copy(Item item){
        name = item.name;
        typeAndEffect = item.typeAndEffect;
        effectValue = item.effectValue;
    }

}
