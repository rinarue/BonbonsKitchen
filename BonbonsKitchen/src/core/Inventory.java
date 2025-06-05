package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inventory {
    private final Map<Ingredient, Integer> ingredientQuantities;

    public Inventory() {
        this.ingredientQuantities = new HashMap<>();
    }

    public void addIngredient(Ingredient ingredient, int quantity) {
        ingredientQuantities.put(ingredient, ingredientQuantities.getOrDefault(ingredient, 0) + quantity);
    }

    public boolean removeIngredient(Ingredient ingredient, int quantity) {
        int current = ingredientQuantities.getOrDefault(ingredient, 0);
        if (current < quantity) {
            return false; // not enough
        }
        if (current == quantity) {
            ingredientQuantities.remove(ingredient);
        } else {
            ingredientQuantities.put(ingredient, current - quantity);
        }
        return true;
    }

    public int getQuantity(Ingredient ingredient) {
        return ingredientQuantities.getOrDefault(ingredient, 0);
    }

    public Set<Ingredient> getIngredients() {
        return ingredientQuantities.keySet();
    }

    public boolean hasIngredient(Ingredient ingredient) {
        return ingredientQuantities.containsKey(ingredient) && ingredientQuantities.get(ingredient) > 0;
    }
}
