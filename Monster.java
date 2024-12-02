import java.util.Random;

public class Monster extends Actor {
    private final int x; // Monster's X position
    private final int y; // Monster's Y position
    private static final Random random = new Random();

    public Monster(int id, int x, int y) {
        super("Monster " + id, 25); // Monsters start with 25 health
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int attack() {
        return random.nextInt(5) + 1; // Damage between 1 and 5
    }
}
