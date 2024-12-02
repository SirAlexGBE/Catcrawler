public abstract class Actor {
    protected String name;
    protected int health;

    public Actor(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void takeDamage(int damage) {
        health = Math.max(health - damage, 0); // Ensure health does not drop below 0
    }

    public boolean isAlive() {
        return health > 0;
    }
}
