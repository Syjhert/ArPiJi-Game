import Entities.*;
import Items.*;
import Weapons.*;
import Statuses.*;
import Moves.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Move move;
        Weapon sword = new Weapon("Sword", 15, .20, .50);
        sword.addMove(new DamagingM("Basic Swing", 0.8));
        move = new DamagingM("Duel Dance", 0.4);
        ((DamagingM)move).addPassiveMove(new StatusM(new Buff("Duelist", 12, 0.3f), false));
        sword.addMove(move);
        move = new DamagingM("Force Swing", 1.4);
        ((DamagingM)move).addPassiveMove(new StatusM(new Debuff("Weakened",24, 0.2f), false));
        sword.addMove(move);

        Weapon spear = new Weapon("Spear", 10, .40, .40);
        move = new DamagingM("Dire Sharpening", 0.6);
        ((DamagingM)move).addPassiveMove(new StatusM(new Buff("Sharpened", 13, 0.1f), false));
        spear.addMove(move);
        move = new DamagingM("Toxic Poke", 0.8);
        ((DamagingM)move).addPassiveMove(new StatusM(new DoT("Poison Sting", 31)));
        spear.addMove(move);
        spear.addMove(new DamagingM("Impact Thrust", 1.0));

        Weapon claymore = new Weapon("Claymore", 30, .05, .80);
        claymore.addMove(new DamagingM("Swaying Slash", 1));
        move = new DamagingM("Guarding Push", 0.5);
        ((DamagingM)move).addPassiveMove(new StatusM(new Buff("Guarded",11, 0.25f), false));
        claymore.addMove(move);
        move = new DamagingM("Reckless Crash", 1.5);
        ((DamagingM)move).addPassiveMove(new DamagingM(0.3, false));
        claymore.addMove(move);


        Weapon[] weapons = {sword, spear, claymore};

        Enemy slime = new Enemy("Slime", 50, 20, 0.1f);
        slime.addMoves(new DamagingM("Slimeball", 0.8));
        move = new DamagingM("Sludge", 0.2);
        ((DamagingM)move).addPassiveMove(new StatusM("Sludge", new DoT("Sludged", 31)));
        slime.addMoves(move);

        Enemy skeleton = new Enemy("Skeleton", 40, 30, 0.3f);
        skeleton.addMoves(new DamagingM("Arm Swing", 0.8));
        skeleton.addMoves(new DamagingM("Skull Bash", 1.2));

        Enemy zombie = new Enemy("Zombie", 60, 10, 0.5f);
        zombie.addMoves(new DamagingM("Arm Swing", 0.8));
        move = new DamagingM("Bite", 0.4);
        ((DamagingM)move).addPassiveMove(new StatusM("Bite", new DoT("Bitten", 32)));
        zombie.addMoves(move);
        move = new DamagingM("Infect", 0.4);
        ((DamagingM)move).addPassiveMove(new StatusM("Infect", new DoT("Infected", 31)));
        zombie.addMoves(move);

        Enemy[] enemies = {slime, skeleton, zombie};

        Item potion = new HealingI("Potion", new HealingM("Heal", 40));
        Item vigor = new StatusI("Vigor", new StatusM(new Buff("Vigor", 11, .25f)));
        Item severity = new StatusI("Severity", new StatusM(new Buff("Severity", 12, .3f)));
        Item fortuity = new StatusI("Fortuity", new StatusM(new Buff("Fortune", 13, .2f)));
        Item fatality = new StatusI("Fatality", new StatusM(new Buff("Fatality", 14, .5f)));
        Item singleBarrel = new DamagingI("Single Barrel Shotgun", 50);

        Item[] items = {potion, vigor, severity, fortuity, fatality, singleBarrel};
        ArrayList<Item> bag = new ArrayList<Item>(Arrays.asList(items));

        System.out.print("Welcome to <ArPiJi Game>!\nLets start with your name: ");
        Player player1 = new Player(scanner.nextLine());
        System.out.println("Hello "+player1.getName()+"! Let's start your adventure by picking a weapon. What do you want?");
        System.out.println("[1] "+sword);
        System.out.println("[2] "+spear);
        System.out.println("[3] "+claymore);
        int choice = scanner.nextInt();
        Weapon currentWeapon;
        currentWeapon = weapons[choice-1];
        player1.equipWeapon(currentWeapon);
        System.out.println("You have equipped the <"+currentWeapon.getName()+">!");

        int input = -1;
        do{
            mainMenu();
            input = scanner.nextInt();
            if(input == 1) battle(player1, enemies, bag);
            else if(input == 2) player1.displayStats();
            else{
                if(input != 3) System.out.println("Input not valid!");
            }
        }while(input != 3);
        System.out.println("Thank you for playing my game");
    }
    static void mainMenu(){
        System.out.println("\t\tMain Menu\t\t");
        System.out.println("What do you want to do?");
        System.out.println("[1] Enter battle\t\t[2] Check stats\n[3] Exit");
    }
    static void battle(Player player, Enemy[] enemies, ArrayList<Item> bag){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        Enemy currEnemy = new Enemy();
        currEnemy.copy(enemies[random.nextInt(3)]);

        System.out.println("-------------------------------------------------");

        int battleInput = -1;
        int bagInput = -1;
        do{
            if(battleInput != -1 && battleInput != 2 && battleInput != 3){
                System.out.println("-------------------------------------------------");
                System.out.println("It is now <"+currEnemy.getName()+"'s> turn!");
                currEnemy.chooseMove(currEnemy, player);
                System.out.println("-------------------------------------------------");
                player.statusesDecrement();
                currEnemy.statusesDecrement();
                System.out.println("-------------------------------------------------");
                if(player.getHealthPoints() < 0){
                    System.out.println("\nYou died bruh!");
                    break;
                }
                System.out.println("The enemy "+currEnemy.getName()+"'s health is now "+currEnemy.getHealthPoints()+"!");
                System.out.println("Your current health is now "+player.getHealthPoints()+"!");
            }
            System.out.println("-------------------------------------------------");
            System.out.println("You have encountered a <"+currEnemy.getName()+">! What do you want to do?");
            System.out.println("[1] Attack\t\t\t\t[2] Check my stats");
            System.out.println("[3] Check enemy stats\t[4] Open Bag\n[5] Run");
            battleInput = scanner.nextInt();
            if(battleInput == 1){
                System.out.println("You chose to attack!");
                player.chooseMove(player, currEnemy);
                if(currEnemy.getHealthPoints() <= 0){
                    System.out.println("\nCongratulations! You have defeated the enemy!");
                    break;
                }
            }
            else if(battleInput == 2){
                player.displayStats();
            }
            else if(battleInput == 3){
                currEnemy.displayStats();
            }
            else if(battleInput == 4){
                do{
                    openBag(bag);
                    System.out.println("What do you want to use?");
                    bagInput = scanner.nextInt();
                    if(bagInput > bag.size()) System.out.println("This is an invalid item choice!\n");
                    else break;
                }while(true);
                Item usedItem = bag.get(bagInput-1);
                if(usedItem instanceof DamagingI){
                    ((DamagingI)usedItem).useItem(player, currEnemy);
                }
                else{
                    usedItem.useItem(player);
                }
            }
            else if(battleInput == 5){
                if(random.nextDouble(1) <= currEnemy.getTrapRate()){
                    System.out.println("You tried to run but was trapped by the enemy!");
                }else{
                    System.out.println("You tried to run and successfully escaped!");
                    break;
                }
            }
            else{
                System.out.println("This is not a valid input!");
                battleInput = -1;
            }
        }while(true);
        player.resetStats();
    }
    static void openBag(ArrayList<Item> bag){
        System.out.println("Bag Contents:");
        for(int i=1; i<=bag.size(); i++){
            System.out.print("["+i+"] "+bag.get(i-1).getName());
            if(i%3 == 0 || i+1 == bag.size()){
                System.out.print("\n");
                continue;
            }
            for(int j=(bag.get(i-1).getName().length())/4; j<=3; j++){
                System.out.print("\t");
            }
        }
    }
}