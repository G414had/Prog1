import java.util.HashMap;
import java.util.InputMismatchException;

public class Project {
    // HashMap with key string which points to a second HashMap which contains the data of moves
    // A MoveType of | 1 is normal | 2 is fire | 3 is water | 4 is grass
    public static HashMap<String, HashMap<String, Integer>> moveSet = new HashMap<>();
    public static void main(String[] args) {

        // Creating moves
        HashMap<String, Integer> Tackle = new HashMap<>();
        Tackle.put("Damage", 5);
        Tackle.put("Speed", 50);
        Tackle.put("MoveType", 1);
        moveSet.put("Tackle", Tackle);
        HashMap<String, Integer> QuickAttack = new HashMap<>();
        QuickAttack.put("Damage", 3);
        QuickAttack.put("Speed", 100);
        QuickAttack.put("MoveType", 1);
        moveSet.put("QuickAttack", QuickAttack);
        HashMap<String, Integer> Ember = new HashMap<>();
        Ember.put("Damage", 9);
        Ember.put("Speed", 40);
        Ember.put("MoveType", 2);
        moveSet.put("Ember", Ember);
        HashMap<String, Integer> FireFang = new HashMap<>();
        FireFang.put("Damage", 6);
        FireFang.put("Speed", 45);
        FireFang.put("MoveType", 2);
        moveSet.put("FireFang", FireFang);
        HashMap<String, Integer> VineWhip = new HashMap<>();
        VineWhip.put("Damage", 5);
        VineWhip.put("Speed", 65);
        VineWhip.put("MoveType", 4);
        moveSet.put("VineWhip", VineWhip);
        HashMap<String, Integer> RazorLeaf = new HashMap<>();
        RazorLeaf.put("Damage", 4);
        RazorLeaf.put("Speed", 70);
        RazorLeaf.put("MoveType", 4);
        moveSet.put("RazorLeaf", RazorLeaf);
        HashMap<String, Integer> WaterGun = new HashMap<>();
        WaterGun.put("Damage", 5);
        WaterGun.put("Speed", 66);
        WaterGun.put("MoveType", 3);
        moveSet.put("WaterGun", WaterGun);
        HashMap<String, Integer> WaterPulse = new HashMap<>();
        WaterPulse.put("Damage", 7);
        WaterPulse.put("Speed", 47);
        WaterPulse.put("MoveType", 3);
        moveSet.put("WaterPulse", WaterPulse);
        
        // Creating ally Pokemon
        AllyPokemon charmander = new AllyPokemon("Charmander", "fire", 37, 8, 2, "Tackle", "QuickAttack", "Ember", "FireFang");
        AllyPokemon bulbasaur = new AllyPokemon("Bulbasaur", "grass", 36, 6, 3, "Tackle", "QuickAttack", "VineWhip", "RazorLeaf");
        AllyPokemon squirtle = new AllyPokemon("Squirtle", "water", 35, 9, 2, "Tackle", "QuickAttack", "WaterGun", "WaterPulse");
        
        // Creating enemy Pokemon
        EnemyPokemon cyndaquil = new EnemyPokemon("Cyndaquil", "fire", 39, 8, 2, "Tackle", "QuickAttack", "Ember", "FireFang");
        EnemyPokemon chikorita = new EnemyPokemon("Chikorita", "grass", 37, 7, 2, "Tackle", "QuickAttack", "VineWhip", "RazorLeaf");
        EnemyPokemon totodile = new EnemyPokemon("Totodile", "water", 42, 6, 5, "Tackle", "QuickAttack", "WaterGun", "WaterPulse");

        Pokemon[] allies = { charmander, bulbasaur, squirtle };
        Pokemon[] enemies = { totodile, chikorita, cyndaquil };

        Game game = new Game(allies, enemies);
        game.startGame();
        
    }
}

