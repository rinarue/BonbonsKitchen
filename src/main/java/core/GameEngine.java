package core;

import core.Dish;
import core.Ingredient;
import core.GameRegistry;

import java.util.*;

public class GameEngine {
    private final List<Ingredient> allIngredients;
    private final List<Customer> customers;
    private final List<Dish> discoveredDishes;
    private static Inventory inventory;
    private static Player player;
    private static GameEngine instance = new GameEngine();
    private int currentDay = 1;
    private List<Customer> customersToday = new ArrayList<>();
    private int customersServedToday = 0;
    private final Random random = new Random();
    private final Set<Customer> customersAppeared = new HashSet<>();

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
        dishes.add(new Dish("Pancakes", List.of(flour, milk, egg), 7, "assets/dishes/pancakes.png"));
        dishes.add(new Dish("Tomato Soup", List.of(tomato, milk), 8, "assets/dishes/tomato_soup.png"));
        dishes.add(new Dish("Cake", List.of(flour, egg, sugar, butter), 9, "assets/dishes/cake.png"));

        return dishes;
    }

    private List<Customer> initializeCustomers() {
        List<Customer> list = new ArrayList<>();
        list.add(new Customer("Suna", "Loves tomatoes", getIngredientByName("Tomato"), "assets/customers/suna.png"));
        list.add(new Customer("Clem", "Dairy fiend", getIngredientByName("Milk"), "assets/customers/clem.png"));
        list.add(new Customer("Phoenix", "Major sweet tooth", getIngredientByName("Sugar"), "assets/customers/phoenix.png"));
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

    private static final Map<Dish, Integer> cookedDishes = new HashMap<>();

    public void addCookedDish(Dish dish) {
        cookedDishes.put(dish, cookedDishes.getOrDefault(dish, 0) + 1);
    }

    public static int getCookedDishCount(Dish dish) {
        return cookedDishes.getOrDefault(dish, 0);
    }

    public static Map<Dish, Integer> getAllCookedDishes() {
        return cookedDishes;
    }

    public static Dish getDishByName(String name) {
        for (Dish dish : getAllCookedDishes().keySet()) {
            if (dish.getName().equals(name)) {
                return dish;
            }
        }
        return null;
    }

    public static void removeCookedDish(Dish dish) {
        Map<Dish, Integer> cooked = getAllCookedDishes();
        int currentQty = cooked.getOrDefault(dish, 0);
        if (currentQty > 1) {
            cooked.put(dish, currentQty - 1);
        } else {
            cooked.remove(dish);
        }
    }

    public void startNewDay() {
        customersToday.clear();
        customersServedToday = 0;

        int numCustomers = 1 + random.nextInt(3); // 1–3 customers
        List<Customer> shuffled = new ArrayList<>(getCustomers());
        Collections.shuffle(shuffled);

        Set<Customer> uniqueCustomers = new LinkedHashSet<>(shuffled); // removes accidental dupes

        int i = 0;
        for (Customer c : uniqueCustomers) {
            if (i >= numCustomers) break;
            customersToday.add(c);
            i++;
        }

    }

    public void customerServed(Customer customer) {
        customersServedToday++;
    }

    public boolean isDayComplete() {
        return customersServedToday >= customersToday.size();
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void advanceDay() {
        currentDay++;
    }

    public List<Customer> getCustomersToday() {
        return customersToday;
    }

    public int getCustomersServedToday() {
        return customersServedToday;
    }

    public static GameEngine getInstance() {
        return instance;
    }
}