import Entities.*;
import Items.*;
import Weapons.*;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Weapon[] weapons = Limited_Objs.createLimWeaps();
        Item[] items = Limited_Objs.createLimItems();

        Scanner scanner = new Scanner(System.in);

        ArrayList<Item> bag = new ArrayList<>(Arrays.asList(items));

        System.out.print("Welcome to <ArPiJi Game>!\nLets start with your name: ");
        Player player1 = new Player(scanner.nextLine());
        System.out.println("Hello "+player1.getName()+"! Let's start your adventure by picking a weapon. What do you want?");
        System.out.println("[1] "+weapons[0]);
        System.out.println("[2] "+weapons[1]);
        System.out.println("[3] "+weapons[2]);
        int choice = scanner.nextInt();
        Weapon currentWeapon;
        currentWeapon = weapons[choice-1];
        player1.equipWeapon(currentWeapon);
        System.out.println("You have equipped the <"+currentWeapon.getName()+">!");

        int input;
        do{
            mainMenu();
            input = scanner.nextInt();
            if(input == 1) battle(player1, bag);
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
    static void battle(Player player, ArrayList<Item> bag) throws Exception {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        Enemy currEnemy = Limited_Objs.createLimEnemy();
        System.out.println("-------------------------------------------------");

        int battleInput;
        int bagInput;
        int state = 0;
        do{
            System.out.println("-------------------------------------------------");
            System.out.println("You have encountered a "+currEnemy.getName()+"! What do you want to do?");
            System.out.println("[1] Attack\t\t\t\t[2] Check my stats");
            System.out.println("[3] Check enemy stats\t[4] Open Bag\n[5] Run");
            battleInput = scanner.nextInt();
            if(battleInput == 1){
                System.out.println("You chose to attack!");
                if((state = player.chooseMove(player, currEnemy)) != 0){
                    deathMessage(state == 1 ? currEnemy.getName() : player.getName());
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
                    if((state = ((DamagingI)usedItem).useItem(player, currEnemy)) != 0){
                        deathMessage(state == 1 ? currEnemy.getName() : player.getName());
                        break;
                    }
                }
                else{
                    usedItem.useItem(player);
                }
            }
            else if(battleInput == 5){
                if(random.nextDouble() <= currEnemy.getTrapRate()){
                    System.out.println("You tried to run but was trapped by the enemy!");
                }else{
                    break;
                }
            }
            else{
                System.out.println("This is not a valid input!");
                battleInput = -1;
            }
            if(battleInput != 2 && battleInput != 3){
                System.out.println("-------------------------------------------------");
                System.out.println("It is now "+currEnemy.getName()+"'s turn!");
                if((state = currEnemy.chooseMove(currEnemy, player)) != 0){
                    deathMessage(state == 1 ? currEnemy.getName() : player.getName());
                    break;
                }
                System.out.println("-------------------------------------------------");
                if((state = player.statusesDecrement()) != 0){
                    deathMessage(state == 1 ? currEnemy.getName() : player.getName());
                    break;
                }
                if((state = currEnemy.statusesDecrement()) != 0){
                    deathMessage(state == 1 ? currEnemy.getName() : player.getName());
                    break;
                }
                System.out.println("-------------------------------------------------");
                System.out.println("The enemy "+currEnemy.getName()+"'s health is now "+currEnemy.getHealthPoints()+"!");
                System.out.println("Your current health is now "+player.getHealthPoints()+"!");
            }
        }while(true);
        System.out.println("-------------------------------");
        System.out.println("-------------------------------");
        if(state == 1){
            System.out.println("Congratulations! You have defeated the enemy!");
        }else if(state == -1){
            System.out.println("Unfortunately, you lost!");
        }else{
            System.out.println("You tried to run and successfully escaped!");
        }
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

    static void deathMessage(String name){
        System.out.println(name + " died!");
    }
}