// Handles all actual game functions
class Game {
    protected int choice, randomCount, randomNumber, allyIndex, enemyIndex, allySpeed, enemySpeed, allyDamage, enemyDamage;
    protected Pokemon currentAlly, currentEnemy;
    protected Pokemon[] allies, enemies;
    protected String enemyChoice;
    protected boolean skipTurn;
    int[] randomList = {
        2, 3, 1, 4, 3, 1, 2, 4, 1, 3,
        4, 1, 2, 3, 2, 4, 3, 1, 2, 4,
        1, 3, 4, 2, 3, 1, 4, 2, 1, 3,
        2, 4, 1, 3, 4, 2, 1, 3, 4, 2,
        3, 1, 4, 2, 3, 1, 4, 2, 1, 3,
        4, 1, 3, 2, 4, 1, 3, 2, 4, 1,
        2, 3, 4, 1, 2, 3, 4, 1, 3, 2,
        4, 1, 3, 4, 2, 1, 3, 2, 4, 1,
        2, 4, 3, 1, 2, 3, 4, 1, 2, 4,
        3, 1, 3, 2, 4, 1, 3, 2, 4, 1};

    Game(Pokemon[] allies, Pokemon []enemies){
        this.allies = allies;
        this.enemies = enemies;
        this.allyIndex = 0;
        this.enemyIndex = 0;
        this.currentAlly = allies[0];
        this.currentEnemy = enemies[0];
    }
    
    public Pokemon getCurrentAlly(){
        return currentAlly;
    }

    public Pokemon getCurrentEnemy(){
        return currentEnemy;
    }
    
