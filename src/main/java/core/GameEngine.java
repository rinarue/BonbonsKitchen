package core;

import core.Dish;
import core.Ingredient;
import core.GameRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {
    private final List<Ingredient> allIngredients;
    private final List<Customer> customers;
    private final List<Dish> discoveredDishes;
    private static Inventory inventory;
    private static Player player;
    private static GameEngine instance = new GameEngine();

    public GameEngine() {
        this.allIngredients = initializeIngredients();
        GameRegistry.setAllIngredients(allIngredients);
        GameRegistry.setAllDishes(initializeDishes());
        this.customers = initializeCustomers();
        this.discoveredDishes = new ArrayList<>();
        this.inventory = new Inventory();
        this.player = new Player(20); // starting money
    }

    private List<Ingredient> initializeIngredients() {
        List<Ingredient> list = new ArrayList<>();
        list.add(new Ingredient("Milk", 2, "assets/ingredients/milk.png"));
        list.add(new Ingredient("Egg", 2, "assets/ingredients/egg.png"));
        list.add(new Ingredient("Flour", 2, "assets/ingredients/flour.png"));
        list.add(new Ingredient("Sugar", 3, "assets/ingredients/sugar.png"));
        list.add(new Ingredient("Butter", 3, "assets/ingredients/butter.png"));
        list.add(new Ingredient("Tomato", 4, "assets/ingredients/tomato.png"));
        // Add more as needed
        return list;
    }

    private List<Dish> initializeDishes() {
        List<Dish> dishes = new ArrayList<>();

        Ingredient milk = getIngredientByName("Milk");
        Ingredient sugar = getIngredientByName("Sugar");
        Ingredient egg = getIngredientByName("Egg");
        Ingredient flour = getIngredientByName("Flour");
        Ingredient butter = getIngredientByName("Butter");
        Ingredient tomato = getIngredientByName("Tomato");

        // Example dishes
        dishes.add(new Dish("Pancakes", List.of(flour, milk, egg), 12, "assets/dishes/pancakes.png"));
        dishes.add(new Dish("Tomato Soup", List.of(tomato, milk), 13, "assets/dishes/tomato_soup.png"));
        dishes.add(new Dish("Cake", List.of(flour, egg, sugar, butter), 18, "assets/dishes/cake.png"));

        return dishes;
    }

    private List<Customer> initializeCustomers() {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer("Suna", "Loves tomatoes", getIngredientByName("Tomato"), "assets/customers/suna.png"));
        list.add(new Customer("Clem", "Prefers breakfast dishes", getIngredientByName("Milk"), "assets/customers/clem.png"));
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
    public static Inventory getInventory() { return inventory; }
    public static Player getPlayer() { return player; }

    public void discoverDish(Dish dish) {
        if (discoveredDishes.stream().noneMatch(d -> d.getName().equals(dish.getName()))) {
            discoveredDishes.add(dish);
        }
    }

    private final Map<Dish, Integer> cookedDishes = new HashMap<>();

    public void addCookedDish(Dish dish) {
        cookedDishes.put(dish, cookedDishes.getOrDefault(dish, 0) + 1);
    }

    public int getCookedDishCount(Dish dish) {
        return cookedDishes.getOrDefault(dish, 0);
    }

    public static Map<Dish, Integer> getAllCookedDishes() {
        return cookedDishes;
    }


    public static GameEngine getInstance() {
        return instance;
    }
}