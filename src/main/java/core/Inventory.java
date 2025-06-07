package core;

import core.Ingredient;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<Ingredient, Integer> ingredientCounts = new HashMap<>();

    public void addIngredient(Ingredient ingredient) {
        ingredientCounts.put(ingredient, ingredientCounts.getOrDefault(ingredient, 0) + 1);
    }

    public boolean useIngredient(Ingredient ingredient) {
        if (ingredientCounts.getOrDefault(ingredient, 0) > 0) {
            ingredientCounts.put(ingredient, ingredientCounts.get(ingredient) - 1);
            return true;
        }
        return false;
    }

    public Map<Ingredient, Integer> getInventoryMap() {
        return new HashMap<>(ingredientCounts);
    }

    public int getQuantity(Ingredient ingredient) {
        return ingredientCounts.getOrDefault(ingredient, 0);
    }
}