    // Handles the actual process of the game
    public void startGame(){
        skipTurn = false;
        while (true) {
            if (currentAlly.hp <= 0) {
                skipTurn = false;
                allyIndex++;
                if (allyIndex >= allies.length) {
                    System.out.println("You have no Pokemon left! You blacked out!");
                    break;
                }
                System.out.println("Oh no! " + currentAlly.name + " fainted!");
                currentAlly = allies[allyIndex];
                System.out.println("You send out " + currentAlly.name);
            }

            if (currentEnemy.hp <= 0) {
                skipTurn = false;
                enemyIndex++;
                if (enemyIndex >= enemies.length) {
                    System.out.println("The enemy has no Pokemon left! You won!");
                    break;
                }
                System.out.println(currentEnemy.name + " fainted!");
                currentEnemy = enemies[enemyIndex];
                System.out.println("The enemy sends out " + currentEnemy.name);
            }

            fightSequence();
        }
    }

    
    // Purely used for the fight sequence
    private void fightSequence(){
        // Try catch to validate input - Catches numbers out of bounds and non-integer inputs
        while (true) {
            try {
                System.out.println("Select a move (input 1-4): ");
                System.out.println("1: " + getCurrentAlly().move1);
                System.out.println("2: " + getCurrentAlly().move2);
                System.out.println("3: " + getCurrentAlly().move3);
                System.out.println("4: " + getCurrentAlly().move4);
                choice = In.nextInt();

                if (choice < 1 || choice > 4) {
                    throw new IllegalArgumentException("Your choice must be between either 1, 2, 3, or 4");
                } else {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. " + e.getMessage());
                In.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Your input must be an integer");
                In.nextLine();
            }
        }

        if (choice == 1) {
            getCurrentAlly().setSelectedMove(getCurrentAlly().move1);
        } else if (choice == 2) {
            getCurrentAlly().setSelectedMove(getCurrentAlly().move2);
        } else if (choice == 3) {
            getCurrentAlly().setSelectedMove(getCurrentAlly().move3);
        } else {
            getCurrentAlly().setSelectedMove(getCurrentAlly().move4);
        }

        randomCount++;
        if (randomCount >= 100) {
            System.out.println("This game went on so long that the random numbers array ran out");
            System.out.println("I didn't think that was even possible");
            System.out.println("bye bye");
            while (true) {
            }
        }
        randomNumber = randomList[randomCount];
        
        if (randomNumber == 1) {
            enemyChoice = getCurrentEnemy().move1;
        } else if (randomNumber == 2) {
            enemyChoice = getCurrentEnemy().move2;
        } else if (randomNumber == 3) {
            enemyChoice = getCurrentEnemy().move3;
        } else {
            enemyChoice = getCurrentEnemy().move4;
        }
        
        allySpeed = Project.moveSet.get(currentAlly.selectedMove).get("Speed");
        enemySpeed = Project.moveSet.get(enemyChoice).get("Speed");

        if (allySpeed > enemySpeed) {
            System.out.println("You were faster");
            System.out.println("Enemy used " + enemyChoice);
            System.out.println("Ally used " + currentAlly.selectedMove);
            allyDamage = currentAlly.fight(currentEnemy,  currentAlly.selectedMove);
            getCurrentEnemy().hp -= allyDamage;
            System.out.println(getCurrentEnemy().name + " took " + allyDamage + " damage!");
            if (getCurrentEnemy().hp <= 0) {
                System.out.println(currentEnemy.name + " has no hp left!");
            } else {
                System.out.println(currentEnemy.name + " has " + currentEnemy.hp + " health left!");
            }
            if (getCurrentEnemy().hp <= 0) {
                skipTurn = true;
            }
            if (!skipTurn) {
                enemyDamage = getCurrentEnemy().fight(currentAlly, enemyChoice);
                currentAlly.hp -= enemyDamage;
                System.out.println("You took " + enemyDamage + " damage!");
                if (currentAlly.hp <= 0) {
                    System.out.println(currentAlly.name + " has no hp left!");
                } else {
                    System.out.println(currentAlly.name + " has " + currentAlly.hp + " health left!");
                }
            }

        } else if (enemySpeed > allySpeed) {
            System.out.println("Enemy was faster");
            System.out.println("Enemy used " + enemyChoice);
            System.out.println("You used " + currentAlly.selectedMove);
            enemyDamage =  currentEnemy.fight( currentAlly, enemyChoice);
            currentAlly.hp -= enemyDamage;
            System.out.println( currentAlly.name + " took " + enemyDamage + " damage!");
            if (getCurrentAlly().hp <= 0) {
                System.out.println(currentAlly.name + " has no hp left!");
            } else {
                System.out.println(currentAlly.name + " has " + currentAlly.hp + " health left!");
            }
            if (currentAlly.hp <= 0) {
                skipTurn = true;
            }
            if (!skipTurn) {
                allyDamage = currentAlly.fight(currentEnemy,  currentAlly.selectedMove);
                getCurrentEnemy().hp -= allyDamage;
                System.out.println(getCurrentEnemy().name + " took " + allyDamage + " damage!");
                if (getCurrentEnemy().hp <= 0) {
                    System.out.println(currentEnemy.name + " has no hp left!");
                } else {
                    System.out.println(currentEnemy.name + " has " + currentEnemy.hp + " health left!");
                }
            }
        } else {
            System.out.println("Same speed");
            System.out.println("Enemy used " + enemyChoice);
            System.out.println("Ally used " + currentAlly.selectedMove);
            allyDamage = currentAlly.fight(currentEnemy,  currentAlly.selectedMove);
            getCurrentEnemy().hp -= allyDamage;
            System.out.println(getCurrentEnemy().name + " took " + allyDamage + " damage!");
            if (getCurrentEnemy().hp <= 0) {
                System.out.println(currentEnemy.name + " has no hp left!");
            } else {
                System.out.println(currentEnemy.name + " has " + currentEnemy.hp + " health left!");
            }
            if (getCurrentEnemy().hp <= 0) {
                skipTurn = true;
            }
            if (!skipTurn) {
                enemyDamage = getCurrentEnemy().fight(currentAlly, enemyChoice);
                currentAlly.hp -= enemyDamage;
                System.out.println("You took " + enemyDamage + " damage!");
                if (currentAlly.hp <= 0) {
                    System.out.println(currentAlly.name + " has no hp left!");
                } else {
                    System.out.println(currentAlly.name + " has " + currentAlly.hp + " health left!");
                }
            }

        }
        
    }

}

// Parent Pokemon class
class Pokemon {
    protected String name, type, selectedMove, move1, move2, move3, move4;
    protected int hp, attack, defense, critCheck, crit, critNum, maxhp, damage;
    protected double effectiveness, damageStore;
    

