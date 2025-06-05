package core;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final List<Ingredient> allIngredients;
    private final List<Customer> customers;
    private final List<Dish> discoveredDishes;
    private final Inventory inventory;
    private static Player player;

    public GameEngine(String playerName) {
        this.allIngredients = initializeIngredients();
        GameRegistry.setAllIngredients(allIngredients);
        this.customers = initializeCustomers();
        this.discoveredDishes = new ArrayList<>();
        this.inventory = new Inventory();
        this.player = new Player(20.00); // starting money
    }

    private List<Ingredient> initializeIngredients() {
        List<Ingredient> list = new ArrayList<>();
        list.add(new Ingredient("Milk", 2.0, "file:assets/ingredients/milk.png"));
        list.add(new Ingredient("Egg", 1.5, "file:assets/ingredients/egg.png"));
        list.add(new Ingredient("Flour", 1.0, "file:assets/ingredients/flour.png"));
        list.add(new Ingredient("Sugar", 1.2, "file:assets/ingredients/sugar.png"));
        list.add(new Ingredient("Butter", 2.5, "file:assets/ingredients/butter.png"));
        // Add more as needed
        return list;
    }

    private List<Customer> initializeCustomers() {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer("Suna", "Loves eggs", getIngredientByName("Egg"), "assets/customers/suna.png"));
        list.add(new Customer("Clem", "Prefers creamy dishes", getIngredientByName("Milk"), "assets/customers/clem.png"));
        list.add(new Customer("Phoenix", "Enjoys sweets", getIngredientByName("Sugar"), "assets/customers/phoenix.png"));
        // Add more as needed
        return list;
    }

    private Ingredient getIngredientByName(String name) {
        return allIngredients.stream()
                .filter(i -> i.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Ingredient> getAllIngredients() { return allIngredients; }
    public List<Customer> getCustomers() { return customers; }
    public List<Dish> getDiscoveredDishes() { return discoveredDishes; }
    public Inventory getInventory() { return inventory; }
    public static Player getPlayer() { return player; }

    public void discoverDish(Dish dish) {
        if (discoveredDishes.stream().noneMatch(d -> d.getName().equals(dish.getName()))) {
            discoveredDishes.add(dish);
        }
    }
}