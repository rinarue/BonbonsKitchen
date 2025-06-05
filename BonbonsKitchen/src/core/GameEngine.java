package core;

import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private final Player player;
    private final List<Customer> customers;
    private final List<Dish> dishesMade;
    private final List<Ingredient> availableIngredients; // NEW
    private final List<Recipe> knownRecipes = new ArrayList<>();
    private final List<Recipe> discoveredRecipes = new ArrayList<>();
    private int currentCustomerIndex;

    public GameEngine() {
        player = new Player(10); // starts with $10
        customers = new ArrayList<>();
        dishesMade = new ArrayList<>();
        availableIngredients = new ArrayList<>();
        currentCustomerIndex = 0;

        initializeIngredientsAndCustomers();
    }

    private void initializeIngredientsAndCustomers() {
        // Create Ingredients (replace with your own images)
        Ingredient flour = new Ingredient("Flour", "assets/flour.png", 2);
        Ingredient tomato = new Ingredient("Tomato", "assets/tomato.png", 2);
        Ingredient leafyGreen = new Ingredient("Leafy Green", "assets/leafygreen.png", 2);
        Ingredient egg = new Ingredient("Egg", "assets/egg.png", 2);
        Ingredient sugar = new Ingredient("Sugar", "assets/sugar.png", 2);
        Ingredient apple = new Ingredient("Apple", "assets/apple.png", 3);
        Ingredient milk = new Ingredient("Milk", "assets/milk.png", 3);
        Ingredient potato = new Ingredient("Potato", "assets/potato.png", 3);
        Ingredient butter = new Ingredient("Butter", "assets/butter.png", 3);
        Ingredient mushroom = new Ingredient("Mushroom", "assets/mushroom.png", 3);
        Ingredient cheese = new Ingredient("Cheese", "assets/cheese.png", 3);
        Ingredient rice = new Ingredient("Rice", "assets/rice.png", 3);
        Ingredient noodles = new Ingredient("Noodles", "assets/noodles.png", 3);
        Ingredient chicken = new Ingredient("Chicken", "assets/chicken.png", 4);
        Ingredient beef = new Ingredient("Beef", "assets/beef.png", 4);
        Ingredient pork = new Ingredient("Pork", "assets/pork.png", 4);
        Ingredient fish = new Ingredient("Fish", "assets/fish.png", 4);
        Ingredient honey = new Ingredient("Honey", "assets/honey.png", 4);

        // Add to available ingredients list
        availableIngredients.add(flour);
        availableIngredients.add(tomato);
        availableIngredients.add(leafyGreen);
        availableIngredients.add(egg);
        availableIngredients.add(sugar);
        availableIngredients.add(apple);
        availableIngredients.add(milk);
        availableIngredients.add(potato);
        availableIngredients.add(butter);
        availableIngredients.add(mushroom);
        availableIngredients.add(cheese);
        availableIngredients.add(rice);
        availableIngredients.add(noodles);
        availableIngredients.add(chicken);
        availableIngredients.add(beef);
        availableIngredients.add(pork);
        availableIngredients.add(fish);
        availableIngredients.add(honey);

        knownRecipes.add(new Recipe("Tomato Soup", List.of(tomato, butter), 5));
        knownRecipes.add(new Recipe("Cheesy Greens", List.of(leafyGreen, cheese), 6));
        knownRecipes.add(new Recipe("Apple Pie", List.of(flour, apple, sugar), 8));
        knownRecipes.add(new Recipe("Fried Egg", List.of(egg, butter), 4));

        // Add some ingredients to player's inventory to start
        player.getInventory().addIngredient(tomato, 3);
        player.getInventory().addIngredient(cheese, 2);

        // Create customers
        customers.add(new Customer("Suna", "Loves tangy veggies!", tomato, "suna.png"));
        customers.add(new Customer("Clem", "Cheese fanatic", cheese, "clem.png"));
        customers.add(new Customer("Phoenix", "Save the bees!", honey, "phoenix.png"));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getCurrentCustomer() {
        if (currentCustomerIndex >= customers.size()) return null;
        return customers.get(currentCustomerIndex);
    }

    public void nextCustomer() {
        if (currentCustomerIndex < customers.size() - 1) {
            currentCustomerIndex++;
        }
    }

    public List<Dish> getDishesMade() {
        return dishesMade;
    }

    public void addDish(Dish dish) {
        dishesMade.add(dish);
    }

    public List<Recipe> getKnownRecipes() {
        return knownRecipes;
    }

    public List<Recipe> getDiscoveredRecipes() {
        return discoveredRecipes;
    }

    public boolean isRecipeDiscovered(Recipe recipe) {
        return discoveredRecipes.contains(recipe);
    }

    public void discoverRecipe(Recipe recipe) {
        if (!discoveredRecipes.contains(recipe)) {
            discoveredRecipes.add(recipe);
        }
    }

    /**
     * Calculates how much money customer will pay based on dish and their favorite ingredient.
     * If dish contains favorite ingredient, full base payment.
     * Otherwise, half base payment.
     */
    public int calculatePayment(Customer customer, Dish dish) {
        if (dish.containsIngredient(customer.getFavoriteIngredient())) {
            return dish.getPrice() + 3; // customer pays base price +3 if favorite ingredient
        } else {
            return dish.getPrice();
        }
    }

    public List<Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }


}