    Pokemon(String name, String type, int hp, int attack, int defense, String move1, String move2, String move3, String move4){
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.maxhp = hp;
        this.attack = attack;
        this.defense = defense;
        this.move1 = move1;
        this.move2 = move2;
        this.move3 = move3;
        this.move4 = move4;
    }

    // Empty method used in other classes for damage calculation.
    public int fight(Pokemon target, String selectedMove){
        return 0;
    }

    // Checking if the attack is critical for double damage
    // Due to restrictions and not being able to use math.random, Every 7 attacks from the player is a crit
    public int critCheck(){
        if (crit == 7) {
            crit = 0;
            return 2;
        } else {
            crit++;
            return 1;
        }
    }

    public void setSelectedMove(String selectedMove) {
        this.selectedMove = selectedMove;

    }
}

// Handles creation and damage calculations for allies. Stronger
class AllyPokemon extends Pokemon{
    AllyPokemon(String name, String type, int hp, int attack, int defense, String move1, String move2, String move3, String move4){
        super(name, type, hp, attack, defense, move1, move2, move3, move4);
    }

    @Override
    public int fight(Pokemon target, String selectedMove){
        damage = 0;
        damageStore = 0;
        if (Project.moveSet.get(selectedMove).get("MoveType") == 2 && target.type == "water") {
            effectiveness = 0.5;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 2 && target.type == "grass") {
            effectiveness = 2;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 3 && target.type == "fire") {
            effectiveness = 2;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 3 && target.type == "grass") {
            effectiveness = 0.5;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 4 && target.type == "fire") {
            effectiveness = 0.5;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 2 && target.type == "water") {
            effectiveness = 0.5;
        } else {
            effectiveness = 1;
        }
        // Allies are able to crit
        damageStore = (Project.moveSet.get(selectedMove).get("Damage") * effectiveness * critCheck() + this.attack) - (target.defense);
        while (damage + 1 <= damageStore) {
            damage++;
        }
        if (damage <= 0) {
            System.out.println("It did no damage!");
            return 0;
        }
        
        return damage;
    }
}


// Handles creation and damage calculations for enemy pokemon. Weaker
class EnemyPokemon extends Pokemon {
    EnemyPokemon(String name, String type, int hp, int attack, int defense, String move1, String move2, String move3, String move4){
        super(name, type, hp, attack, defense, move1, move2, move3, move4);
    }

    @Override
    public int fight(Pokemon target, String selectedMove){
        damage = 0;
        damageStore = 0;
        if (Project.moveSet.get(selectedMove).get("MoveType") == 2 && target.type == "water") {
            effectiveness = 0.5;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 2 && target.type == "grass") {
            effectiveness = 2;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 3 && target.type == "fire") {
            effectiveness = 2;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 3 && target.type == "grass") {
            effectiveness = 0.5;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 4 && target.type == "fire") {
            effectiveness = 0.5;
        } else if (Project.moveSet.get(selectedMove).get("MoveType") == 2 && target.type == "water") {
            effectiveness = 0.5;
        } else {
            effectiveness = 1;
        }
        // Enemies are unable to crit
        // Target defense is also raised
        damageStore = (Project.moveSet.get(selectedMove).get("Damage") * effectiveness + this.attack) - (target.defense + 5);
        while (damage + 1 <= damageStore) {
            damage++;
        }
        if (damage <= 0) {
            System.out.println("It did no damage!");
            return 0;
        }
        return damage;
    }
}

