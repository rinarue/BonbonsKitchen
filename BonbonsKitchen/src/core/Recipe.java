package core;

import java.util.List;

public class Recipe {
    private final String name;
    private final List<Ingredient> ingredients;
    private final int basePrice;

    public Recipe(String name, List<Ingredient> ingredients, int basePrice) {
        this.name = name;
        this.ingredients = ingredients;
        this.basePrice = basePrice;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public boolean matches(List<Ingredient> inputIngredients) {
        if (inputIngredients.size() != ingredients.size()) return false;
        return inputIngredients.containsAll(ingredients) && ingredients.containsAll(inputIngredients);
    }
}
