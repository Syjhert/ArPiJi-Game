import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Weapon sword = new Weapon("Sword", 15, .20, .50);
        sword.addMoves("Basic Swing", 0.8);
        sword.addMoves("Duel Dance", 0.6, 12, 0.3, false);
        sword.addMoves("Force Swing", 1.4, 22, 0.3, false);
        Weapon spear = new Weapon("Spear", 10, .40, .40);
        spear.addMoves("Dire Sharpening", 0.6, 13, 0.1, false);
        spear.addMoves("Toxic poke", 0.8, 31, -30, true);
        spear.addMoves("Impact Thrust", 1.0);
        Weapon claymore = new Weapon("Claymore", 30, .05, .80);

        Weapon[] weapons = {sword, spear, claymore};

        Enemy slime = new Enemy("Slime", 50, 20, 0.1);
        slime.addMoves("Slimeball", 0.8);
        slime.addMoves("Sludge", 0.2, 31, -30, true);
        Enemy skeleton = new Enemy("Skeleton", 40, 30, 0.3);
        skeleton.addMoves("Arm Swing", 0.8);
        skeleton.addMoves("Skull Bash", 1.0);
        Enemy zombie = new Enemy("Zombie", 60, 10, 0.5);
        zombie.addMoves("Arm Swing", 0.8);
        zombie.addMoves("Bite", 0.4, 32, -30, true);
        zombie.addMoves("Infect", 0.4, 31, -30, true);

        Enemy[] enemies = {slime, skeleton, zombie};

        Item potion = new Item("Potion", 10, 50);
        Item vigor = new Item("Vigor", 11, .25);
        Item severity = new Item("Severity", 12, 0.3);
        Item fortuity = new Item("Fortuity", 13, 0.2);
        Item fatality = new Item("Fatality", 14, 0.5);
        Item singleBarrel = new Item("Single Barrel Shotgun", 1, 50);

        Item[] items = {potion, vigor, severity, fortuity, fatality, singleBarrel};
        ArrayList<Item> bag = new ArrayList<Item>(Arrays.asList(items));

        System.out.print("Welcome to <ArPiJi Game>!\nLets start with your name: ");
        Player player1 = new Player(scanner.nextLine());
        System.out.println("Hello "+player1.name+"! Let's start your adventure by picking a weapon. What do you want?");
        System.out.println("[1] "+sword);
        System.out.println("[2] "+spear);
        System.out.println("[3] "+claymore);
        int choice = scanner.nextInt();
        Weapon currentWeapon;
        currentWeapon = weapons[choice-1];
        player1.equipWeapon(currentWeapon);
        System.out.println("You have equipped the <"+currentWeapon.name+">!");

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
                System.out.println("It is now <"+currEnemy.name+"'s> turn!");
                currEnemy.chooseMove(currEnemy, player);
                System.out.println("-------------------------------------------------");
                player.statusesDecrement();
                currEnemy.statusesDecrement();
                System.out.println("-------------------------------------------------");
                if(player.healthPoints < 0){
                    System.out.println("\nYou died bruh!");
                    break;
                }
                System.out.println("The enemy "+currEnemy.name+"'s health is now "+currEnemy.healthPoints+"!");
                System.out.println("Your current health is now "+player.healthPoints+"!");
            }
            System.out.println("-------------------------------------------------");
            System.out.println("You have encountered a <"+currEnemy.name+">! What do you want to do?");
            System.out.println("[1] Attack\t\t\t\t[2] Check my stats");
            System.out.println("[3] Check enemy stats\t[4] Open Bag\n[5] Run");
            battleInput = scanner.nextInt();
            if(battleInput == 1){
                System.out.println("You chose to attack!");
                player.chooseMove(player, currEnemy);
                if(currEnemy.healthPoints <= 0){
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
                Item usedItem = new Item();
                do{
                    openBag(bag);
                    System.out.println("What do you want to use?");
                    bagInput = scanner.nextInt();
                    if(bagInput > bag.size()) System.out.println("This is an invalid item choice!\n");
                    else break;
                }while(true);
                usedItem.copy(bag.get(bagInput-1));
                if(usedItem.typeAndEffect == 1){
                    usedItem.useItem(currEnemy);
                }
                else{
                    usedItem.useItem(player);
                }
            }
            else if(battleInput == 5){
                if(random.nextDouble(1) <= currEnemy.trapRate){
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
            System.out.print("["+i+"] "+bag.get(i-1).name);
            if(i%3 == 0 || i+1 == bag.size()){
                System.out.print("\n");
                continue;
            }
            for(int j=(bag.get(i-1).name.length())/4; j<=3; j++){
                System.out.print("\t");
            }
        }
    }
}