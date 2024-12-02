import java.util.Arrays;
import java.util.List;

public class Item {
    private String name;
    private String type; // e.g., "potion", "gold"
    private static final List<String> validTypes = Arrays.asList("potion", "gold", "weapon");

    public Item(String name, String type) {
        if (!validTypes.contains(type)) {
            throw new IllegalArgumentException("Invalid item type: " + type);
        }
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
