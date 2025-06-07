package core;

import core.Ingredient;
import java.util.List;

public class Dish {
    private final String name;
    private final List<Ingredient> ingredients;
    private final int sellPrice;
    private final String imagePath;

    public Dish(String name, List<Ingredient> ingredients, int sellPrice, String imagePath) {
        this.name = name;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public int getSellPrice() { return sellPrice; }
    public String getImagePath() { return imagePath; }

    public boolean containsIngredient(Ingredient ingredient) {
        return ingredients.contains(ingredient);
    }

    @Override
    public String toString() {
        return name;
    }
}
