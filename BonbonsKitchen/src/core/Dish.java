package core;

import java.util.List;

public class Dish {
    private final String name;
    private final List<Ingredient> ingredients;
    private final double cost;
    private final double sellPrice;

    public Dish(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.cost = calculateCost();
        this.sellPrice = calculateSellPrice();
    }

    private double calculateCost() {
        return ingredients.stream().mapToDouble(Ingredient::getPrice).sum();
    }

    private double calculateSellPrice() {
        return cost * 1.5;
    }

    public String getName() { return name; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public double getCost() { return cost; }
    public double getSellPrice() { return sellPrice; }

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    @Override
    public String toString() {
        return name;
    }
}
