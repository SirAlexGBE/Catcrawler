import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CatacombCrawler {
    private final Hero hero;
    private final List<Monster> monsters;
    private int heroX;
    private int heroY;
    private final int catacombSize;
    private final char[][] map;

    public CatacombCrawler(String heroName, int size) {
        this.hero = new Hero(heroName);
        this.catacombSize = size;
        this.monsters = new ArrayList<>();
        this.heroX = 0; // Starting position
        this.heroY = 0; // Starting position
        this.map = new char[catacombSize][catacombSize];
        initializeMonsters();
    }

    private void initializeMonsters() {
        int numberOfMonsters = catacombSize * catacombSize / 6; // One monster for every six rooms
        Random random = new Random();
        for (int i = 1; i <= numberOfMonsters; i++) {
            int x, y;
            do {
                x = random.nextInt(catacombSize);
                y = random.nextInt(catacombSize);
            } while (x == 0 && y == 0); // Ensure monster is not in the starting position
            monsters.add(new Monster(i, x, y));
        }
    }

    private void updateMap() {
        for (int i = 0; i < catacombSize; i++) {
            for (int j = 0; j < catacombSize; j++) {
                map[i][j] = '.'; // Empty room
            }
        }
        map[heroX][heroY] = 'H'; // Hero's current position
        for (Monster monster : monsters) {
            map[monster.getX()][monster.getY()] = 'M'; // Mark monster positions
        }
    }

    private void displayMap() {
        for (int i = 0; i < catacombSize; i++) {
            for (int j = 0; j < catacombSize; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void playGame() {
        System.out.println("Welcome to CatacombCrawler!");
        System.out.println("Guide your hero through the catacomb and defeat all monsters.");
        System.out.println("Commands: north, south, east, west");

        try (Scanner scanner = new Scanner(System.in)) {
            while (hero.isAlive() && !monsters.isEmpty()) {
                updateMap();
                displayStatus();
                displayMap();
                System.out.println("Which way do you want to go (north, south, east, west)?");
                String direction = scanner.nextLine().toLowerCase();
                moveHero(direction);
            }
        }

        if (hero.isAlive()) {
            System.out.println("Congratulations! You have defeated all the monsters.");
        } else {
            System.out.println("Game Over! Your hero has died.");
        }
    }

    private void displayStatus() {
        System.out.println(hero.getName() + " at (" + heroX + ", " + heroY + ") with " + hero.getHealth() + " health.");
        // Check for nearby monsters
        int nearbyMonsters = countNearbyMonsters();
        System.out.println("You smell " + nearbyMonsters + " monsters nearby.");
    }

    private int countNearbyMonsters() {
        int count = 0;
        for (Monster monster : monsters) {
            if (isAdjacent(heroX, heroY, monster.getX(), monster.getY())) {
                count++;
            }
        }
        return count;
    }

    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) == 1 && y1 == y2) || (Math.abs(y1 - y2) == 1 && x1 == x2);
    }

    private void moveHero(String direction) {
        int newX = heroX;
        int newY = heroY;

        switch (direction) {
            case "north" -> newX--;
            case "south" -> newX++;
            case "east" -> newY++;
            case "west" -> newY--;
            default -> {
                System.out.println("Invalid direction! You can't move that way.");
                return;
            }
        }

        if (isValidMove(newX, newY)) {
            heroX = newX;
            heroY = newY;
            hero.takeDamage(2); // Damage taken while moving
            checkForMonsters();
            if (heroX == catacombSize - 1 && heroY == catacombSize - 1) {
                System.out.println("Congratulations! You have reached the exit and won the game!");
                System.exit(0);
            }
        } else {
            System.out.println("You can't move that way!");
        }
    }

    private boolean isValidMove(int x, int y) {
        return x >= 0 && x < catacombSize && y >= 0 && y < catacombSize;
    }

    private void checkForMonsters() {
        for (Iterator<Monster> iterator = monsters.iterator(); iterator.hasNext(); ) {
            Monster monster = iterator.next();
            if (monster.getX() == heroX && monster.getY() == heroY) {
                fightMonster(monster);
                iterator.remove(); // Remove monster if defeated
            }
        }
    }

    private void fightMonster(Monster monster) {
        System.out.println(hero.getName() + " versus " + monster.getName() + " at (" + monster.getX() + ", " + monster.getY() + ")");
        Random random = new Random();

        while (hero.isAlive() && monster.isAlive()) {
            int heroDamage = random.nextInt(10) + 1; // Hero does between 1 and 10 damage
            int monsterDamage = random.nextInt(5) + 1; // Monster does between 1 and 5 damage

            monster.takeDamage(heroDamage);
            System.out.println(hero.getName() + " hits for " + heroDamage + ". " + monster.getName() + " has " + (monster.isAlive() ? monster.getHealth() : 0) + " health left.");

            if (monster.isAlive()) {
                hero.takeDamage(monsterDamage);
                System.out.println(monster.getName() + " hits for " + monsterDamage + ". " + hero.getName() + " has " + (hero.isAlive() ? hero.getHealth() : 0) + " health left.");
            }
        }

        if (!monster.isAlive()) {
            System.out.println(monster.getName() + " has been defeated!");
        }
    }

    public static void main(String[] args) {
        CatacombCrawler game = new CatacombCrawler("Hero", 10);
        game.playGame();
    }
}
