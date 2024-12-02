import java.util.ArrayList;
import java.util.List;

public class Hero extends Actor {
    private final List<Item> inventory;

    public Hero(String name) {
        super(name, 100); // Hero starts with 100 health
        this.inventory = new ArrayList<>();
    }

    public void collectItem(Item item) {
        inventory.add(item);
        System.out.println("You collected a " + item.getName() + "!");
    }

    public void usePotion() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getType().equals("potion")) {
                restoreHealth(20); // Restore health
                inventory.remove(i); // Remove the potion from inventory
                System.out.println("You used a health potion and restored 20 health points!");
                System.out.println("Current health: " + getHealth());
                return;
            }
        }
        System.out.println("You have no potions to use!");
    }

    public void restoreHealth(int amount) {
        health += amount;
        if (health > 100) {
            health = 100; // Cap health at 100
        }
    }

    public void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + " (" + item.getType() + ")");
            }
        }
    }
}
